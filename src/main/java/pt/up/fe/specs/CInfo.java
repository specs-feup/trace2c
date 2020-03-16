package pt.up.fe.specs;

import java.util.*;

/**
 * 
 * Class that stores the information to initializes variables correctly in
 * the output C.
 * 
 * @author Afonso
 *
 */

public class CInfo {
    public List<VarIO> io_info = new ArrayList<VarIO>();
    public List<VarLoc> localInfo = new ArrayList<>();
    public HashMap<String, Integer> variablesCounter = new HashMap<>();
    public boolean full_part = false;

    /**
     * Prints the stored contents
     *
     */
    public void print() {
        System.out.println(" ");
        System.out.println("------ printing information file contents--------- ");
        
        if (localInfo != null) {
            for (VarLoc var : localInfo) {
                System.out.println("Name: " + var.getName() + " Size: " + var.getSize());

            }
        }

        if (io_info != null) {
            for (VarIO var : io_info) {
                System.out
                        .println("Name: " + var.getName() + " Size: " + var.getSize() + " isInput: " + var.getInput()
                                + "  isOutput: " + var.getOutput() + "  Dim: " + var.getDim());
            }
        }
        
        System.out.println("---------------------------------------------------");
        System.out.println(" ");
    }

    /**
     * Prints the counter of the variable. 
     */
    public void printcounter() {    
        System.out.println(" ");
        System.out.println("------ printing counters --------- ");

        if (variablesCounter != null) {
            for (Map.Entry<String, Integer> pair : variablesCounter.entrySet()) {
                System.out
                        .println(
                                "variable : " + pair.getKey() + " counter: " + pair.getValue());
            }
        }
        
        System.out.println("------------------------------------- ");
        System.out.println(" ");
    }

}
