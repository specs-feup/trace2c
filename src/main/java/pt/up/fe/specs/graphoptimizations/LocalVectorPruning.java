package pt.up.fe.specs.graphoptimizations;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 * Algorithm that replaces local arrays with unique array variables.
 * @author CPU TOSH
 *
 */
public class LocalVectorPruning implements Algorithm {
    Graph graph;

    @Override
    /**
     * Initializes algorithm.
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        this.graph = graph;
    }

    @Override
    /**
     * Changes the labels of all local vectors from array[] to array
     */
    public void compute() {
        // TODO Auto-generated method stub
        boolean isArray;
        String label;

        System.out.println("Local vectors pruned due to unrolling");
        System.out.println("Currently all local vectors pruned");
        for (Node n : graph) {
            // Later maybe from attribute
            if (n.hasAttribute("att2")) {
                if (n.getAttribute("att2").toString().equals("loc")) {
                    isArray = n.getAttribute("label").toString().contains("[");
                    if (isArray) {
                        label = n.getAttribute("label").toString();
                        label = label.replace("[", "");
                        label = label.replace("]", "");
                        n.setAttribute("label", label);
                    }
                }
            }
        }

    }

}
