package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.Config;
import pt.up.fe.specs.utils.Var;
import pt.up.fe.specs.utils.Utils;

import java.util.List;

public class InfoFromConfig implements Algorithm {

    Graph graph;
    Config config;

    @Override
    public void init(Graph graph) {
        this.graph = graph;
        this.config = graph.getAttribute("config");
    }

    @Override
    public void compute() {
        CInfo info = new CInfo();
        List<Integer> indexes;
        int dim;
        if (config.getInputs().size() != config.getInput_types().size()) {
            System.err.println("Inputs and Input types do not match");
            return;
        }
        if (config.getOutputs().size() != config.getOutput_types().size()) {
            System.err.println("Outputs and Output types do not match");
            return;
        }
        for (int i = 0; i < config.getInputs().size(); i++) {
            String input = config.getInputs().get(i);
            String inputVarName = parseVarName(input);
            indexes = Utils.getIndexes(input);
            dim = indexes.size();
            Var in = new Var(config.getInput_types().get(i), inputVarName, dim > 0, indexes);
            info.addInput(in);
        }
        for (int i = 0; i < config.getOutputs().size(); i++) {
            String output = config.getOutputs().get(i);
            String outputVarName = parseVarName(output);
            indexes = Utils.getIndexes(output);
            dim = indexes.size();
            Var out = new Var(config.getOutput_types().get(i), outputVarName, dim > 0, indexes);
            info.addOutput(out);
        }
        graph.setAttribute("info", info);
        System.out.println("InfoFromConfig finished");
    }

    private String parseVarName(String name) {
        if (name.contains("["))
            return name.substring(0, name.indexOf("["));
        else
            return name;
    }
}
