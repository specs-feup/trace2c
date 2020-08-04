package pt.up.fe.specs.customNodes;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

public class ArrayAccessNode {
    Node node;

    public ArrayAccessNode(Node n) {
        this.node = n;
    }

    public String getArrayName() {
        return node.getAttribute("att2");
    }

    public int getArrayDimension() {
        return node.getInDegree();
    }

    public Edge getEdgeAtDimension(int dim) {
        for (Edge inEdge: node.getEachEnteringEdge()) {
            int edgeDim = Integer.parseInt(inEdge.getAttribute("dim"));
            if (edgeDim == dim) {
                return inEdge;
            }
        }
        return null;
    }
}
