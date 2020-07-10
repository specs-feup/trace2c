package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.Var;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CreatePrologue implements Algorithm {

    Graph mainGraph;
    Graph prologueGraph;
    String functionName = "prologue";


    @Override
    public void init(Graph graph) {
        mainGraph = graph;
    }

    @Override
    public void compute() {
        HashSet<Node> nodesToMove = new HashSet<>();
        getNodesToMove(nodesToMove);
        WrapNodesIntoFunction wrapAlgorithm = new WrapNodesIntoFunction(nodesToMove, functionName);
        wrapAlgorithm.init(mainGraph);
        wrapAlgorithm.compute();

        Node callNode = wrapAlgorithm.getCallNode();
        Node functionNode = wrapAlgorithm.getFunctionNode();
        Graph functionGraph = wrapAlgorithm.getFunctionGraph();
        addCallingEdges(functionGraph, callNode, functionNode);
        System.out.println("Debug");
    }

    public Graph getPrologueGraph(){
        return prologueGraph;
    }


    private ArrayList<String> buildCallParams(CInfo prologueInfo) {
        ArrayList<String> callParams = new ArrayList<>();
        List<Var> parameters = new ArrayList<>();
        parameters.addAll(prologueInfo.getInputs());
        parameters.addAll(prologueInfo.getOutputs());
        parameters.forEach(var -> {
            String varName = var.getName();
            if (varName.startsWith("*")) {
                varName = varName.substring(1);
            }
            callParams.add(varName);
        });
        return callParams;
    }

    private void addCallingEdges(Graph functionGraph, Node callNode, Node functionNode) {
        CInfo prologueInfo = functionGraph.getAttribute("info");
        ArrayList<String> callParams = buildCallParams(prologueInfo);
        Edge callEdge = mainGraph.addEdge("call_prologue", callNode, functionNode, true);
        callEdge.setAttribute("att1", "call");
        callEdge.setAttribute("att2", functionGraph);
        callEdge.setAttribute("att3", callParams);
    }


    private void getNodesToMove(HashSet<Node> nodesToMove) {
        ArrayList<ArrayList<Node>> levelGraph = mainGraph.getAttribute("levelgraph");

        Node callNode = getFirstCallNode(levelGraph);
        int callNodeLevel = callNode.getAttribute("level");
        int functionNodeLevel = callNodeLevel + 1;
        for (int i = 0; i < callNodeLevel; i++) {
            nodesToMove.addAll(levelGraph.get(i));
        }
        for (Node n: levelGraph.get(callNodeLevel)) {
            if (!n.getId().equals(callNode.getId())) {
                nodesToMove.add(n);
            }
        }
    }

    private Node getFirstCallNode(ArrayList<ArrayList<Node>> levelGraph) {
        for (ArrayList<Node> level: levelGraph) {
            for (Node n: level) {
                if (n.getAttribute("att1").equals("call")) {
                    return n;
                }
            }
        }
        return null;
    }

}
