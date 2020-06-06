package pt.up.fe.specs.utils;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Comparator;

public class GraphInputComparator implements Comparator<Graph> {
    Utils utils = new Utils();

    public int compare(Graph one, Graph two) {
        Node start1 = one.getNode("Start");
        Node start2 = two.getNode("Start");
        Edge edge1 = start1.getLeavingEdge(0);
        Edge edge2 = start2.getLeavingEdge(0);
        if (edge1.hasAttribute("label") && edge2.hasAttribute("label")) {
            ArrayList<Integer> indexes1 = utils.getIndexes(edge1.getAttribute("label"));
            ArrayList<Integer> indexes2 = utils.getIndexes(edge2.getAttribute("label"));
            return indexes1.get(0) - indexes2.get(0);
        }
        return 0;
    }
}
