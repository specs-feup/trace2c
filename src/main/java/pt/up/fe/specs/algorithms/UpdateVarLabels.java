package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.Var;
import pt.up.fe.specs.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdateVarLabels implements Algorithm {

    private CInfo cinfo;
    private Utils utils = new Utils();
    private Graph graph;


    @Override
    public void init(Graph graph) {

        this.graph = graph;
        this.cinfo = graph.getAttribute("info");
    }

    @Override
    public void compute() {
        HashMap<String, Integer> varCount = cinfo.getVariablesCounter();
        List<Var> localInfo = cinfo.getLocalInfo();
        for (Edge e: graph.getEachEdge()) {

            if (e.hasAttribute("att1") && e.getAttribute("att1").equals("var")){
                String label = e.getAttribute("label");
                if (!e.getAttribute("att2").equals("global")) {
                    if (!utils.isEndNode(e.getTargetNode())) {
                        if (label.startsWith("*")) {
                            label = label.substring(1);
                        }
                        if (!utils.isArray(label)) {
                            Integer count = varCount.getOrDefault(label, 1);
                            varCount.put(label, count + 1);
                            label += "_w" + count;
                            e.setAttribute("label", label);
                            localInfo.add(new Var(e.getAttribute("att3"), label, false, null));
                        }
                    }
                }
            } else if (e.getSourceNode().getAttribute("att1").equals("op")) {
                String newLabel = "operationOutput";
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
        if (graph.hasAttribute("subgraphs")) {
            for (Graph subgraph: (ArrayList<Graph>) graph.getAttribute("subgraphs")) {
                UpdateVarLabels updateSubgraph = new UpdateVarLabels();
                updateSubgraph.init(subgraph);
                updateSubgraph.compute();
            }
        }
    }
}
