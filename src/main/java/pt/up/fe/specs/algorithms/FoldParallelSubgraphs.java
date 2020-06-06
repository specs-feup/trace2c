package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.MultiGraph;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.Config;
import pt.up.fe.specs.VarIO;
import pt.up.fe.specs.VarLoc;
import pt.up.fe.specs.utils.AddStartAndEnd;
import pt.up.fe.specs.utils.GraphInputComparator;
import pt.up.fe.specs.utils.GraphOutputComparator;
import pt.up.fe.specs.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class FoldParallelSubgraphs implements Algorithm {

    private final String arraySuffix = "_array";
    private final Config config;

    Graph mainGraph;
    List<HashSet<Node>> parallelSubgraphs;
    Node callSourceNode; // the node that makes the calls to the hyperNode
    private Graph functionGraph;
    private CInfo cInfo;
    private Utils utils = new Utils();
    private HashMap<String, Integer> varCounter = new HashMap<>();
    private HashMap<String, String> varsToTypes = new HashMap<>();
    private int edgeCounter = 0;
    private int graphCounter = 0;


    public FoldParallelSubgraphs(List<HashSet<Node>> parallelSubgraphs, Config config) {
        this.parallelSubgraphs = parallelSubgraphs;
        this.config = config;
    }

    @Override
    public void init(Graph graph) {
        mainGraph = graph;
        cInfo = graph.getAttribute("info");
    }

    @Override
    public void compute() {
        Node hyperNode = createHyperNode();
        moveNodesToSubgraphList(parallelSubgraphs, hyperNode);
        orderSubgraphs();
        updateOutputNames();
        removeNodesFromMainGraph(parallelSubgraphs);
        foldSubgraphs();
        updateLocalInfo();
        setFunctionGraphCInfo();
        addCallEdges(hyperNode);
    }

    public Graph getFunctionGraph() {
        return functionGraph;
    }

    private void addCallEdges(Node hyperNode) {
        String functionName = functionGraph.getAttribute("functionName");
        int numberParallelFunctions = config.getParallelFunctions();
        ArrayList<Integer> offsets = new ArrayList<>(numberParallelFunctions);
        ArrayList<Integer> widths = new ArrayList<>(numberParallelFunctions);
        calculateOffsetsAndWidths(offsets, widths);
        CInfo info = functionGraph.getAttribute("info");
        List<VarIO> inputs = info.getInputs();
        List<VarIO> outputs = info.getOutputs();
        ArrayList<ArrayList<String>> params = new ArrayList<>();
        buildCallParams(params, offsets, widths, inputs, outputs);
        for (int i = 0; i < numberParallelFunctions; i++) {
            Edge callEdge = mainGraph.addEdge("call_"+functionName+"_"+edgeCounter++, callSourceNode, hyperNode, true);
            callEdge.setAttribute("att1", "call");
            callEdge.setAttribute("att2", functionName);
            callEdge.setAttribute("att3", params.get(i));
        }

    }

    private void buildCallParams(ArrayList<ArrayList<String>> params, ArrayList<Integer> offsets, ArrayList<Integer> widths, List<VarIO> inputs, List<VarIO> outputs) {
        int numberOfCalls = offsets.size();
        for (int i=0; i < numberOfCalls; i++) {
            ArrayList<String> paramsForFunction = new ArrayList<>();
            for (int j = 0; j < inputs.size() - 2; j++) {
                paramsForFunction.add(inputs.get(j).getName());
            }
            paramsForFunction.add(String.valueOf(offsets.get(i)));
            paramsForFunction.add(String.valueOf(widths.get(i)));
            for (int j = 0; j < outputs.size(); j++) {
                paramsForFunction.add(outputs.get(j).getName());
            }
            params.add(paramsForFunction);
        }
    }

    private void calculateOffsetsAndWidths(ArrayList<Integer> offsets, ArrayList<Integer> widths) {
        int size = functionGraph.getAttribute("size");
        int numberParallelFunctions = config.getParallelFunctions();
        int currentOffset = 0;
        int width = 0;
        for (int i=0; i < numberParallelFunctions; i++) {
            offsets.add(currentOffset);
            width = Math.min((int) Math.ceil((float) size/numberParallelFunctions), size - currentOffset);
            currentOffset += width;
            widths.add(width);
        }
    }

    private void foldSubgraphs() {
        FoldingAlgorithm foldingAlgorithm = new FoldingAlgorithm();
        foldingAlgorithm.init(mainGraph);
        foldingAlgorithm.compute();
        functionGraph = foldingAlgorithm.getFunctionGraph();
    }

    private void setFunctionGraphCInfo() {
        SetGraphCInfo infoAlgorithm = new SetGraphCInfo(cInfo);
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

    void updateOutputNames() {
        ArrayList<Graph> subgraphs = mainGraph.getAttribute("HyperNode");
        subgraphs.forEach(graph -> {
            Node endNode = graph.getNode("End");
            for (int i = 0; i < endNode.getInDegree(); i++) {
                Edge edge = endNode.getEnteringEdge(i);
                if (!edge.hasAttribute("array")) {
                    String oldLabel = edge.getAttribute("label");
                    Node sourceInHyper = edge.getSourceNode();
                    String newLabel = transformScalarIntoArray(edge);
                    Node sourceInMain = mainGraph.getNode(sourceInHyper.getId());
                    for (Edge leavingSrcInMainEdge: sourceInMain.getEachLeavingEdge()) {
                        if (leavingSrcInMainEdge.getAttribute("label").equals(oldLabel)) {
                            Node targetInMain = leavingSrcInMainEdge.getTargetNode();
                            for (Edge enteringInTarget: targetInMain.getEachEnteringEdge()) {
                                if (enteringInTarget.getSourceNode().getId().equals("HyperNode")) {
                                    if (enteringInTarget.getAttribute("pos").equals(leavingSrcInMainEdge.getAttribute("pos"))) {
                                        enteringInTarget.setAttribute("label", newLabel);
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
        });
    }

    void updateLocalInfo() {
        List<VarLoc> localInfo = cInfo.getLocalInfo();
        varCounter.forEach( (label,count) -> {
            List<Integer> indexes = new ArrayList<>();
            indexes.add(count);
            localInfo.add(new VarLoc(varsToTypes.get(label), label + arraySuffix, true, indexes));
        });
    }

    void orderSubgraphs() {
        ArrayList<Graph> subgraphs = mainGraph.getAttribute("HyperNode");
        Graph subgraph0 = subgraphs.get(0);
        if (subgraph0.getNode("End").getEnteringEdge(0).hasAttribute("array")) {
            // order by output
            subgraphs.sort(new GraphOutputComparator());
        } else {
            // order by input
            subgraphs.sort(new GraphInputComparator());
        }

    }

    /**
     * If the output of all subgraphs is a single var that has the same name, then it should be replaced by a temporary
     * local array, so that the the subgraphs can be ordered and the folding can be completed.
     * @param commonSubgraphs
     * @param hyperNode
     */
    ArrayList<MultiGraph> moveNodesToSubgraphList(List<HashSet<Node>> commonSubgraphs, Node hyperNode) {
        ArrayList<MultiGraph> subgraphList = new ArrayList<>();
        commonSubgraphs.forEach(nodes -> {
            MultiGraph g = new MultiGraph("parallelGraph"+graphCounter++);
            AddStartAndEnd addStartAndEnd = new AddStartAndEnd();
            addStartAndEnd.init(g);
            addStartAndEnd.compute();
            nodes.forEach(node -> {
                copyNodeAndEdges(node, g, nodes, hyperNode);
            });

            subgraphList.add(g);
        });
        mainGraph.addAttribute("HyperNode", subgraphList);
        return subgraphList;
    }

    Node createHyperNode() {
        Node hyperNode = mainGraph.addNode("HyperNode");
        hyperNode.addAttribute("label", "hyper1");
        hyperNode.addAttribute("att1", "hyper");
        hyperNode.addAttribute("name", "hyper1");
        hyperNode.addAttribute("type", "parallel");
        return hyperNode;
    }

    void copyNodeAndEdges(Node node, Graph subgraph, HashSet<Node> nodesToAdd, Node hyperNode) {
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
                if (callSourceNode == null) {
                    callSourceNode = source;
                }
                newSource = subgraph.getNode("Start");
            }
            Edge newEdge = subgraph.addEdge(edge.getId(), newSource, finalN, true);
            Graphs.copyAttributes(edge, newEdge);
        });
        node.getEachLeavingEdge().forEach(edge -> {
            Node target = edge.getTargetNode();
            if (!nodesToAdd.contains(target)) {
                //transformScalarIntoArray(edge);
                Edge hyperNodeToMainGraph = mainGraph.addEdge(hyperNode.getId() + "_" + edgeCounter++ + "->" + target.getId(), hyperNode, target, true);
                Graphs.copyAttributes(edge, hyperNodeToMainGraph);
                Node newTarget = subgraph.getNode("End");
                Edge newEdge = subgraph.addEdge(edge.getId(), finalN, newTarget, true);
                Graphs.copyAttributes(edge, newEdge);
            }
        });
    }

    private String transformScalarIntoArray(Edge edge) {
        String label = edge.getAttribute("label");
        varsToTypes.putIfAbsent(label, edge.getAttribute("att3"));
        int count = varCounter.getOrDefault(label, 0);
        String newLabel = label + arraySuffix + "[" + count + "]";
        edge.setAttribute("label", newLabel);
        varCounter.put(label, count + 1);
        edge.setAttribute("array", true);
        return newLabel;
    }
}
