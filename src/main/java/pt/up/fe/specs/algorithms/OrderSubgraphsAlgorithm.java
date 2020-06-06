package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;

import java.util.ArrayList;




public class OrderSubgraphsAlgorithm implements Algorithm {

    private Graph graph;

    @Override
    public void init(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void compute() {
        ArrayList<Graph> subgraphsList = graph.getAttribute("hyperNode");

    }
}
