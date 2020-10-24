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
            if (Utils.isArray(edge1) && Utils.isArray(edge2)) {
                String label1 = Utils.getLabel(edge1);
                String label2 = Utils.getLabel(edge2);
                ArrayList<Integer> indexes1 = Utils.getIndexes(label1);
                ArrayList<Integer> indexes2 = Utils.getIndexes(label2);
                for (int j = 0; j < indexes1.size(); j++) {
                    if (indexes1.get(j) != indexes2.get(j)) {
                        return indexes1.get(j) - indexes2.get(j);
                    }
                }

                return Utils.getName(edge1).hashCode() - Utils.getName(edge2).hashCode();
            }
        }

        return 0;
    }
}
