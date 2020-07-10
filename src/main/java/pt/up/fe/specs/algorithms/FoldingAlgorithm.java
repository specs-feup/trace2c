package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.Config;
import pt.up.fe.specs.Var;
import pt.up.fe.specs.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * To fold we need to find the progression for each variable in the subgraph;
 */
public class FoldingAlgorithm implements Algorithm {

    private static Integer loopCounter = 0;
    private final Config config;
    private ArrayList<Graph> subgraphList;
    private Graph graph;
    private Utils utils = new Utils();
    private String loopName = "parallelLoop";
    private String functionName;
    private Graph functionGraph;
    private CInfo mainInfo;
    
    public FoldingAlgorithm(Config config, ArrayList<Graph> subgraphList ){
        this.config = config;
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
        // I have to update var accesses on the subgraphList here
        setVarsPartitions(subgraphList);
        updateVarsAccesses(subgraphList);
        computeLoopInfoForAllEdges(subgraphList);
        functionGraph = subgraphList.get(0);
        FoldInfo foldInfo = functionGraph.getAttribute("foldInfo");
        foldInfo.setWidth(subgraphList.size());
        foldInfo.setInitialValue(0);
        foldInfo.setIncrement(1);
        functionGraph.setAttribute("functionName", functionName);
        addLoopNames();
        addOutputsToVarsToFold(foldInfo);



        ArrayList<Graph> subgraphs = graph.getAttribute("subgraphs");
        if (subgraphs == null) {
            subgraphs = new ArrayList<>();
        }
        subgraphs.add(functionGraph);
        graph.setAttribute("subgraphs", subgraphs);


    }

    private void addOutputsToVarsToFold(FoldInfo foldInfo) {
        Node endNode = functionGraph.getNode("End");
        for (Edge edge: endNode.getEachEnteringEdge()) {
            if (edge.hasAttribute("name") && edge.hasAttribute("array")) {
                String name = edge.getAttribute("name");
                if (!foldInfo.hasVar(name)) {
                    foldInfo.addVar(name, 0);
                }
            }
        }
    }

    private void setVarsPartitions(ArrayList<Graph> subgraphList) {
        List<Var> inputs = mainInfo.getInputs();
        for (Var input: inputs) {
            if (input.isArray()) {
                // find the first and last indexes that are used inside the folded graph
                List<Integer> sizes = input.getSizes();
                Integer dimToPartition = config.getDimToPartition(input.getName());
                Integer dimMaxSize = sizes.get(dimToPartition);

                int minIndex = getVarMinUsedIndex(subgraphList.get(0), input.getName(), dimToPartition);
                int maxIndexInFirstSubgraph = getVarMaxUsedIndex(subgraphList.get(0), input.getName(), dimToPartition);
                int maxIndex = getVarMaxUsedIndex(subgraphList.get(subgraphList.size() - 1), input.getName(), dimToPartition);
                if (minIndex != 0) {
                    input.addPartition(minIndex, dimToPartition);
                }
                if (maxIndex != dimMaxSize - 1) {
                    input.addPartition(maxIndex + 1, dimToPartition);
                }
                input.setHlsPartition(new HLSPartition(dimToPartition, maxIndexInFirstSubgraph - minIndex + 1));
            }
        }
    }

    private int getVarMinUsedIndex(Graph firstSubgraph, String var, int dim) {
        int minIndex = Integer.MAX_VALUE;
        for (Edge edge: firstSubgraph.getEachEdge()){
            if (edge.hasAttribute("name") && edge.getAttribute("name").equals(var)) {
                String label = edge.getAttribute("label");
                List<Integer> indexes = utils.getIndexes(label);
                int index = indexes.get(dim);
                if (index < minIndex) {
                    minIndex = index;
                }
            }
        }
        return minIndex;
    }

    private int getVarMaxUsedIndex(Graph lastSubgraph, String var, int dim) {
        int maxIndex = 0;
        for (Edge edge: lastSubgraph.getEachEdge()){
            if (edge.hasAttribute("name") && edge.getAttribute("name").equals(var)) {
                String label = edge.getAttribute("label");
                List<Integer> indexes = utils.getIndexes(label);
                int index = indexes.get(dim);
                if (index > maxIndex) {
                    maxIndex = index;
                }
            }
        }
        return maxIndex;
    }

    private void updateVarsAccesses(ArrayList<Graph> subgraphList) {
        CInfo info = graph.getAttribute("info");
        List<Var> vars = new ArrayList<>();
        vars.addAll(info.getInputs());
        vars.addAll(info.getLocalInfo());
        vars.addAll(info.getOutputs());

        for (Var var: vars) {
            if (var.isArray() && var.hasPartitions()) {
                int dimToPartition = config.getDimToPartition(var.getName());
                int firstPartitionIndex = var.getPartitionsMap().get(dimToPartition).getFirst();
                for (int i = 0; i < 3; i++) {
                    // update the var accesses only on the first 3 subgraphs
                    Graph subgraph = subgraphList.get(i);
                    for (Edge edge: subgraph.getEachEdge()) {
                        if (edge.hasAttribute("name") && edge.getAttribute("name").equals(var.getName())) {
                            String label = edge.getAttribute("label");
                            ArrayList<Integer> indexes = utils.getIndexes(label);
                            String newLabelSuffix = new String();
                            for (int j=0; j < indexes.size(); j++) {
                                int newIndex = indexes.get(j);
                                if (j == dimToPartition) {
                                    newIndex = newIndex - firstPartitionIndex;
                                }
                                newLabelSuffix = newLabelSuffix.concat("[" + newIndex + "]");
                            }
                            edge.setAttribute("label", var.getName() + newLabelSuffix);
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

    private void recursivelyComputeLoopInfo(Node node0, Node node1, Node node2) {
        if (node0.getId().equals("End")) {
            return;
        }
        List<Edge> edgesLeavingNode0 = StreamSupport.stream(node0.getEachLeavingEdge().spliterator(), false).collect(Collectors.toList());
        List<Edge> edgesLeavingNode1 = StreamSupport.stream(node1.getEachLeavingEdge().spliterator(), false).collect(Collectors.toList());
        List<Edge> edgesLeavingNode2 = StreamSupport.stream(node2.getEachLeavingEdge().spliterator(), false).collect(Collectors.toList());
        orderEdges(edgesLeavingNode0);
        orderEdges(edgesLeavingNode1);
        orderEdges(edgesLeavingNode2);


        for (int i = 0; i < edgesLeavingNode0.size(); i++) {
            Edge edge0 = edgesLeavingNode0.get(i);
            Edge edge1 = edgesLeavingNode1.get(i);
            Edge edge2 = edgesLeavingNode2.get(i);
            boolean hasLoopInfo = edge0.hasAttribute("loopinfo");
            LoopInfo loopInfo = edge0.getAttribute("loopinfo");
            if (hasLoopInfo) {
                if (loopInfo.hasSpecificLoopInfo(loopName)) {
                    continue;
                }
            } else {
                loopInfo = new LoopInfo();
            }
            String label0 = edge0.getAttribute("label");
            String label1 = edge1.getAttribute("label");
            String label2 = edge2.getAttribute("label");
            ArrayList<Integer> indexes0 = utils.getIndexes(label0);
            ArrayList<Integer> indexes1 = utils.getIndexes(label1);
            ArrayList<Integer> indexes2 = utils.getIndexes(label2);
            if (indexes0.size() > 0) {
                try {
                    SpecificLoopInfo specificLoopInfo = getSpecificLoopInfo(indexes0, indexes1, indexes2);
                    loopInfo.addSpecificLoopInfo(specificLoopInfo);
                    edge0.setAttribute("loopinfo", loopInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            recursivelyComputeLoopInfo(edge0.getTargetNode(), edge1.getTargetNode(), edge2.getTargetNode());
        }
    }

    private void computeLoopInfoForAllEdges(ArrayList<Graph> subgraphList) {
        Graph subgraph0 = subgraphList.get(0);
        Graph subgraph1 = subgraphList.get(1);
        Graph subgraph2 = subgraphList.get(2);
        Node node0 = subgraph0.getNode("Start");
        Node node1 = subgraph1.getNode("Start");
        Node node2 = subgraph2.getNode("Start");

        recursivelyComputeLoopInfo(node0, node1, node2);

    }

    private SpecificLoopInfo getSpecificLoopInfo(ArrayList<Integer> indexes0, ArrayList<Integer> indexes1, ArrayList<Integer> indexes2) throws Exception {
        ArrayList<ProgressionType> progressionTypes = new ArrayList<>();
        ArrayList<Integer> ratios = new ArrayList<>();
        ArrayList<Integer> initialValues = new ArrayList<>();
        for (int i = 0; i < indexes0.size(); i++) {
            Integer ratio = indexes1.get(i) - indexes0.get(i);
            initialValues.add(indexes0.get(0));
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
                    Exception e = new Exception("Invalid progression");
                    throw e;
                }
            }
        }
        return new SpecificLoopInfo(loopName, ratios, progressionTypes, initialValues);
    }
}
