package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.utils.Utils;

import java.util.*;

/**
 * New algorithm to detect addition chains starting in a given level
 * O(NodesInStartingLevel +
 */
public class DetectAdditionChains implements Algorithm {
    private final int startLevel;
    private Graph graph;
    private HashMap<Node, LinkedList<Node>> chains = new HashMap<>(); // maps node heads to node sequences
    private HashMap<Node, Node> chainHeads = new HashMap<>(); // maps node to their sequence head
    private Queue<Node> nodesToAnalyze = new LinkedList<>();
    private List<List<Node>> levelGraph;

    /**
     * Level to start searching for chains
     * @param startLevel
     */
    public DetectAdditionChains(int startLevel) {
        this.startLevel = startLevel;
    }
    
    @Override
    public void init(Graph graph) {
        this.graph = graph;
        this.levelGraph = Utils.getLevelGraph(graph);
    }

    public HashMap<Node, LinkedList<Node>> getChains() {
        return chains;
    }

    @Override
    public void compute() {
        // initialize chains
        for (Node node: levelGraph.get(startLevel)) {
            if (Utils.isSumOperation(node)) {
                chainHeads.put(node, node);
                LinkedList<Node> chain = new LinkedList<>();
                chain.add(node);

                chains.put(node, chain);
                nodesToAnalyze.add(node);
            }
        }

        while (!nodesToAnalyze.isEmpty()) {
            Node parent = nodesToAnalyze.poll();
            Node chainHead = chainHeads.get(parent);
            for (Node child: Utils.getChildren(parent)) {
                if (Utils.isSumOperation(child)) {
                    chainHeads.put(child, chainHead);
                    chains.get(chainHead).add(child);
                    nodesToAnalyze.add(child);
                }
            }
        }


    }
}
