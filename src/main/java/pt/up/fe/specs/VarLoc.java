package pt.up.fe.specs;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores information of local variables.
 * 
 * @author CPU TOSH
 *
 */
public class VarLoc {

    protected boolean array;
    protected String name;
    protected String type;
    protected List<Integer> size = new ArrayList<>();
    protected int dim;

    public VarLoc(String type, String name, boolean array, List<Integer> indexes) {
        if (indexes == null) {
            this.size.add(0);
            this.dim = 0;
        } else {
            this.size = indexes;
            this.dim = indexes.size();
        }
        this.name = name;
        this.type = type;
        this.array = array;
    }
    /**
     * Checks given indexes and updates the maximum size of array dimensions if necessary. 
     * @param indexes List of Integers containing indexes of a specific array access
     */
    public void updateSize(List<Integer> indexes) {
        for (int i = 0; i < dim; i++) {
            if (indexes.get(i) > size.get(i))
                size.set(i, indexes.get(i));
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
     * Returns maximum size of each array dimension.
     * @return
     */
    public List<Integer> getSize() {
        return size;
    }

    /**
     * Returns type of variable
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Returns true if variable is an array.
     * @return
     */
    public boolean isArray() {
        return array;
    }

    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj) return true;
        if (otherObj == null || getClass() != otherObj.getClass()) return false;
        VarLoc other = (VarLoc) otherObj;
        return name.equals(other.name);
    }
}
