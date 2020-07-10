package pt.up.fe.specs;

import pt.up.fe.specs.utils.HLSPartition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that stores information of local variables.
 * 
 * @author CPU TOSH
 *
 */
public class Var {

    protected boolean isArray;
    protected String name;
    protected String type;
    protected List<Integer> sizes = new ArrayList<>();
    protected int dim;
    private boolean isSplit = false;
    // maps dimensions to partitions
    protected HashMap<Integer, LinkedList<Integer>> partitionsMap = new HashMap<>();

    public HLSPartition getHlsPartition() {
        return hlsPartition;
    }

    private HLSPartition hlsPartition;

    public int getNumberOfSubVariables(int numberOfParallelCalls, int dimToFold) {
        return numberOfParallelCalls + partitionsMap.get(dimToFold).size();
    }

    public int getParallelizableSize(int dimToFold) {
        LinkedList<Integer> partitionsAtDim = partitionsMap.getOrDefault(dimToFold, new LinkedList<>());
        if (!partitionsAtDim.isEmpty()) {
            if (partitionsAtDim.size() == 1) {
                return sizes.get(dim) - partitionsAtDim.getFirst();
            } else {
                return partitionsAtDim.getLast() - partitionsAtDim.getFirst();
            }
        }
        return sizes.get(dimToFold);
    }

    public boolean hasEpilogue(int dimToFold) {
        return partitionsMap.get(dimToFold).size() == 2;
    }

    public boolean hasPrologue(int dimToFold) {
        return partitionsMap.get(dimToFold).size() >= 1;
    }

    public void setHlsPartition(HLSPartition hlsPartition) {
        this.hlsPartition = hlsPartition;
    }

    public boolean hasHlsPartition() {
        return hlsPartition != null;
    }

    public HashMap<Integer, LinkedList<Integer>> getPartitionsMap() {
        return partitionsMap;
    }

    public LinkedList<Integer> getPartitionsAtDim(int dim) {
        return partitionsMap.getOrDefault(dim, new LinkedList<>());
    }


    public boolean hasPartitions() {
        return !partitionsMap.isEmpty();
    }

    public Var(String type, String name, boolean isArray, List<Integer> sizes) {
        if (sizes == null) {
            this.sizes.add(0);
            this.dim = 0;
        } else {
            this.sizes = sizes;
            this.dim = sizes.size();
        }
        this.name = name;
        this.type = type;
        this.isArray = isArray;
    }


    public void addPartition(int index, int dim) {
        partitionsMap.putIfAbsent(dim, new LinkedList<>());
        LinkedList<Integer> partitionsForDim = partitionsMap.get(dim);
        partitionsForDim.add(index);
    }


    /**
     * Checks given indexes and updates the maximum size of array dimensions if necessary. 
     * @param indexes List of Integers containing indexes of a specific array access
     */
    public void updateSize(List<Integer> indexes) {
        for (int i = 0; i < dim; i++) {
            if (indexes.get(i) > sizes.get(i))
                sizes.set(i, indexes.get(i));
        }
    }

    public void setSplit(boolean isSplit) {
        this.isSplit = isSplit;
    }

    public boolean isSplit() {
        return isSplit;
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
    public List<Integer> getSizes() {
        return sizes;
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
        return isArray;
    }

    public int getDim() { return dim; }

    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj) return true;
        if (otherObj == null || getClass() != otherObj.getClass()) return false;
        Var other = (Var) otherObj;
        return name.equals(other.name);
    }
}
