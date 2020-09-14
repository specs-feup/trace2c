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
            detectSequences(startNode, starterLevel);
            if (sumSequences.isEmpty()) break;
            rotateGraph();

        }
        System.out.println("ParallelizeSums half-finished, leveling again");
        if (performedRotations) {
            Algorithm leveling = new Leveling();
            leveling.init(graph);
            leveling.compute();
        }
        System.out.println("ParallelizeSums finished");
    }


    private void detectSequences(Node n, Integer minLevel) {
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>(); // efficient lookup
        Node sequenceStarter = null;
        int count = 0;

        stack.push(n);
        while(!stack.isEmpty()) {
            Node node = stack.pop();
            if (!visited.contains(node)) {
                visited.add(node);
                Node child = node.getLeavingEdge(0).getTargetNode();
                Integer nodeLevel = Utils.getLevel(node);
                if (Utils.isSumOperation(node) && nodeLevel >= minLevel) {
                    if (count == 0) {
                        sequenceStarter = node;
                    }
                    count++;

                } else if (count >= MIN_COUNT) {
                    if (Utils.isEndNode(child)) {
                        sumSequences.put(sequenceStarter, count - 1);
                    } else {
                        sumSequences.put(sequenceStarter, count);
                    }
                    count = 0;
                }
                if (Utils.isEndNode(child)) {
                    if (count >= MIN_COUNT) {
                        sumSequences.put(sequenceStarter, count - 1);
                    }
                }
                else if (child.getOutDegree() > 0) {
                    stack.push(child);
                }

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
