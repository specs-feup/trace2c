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
    List<Var> localInfo;

    @Override
    public void init(Graph graph) {

        this.graph = graph;
        this.cinfo = graph.getAttribute("info");
        this.varCount = cinfo.getVariablesCounter();;
        this.localInfo = cinfo.getLocalInfo();
    }

    @Override
    public void compute() {
        for (Edge e : graph.getEachEdge()) {
            Node sourceNode = e.getSourceNode();
            if (Utils.isVar(e) && !Utils.isGlobalVar(e)) {
                String label = Utils.getLabel(e);

                if (!Utils.isEndNode(e.getTargetNode()) && !Utils.isStartNode(sourceNode)) {
                    if (label.startsWith("*")) {
                        label = label.substring(1);
                    }
                    if (!Utils.isArray(e)) {
                        Integer count = varCount.getOrDefault(label, 1);
                        varCount.put(label, count + 1);
                        label += "_w" + count;
                        e.setAttribute("label", label);
                        localInfo.add(new Var(e.getAttribute("att3"), label, false, null));
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
        if (graph.hasAttribute("subgraphs")) {
            for (Graph subgraph : (ArrayList<Graph>) graph.getAttribute("subgraphs")) {
                UpdateVarLabels updateSubgraph = new UpdateVarLabels();
                updateSubgraph.init(subgraph);
                updateSubgraph.compute();
            }
        }
    }

    private void addNewLabelToEdge(Edge e, String newLabel) {
        Integer count = varCount.getOrDefault(newLabel, 1);
        varCount.put(newLabel, count + 1);
        newLabel += "_w" + count;
        e.setAttribute("label", newLabel);
        e.setAttribute("att1", "var");
        e.setAttribute("att2", "loc");
        e.setAttribute("att3", "double");
        localInfo.add(new Var(e.getAttribute("att3"), newLabel, false, null));
    }
}
