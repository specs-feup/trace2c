package pt.up.fe.specs.utils;

import org.graphstream.graph.Node;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node n1, Node n2) {
        if (n1.getInDegree() > 0 && n2.getInDegree() > 0) {
            EdgeComparator edgeComparator = new EdgeComparator();
            return edgeComparator.compare(n1.getEnteringEdge(0), n2.getEnteringEdge(0));
        }
        return 0;
    }
}
