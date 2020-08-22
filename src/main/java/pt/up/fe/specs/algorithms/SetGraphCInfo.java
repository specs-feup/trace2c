package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.utils.Var;
import pt.up.fe.specs.utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetGraphCInfo implements Algorithm {

    private Graph graph;
    private CInfo info;
    private FoldInfo foldInfo;
    private final HashMap<String, List<Integer>> varsMaxIndexes = new HashMap<>();
    private final HashMap<String, String> varTypes = new HashMap<>();
    private Integer foldWidth = 0;


    @Override
    public void init(Graph graph) {
        this.graph = graph;
        this.info = new CInfo();
        if (graph.hasAttribute("foldInfo")) {
            this.foldInfo = graph.getAttribute("foldInfo");
            this.foldWidth = foldInfo.getWidth();
        }

    }

    @Override
    public void compute() {
        Node startNode = Utils.getStartNode(graph);
        Node endNode = Utils.getEndNode(graph);
        List<Var> inputs = getInfoFromEdges(startNode.getEachLeavingEdge());
        List<Var> outputs = getInfoFromEdges(endNode.getEachEnteringEdge());
        inputs.forEach(input -> info.addInput(input));
        outputs.forEach(output -> info.addOutput(output));
        graph.setAttribute("info", info);
    }

    List<Integer> unfoldIndexes(Edge edge) {
        String label =Utils.getLabel(edge);

        if (edge.hasAttribute("loopinfo")) {
            List<Integer> unfoldedIndexes = new ArrayList<>();
            LoopInfo loopInfo = edge.getAttribute("loopinfo");
            ArrayList<SpecificLoopInfo> loopList = loopInfo.getLoopList();
            for (SpecificLoopInfo loop: loopList) {
                for (int dim = 0; dim < loop.dim; dim++) {
                    if (loop.progressionTypes.get(dim).equals(ProgressionType.Arithmetic)) {
                        int maxIndex = loop.ratios.get(dim) * (foldWidth-1) + loop.initialValues.get(dim);
                        unfoldedIndexes.add(maxIndex);
                    }
                }
            }
            return unfoldedIndexes;
        } else {
            return Utils.getIndexes(label);
        }


    }

    private List<Integer> transformIndexesToSizes(List<Integer> indexes) {
        List<Integer> sizes = new ArrayList<>();
        for (Integer index : indexes) {
            if (index == null) {
                sizes.add(null);
            } else sizes.add(index + 1);
        }
        return sizes;
    }


    private List<Var> getInfoFromEdges(Iterable<Edge> edgesToAnalyze) {
        List<Var> varsInfo = new ArrayList<>();
        for (Edge edge: edgesToAnalyze) {
            if (!Utils.isVar(edge)) continue;
            if (Utils.isArray(edge)) {
                String varName = Utils.getName(edge);
                List<Integer> indexes = unfoldIndexes(edge);
                varTypes.putIfAbsent(varName, Utils.getVarType(edge));
                if (varsMaxIndexes.containsKey(varName)) {
                    List<Integer> prevMaxSizes = varsMaxIndexes.get(varName);
                    if (Utils.compareIndexes(prevMaxSizes, indexes) < 0) {
                        varsMaxIndexes.put(varName, indexes);
                    }

                } else {
                    varsMaxIndexes.put(varName, indexes);
                }
            } else {
                String label = Utils.getLabel(edge);
                String type = Utils.getVarType(edge);
                varsInfo.add(new Var(type, label, false, null));
            }
        }

        for (Map.Entry<String, List<Integer>> varAndIndexes : varsMaxIndexes.entrySet()) {
            String varName = varAndIndexes.getKey();
            List<Integer> maxIndexes = varAndIndexes.getValue();
            List<Integer> sizes = transformIndexesToSizes(maxIndexes);
            Var newVar = new Var(varTypes.get(varName), varName, true, sizes);
            varsInfo.add(newVar);
        }
        varTypes.clear();
        varsMaxIndexes.clear();
        return varsInfo;
    }




    public CInfo getInfo() {
        return info;
    }


}
