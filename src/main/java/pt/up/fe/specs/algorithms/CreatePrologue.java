package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.Config;
import pt.up.fe.specs.utils.Utils;
import pt.up.fe.specs.utils.Var;

import java.util.*;

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
        HashSet<Node> nodesToMove = getNodesToMove();
        if (!nodesToMove.isEmpty()) {
            WrapNodesIntoFunction wrapAlgorithm = new WrapNodesIntoFunction(nodesToMove, functionName, Config.isToFoldPrologue());
            wrapAlgorithm.init(mainGraph);
            wrapAlgorithm.compute();

            Node callNode = wrapAlgorithm.getCallNode();
            Node functionNode = wrapAlgorithm.getFunctionNode();
            prologueGraph = wrapAlgorithm.getFunctionGraph();
            addCallingEdges(callNode, functionNode);
            Leveling leveling = new Leveling();
            leveling.init(mainGraph);
            leveling.compute();
            mainGraph.setAttribute("prologue", prologueGraph);
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
        CInfo prologueInfo = prologueGraph.getAttribute("info");
        ArrayList<String> callParams = buildCallParams(prologueInfo);
        Edge callEdge = mainGraph.addEdge("call_prologue", callNode, functionNode, true);
        callEdge.setAttribute("att1", "call");
        callEdge.setAttribute("att2", functionName);
        callEdge.setAttribute("att3", callParams);
    }


    /**
     * The nodes that need to be wrapped into the prologue, are the ones that are (in)directly connected to the
     * parallel function call nodes.
     * This function starts in each call node and moves upward. All nodes in the upward path are selected to be
     * moved into the prologue.
     *
     * @return HashSet<Node> returns the nodes that should be wrapped into the prologue
     */
    private HashSet<Node> getNodesToMove() {
        HashSet<Node> nodesToMove = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        List<Node> callNodes = getCallNodes();
        for (Node callNode : callNodes) {
            for (Edge inEdge : callNode.getEachEnteringEdge()) {
                Node sourceNode = inEdge.getSourceNode();
                queue.add(sourceNode);
            }
            while (!queue.isEmpty()) {
                Node currentNode = queue.remove();
                if (!Utils.isVisited(currentNode)) {
                    Utils.markVisited(currentNode);
                    if (!Utils.isStartNode(currentNode)) nodesToMove.add(currentNode);
                    for (Edge inEdge : currentNode.getEachEnteringEdge()) {
                        queue.add(inEdge.getSourceNode());
                    }
                }

            }


        }


        return nodesToMove;
    }

    private List<Node> getCallNodes() {
        List<Node> callNodes = new ArrayList<>();
        for (Node n : mainGraph) {
            if (Utils.isCall(n)) {
                callNodes.add(n);
            }
        }
        return callNodes;
    }

}
