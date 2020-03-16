package pt.up.fe.specs.graphoptimizations;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the information of an array access in reference to a specific loop.
 * @author CPU TOSH
 *
 */
public class Loopinfo {

    public String name = new String();
    public List<Integer> increments = new ArrayList<>();
    public int dim;

    /**
     * 
     * @param name       name of loop.
     * @param increments List of increments per loop iteration for each separate dimension.
     * @param dim        dimension of array access.
     */
    public Loopinfo(String name, List<Integer> increments, int dim) {
        this.dim = dim;
        this.increments = increments;
        this.name = name;
    }

    /**
     * Prints out contents. 
     */
    public void print() {
        System.out.println("Loopname " + name + " dim " + dim);
        for (int i = 0; i < dim; i++) {
            System.out.println(" index " + i + " : " + increments.get(i));
        }
    }

}
