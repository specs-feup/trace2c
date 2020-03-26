package pt.up.fe.specs.graphoptimizations;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.Graphs;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> inputParameters = new ArrayList<>();
    private List<Node> nodesThatConnectToFunction = new ArrayList<>();
    private List<Node> nodesThatFunctionConnectsTo = new ArrayList<>();

    public FunctionWrapper(Integer levels, String endNodeId) {
       this.endNodeId = endNodeId;
       this.totalLevels = levels;
       this.functionName = "func"+ functionsCounter++;

    }

    @Override
    public void init(Graph graph) {
        this.mainGraph = graph;
        this.functionGraph = new DefaultGraph(functionName+"Graph");
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

    }

    private void addCallNodeToMainGraph() {
        Node callNode = mainGraph.addNode("call_"+functionName);
        callNode.setAttribute("att1", "call");
        callNode.setAttribute("att2", functionName);
        for (int i = 0; i < nodesThatConnectToFunction.size() ; i++) {
            Node previousNode = nodesThatConnectToFunction.get(i);
            Edge inEdge = mainGraph.addEdge("in_"+i+"_"+functionName, previousNode, callNode, true); // this edge should contain the call parameters
            inEdge.addAttribute("params", inputParameters);
        }
        for (int i = 0; i < nodesThatFunctionConnectsTo.size(); i++) {
            Node nextNode = nodesThatFunctionConnectsTo.get(i);
            mainGraph.addEdge("out_"+i+"_"+functionName, callNode, nextNode, true); // this edge should contain the return parameters
        }

    }

    private void removeAndCopyNodesToNewGraph(Node n, int currentLevel) {
        if (currentLevel > this.totalLevels) {
            nodesThatConnectToFunction.add(n);
            return;
        }
        if (n.hasAttribute("att1") && n.getAttribute("att1").equals("var")) {
            if (n.hasAttribute("att2") && n.getAttribute("att2").equals("inte")) {
                String paramName = n.getAttribute("name");
                boolean isPointer = paramName.charAt(0) == '*';
                if (isPointer) {
                    paramName = paramName.substring(1);
                }
                inputParameters.add(paramName);
            }
        }


        Node currentNodeInFunc = functionGraph.addNode(n.getId());
        Graphs.copyAttributes(n, currentNodeInFunc);
        for(Edge e : n.getEachEnteringEdge()){
            Node prevNodeInMain = e.getSourceNode();
            removeAndCopyNodesToNewGraph(prevNodeInMain, currentLevel+1);
            if (currentLevel < this.totalLevels) {
                Node prevNodeInFunc = functionGraph.getNode(prevNodeInMain.getId());
                Edge edgeInFunc = functionGraph.addEdge(e.getId(), prevNodeInFunc, currentNodeInFunc, true);
                Graphs.copyAttributes(e, edgeInFunc);
            }

        }
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
            else if (n.getOutDegree() == 0 && !n.equals(endNode) && !n.equals(startNode))
                g.addEdge("end from:" + n.getId(), n, endNode, true);



        }
    }
}
