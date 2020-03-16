package pt.up.fe.specs;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Algorithm that levels a given dataflow. 
 * @author Afonso
 *
 */
public class LevelingAlgorithmExt implements Algorithm {

    Graph graph;
    private List<Node> nodelist = new ArrayList<>();
    private List<Node> tempnodelist = new ArrayList<>();
    private List<Node> clearnodelist = new ArrayList<>();
    private List<List<Node>> levelgraph = new ArrayList<List<Node>>();
    private int level;

    @Override
    /**
     * Initializes algorithm. Checks if graphs has passed through the algorithm already.
     * If true erases information from previous leveling. Start node
     * selected as beginning of algorithm 
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        this.graph = graph;
        this.level = 0;
        // graph.display();
        if (graph.hasAttribute("level")) {
            clearLeveling();
        } else
            graph.addAttribute("level", true);

        levelgraph.add(new ArrayList<>());
        Node start = graph.getNode("Start");
        if (start.equals(null)) {
            System.out.println(graph.getId());
            return;
        }

        start.addAttribute("level", this.level);
        levelgraph.get(level).add(start);
        addNodestoList(start);
        nodelist.addAll(tempnodelist);
        tempnodelist.clear();

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

        while (!nodelist.isEmpty()) {
            level++;
            levelgraph.add(new ArrayList<>());

            for (Node n : nodelist) {
                if (markforLevel(n, level)) {
                    if (n.getOutDegree() != 0)
                        addNodestoList(n);
                    clearnodelist.add(n);
                }
            }

            for (Node n : clearnodelist) {
                n.addAttribute("level", level);
                nodelist.remove(n);
            }

            clearnodelist.clear();

            for (Node n : tempnodelist) {
                if (!nodelist.contains(n))
                    nodelist.add(n);
            }

            tempnodelist.clear();

        }

        graph.addAttribute("levelgraph", levelgraph);
        graph.addAttribute("maxlevel", level);
        if (!graph.hasAttribute("hierarchy"))
            return;

        List<String> hierarchy = graph.getAttribute("hierarchy");
        List<Graph> graphlist = new ArrayList<>();
        for (String h : hierarchy) {
            Node n = graph.getNode(h);
            graphlist = graph.getAttribute(n.getId());
            Graph temg = graphlist.get(graphlist.size() - 1);
            Algorithm leveling = new LevelingAlgorithmExt();
            leveling.init(temg);
            leveling.compute();
        }
        //System.out.println("Done graph leveling ext");
    }
    
    /**
     * Checks if node can be leveled
     * 
     * @param n      Node to mark.
     * @param level  level to assign if possible.
     * @return       true if node marked.
     */
    private boolean markforLevel(Node n, int level) {
        boolean set = true;
        for (Edge e : n.getEachEnteringEdge()) {
            if (!e.getNode0().hasAttribute("level"))
                set = false;
        }

        if (set && !n.hasAttribute("level"))
            levelgraph.get(level).add(n);

        return set;

    }
    
    /**
     * Adds nodes connected to outgoing edges of a given node to a temporary list of nodes to be checked.
     * @param n Node from which the child nodes are obtained.
     */
    private void addNodestoList(Node n) {
        for (Edge e : n.getEachLeavingEdge()) {
            if (!e.getNode1().equals(n))
                tempnodelist.add(e.getNode1());
        }
    }
    
    /**
     * Gets the highest level of the leveled graph.
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
        return levelgraph;
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
        levelgraph.clear();
    }

}
