package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Algorithm that detects a sequence that generates a given output.
 *
 * @author Afonso
 */
public class DetectBasicSequence implements Algorithm {

    private Graph graph;
    private Graph basicgraph;

    private boolean complete;
    private List<Node> addlist;
    private List<Node> tempaddlist;

    /**
     *
     */
    public DetectBasicSequence() {
        complete = false;
    }

    @Override
    /**
     * Initializes the algorithm. Checks if all outputs have been separated. If true the Algorithm is complete.
     * If  false a new output is selected to be separated.
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub

        this.graph = graph;
        basicgraph = new MultiGraph("basicgraph");
        for (String a : graph.getEachAttributeKey()) {
            basicgraph.addAttribute(a, graph.getAttribute(a));
        }
        addlist = new ArrayList<>();
        tempaddlist = new ArrayList<>();
        int i = 0;

        for (i = 0; i < graph.getNode("End").getInDegree(); i++) {
            if (!graph.getNode("End").getEnteringEdge(i).hasAttribute("mark"))
                break;
        }

        if (i == graph.getNode("End").getInDegree()) {
            complete = true;
            return;
        }

        basicgraph.addNode("End");
        for (String a : graph.getNode("End").getEachAttributeKey())
            basicgraph.getNode("End").addAttribute(a, graph.getNode("End").getAttribute(a).toString());

        graph.getNode("End").getEnteringEdge(i).addAttribute("mark", true);
        copyNode(graph.getNode("End"), graph.getNode("End").getEnteringEdge(i).getNode0(),
                graph.getNode("End").getEnteringEdge(i));
        addlist.clear();
        addlist.addAll(tempaddlist);
        tempaddlist.clear();
    }

    @Override
    /**
     * Main body of the algorithm. Goes up the graph and copies passed node up to the start node.
     */
    public void compute() {
        // TODO Auto-generated method stub

        while (!addlist.isEmpty()) {
            for (Node n : addlist) {
                for (Edge e : n.getEachEnteringEdge()) {
                    if (!e.getNode0().getId().equals("Start")) {
                        copyNode(n, e.getNode0(), e);

                    }
                }
            }

            addlist.clear();
            addlist.addAll(tempaddlist);
            tempaddlist.clear();
        }

        basicgraph.addNode("Start");
        for (String a : graph.getNode("Start").getEachAttributeKey())
            basicgraph.getNode("Start").addAttribute(a, graph.getNode("Start").getAttribute(a).toString());

        for (Node n : basicgraph.getEachNode()) {
            if (n.getInDegree() == 0 && !n.equals(basicgraph.getNode("Start")) && !n.equals(basicgraph.getNode("End")))
                basicgraph.addEdge("begin to:" + n.getId(), basicgraph.getNode("Start"), n, true);
        }
    }

    /**
     * Returns the most recent separated sequence.
     *
     * @return
     */
    public Graph getGraph() {
        return basicgraph;
    }

    /**
     * Copies a node to the subgraph representing the most recent separated sequence.
     *
     * @param n1 Node that is connected to n2 by edge e. Necessary to copy edge e to the new graph. n1 must have been already added.
     * @param n2 Node to copy to new subgraph.
     * @param e  Edge to copy to new subgragh.
     */
    private void copyNode(Node n1, Node n2, Edge e) {
        Node test = basicgraph.getNode(n2.getId());
        if (test == null) {

            basicgraph.addNode(n2.getId());
            for (String a : n2.getEachAttributeKey())
                basicgraph.getNode(n2.getId()).addAttribute(a, n2.getAttribute(a).toString());
            tempaddlist.add(n2);
        }

        if (basicgraph.getEdge(e.getId()) == null) {
            basicgraph.addEdge(e.getId(),
                    basicgraph.getNode(n2.getId()).getIndex(), basicgraph.getNode(n1.getId()).getIndex(), true);
            Graphs.copyAttributes(e, basicgraph.getEdge(e.getId()));

        }
    }

    /**
     * Returns true if all outputs have been separated.
     *
     * @return
     */
    public boolean isComplete() {
        // TODO Auto-generated method stub
        return complete;
    }

}
