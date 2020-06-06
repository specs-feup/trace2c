package pt.up.fe.specs.algorithms;

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
    private String loopName = "parallelFunction";
    private Graph functionGraph;

    @Override
    public void init(Graph graph) {
        this.graph = graph;
        this.loopName = this.loopName + loopCounter++;
    }

    @Override
    public void compute() {
        ArrayList<Graph> subgraphList = graph.getAttribute("HyperNode");
        computeLoopInfoForAllEdges(subgraphList);
        functionGraph = subgraphList.get(0);
        functionGraph.setAttribute("initv", getOutputValue(subgraphList.get(0)));
        functionGraph.setAttribute("+incr", getIncrementValue(subgraphList));
        functionGraph.setAttribute("size", subgraphList.size());
        functionGraph.setAttribute("functionName", this.loopName);
        functionGraph.setAttribute("plus_fold", 0);
        functionGraph.setAttribute("mult_fold", 1);
        functionGraph.setAttribute("type", "parallel");

    }

    public Graph getFunctionGraph() {
        return functionGraph;
    }

    private Integer getOutputValue(Graph graph) {
        Node endNode = graph.getNode("End");
        String label = endNode.getEnteringEdge(0).getAttribute("label");
        return utils.getIndexes(label).get(0);
    }

    private Integer getIncrementValue(ArrayList<Graph> subgraphs) {
        return getOutputValue(subgraphs.get(1)) - getOutputValue(subgraphs.get(0));
    }

    private void recursivelyComputeLoopInfo(Node node0, Node node1, Node node2) {
        if (node0.getId().equals("End")) {
            return;
        }
        for (int i = 0; i < node0.getOutDegree(); i++) {
            Edge edge0 = node0.getLeavingEdge(i);
            Edge edge1 = node1.getLeavingEdge(i);
            Edge edge2 = node2.getLeavingEdge(i);
            if (edge0.hasAttribute("loopinfo")) {
                continue;
            }
            String label0 = edge0.getAttribute("label");
            String label1 = edge1.getAttribute("label");
            String label2 = edge2.getAttribute("label");
            ArrayList<Integer> indexes0 = utils.getIndexes(label0);
            ArrayList<Integer> indexes1 = utils.getIndexes(label1);
            ArrayList<Integer> indexes2 = utils.getIndexes(label2);
            if (indexes0.size() > 0) {
                try {
                    LoopInfo loopInfo = getLoopInfo(indexes0, indexes1, indexes2);
                    edge0.setAttribute("loopinfo", loopInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            recursivelyComputeLoopInfo(edge0.getTargetNode(), edge1.getTargetNode(), edge2.getTargetNode());
        }
    }

    private void computeLoopInfoForAllEdges(ArrayList<Graph> subgraphList) {
        Graph subgraph0 = subgraphList.get(0);
        Graph subgraph1 = subgraphList.get(1);
        Graph subgraph2 = subgraphList.get(2);
        Node node0 = subgraph0.getNode("Start");
        Node node1 = subgraph1.getNode("Start");
        Node node2 = subgraph2.getNode("Start");

        recursivelyComputeLoopInfo(node0, node1, node2);

    }

    private LoopInfo getLoopInfo(ArrayList<Integer> indexes0, ArrayList<Integer> indexes1, ArrayList<Integer> indexes2) throws Exception {
        ArrayList<ProgressionType> progressionTypes = new ArrayList<>();
        ArrayList<Integer> ratios = new ArrayList<>();
        ArrayList<Integer> initialValues = new ArrayList<>();
        for (int i = 0; i < indexes0.size(); i++) {
            Integer ratio = indexes1.get(i) - indexes0.get(i);
            initialValues.add(indexes0.get(0));
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
        return new LoopInfo(loopName, ratios, progressionTypes, initialValues);
    }
}
