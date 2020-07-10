package pt.up.fe.specs.utils;

import org.graphstream.graph.Edge;

import java.util.ArrayList;
import java.util.Comparator;

public class EdgeComparator implements Comparator<Edge> {

    Utils utils = new Utils();
    String varToFold;

    public void setVarToFold(String s) {
        varToFold = s;
    }

    private int compareIndexes(String label1, String label2) {
        ArrayList<Integer> indexes1 = utils.getIndexes(label1);
        ArrayList<Integer> indexes2 = utils.getIndexes(label2);
        if (indexes1.size() != indexes2.size()) {
            return indexes1.size() - indexes2.size();
        }
        if (indexes1.size() == 0) {
            return 0;
        }
        for (int i = 0; i < indexes1.size(); i++) {
            int size1 = indexes1.get(i);
            int size2 = indexes2.get(i);
            if (size1 != size2) {
                return size1 - size2;
            }
        }
        return 0;
    }

    @Override
    public int compare(Edge e1, Edge e2) {
        String label1 = e1.getAttribute("label");
        String label2 = e2.getAttribute("label");
        if (label1 == null || label2 == null) {
            System.out.println("Debug");
        }
        String name1 = e1.getAttribute("name");
        String name2 = e2.getAttribute("name");
        if (varToFold != null) {
            if (e1.hasAttribute("array") && e2.hasAttribute("array")) {
                if (name1.equals(varToFold)) {
                    if (name2.equals(varToFold)) {
                        return compareIndexes(label1, label2);
                    } else {
                        return -1;
                    }
                } else if (name2.equals(varToFold)) {
                    return 1;
                }
            } else if (e1.hasAttribute("array")) {
                if (name1.equals(varToFold)) {
                    return -1;
                }

            } else if (e2.hasAttribute("array")) {
                if (name2.equals(varToFold)) {
                    return 1;
                }
            }
        }
        if (e1.hasAttribute("array") && e2.hasAttribute("array")) {
            if (name1.equals(name2)) {
                return compareIndexes(label1, label2);
            } else {
                return name1.compareTo(name2);
            }
        }
        if (e1.hasAttribute("array")) {
            return -1;
        }
        if (e2.hasAttribute("array")) {
            return 1;
        }

        return label1.compareTo(label2);

    }
}
