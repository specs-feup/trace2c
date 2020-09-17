package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Algorithm that levels a given dataflow.
 * Warning: It will get stuck in a loop if the graph is cyclic
 *
 * @author Renato
 */
public class Leveling implements Algorithm {

    Graph graph;
    private Stack<Node> nodeStack = new Stack<>();
    private List<List<Node>> levelGraph = new ArrayList<List<Node>>();

    @Override
    /**
     * Initializes algorithm. Checks if graphs has passed through the algorithm already.
     * If true erases information from previous leveling. Start node
     * selected as beginning of algorithm 
     */
    public void init(Graph graph) {
        System.out.println("Initiating leveling");
        this.graph = graph;
        this.nodeStack = new Stack<>();
        levelGraph = new ArrayList<>();
        clearLeveling();
        Node startNode = Utils.getStartNode(graph);
        nodeStack.add(startNode);
        System.out.println("Leveling initiated");
    }

    private int getMaxPreviousLevel(Node n) {
        int maxLevel = -1;
        for (Edge e: n.getEachEnteringEdge()) {
            Node source = e.getSourceNode();
            int sourceLevel = Utils.getLevel(source);
            if (sourceLevel > maxLevel) {
                maxLevel = sourceLevel;
            }
        }
        return maxLevel;
    }

    private void addChildrenToTheStack(Node n) {
        for (Edge e: n.getEachLeavingEdge()) {
            nodeStack.add(e.getTargetNode());
        }
    }

    private void levelAllNodes() {
        while (!nodeStack.isEmpty()) {
            Node node = nodeStack.pop();
            // check that all parents are leveled
            if (!areAllParentsLeveled(node)) continue;
            int prevMaxLevel = getMaxPreviousLevel(node);
            if (Utils.isLeveled(node) && Utils.getLevel(node) > prevMaxLevel) {
                continue; // if this node is leveled and is larger than all previous levels, then stop processing it.
            }
            Utils.setLevel(node, prevMaxLevel + 1);
            addChildrenToTheStack(node);
        }
    }

    private void createLevelGraph() {
        Node endNode = Utils.getEndNode(graph);
        int maxLevel = Utils.getLevel(endNode);
        for (int i = 0; i < maxLevel + 1; i++) {
            levelGraph.add(new ArrayList<>());
        }

        for (Node n: graph) {
            int level = Utils.getLevel(n);
            levelGraph.get(level).add(n);
        }
        Utils.setLevelGraph(graph, levelGraph);
        Utils.setMaxLevel(graph, maxLevel);
        System.out.println("Graph: " + graph.getId() + " has " + (maxLevel+1) + " levels");
    }

    @Override
    /**
     * Main body of the algorithm. Applies the levels to the nodes. After graph is leveled
     * the algorithm is called for the lower levels.
     *
     */
    public void compute() {
       levelAllNodes();
       createLevelGraph();

        if (graph.hasAttribute("subgraphs")) {
            for (Graph subgraph: graph.<List<Graph>>getAttribute("subgraphs")) {
                Leveling leveling = new Leveling();
                leveling.init(subgraph);
                leveling.compute();
            }
        }
    }

    private boolean areAllParentsLeveled(Node node) {
        for (Edge e: node.getEachEnteringEdge()) {
            if (!Utils.isLeveled(e.getSourceNode())) {
                return false;
            }
        }
        return true;
    }


    /**
     * Clears the information of a prior leveling from the graph.
     */
    private void clearLeveling() {

        for (Node n : graph) {
            n.removeAttribute("level");
        }
        Utils.clearLevelingAttributes(graph);
        levelGraph.clear();
    }

}
