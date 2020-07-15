package pt.up.fe.specs.utils;

import org.graphstream.graph.Node;

import java.util.Comparator;
import java.util.List;

public class NodeListComparator implements Comparator<List<Node>> {

    @Override
    public int compare(List<Node> o1, List<Node> o2) {
        NodeComparator nodeComparator = new NodeComparator();
        o1.sort(nodeComparator);
        o2.sort(nodeComparator);
        return nodeComparator.compare(o1.get(0), o2.get(0));
    }
}
