package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ArithmeticOptimizations implements Algorithm {

    private Graph graph;
    private HashSet<String> mathFunctions = new HashSet<>();

    @Override
    public void init(Graph graph) {
        this.graph = graph;
        mathFunctions.add("exp");
        mathFunctions.add("sqrt");
        mathFunctions.add("pow");
        mathFunctions.add("log");
        mathFunctions.add("log10");
        mathFunctions.add("abs");
        mathFunctions.add("cos");
        mathFunctions.add("sin");
    }

    @Override
    public void compute() {
        System.out.println("Starting arithmetic optimizations for graph: " + graph.getId());
        optimizeMathFunctions();
        optimizeMultiplications();
        if (graph.hasAttribute("subgraphs")) {
            for (Graph subgraph : (ArrayList<Graph>) graph.getAttribute("subgraphs")) {
                Algorithm algorithm = new ArithmeticOptimizations();
                algorithm.init(subgraph);
                algorithm.compute();
            }
        }
        System.out.println("Arithmetic optimizations complete");
    }

    /**
     * If possible, change multiplications into shifts
     */
    private void optimizeMultiplications() {
    }

    /**
     * Replaces double precision math.h functions by single precision ones.
     * NOT WORKING
     */
    private void optimizeMathFunctions() {
        for (Edge e: graph.getEachEdge()) {
            if (e.hasAttribute("mod")) {
                String mod = e.getAttribute("mod");
                int modFirstParenthesis = mod.indexOf("(");
                mod = mod.substring(0, modFirstParenthesis);
                if (mathFunctions.contains(mod)) {
                    e.setAttribute("mod",  mod+"f(");
                }
            }
        }
    }
}
