package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.utils.NodeComparator;
import pt.up.fe.specs.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class OrderLevelGraph implements Algorithm {
    List<HashSet<Node>> levelGraph;
    private Graph graph;

    @Override
    public void init(Graph graph) {
        this.graph = graph;
        levelGraph = Utils.getLevelGraph(graph);
    }

    @Override
    public void compute() {
        List<List<Node>> sortedLevelGraph = new ArrayList<>();
        System.out.println("Starting order level graph on graph: " + graph.getId());
        for (HashSet<Node> nodes : levelGraph) {
            List<Node> nodesInLevel = nodes.stream().collect(Collectors.toList());
            nodesInLevel.sort(new NodeComparator());
            sortedLevelGraph.add(nodesInLevel);
        }
        Utils.setSortedLevelGraph(graph, sortedLevelGraph);
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
