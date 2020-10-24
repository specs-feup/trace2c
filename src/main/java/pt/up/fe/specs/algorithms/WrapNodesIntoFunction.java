package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.MultiGraph;
import pt.up.fe.specs.utils.EdgeComparator;
import pt.up.fe.specs.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class WrapNodesIntoFunction implements Algorithm {

    private final boolean isToFold;
    Graph mainGraph;
    Graph functionGraph;
    HashSet<Node> nodesToMove;
    String functionName;
    Node callNode;
    Node functionNode;
    private static Integer edgeCounter = 0;
    private final HashMap<String, Integer> varCounter = new HashMap<>();
    private final HashMap<String, String> varsToTypes = new HashMap<>();
    private final String arraySuffix = "_array";
    private boolean isToUpdateOutputs = true;

    public WrapNodesIntoFunction(HashSet<Node> nodesToAdd, String functionName, boolean isToFold) {
        this.nodesToMove = nodesToAdd;
        this.functionName = functionName;
        this.isToFold = isToFold;
    }

    @Override
    public void init(Graph graph) {
        this.mainGraph = graph;
    }

    @Override
    public void compute() {
        createCallNode();
        createFunctionNode();
        createFunctionGraph();
        moveNodesToFunctionGraph(nodesToMove);
        if (isToUpdateOutputs) {
            updateOutputNames();
        }

        removeNodesFromMainGraph();
        setFunctionGraphCInfo();
        addFunctionToSubgraphList();
        Leveling leveling = new Leveling();
        leveling.init(functionGraph);
        leveling.compute();

        if (isToFold) {
            /*
            AllSubgraphsAlgorithm allSubgraphsAlgorithm = new AllSubgraphsAlgorithm();
            allSubgraphsAlgorithm.init(functionGraph);
            allSubgraphsAlgorithm.compute();
            List<HashSet<Node>> mostRepeatableClusters = functionGraph.getAttribute("bestSequentialClusters");*/
            System.out.println("TODO: create an algorithm to fold sequential clusters");
        }
    }

    public void disableUpdateOutputs() {
        isToUpdateOutputs = false;
    }

    public Graph getFunctionGraph() {
        return functionGraph;
    }

    public Node getCallNode() {
        return callNode;
    }

    public Node getFunctionNode() {
        return functionNode;
    }

    private void removeNodesFromMainGraph() {
        nodesToMove.forEach(n -> mainGraph.removeNode(n));
    }

    private void createCallNode() {
        callNode = mainGraph.addNode("callNode_" + functionName);
        callNode.setAttribute("att1", "call");
    }

    private void createFunctionNode() {
        functionNode = mainGraph.addNode(functionName);
        functionNode.setAttribute("att1", "function");
        functionNode.setAttribute("att2", functionName);
    }

    private void createFunctionGraph() {
            functionGraph = new MultiGraph(functionName);
            functionGraph.setAttribute("functionName", functionName);
            AddStartAndEnd addStartAndEnd = new AddStartAndEnd();
            addStartAndEnd.init(functionGraph);
            addStartAndEnd.compute();

    }

    private void moveNodesToFunctionGraph(HashSet<Node> nodesToMove) {
        nodesToMove.forEach(node -> copyNodeAndEdges(node, nodesToMove));
    }

    private void setFunctionGraphCInfo() {
        SetGraphCInfo infoAlgorithm = new SetGraphCInfo();
        infoAlgorithm.init(functionGraph);
        infoAlgorithm.compute();
    }

    private void addFunctionToSubgraphList() {
        ArrayList<Graph> subgraphs = mainGraph.getAttribute("subgraphs");
        subgraphs.add(functionGraph);
        mainGraph.setAttribute("subgraphs", subgraphs);
    }

    void copyNodeAndEdges(Node node, HashSet<Node> nodesToAdd) {
        Node n = functionGraph.getNode(node.getId());
        if (n == null) {
            n = functionGraph.addNode(node.getId());
            Graphs.copyAttributes(node, n);
        }

        Node finalN = n;
        node.getEachEnteringEdge().forEach(edge -> {
            Node source = edge.getSourceNode();
            Node newSource = functionGraph.getNode(source.getId());
            if (nodesToAdd.contains(source) && newSource == null) {
                newSource = functionGraph.addNode(source.getId());
                Graphs.copyAttributes(source, newSource);
            }

            if (newSource == null || Utils.isStartNode(newSource)) {
                newSource = Utils.getStartNode(functionGraph);
                if (!Utils.areConnected(edge.getSourceNode(), callNode)) {
                    mainGraph.addEdge(edge.getSourceNode() + ";" + callNode.getId() + "_" + edgeCounter++,
                            edge.getSourceNode(), callNode, true);
                }
            }
            Edge newEdge = functionGraph.addEdge(edge.getId(), newSource, finalN, true);
            Graphs.copyAttributes(edge, newEdge);
        });
        node.getEachLeavingEdge().forEach(edge -> {
            Node target = edge.getTargetNode();
            if (!nodesToAdd.contains(target)) {
                Edge hyperNodeToMainGraph = mainGraph.addEdge(functionNode.getId() + "_" + edgeCounter++ + "->" + target.getId(), functionNode, target, true);
                Graphs.copyAttributes(edge, hyperNodeToMainGraph);
                Node newTarget = functionGraph.getNode("End");
                Edge newEdge = functionGraph.addEdge(edge.getId(), finalN, newTarget, true);
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
        String newName = label + arraySuffix;
        String newLabel = newName + "[" + count + "]";
        edge.setAttribute("label", newLabel);
        edge.setAttribute("name", newName);
        varCounter.put(label, count + 1);
        edge.setAttribute("array", true);
        return newLabel;
    }

    private void updateOutputNames() {

        // if subgraph has an output that is not an array, then we need to make sure that all outputs have the same label
        ArrayList<String> outputNames = new ArrayList<>();

        Node endNode = functionGraph.getNode("End");
        // I have to sort the edges here;
        List<Edge> edgesEnteringEnd = StreamSupport.stream(endNode.getEachEdge().spliterator(), false).sorted(new EdgeComparator()).collect(Collectors.toList());
        for (int i = 0; i < edgesEnteringEnd.size(); i++) {
            Edge edge = edgesEnteringEnd.get(i);
            if (!Utils.isArray(edge)) {
                if (outputNames.size() <= i) {
                    outputNames.add(Utils.getLabel(edge));
                } else {
                    String oldLabel = Utils.getLabel(edge);
                    Node sourceInMain = mainGraph.getNode(edge.getSourceNode().getId());
                    for (Edge edgeInMainGraph: sourceInMain.getEachLeavingEdge()) {
                        if (Utils.getLabel(edgeInMainGraph).equals(oldLabel)) {
                            edgeInMainGraph.setAttribute("label", outputNames.get(i));
                            edgeInMainGraph.setAttribute("name", Utils.varNameFromLabel(outputNames.get(i)));
                        }
                    }
                    edge.setAttribute("label", outputNames.get(i));
                    edge.setAttribute("name", Utils.varNameFromLabel(outputNames.get(i)));
                }

            }
        }

        for (Edge edge : edgesEnteringEnd) {
            if (!Utils.isArray(edge)) {
                String oldLabel = Utils.getLabel(edge);
                Node sourceInFunction = edge.getSourceNode();
                String newLabel = transformScalarIntoArray(edge);
                Node sourceInMain = mainGraph.getNode(sourceInFunction.getId());
                for (Edge leavingSrcInMainEdge : sourceInMain.getEachLeavingEdge()) {
                    if (Utils.getLabel(leavingSrcInMainEdge).equals(oldLabel)) {
                        Node targetInMain = leavingSrcInMainEdge.getTargetNode();
                        for (Edge enteringInTarget : targetInMain.getEachEnteringEdge()) {
                            boolean updateEdge = false;
                            if (enteringInTarget.getSourceNode().getId().equals(functionNode.getId())) {
                                if (enteringInTarget.hasAttribute("pos") && leavingSrcInMainEdge.hasAttribute("pos")) {
                                    if (Utils.getPos(enteringInTarget).equals(Utils.getPos(leavingSrcInMainEdge))) {
                                        updateEdge = true;
                                    }
                                } else {
                                    updateEdge = true;
                                }
                                if (updateEdge) {
                                    enteringInTarget.setAttribute("label", newLabel);
                                    enteringInTarget.setAttribute("name", Utils.varNameFromLabel(newLabel));
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
