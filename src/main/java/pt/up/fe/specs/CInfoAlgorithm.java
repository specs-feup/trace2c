package pt.up.fe.specs;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;


/**
 * Algorithm dedicated to get the information of variables and store that information
 * as attributes in the nodes.
 *  
 * 
 * @author CPU TOSH
 *
 */

public class CInfoAlgorithm implements Algorithm {

    private Graph graph;
    private CInfo info;

    @Override
    /**
     * Initializes the algorithm
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        this.graph = graph;
        info = new CInfo();
    }

    @Override
    /**
     * Goes through all variable nodes and adds attributes to them such as variable names,
     * array dimension, an array flag and array indexes.
     * 
     */
    public void compute() {
        // TODO Auto-generated method stub
        boolean isArray = false;
        String temp_name;

        for (Node n : graph) {
            // System.out.println(n.getId());
            if (!n.hasAttribute("att1")) {
                System.out.println("name : " + n.getId());
            }
            if (n.getInDegree() == 0 && n.getOutDegree() == 0)
                System.out.println("this name : " + n.getId());

            if (n.getAttribute("att1").equals("var")) {
                int arrayDim = 0;
                List<Integer> indexes = new ArrayList<>();
           
                    isArray = n.getAttribute("label").toString().contains("[");

                    if (isArray) {
                        temp_name=cutIndexes(n.getAttribute("label").toString());
                        arrayDim = getArrayDimension(n.getAttribute("label").toString(), indexes);
                        n.addAttribute("name", temp_name);
                        n.addAttribute("array", true);
                        n.addAttribute("Index", indexes);
                        n.addAttribute("dim", arrayDim);
                    } else {
                        temp_name = n.getAttribute("label").toString();

                        n.addAttribute("name", temp_name);
                    }
                  
                }                 

            }
        }

    
    /**
     * 
     * Isolates the name of an array. Does not work with structs.
     * 
     * @param name label of array access.
     * @return     label of array access without indexes.
     */
    public String conc_vec(String name) {

        return name.substring(0,
                name.toString().indexOf("["));

    }

    /**
     * Isolate the dimensions and indexes of an array. 
     * 
     * @param name    label of array access.
     * @param indexes list to store identified indexes.
     * @return        dimension of array.
     */
    public int getArrayDimension(String name, List<Integer> indexes) {
        String temp = name;
        int dim = 0;
        while (temp.lastIndexOf("[") != -1) {

            temp = temp.substring(temp.indexOf("["));
            String temp2 = temp.substring(1, temp.indexOf("]"));

            indexes.add(Integer.parseInt(temp2));
            temp = temp.substring(temp.indexOf("]"));

            dim++;
        }
        return dim;

    }
    
    
    /**
     * 
     * Isolates dimension and indexes of array. Works with structs.
     * 
     * @param name label of array access.
     * @return     label of array access without indexes.
     */
    public String cutIndexes(String name) {
        String temp = name;
        while (temp.lastIndexOf("[") != -1) {
            String temp2 = temp.substring(temp.indexOf("["));
            String temp3 = temp2.substring(0, temp2.indexOf("]"));
            temp=temp.replace(temp3 + "]", "");
        }
       
        return temp;
    }

    /**
     * Returns the updated information for variable initialization in writing process.
     * 
     * @return
     */
    public CInfo getInfo() {
        // TODO Auto-generated method stub
        //Information for pt.up.fe.specs.CInfo used to be acquired here but now it is done at other methods
        return info;
    }

}
