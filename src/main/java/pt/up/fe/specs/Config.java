package pt.up.fe.specs;

import pt.up.fe.specs.utils.VarAndDim;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class that stores the information from the configuration file.
 * 
 * @author Afonso
 *
 */
public class Config {

    int loadstores;


    List<String> inputs = new ArrayList<>();
    List<String> input_types = new ArrayList<>();
    List<String> outputs;
    List<String> output_types;
    boolean arithmetic;
    String folding;
    boolean acc;
    String graph;
    String outputFile;
    Integer parallelFunctions;
    Integer maxNodesPerSubgraph;
    List<VarAndDim> varsToPartition;

    public Config() {
        // TODO Auto-generated constructor stub
    }

    public int getLoadStores() {
        return loadstores;
    }

    public int getParallelFunctions() {return parallelFunctions; }

    public List<VarAndDim> getVarsToPartition() {
        return varsToPartition;
    }

    public Integer getDimToPartition(String var) {
        for (VarAndDim varAndDim: varsToPartition) {
            if (var.equals(varAndDim.getVar())) {
                return varAndDim.getDim();
            }
        }
        return 0;
    }

    public List<String> getInputs() {
        return inputs;
    }

    public List<String> getInput_types() {
        return input_types;
    }

    public List<String> getOutputs() {
        return outputs;
    }

    public List<String> getOutput_types() {
        return output_types;
    }

}
