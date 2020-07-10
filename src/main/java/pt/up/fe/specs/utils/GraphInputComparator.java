package pt.up.fe.specs.utils;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class GraphInputComparator implements Comparator<Graph> {
    private final HashMap<String, List<Edge>> graphIdToEdgesLeavingStart;
    Utils utils = new Utils();

    public GraphInputComparator(HashMap<String, List<Edge>> graphIdToEdgesLeavingStart) {
        this.graphIdToEdgesLeavingStart = graphIdToEdgesLeavingStart;
    }


    public int compare(Graph one, Graph two) {
        List<Edge> edges1 = graphIdToEdgesLeavingStart.get(one.getId());
        List<Edge> edges2 = graphIdToEdgesLeavingStart.get(two.getId());
        for (int i = 0; i < edges1.size(); i++) {
            Edge edge1 = edges1.get(i);
            Edge edge2 = edges2.get(i);
            if (edge1.hasAttribute("array") && edge2.hasAttribute("array")) {
                String label1 = edge1.getAttribute("label");
                String label2 = edge2.getAttribute("label");
                ArrayList<Integer> indexes1 = utils.getIndexes(label1);
                ArrayList<Integer> indexes2 = utils.getIndexes(label2);
                for (int j = 0; j < indexes1.size(); j++) {
                    if (indexes1.get(j) != indexes2.get(j)) {
                        return indexes1.get(j) - indexes2.get(j);
                    }
                }
                return edge1.getAttribute("name").hashCode() - edge2.getAttribute("name").hashCode();
            }
        }

        return 0;
    }
}
