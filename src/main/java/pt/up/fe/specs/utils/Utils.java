package pt.up.fe.specs.utils;

import org.graphstream.graph.Node;
import pt.up.fe.specs.Var;

import java.util.*;

public class Utils {


    /**
     * Receives a variable that is an array, and returns the sizes of all of it's partitions.
     * So, if it receives a variable x[100][10], to be folded on the first dimension, with 2 parallel calls,
     * it returns [ [50][10], [50][10] ].
     * @param var
     * @param numberOfParallelCalls
     * @param dimToSplit
     * @param totalFoldWidth
     * @return
     */
    public static List<List<Integer>> splitDimensions(Var var, Integer dimToSplit, Integer numberOfParallelCalls, Integer totalFoldWidth) {
        var.setSplit(true);
        List<Integer> sizes = var.getSizes();

        List<List<Integer>> allPartitionsSizes = new ArrayList<>();
        LinkedList<Integer> partitions = var.getPartitionsAtDim(dimToSplit);
        if (var.hasPrologue(dimToSplit)) {
            List<Integer> newSizes = new ArrayList<>();
            for (int dim = 0; dim < sizes.size(); dim++) {
                if (dim == dimToSplit) {
                    newSizes.add(partitions.getFirst());
                } else {
                    newSizes.add(sizes.get(dim));
                }
            }
            allPartitionsSizes.add(newSizes);
        }

        int loopWidth = (int) Math.ceil((float)totalFoldWidth / numberOfParallelCalls);
        int parallelizableSize = var.getParallelizableSize(dimToSplit);
        int unrollFactor = parallelizableSize / totalFoldWidth;
        int parallelPartitionsSize = unrollFactor * loopWidth;
        int lastParallelPartitionSize = parallelizableSize - (parallelPartitionsSize * (numberOfParallelCalls - 1));
        for (int i = 0; i < numberOfParallelCalls; i++) {
            List<Integer> newSizes = new ArrayList<>();
            for (int dim = 0; dim < sizes.size(); dim++) {
                if (dim == dimToSplit) {
                    if (i == numberOfParallelCalls - 1) {
                        newSizes.add(lastParallelPartitionSize);
                    } else {
                        newSizes.add(parallelPartitionsSize);
                    }

                } else {
                    newSizes.add(sizes.get(dim));
                }
            }
            allPartitionsSizes.add(newSizes);
        }
        if (var.hasEpilogue(dimToSplit)) {
            List<Integer> newSizes = new ArrayList<>();
            for (int dim = 0; dim < sizes.size(); dim++) {
                if (dim == dimToSplit) {
                    newSizes.add(sizes.get(dim) - partitions.getLast());
                } else {
                    newSizes.add(sizes.get(dim));
                }
            }
            allPartitionsSizes.add(newSizes);
        }

        return allPartitionsSizes;
    }

    /**
     * Isolate indexes of an array access.
     * @param label    label of array access,
     * @return indexes
     */
    public static ArrayList<Integer> getIndexes(String label) {
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

    public static int compareIndexes(List<Integer> indexes1, List<Integer> indexes2) {
        if (indexes1.size() != indexes2.size()) return indexes1.size() - indexes2.size();
        for (int dim = 0; dim < indexes1.size(); dim++) {
            int index1 = indexes1.get(dim);
            int index2 = indexes2.get(dim);
            if (index1 != index2) {
                return index1 - index2;
            }
        }
        return 0;
    }

    public static boolean isArray(String label) {
        return label.contains("[");
    }

    public static boolean isStartNode(Node node) {
        return node.getId().equals("Start");
    }

    public static boolean isEndNode(Node node) {
        return node.getId().equals("End");
    }

    public static String varNameFromLabel(String label) {
        if (label.contains("[")) {
            return label.substring(0, label.indexOf("["));
        } else {
            return label;
        }
    }
}
