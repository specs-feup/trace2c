package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.utils.Utils;

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

        operationTypeToInt.put("+", 1);
        operationTypeToInt.put("-", 2);
        operationTypeToInt.put("*", 3);
        operationTypeToInt.put("/", 5);
        operationTypeToInt.put(">", 7);
        operationTypeToInt.put("<", 11);
        operationTypeToInt.put("==", 13);

        nodeTypeToInt.put("nop", 17);
        nodeTypeToInt.put("mux", 19);
        nodeTypeToInt.put("call", 23);
        nodeTypeToInt.put("assignment", 29);
        nodeTypeToInt.put("arrayAccess",31);
        nodeTypeToInt.put("complexAssignment", 37);

        edgeTypeToInt.put(null, 43);
        edgeTypeToInt.put("var", 47);
        edgeTypeToInt.put("const", 53);

        positionToInt.put("", 59);
        positionToInt.put("l", 61);
        positionToInt.put("r", 67);
        positionToInt.put("sel", 71);
        positionToInt.put("t", 73);
        positionToInt.put("f", 79);
    }

    @Override
    public void compute() {
        System.out.println("Computing graph weight");
        final int k1 = 1000;
        final int k2 = 100;

        for (Node n : graph) {
            String nodeType = n.getAttribute("att1");
            Integer typeWeight = Utils.isOperation(n) ? operationTypeToInt.get(Utils.getLabel(n)) : nodeTypeToInt.get(nodeType);
            Integer nodeLevel = Utils.getLevel(n);
            if (typeWeight == null) {
                System.err.println("Null type weight for node " + n.getId());
            }
            if (nodeLevel == null) {
                System.err.println("Null level for node " + n.getId());
            }
            int sequential_weight = k2 * typeWeight * n.getDegree();

            for (Edge e: n.getEachEnteringEdge()) {
                Integer edgeWeight = edgeTypeToInt.get(e.getAttribute("att1"));
                edgeWeight += positionToInt.get(Utils.getPos(e));
                if (Utils.isArray(e)) {
                    edgeWeight += Integer.valueOf(Utils.getName(e).hashCode()) % 83;
                }
                if (e.hasAttribute("dim")) {
                    edgeWeight = edgeWeight * (int) e.getAttribute("dim");
                }
                sequential_weight += edgeWeight;
            }
            int parallel_weight = k1 * nodeLevel + sequential_weight;

            n.setAttribute("parallel_weight", parallel_weight);
            n.setAttribute("sequential_weight", sequential_weight);
        }
        System.out.println("Weights computation finished");
    }
}
