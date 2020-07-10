package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.MultiGraph;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.Var;
import pt.up.fe.specs.WrapConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wraps a sub-graph into a new graph that represents a new function
 * A sub-graph is defined by the bottom node and the number of levels
 */
public class FunctionWrapper implements Algorithm {
    private final String endNodeId;
    private Node endNode;
    private String functionName;
    private Graph mainGraph;
    private Graph functionGraph;
    private int totalLevels;
    private static int functionsCounter = 1;
    private HashMap<Node, Node> nodesThatConnectToFunction = new HashMap<>(); //Maps nodes to it's respective input parameters
    private List<Node> nodesThatFunctionConnectsTo = new ArrayList<>();

    public FunctionWrapper(WrapConfig wrapConfig) {
       this.endNodeId = wrapConfig.getEndNode();
       this.totalLevels = wrapConfig.getLevels();
       this.functionName = "func"+ functionsCounter++;

    }

    @Override
    public void init(Graph graph) {
        this.mainGraph = graph;
        this.functionGraph = new MultiGraph(functionName+"Graph");
        this.endNode = this.mainGraph.getNode(endNodeId);
        for(Edge e: this.endNode.getEachLeavingEdge()) {
            this.nodesThatFunctionConnectsTo.add(e.getTargetNode());
        }
    }

    @Override
    public void compute() {
        removeAndCopyNodesToNewGraph(endNode, 1);
        addCallNodeToMainGraph();
        addStartAndEnd(functionGraph);
        addInputsAndOutputsToFunctionGraph();
    }


    private void addInputsAndOutputsToFunctionGraph() {
        CInfo info = new CInfo();
        for (Node param : nodesThatConnectToFunction.values()) {
            if (!param.getAttribute("att1").equals("const")) {
                Var input = new Var(param.getAttribute("att3"), param.getAttribute("label"), false, null);
                info.addInput(input);
                if (param.getAttribute("att2").equals("loc")) {
                    Node nodeInFunc = functionGraph.getNode(param.getId());
                    nodeInFunc.setAttribute("att2", "inte");
                }
            }

        }
        for (Edge entry : functionGraph.getNode("End").getEachEnteringEdge()) {
            Node sourceNode = entry.getSourceNode();
            String outputType = sourceNode.getAttribute("att3");
            String outputName = sourceNode.getAttribute("label");
            Var output = new Var(outputType, outputName, false, null);
            info.addOutput(output);
        }
        functionGraph.setAttribute("info", info);
    }

    private void addCallNodeToMainGraph() {
        Node callNode = mainGraph.addNode("call_"+functionName);
        callNode.setAttribute("att1", "call");
        callNode.setAttribute("att2", functionName);

        callNode.setAttribute("label", "call_"+functionName);
        for (Map.Entry<Node, Node> entry : nodesThatConnectToFunction.entrySet()) {
            Edge inEdge = mainGraph.addEdge("in_"+entry.getKey().getAttribute("label")+"_"+functionName, entry.getKey(), callNode, true); // this edge should contain the call parameters
            inEdge.setAttribute("att2", "inte");
        }
        for (int i = 0; i < nodesThatFunctionConnectsTo.size(); i++) {
            Node nextNode = nodesThatFunctionConnectsTo.get(i);
            mainGraph.addEdge("out_"+i+"_"+functionName, callNode, nextNode, true); // this edge should contain the return parameters
        }

    }

    private void removeAndCopyNodesToNewGraph(Node n, int currentLevel) {
        if (currentLevel > this.totalLevels) {
            nodesThatConnectToFunction.put(n, n.getLeavingEdge(0).getTargetNode());
            return;
        }

        Node currentNodeInFunc = functionGraph.addNode(n.getId());
        Graphs.copyAttributes(n, currentNodeInFunc);

        // This conversion from iterable to list was necessary due to a bug caused by using the iterable
        List<Edge> enteringEdges = new ArrayList<>();
        n.getEachEnteringEdge().forEach(enteringEdges::add);

        enteringEdges.forEach(e -> {
            Node prevNodeInMain = e.getSourceNode();
            removeAndCopyNodesToNewGraph(prevNodeInMain, currentLevel+1);
            if (currentLevel < this.totalLevels) {
                Node prevNodeInFunc = functionGraph.getNode(prevNodeInMain.getId());
                Edge edgeInFunc = functionGraph.addEdge(e.getId(), prevNodeInFunc, currentNodeInFunc, true);
                Graphs.copyAttributes(e, edgeInFunc);
            }
        });

        mainGraph.removeNode(n);

    }

    public Graph getNewGraph() {
        return functionGraph;
    }

    /**
     * Add Start and End nodes to beginning and end of the dataflow.
     *
     * @param g
     *            Graph to change.
     */
    private void addStartAndEnd(Graph g) {
        Node startNode = g.getNode("Start");
        Node endNode = g.getNode("End");
        if (startNode == null) {
            startNode = g.addNode("Start");
            startNode.addAttribute("label", "start");
            startNode.addAttribute("att1", "nop");
            startNode.addAttribute("att2", this.functionName );
        }
        if (endNode == null) {
            endNode = g.addNode("End");
            endNode.addAttribute("label", "end");
            endNode.addAttribute("att1", "nop");
        }
        for (Node n : g.getEachNode()) {
            if (n.getInDegree() == 0 && !n.equals(startNode) && !n.equals(endNode))
                g.addEdge("begin to:" + n.getId(), startNode, n, true);
            else if (n.getOutDegree() == 0 && !n.equals(endNode) && !n.equals(startNode)) {

                g.addEdge("end from:" + n.getId(), n, endNode, true);

            }


        }
    }
}
