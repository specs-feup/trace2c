package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.VarIO;
import pt.up.fe.specs.VarLoc;
import pt.up.fe.specs.utils.Utils;

public class SetGraphCInfo implements Algorithm {

    private Graph graph;
    private CInfo upperInfo;
    private CInfo info;
    private Utils utils = new Utils();

    /**
     *
     * @param upperInfo The CInfo from the upper graph in the hierarchy
     */
    public SetGraphCInfo(CInfo upperInfo) {
        this.upperInfo = upperInfo;
    }

    @Override
    public void init(Graph graph) {
        this.graph = graph;
        this.info = new CInfo();
    }

    @Override
    public void compute() {
        addInputs();
        addOutputs();
        graph.setAttribute("info", info);
    }

    private void addInputs() {
        Node start = graph.getNode("Start");
        start.getEachLeavingEdge().forEach(edge -> {
            String label = edge.getAttribute("label");
            String varName = utils.varNameFromLabel(label);
            if (info.getInput(varName) == null) {
                VarIO upperInput = upperInfo.getInput(varName);
                if ( upperInput != null) {
                    info.addInput(upperInput);
                } else {
                    System.out.println("TODO: Unhandled case");
                }
            }

        });
        info.addInput(new VarIO("int", "offset", false, null));
        info.addInput(new VarIO("int", "size", false, null));
    }

    private void addOutputs() {
        Node end = graph.getNode("End");
        end.getEachEnteringEdge().forEach(edge -> {
            String label = edge.getAttribute("label");
            String varName = utils.varNameFromLabel(label);
            if (info.getOutput(varName) == null) {
                VarIO upperOutput = upperInfo.getOutput(varName);
                if ( upperOutput != null) {
                    info.addOutput(upperOutput);
                } else {
                    VarLoc varLoc = upperInfo.getLocalVar(varName);
                    if (varLoc != null) {
                        info.addOutput(new VarIO(varLoc.getType(), varLoc.getName(), varLoc.isArray(), varLoc.getSize()));
                    } else {
                        info.addOutput(computeOutputVar(edge, varName));
                    }
                }
            }
        });
    }

    private VarIO computeOutputVar(Edge edge, String varName) {
        Boolean isArray = edge.hasAttribute("array");
        String type = edge.getAttribute("att3");
        String label = edge.getAttribute("label");
        if (isArray) {
            System.err.println("TODO: Unhandled case in computeOutputVar");
            return new VarIO(type, varName, true, utils.getIndexes(label));
        } else {
            return new VarIO(type, edge.getAttribute("label"), false, null);
        }
    }

    public CInfo getInfo() {
        return info;
    }


}
