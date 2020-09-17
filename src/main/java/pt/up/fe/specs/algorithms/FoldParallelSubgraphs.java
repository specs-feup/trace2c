package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
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

    private int graph1Width;
    private int graph2Width;

    Graph mainGraph;
    private CInfo mainInfo;
    private final HashMap<String, Integer> varCounter = new HashMap<>();
    private final HashMap<String, String> varsToTypes = new HashMap<>();
    private int edgeCounter = 0;
    private int graphCounter = 0;
    private final HashMap<String, List<Edge>> graphIdToEdgesLeavingStart = new HashMap<>();
    private static int functionCounter = 0;
    private List<HashSet<Node>> allNodeClusters;
    private List<List<Node>> function0NodeLists;
    private List<List<Node>> function1NodeLists;
    private int parallelCalls;
    private final HashMap<Node, String> nodeToOutputLabel = new HashMap<>();


    @Override
    public void init(Graph graph) {
        mainGraph = graph;
        mainInfo = graph.getAttribute("info");

        this.parallelCalls = Config.getParallelFunctions();
        this.allNodeClusters = graph.getAttribute("bestParallelClusters");

        this.graph1Width = allNodeClusters.size();
        this.graph2Width = 0;
        List<List<Node>> parallelNodeLists = getNodeListsFromSets(allNodeClusters);

        if (allNodeClusters.size() % Config.getParallelFunctions() != 0) {
            System.err.println("The number of parallel functions may produce invalid code.");
            System.err.println("This case has been handled, but further testing is required.");
            System.err.println("If so, consider updating to some number that divides " + allNodeClusters.size());
            graph1Width = (int) Math.ceil((float) allNodeClusters.size() / Config.getParallelFunctions()) * (Config.getParallelFunctions() - 1);
            graph2Width = allNodeClusters.size() - graph1Width;
            // I can't split the node clusters like this, because they are not ordered yet.

            orderParallelNodeLists(parallelNodeLists);
            this.function0NodeLists = parallelNodeLists.subList(0, graph1Width);
            this.function1NodeLists = parallelNodeLists.subList(graph1Width, allNodeClusters.size());
        } else {
            this.function0NodeLists = parallelNodeLists;
        }
    }

    @Override
    public void compute() {
        if (allNodeClusters.size() > 2) {
            if (graph2Width == 0) {
                computeFor1Function();
            } else {
                computeFor2Functions();
            }
            Algorithm leveling = new Leveling();
            leveling.init(mainGraph);
            leveling.compute();
        } else {
            System.out.println("Parallel node clusters are not repeatable enough to fold");
        }

    }


    private void computeFor1Function() {
        String functionName = "parallel_" + functionCounter++;
        Node callNode = createCallNode(functionName);
        Node functionNode = createFunctionNode(functionName);
        ArrayList<Graph> allSubgraphs = moveNodesToSubgraphList(function0NodeLists, callNode, functionNode);
        orderSubgraphs(allSubgraphs);
        updateInputNames(allSubgraphs);
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

    /*
    Order inputs of each subgraph.
    If an input is a scalar variable, transforms it into an array
     */
    private void updateInputNames(ArrayList<Graph> allSubgraphs) {
        List<List<Edge>> inputs = allSubgraphs.stream().map(subgraph ->
                StreamSupport.stream(Utils.getStartNode(subgraph).getEachLeavingEdge().spliterator(), false)
                        .collect(Collectors.toList())).collect(Collectors.toList());
        for (List<Edge> inputsOfSubgraph : inputs) {
            inputsOfSubgraph.sort(new EdgeComparator());
        }
        List<Edge> inputsOfSubgraph0 = inputs.get(0);
        //List<Edge> inputsOfSubgraph1 = inputs.get(1);
        for (int i = 0; i < inputsOfSubgraph0.size(); i++) {
            Edge edgeOfSubgraph0 = inputsOfSubgraph0.get(i);
            //Edge edgeOfSubgraph1 = inputsOfSubgraph1.get(i);
            if (!Utils.isArray(edgeOfSubgraph0) && Utils.isVar(edgeOfSubgraph0)) {
                //if (!Utils.getName(edgeOfSubgraph0).equals(Utils.getName(edgeOfSubgraph1))) {
                // give the edges all the same name and transform them into arrays
                Utils.setLabel(edgeOfSubgraph0, Utils.getLabel(edgeOfSubgraph0) + "_in");
                String varLabel = Utils.getLabel(edgeOfSubgraph0);
                for (List<Edge> subgraphEdges : inputs) {
                    Edge edgeAtIndex = subgraphEdges.get(i);
                    Utils.setLabel(edgeAtIndex, varLabel);
                    Node mainGraphSourceNode = mainGraph.getEdge(edgeAtIndex.getId()).getSourceNode();
                    transformScalarIntoArray(edgeAtIndex);
                    for (Edge leavingMain : mainGraphSourceNode.getEachLeavingEdge()) {
                        Graphs.copyAttributes(edgeAtIndex, leavingMain);
                    }
                }
                //}
            }
        }


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
        updateInputNames(function0Subgraphs);
        updateInputNames(function1Subgraphs);
        updateOutputNames(function0Name, function0Subgraphs);
        updateOutputNames(function1Name, function1Subgraphs);
        updateLocalInfo();
        removeNodesFromMainGraph(allNodeClusters);
        setVarsPartitions(function0Subgraphs.get(0), function1Subgraphs.get(function1Subgraphs.size() - 1));
        Graph foldedGraph0 = foldSubgraphs(function0Name, function0Subgraphs);
        Graph foldedGraph1 = foldSubgraphs(function1Name, function1Subgraphs);
        updateGraphFoldWidth(foldedGraph0, graph1Width / (parallelCalls - 1), false);
        setFunctionGraphCInfo(foldedGraph0);
        setFunctionGraphCInfo(foldedGraph1);
        addCallEdges(foldedGraph0, functionNode0, callNode0, (parallelCalls - 1), 0);
        addCallEdges(foldedGraph1, functionNode1, callNode1, 1, parallelCalls - 1);
        splitVars(foldedGraph0.getAttribute("foldInfo"));
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

    private boolean isVarUsedInSubgraph(Var var, Graph graph) {
        for (Edge edge : graph.getEachEdge()) {
            if (Utils.getName(edge).equals(var.getName())) return true;
        }
        return false;
    }


    private void setVarsPartitions(Graph firstSubgraph, Graph lastSubgraph) {
        List<Var> inputs = mainInfo.getInputs();
        for (Var input : inputs) {
            if (input.isArray() && isVarUsedInSubgraph(input, firstSubgraph)) {
                // find the first and last indexes that are used inside the folded graph
                List<Integer> sizes = input.getSizes();
                int dimToPartition = Config.getDimToPartition(input.getName());
                Integer dimMaxSize = sizes.get(dimToPartition);

                int minIndex = getVarMinUsedIndex(firstSubgraph, input.getName(), dimToPartition);
                int maxIndex = getVarMaxUsedIndex(lastSubgraph, input.getName(), dimToPartition);
                if (minIndex != 0) {
                    input.addPartition(minIndex, dimToPartition);
                }
                if (maxIndex != dimMaxSize - 1) {
                    input.addPartition(maxIndex + 1, dimToPartition);
                }
            }
        }
    }

    private int getVarMinUsedIndex(Graph firstSubgraph, String varName, int dim) {
        int minIndex = Integer.MAX_VALUE;
        for (Edge edge : firstSubgraph.getEachEdge()) {
            if (Utils.hasName(edge) && Utils.getName(edge).equals(varName)) {
                String label = Utils.getLabel(edge);
                List<Integer> indexes = Utils.getIndexes(label);
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
        for (Edge edge : lastSubgraph.getEachEdge()) {
            if (Utils.hasName(edge) && Utils.getName(edge).equals(var)) {
                String label = Utils.getLabel(edge);
                List<Integer> indexes = Utils.getIndexes(label);
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
        List<List<Integer>> newVarsSizes = Utils.splitDimensions(var, dimToFold, numberOfParallelFunctions, allNodeClusters.size());

        if (var.hasHlsPartition()) {
            HLSPartition oldHLSPartition = var.getHlsPartition();
            var.setHlsPartition(new HLSPartition(oldHLSPartition.getDim(), oldHLSPartition.getFactor() / numberOfParallelFunctions));
        }

        for (int i = 0; i < newVarsSizes.size(); i++) {
            List<Integer> newVarSizes = newVarsSizes.get(i);
            Var newVar = new Var(var.getType(), var.getName() + "_" + i, var.isArray(), newVarSizes);
            newVar.setHlsPartition(var.getHlsPartition());
            newVars.add(newVar);
        }
        return newVars;
    }


    /**
     * It will split the arrays in the main graph
     *
     * @param foldInfo foldInfo
     */
    private void splitVars(FoldInfo foldInfo) {

        int numberOfParallelFunctions = Config.getParallelFunctions();
        // move input parameters that are folded into new inputs with sizes ready for dataflow
        List<Var> localInfo = mainInfo.getLocalInfo();
        List<Var> mainInputs = mainInfo.getInputs();
        HashMap<Var, List<Var>> inputsToRemove = new HashMap<>();
        HashMap<Var, List<Var>> localVarsToRemove = new HashMap<>();
        splitVarsInList(foldInfo, numberOfParallelFunctions, mainInputs, inputsToRemove);
        splitVarsInList(foldInfo, numberOfParallelFunctions, localInfo, localVarsToRemove);


        inputsToRemove.forEach((varToRemove, newVars) -> {
            mainInputs.remove(varToRemove);
            mainInputs.addAll(newVars);
        });
        localVarsToRemove.forEach((varToRemove, newVars) -> {
            localInfo.remove(varToRemove);
            localInfo.addAll(newVars);
        });
        inputsToRemove.forEach(this::updateAccessesToVar);
        localVarsToRemove.forEach(this::updateAccessesToVar);

    }

    private void splitVarsInList(FoldInfo foldInfo, int numberOfParallelFunctions, List<Var> varsList, HashMap<Var, List<Var>> varsToRemove) {
        for (Var var : varsList) {
            if (var.isArray()) {
                if (foldInfo.hasVar(var)) {
                    if (foldInfo.hasVarHLSPartitionDim(var.getName())) {
                        var.setHLSPartitionFromSize(foldInfo.getVarHLSPartitionDimension(var.getName()));
                    }
                    List<Var> newVars = splitSingleVar(var, numberOfParallelFunctions, foldInfo.getDimOfVar(var));
                    varsToRemove.put(var, newVars);
                } else if (foldInfo.hasVarHLSPartitionDim(var.getName())) {
                    int dimToPartition = foldInfo.getVarHLSPartitionDimension(var.getName());
                    var.setHlsPartition(new HLSPartition(dimToPartition, var.getSizes().get(dimToPartition)));
                }
            }

        }
    }

    private void updateAccessesToVar(Var varToUpdate, List<Var> newVars) {
        if (!varToUpdate.isSplit()) return;

        for (Edge edge : mainGraph.getEachEdge()) {
            if (Utils.isArray(edge)) {
                String edgeName = Utils.getName(edge);
                if (edgeName.equals(varToUpdate.getName())) {
                    List<Integer> oldIndexes = Utils.getIndexes(Utils.getLabel(edge));
                    int arraySuffix = 0;
                    String newLabelSuffix = "";
                    for (int i = 0; i < oldIndexes.size(); i++) {
                        int oldIndex = oldIndexes.get(i);
                        int indexAcc = 0;
                        while (oldIndex >= indexAcc + newVars.get(arraySuffix).getSizes().get(i)) {
                            indexAcc += newVars.get(arraySuffix).getSizes().get(i);
                            arraySuffix++;
                        }
                        int newIndex = indexAcc > 0 ? oldIndex - indexAcc : oldIndex;

                        newLabelSuffix = newLabelSuffix.concat("[" + newIndex + "]");
                    }
                    String newName = edgeName + "_" + arraySuffix;
                    String newLabel = newName + newLabelSuffix;
                    Utils.setName(edge, newName);
                    Utils.setLabel(edge, newLabel);
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
            Edge callEdge = mainGraph.addEdge("call_" + functionName + "_" + edgeCounter++, callNode, functionNode, true);
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
                varName += "_" + (callNumber + (mainVar.hasPartitions() ? 1 : 0));
            }
            paramsForFunc.add(varName);
        }
    }

    private void buildCallParams(ArrayList<ArrayList<String>> calls, List<Var> inputs, List<Var> outputs, FoldInfo foldInfo, int numberOfCalls, int callsOffset) {
        for (int i = callsOffset; i < callsOffset + numberOfCalls; i++) {
            ArrayList<String> paramsForFunction = new ArrayList<>();
            // add input params
            addVarsToParams(inputs.size(), inputs, paramsForFunction, i, foldInfo);
            // add output params
            addVarsToParams(outputs.size(), outputs, paramsForFunction, i, foldInfo);
            calls.add(paramsForFunction);
        }
    }

    private void updateOutputNames(String functionName, List<Graph> subgraphs) {
        // if subgraph has an output that is not an array, then we need to make sure that all outputs have the same label
        ArrayList<String> outputNames = new ArrayList<>();
        for (Graph subgraph : subgraphs) {
            Node endNode = Utils.getEndNode(subgraph);

            for (int i = 0; i < endNode.getInDegree(); i++) {
                Edge edge = endNode.getEnteringEdge(i);
                if (!Utils.isArray(edge)) {
                    if (outputNames.size() <= i) {
                        outputNames.add(Utils.getLabel(edge));
                    } else {
                        String oldLabel = Utils.getLabel(edge);
                        Node sourceInMain = mainGraph.getNode(edge.getSourceNode().getId());
                        for (Edge edgeInMainGraph : sourceInMain.getEachLeavingEdge()) {
                            if (Utils.getLabel(edgeInMainGraph).equals(oldLabel)) {
                                Utils.setLabel(edgeInMainGraph, outputNames.get(i));
                                Utils.setNameFromLabel(edgeInMainGraph);
                            }
                        }
                        Utils.setLabel(edge, outputNames.get(i));
                        Utils.setNameFromLabel(edge);
                    }

                }
            }
        }

        // if the output of the subgraphs is not an array, then transform it into an array
        // edges that come from the same node should have the same label
        for (Graph subgraph : subgraphs) {
            Node endNode = Utils.getEndNode(subgraph);
            for (Edge edgeInSubgraph : endNode.getEachEnteringEdge()) {
                if (!Utils.isArray(edgeInSubgraph)) {
                    String newLabel;
                    Node sourceInSubgraph = edgeInSubgraph.getSourceNode();
                    if (nodeToOutputLabel.containsKey(sourceInSubgraph)) {
                        newLabel = nodeToOutputLabel.get(sourceInSubgraph);
                        setArrayAttributes(edgeInSubgraph, newLabel);
                    } else {
                        newLabel = transformScalarIntoArray(edgeInSubgraph);
                        nodeToOutputLabel.put(sourceInSubgraph, newLabel);
                    }

                    Edge edgeInMain = mainGraph.getEdge(edgeInSubgraph.getId());
                    for (Edge enteringInTarget : edgeInMain.getTargetNode().getEachEnteringEdge()) {
                        if (enteringInTarget.getSourceNode().getId().equals(functionName)) {
                            if (Utils.getPos(enteringInTarget).equals(Utils.getPos(edgeInMain))) {
                                Utils.setLabel(enteringInTarget, newLabel);
                                Utils.setNameFromLabel(enteringInTarget);
                                Utils.setArray(enteringInTarget);
                                break;
                            }
                        }
                    }
                }
            }
        }


    }


    private Graph foldSubgraphs(String functionName, List<Graph> subgraphList) {
        FoldingAlgorithm foldingAlgorithm = new FoldingAlgorithm(subgraphList);
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
        parallelSubgraphs.forEach(nodes -> nodes.forEach(node -> mainGraph.removeNode(node)));
    }

    /**
     * When the inputs and outputs of the subgraphs were transformed from scalar variables to arrays,
     * we stored the size of each new array in the varCounter map.
     * Thus, updateLocalInfo will add these new arrays to the local variables of the mainGraph.
     */
    void updateLocalInfo() {
        List<Var> localInfo = mainInfo.getLocalInfo();
        varCounter.forEach((label, count) -> {
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
        for (Edge e : subgraph.getEachEdge()) {
            if (Utils.isArray(e)) {
                String name = Utils.getName(e);
                varNameToIndexes.putIfAbsent(name, new ArrayList<>());
                varNameToMeans.putIfAbsent(name, new ArrayList<>());
                varNameToStdDev.putIfAbsent(name, new ArrayList<>());
                ArrayList<Integer> indexes = Utils.getIndexes(Utils.getLabel(e));
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
        varNameToIndexes.forEach((key, matrix) -> {
            for (ArrayList<Integer> valuesInDimension : matrix) {
                float mean = 0;
                for (Integer integer : valuesInDimension) {
                    mean += integer;
                }
                mean = mean / valuesInDimension.size();
                varNameToMeans.get(key).add(mean);
            }
        });

        // Computes the std deviations of the array accesses per dimension
        for (Map.Entry<String, ArrayList<ArrayList<Integer>>> varToIndexesMatrix : varNameToIndexes.entrySet()) {

            String varName = varToIndexesMatrix.getKey();
            ArrayList<Float> meansPerDimension = varNameToMeans.get(varName);
            ArrayList<ArrayList<Integer>> matrix = varToIndexesMatrix.getValue();
            for (int dim = 0; dim < matrix.size(); dim++) {
                ArrayList<Integer> valuesInDimension = matrix.get(dim);
                float dimensionMean = meansPerDimension.get(dim);
                float stdDev = 0;
                for (Integer integer : valuesInDimension) {
                    stdDev += Math.abs(integer - dimensionMean);
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
        //Graph subgraph0 = subgraphs.get(0);
        // order by input
        //FoldInfo foldInfo = findVarsToFoldHeuristically(subgraph0);
        FoldInfo foldInfo = computeFoldInfoFromConfig();
        // order the Edges that leave the Start node
        subgraphs.forEach(subgraph -> {
            Node start = Utils.getStartNode(subgraph);
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
        Config.getVarsToPartition().forEach(varAndDim -> foldInfo.addVar(varAndDim.getVar(), varAndDim.getDim()));
        return foldInfo;
    }

    /**
     * If the output of all subgraphs is a single var that has the same name, then it should be replaced by a temporary
     * local array, so that the the subgraphs can be ordered and the folding can be completed.
     *
     * @param commonSubgraphs list of clusters of nodes
     * @param callNode        Node used to call the function node
     * @param functionNode    function node that represents the new subgraph
     */
    ArrayList<Graph> moveNodesToSubgraphList(List<List<Node>> commonSubgraphs, Node callNode, Node functionNode) {
        ArrayList<Graph> subgraphList = new ArrayList<>();
        commonSubgraphs.forEach(nodesToAdd -> {
            MultiGraph subgraph = new MultiGraph("parallelGraph" + graphCounter++);
            AddStartAndEnd addStartAndEnd = new AddStartAndEnd();
            addStartAndEnd.init(subgraph);
            addStartAndEnd.compute();
            nodesToAdd.forEach(node -> copyNodeAndEdges(node, subgraph, nodesToAdd, callNode, functionNode));

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

            if (newSource == null || Utils.isStartNode(newSource)) {
                newSource = Utils.getStartNode(subgraph);
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
                Node newTarget = Utils.getEndNode(subgraph);
                Edge newEdge = subgraph.addEdge(edge.getId(), finalN, newTarget, true);
                Graphs.copyAttributes(edge, newEdge);
            }
        });
    }

    private String transformScalarIntoArray(Edge edge) {
        String label = Utils.getLabel(edge);
        if (label.startsWith("*")) {
            label = label.substring(1);
        }
        varsToTypes.putIfAbsent(label, Utils.getVarType(edge));
        int count = varCounter.getOrDefault(label, 0);
        String newName = label + arraySuffixStr;
        String newLabel = newName + "[" + count + "]";
        varCounter.put(label, count + 1);
        setArrayAttributes(edge, newName, newLabel);
        return newLabel;
    }

    private void setArrayAttributes(Element element, String newName, String newLabel) {
        Utils.setLabel(element, newLabel);
        Utils.setName(element, newName);
        Utils.setArray(element);
    }

    private void setArrayAttributes(Element element, String newLabel) {
        String newName = Utils.varNameFromLabel(newLabel);
        setArrayAttributes(element, newName, newLabel);
    }

}

