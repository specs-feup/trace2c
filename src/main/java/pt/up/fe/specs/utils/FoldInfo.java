package pt.up.fe.specs.utils;

import java.util.HashMap;

public class FoldInfo implements Cloneable {

    private HashMap<String, Integer> varsToFold = new HashMap<>();
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
