package pt.up.fe.specs.utils;


import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class AddStartAndEnd implements Algorithm {

    private Graph g;
    @Override
    public void init(Graph graph) {
        g = graph;
    }

    @Override
    public void compute() {
        /**
         * Add Start and End nodes to beginning and end of the dataflow.
         *
         * @param g
         *            Graph to change.
         */
            Node startNode = g.getNode("Start");
            Node endNode = g.getNode("End");
            if (startNode == null) {
                startNode = g.addNode("Start");
                startNode.addAttribute("label", "start");
                startNode.addAttribute("att1", "nop");
            }
            if (endNode == null) {
                endNode = g.addNode("End");
                endNode.addAttribute("label", "end");
                endNode.addAttribute("att1", "nop");
            }
            for (Node n : g.getEachNode()) {
                if (n.getInDegree() == 0 && !n.equals(startNode) && !n.equals(endNode))
                    g.addEdge("begin to:" + n.getId(), startNode, n, true);
                else if (n.getOutDegree() == 0 && !n.equals(endNode) && !n.equals(startNode))
                    g.addEdge("end from:" + n.getId(), n, endNode, true);
            }

    }
}
