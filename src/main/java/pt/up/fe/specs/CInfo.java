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
    private List<VarIO> inputs = new ArrayList<>();
    private List<VarIO> outputs = new ArrayList<>();
    private List<VarLoc> localInfo = new ArrayList<>();
    private HashMap<String, Integer> variablesCounter = new HashMap<>();
    private boolean isFullPartition = false;

    public void addInput(VarIO input) {
        inputs.add(input);
    }

    public void addOutput(VarIO output) {
        this.outputs.add(output);
    }

    public void clearLocalInfo() {
        this.localInfo.clear();
    }

    public void clearVariablesCounter() {
        this.variablesCounter.clear();
    }
    public List<VarIO> getInputs() {
        return inputs;
    }

    public void setInputs(List<VarIO> inputs) {
        this.inputs = inputs;
    }

    public List<VarIO> getOutputs() {
        return outputs;
    }


    public List<VarLoc> getLocalInfo() {
        return localInfo;
    }

    public void setLocalInfo(List<VarLoc> localInfo) {
        this.localInfo = localInfo;
    }

    public HashMap<String, Integer> getVariablesCounter() {
        return variablesCounter;
    }

    public void setVariablesCounter(HashMap<String, Integer> variablesCounter) {
        this.variablesCounter = variablesCounter;
    }

    public boolean isFullPartition() {
        return isFullPartition;
    }

    public void setFullPartition(boolean fullPartition) {
        this.isFullPartition = fullPartition;
    }



}
