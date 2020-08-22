package pt.up.fe.specs.utils;

import org.graphstream.graph.Edge;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EdgeComparator implements Comparator<Edge> {

    String varToFold;

    public void setVarToFold(String s) {
        varToFold = s;
    }

    private int compareIndexes(String label1, String label2) {
        ArrayList<Integer> indexes1 = Utils.getIndexes(label1);
        ArrayList<Integer> indexes2 = Utils.getIndexes(label2);
        if (indexes1.size() != indexes2.size()) {
            return indexes1.size() - indexes2.size();
        }
        if (indexes1.size() == 0) {
            return 0;
        }
        for (int i = 0; i < indexes1.size(); i++) {
            Integer size1 = indexes1.get(i);
            Integer size2 = indexes2.get(i);
            if (!size1.equals(size2)) {
                return size1 - size2;
            }
        }
        return 0;
    }

    @Override
    public int compare(Edge e1, Edge e2) {
        String label1 = Utils.getLabel(e1);
        String label2 = Utils.getLabel(e2);
        if (label1 == null || label2 == null) {
            System.err.println("Debug null label");
            return 0;
        }
        String name1 = Utils.getName(e1);
        String name2 = Utils.getName(e2);
        if (varToFold != null) {
            if (Utils.isArray(e1) && Utils.isArray(e2)) {
                if (name1.equals(varToFold)) {
                    if (name2.equals(varToFold)) {
                        return compareIndexes(label1, label2);
                    } else {
                        return -1;
                    }
                } else if (name2.equals(varToFold)) {
                    return 1;
                }
            } else if (Utils.isArray(e1)) {
                if (name1.equals(varToFold)) {
                    return -1;
                }

            } else if (Utils.isArray(e2)) {
                if (name2.equals(varToFold)) {
                    return 1;
                }
            }
        }
        if (Utils.isArray(e1) && Utils.isArray(e2)) {
            if (name1.equals(name2)) {
                return compareIndexes(label1, label2);
            } else {
                return name1.compareTo(name2);
            }
        }
        if (Utils.isArray(e1)) {
            return -1;
        }
        if (Utils.isArray(e2)) {
            return 1;
        }

        // create regex to extract numbers from end of the labels;
        Pattern pattern = Pattern.compile("(\\d*)$");
        Matcher matcherLabel1 = pattern.matcher(label1);
        Matcher matcherLabel2 = pattern.matcher(label2);
        if (matcherLabel1.find() && matcherLabel2.find()) {
            if (!matcherLabel1.group().isEmpty() && !matcherLabel2.group().isEmpty()) {
                int match1 = Integer.parseInt(matcherLabel1.group());
                int match2 = Integer.parseInt(matcherLabel2.group());
                return match1 - match2;
            }
        }

        return label1.compareTo(label2);

    }
}
