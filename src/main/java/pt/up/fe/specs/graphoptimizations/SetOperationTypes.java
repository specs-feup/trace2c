package pt.up.fe.specs.graphoptimizations;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class SetOperationTypes implements Algorithm {
    Graph graph;
    @Override
    public void init(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void compute() {

        setOperationType(graph.getNode("End"));
    }

    private void setOperationType(Node n) {
        String outputType = "int";
        for (Edge e: n.getEachEnteringEdge()) {
            Node previousNode = e.getSourceNode();
            setOperationType(previousNode);
            if (n.getAttribute("att1").equals("op")){
                String previousNodeType = previousNode.getAttribute("att3");
                if (previousNodeType.equals("float") && outputType.equals("int")) {
                    outputType = "float";
                } else if (previousNodeType.equals("double")) {
                    outputType = "double";
                }
            }

        }
        n.setAttribute("att3", outputType);
    }
}
