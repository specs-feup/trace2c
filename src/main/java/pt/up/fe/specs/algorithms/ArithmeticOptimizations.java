package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

import java.util.HashSet;

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
        optimizeMathFunctions();
        optimizeMultiplications();
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
