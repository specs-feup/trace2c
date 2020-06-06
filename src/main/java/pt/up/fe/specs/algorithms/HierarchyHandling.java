package pt.up.fe.specs.algorithms;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.Graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to call algorithms to handle generic and simple tasks for hierarchy levels.
 * 
 * @author CPU TOSH
 *
 */
public class HierarchyHandling {

    

    /**
     * Displays all the subgraphs in the hierarchy.
     * @param graph Graph to be displayed.
     */
    public static void displayFullHierarchy(Graph graph) {
        graph.display();
        
        if(!graph.hasAttribute("hierarchy"))
            return;
        List<Graph> graphlist = new ArrayList<>();
        List<String> hierarchy = graph.getAttribute("hierarchy");
        for (String s : hierarchy) {
            Node n = graph.getNode(s);
            graphlist = graph.getAttribute(n.getId());
            Graph temg = graphlist.get(graphlist.size() - 1);
            displayFullHierarchy(temg);
        }
        return;
    }
    
    /**
     * Removes all unnecessary var and const nodes, in case a hierarchy level does not contain op nodes.
     * @param graph Graph to trim
     * @return 
     * @return      trimmed Graph.
     */
    public Graph trimHierarchy(Graph graph) {
        boolean trim = true;
        for (Node n : graph) {

            if (n.getAttribute("att1").toString().equals("op"))
                trim = false;
        }
        if (trim) {
            Graph newg = new DefaultGraph("graph");
            Graphs.copyAttributes(graph, newg);
            newg.addNode("HyperNode");
            Graphs.copyAttributes(graph.getNode("HyperNode"), newg.getNode("HyperNode"));
            add_start_end(newg);
            // g.addEdge("end from: HyperNode", newg.getNode("HyperNode"), newg.getNode("End"), true);
            newg.addAttribute("trim", true);
            graph = newg;

        }

        return graph;

    }
    
    /**
     * Adds start and end nodes to dataflow.
     * @param g
     */
    private static void add_start_end(Graph g) {

        g.addNode("Start");
        g.getNode("Start").addAttribute("label", "start");
        g.getNode("Start").addAttribute("att1", "nop");
        g.addNode("End");
        g.getNode("End").addAttribute("label", "end");
        g.getNode("End").addAttribute("att1", "nop");
        for (Node n : g.getEachNode()) {
            if (n.getInDegree() == 0 && !n.equals(g.getNode("Start")) && !n.equals(g.getNode("End")))
                g.addEdge("begin to:" + n.getId(), g.getNode("Start"), n, true);
            if (n.getOutDegree() == 0 && !n.equals(g.getNode("End")) && !n.equals(g.getNode("Start")))
                g.addEdge("end from:" + n.getId(), n, g.getNode("End"), true);

        }
    }

}
