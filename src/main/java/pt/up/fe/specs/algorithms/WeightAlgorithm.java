package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.Collection;
import java.util.HashMap;

public class WeightAlgorithm implements Algorithm {
    private Graph graph;
    private HashMap<String, Integer> operationTypeToInt = new HashMap<>();
    private HashMap<String, Integer> nodeTypeToInt = new HashMap<>();
    private HashMap<String, Integer> edgeTypeToInt = new HashMap<>();
    private HashMap<String, Integer> positionToInt = new HashMap<>();

    @Override
    public void init(Graph graph) {
        this.graph = graph;

        operationTypeToInt.put("=", 0);
        operationTypeToInt.put("+", 1);
        operationTypeToInt.put("-", 4);
        operationTypeToInt.put("*", 8);
        operationTypeToInt.put("/", 16);
        operationTypeToInt.put(">", 32);
        operationTypeToInt.put("<", 64);

        nodeTypeToInt.put("nop", 0);
        nodeTypeToInt.put("mux", 32);
        nodeTypeToInt.put("call", 64);

        edgeTypeToInt.put(null, 0);
        edgeTypeToInt.put("var", 10);
        edgeTypeToInt.put("const", 1);

        positionToInt.put(null, 0);
        positionToInt.put("l", 1);
        positionToInt.put("r", 2);
        positionToInt.put("sel", 4);
        positionToInt.put("t", 8);
        positionToInt.put("f", 16);
    }

    @Override
    public void compute() {
        final int k1 = 1000;
        final int k2 = 100;

        for (Node n : graph) {
            String nodeType = n.getAttribute("att1");
            Integer typeWeight = nodeType.equals("op") ? operationTypeToInt.get(n.getAttribute("label")) : nodeTypeToInt.get(nodeType);
            Integer nodeLevel = n.getAttribute("level");
            int sequential_weight = k2 * typeWeight + n.getDegree() * typeWeight;

            for (Edge e: n.getEachEnteringEdge()) {
                Integer edgeWeight = edgeTypeToInt.get(e.getAttribute("att1"));
                edgeWeight += positionToInt.get(e.getAttribute("pos"));
                if (e.hasAttribute("array")) {
                    edgeWeight += Integer.valueOf(e.getAttribute("name").hashCode()) % 67;
                }
                if (e.hasAttribute("dim")) {
                    edgeWeight += (int) e.getAttribute("dim");
                }
                sequential_weight += edgeWeight;
            }
            int parallel_weight = k1 * nodeLevel + sequential_weight;

            n.setAttribute("parallel_weight", parallel_weight);
            n.setAttribute("sequential_weight", sequential_weight);
        }
    }
}
