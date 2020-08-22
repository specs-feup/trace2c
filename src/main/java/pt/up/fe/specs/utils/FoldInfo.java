package pt.up.fe.specs.utils;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Contains the vars that should be folded and some information about the loop
 */
public class FoldInfo implements Cloneable {

    private HashMap<String, Integer> varsToFold = new HashMap<>();
    private HashMap<String, Integer> varsHLSPartitionDimensions = new HashMap<>();
    private Integer width;
    private Integer increment;
    private Integer initialValue;

    public String getAnyVarToFold() {
        for (String varName : varsToFold.keySet()) {
            return varName;
        }
        return new String();
    }

    public boolean hasVar(Var var) {
        return varsToFold.containsKey(var.getName());
    }

    public boolean hasVar(String varName) {
        return varsToFold.containsKey(varName);
    }

    public boolean hasVarsToFold() {
        return !varsToFold.isEmpty();
    }

    public boolean hasVarHLSPartitionDim(String varName) {
        return varsHLSPartitionDimensions.containsKey(varName);
    }

    public void setVarsHLSPartitionDimension(String varName, Integer dim) {
        varsHLSPartitionDimensions.put(varName, dim);
    }

    /**
     * Gets the dimension to be partition by the hls directive
     * @param varName
     * @return -1 case the var should not be partitioned using the #hls directive
     */
    public int getVarHLSPartitionDimension(String varName) {
        return (hasVarHLSPartitionDim(varName) ? varsHLSPartitionDimensions.get(varName) : -1);
    }

    public void addVar(String varName, Integer dimension) {
        varsToFold.put(varName, dimension);
    }

    public int getDimOfVar(Var var) {
        return varsToFold.get(var.getName());
    }

    public int getDimOfVar(String varName) {
        return varsToFold.get(varName);
    }
    
    public void setIncrement(Integer increment) {
        this.increment = increment;
    }

    public void setInitialValue(Integer initV) {
        this.initialValue = initV;
    }

    public void setWidth(Integer foldSize) {
        this.width = foldSize;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getIncrement() {
        return increment;
    }

    public Integer getInitialValue() {
        return initialValue;
    }

    public Object clone() throws
            CloneNotSupportedException
    {
        return super.clone();
    }
}
