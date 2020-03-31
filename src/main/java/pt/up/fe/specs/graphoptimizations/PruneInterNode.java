package pt.up.fe.specs.graphoptimizations;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

import java.util.ArrayList;
import java.util.List;


/**
 * Algorithm dedicated to pruning intermediate nodes
 * 
 * @author CPU TOSH
 *
 */
public class PruneInterNode implements Algorithm {

    Graph graph;
    List<Node> removelist = new ArrayList<>();
    int ne;

    public PruneInterNode() {
        // TODO Auto-generated constructor stub
    }

    @Override
    /**
     * 
     * Initialization of algorithm
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        this.graph = graph;
        removelist = new ArrayList<>();
        ne = 0;
    }

    @Override
    /**
     * 
     * main method applying the algorithm. Selects the nodes to cut and 
     * call the method for their removal
     */
    public void compute() {
        // TODO Auto-generated method stub
        List<Node> io = new ArrayList<>();
        for (Node n : graph) {
            String a = n.getAttribute("att1").toString();

            if (!a.equals("nop") && !a.equals("call") && !a.equals("op")) {

                if (n.getEnteringEdge(0).getSourceNode().getAttribute("label").equals("start") ||
                        n.getLeavingEdge(0).getTargetNode().getAttribute("label").equals("end")) {
                    io.add(n);
                }
                if (!n.getEnteringEdge(0).getSourceNode().getAttribute("label").equals("start"))
                    removelist.add(n);
            }

        }

        for (Node n : removelist)
            removeIntermediate(n);

        for (Node n : io) {
            for (Edge e : n.getEachEdge()) {
                Graphs.copyAttributes(n, e);
            }
        }

    }

    /**
     * Method that deals with removal of intermediate nodes. If a node is between two
     * op nodes it is cut and replaced with a single edge that stores the variable information.
     * If not the case, the node is replaced with a op node that implements an assignment and the
     * variable information is again stored in edges.
     * 
     * @param n Node to handled.
     */
    public void removeIntermediate(Node n) {
        Node prev1 = n.getEnteringEdge(0).getSourceNode();
        Node nxt = n.getLeavingEdge(0).getTargetNode();
        boolean not_remove = false;

        if (prev1.getAttribute("att1").equals("op") && nxt.getAttribute("att1").equals("op")) {

            for (Edge e : n.getEachLeavingEdge()) {
                if (e.getTargetNode().getAttribute("att1").equals("var"))
                    System.out.println("name " + n.getId() + " next " + e.getTargetNode().getId());
                ne++;
                try {
                    Edge newEdge = graph.addEdge(prev1.getId() + "->" + e.getTargetNode().getId() + "_" + ne, prev1, e.getTargetNode(),
                            true);
                    Graphs.copyAttributes(e, newEdge);
                    Graphs.copyAttributes(n, newEdge);
                } catch (EdgeRejectedException ex) {
                    //Very rare exception for some input DFGs. In principle should not occur if input graph is correct
                    System.out.println("Pruning: already exist edge between op node");
                    if (prev1.hasEdgeBetween(e.getNode1().getId())) {
                        Graphs.copyAttributes(n, e);
                        not_remove = true;
                    }
                }
            }
            if (!not_remove)
                graph.removeNode(n);
            else {
                n.clearAttributes();
                n.addAttribute("att1", "op");
                n.addAttribute("label", "=");
            }
        }
        else{
            Graphs.copyAttributes(n, n.getEnteringEdge(0));
            for (Edge e : n.getEachLeavingEdge()) {
                Graphs.copyAttributes(n, e);
            }
            n.clearAttributes();
            n.addAttribute("att1", "op");
            n.addAttribute("label", "=");

        }
    }

}
