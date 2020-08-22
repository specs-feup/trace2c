package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.Config;
import pt.up.fe.specs.utils.Utils;
import pt.up.fe.specs.utils.Var;

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
        HashSet<Node> nodesToMove = getNodesToMove();
        if (!nodesToMove.isEmpty()) {
            WrapNodesIntoFunction wrapAlgorithm = new WrapNodesIntoFunction(nodesToMove, functionName, Config.isToFoldEpilogue());
            wrapAlgorithm.disableUpdateOutputs();
            wrapAlgorithm.init(mainGraph);
            wrapAlgorithm.compute();
            Node callNode = wrapAlgorithm.getCallNode();
            Node functionNode = wrapAlgorithm.getFunctionNode();
            epilogueGraph = wrapAlgorithm.getFunctionGraph();
            addCallingEdges(callNode, functionNode);
            Leveling leveling = new Leveling();
            leveling.init(mainGraph);
            leveling.compute();
            mainGraph.setAttribute("epilogue", epilogueGraph);
        }

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

    private void addCallingEdges(Node callNode, Node functionNode) {
        CInfo prologueInfo = epilogueGraph.getAttribute("info");
        ArrayList<String> callParams = buildCallParams(prologueInfo);
        Edge callEdge = mainGraph.addEdge("call_" + functionName, callNode, functionNode, true);
        callEdge.setAttribute("att1", "call");
        callEdge.setAttribute("att2", functionName);
        callEdge.setAttribute("att3", callParams);
    }


    private HashSet<Node> getNodesToMove() {
        HashSet<Node> nodesToMove = new HashSet<>();
        for (Node n : mainGraph.getEachNode()) {
            if (!Utils.isCall(n) && !Utils.isFunction(n) && !Utils.isStartNode(n) && !Utils.isEndNode(n)) {
                nodesToMove.add(n);
            }
        }
        return nodesToMove;
    }


}
