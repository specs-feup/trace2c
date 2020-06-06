package pt.up.fe.specs;

import java.util.ArrayList;
import java.util.List;
/**
 * Class that stores information for input and output variables of the input graph.
 * 
 * @author Afonso
 *
 */
public class VarIO extends VarLoc {
    private List<Integer> partitionFactor = new ArrayList<>();

    /**
     * 
     * @param type  type of variable
     * @param name  name of variable
     * @param array boolean indicating if variable is array.
     * @param dim   dim of variable.
     * @param indexes indexes of array access.
     */
    public VarIO(String type, String name, boolean array, List<Integer> indexes) {
        super(type, name,array,indexes);
        for (int i = 0; i < dim; i++) {
            partitionFactor.add(0);
        }
    }

    /**
     * Checks given indexes and updates the maximum size of array dimensions if necessary.
     * 
     * @param indexes List of Integer containing indexes of a specific array access.
     */
    public void updateSize(List<Integer> indexes) {
        for (int i = 0; i < dim; i++) {
            if (indexes.get(i) + 1 > size.get(i))
                size.set(i, indexes.get(i) + 1);
        }
    }
    
   /**
    * Updates the partitioning factor for each dimension based on the number of separate accesses of each dimension.
    * 
    * @param accesses Lisst of number of accesses to each dimension.
    */
    public void updatePartitionFactor(List<Integer> accesses) {
        for (int j = 0; j < dim; j++) {
            if (accesses.get(j) > partitionFactor.get(j)) {
                int temp = accesses.get(j);
                //increments part factor to be a divisor of the array size
                while ((size.get(j) % accesses.get(j)) != 0) {
                    accesses.set(j, accesses.get(j) + 1);
                    if (accesses.get(j) > size.get(j) / 2) {
                        accesses.set(j, temp);
                        break;
                    }
                }
                partitionFactor.set(j, accesses.get(j));
            }
        }
        return;
    }

    /**
     * Prints out partitioning factor
     */
    public void printPartitionFactors() {
        System.out.println(name + " Print partition: ");
        for (Integer i : partitionFactor) {
            System.out.println("  " + i);
        }
    }


    /**
     * Returns name of variable.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Returns type of variable.
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Returns array dimensions of variable.
     * @return
     */
    public int getDim() {
        return dim;
    }

    /**
     * Return the maximum sizes of array dimension of the vraiable.
     * @return
     */
    public List<Integer> getSize() {
        return size;
    }

    /**
     * Return the partitioning factors.
     * 
     * @return
     */
    public List<Integer> getPartFactor() {
        return partitionFactor;
    }
    
    /**
     * Returns true if variable is an array.
     * @return
     */
    public boolean isArray() {
        return array;
    }

}
