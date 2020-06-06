package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.utils.SubgraphSearchAux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Calculates all possible subgraphs and gives a weight to each subgraph.
 * Subgraphs are clustered by weight.
 */
public class AllSubgraphsAlgorithm implements Algorithm {
    private Graph graph;
    private HashMap<Integer, List<HashSet<Node>>> allParallelSubgraphs = new HashMap<>(); //maps weight to subgraphs
    private HashMap<Integer, List<HashSet<Node>>> allSequentialSubgraphs = new HashMap<>(); //maps weight to subgraphs

    @Override
    public void init(Graph graph) {
        this.graph = graph;
        allParallelSubgraphs.clear();
        allSequentialSubgraphs.clear();
    }

    /**
     * Returns weight of the new subgraph
     * @param n
     * @return
     */
    private void addNodeToTempSubgraph(Node n, HashSet<Node> addedNodes, SubgraphSearchAux searchAux) {

        addedNodes.add(n);
        searchAux.addNode(n);
        HashSet<Node> connections = new HashSet<>();
        n.getEachLeavingEdge().forEach(e -> connections.add(e.getTargetNode()));
        n.getEachEnteringEdge().forEach(e -> connections.add(e.getSourceNode()));
        for (Node node: connections) {
            Integer nodeLevel = node.getAttribute("level");
            if (nodeLevel >= searchAux.getMinLevel() && nodeLevel <= searchAux.getMaxLevel() && !searchAux.wasNodeVisited(node)) {
                addNodeToTempSubgraph(node, addedNodes, searchAux);
            }
        }

    }

    /**
     * Uses an heuristic to find the most repeatable parallel subgraph
     * @param
     * @return
     */
    public List<HashSet<Node>> getMostRepeatableParallelSubgraph() {
        int maxComplexity = 0;
        List<HashSet<Node>> mostRepeatableSubgraph = new ArrayList<>();
        for ( List<HashSet<Node>> listOfSubgraphs : allParallelSubgraphs.values()) {
            int complexity = listOfSubgraphs.size() * listOfSubgraphs.get(0).size();
            if (listOfSubgraphs.size() > 1 && complexity > maxComplexity) {
                maxComplexity = listOfSubgraphs.size() * listOfSubgraphs.get(0).size();
                mostRepeatableSubgraph = listOfSubgraphs;
            }
        }
        return mostRepeatableSubgraph;
    }

    @Override
    public void compute() {

        List<List<Node>> levelGraph = graph.getAttribute("levelgraph");
        int maxLevels = graph.getAttribute("maxlevel");
        for (int i = 1; i < maxLevels; i++) {
            for (int j = i; j < maxLevels; j++) {
                HashSet<Node> addedNodes = new HashSet<>();
                for (Node n: levelGraph.get(i)) {
                    if (!addedNodes.contains(n)) {
                        SubgraphSearchAux searchAux = new SubgraphSearchAux(i,j);
                        addNodeToTempSubgraph(n, addedNodes, searchAux);
                        if (searchAux.getMaxLevelReached() == j) {
                            allParallelSubgraphs.putIfAbsent(searchAux.getSubgraphParallelWeight(), new ArrayList<>());
                            allParallelSubgraphs.get(searchAux.getSubgraphParallelWeight()).add(searchAux.getTemporarySubgraph());
                            allSequentialSubgraphs.putIfAbsent(searchAux.getSubgraphSequentialWeight(), new ArrayList<>());
                            allSequentialSubgraphs.get(searchAux.getSubgraphSequentialWeight()).add(searchAux.getTemporarySubgraph());
                        }
                    }
                }
            }
        }
    }
}
