package pt.up.fe.specs;

import com.google.gson.annotations.SerializedName;
import pt.up.fe.specs.utils.VarAndDim;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores the information from the configuration file.
 *
 * @author Afonso
 */
public class Config {

    public static class StaticConfig {
        public static List<String> inputs = new ArrayList<>();
        @SerializedName("input_types")
        private static List<String> inputTypes = new ArrayList<>();
        private static List<String> outputs = new ArrayList<>();
        @SerializedName("output_types")
        private static List<String> outputTypes = new ArrayList<>();

        private static boolean arithmetic = true;
        private static boolean pruneLocalArrays = true;
        private static boolean fold = false;
        private static boolean foldPrologue = false;
        private static boolean foldEpilogue = false;
        private static boolean parallelizeSums = false;
        private static boolean saveEnergy = false;
        private static String graph = "";
        private static String outputFile = "";
        private static Integer parallelFunctions = 1;
        private static Integer maxNodesPerSubgraph = 1000;
        private static Integer maxFoldLevels = 100;
        private static Integer minFoldLevels = 1;
        private static Integer subgraphRepeats = 0;
        private static List<VarAndDim> varsToPartition = new ArrayList<>();
        private static List<String> includes = new ArrayList<>();
        private static List<String> defines = new ArrayList<>();
    }

    public static boolean isSubgraphRepeatsSet() {
        return StaticConfig.subgraphRepeats > 0;
    }

    public static boolean isSaveEnergy() {
        return StaticConfig.saveEnergy;
    }

    public static int getSubgraphRepeats() {
        return StaticConfig.subgraphRepeats;
    }

    public static boolean isToParallelizeSums() {
        return StaticConfig.parallelizeSums;
    }

    public static boolean isToFold() {
        return StaticConfig.fold;
    }

    public static boolean isArithmetic() {
        return StaticConfig.arithmetic;
    }

    public static int getParallelFunctions() {
        return StaticConfig.parallelFunctions;
    }

    public static List<VarAndDim> getVarsToPartition() {
        return StaticConfig.varsToPartition;
    }

    public static Integer getDimToPartition(String var) {
        for (VarAndDim varAndDim : StaticConfig.varsToPartition) {
            if (var.equals(varAndDim.getVar())) {
                return varAndDim.getDim();
            }
        }
        return 0;
    }

    public static List<String> getInputs() {
        return StaticConfig.inputs;
    }

    public static List<String> getInputTypes() {
        return StaticConfig.inputTypes;
    }

    public static List<String> getOutputs() {
        return StaticConfig.outputs;
    }

    public static List<String> getOutputTypes() {
        return StaticConfig.outputTypes;
    }

    public static boolean hasIncludes() {
        return !StaticConfig.includes.isEmpty();
    }

    public static List<String> getIncludes() {
        return StaticConfig.includes;
    }


    public static boolean hasDefines() {
        return !StaticConfig.defines.isEmpty();
    }

    public static List<String> getDefines() {
        return StaticConfig.defines;
    }

    public static Integer getMaxFoldLevels() {
        return StaticConfig.maxFoldLevels;
    }

    public static Integer getMinFoldLevels() { return StaticConfig.minFoldLevels; }

    public static Integer getMaxNodesPerSubgraph() {
        return StaticConfig.maxNodesPerSubgraph;
    }

    public static boolean isToFoldPrologue() {
        return StaticConfig.foldPrologue;
    }

    public static boolean isToFoldEpilogue() {
        return StaticConfig.foldEpilogue;
    }

    public static void setOutputs(List<String> outputs) {
        StaticConfig.outputs = outputs;
    }

    public static void setOutputTypes(List<String> outputTypes) {
        StaticConfig.outputTypes = outputTypes;
    }

    public static boolean isPruneLocalArrays() {
        return StaticConfig.pruneLocalArrays;
    }

    public static String getGraph() {
        return StaticConfig.graph;
    }


    public static String getOutputFile() {
        return StaticConfig.outputFile;
    }
}
