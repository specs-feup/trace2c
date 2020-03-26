package pt.up.fe.specs.graphoptimizations;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.Graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionWrapper implements Algorithm {
    private final String startNodeId;
    private final String endNodeId;
    private Node startNode;
    private Node endNode;
    private Node previousNode;
    private Node nextNode;
    private String functionName;
    private Graph mainGraph;
    private Graph functionGraph;
    private static int functionsCounter = 1;
    private List<String> inputParameters = new ArrayList<>();

    public FunctionWrapper(String startNodeId, String endNodeId) {
       this.startNodeId = startNodeId;
       this.endNodeId = endNodeId;
       this.functionName = "func"+ functionsCounter++;

    }

    @Override
    public void init(Graph graph) {
        this.mainGraph = graph;
        this.functionGraph = new DefaultGraph(functionName+"Graph");
        this.startNode = this.mainGraph.getNode(startNodeId);
        this.endNode = this.mainGraph.getNode(endNodeId);
        this.previousNode = this.startNode.getEnteringEdge(0).getSourceNode();
        this.nextNode = this.endNode.getLeavingEdge(0).getTargetNode();
    }

    @Override
    public void compute() {
        removeAndCopyNodesToNewGraph(startNode);
        addCallNodeToMainGraph();
        addStartAndEnd(functionGraph);

    }

    private void addCallNodeToMainGraph() {
        Node callNode = mainGraph.addNode("call_"+functionName);
        callNode.setAttribute("att1", "call");
        callNode.setAttribute("att2", functionName);
        Edge inEdge = mainGraph.addEdge("in_"+functionName, previousNode, callNode, true); // this edge should contain the call parameters
        Edge outEdge = mainGraph.addEdge("out_"+functionName, callNode, nextNode, true); // this edge should contain the return parameters
        // Set input parameters on the inEdge
        inEdge.addAttribute("params", inputParameters);
    }

    private void removeAndCopyNodesToNewGraph(Node n) {
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
        if (n == this.endNode) {
            mainGraph.removeNode(n);
            return;
        }
        if (n == startNode) {
            Node newNode = functionGraph.addNode(n.getId());
            Graphs.copyAttributes(n, newNode);
        }
        Node currentNodeInFunc = functionGraph.getNode(n.getId());
        for(Edge e : n.getEachLeavingEdge()){
            Node nextNodeInMain = e.getTargetNode();
            Node nextNodeInFunc = functionGraph.addNode(nextNodeInMain.getId());
            Graphs.copyAttributes(nextNodeInMain, nextNodeInFunc);
            Edge edgeInFunc = functionGraph.addEdge(e.getId(), currentNodeInFunc, nextNodeInFunc, true);
            Graphs.copyAttributes(e, edgeInFunc);
            removeAndCopyNodesToNewGraph(nextNodeInMain);
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
