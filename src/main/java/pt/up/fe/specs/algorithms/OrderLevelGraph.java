package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.utils.NodeComparator;

import java.util.List;

public class OrderLevelGraph implements Algorithm {
    List<List<Node>> levelGraph;
    private Graph graph;

    @Override
    public void init(Graph graph) {
        this.graph = graph;
        levelGraph = graph.getAttribute("levelgraph");
    }

    @Override
    public void compute() {
        System.out.println("Starting order level graph on graph: " + graph.getId());
        for (List<Node> nodes : levelGraph) {
            nodes.sort(new NodeComparator());
        }
        if (graph.hasAttribute("subgraphs")) {
            List<Graph> subgraphs = graph.getAttribute("subgraphs");
            for (Graph subgraph: subgraphs) {
                Algorithm algorithm = new OrderLevelGraph();
                algorithm.init(subgraph);
                algorithm.compute();
            }

        }
        System.out.println("Finished order level graph on graph: " + graph.getId());

    }
}
