package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import pt.up.fe.specs.utils.Utils;

import java.util.*;

public class ParallelizeSums implements Algorithm {

    private final int MIN_COUNT = 3;
    private Graph graph;
    private HashMap<Node, Integer> sumSequences = new HashMap<>();
    private boolean performedRotations = false;

    @Override
    public void init(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void compute() {
        Node startNode = graph.getNode("Start");
        int maxLevel = graph.getAttribute("maxlevel");
        for (int starterLevel = 1; starterLevel < maxLevel; starterLevel++) {
            detectSequences(startNode, null, 0, starterLevel);
            rotateGraph();
            clearInSeqAttributes();
        }
        System.out.println("ParallelizeSums half-finished, leveling again");
        if (performedRotations) {
            Algorithm leveling = new Leveling();
            leveling.init(graph);
            leveling.compute();
        }
        System.out.println("ParallelizeSums finished");
    }

    private void clearInSeqAttributes() {
        for (Node n: graph) {
            n.removeAttribute("inSeq");
        }
    }

    private void detectSequences(Node currentNode, Node sequenceStarter, Integer count, Integer minLevel) {
        if (currentNode.getOutDegree() > 0) {
            Edge e = currentNode.getLeavingEdge(0);
            Node child = e.getTargetNode();
            int childLevel = child.getAttribute("level");
            if (Utils.isOperation(child) && childLevel >= minLevel) {
                if (count == 0) {
                    sequenceStarter = child;
                }
                detectSequences(child, sequenceStarter, count + 1, minLevel);
            } else if (count >= MIN_COUNT) {
                if (child.getId().equals("End")) {
                    sumSequences.put(sequenceStarter, count - 1); //there's no need to rotate if the child is an end node
                } else {
                    sumSequences.put(sequenceStarter, count);
                }

                detectSequences(child, null, 0, minLevel);
            } else {
                detectSequences(child, null, 0, minLevel);
            }
        }
    }

    public boolean performedRotations() {
        return performedRotations;
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
        Graphs.copyAttributes(oldEdge, addedEdge);
        addedEdge.setAttribute("pos", pos);
        return addedEdge;
    }

    private void rotateGraph() {
        while(!sumSequences.isEmpty()) {
            performedRotations = true;
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

                    thirdOp.setAttribute("level", (int) thirdOp.getAttribute("level") - 2);
                    fourthNode.setAttribute("level", (int) fourthNode.getAttribute("level") - 1);

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
