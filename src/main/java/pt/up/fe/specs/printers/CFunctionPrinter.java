package pt.up.fe.specs.printers;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.*;
import pt.up.fe.specs.utils.HLSPartition;
import pt.up.fe.specs.utils.Utils;
import pt.up.fe.specs.utils.Var;
import pt.up.fe.specs.utils.VarComparator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Algorithm that prints out the  C code for the upper level of the hierarchy.
 *
 * @author Afonso
 */

public class CFunctionPrinter extends CPrinter {
    List<List<Node>> sortedLevelGraph;
    int maxLevel;
    String graphType;

    /**
     *
     */
    public CFunctionPrinter(BufferedWriter outBuffer, Graph graph) {
        super(outBuffer, graph);
        ArrayList<Graph>  subgraphs = graph.getAttribute("subgraphs");
        if (subgraphs != null) {
            for (Graph subgraph: subgraphs) {
                CPrinter printer = new CFunctionPrinter(outBuffer, subgraph);
                printer.print();
            }
        }


        this.maxLevel = Utils.getMaxLevel(graph);
        this.sortedLevelGraph = Utils.getSortedLevelGraph(graph);
        this.graphType = graph.hasAttribute("type") ? graph.getAttribute("type") : "normal";
    }

    @Override
    /**
     * Main method that applies the algorithms. First initializes input and local variables in the output C code
     * and then prints out the lines of code corresponding to operation. If loop detected call algorithm to wirte loop.
     * Requires leveled graph.
     */
    public void print() {

        try {
            writeFunctionHeader();
            if (graphType.equals("parallel")) {
                CParallelLoopPrinter parallelLoopPrinter = new CParallelLoopPrinter(outBuffer, graph, 0);
                parallelLoopPrinter.print();
            } else {
                writeCodeByLevel(sortedLevelGraph, 0);
            }


            outBuffer.append("}\n");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected String getLabel(Edge edge) throws IOException {
        String name;
        if (!edge.hasAttribute("att1")) {
            throw new IOException("Edge without att1, label: " + Utils.getLabel(edge));
        }
        if (Utils.isCall(edge)) {
            name = getCallStatement(edge);
        } else {
            if (!edge.hasAttribute("label")) {
                throw new IOException("No label on edge");
            }
            name = Utils.getLabel(edge);
        }
        if (edge.hasAttribute("cast")) {
            name = edge.getAttribute("cast") + name;
        }
        if (edge.hasAttribute("mod")) {
            name = edge.getAttribute("mod") + name + ")";
        }
        return name;
    }

    @Override
    protected String getAssignmentLabel(Edge edge) {
        return Utils.getLabel(edge);
    }


    private String getFunctionParameters(CInfo info) {
        StringBuilder functionParams = new StringBuilder();
        ArrayList<Var> params = new ArrayList<>();
        params.addAll(info.getInputs());
        params.addAll(info.getOutputs());
        boolean isFirstParam = true;

        for (Var var : params) {
            if (!isFirstParam) {
                functionParams.append(", ");
            }
            isFirstParam = false;
            if (var.isArray()) {
                functionParams.append(var.getType()).append(" ").append(var.getName());
                List<Integer> indexes = var.getSizes();
                for (int i = 0; i < var.getDim(); i++) {
                    functionParams.append("[").append(indexes.get(i)).append("]");
                }

            } else {
                functionParams.append(var.getType()).append(" ").append(var.getName());
            }
        }
        return functionParams.toString();
    }

    /**
     * Creates the output function header and the lines of code that initialize the local variables in the
     * output  C code.
     *
     * @throws IOException
     */
    public void writeFunctionHeader() throws IOException {
        CInfo info = this.graph.getAttribute("info");
        String functionName = graph.getAttribute("functionName");
        String outputType = "void";
        outBuffer.append("\n").append(outputType).append(" ").append(functionName).append("(");

        outBuffer.append(getFunctionParameters(info));
        outBuffer.append(") {\n");


        outBuffer.append("// Step 2: Initialize local variables\n");
        info.getLocalInfo().sort(new VarComparator());
        for (Var var : info.getLocalInfo()) {
            if (var.isArray()) {
                outBuffer.append(var.getType()).append(" ").append(var.getName());
                for (int size: var.getSizes()) {
                    outBuffer.append("[").append(String.valueOf(size)).append("]");
                }
                outBuffer.append(";\n");
            }
            else {
                outBuffer.append(var.getType()).append(" ").append(var.getName()).append(";\n");
            }
        }
        List<Var> allVars = new ArrayList<>();
        allVars.addAll(info.getInputs());
        allVars.addAll(info.getLocalInfo());
        allVars.addAll(info.getOutputs());
        for (Var var: allVars) {
            if (var.hasHlsPartition()) {
                HLSPartition hlsPartition = var.getHlsPartition();
                outBuffer.append("#pragma HLS ARRAY_PARTITION variable=")
                        .append(var.getName()).append(" cyclic factor=")
                        .append(String.valueOf(hlsPartition.getFactor()))
                        .append(" dim=")
                        .append(String.valueOf(hlsPartition.getDim() + 1))
                        .append("\n");

            }
        }
        outBuffer.append("// Initialization done\n");
        if (graph.hasAttribute("subgraphs")) {
            outBuffer.append("#pragma HLS dataflow\n");
        }
    }




}
