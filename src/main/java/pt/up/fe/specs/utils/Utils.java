package pt.up.fe.specs.utils;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

public class Utils {

    private static final String startNodeID = "Start";
    private static final String endNodeID = "End";

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
            Integer index1 = indexes1.get(dim);
            Integer index2 = indexes2.get(dim);
            if (index2 == null || index1 == null) {
                continue;
            }
            if (!index1.equals(index2)) {
                return index1 - index2;
            }
        }
        return 0;
    }

    public static Node getStartNode(Graph graph) {
        return graph.getNode(startNodeID);
    }

    public static Node getEndNode(Graph graph) {
        return graph.getNode(endNodeID);
    }

    public static String getLabel(Element element) {
        return element.getAttribute("label");
    }

    public static String getName(Element element) {
        return element.hasAttribute("name") ? element.getAttribute("name") : getLabel(element);}

    public static String getPos(Element element) {
        if (!element.hasAttribute("pos")) return "";
        return element.getAttribute("pos");
    }

    public static boolean isArray(Element element) {
        if (!element.hasAttribute("label")) return false;
        return getLabel(element).contains("[") || (element.hasAttribute("array") && element.getAttribute("array").equals(true));
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

    public static void setNameFromLabel(Element e) {
        e.setAttribute("name", varNameFromLabel(getLabel(e)));
    }

    public static boolean hasName(Element e) {
        return e.hasAttribute("name");
    }

    public static void setName(Element e, String newName) {
        e.setAttribute("name", newName);
    }

    public static void setLabel(Element e, String label) {
        e.setAttribute("label", label);
    }

    public static void markVisited(Element e) {
        e.setAttribute("visited", true);
    }

    public static boolean isVisited(Element e) {
        if (!e.hasAttribute("visited")) return false;
        return e.getAttribute("visited").equals(true);
    }

    public static void setArray(Element e) {
        e.setAttribute("array", true);
    }

    public static boolean isVar(Element element) {
        if (!element.hasAttribute("att1")) return false;
        return element.getAttribute("att1").equals("var");
    }

    public static boolean isConst(Element element) {
        if (!element.hasAttribute("att1")) return false;
        return element.getAttribute("att1").equals("const");
    }

    public static boolean isComplexAssignment(Element element) {
        if (!element.hasAttribute("att1")) return false;
        return element.getAttribute("att1").equals("complexAssignment");
    }

    public static String getVarType(Element element) {
        return element.getAttribute("att3");
    }

    public static boolean isNOP(Element element) {
        if (!element.hasAttribute("att1")) return false;
        return element.getAttribute("att1").equals("nop");
    }


    public static boolean isLocalVar(Element element) {
        if (!isVar(element)) return false;
        if (!element.hasAttribute("att2")) return false;
        return element.getAttribute("att2").equals("loc");
    }


    public static boolean isParamVar(Element element) {
        if (!isVar(element)) return false;
        if (!element.hasAttribute("att2")) return false;
        String att2 = element.getAttribute("att2");
        return att2.equals("param") || att2.equals("inte");
    }

    public static boolean isGlobalVar(Element element) {
        if (!isVar(element)) return false;
        if (!element.hasAttribute("att2")) return false;
        return element.getAttribute("att2").equals("global");
    }

    public static boolean isOperation(Element element) {
        if (!element.hasAttribute("att1")) return false;
        return element.getAttribute("att1").equals("op");
    }

    public static boolean isMux(Element element) {
        if (!element.hasAttribute("att1")) return false;
        return element.getAttribute("att1").equals("mux");
    }

    public static Integer getLevel(Element element) {
        return element.getAttribute("level");
    }

    public static boolean isSumOperation(Element element) {
        if (!isOperation(element)) return false;
        return getLabel(element).equals("+");
    }

    public static boolean isAssignment(Element element) {
        if (!element.hasAttribute("att1")) return false;
        return element.getAttribute("att1").equals("assignment");
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


    public static boolean isCall(Element e) {
        if (!e.hasAttribute("att1")) return false;
        return e.getAttribute("att1").equals("call");
    }

    public static boolean isFunction(Element e) {
        if (!e.hasAttribute("att1")) return false;
        return e.getAttribute("att1").equals("function");
    }

    public static void setLoopInfo(Element e) {
        e.setAttribute("loopinfo");
    }

    public static LoopInfo getLoopInfo(Element e) {
        return e.getAttribute("loopinfo");
    }


    public static boolean hasLoopInfo(Element e) {
        return e.hasAttribute("loopinfo");
    }

    public static void setFoldInfo(Graph g, FoldInfo foldInfo) {
       g.setAttribute("foldInfo", foldInfo);
    }

    public static FoldInfo getFoldInfo(Graph g) {
        return g.getAttribute("foldInfo");
    }

    public static boolean hasFoldInfo(Graph g) {
        return g.hasAttribute("foldInfo");
    }

    public static void setLevel(Node node, int level) {
        node.setAttribute("level", level);
    }

    public static void setMaxLevel(Graph graph, int maxLevel) {
        graph.setAttribute("maxlevel", maxLevel);
    }

    public static boolean isLeveled(Node node) {
        return node.hasAttribute("level");
    }

    public static void setLevelGraph(Graph graph, List<List<Node>> levelGraph) {
        graph.setAttribute("levelgraph", levelGraph);
        graph.addAttribute("level", true);
    }

    public static List<List> getLevelGraph(Graph graph) {
        return graph.getAttribute("levelgraph");
    }

    public static void clearLevelingAttributes(Graph graph) {
        graph.removeAttribute("levelgraph");
        graph.removeAttribute("level");
        graph.removeAttribute("maxlevel");
    }
}
