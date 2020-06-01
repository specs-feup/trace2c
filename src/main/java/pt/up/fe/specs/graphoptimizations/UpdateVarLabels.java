package pt.up.fe.specs.graphoptimizations;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.VarLoc;
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
        List<VarLoc> localInfo = cinfo.getLocalInfo();
        for (Edge e: graph.getEachEdge()) {
            if (e.hasAttribute("att1") && e.getAttribute("att1").equals("var")){
                if (!utils.isEndNode(e.getTargetNode())) {
                    String label = e.getAttribute("label");
                    if (!utils.isArray(label)) {
                        Integer count = varCount.getOrDefault(label, 1);
                        varCount.put(label, count + 1);
                        label += "_w" + count;
                        e.setAttribute("label", label);
                        localInfo.add(new VarLoc(e.getAttribute("att3"), label, false, 0, null));
                    }
                }

            }
        }
        if (graph.hasAttribute("HyperNode")) {
            Graph subgraph = ((List<Graph>)graph.getAttribute("HyperNode")).get(0);
            UpdateVarLabels updateSubgraph = new UpdateVarLabels();
            updateSubgraph.init(subgraph);
            updateSubgraph.compute();
        }
    }
}
