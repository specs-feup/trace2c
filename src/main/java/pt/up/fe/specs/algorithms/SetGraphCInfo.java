package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.Var;
import pt.up.fe.specs.utils.FoldInfo;
import pt.up.fe.specs.utils.Utils;

public class SetGraphCInfo implements Algorithm {

    private Graph graph;
    private CInfo upperInfo;
    private CInfo info;
    private Utils utils = new Utils();
    private boolean isParallel = false;
    private Integer numberOfParallelCalls = 1;
    private FoldInfo foldInfo;

    /**
     *
     * @param upperInfo The CInfo from the upper graph in the hierarchy
     */
    public SetGraphCInfo(CInfo upperInfo) {
        this.upperInfo = upperInfo;
    }

    public void setNumberOfParallelCalls(Integer parallelCalls) {
        this.numberOfParallelCalls = parallelCalls;
    }

    @Override
    public void init(Graph graph) {
        this.graph = graph;
        this.info = new CInfo();
        this.foldInfo = graph.getAttribute("foldInfo");
        if (graph.hasAttribute("type")) {
            if (graph.getAttribute("type").equals("parallel")) {
                isParallel = true;
            }
        }

    }

    @Override
    public void compute() {
        addInputs();
        addOutputs();
        graph.setAttribute("info", info);
    }

    private Var getVarFromUpperInfo(String varName) {
        Var upperInput = upperInfo.getInput(varName);
        if (upperInput == null) {
            Var localVar = upperInfo.getLocalVar(varName);
            if (localVar == null) {
                return upperInfo.getOutput(varName);
            } else {
                return new Var(localVar.getType(),localVar.getName(),localVar.isArray(),localVar.getSizes());
            }
        }
        return upperInput;
    }

    private void addInputs() {
        Node start = graph.getNode("Start");
        for (Edge edge: start.getEachLeavingEdge()) {
            String label = edge.getAttribute("label");
            String varName = utils.varNameFromLabel(label);

            if (!info.hasInput(varName)) {
                Var upperInput = getVarFromUpperInfo(varName);
                if (upperInput != null) {
                    upperInput = splitDimensions(upperInput);
                    info.addInput(upperInput);
                }
            }

        }
        if (isParallel) {
            info.addInput(new Var("int", "width", false, null));
        }

    }

    private Var splitDimensions(Var var) {
        if (numberOfParallelCalls > 1 && (foldInfo.hasVar(var.getName()))) {
            return utils.splitDimensions(var, numberOfParallelCalls, foldInfo.getDimOfVar(var.getName()));
        }
        return var;
    }

    private void addOutputs() {
        Node end = graph.getNode("End");
        end.getEachEnteringEdge().forEach(edge -> {
            String label = edge.getAttribute("label");
            String varName = utils.varNameFromLabel(label);
            if (info.getOutput(varName) == null) {
                Var upperOutput = upperInfo.getOutput(varName);
                if ( upperOutput != null) {
                    info.addOutput(upperOutput);
                } else {
                    Var var = upperInfo.getLocalVar(varName);
                    if (var != null) {
                        info.addOutput(splitDimensions(var));
                    } else {
                        info.addOutput(computeOutputVar(edge, varName));
                    }
                }
            }
        });
    }

    private Var computeOutputVar(Edge edge, String varName) {
        Boolean isArray = edge.hasAttribute("array");
        String type = edge.getAttribute("att3");
        String label = edge.getAttribute("label");
        if (isArray) {
            return splitDimensions(new Var(type, varName, true, utils.getIndexes(label)));
        } else {
            return new Var(type, edge.getAttribute("label"), false, null);
        }
    }

    public CInfo getInfo() {
        return info;
    }


}
