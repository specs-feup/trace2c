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
    public List<List<Integer>> splitDimensions(Var var, Integer dimToSplit, Integer numberOfParallelCalls, Integer totalFoldWidth) {
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
        int lastParallelPartitionSize = totalFoldWidth - (parallelizableSize * (numberOfParallelCalls - 1));
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
