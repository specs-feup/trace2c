package pt.up.fe.specs.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the min and max indexes used for a variable
 */
public class VarAccesses {

    private List<Integer> minIndexes = new ArrayList<>();
    private List<Integer> maxIndexes = new ArrayList<>();

    public boolean hasMinIndexes() {
        return !minIndexes.isEmpty();
    }

    public boolean hasMaxIndexes() {
        return !maxIndexes.isEmpty();
    }

    public List<Integer> getMinIndexes() {
        return minIndexes;
    }

    public void setMinIndexes(List<Integer> minIndexes) {
        this.minIndexes = minIndexes;
    }

    public List<Integer> getMaxIndexes() {
        return maxIndexes;
    }

    public void setMaxIndexes(List<Integer> maxIndexes) {
        this.maxIndexes = maxIndexes;
    }




}
