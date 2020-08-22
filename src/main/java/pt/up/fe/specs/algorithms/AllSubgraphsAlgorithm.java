package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.Config;
import pt.up.fe.specs.utils.Var;
import pt.up.fe.specs.utils.SubgraphSearchAux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Calculates all possible subgraphs and gives a weight to each subgraph.
 * Subgraphs are clustered by weight.
 */
public class AllSubgraphsAlgorithm implements Algorithm {
    private int maxNodesPerSubgraph;
    private int maxFoldLevels;
    private int minFoldLevels;
    private Graph graph;
    private final HashMap<Integer, List<HashSet<Node>>> allParallelSubgraphs = new HashMap<>(); //maps weight to subgraphs
    private final HashMap<Integer, List<HashSet<Node>>> allSequentialSubgraphs = new HashMap<>(); //maps weight to subgraphs
    private List<Var> arrayInputs;


    @Override
    public void init(Graph graph) {
        this.graph = graph;
        allParallelSubgraphs.clear();
        allSequentialSubgraphs.clear();
        CInfo info = this.graph.getAttribute("info");
        arrayInputs = info.getInputs().stream().filter(Var::isArray).collect(Collectors.toList());
        maxFoldLevels = Config.getMaxFoldLevels();
        minFoldLevels = Config.getMinFoldLevels();
        maxNodesPerSubgraph = Config.getMaxNodesPerSubgraph();
    }


    /**
     * @param n
     * @param addedNodes
     * @param searchAux
     */
    private void addNodeToTempSubgraph(Node n, HashSet<Node> addedNodes, SubgraphSearchAux searchAux) {

        addedNodes.add(n);
        searchAux.addNode(n);
        HashSet<Node> connections = new HashSet<>();
        n.getEachLeavingEdge().forEach(e -> connections.add(e.getTargetNode()));
        n.getEachEnteringEdge().forEach(e -> connections.add(e.getSourceNode()));
        for (Node node : connections) {
            Integer nodeLevel = node.getAttribute("level");
            if (nodeLevel >= searchAux.getMinLevel() && nodeLevel <= searchAux.getMaxLevel() && !searchAux.wasNodeVisited(node)) {
                addNodeToTempSubgraph(node, addedNodes, searchAux);
            }
        }

    }

    /**
     * Uses an heuristic to find the most repeatable parallel subgraph
     *
     * @return The most repeatable parallel subgraph
     */
    private List<HashSet<Node>> getMostRepeatableParallelSubgraph() {
        int maxScore = 0;
        List<HashSet<Node>> mostRepeatableSubgraph = new ArrayList<>();
        List<List<HashSet<Node>>> filteredSubgraphs;
        filteredSubgraphs = allParallelSubgraphs.values().stream()
                .filter(clusters -> clusters.size() > 2 && clusters.get(0).size() < maxNodesPerSubgraph)
                .collect(Collectors.toList());
        if (Config.isSubgraphRepeatsSet()) {
            filteredSubgraphs = filteredSubgraphs.stream()
                    .filter(clusters -> clusters.size() == Config.getSubgraphRepeats())
                    .collect(Collectors.toList());
        }



        for (List<HashSet<Node>> listOfSubgraphs : filteredSubgraphs) {
            int numberOfParallelSubgraphs = listOfSubgraphs.size();
            int score = numberOfParallelSubgraphs * listOfSubgraphs.get(0).size();
            for (Var input : arrayInputs) {
                if (input.getSizes().get(0) % numberOfParallelSubgraphs == 0) {
                    score = score * 2;
                }
            }
            if (score > maxScore) {
                maxScore = score;
                mostRepeatableSubgraph = listOfSubgraphs;
            }

        }
        System.out.println("Best parallel subgraph repeats " + mostRepeatableSubgraph.size() + " times and has " + mostRepeatableSubgraph.get(0).size() + " repeatable nodes");
        return mostRepeatableSubgraph;
    }

    private List<HashSet<Node>> getMostRepeatableSequentialSubgraph() {
        int maxScore = 0;
        List<HashSet<Node>> mostRepeatableSubgraph = new ArrayList<>();

        for (List<HashSet<Node>> listOfSubgraphs : allSequentialSubgraphs.values()) {
            int numberOfSequentialSubgraphs = listOfSubgraphs.size();
            if (listOfSubgraphs.get(0).size() < maxNodesPerSubgraph && numberOfSequentialSubgraphs > 2) {
                int score = numberOfSequentialSubgraphs * listOfSubgraphs.get(0).size();
                if (score > maxScore) {
                    maxScore = score;
                    mostRepeatableSubgraph = listOfSubgraphs;
                }
            }
        }
        System.out.println("Best sequential subgraph repeats " + mostRepeatableSubgraph.size() + " times and has " + mostRepeatableSubgraph.get(0).size() + " repeatable nodes");
        return mostRepeatableSubgraph;
    }

    @Override
    public void compute() {
        System.out.println("All subgraphs algorithm is starting");
        List<List<Node>> levelGraph = graph.getAttribute("levelgraph");
        int graphMaxLevel = graph.getAttribute("maxlevel");
        System.out.println("Max Level: " + graphMaxLevel);
        for (int startLevel = 1; startLevel < graphMaxLevel; startLevel++) {
            int maxLastLevel = Math.min(graphMaxLevel, startLevel + maxFoldLevels);
            for (int lastLevel = startLevel + minFoldLevels - 1; lastLevel < maxLastLevel; lastLevel++) {
                HashSet<Node> addedNodes = new HashSet<>();
                for (Node n : levelGraph.get(startLevel)) {
                    if (!addedNodes.contains(n)) {
                        SubgraphSearchAux searchAux = new SubgraphSearchAux(startLevel, lastLevel);
                        addNodeToTempSubgraph(n, addedNodes, searchAux);
                        if (searchAux.getMaxLevelReached() == lastLevel) {
                            allParallelSubgraphs.putIfAbsent(searchAux.getSubgraphParallelWeight(), new ArrayList<>());
                            allParallelSubgraphs.get(searchAux.getSubgraphParallelWeight()).add(searchAux.getTemporarySubgraph());
                            allSequentialSubgraphs.putIfAbsent(searchAux.getSubgraphSequentialWeight(), new ArrayList<>());
                            allSequentialSubgraphs.get(searchAux.getSubgraphSequentialWeight()).add(searchAux.getTemporarySubgraph());
                        }
                    }
                }
            }
        }
        graph.setAttribute("bestParallelClusters", getMostRepeatableParallelSubgraph());
        graph.setAttribute("bestSequentialClusters", getMostRepeatableSequentialSubgraph());
        System.out.println("All subgraphs algorithm finished");
    }
}
