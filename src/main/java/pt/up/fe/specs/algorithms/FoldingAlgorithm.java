package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.Config;
import pt.up.fe.specs.utils.Var;
import pt.up.fe.specs.utils.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * To fold we need to find the progression for each variable in the subgraph;
 */
public class FoldingAlgorithm implements Algorithm {

    private static Integer loopCounter = 0;
    private List<Graph> subgraphList;
    private Graph graph;
    private String loopName = "parallelLoop";
    private String functionName;
    private Graph functionGraph;
    private CInfo mainInfo;
    private HashMap<String, VarAccesses> varsAccessesInfo = new HashMap<>();

    public FoldingAlgorithm(List<Graph> subgraphList) {
        this.subgraphList = subgraphList;
    }

    @Override
    public void init(Graph graph) {
        this.graph = graph;
        this.loopName = this.loopName + loopCounter++;
        this.mainInfo = graph.getAttribute("info");
    }

    @Override
    public void compute() {
        System.out.println("Folding algorithm starting");
        // I have to update var accesses on the subgraphList here
        computeVarsMinAccessesInfo(subgraphList.get(0));
        computeVarsMaxAccessesInfo(subgraphList.get(subgraphList.size() - 1));
        updateVarsAccesses(subgraphList);
        computeLoopInfoForAllEdges(subgraphList);
        functionGraph = subgraphList.get(0);
        FoldInfo foldInfo = functionGraph.getAttribute("foldInfo");
        foldInfo.setWidth(subgraphList.size());
        foldInfo.setInitialValue(0);
        foldInfo.setIncrement(1);
        functionGraph.setAttribute("functionName", functionName);
        addLoopNames();
        addInputsToVarsToFold(foldInfo);
        addOutputsToVarsToFold(foldInfo);
        Leveling leveling = new Leveling();
        leveling.init(functionGraph);
        leveling.compute();

        /**
         * Remove once the parallelize sums algorithm runs for all paths
         * The current parallelize sums may not parallelize all chains because it follows only 1 path
         * Thus, when we dont parallelize before folding, we can try to parallelize after folding
         */
        if (!Config.isToParallelizeSums()) {
            Algorithm parallelizeSums = new ParallelizeSums();
            parallelizeSums.init(functionGraph);
            parallelizeSums.compute();
        }


        ArrayList<Graph> subgraphs = graph.getAttribute("subgraphs");
        if (subgraphs == null) {
            subgraphs = new ArrayList<>();
        }
        subgraphs.add(functionGraph);
        graph.setAttribute("subgraphs", subgraphs);


    }

    private void computeVarsMinAccessesInfo(Graph firstSubgraph) {
        for (Edge e : firstSubgraph.getEachEdge()) {
            if (Utils.isArray(e)) {
                String name = Utils.getName(e);
                List<Integer> indexes = Utils.getIndexes(Utils.getLabel(e));
                varsAccessesInfo.putIfAbsent(name, new VarAccesses());
                VarAccesses oldAccesses = varsAccessesInfo.get(name);
                if (oldAccesses.hasMinIndexes()) {
                    List<Integer> oldMinIndexes = oldAccesses.getMinIndexes();
                    if (Utils.compareIndexes(indexes, oldMinIndexes) < 0) {
                        oldAccesses.setMinIndexes(indexes);
                    }
                } else {
                    oldAccesses.setMinIndexes(indexes);
                }
            }
        }
    }

    private void computeVarsMaxAccessesInfo(Graph lastSubgraph) {
        for (Edge e : lastSubgraph.getEachEdge()) {
            if (Utils.isArray(e)) {
                String name = Utils.getName(e);
                List<Integer> indexes = Utils.getIndexes(Utils.getLabel(e));
                varsAccessesInfo.putIfAbsent(name, new VarAccesses());
                VarAccesses oldAccesses = varsAccessesInfo.get(name);
                if (oldAccesses.hasMaxIndexes()) {
                    List<Integer> oldMaxIndexes = oldAccesses.getMaxIndexes();
                    if (Utils.compareIndexes(indexes, oldMaxIndexes) > 0) {
                        oldAccesses.setMaxIndexes(indexes);
                    }
                } else {
                    oldAccesses.setMaxIndexes(indexes);
                }
            }
        }
    }

    private void computeFoldInfoFromEdge(FoldInfo foldInfo, Edge edge) {
        if (Utils.isArray(edge)) {
            String name = Utils.getName(edge);
            if (!foldInfo.hasVar(name)) {
                LoopInfo loopInfo = Utils.getLoopInfo(edge);
                SpecificLoopInfo firstLoopInfo = loopInfo.getLoopList().get(0);
                for (int dim = 0; dim < firstLoopInfo.dim; dim++) {
                    int ratio = firstLoopInfo.ratios.get(dim);
                    if (ratio != 0) {
                        foldInfo.addVar(name, dim);
                    }
                }

            }
            if (!foldInfo.hasVarHLSPartitionDim(name)) {
                LoopInfo loopInfo = Utils.getLoopInfo(edge);
                SpecificLoopInfo firstLoopInfo = loopInfo.getLoopList().get(0);
                for (int dim = 0; dim < firstLoopInfo.dim; dim++) {
                    int ratio = firstLoopInfo.ratios.get(dim);
                    if (ratio == 0) {
                        foldInfo.setVarsHLSPartitionDimension(name, dim);
                    }
                }
            }
        }
    }

    /**
     * Inputs which accesses vary between iterations, should be added to the vars to fold in the dimension that varies.
     *
     * @param foldInfo
     */
    private void addInputsToVarsToFold(FoldInfo foldInfo) {
        Node startNode = Utils.getStartNode(functionGraph);
        for (Edge edge : startNode.getEachLeavingEdge()) {
            computeFoldInfoFromEdge(foldInfo, edge);
        }

    }

    private void addOutputsToVarsToFold(FoldInfo foldInfo) {
        Node endNode = Utils.getEndNode(functionGraph);
        for (Edge edge : endNode.getEachEnteringEdge()) {
            computeFoldInfoFromEdge(foldInfo, edge);
        }
    }

    private void updateVarsAccesses(List<Graph> subgraphList) {
        List<Var> vars = new ArrayList<>();
        vars.addAll(mainInfo.getInputs());
        vars.addAll(mainInfo.getLocalInfo());
        vars.addAll(mainInfo.getOutputs());

        for (Var var : vars) {
            if (varsAccessesInfo.containsKey(var.getName())) {
                int dimToPartition = Config.getDimToPartition(var.getName());
                int minIndexOnDimToPartition = varsAccessesInfo.get(var.getName()).getMinIndexes().get(dimToPartition);
                for (int i = 0; i < 3; i++) {
                    // update the var accesses only on the first 3 subgraphs
                    Graph subgraph = subgraphList.get(i);
                    for (Edge edge : subgraph.getEachEdge()) {

                        if (Utils.hasName(edge) && Utils.getName(edge).equals(var.getName())) {
                            String label = Utils.getLabel(edge);
                            ArrayList<Integer> indexes = Utils.getIndexes(label);
                            String newLabelSuffix = "";
                            for (int j = 0; j < indexes.size(); j++) {
                                int newIndex = indexes.get(j);
                                if (j == dimToPartition) {
                                    newIndex = newIndex - minIndexOnDimToPartition;
                                }
                                newLabelSuffix = newLabelSuffix.concat("[" + newIndex + "]");
                            }
                            Utils.setLabel(edge, var.getName() + newLabelSuffix);
                        }
                    }
                }
            }
        }
    }

    private void addLoopNames() {
        ArrayList<String> loopNames = functionGraph.getAttribute("loopNames");
        if (loopNames == null) {
            loopNames = new ArrayList<>();
        }
        loopNames.add(loopName);
        functionGraph.setAttribute("loopNames", loopNames);
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Graph getFunctionGraph() {
        return functionGraph;
    }

    private void orderEdges(List<Edge> edges) {
        edges.sort(new EdgeComparator());
    }


    private void computeLoopInfoForAllEdges(List<Graph> subgraphList) {
        Graph subgraph0 = subgraphList.get(0);
        Graph subgraph1 = subgraphList.get(1);
        Graph subgraph2 = subgraphList.get(2);
        Node node0 = Utils.getStartNode(subgraph0);
        Node node1 = Utils.getStartNode(subgraph1);
        Node node2 = Utils.getStartNode(subgraph2);
        Queue<Node> nodesQueue = new LinkedList<>();
        nodesQueue.add(node0);
        nodesQueue.add(node1);
        nodesQueue.add(node2);
        while (!nodesQueue.isEmpty()) {
            List<Edge> edgesLeavingNode0 = StreamSupport.stream(nodesQueue.remove().getEachLeavingEdge()
                    .spliterator(), false).collect(Collectors.toList());
            List<Edge> edgesLeavingNode1 = StreamSupport.stream(nodesQueue.remove().getEachLeavingEdge()
                    .spliterator(), false).collect(Collectors.toList());
            List<Edge> edgesLeavingNode2 = StreamSupport.stream(nodesQueue.remove().getEachLeavingEdge()
                    .spliterator(), false).collect(Collectors.toList());
            orderEdges(edgesLeavingNode0);
            orderEdges(edgesLeavingNode1);
            orderEdges(edgesLeavingNode2);
            if (edgesLeavingNode0.size() != edgesLeavingNode1.size() ||
                    edgesLeavingNode1.size() != edgesLeavingNode2.size()) {
                System.err.println("The subgraphs don't look the same!");
            }


            for (int i = 0; i < edgesLeavingNode0.size(); i++) {
                Edge edge0 = edgesLeavingNode0.get(i);
                Edge edge1 = edgesLeavingNode1.get(i);
                Edge edge2 = edgesLeavingNode2.get(i);
                boolean hasLoopInfo = Utils.hasLoopInfo(edge0);
                LoopInfo loopInfo = Utils.getLoopInfo(edge0);
                if (hasLoopInfo) {
                    if (loopInfo.hasSpecificLoopInfo(loopName)) {
                        continue;
                    }
                } else {
                    loopInfo = new LoopInfo();
                }
                ArrayList<Integer> indexes0 = Utils.getIndexes(Utils.getLabel(edge0));
                ArrayList<Integer> indexes1 = Utils.getIndexes(Utils.getLabel(edge1));
                ArrayList<Integer> indexes2 = Utils.getIndexes(Utils.getLabel(edge2));
                if (indexes0.size() > 0) {
                    try {
                        SpecificLoopInfo specificLoopInfo = getSpecificLoopInfo(indexes0, indexes1, indexes2);
                        loopInfo.addSpecificLoopInfo(specificLoopInfo);
                        edge0.setAttribute("loopinfo", loopInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!Utils.isEndNode(edge0.getTargetNode())) {
                    nodesQueue.add(edge0.getTargetNode());
                    nodesQueue.add(edge1.getTargetNode());
                    nodesQueue.add(edge2.getTargetNode());
                }

            }

        }
    }

    private SpecificLoopInfo getSpecificLoopInfo(ArrayList<Integer> indexes0, ArrayList<Integer> indexes1, ArrayList<Integer> indexes2) throws Exception {
        ArrayList<ProgressionType> progressionTypes = new ArrayList<>();
        ArrayList<Integer> ratios = new ArrayList<>();
        ArrayList<Integer> initialValues = new ArrayList<>();
        for (int i = 0; i < indexes0.size(); i++) {
            Integer ratio = indexes1.get(i) - indexes0.get(i);
            initialValues.add(indexes0.get(i));
            if (indexes2.get(i) == indexes0.get(i) + 2 * ratio) {
                // Then it is an arithmetic progression
                progressionTypes.add(ProgressionType.Arithmetic);
                ratios.add(ratio);
            } else {
                // Check if it is a geometric progression
                ratio = indexes1.get(i) / indexes0.get(i);
                if (indexes2.get(i) / ratio == indexes1.get(i)) {
                    // Then it is a geometric progression
                    progressionTypes.add(ProgressionType.Geometric);
                    ratios.add(ratio);
                } else {
                    throw new Exception("Invalid progression");
                }
            }
        }
        return new SpecificLoopInfo(loopName, ratios, progressionTypes, initialValues);
    }
}
