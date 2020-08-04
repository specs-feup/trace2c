package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import pt.up.fe.specs.utils.Utils;
import sun.nio.ch.Util;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;


/**
 * Algorithm dedicated to pruning intermediate nodes
 * 
 * @author CPU TOSH
 *
 */
public class Pruning implements Algorithm {

    Graph graph;
    int ne;

    @Override
    /**
     * 
     * Initialization of algorithm
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        this.graph = graph;
        ne = 0;
        System.out.println("Initializing pruning - node count: " + graph.getNodeCount());
        System.out.println("Initializing pruning - edge count: " + graph.getEdgeCount());
    }

    @Override
    /**
     * 
     * main method applying the algorithm. Selects the nodes to cut and 
     * call the method for their removal
     */
    public void compute() {
        // TODO Auto-generated method stub
        List<Node> nodes = graph.getNodeSet().stream().collect(toCollection(ArrayList::new));


        for (Node n: nodes) {
            if (isDisconnected(n)) {
                safeRemoveNode(n);
            } else {
                if (Utils.isVar(n) || Utils.isConst(n)) {
                    removeIntermediate(n);
                }
            }


        }
        System.out.println("Pruning finished - node count: " + graph.getNodeCount());
        System.out.println("Pruning finished - edge count: " + graph.getEdgeCount());

    }

    private boolean isDisconnected(Node n) {
        return n.getInDegree() == 0 && n.getOutDegree() == 0;
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

        if (n.getInDegree() <= 1) {
            Node prev = n.getEnteringEdge(0).getSourceNode();
            String mod = getModAttribute(n);
            for (Edge edgeToTarget: n.getEachLeavingEdge()) {
                Node targetNode = edgeToTarget.getTargetNode();

                if (Utils.isConst(n) && Utils.isVar(targetNode)) {
                    for (Edge nxtLeaveEdge: targetNode.getEachLeavingEdge()) {
                        Node target = nxtLeaveEdge.getTargetNode();
                        Edge newEdge = graph.addEdge(prev.getId() + "->" + target.getId(), prev, target, true);
                        Graphs.copyAttributes(nxtLeaveEdge, newEdge);
                        Graphs.copyAttributes(n, newEdge);
                    }
                    safeRemoveNode(targetNode);
                } else {
                    Edge newEdge = graph.addEdge(prev.getId() + "->" + targetNode.getId() + "_" + ne++, prev, targetNode, true);
                    Graphs.copyAttributes(edgeToTarget, newEdge);
                    Graphs.copyAttributes(n, newEdge);
                    if (!mod.isEmpty()) {
                        newEdge.addAttribute("mod", mod);
                    }
                }
            }
            safeRemoveNode(n);
        }
    }

    private void safeRemoveNode(Node n) {
        if (graph.getNode(n.getId()) != null) {
            graph.removeNode(n);
        }
    }

    private String getModAttribute(Node n) {
        String mod = new String();
        if (n.getLeavingEdge(0).hasAttribute("mod")) {
            mod = mod.concat(n.getLeavingEdge(0).getAttribute("mod"));
        }
        if (n.getEnteringEdge(0).hasAttribute("mod")) {
            mod = mod.concat(n.getEnteringEdge(0).getAttribute("mod"));
        }
        return mod;
    }

}
