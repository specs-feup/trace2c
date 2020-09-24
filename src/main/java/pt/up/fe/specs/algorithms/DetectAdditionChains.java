package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * New algorithm to detect addition chains starting in a given level
 */
public class DetectAdditionChains implements Algorithm {
    private final int startLevel;
    private HashMap<Node, LinkedList<Node>> chains = new HashMap<>(); // maps node heads to node sequences
    private HashMap<Node, Node> chainHeads = new HashMap<>(); // maps node to their sequence head
    private Queue<Node> nodesToAnalyze = new LinkedList<>();
    private List<HashSet<Node>> levelGraph;
    private final int MIN_COUNT = 4;

    /**
     * Level to start searching for chains
     * @param startLevel
     */
    public DetectAdditionChains(int startLevel) {
        this.startLevel = startLevel;
    }
    
    @Override
    public void init(Graph graph) {
        this.levelGraph = Utils.getLevelGraph(graph);
    }

    public List<LinkedList<Node>> getChains() {

        return chains.values().stream().filter(chain -> chain.size() >= MIN_COUNT).collect(Collectors.toList());
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
