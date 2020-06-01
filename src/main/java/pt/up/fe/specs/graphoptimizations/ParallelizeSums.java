package pt.up.fe.specs.graphoptimizations;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ParallelizeSums implements Algorithm {

    private final int MIN_COUNT = 3;
    private Graph graph;
    private HashMap<Node, Integer> sumSequences = new HashMap<>();

    @Override
    public void init(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void compute() {
        Node startNode = graph.getNode("Start");
        detectSequences(startNode, null, 0);
        rotateGraph();

    }

    private void detectSequences(Node currentNode, Node sequenceStarter, Integer count) {
        for (Edge e: currentNode.getEachLeavingEdge()) {
            Node child = e.getTargetNode();
            if (child.hasAttribute("inSeq")) {
                continue;
            }
            if (child.getAttribute("att1").equals("op") && child.getAttribute("label").equals("+")) {
                if (count == 0) {
                    sequenceStarter = child;
                }
                child.setAttribute("inSeq", true);
                detectSequences(child, sequenceStarter, count + 1);
            } else if (count >= MIN_COUNT) {
                sumSequences.put(sequenceStarter, count);
                detectSequences(child, null, 0);
            } else {
                detectSequences(child, null, 0);
            }

        }
    }



    /**
     * Removes the edge that enters into node n in position pos
     * @param n Node in which the edge enters
     * @param pos Position in which the edge enters the node
     * @return The edge removed
     */
    private Edge removeEnteringEdge(Node n, String pos) {
        for (Edge e: n.getEachEnteringEdge()) {
            if (e.hasAttribute("pos") && e.getAttribute("pos").equals(pos)) {
                return graph.removeEdge(e);
            }
        }

        return graph.removeEdge((Edge) n.getEnteringEdge(0));
    }

    private Edge addNewEdge(Edge oldEdge, Node target, String pos) {
        Node source = oldEdge.getSourceNode();
        String edgeId = "(" + source.getId() + ";" + target.getId() + ")";
        Edge addedEdge = graph.addEdge(edgeId, source, target, true);
        addedEdge.setAttribute("pos", pos);
        Graphs.copyAttributes(oldEdge, addedEdge);
        return addedEdge;
    }

    private void rotateGraph() {
        while(!sumSequences.isEmpty()) {
            HashMap<Node, Integer> newMap = new HashMap<>();
            for (Map.Entry<Node, Integer> starterAndCount : sumSequences.entrySet()) {
                Integer count = starterAndCount.getValue();
                Node starterOp = starterAndCount.getKey();
                if (count >= MIN_COUNT) {
                    Node secondOp = starterOp.getLeavingEdge(0).getTargetNode();
                    Node thirdOp = secondOp.getLeavingEdge(0).getTargetNode();
                    Node fourthNode = thirdOp.getLeavingEdge(0).getTargetNode();

                    Edge e1 = removeEnteringEdge(secondOp, "r");
                    addNewEdge(e1, thirdOp, "l");

                    Edge e3 = removeEnteringEdge(fourthNode, "l");
                    addNewEdge(e3, secondOp, "r");

                    Edge e2 = removeEnteringEdge(thirdOp, "l");
                    addNewEdge(e2, fourthNode, "l");

                    Integer newCount = count - 2;
                    if (newCount >= MIN_COUNT) {
                        newMap.put(secondOp, newCount);
                    }

                    //sumSequences.remove(starterOp);
                }
            }
            sumSequences = newMap;
        }

    }
}
