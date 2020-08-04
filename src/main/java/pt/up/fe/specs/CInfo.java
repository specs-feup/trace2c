package pt.up.fe.specs;

import pt.up.fe.specs.utils.Var;

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
    private List<Var> inputs = new ArrayList<>();
    private List<Var> outputs = new ArrayList<>();
    private List<Var> localInfo = new ArrayList<>();
    private HashMap<String, Integer> variablesCounter = new HashMap<>();
    private boolean isFullPartition = false;

    public Var getVar(String varName) {
        for (Var input: inputs) {
            if (input.getName().equals(varName)) return input;
        }
        for (Var output: outputs) {
            if (output.getName().equals(varName)) return output;
        }
        for (Var local: localInfo) {
            if (local.getName().equals(varName)) return local;
        }
        return null;
    }


    public void addInput(Var input) {
        inputs.add(input);
    }


    public boolean hasInput(String varName) {
        for (Var input: inputs ) {
            if (input.getName().equals(varName)) {
                return true;
            }
        }
        return false;
    }

    public Var getInput(String varName) {
        for (Var input: inputs ) {
            if (input.getName().equals(varName)) {
                return input;
            }
        }
        return null;
    }

    public Var getOutput(String varName) {
        for (Var output: outputs ) {
            if (output.getName().equals(varName)) {
                return output;
            }
        }
        return null;
    }

    public Var getLocalVar(String varName) {
        for (Var var : localInfo) {
            if (var.getName().equals(varName)) {
                return var;
            }
        }
        return null;
    }


    public void addOutput(Var output) {
        this.outputs.add(output);
    }

    public void clearLocalInfo() {
        this.localInfo.clear();
    }

    public void clearVariablesCounter() {
        this.variablesCounter.clear();
    }
    public List<Var> getInputs() {
        return inputs;
    }

    public void setInputs(List<Var> inputs) {
        this.inputs = inputs;
    }

    public List<Var> getOutputs() {
        return outputs;
    }


    public List<Var> getLocalInfo() {
        return localInfo;
    }

    public void setLocalInfo(List<Var> localInfo) {
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
