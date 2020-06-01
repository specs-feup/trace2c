package pt.up.fe.specs.graphoptimizations;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.utils.ProgressionType;
import pt.up.fe.specs.utils.Utils;

import java.util.ArrayList;

/**
 * To fold we need to find the progression for each variable in the subgraph;
 */
public class FoldingAlgorithm implements Algorithm {

    private static Integer loopCounter = 0;
    private Graph graph;
    private Utils utils = new Utils();
    private String loopName = "loop_match_fold";


    @Override
    public void init(Graph graph) {
        this.graph = graph;
        this.loopName = this.loopName + loopCounter++;
    }

    @Override
    public void compute() {
        ArrayList<Graph> subgraphList = graph.getAttribute("HyperNode");
       computeLoopInfoForAllEdges(subgraphList);
       Node hyperNode = graph.getNode("HyperNode");
       hyperNode.setAttribute("initv", getOutputValue(subgraphList.get(0)));
       hyperNode.setAttribute("+incr", getIncrementValue(subgraphList));
       hyperNode.setAttribute("size", subgraphList.size());
       hyperNode.setAttribute("loopname", this.loopName);
       hyperNode.setAttribute("plus_fold", 0);
       hyperNode.setAttribute("mult_fold", 1);
    }

    private Integer getOutputValue(Graph graph) {
        Node endNode = graph.getNode("End");
        String label = endNode.getEnteringEdge(0).getAttribute("label");
        return utils.getIndexes(label).get(0);
    }

    private Integer getIncrementValue(ArrayList<Graph> subgraphs) {
        return getOutputValue(subgraphs.get(1)) - getOutputValue(subgraphs.get(0));
    }

    private void computeLoopInfoForAllEdges(ArrayList<Graph> subgraphList) {
        Graph subgraph0 = subgraphList.get(0);
        Graph subgraph1 = subgraphList.get(1);
        Graph subgraph2 = subgraphList.get(2);
        for (int i = 0; i < subgraph0.getEdgeCount(); i++) {
            Edge edge0 = subgraph0.getEdge(i);
            String label0 = edge0.getAttribute("label");
            String label1 = subgraph1.getEdge(i).getAttribute("label");
            String label2 = subgraph2.getEdge(i).getAttribute("label");
            ArrayList<Integer> indexes0 = utils.getIndexes(label0);
            ArrayList<Integer> indexes1 = utils.getIndexes(label1);
            ArrayList<Integer> indexes2 = utils.getIndexes(label2);
            try {
                LoopInfo loopInfo = getLoopInfo(indexes0, indexes1, indexes2);
                edge0.setAttribute("loopinfo", loopInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private LoopInfo getLoopInfo(ArrayList<Integer> indexes0, ArrayList<Integer> indexes1, ArrayList<Integer> indexes2) throws Exception {
        ArrayList<ProgressionType> progressionTypes = new ArrayList<>();
        ArrayList<Integer> ratios = new ArrayList<>();
        for (int i = 0; i < indexes0.size(); i++) {
            Integer ratio = indexes1.get(i) - indexes0.get(i);
            if (indexes2.get(i) == indexes0.get(i) + 2 * ratio) {
                // Then it is an arithmetic progression
                progressionTypes.add(ProgressionType.Arithmetic);
                ratios.add(ratio);
            } else {
                // Check if it is a geometric progression
                ratio = indexes1.get(i) / indexes0.get(i);
                if (indexes2.get(i) / ratio == indexes1.get(i)) {
                    // Then it is a geometric progression
                    progressionTypes.add(ProgressionType.Geometric);
                    ratios.add(ratio);
                } else {
                    Exception e = new Exception("Invalid progression");
                    throw e;
                }
            }
        }
        return new LoopInfo(loopName, ratios, progressionTypes);
    }
}
