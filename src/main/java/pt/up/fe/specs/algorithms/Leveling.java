package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Algorithm that levels a given dataflow.
 *
 * @author Renato
 */
public class Leveling implements Algorithm {

    Graph graph;
    private HashSet<Node> nodeSet = new HashSet<>();
    private List<Node> nodesLeveled = new ArrayList<>();
    private List<HashSet<Node>> levelGraph = new ArrayList<>();
    private int level;

    @Override
    /**
     * Initializes algorithm. Checks if graphs has passed through the algorithm already.
     * If true erases information from previous leveling. Start node
     * selected as beginning of algorithm
     */
    public void init(Graph graph) {
        System.out.println("Initiating leveling on graph " + graph.getId());
        this.graph = graph;
        this.level = 0;
        this.nodeSet = new HashSet<>();
        nodesLeveled = new ArrayList<>();
        levelGraph = new ArrayList<>();
        // graph.display();
        clearLeveling();

        levelGraph.add(new HashSet<>());
        Node start = graph.getNode("Start");

        start.addAttribute("level", this.level);
        levelGraph.get(level).add(start);
        nodeSet.addAll(getChildren(start));
        graph.addAttribute("level", true);
        System.out.println("Leveling initiated on graph" + graph.getId());

    }

    private boolean areAllParentsLeveled(Node n) {
        for (Edge e: n.getEachEnteringEdge()) {
            if (!Utils.isLeveled(e.getSourceNode())) {
                return false;
            }
        }
        return true;
    }

    @Override
    /**
     * Main body of the algorithm. Applies the levels to the nodes. After graph is leveled
     * the algorithm is called for the lower levels.
     *
     */
    public void compute() {
        System.out.println("Starting graph leveling compute function");
        graph.addAttribute("level", true);
        while (!nodeSet.isEmpty()) {
            if (level % 10000 == 0) System.out.println("Processing level " + level);
            level++;
            levelGraph.add(new HashSet<>());

            Set<Node> childrenToAdd = new HashSet<>();

            for (Node n : nodeSet) {
                levelNode(n, level);
                if (n.getOutDegree() != 0) {
                    childrenToAdd.addAll(getChildren(n));
                }
                nodesLeveled.add(n);
            }

            nodeSet.removeAll(nodesLeveled);
            nodeSet.addAll(childrenToAdd);
            nodesLeveled.clear();

        }

        Utils.setLevelGraph(graph, levelGraph);
        graph.addAttribute("levelgraph", levelGraph);
        graph.addAttribute("maxlevel", level);
        System.out.println("Leveling Finished: graph " + graph.getId() + " has " + (level+1) + " levels");

        if (graph.hasAttribute("subgraphs")) {
            for (Graph subgraph: graph.<List<Graph>>getAttribute("subgraphs")) {
                init(subgraph);
                compute();
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
    private void levelNode(Node n, int level) {
        if (Utils.isLeveled(n)) System.err.println("Leveling: Cyclic graph at node " + n.getId());
            levelGraph.get(level).add(n);
            n.addAttribute("level", level);
    }


    /**
     * Gets children that can be leveled
     *
     * @param n Node from which the child nodes are obtained.
     */
    private Set<Node> getChildren(Node n) {
        Set<Node> children = new HashSet<>();
        for (Edge e : n.getEachLeavingEdge()) {
            Node child = e.getTargetNode();
            if (!children.contains(child)) {
                if (!child.equals(n) && areAllParentsLeveled(child)) {
                    children.add(child);
                }
            }
        }
        return children;
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
    public List<HashSet<Node>> getLevelGraph() {
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