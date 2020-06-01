package pt.up.fe.specs.graphoptimizations;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.MultiGraph;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.VarLoc;
import pt.up.fe.specs.utils.AddStartAndEnd;
import pt.up.fe.specs.utils.SubgraphSearchAux;
import pt.up.fe.specs.utils.Utils;

import java.util.*;

public class AllSubgraphsAlgorithm implements Algorithm {
    private CInfo cInfo;
    private Utils utils = new Utils();
    private Graph graph;
    private HashMap<String, Integer> varCounter = new HashMap<>();
    private HashMap<String, String> varsToTypes = new HashMap<>();
    private int edgeCounter = 0;

    @Override
    public void init(Graph graph) {
        this.graph = graph;
        cInfo = graph.getAttribute("info");
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
     * Uses an heuristic to find a subgraph that 
     * @param subgraphs
     * @return
     */
    private List<HashSet<Node>> getMostRepeatableSubgraph(HashMap<Integer, List<HashSet<Node>>> subgraphs) {
        int maxComplexity = 0;
        List<HashSet<Node>> mostRepeatableSubgraph = new ArrayList<>();
        for ( List<HashSet<Node>> listOfSubgraphs : subgraphs.values()) {
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
        HashMap<Integer, List<HashSet<Node>>> allParallelSubgraphs = new HashMap<>(); //maps weight to subgraphs
        HashMap<Integer, List<HashSet<Node>>> allSequentialSubgraphs = new HashMap<>(); //maps weight to subgraphs
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
        Node hyperNode = createHyperNode();
        List<HashSet<Node>> parallelSubgraphs = getMostRepeatableSubgraph(allParallelSubgraphs);
        moveNodesToSubgraphList(parallelSubgraphs, hyperNode);
        updateLocalInfo();
    }

    void updateLocalInfo() {
        List<VarLoc> localInfo = cInfo.getLocalInfo();
        varCounter.forEach( (label,count) -> {
            List<Integer> indexes = new ArrayList<>();
            indexes.add(count);
            localInfo.add(new VarLoc(varsToTypes.get(label), label, true, 1, indexes));
        });
    }

    /**
     * If the output of all subgraphs is a single var that has the same name, then it should be replaced by a temporary
     * local array, so that the the subgraphs can be ordered and the folding can be completed.
     * @param commonSubgraphs
     * @param hyperNode
     */
    void moveNodesToSubgraphList(List<HashSet<Node>> commonSubgraphs, Node hyperNode) {
        ArrayList<MultiGraph> subgraphList = new ArrayList<>();
        commonSubgraphs.forEach(nodes -> {
            MultiGraph g = new MultiGraph("basicgraph");
            AddStartAndEnd addStartAndEnd = new AddStartAndEnd();
            addStartAndEnd.init(g);
            addStartAndEnd.compute();
            nodes.forEach(node -> {
                copyNodeAndEdges(node, g, nodes, hyperNode);
                graph.removeNode(node);
            });

            subgraphList.add(g);
        });
        graph.addAttribute("HyperNode", subgraphList);
    }

    Node createHyperNode() {
        Node hyperNode = graph.addNode("HyperNode");
        hyperNode.addAttribute("label", "hyper1");
        hyperNode.addAttribute("att1", "hyper");
        hyperNode.addAttribute("name", "hyper1");
        return hyperNode;
    }

    void copyNodeAndEdges(Node node, Graph subgraph, HashSet<Node> nodesToAdd, Node hyperNode) {
        Node n = subgraph.getNode(node.getId());
        if (n == null) {
            n = subgraph.addNode(node.getId());
            Graphs.copyAttributes(node, n);
        }

        Node finalN = n;
        node.getEachEnteringEdge().forEach(edge -> {
            Node source = edge.getSourceNode();
            Node newSource = subgraph.getNode(source.getId());
            if (nodesToAdd.contains(source) && newSource == null) {
                newSource = subgraph.addNode(source.getId());
                Graphs.copyAttributes(source, newSource);
            }

            if (newSource == null) {
                Edge mainGraphToHyperNode = graph.addEdge(source.getId() + "_" + edgeCounter++ + "->" + hyperNode.getId(), source, hyperNode, true);
                Graphs.copyAttributes(edge, mainGraphToHyperNode);
                newSource = subgraph.getNode("Start");
                Graphs.copyAttributes(source, newSource);
            }
            Edge newEdge = subgraph.addEdge(edge.getId(), newSource, finalN, true);
            Graphs.copyAttributes(edge, newEdge);
        });
        node.getEachLeavingEdge().forEach(edge -> {
            Node target = edge.getTargetNode();
            if (!nodesToAdd.contains(target)) {
                //transformScalarIntoArray(edge);
                Edge hyperNodeToMainGraph = graph.addEdge(hyperNode.getId() + "_" + edgeCounter++ + "->" + target.getId(), hyperNode, target, true);
                Graphs.copyAttributes(edge, hyperNodeToMainGraph);
                Node newTarget = subgraph.getNode("End");
                Edge newEdge = subgraph.addEdge(edge.getId(), finalN, newTarget, true);
                Graphs.copyAttributes(edge, newEdge);
            }
        });
    }

    private void transformScalarIntoArray(Edge edge) {
        String label = edge.getAttribute("label");
        if (!utils.isArray(label)) {
            varsToTypes.putIfAbsent(label, edge.getAttribute("att3"));
            int count = varCounter.getOrDefault(label, 0);
            String newLabel = label + "[" + count + "]";
            edge.setAttribute("label", newLabel);
            varCounter.put(label, count + 1);
        }
    }
}
