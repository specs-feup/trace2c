package pt.up.fe.specs;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Algorithm that prints out the  C code for the upper level of the hierarchy.
 * 
 * @author Afonso
 *
 */

public class CGenerateSimpleHierarchyPruned implements Algorithm {
    Graph graph;
    List<List<Node>> levelGraph;
    GraphsWrapper graphsWrapper;
    CInfo info;
    BufferedWriter outBuffer;
    String path;
    int maxLevel;
    int loadstore;

    /**
     * 
     * @param info        information file to initialize the variables in the output function.
     * @param loadstore   number of  concurrent load/stores.
     * @param graphsWrapper graphs wrapper
     * @throws IOException
     */
    public CGenerateSimpleHierarchyPruned(CInfo info, int loadstore, GraphsWrapper graphsWrapper, BufferedWriter outBuffer) throws IOException {
        this.info = info;
        this.graphsWrapper = graphsWrapper;
        this.outBuffer = outBuffer;
        if (loadstore < 17)
            this.loadstore = loadstore;
        else
            this.loadstore = 16;
    }

    @Override
    /**
     * Initializes the algorithm.
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        this.graph = graph;
        this.maxLevel = graph.getAttribute("maxlevel");
        this.levelGraph = graph.getAttribute("levelgraph");

    }

    @Override
    /**
     * Main method that applies the algorithms. First initializes input and local variables in the output C code
     * and then prints out the lines of code corresponding to operation. If loop detected call algorithm to wirte loop.
     * Requires leveled graph.
     */
    public void compute() {

        try {
            outBuffer.append("// Step by step description of process\n");
            outBuffer.append("// Step 1: create function header\n");

            initFunction(info);

            outBuffer.append("\n\n\n\n// Step 3: write code by level\n");
            outBuffer.append("// Currently we write attributions and simple operations between two variables\n");
            for (List<Node> list : levelGraph) {
                // out_c.append("// Start of Level " + levelgraph.indexOf(list) + ":\n");

                if (levelGraph.indexOf(list) > 0) {
                    for (Node n : list)
                        if (n.getAttribute("att1").equals("op") && levelGraph.indexOf(list) > 1)
                            checkAndWrite(n);
                        else if (n.getAttribute("att1").equals("hyper")) {
                            if (!n.hasAttribute("done")) {
                                startLoop(n);
                                n.addAttribute("done", true);
                            }

                        } else if (n.getAttribute("att1").equals("call")) {
                            writeCall(n);
                        }

                }
                outBuffer.append("\n");
            }
            outBuffer.append("}\n");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void writeCall(Node n) throws IOException {
        outBuffer.append(n.getAttribute("att2"));
        outBuffer.append("(");
        boolean isFirstParam = true;
        for (Edge e: n.getEachEnteringEdge()) {
            if (e.hasAttribute("params")) {
                for (String param: (List<String>) e.getAttribute("params")) {
                    if (isFirstParam) {
                        outBuffer.append(param);
                        isFirstParam = false;
                    } else {
                        outBuffer.append("," + param);
                    }
                }
           }
        }
        outBuffer.append(");");
    }

    /**
     * Calls algorithm to print out the C code for a subgraph that repreents a loop.
     *  
     * @param n  Node containing the subgraph of the loop.
     * @throws IOException
     */
    private void startLoop(Node n) throws IOException {
        // TODO Auto-generated method stub
        outBuffer.append("\n // Creating Loop\n");
        List<Graph> graphlist = graph.getAttribute(n.getId());
        Graph struct = graphlist.get(graphlist.size() - 1);
        int N;
        if (struct.hasAttribute("size"))
            N = struct.getAttribute("size");
        else
            N = graphlist.size() - 1;

        int plus_fold = struct.getAttribute("plus_fold");
        int mult_fold = struct.getAttribute("mult_fold");
        int loopLevel = 0;
        ArrayList<LoopNameAndIterator> variables = new ArrayList<>();
        variables.add(new LoopNameAndIterator(struct.getAttribute("Loopname"), "i"));
        Algorithm loop = new CGenerateLoopPruned(outBuffer, graphlist, struct, struct.getAttribute("levelgraph"),
                struct.getAttribute("maxlevel"), N, plus_fold,
                mult_fold, variables,
                loopLevel, true);
        loop.init(struct);
        loop.compute();
        return;

    }

    /**
     * Creates the output function header and the lines of code that initialize the local variables in the
     * output  C code.
     * 
     * @param info  information of the variables. to initialize.
     * @throws IOException
     */
    private void initFunction(CInfo info) throws IOException {
        outBuffer.append("\nvoid " + this.graph.getNode("Start").getAttribute("att2") + "( ");

        // write function parameters
        for (VarIO var : info.io_info) {
            if (var.isArray()) {
                outBuffer.append(var.getType() + " " + var.getName());
                List<Integer> indexes = var.getSize();
                for (int i = 0; i < var.getDim(); i++) {
                    outBuffer.append("[" + indexes.get(i) + "]");

                }

            } else
                outBuffer.append(var.getType() + " " + var.getName());

            if ((info.io_info.indexOf(var) + 1) != info.io_info.size())
                outBuffer.append(", ");

        }
        outBuffer.append("){\n");
        if (info.full_part) {
            for (VarIO var : info.io_info) {
                List<Integer> part = var.getPartFactor();
                for (int dim = 0; dim < var.getDim(); dim++) {
                    if (var.isArray()) {
                        if (part.get(dim) != 0) {
                            outBuffer.append(
                                    "#pragma HLS array_partition variable=" + var.getName() + " cyclic factor=");
                            if (dim < 1)
                                outBuffer.append((part.get(dim) + 1) / 2 + " dim= " + (dim + 1) + "\n");
                            else
                                outBuffer.append((part.get(dim)) + " dim= " + (dim + 1) + "\n");

                        }
                    }
                }
            }

        } else {
            if (loadstore > 2) {
                for (VarIO var : info.io_info) {
                    if (var.isArray()) {
                        outBuffer.append(
                                "#pragma HLS array_partition variable=" + var.getName() + " cyclic factor="
                                        + (loadstore + 2 - 1) / 2 + "\n");

                    }
                }
            }
        }
        outBuffer.append("// Step 2: Initialize local variables\n");
        for (VarLoc var : info.localInfo) {
            if (var.getType().equals("global")) {
                continue;
            }
            if (var.isArray())
                outBuffer.append(var.getType() + " " + var.getName() + "[" + var.getSize() + "];\n");
            else
                outBuffer.append(var.getType() + " " + var.getName() + ";\n");

        }

        outBuffer.append("// Initialization done\n");
    }

    /**
     * Generates the lines of code for a give operation node.
     * 
     * @param n  Node describing operation. 
     * @throws IOException
     */
    private void checkAndWrite(Node n) throws IOException {
        if (n.getAttribute("label").toString().equals("div"))
            n.setAttribute("label", "/");

        if (n.getAttribute("label").toString().equals("=") || n.getInDegree() == 1) {
            for (Edge res : n.getEachLeavingEdge()) {
                if (n.hasAttribute("mod")) {
                    if (n.getAttribute("mod").equals("sqr")) {
                        outBuffer.append(res.getAttribute("label").toString() + n.getAttribute("label").toString()
                                + n.getEnteringEdge(0).getAttribute("label").toString() + " * "
                                + n.getEnteringEdge(0).getAttribute("label").toString() + ";\n");
                    }

                } else
                    outBuffer.append(res.getAttribute("label").toString() + n.getAttribute("label").toString()
                            + n.getEnteringEdge(0).getAttribute("label").toString() + ";\n");
            }
            return;
        }

        Edge in0 = n.getEnteringEdge(0);
        Edge in1 = n.getEnteringEdge(1);

        String name0 = in0.getAttribute("label").toString();
        String name1 = in1.getAttribute("label").toString();

        String append = null;
        if (in0.hasAttribute("cast")) {
            append = in0.getAttribute("cast");
            name0 = append.concat(name0);
        }

        if (in0.hasAttribute("mod")) {
            append = in0.getAttribute("mod");
            name0 = append.concat(name0);
            name0 = name0.concat(")");
        }

        if (in1.hasAttribute("cast")) {
            append = in1.getAttribute("cast");
            name1 = append.concat(name1);
        }

        if (in1.hasAttribute("mod")) {
            append = in1.getAttribute("mod");
            name1 = append.concat(name1);
            name1 = name1.concat(")");
        }

        for (Edge res : n.getEachLeavingEdge()) {
            String name = res.getAttribute("label").toString();
            if (in0.getAttribute("pos").toString().equals("l"))
                outBuffer.append(name + "=" + name0 + " " +
                        n.getAttribute("label").toString() + " " + name1 + ";\n");
            else
                outBuffer.append(name + "=" + name1 + " " +
                        n.getAttribute("label").toString() + " " + name0 + ";\n");
        }
        return;
    }
}
