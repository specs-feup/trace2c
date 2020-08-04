package pt.up.fe.specs.utils;

import org.graphstream.graph.Element;
import org.graphstream.graph.Node;

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
     * Isolate the dimensions and indexes of an array.
     *
     * @param label label of array access.
     * @return dimension of array.
     */
    public static int getArrayDimension(String label) {
        String temp = label;
        int dim = 0;
        while (temp.lastIndexOf("[") != -1) {
            temp = temp.substring(temp.indexOf("]") + 1);
            dim++;
        }
        return dim;

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
            try {
                Integer intValue = Integer.parseInt(temp2);
                indexes.add(intValue);
            } catch (NumberFormatException exception) {
                indexes.add(null);
            }

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


    public static String getLabel(Element element) {
        return element.getAttribute("label");
    }

    public static String getName(Element element) { return element.getAttribute("name");}

    public static boolean isArray(Element element) {
        if (!element.hasAttribute("label")) return false;
        return getLabel(element).contains("[");
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

    public static boolean isVar(Element element) {
        if (!element.hasAttribute("att1")) return false;
        if (element.getAttribute("att1").equals("var")) return true;
        return  false;
    }

    public static boolean isConst(Element element) {
        if (!element.hasAttribute("att1")) return false;
        if (element.getAttribute("att1").equals("const")) return true;
        return  false;
    }

    public static boolean isComplexAssignment(Element element) {
        if (!element.hasAttribute("att1")) return false;
        if (element.getAttribute("att1").equals("complexAssignment")) return true;
        return false;
    }

    public static boolean isArrayAccess(Element element) {
        if (!element.hasAttribute("att1") || !element.hasAttribute("att2")) return false;
        if (element.getAttribute("att1").equals("arrayAccess")) return true;
        return false;
    }

    public static boolean isNOP(Element element) {
        if (!element.hasAttribute("att1")) return false;
        if (element.getAttribute("att1").equals("nop")) return true;
        return false;
    }


    public static boolean isLocalVar(Element element) {
        if (!isVar(element)) return false;
        if (!element.hasAttribute("att2")) return false;
        if (element.getAttribute("att2").equals("loc")) return true;
        return false;
    }


    public static boolean isParamVar(Element element) {
        if (!isVar(element)) return false;
        if (!element.hasAttribute("att2")) return false;
        String att2 = element.getAttribute("att2");
        if (att2.equals("param") || att2.equals("inte")) return true;
        return false;
    }

    public static boolean isGlobalVar(Element element) {
        if (!isVar(element)) return false;
        if (!element.hasAttribute("att2")) return false;
        if (element.getAttribute("att2").equals("global")) return true;
        return false;
    }

    public static boolean isOperation(Element element) {
        if (!element.hasAttribute("att1")) return false;
        if (element.getAttribute("att1").equals("op")) return true;
        return false;
    }

    public static boolean isMux(Element element) {
        if (!element.hasAttribute("att1")) return false;
        if (element.getAttribute("att1").equals("mux")) return true;
        return false;
    }

    public static boolean isSumOperation(Element element) {
        if (!isOperation(element)) return false;
        if (getLabel(element).equals("+")) return true;
        return false;
    }

    public static boolean isAssignment(Element element) {
        if (!element.hasAttribute("att1")) return false;
        if (element.getAttribute("att1").equals("assignment")) return true;
        return false;
    }



    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }




}
