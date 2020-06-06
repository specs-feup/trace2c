package pt.up.fe.specs.printers;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.*;

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
    List<List<Node>> levelGraph;
    int maxLevel;
    int loadStores;
    String graphType;

    /**
     *
     */
    public CFunctionPrinter(BufferedWriter outBuffer, Graph graph, Config config) {
        super(outBuffer, graph, config);
        this.maxLevel = graph.getAttribute("maxlevel");
        this.levelGraph = graph.getAttribute("levelgraph");
        if (this.loadStores < 17)
            this.loadStores = config.getLoadStores();
        else
            this.loadStores = 16;
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
                CParallelLoopPrinter parallelLoopPrinter = new CParallelLoopPrinter(outBuffer, graph, config, 0);
                parallelLoopPrinter.print();
            } else {
                writeCodeByLevel(levelGraph, 0);
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
            throw new IOException("Edge without att1");
        }
        if (edge.getAttribute("att1").equals("call")) {
            name = getCallStatement(edge);
        } else {
            if (!edge.hasAttribute("label")) {
                throw new IOException("No label on edge");
            }
            name = edge.getAttribute("label").toString();
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
    protected String getAssignmentLabel(Edge edge) throws IOException {
        return edge.getAttribute("label").toString();
    }


    private String getFunctionParameters(CInfo info) throws IOException {
        String functionParams = new String();
        ArrayList<VarIO> params = new ArrayList<>();
        params.addAll(info.getInputs());
        params.addAll(info.getOutputs());
        boolean isFirstParam = true;

        for (VarIO var : params) {
            if (!isFirstParam) {
                functionParams += (", ");
            }
            isFirstParam = false;
            if (var.isArray()) {
                functionParams += (var.getType() + " " + var.getName());
                List<Integer> indexes = var.getSize();
                for (int i = 0; i < var.getDim(); i++) {
                    functionParams += ("[" + indexes.get(i) + "]");
                }

            } else {
                functionParams += (var.getType() + " " + var.getName());
            }
        }
        return functionParams;
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
        outBuffer.append("\n" + outputType + " " + functionName + "(");

        outBuffer.append(getFunctionParameters(info));
        outBuffer.append(") {\n");

        if (info.isFullPartition()) {
            for (VarIO var : info.getInputs()) {
                List<Integer> part = var.getPartFactor();
                for (int dim = 0; dim < var.getDim(); dim++) {
                    if (var.isArray()) {
                        if (part.get(dim) != 0) {
                            outBuffer.append("#pragma HLS array_partition variable=" + var.getName() + " cyclic factor=");
                            if (dim < 1)
                                outBuffer.append((part.get(dim) + 1) / 2 + " dim= " + (dim + 1) + "\n");
                            else
                                outBuffer.append((part.get(dim)) + " dim= " + (dim + 1) + "\n");

                        }
                    }
                }
            }

        } else {
            if (loadStores > 2) {
                for (VarIO var : info.getInputs()) {
                    if (var.isArray()) {
                        outBuffer.append("#pragma HLS array_partition variable=" + var.getName() + " cyclic factor="
                                        + (loadStores + 2 - 1) / 2 + "\n");

                    }
                }
            }
        }
        Node hyperNode = graph.getNode("HyperNode");
        if (hyperNode != null && hyperNode.getAttribute("type").equals("parallel")) {
            outBuffer.append("#pragma HLS dataflow\n");
        }
        outBuffer.append("// Step 2: Initialize local variables\n");
        for (VarLoc var : info.getLocalInfo()) {
            if (var.getType().equals("global")) {
                continue;
            }
            if (var.isArray()) {
                outBuffer.append(var.getType() + " " + var.getName());
                for (int size: var.getSize()) {
                    outBuffer.append("[" + size + "]");
                }
                outBuffer.append(";\n");
            }
            else {
                outBuffer.append(var.getType() + " " + var.getName() + ";\n");
            }


        }
        outBuffer.append("// Initialization done\n");
    }




}
