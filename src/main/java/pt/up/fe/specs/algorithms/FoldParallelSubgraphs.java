package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.MultiGraph;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.Config;
import pt.up.fe.specs.Var;
import pt.up.fe.specs.utils.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FoldParallelSubgraphs implements Algorithm {

    private final String arraySuffixStr = "_array";
    private final Config config;
    private int graph1Width;
    private int graph2Width;

    Graph mainGraph;
    private CInfo mainInfo;
    private Utils utils = new Utils();
    private HashMap<String, Integer> varCounter = new HashMap<>();
    private HashMap<String, String> varsToTypes = new HashMap<>();
    private int edgeCounter = 0;
    private int graphCounter = 0;
    private HashMap<String, List<Edge>> graphIdToEdgesLeavingStart = new HashMap<>();
    private static int functionCounter = 0;
    private List<HashSet<Node>> parallelNodeClusters;


    public FoldParallelSubgraphs(List<HashSet<Node>> parallelNodeClusters, Config config) {
        this.config = config;
        this.parallelNodeClusters = parallelNodeClusters;
        this.graph1Width = parallelNodeClusters.size();
        this.graph2Width = 0;
        if (parallelNodeClusters.size() % config.getParallelFunctions() != 0) {
            System.err.println("The number of parallel functions may produce invalid code.");
            System.err.println("Consider updating to some number that divides " + parallelNodeClusters.size());
            graph1Width = (int) Math.ceil((float) parallelNodeClusters.size() / config.getParallelFunctions()) * (config.getParallelFunctions() - 1);
            graph2Width = parallelNodeClusters.size() - graph1Width;
        }
    }

    @Override
    public void init(Graph graph) {
        mainGraph = graph;
        mainInfo = graph.getAttribute("info");
    }

    @Override
    public void compute() {
        List<Graph> functionGraphs = new ArrayList<>();
        List<Node> callNodes = new ArrayList<>();
        List<Node> functionNodes = new ArrayList<>();
        String functionName = "parallel_" + functionCounter++;
        callNodes.add(createCallNode(functionName));
        functionNodes.add(createFunctionNode(functionName));
        if (graph2Width != 0) {
            String function2Name = "parallel_" + functionCounter++;
            callNodes.add(createCallNode(function2Name));
            functionNodes.add(createFunctionNode(function2Name));
        }
        ArrayList<Graph> allSubgraphsOfThisFunction = moveNodesToSubgraphList(parallelNodeClusters, callNodes.get(0), functionNodes.get(0));
        orderSubgraphs(allSubgraphsOfThisFunction);
        updateOutputNames(functionName, allSubgraphsOfThisFunction);
        removeNodesFromMainGraph(parallelNodeClusters);
        Graph functionGraph1 = foldSubgraphs(functionName, allSubgraphsOfThisFunction);
        functionGraphs.add(functionGraph1);
        updateLocalInfo();
        if (graph2Width != 0) {
            updateGraphFoldWidth(functionGraph1, graph1Width);
            Graph functionGraph2 = new MultiGraph("parallel_" + functionCounter++);
            Graphs.mergeIn(functionGraph2, functionGraph1);
            updateGraphFoldWidth(functionGraph2, graph2Width);
            functionGraphs.add(functionGraph2);

        }
        FoldInfo foldInfo = functionGraph1.getAttribute("foldInfo");
        splitVars(foldInfo);
        if (graph2Width != 0) {
            moveEdgesConnectingToCallNode(callNodes);
            moveEdgesLeavingFunctionNode(functionNodes);
        }
        for (int i = 0; i < functionGraphs.size(); i++) {
            Graph functionGraph = functionGraphs.get(i);
            setFunctionGraphCInfo(functionGraph);
            addCallEdges(functionGraph, functionNodes.get(i), callNodes.get(i));
        }




    }

    private void moveEdgesLeavingFunctionNode(List<Node> functionNodes) {
    }

    private void moveEdgesConnectingToCallNode(List<Node> callNodes) {
        Node callNode1 = callNodes.get(0);
        Node callNode2 = callNodes.get(1);
        List<Edge> edgesToMove = new ArrayList<>();
        for (Edge edge: callNode1.getEachEnteringEdge()) {
            String varName = edge.getAttribute("name");
            System.out.println("debug");
        }

        edgesToMove.forEach(edge -> mainGraph.removeEdge(edge));
    }

    private void updateGraphFoldWidth(Graph graph, Integer width) {
        FoldInfo foldInfo = graph.getAttribute("foldInfo");
        try {
            FoldInfo newFoldInfo = (FoldInfo) foldInfo.clone();
            newFoldInfo.setWidth(width);
            graph.setAttribute("foldInfo", newFoldInfo);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }

    private List<Integer> getVarNewSizes(int dim, int size, List<Integer> oldSizes) {
        List<Integer> newSizes = new ArrayList<>();
        for (int i = 0; i < oldSizes.size(); i++) {
            if (i==dim) {
                newSizes.add(size);
            } else {
                newSizes.add(oldSizes.get(i));
            }
        }
        return newSizes;
    }

    private List<Var> splitSingleVar(Var var, int numberOfParallelFunctions, int dimToFold) {
        List<Var> newVars = new ArrayList<>();
        List<List<Integer>> newVarsSizes = utils.splitDimensions(var, dimToFold, numberOfParallelFunctions, parallelNodeClusters.size());
        for (int i = 0; i < newVarsSizes.size(); i++) {
            List<Integer> newVarSizes = newVarsSizes.get(i);
            Var newVar = new Var(var.getType(), var.getName() + "_" + i, var.isArray(), newVarSizes);
            newVar.setHlsPartition(new HLSPartition(dimToFold, newVarSizes.get(dimToFold)));
            newVars.add(newVar);
        }
        return newVars;
    }


    private void splitVars(FoldInfo foldInfo) {
        int numberOfParallelFunctions = config.getParallelFunctions();
        // move input parameters that are folded into new inputs with sizes ready for dataflow
        List<Var> localInfo = mainInfo.getLocalInfo();
        List<Var> mainInputs = mainInfo.getInputs();
        List<Var> inputsToRemove = new ArrayList<>();
        List<Var> inputsToAdd = new ArrayList<>();
        for (Var var: mainInputs) {
            if (var.isArray()) {
                if (foldInfo.hasVar(var)) {
                    inputsToRemove.add(var);
                    inputsToAdd.addAll(splitSingleVar(var, numberOfParallelFunctions, foldInfo.getDimOfVar(var)));
                }
            }

        }
        mainInputs.removeAll(inputsToRemove);
        mainInputs.addAll(inputsToAdd);

        List<Var> localVarsToRemove = new ArrayList<>();
        List<Var> localVarsToAdd = new ArrayList<>();
        for (Var var: localInfo) {
            if (var.isArray()) {
                if (foldInfo.hasVar(var)) {
                    localVarsToRemove.add(var);
                    localVarsToAdd.addAll(splitSingleVar(var, numberOfParallelFunctions, foldInfo.getDimOfVar(var)));
                }
            }
        }
        localVarsToRemove.forEach( v -> localInfo.remove(v));
        localVarsToAdd.forEach(v -> localInfo.add(v));
        localVarsToRemove.forEach(var -> updateAccessesToVar(var, numberOfParallelFunctions, foldInfo ));
        inputsToRemove.forEach(var -> updateAccessesToVar(var, numberOfParallelFunctions, foldInfo));
    }

    private void updateAccessesToVar(Var varToUpdate, int numberOfParallelFunctions, FoldInfo foldInfo) {
        if (!varToUpdate.isSplit()) return;
        Var newVar = utils.splitDimensions(varToUpdate, numberOfParallelFunctions, foldInfo.getDimOfVar(varToUpdate));
        List<Integer> sizes = newVar.getSizes();
        for (Edge edge: mainGraph.getEachEdge()) {
            if (edge.hasAttribute("array")) {
                String edgeName = edge.getAttribute("name");
                if (edgeName.equals(varToUpdate.getName())) {
                    List<Integer> oldIndexes = utils.getIndexes(edge.getAttribute("label"));
                    int arraySuffix = 0;
                    String newLabelSuffix = new String();
                    for (int i = 0; i < oldIndexes.size(); i++) {
                        LinkedList<Integer> partitions = varToUpdate.getPartitionsAtDim(i);
                        int oldIndex = oldIndexes.get(i);
                        int maxSize = sizes.get(i);
                        int newIndex = oldIndex;
                        if (partitions.isEmpty()) {
                            if (oldIndex >= maxSize) {
                                arraySuffix = (int) Math.floor(oldIndex / maxSize);
                            }
                            newIndex = oldIndex % maxSize;
                        } else {
                            if (oldIndex < partitions.getFirst()) {
                                arraySuffix = 0;
                            } else {
                                if (partitions.size() == 1) {
                                    oldIndex = oldIndex - partitions.getFirst();
                                    if (oldIndex >= maxSize) {
                                        arraySuffix = (int) Math.floor(oldIndex / maxSize);
                                    }
                                    newIndex = oldIndex % maxSize;
                                } else {
                                    if (oldIndex >= partitions.getLast()) {
                                        newIndex = oldIndex - partitions.getLast();
                                        arraySuffix = numberOfParallelFunctions + 1;
                                    } else {
                                        oldIndex = oldIndex - partitions.getFirst();
                                        arraySuffix = (int) Math.floor(oldIndex / maxSize) + 1;
                                        newIndex = oldIndex % maxSize;
                                    }
                                }
                            }
                        }


                        newLabelSuffix = newLabelSuffix.concat("[" + newIndex + "]");
                    }
                    String newName = edgeName + "_" + arraySuffix;
                    String newLabel = newName + newLabelSuffix;
                    edge.setAttribute("name", newName);
                    edge.setAttribute("label", newLabel);
                }
            }
        }
    }

    private Node createCallNode(String functionName) {
        Node callNode = mainGraph.addNode("callNode_" + functionName);
        callNode.setAttribute("att1", "call");
        return callNode;
    }

    private Node createFunctionNode(String functionName) {
        Node functionNode = mainGraph.addNode(functionName);
        functionNode.addAttribute("att1", "function");
        functionNode.setAttribute("att2", functionName);
        return functionNode;
    }

    private void addCallEdges(Graph functionGraph, Node functionNode, Node callNode) {
        int numberParallelFunctions = config.getParallelFunctions();
        String functionName = functionNode.getId();

        CInfo functionInfo = functionGraph.getAttribute("info");
        List<Var> inputs = functionInfo.getInputs();
        List<Var> outputs = functionInfo.getOutputs();
        ArrayList<ArrayList<String>> params = new ArrayList<>();
        buildCallParams(params, inputs, outputs);
        for (int i = 0; i < numberParallelFunctions; i++) {
            Edge callEdge = mainGraph.addEdge("call_"+functionName+"_"+edgeCounter++, callNode, functionNode, true);
            callEdge.setAttribute("att1", "call");
            callEdge.setAttribute("att2", functionName);
            callEdge.setAttribute("att3", params.get(i));
            callEdge.setAttribute("label", callEdge.getId());
        }

    }

    private void addVarsToParams(int width, List<Var> vars, ArrayList<String> paramsForFunc, int callNumber) {
        for (int j = 0; j < width; j++) {
            String varName = vars.get(j).getName();
            Var mainVar = mainInfo.getVar(varName);
            if (vars.get(j).isSplit()) {
                varName += "_" + (callNumber + (mainVar.hasPartitions() ? 1: 0));
            }
            paramsForFunc.add(varName);
        }
    }

    private void buildCallParams(ArrayList<ArrayList<String>> calls, List<Var> inputs, List<Var> outputs) {
        int numberOfCalls = config.getParallelFunctions();
        for (int i=0; i < numberOfCalls; i++) {
            ArrayList<String> paramsForFunction = new ArrayList<>();
            // add input params
            addVarsToParams(inputs.size() - 1, inputs, paramsForFunction, i);
            // add output params
            addVarsToParams(outputs.size(), outputs, paramsForFunction, i);
            calls.add(paramsForFunction);
        }
    }

    private ArrayList<Integer> calculateExternalLoopWidths(Graph functionGraph) {
        ArrayList<Integer> widths = new ArrayList<>(config.getParallelFunctions());
        int size = ((FoldInfo) functionGraph.getAttribute("foldInfo")).getWidth();
        int numberParallelFunctions = config.getParallelFunctions();
        int currentOffset = 0;
        int width;
        for (int i=0; i < numberParallelFunctions; i++) {
            width = Math.min((int) Math.ceil((float) size/numberParallelFunctions), size - currentOffset);
            currentOffset += width;
            widths.add(width);
        }
        return widths;
    }

    private void updateOutputNames(String functionName, ArrayList<Graph> subgraphs) {
        // if subgraph has an output that is not an array, then we need to make sure that all outputs have the same label
        ArrayList<String> outputNames = new ArrayList<>();
        for (Graph subgraph: subgraphs) {
            Node endNode = subgraph.getNode("End");
            // I have to sort the edges here;
            for (int i = 0; i < endNode.getInDegree(); i++) {
                Edge edge = endNode.getEnteringEdge(i);
                if (!edge.hasAttribute("array")) {
                    if (outputNames.size() <= i) {
                        outputNames.add(edge.getAttribute("label"));
                    } else {
                        String oldLabel = edge.getAttribute("label");
                        Node sourceInMain = mainGraph.getNode(edge.getSourceNode().getId());
                        for (Edge edgeInMainGraph: sourceInMain.getEachLeavingEdge()) {
                            if (edgeInMainGraph.getAttribute("label").equals(oldLabel)) {
                                edgeInMainGraph.setAttribute("label", outputNames.get(i));
                                edgeInMainGraph.setAttribute("name", utils.varNameFromLabel(outputNames.get(i)));
                            }
                        }
                        edge.setAttribute("label", outputNames.get(i));
                        edge.setAttribute("name", utils.varNameFromLabel(outputNames.get(i)));
                    }

                }
            }
        }

        // if the output of the subgraphs is not an array, then transform it into an array
        for (Graph subgraph: subgraphs) {
            Node endNode = subgraph.getNode("End");
            for(int i = 0; i < endNode.getInDegree(); i++) {
                Edge edge = endNode.getEnteringEdge(i);
                if (!edge.hasAttribute("array")) {
                    String oldLabel = edge.getAttribute("label");
                    Node sourceInFunction = edge.getSourceNode();
                    String newLabel = transformScalarIntoArray(edge);
                    Node sourceInMain = mainGraph.getNode(sourceInFunction.getId());
                    for (Edge leavingSrcInMainEdge: sourceInMain.getEachLeavingEdge()) {
                        if (leavingSrcInMainEdge.getAttribute("label").equals(oldLabel)) {
                            Node targetInMain = leavingSrcInMainEdge.getTargetNode();
                            for (Edge enteringInTarget: targetInMain.getEachEnteringEdge()) {
                                if (enteringInTarget.getSourceNode().getId().equals(functionName)) {
                                    if (enteringInTarget.getAttribute("pos").equals(leavingSrcInMainEdge.getAttribute("pos"))) {
                                        enteringInTarget.setAttribute("label", newLabel);
                                        enteringInTarget.setAttribute("name", utils.varNameFromLabel(newLabel));
                                        enteringInTarget.setAttribute("array", true);
                                        break;
                                    }

                                }
                            }
                            break;
                        }
                    }
                }
            }
        }


    }


    private Graph foldSubgraphs(String functionName, ArrayList<Graph> subgraphList) {
        FoldingAlgorithm foldingAlgorithm = new FoldingAlgorithm(config, subgraphList);
        foldingAlgorithm.setFunctionName(functionName);
        foldingAlgorithm.init(mainGraph);
        foldingAlgorithm.compute();
        Graph functionGraph = foldingAlgorithm.getFunctionGraph();
        functionGraph.setAttribute("type", "parallel");
        return functionGraph;
    }

    private void setFunctionGraphCInfo(Graph functionGraph) {
        SetGraphCInfo infoAlgorithm = new SetGraphCInfo(mainInfo);
        infoAlgorithm.setNumberOfParallelCalls(config.getParallelFunctions());
        infoAlgorithm.init(functionGraph);
        infoAlgorithm.compute();
    }

    private void removeNodesFromMainGraph(List<HashSet<Node>> parallelSubgraphs) {
        parallelSubgraphs.forEach(nodes -> {
            nodes.forEach(node -> {
                mainGraph.removeNode(node);
            });
        });
    }

    void updateLocalInfo() {
        List<Var> localInfo = mainInfo.getLocalInfo();
        varCounter.forEach( (label,count) -> {
            List<Integer> indexes = new ArrayList<>();
            indexes.add(count);
            localInfo.add(new Var(varsToTypes.get(label), label + arraySuffixStr, true, indexes));
        });

    }

    private FoldInfo findVarsToFold(Graph subgraph) {
        HashMap<String, ArrayList<ArrayList<Integer>>> varNameToIndexes = new HashMap<>();
        HashMap<String, ArrayList<Float>> varNameToMeans = new HashMap<>();
        HashMap<String, ArrayList<Float>> varNameToStdDev = new HashMap<>();
        FoldInfo varsToFold = new FoldInfo();

        // Put the accesses to array indexes in a matrix per dimension
        for (Edge e: subgraph.getEachEdge()) {
            if (e.hasAttribute("array")) {
                String name = e.getAttribute("name");
                varNameToIndexes.putIfAbsent(name, new ArrayList<>());
                varNameToMeans.putIfAbsent(name, new ArrayList<>());
                varNameToStdDev.putIfAbsent(name, new ArrayList<>());
                ArrayList<Integer> indexes = utils.getIndexes(e.getAttribute("label"));
                for (int i = 0; i < indexes.size(); i++) {
                    ArrayList<ArrayList<Integer>> varToIndexesMatrix = varNameToIndexes.get(name);
                    if (varToIndexesMatrix.size() <= i) {
                        varToIndexesMatrix.add(new ArrayList<>());
                    }
                    varToIndexesMatrix.get(i).add(indexes.get(i));
                }
            }
        }

        // Compute the means of the array accesses per dimension
        for (Map.Entry<String, ArrayList<ArrayList<Integer>>> varToIndexesMatrix : varNameToIndexes.entrySet() ) {
            ArrayList<ArrayList<Integer>> matrix = varToIndexesMatrix.getValue();
            for (int dim = 0; dim < matrix.size(); dim++) {
                ArrayList<Integer> valuesInDimension = matrix.get(dim);
                float mean = 0;
                for (int i = 0; i < valuesInDimension.size(); i++) {
                    mean += valuesInDimension.get(i);
                }
                mean = mean / valuesInDimension.size();
                varNameToMeans.get(varToIndexesMatrix.getKey()).add(mean);
            }
        }

        // Computes the std deviations of the array accesses per dimension
        for (Map.Entry<String, ArrayList<ArrayList<Integer>>> varToIndexesMatrix : varNameToIndexes.entrySet() ) {

            String varName = varToIndexesMatrix.getKey();
            ArrayList<Float> meansPerDimension = varNameToMeans.get(varName);
            ArrayList<ArrayList<Integer>> matrix = varToIndexesMatrix.getValue();
            for (int dim = 0; dim < matrix.size(); dim++) {
                ArrayList<Integer> valuesInDimension = matrix.get(dim);
                float dimensionMean = meansPerDimension.get(dim);
                float stdDev = 0;
                for (int i = 0; i < valuesInDimension.size(); i++) {
                    stdDev += Math.abs(valuesInDimension.get(i) - dimensionMean);
                }
                stdDev = stdDev / valuesInDimension.size();
                varsToFold.addVar(varName, dim);
                varNameToStdDev.get(varName).add(stdDev);
            }
        }
        // return name of the var with the lowest std deviation
        return varsToFold;
    }

    void orderSubgraphs(ArrayList<Graph> subgraphs) {
        Graph subgraph0 = subgraphs.get(0);
        // order by input
        // find var which has a constant index, otherwise return any array
        FoldInfo foldInfo = findVarsToFold(subgraph0);
        // order the Edges that leave the Start node
        subgraphs.forEach(subgraph -> {
            Node start = subgraph.getNode("Start");
            List<Edge> edgesLeavingStart = StreamSupport.stream(start.getEachLeavingEdge().spliterator(), false).collect(Collectors.toList());
            EdgeComparator edgeComparator = new EdgeComparator();
            if (foldInfo.hasVarsToFold()) {
                edgeComparator.setVarToFold(foldInfo.getAnyVarToFold());
            }
            edgesLeavingStart.sort(edgeComparator);
            graphIdToEdgesLeavingStart.put(subgraph.getId(), edgesLeavingStart);
        });
        subgraphs.sort(new GraphInputComparator(graphIdToEdgesLeavingStart));
        subgraphs.get(0).setAttribute("foldInfo", foldInfo);
    }

    /**
     * If the output of all subgraphs is a single var that has the same name, then it should be replaced by a temporary
     * local array, so that the the subgraphs can be ordered and the folding can be completed.
     * @param commonSubgraphs
     */
    ArrayList<Graph> moveNodesToSubgraphList(List<HashSet<Node>> commonSubgraphs, Node callNode, Node functionNode) {
        ArrayList<Graph> subgraphList = new ArrayList<>();
        commonSubgraphs.forEach(nodesToAdd -> {
            MultiGraph subgraph = new MultiGraph("parallelGraph"+graphCounter++);
            AddStartAndEnd addStartAndEnd = new AddStartAndEnd();
            addStartAndEnd.init(subgraph);
            addStartAndEnd.compute();
            nodesToAdd.forEach(node -> {
                copyNodeAndEdges(node, subgraph, nodesToAdd, callNode, functionNode);
            });

            subgraphList.add(subgraph);
        });
        return subgraphList;
    }

    /**
     *
     * @param e
     * @return either 0 or 1
     */
    int getIndexOfGraphWhereEdgeBelongs(Edge e) {
        String label = e.getAttribute("label");
        List<Integer> indexes = utils.getIndexes(label);
        String varName = e.getAttribute("name");
        return 0;
    }


    void copyNodeAndEdges(Node node, Graph subgraph, HashSet<Node> nodesToAdd, Node callNode, Node functionNode) {
        Node n = subgraph.getNode(node.getId());
        if (n == null) {
            n = subgraph.addNode(node.getId());
            Graphs.copyAttributes(node, n);
        }

        Node finalN = n;
        node.getEachEnteringEdge().forEach(edge -> {
            Node source = edge.getSourceNode();
            Node newSource = subgraph.getNode(source.getId());
            if (nodesToAdd.contains(source) && newSource == null) {
                newSource = subgraph.addNode(source.getId());
                Graphs.copyAttributes(source, newSource);
            }

            if (newSource == null || utils.isStartNode(newSource)) {
                newSource = subgraph.getNode("Start");
                Edge edgeEnteringInCallNode = mainGraph.addEdge(edge.getSourceNode() + ";" + callNode.getId() + "_" + edgeCounter++,
                        edge.getSourceNode(), callNode, true);
                Graphs.copyAttributes(edge, edgeEnteringInCallNode);

            }
            Edge newEdge = subgraph.addEdge(edge.getId(), newSource, finalN, true);
            Graphs.copyAttributes(edge, newEdge);
        });
        node.getEachLeavingEdge().forEach(edge -> {
            Node target = edge.getTargetNode();
            if (!nodesToAdd.contains(target)) {
                Edge functionNodeToMainGraph = mainGraph.addEdge(functionNode.getId() + "_" + edgeCounter++ + "->" + target.getId(),
                        functionNode, target, true);
                Graphs.copyAttributes(edge, functionNodeToMainGraph);
                Node newTarget = subgraph.getNode("End");
                Edge newEdge = subgraph.addEdge(edge.getId(), finalN, newTarget, true);
                Graphs.copyAttributes(edge, newEdge);
            }
        });
    }

    private String transformScalarIntoArray(Edge edge) {
        String label = edge.getAttribute("label");
        if (label.startsWith("*")) {
            label = label.substring(1);
        }
        varsToTypes.putIfAbsent(label, edge.getAttribute("att3"));
        int count = varCounter.getOrDefault(label, 0);
        String newName = label + arraySuffixStr;
        String newLabel = newName + "[" + count + "]";
        edge.setAttribute("label", newLabel);
        edge.setAttribute("name", newName);
        varCounter.put(label, count + 1);
        edge.setAttribute("array", true);
        return newLabel;
    }

}

