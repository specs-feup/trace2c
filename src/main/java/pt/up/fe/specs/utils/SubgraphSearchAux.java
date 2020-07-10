package pt.up.fe.specs.utils;

import org.graphstream.graph.Node;

import java.util.HashSet;

public class SubgraphSearchAux {
    private Integer subgraphParallelWeight;
    private Integer subgraphSequentialWeight;
    private Integer maxLevelReached;
    private Integer minLevel;
    private Integer maxLevel;
    private HashSet<Node> temporarySubgraph;

    public SubgraphSearchAux (Integer minLevel, Integer maxLevel) {
        temporarySubgraph = new HashSet<>();
        subgraphParallelWeight = 0;
        subgraphSequentialWeight = 0;
        maxLevelReached = minLevel;
        this.setMinLevel(minLevel);
        this.setMaxLevel(maxLevel);
    }

    public HashSet<Node> getTemporarySubgraph() {
        return temporarySubgraph;
    }

    public boolean wasNodeVisited(Node n) {
        return temporarySubgraph.contains(n);
    }

    public void addNode(Node n) {
        if (n == null) {
            System.out.println("Subgraph search failing due to null Node");
        }
        temporarySubgraph.add(n);
        if (n.hasAttribute("parallel_weight")) {
            subgraphParallelWeight += (Integer) n.getAttribute("parallel_weight");
            subgraphSequentialWeight += (Integer) n.getAttribute("sequential_weight");
        } else {
            subgraphParallelWeight += 1;
            subgraphSequentialWeight += 1;
        }

        Integer nodeLevel = n.getAttribute("level");
        if (nodeLevel > maxLevelReached) {
            maxLevelReached = nodeLevel;
        }
    }

    public Integer getSubgraphParallelWeight() {
        return subgraphParallelWeight;
    }

    public Integer getSubgraphSequentialWeight() {
        return subgraphSequentialWeight;
    }

    public Integer getMaxLevelReached() {
        return maxLevelReached;
    }

    public Integer getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(Integer minLevel) {
        this.minLevel = minLevel;
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
    }
}
