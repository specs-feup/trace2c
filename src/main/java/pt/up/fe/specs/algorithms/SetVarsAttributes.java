package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Algorithm dedicated to get the information of variables and store that information
 * as attributes in the nodes.
 *
 * @author CPU TOSH
 */

public class SetVarsAttributes implements Algorithm {

    private Graph graph;

    @Override
    /**
     * Initializes the algorithm
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        this.graph = graph;
    }

    @Override
    /**
     * Goes through all variable nodes and adds attributes to them such as variable names,
     * array dimension, an array flag and array indexes.
     *
     */
    public void compute() {
        // TODO Auto-generated method stub
        String temp_name;

        for (Node node : graph) {
            // System.out.println(n.getId());
            if (!node.hasAttribute("att1")) {
                System.out.println("No att1 error, name : " + node.getId());
            }
            if (node.getInDegree() == 0 && node.getOutDegree() == 0)
                System.out.println("Disconnected node, name : " + node.getId());

            if (Utils.isVar(node)) {
                int arrayDim = 0;
                String label = Utils.getLabel(node);
                if (Utils.isArray(node)) {
                    temp_name = Utils.varNameFromLabel(label);
                    arrayDim = Utils.getArrayDimension(label);
                    node.addAttribute("name", temp_name);
                    node.addAttribute("array", true);
                    node.addAttribute("dim", arrayDim);
                    setIndexes(node);
                } else {
                    node.addAttribute("name", label);
                }

            }

        }
        System.out.println("SetVarsAttributes finished");
    }

    private void setIndexes(Node node) {
        List indexes = Utils.getIndexes(Utils.getLabel(node));
        node.setAttribute("indexes", indexes);
    }





}
