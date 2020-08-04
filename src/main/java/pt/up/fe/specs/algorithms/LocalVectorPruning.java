package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.utils.Utils;

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

        for (Node n : graph) {
            if (Utils.isLocalVar(n) && Utils.isArray(n)) {
                label = n.getAttribute("label").toString();
                label = label.replace("[", "");
                label = label.replace("]", "");
                n.setAttribute("label", label);
            }
        }
        System.out.println("LocalVectorPruning finished");
    }

}
