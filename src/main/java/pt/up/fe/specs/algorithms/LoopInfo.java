package pt.up.fe.specs.algorithms;

import pt.up.fe.specs.utils.ProgressionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the information of an array access in reference to a specific loop.
 * @author CPU TOSH
 *
 */
public class LoopInfo {

    // loop name
    public String name;
    // ratios of the arithmetic or geometric progression per dimension
    public List<Integer> ratios;
    // progressionTypes per dimension
    public List<ProgressionType> progressionTypes = new ArrayList<>();

    public List<Integer> initialValues;
    public int dim;

    /**
     * 
     * @param name       name of loop.
     * @param ratios List of ratios per loop iteration for each dimension.
     * @param progressionTypes List of the progression types for each dimension
     */
    public LoopInfo(String name, List<Integer> ratios, List<ProgressionType> progressionTypes, List<Integer> initialValues) {
        this.dim = ratios.size();
        this.ratios = ratios;
        this.name = name;
        this.progressionTypes = progressionTypes;
        this.initialValues = initialValues;
    }

    /**
     * Prints out contents. 
     */
    public void print() {
        System.out.println("Loopname " + name + " dim " + dim);
        for (int i = 0; i < dim; i++) {
            System.out.println(" index " + i + " : " + ratios.get(i));
        }
    }

}
