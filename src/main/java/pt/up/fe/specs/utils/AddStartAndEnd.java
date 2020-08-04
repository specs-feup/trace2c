package pt.up.fe.specs.utils;


import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class AddStartAndEnd implements Algorithm {

    private Graph graph;

    @Override
    public void init(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void compute() {
        /**
         * Add Start and End nodes to beginning and end of the dataflow.
         *
         * @param g
         *            Graph to change.
         */
        Node startNode = graph.getNode("Start");
        Node endNode = graph.getNode("End");
        if (startNode == null) {
            startNode = graph.addNode("Start");
            startNode.addAttribute("label", "start");
            startNode.addAttribute("att1", "nop");
        }
        if (endNode == null) {
            endNode = graph.addNode("End");
            endNode.addAttribute("label", "end");
            endNode.addAttribute("att1", "nop");
        }
        for (Node n : graph.getEachNode()) {
            if (n.getInDegree() == 0 && !n.equals(startNode) && !n.equals(endNode))
                graph.addEdge("begin to:" + n.getId(), startNode, n, true);
            else if (n.getOutDegree() == 0 && !n.equals(endNode) && !n.equals(startNode))
                graph.addEdge("end from:" + n.getId(), n, endNode, true);
        }
        System.out.println("AddStartAndEnd finished on graph " + graph.getId());
    }
}
