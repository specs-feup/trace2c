package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.MultiGraph;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.Config;
import pt.up.fe.specs.utils.Var;
import pt.up.fe.specs.utils.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Receives a list of parallel node clusters and folds it into functions
 * that are called in parallel.
 */
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
    private List<HashSet<Node>> allNodeClusters;
    private List<List<Node>> function0NodeLists;
    private List<List<Node>> function1NodeLists;
    private int parallelCalls = 1;


    public FoldParallelSubgraphs(List<HashSet<Node>> parallelNodeSets, Config config) {
        this.config = config;
        this.parallelCalls = config.getParallelFunctions();
        this.allNodeClusters = parallelNodeSets;

        this.graph1Width = parallelNodeSets.size();
        this.graph2Width = 0;
        List<List<Node>> parallelNodeLists = getNodeListsFromSets(parallelNodeSets);

        if (parallelNodeSets.size() % config.getParallelFunctions() != 0) {
            System.err.println("The number of parallel functions may produce invalid code.");
            System.err.println("This case has been handled, but further testing is required.");
            System.err.println("If so, consider updating to some number that divides " + parallelNodeSets.size());
            graph1Width = (int) Math.ceil((float) parallelNodeSets.size() / config.getParallelFunctions()) * (config.getParallelFunctions() - 1);
            graph2Width = parallelNodeSets.size() - graph1Width;
            // I can't split the node clusters like this, because they are not ordered yet.

            orderParallelNodeLists(parallelNodeLists);
            this.function0NodeLists = parallelNodeLists.subList(0, graph1Width);
            this.function1NodeLists = parallelNodeLists.subList(graph1Width, parallelNodeSets.size());
        } else {
            this.function0NodeLists = parallelNodeLists;
        }
    }

    private List<List<Node>> getNodeListsFromSets(List<HashSet<Node>> parallelNodeSets) {
        List<List<Node>> parallelNodesList = new ArrayList<>();
        for (HashSet<Node> nodeSet : parallelNodeSets) {
            parallelNodesList.add(new ArrayList<>(nodeSet));
        }
        return parallelNodesList;
    }

    private void orderParallelNodeLists(List<List<Node>> parallelNodeLists) {
        parallelNodeLists.sort(new NodeListComparator());
    }

    @Override
    public void init(Graph graph) {
        mainGraph = graph;
        mainInfo = graph.getAttribute("info");
    }

    @Override
    public void compute() {
        if (graph2Width == 0) {
            computeFor1Function();
        } else {
            computeFor2Functions();
        }
    }


    private void computeFor1Function() {
        String functionName = "parallel_" + functionCounter++;
        Node callNode = createCallNode(functionName);
        Node functionNode = createFunctionNode(functionName);
        ArrayList<Graph> allSubgraphs = moveNodesToSubgraphList(function0NodeLists, callNode, functionNode);
        orderSubgraphs(allSubgraphs);
        updateOutputNames(functionName, allSubgraphs);
        updateLocalInfo();
        removeNodesFromMainGraph(allNodeClusters);
        setVarsPartitions(allSubgraphs.get(0), allSubgraphs.get(allSubgraphs.size() - 1));
        Graph foldedGraph = foldSubgraphs(functionName, allSubgraphs);
        updateGraphFoldWidth(foldedGraph, graph1Width / parallelCalls, false);
        setFunctionGraphCInfo(foldedGraph);
        addCallEdges(foldedGraph, functionNode, callNode, parallelCalls, 0);
        splitVars(foldedGraph.getAttribute("foldInfo"));
    }

    /**
     * Folds the parallel clusters into 2 functions.
     * This should be used when the number of parallel calls does not divide the number of parallel clusters.
     * The 2nd function will be exactly the same as the first one, except for the width of the loop,
     * because it will fold a smaller number of parallel node clusters.
     */
    private void computeFor2Functions() {
        String function0Name = "parallel_" + functionCounter++;
        String function1Name = "parallel_" + functionCounter++;
        Node callNode0 = createCallNode(function0Name);
        Node functionNode0 = createFunctionNode(function0Name);
        Node callNode1 = createCallNode(function1Name);
        Node functionNode1 = createFunctionNode(function1Name);
        ArrayList<Graph> function0Subgraphs = moveNodesToSubgraphList(function0NodeLists, callNode0, functionNode0);
        ArrayList<Graph> function1Subgraphs = moveNodesToSubgraphList(function1NodeLists, callNode1, functionNode1);
        orderSubgraphs(function0Subgraphs);
        orderSubgraphs(function1Subgraphs);
        updateOutputNames(function0Name, function0Subgraphs);
        updateOutputNames(function1Name, function1Subgraphs);
        updateLocalInfo();
        removeNodesFromMainGraph(allNodeClusters);
        setVarsPartitions(function0Subgraphs.get(0), function1Subgraphs.get(function1Subgraphs.size() - 1));
        Graph foldedGraph0 = foldSubgraphs(function0Name, function0Subgraphs);
        Graph foldedGraph1 = foldSubgraphs(function1Name, function1Subgraphs);
        updateGraphFoldWidth(foldedGraph0, graph1Width / (parallelCalls-1), false);
        setFunctionGraphCInfo(foldedGraph0);
        setFunctionGraphCInfo(foldedGraph1);
        addCallEdges(foldedGraph0, functionNode0, callNode0, (parallelCalls-1), 0);
        addCallEdges(foldedGraph1, functionNode1, callNode1, 1, parallelCalls - 1);
        splitVars(foldedGraph0.getAttribute("foldInfo"));
    }


    private void setVarsPartitions(Graph firstSubgraph, Graph lastSubgraph) {
        List<Var> inputs = mainInfo.getInputs();
        for (Var input: inputs) {
            if (input.isArray()) {
                // find the first and last indexes that are used inside the folded graph
                List<Integer> sizes = input.getSizes();
                Integer dimToPartition = config.getDimToPartition(input.getName());
                Integer dimMaxSize = sizes.get(dimToPartition);

                int minIndex = getVarMinUsedIndex(firstSubgraph, input.getName(), dimToPartition);
                int maxIndexInFirstSubgraph = getVarMaxUsedIndex(firstSubgraph, input.getName(), dimToPartition);
                int maxIndex = getVarMaxUsedIndex(lastSubgraph, input.getName(), dimToPartition);
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



    private void updateGraphFoldWidth(Graph graph, Integer width, boolean isToClone) {
        FoldInfo foldInfo = graph.getAttribute("foldInfo");
        if (isToClone) {
            try {
                FoldInfo newFoldInfo = (FoldInfo) foldInfo.clone();
                newFoldInfo.setWidth(width);
                graph.setAttribute("foldInfo", newFoldInfo);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        } else {
            foldInfo.setWidth(width);
        }


    }


    private List<Var> splitSingleVar(Var var, int numberOfParallelFunctions, int dimToFold) {
        List<Var> newVars = new ArrayList<>();
        HLSPartition varHLSPartition = var.getHlsPartition();
        List<List<Integer>> newVarsSizes = utils.splitDimensions(var, dimToFold, numberOfParallelFunctions, allNodeClusters.size());
        for (int i = 0; i < newVarsSizes.size(); i++) {
            List<Integer> newVarSizes = newVarsSizes.get(i);
            Var newVar = new Var(var.getType(), var.getName() + "_" + i, var.isArray(), newVarSizes);
            if (var.hasHlsPartition()) {
                newVar.setHlsPartition(new HLSPartition(dimToFold, Math.min(newVarSizes.get(dimToFold), varHLSPartition.getFactor())));
            } else {
                newVar.setHlsPartition(new HLSPartition(dimToFold, 2));
            }

            newVars.add(newVar);
        }
        return newVars;
    }


    /**
     * It will split the arrays in the main graph
     * Check what happens with sum_array
     * @param foldInfo
     */
    private void splitVars(FoldInfo foldInfo) {

        int numberOfParallelFunctions = config.getParallelFunctions();
        // move input parameters that are folded into new inputs with sizes ready for dataflow
        List<Var> localInfo = mainInfo.getLocalInfo();
        List<Var> mainInputs = mainInfo.getInputs();
        HashMap<Var, List<Var>> inputsToRemove = new HashMap<>();
        for (Var var: mainInputs) {
            if (var.isArray()) {
                if (foldInfo.hasVar(var)) {
                    List<Var> newVars = splitSingleVar(var, numberOfParallelFunctions, foldInfo.getDimOfVar(var));
                    inputsToRemove.put(var, newVars);
                }
            }

        }

        HashMap<Var, List<Var>> localVarsToRemove = new HashMap<>();

        for (Var var: localInfo) {
            if (var.isArray()) {
                if (foldInfo.hasVar(var)) {
                    List<Var> newVars = splitSingleVar(var, numberOfParallelFunctions, foldInfo.getDimOfVar(var));
                    localVarsToRemove.put(var, newVars);
                }
            }
        }
        inputsToRemove.forEach((varToRemove, newVars) -> {mainInputs.remove(varToRemove); mainInputs.addAll(newVars);});
        localVarsToRemove.forEach((varToRemove, newVars) -> { localInfo.remove(varToRemove); localInfo.addAll(newVars);});
        inputsToRemove.forEach((oldVar, newVars) -> updateAccessesToVar(oldVar, newVars));
        localVarsToRemove.forEach( (oldVar, newVars) -> updateAccessesToVar(oldVar, newVars));

    }

    private void updateAccessesToVar(Var varToUpdate, List<Var> newVars) {
        if (!varToUpdate.isSplit()) return;

        for (Edge edge: mainGraph.getEachEdge()) {
            if (edge.hasAttribute("array")) {
                String edgeName = edge.getAttribute("name");
                if (edgeName.equals(varToUpdate.getName())) {
                    List<Integer> oldIndexes = utils.getIndexes(edge.getAttribute("label"));
                    int arraySuffix = 0;
                    String newLabelSuffix = new String();
                    for (int i = 0; i < oldIndexes.size(); i++) {
                        int oldIndex = oldIndexes.get(i);
                        int indexAcc = 0;
                        while (oldIndex >= indexAcc + newVars.get(arraySuffix).getSizes().get(i)) {
                            indexAcc += newVars.get(arraySuffix).getSizes().get(i);
                            arraySuffix++;
                        }
                        int newIndex = indexAcc > 0 ? oldIndex % indexAcc : oldIndex;

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

    private void addCallEdges(Graph functionGraph, Node functionNode, Node callNode, int numberOfCalls, int callsOffset) {
        FoldInfo foldInfo = functionGraph.getAttribute("foldInfo");
        String functionName = functionNode.getId();

        CInfo functionInfo = functionGraph.getAttribute("info");
        List<Var> inputs = functionInfo.getInputs();
        List<Var> outputs = functionInfo.getOutputs();
        ArrayList<ArrayList<String>> params = new ArrayList<>();
        buildCallParams(params, inputs, outputs, foldInfo, numberOfCalls, callsOffset);
        for (int i = 0; i < numberOfCalls; i++) {
            Edge callEdge = mainGraph.addEdge("call_"+functionName+"_"+edgeCounter++, callNode, functionNode, true);
            callEdge.setAttribute("att1", "call");
            callEdge.setAttribute("att2", functionName);
            callEdge.setAttribute("att3", params.get(i));
            callEdge.setAttribute("label", callEdge.getId());
        }

    }

    private void addVarsToParams(int width, List<Var> vars, ArrayList<String> paramsForFunc, int callNumber, FoldInfo foldInfo) {
        for (int j = 0; j < width; j++) {
            String varName = vars.get(j).getName();
            Var mainVar = mainInfo.getVar(varName);
            if (foldInfo.hasVar(varName)) {
                varName += "_" + (callNumber + (mainVar.hasPartitions() ? 1: 0));
            }
            paramsForFunc.add(varName);
        }
    }

    private void buildCallParams(ArrayList<ArrayList<String>> calls, List<Var> inputs, List<Var> outputs, FoldInfo foldInfo, int numberOfCalls, int callsOffset) {
        for (int i=callsOffset; i < callsOffset + numberOfCalls; i++) {
            ArrayList<String> paramsForFunction = new ArrayList<>();
            // add input params
            addVarsToParams(inputs.size(), inputs, paramsForFunction, i, foldInfo);
            // add output params
            addVarsToParams(outputs.size(), outputs, paramsForFunction, i, foldInfo);
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

    private void updateOutputNames(String functionName, List<Graph> subgraphs) {
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


    private Graph foldSubgraphs(String functionName, List<Graph> subgraphList) {
        FoldingAlgorithm foldingAlgorithm = new FoldingAlgorithm(config, subgraphList);
        foldingAlgorithm.setFunctionName(functionName);
        foldingAlgorithm.init(mainGraph);
        foldingAlgorithm.compute();
        Graph functionGraph = foldingAlgorithm.getFunctionGraph();
        functionGraph.setAttribute("type", "parallel");
        return functionGraph;
    }

    private void setFunctionGraphCInfo(Graph functionGraph) {
        SetGraphCInfo infoAlgorithm = new SetGraphCInfo();
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

    /**
     * When the outputs of the subgraphs were transformed from scalar variables to arrays,
     * we stored the size of each new array in the varCounter map.
     * Thus, updateLocalInfo will add these new arrays to the local variables of the mainGraph.
     */
    void updateLocalInfo() {
        List<Var> localInfo = mainInfo.getLocalInfo();
        varCounter.forEach( (label,count) -> {
            List<Integer> indexes = new ArrayList<>();
            indexes.add(count);
            localInfo.add(new Var(varsToTypes.get(label), label + arraySuffixStr, true, indexes));
        });

    }

    private FoldInfo findVarsToFoldHeuristically(Graph subgraph) {
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

    void orderSubgraphs(List<Graph> subgraphs) {
        Graph subgraph0 = subgraphs.get(0);
        // order by input
        //FoldInfo foldInfo = findVarsToFoldHeuristically(subgraph0);
        FoldInfo foldInfo = computeFoldInfoFromConfig();
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

    private FoldInfo computeFoldInfoFromConfig() {
        FoldInfo foldInfo = new FoldInfo();
        config.getVarsToPartition().forEach(varAndDim -> foldInfo.addVar(varAndDim.getVar(), varAndDim.getDim()));
        return foldInfo;
    }

    /**
     * If the output of all subgraphs is a single var that has the same name, then it should be replaced by a temporary
     * local array, so that the the subgraphs can be ordered and the folding can be completed.
     * @param commonSubgraphs
     */
    ArrayList<Graph> moveNodesToSubgraphList(List<List<Node>> commonSubgraphs, Node callNode, Node functionNode) {
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



    void copyNodeAndEdges(Node node, Graph subgraph, List<Node> nodesToAdd, Node callNode, Node functionNode) {
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

