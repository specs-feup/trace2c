package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import java.util.HashMap;
import java.util.Map;


/**
 * Algorithm that detects the variable that is most often used sequentially.
 * @author CPU TOSH
 *
 */
public class DetectMostWrittenSeqVariable implements Algorithm {

    HashMap<String, Integer> varCountFinal = new HashMap<>();
    Node start = null;

    /**
     * 
     */
    public DetectMostWrittenSeqVariable() {
        // TODO Auto-generated constructor stub
    }

    @Override
    /**
     * Initialize the algorithm. Algorithm starts from the end of the graph.
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        Node end = graph.getNode("End");
        do {
            end = end.getEnteringEdge(0).getNode0();
        } while (!end.getAttribute("att1").toString().equals("op"));
        start = end;

    }

    @Override
    /**
     * Main body of algorithm
     */
    public void compute() {
        // TODO Auto-generated method stub
        checkNextNode(start, new HashMap<>());
        printVarCount(varCountFinal);
    }
    
    /**
     * Checks the given node and updates the temporary counters. Calls the method again for the parent nodes.
     * In case the parent node is the Start node the main counters are updated.
     * @param n  Node to be checked.
     * @param varCount List containing identified variables and the number of they occurrences.
     */
    private void checkNextNode(Node n, HashMap<String, Integer> varCount) {
        boolean once = false;
        Edge res = n.getLeavingEdge(0);
        String name = res.getAttribute("label").toString();
        if (!name.contains("temp_l"))
            updateVarCount(name, varCount);

        HashMap<String, Integer> aux = new HashMap<>();
        aux.putAll(varCount);
        Node in0 = n.getEnteringEdge(0).getNode0();
        if (in0.getEnteringEdge(0).getNode0().getId().equals("Start")) {
            updateVarCountFinal(varCount);
            once = true;
        } else {
            checkNextNode(in0, varCount);
        }

        varCount.clear();
        varCount.putAll(aux);

        if (n.getInDegree() > 1) {
            Node in1 = n.getEnteringEdge(1).getNode0();
            if (in1.getEnteringEdge(0).getNode0().getId().equals("Start")) {
                if (!once) {
                    updateVarCountFinal(varCount);
                }

            } else {
                checkNextNode(in1, varCount);
            }
        }

        return;
    }

    
    /**
     * Updates the given counter list based on the given variable name. 
     * 
     * @param name      name of the variable to update. 
     * @param varCount List containing identified variables and the number of they occurrences.
     * @return          updated List containing identified variables and the number of they occurrences.
     */
    private HashMap<String, Integer> updateVarCount(String name, HashMap<String, Integer> varCount) {
        boolean updated = false;

        for (Map.Entry<String, Integer> pair : varCount.entrySet()) {

            if (pair.getKey().equals(name)) {
                updated = true;
                varCount.put(name, pair.getValue() + 1);
                break;
            }
        }

        if (!updated) {
            varCount.put(name, 1);
        }
        return varCount;
    }

    /**
     * Updates the list of final counters given the the list of temporary counters.
     * I have reduced this from complexity n^2 to n using the hashMap
     * @param varCount List containing identified variables and the number of they occurrences.
     */
    private void updateVarCountFinal(HashMap<String, Integer> varCount) {
        for (Map.Entry<String, Integer> pair : varCount.entrySet()) {

            if(!varCountFinal.containsKey(pair.getKey())) {
                varCountFinal.put(pair.getKey(), pair.getValue());
            } else {
                if (varCountFinal.get(pair.getKey()) < pair.getValue()) {
                    varCountFinal.put(pair.getKey(), pair.getValue());
                }
            }
        }

        return;

    }

    /**
     * Prints the max number of times a variable is used sequentially. 
     * @param varCount List containing identified variables and the number of they occurrences.
     */
    private void printVarCount(HashMap<String, Integer> varCount) {

        System.out.println("--------written variables---------");
        for (Map.Entry<String, Integer> pair : varCount.entrySet()) {
            System.out.println("key " + pair.getKey() + " value " + pair.getValue());
        }
        System.out.println("-------------------------"
                + "---------");
        return;
    }

    /**
     * Returns the variable with the highest number of occurrences from the List containing 
     * identified variables and the number of they occurrences.t.
     * 
     * @return
     */
    public String getVar() {
        Integer max = 0;
        String name = "empty";
        for (Map.Entry<String, Integer> pair_final : varCountFinal.entrySet()) {
            if (pair_final.getValue() > max) {
                max = pair_final.getValue();
                name = pair_final.getKey();

            }

        }

        if (name.contains("["))
            name = name.substring(0, name.indexOf("["));
        return name;
    }

}
