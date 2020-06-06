package pt.up.fe.specs.utils;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Comparator;

public class GraphOutputComparator implements Comparator<Graph> {
    Utils utils = new Utils();

    public int compare(Graph one, Graph two) {
        Node end1 = one.getNode("End");
        Node end2 = two.getNode("End");
        Edge edge1 = end1.getEnteringEdge(0);
        Edge edge2 = end2.getEnteringEdge(0);
        if (edge1.hasAttribute("label") && edge2.hasAttribute("label")) {
            ArrayList<Integer> indexes1 = utils.getIndexes(edge1.getAttribute("label"));
            ArrayList<Integer> indexes2 = utils.getIndexes(edge2.getAttribute("label"));
            return indexes1.get(0) - indexes2.get(0);
        }
        return 0;
    }
}
