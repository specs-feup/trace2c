package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.utils.Var;
import pt.up.fe.specs.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdateVarLabels implements Algorithm {

    private CInfo cinfo;
    private Graph graph;
    HashMap<String, Integer> varCount;
    HashMap<String, String> sourceNodeToOutputLabel = new HashMap<>();
    List<Var> localInfo;

    @Override
    public void init(Graph graph) {

        this.graph = graph;
        this.cinfo = graph.getAttribute("info");
        this.varCount = cinfo.getVariablesCounter();
        this.localInfo = cinfo.getLocalInfo();
    }

    @Override
    public void compute() {

        for (Node sourceNode : graph.getEachNode()) {
            for (Edge e : sourceNode.getEachLeavingEdge()) {
                if (Utils.isVar(e) && !Utils.isGlobalVar(e)) {
                    String label = Utils.getLabel(e);
                    if (!Utils.isEndNode(e.getTargetNode()) && !Utils.isStartNode(sourceNode)) {
                        if (label.startsWith("*")) {
                            label = label.substring(1);
                        }
                        if (!Utils.isArray(e)) {
                            Node targetNode = e.getTargetNode();
                            String newLabel;
                            if (sourceNodeToOutputLabel.containsKey(sourceNode.getId())) {
                                newLabel = sourceNodeToOutputLabel.get(sourceNode.getId());
                            } else {
                                Integer count = varCount.getOrDefault(label, 1);
                                varCount.put(label, count + 1);
                                newLabel = label + "_w" + count;
                                sourceNodeToOutputLabel.put(sourceNode.getId(), newLabel);

                            }
                            e.setAttribute("label", newLabel);
                            e.setAttribute("name", newLabel);

                            if (Utils.isComplexAssignment(targetNode) || Utils.isNOP(targetNode)) {
                                updateEdgesLeavingNode(targetNode, label, newLabel);
                            }
                        }
                    }

                } else if (Utils.isOperation(sourceNode)) {
                    if (Utils.isInteger(Utils.getLabel(e))) {
                        addNewLabelToEdge(e, "operationOutput");
                    }
                } else if (Utils.isMux(e.getSourceNode())) {
                    if (Utils.isInteger(Utils.getLabel(e))) {
                        addNewLabelToEdge(e, "muxOutput");
                    }
                }
            }

        }


        if (graph.hasAttribute("subgraphs")) {
            for (Graph subgraph : (ArrayList<Graph>) graph.getAttribute("subgraphs")) {
                UpdateVarLabels updateSubgraph = new UpdateVarLabels();
                updateSubgraph.init(subgraph);
                updateSubgraph.compute();
            }
        }
    }

    private void updateEdgesLeavingNode(Node target, String labelToReplace, String newLabel) {
        for (Edge targetLeavingEdge : target.getEachLeavingEdge()) {
            String oldLabel = Utils.getLabel(targetLeavingEdge);
            targetLeavingEdge.setAttribute("label", oldLabel.replace(labelToReplace, newLabel));
        }
    }

    private void addNewLabelToEdge(Edge e, String newLabel) {
        String sourceId = e.getSourceNode().getId();
        if (sourceNodeToOutputLabel.containsKey(sourceId)) {
          newLabel = sourceNodeToOutputLabel.get(sourceId);
        } else {
            Integer count = varCount.getOrDefault(newLabel, 1);
            varCount.put(newLabel, count + 1);
            newLabel += "_w" + count;
            sourceNodeToOutputLabel.put(sourceId, newLabel);
        }

        e.setAttribute("label", newLabel);
        e.setAttribute("name", newLabel);
        e.setAttribute("att1", "var");
        e.setAttribute("att2", "loc");
        e.setAttribute("att3", "double");
        //localInfo.add(new Var(e.getAttribute("att3"), newLabel, false, null));
    }
}
