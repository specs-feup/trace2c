package pt.up.fe.specs.utils;

import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    /**
     * Isolate indexes of an array access.
     * @param label    label of array access,
     * @return indexes
     */
    public ArrayList<Integer> getIndexes(String label) {
        String temp = label;
        ArrayList<Integer> indexes = new ArrayList<>();
        while (temp.lastIndexOf("[") != -1) {

            temp = temp.substring(temp.indexOf("["));
            String temp2 = temp.substring(1, temp.indexOf("]"));

            indexes.add(Integer.parseInt(temp2));
            temp = temp.substring(temp.indexOf("]"));
        }
        return indexes;
    }

    public boolean isArray(String label) {
        return label.contains("[");
    }

    public boolean isStartNode(Node node) {
        return node.getId().equals("Start");
    }

    public boolean isEndNode(Node node) {
        return node.getId().equals("End");
    }

    public String varNameFromLabel(String label) {
        if (label.contains("[")) {
            return label.substring(0, label.indexOf("["));
        } else {
            return label;
        }
    }
}
