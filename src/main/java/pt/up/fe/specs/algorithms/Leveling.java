package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Algorithm that levels a given dataflow.
 *
 * @author Afonso
 */
public class Leveling implements Algorithm {

    Graph graph;
    private HashSet<Node> nodeList = new HashSet<>();
    private HashSet<Node> tempNodeList = new HashSet<>();
    private List<Node> clearNodeList = new ArrayList<>();
    private List<List<Node>> levelGraph = new ArrayList<List<Node>>();
    private int level;

    @Override
    /**
     * Initializes algorithm. Checks if graphs has passed through the algorithm already.
     * If true erases information from previous leveling. Start node
     * selected as beginning of algorithm
     */
    public void init(Graph graph) {
        System.out.println("Initiating leveling");
        this.graph = graph;
        this.level = 0;
        this.nodeList = new HashSet<>();
        tempNodeList = new HashSet<>();
        clearNodeList = new ArrayList<>();
        levelGraph = new ArrayList<>();
        // graph.display();
        clearLeveling();

        levelGraph.add(new ArrayList<>());
        Node start = graph.getNode("Start");

        start.addAttribute("level", this.level);
        levelGraph.get(level).add(start);
        addChildrenToTempList(start);
        nodeList.addAll(tempNodeList);
        tempNodeList.clear();
        graph.addAttribute("level", true);
        System.out.println("Leveling initiated");

    }

    @Override
    /**
     * Main body of the algorithm. Applies the levels to the nodes. After graph is leveled
     * the algorithm is called for the lower levels.
     *
     */
    public void compute() {
        //System.out.println("Starting graph leveling ext");
        graph.addAttribute("level", true);
        boolean printNodeListSizeFlag = false;
        while (!nodeList.isEmpty()) {
            level++;
            levelGraph.add(new ArrayList<>());
            int prevNodeListSize = nodeList.size();


            for (Node n : nodeList) {
                if (markforLevel(n, level)) {
                    if (n.getOutDegree() != 0)
                        addChildrenToTempList(n);
                    clearNodeList.add(n);
                }
            }

            for (Node n : clearNodeList) {
                n.addAttribute("level", level);
                nodeList.remove(n);
            }

            clearNodeList.clear();

            for (Node n : tempNodeList) {
                if (!nodeList.contains(n))
                    nodeList.add(n);
            }
            if (prevNodeListSize == nodeList.size()) {
                System.out.println("Leveling might be stuck in a loop at level: " + level + "; node list size: " + nodeList.size());
            }

            tempNodeList.clear();

        }

        graph.addAttribute("levelgraph", levelGraph);
        graph.addAttribute("maxlevel", level);
        System.out.println("Graph: " + graph.getId() + " has " + (level+1) + " levels");

        if (graph.hasAttribute("subgraphs")) {
            for (Graph subgraph: graph.<List<Graph>>getAttribute("subgraphs")) {
                Leveling leveling = new Leveling();
                leveling.init(subgraph);
                leveling.compute();
            }
        }
    }

    /**
     * Checks if node can be leveled
     *
     * @param n     Node to mark.
     * @param level level to assign if possible.
     * @return true if node marked.
     */
    private boolean markforLevel(Node n, int level) {
        boolean set = true;
        for (Edge e : n.getEachEnteringEdge()) {
            Node sourceNode = e.getSourceNode();
            if (!sourceNode.hasAttribute("level")) {
                set = false;
                break;
            }
        }

        if (set && !n.hasAttribute("level"))
            levelGraph.get(level).add(n);

        return set;

    }


    /**
     * Adds nodes connected to outgoing edges of a given node to a temporary list of nodes to be checked.
     *
     * @param n Node from which the child nodes are obtained.
     */
    private void addChildrenToTempList(Node n) {
        for (Edge e : n.getEachLeavingEdge()) {
            if (!e.getTargetNode().equals(n))
                tempNodeList.add(e.getTargetNode());
        }
    }

    /**
     * Gets the highest level of the leveled graph.
     *
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the created leveled graph
     *
     * @return
     */
    public List<List<Node>> getLevelGraph() {
        return levelGraph;
    }

    /**
     * Clears the information of a prior leveling from the graph.
     */
    private void clearLeveling() {

        for (Node n : graph) {
            n.removeAttribute("level");
        }
        graph.removeAttribute("levelgraph");
        graph.removeAttribute("maxlevel");
        levelGraph.clear();
    }

}