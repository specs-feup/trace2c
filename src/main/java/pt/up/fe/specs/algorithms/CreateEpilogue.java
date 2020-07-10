package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.LevelingAlgorithmExt;
import pt.up.fe.specs.Var;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CreateEpilogue implements Algorithm {

    Graph mainGraph;
    Graph epilogueGraph;
    String functionName = "epilogue";


    @Override
    public void init(Graph graph) {
        mainGraph = graph;
    }

    @Override
    public void compute() {
        HashSet<Node> nodesToMove = new HashSet<>();
        getNodesToMove(nodesToMove);
        WrapNodesIntoFunction wrapAlgorithm = new WrapNodesIntoFunction(nodesToMove, functionName);
        wrapAlgorithm.disableUpdateOutputs();
        wrapAlgorithm.init(mainGraph);
        wrapAlgorithm.compute();
        Node callNode = wrapAlgorithm.getCallNode();
        Node functionNode = wrapAlgorithm.getFunctionNode();
        Graph functionGraph = wrapAlgorithm.getFunctionGraph();
        addCallingEdges(functionGraph, callNode, functionNode);
        LevelingAlgorithmExt levelingAlgorithmExt = new LevelingAlgorithmExt();
        levelingAlgorithmExt.init(functionGraph);
        levelingAlgorithmExt.compute();
        levelingAlgorithmExt.init(mainGraph);
        levelingAlgorithmExt.compute();
    }

    public Graph getEpilogueGraph(){
        return epilogueGraph;
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
        Edge callEdge = mainGraph.addEdge("call_" + functionName, callNode, functionNode, true);
        callEdge.setAttribute("att1", "call");
        callEdge.setAttribute("att2", functionName);
        callEdge.setAttribute("att3", callParams);
    }


    private void getNodesToMove(HashSet<Node> nodesToMove) {
        ArrayList<ArrayList<Node>> levelGraph = mainGraph.getAttribute("levelgraph");

        Node callNode = getFirstCallNode(levelGraph);
        Node functionNode = callNode.getLeavingEdge(0).getTargetNode();
        int callNodeLevel = callNode.getAttribute("level");
        int functionNodeLevel = callNodeLevel + 1;
        for (Node n: levelGraph.get(callNodeLevel)) {
            if (!n.getId().equals(callNode.getId())) {
                nodesToMove.add(n);
            }
        }
        for (Node n: levelGraph.get(functionNodeLevel)) {
            if (!n.getId().equals(functionNode.getId())) {
                nodesToMove.add(n);
            }
        }
        for (int i = functionNodeLevel + 1; i < levelGraph.size() - 1; i++) {
            nodesToMove.addAll(levelGraph.get(i));
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
