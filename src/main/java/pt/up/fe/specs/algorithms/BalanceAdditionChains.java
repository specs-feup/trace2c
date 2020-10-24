package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import pt.up.fe.specs.utils.Utils;

import java.util.*;

public class BalanceAdditionChains implements Algorithm {

    private final int MIN_COUNT = 4;
    private Graph graph;

    @Override
    public void init(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void compute() {
        System.out.println("Starting BalanceAdditionChains");
        int maxLevel = graph.getAttribute("maxlevel");
        for (int starterLevel = 1; starterLevel < maxLevel; starterLevel++) {
            DetectAdditionChains detectAdditionChains = new DetectAdditionChains(starterLevel);
            detectAdditionChains.init(graph);
            detectAdditionChains.compute();
            List<LinkedList<Node>> chains = detectAdditionChains.getChains();
            if (!chains.isEmpty()) {
                rotateGraph(chains);
                Leveling leveling = new Leveling();
                leveling.init(graph);
                leveling.compute();
                maxLevel = Utils.getMaxLevel(graph);
            }
        }
        System.out.println("BalanceAdditionChains half-finished, leveling again");
        System.out.println("BalanceAdditionChains finished");
    }



    /**
     * Removes the edge that enters into node n in position pos
     *
     * @param n   Node in which the edge enters
     * @param pos Position in which the edge enters the node
     * @return The edge removed
     */
    private Edge removeEnteringEdge(Node n, String pos) {
        for (Edge e : n.getEachEnteringEdge()) {
            if (Utils.getPos(e).equals(pos)) {
                return graph.removeEdge(e);
            }
        }

        return graph.removeEdge((Edge) n.getEnteringEdge(0));
    }

    private Edge addNewEdge(Edge oldEdge, Node target, String pos) {
        Node source = oldEdge.getSourceNode();
        String edgeId = "(" + source.getId() + ";" + target.getId() + ")";
        Edge addedEdge = graph.addEdge(edgeId, source, target, true);
        Graphs.copyAttributes(oldEdge, addedEdge);
        addedEdge.setAttribute("pos", pos);
        return addedEdge;
    }

    private void rotateGraph(List<LinkedList<Node>> chains) {
        //List<HashSet<Node>> levelGraph = Utils.getLevelGraph(graph);
        for (LinkedList<Node> chain : chains) {
            while (chain.size() >= MIN_COUNT) {
                Node secondOp = chain.get(1);
                Node thirdOp = chain.get(2);
                Node fourthOp = chain.get(3);

                Edge e1 = removeEnteringEdge(secondOp, "r");
                Edge e2 = removeEnteringEdge(thirdOp, "l");
                Edge e3 = removeEnteringEdge(fourthOp, "l");

                addNewEdge(e1, thirdOp, "l");
                addNewEdge(e2, fourthOp, "l");
                addNewEdge(e3, secondOp, "r");



                chain.remove(2);
                chain.removeFirst();
            }

        }

    }
}
