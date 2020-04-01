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
 */

public class CGenerateSimpleHierarchyPruned implements Algorithm {
    Graph graph;
    List<List<Node>> levelGraph;
    GraphsWrapper graphsWrapper;
    BufferedWriter outBuffer;
    String path;
    int maxLevel;
    int loadstore;

    /**
     * @param loadstore     number of  concurrent load/stores.
     * @param graphsWrapper graphs wrapper
     * @throws IOException
     */
    public CGenerateSimpleHierarchyPruned(int loadstore, GraphsWrapper graphsWrapper, BufferedWriter outBuffer) throws IOException {
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

            initFunction();

            outBuffer.append("\n\n\n\n// Step 3: write code by level\n");
            outBuffer.append("// Currently we write attributions and simple operations between two variables\n");
            for (int i = 1; i < levelGraph.size() -1; i++) {
                List<Node> list = levelGraph.get(i);
                    for (Node n : list) {
                        String att1 = n.getAttribute("att1");
                        if (att1.equals("op") && levelGraph.indexOf(list) > 1) {
                            checkAndWrite(n);
                        } else if (att1.equals("hyper")) {
                            if (!n.hasAttribute("done")) {
                                startLoop(n);
                                n.addAttribute("done", true);
                            }

                        } else if (att1.equals("call") && n.getLeavingEdge(0).hasAttribute("label")) {
                            if (!n.getLeavingEdge(0).getTargetNode().getAttribute("label").equals("=")){
                                // write only if the return value is not assigned, otherwise write as an operation
                                writeCall(n);
                            }

                        }
                    }
                outBuffer.append("\n");
            }
            writeReturn();
            outBuffer.append("}\n");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void writeReturn() throws IOException {
        Node endNode = graph.getNode("End");
        Node previousNode = endNode.getEnteringEdge(0).getSourceNode();
        if (previousNode.getAttribute("att1").equals("op")) {
            if (!previousNode.getAttribute("label").equals("=")) {
                outBuffer.append("return " + getOperationInC(previousNode) + ";\n");
            } else {
                outBuffer.append("return " + endNode.getEnteringEdge(0).getAttribute("label") + ";\n");
            }
        } else if (previousNode.getAttribute("att1").equals("call")) {
            outBuffer.append("return ");
            writeCall(previousNode);
            outBuffer.append("\n");
        } else {
            CInfo info =  graph.getAttribute("info");
            List<VarIO> outputs = info.getOutputs();
            if (outputs.size() == 1) {
                outBuffer.append("return " + outputs.get(0).getName() + ";\n");
            }
        }
    }

    private String getOperationInC(Node n) {
        StringBuffer buffer = new StringBuffer();
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
        String operationType = n.getAttribute("label").toString();
        if (in0.getAttribute("pos").toString().equals("l")) {
            buffer.append(name0 + " " + operationType + " " + name1);
        }
        else {
            buffer.append(name1 + " " + operationType + " " + name0);
        }
        return buffer.toString();
    }

    private void writeCall(Node n) throws IOException {
        outBuffer.append(n.getAttribute("att2"));
        outBuffer.append("(");
        boolean isFirstParam = true;
        for (Edge e : n.getEachEnteringEdge()) {
            if (e.hasAttribute("label")) {
                String param = e.getAttribute("label").toString();
                if (isFirstParam) {
                    outBuffer.append(param);
                    isFirstParam = false;
                } else {
                    outBuffer.append("," + param);
                }
            }
        }
        outBuffer.append(");");
    }

    /**
     * Calls algorithm to print out the C code for a subgraph that repreents a loop.
     *
     * @param n Node containing the subgraph of the loop.
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

    private void writeFunctionParameters(CInfo info) throws IOException {
        boolean isFirstParam = true;
        for (VarIO var : info.getInputs()) {
            if (!isFirstParam) {
                outBuffer.append(",");
            }
            isFirstParam = false;
            if (var.isArray()) {
                outBuffer.append(var.getType() + " " + var.getName());
                List<Integer> indexes = var.getSize();
                for (int i = 0; i < var.getDim(); i++) {
                    outBuffer.append("[" + indexes.get(i) + "]");
                }

            } else {
                outBuffer.append(var.getType() + " " + var.getName());
            }
        }
        outBuffer.append("){\n");
    }

    /**
     * Creates the output function header and the lines of code that initialize the local variables in the
     * output  C code.
     *
     * @throws IOException
     */
    private void initFunction() throws IOException {
        CInfo info = this.graph.getAttribute("info");
        String outputType = info.getOutputs().isEmpty() ? "void" : info.getOutputs().get(0).getType();
        outBuffer.append("\n"+ outputType + " " + this.graph.getNode("Start").getAttribute("att2") + "(");

        writeFunctionParameters(info);

        if (info.isFullPartition()) {
            for (VarIO var : info.getInputs()) {
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
                for (VarIO var : info.getInputs()) {
                    if (var.isArray()) {
                        outBuffer.append(
                                "#pragma HLS array_partition variable=" + var.getName() + " cyclic factor="
                                        + (loadstore + 2 - 1) / 2 + "\n");

                    }
                }
            }
        }
        outBuffer.append("// Step 2: Initialize local variables\n");
        for (VarLoc var : info.getLocalInfo()) {
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
     * @param n Node describing operation.
     * @throws IOException
     */
    private void checkAndWrite(Node n) throws IOException {
        if (n.getAttribute("label").toString().equals("div"))
            n.setAttribute("label", "/");

        if (n.getAttribute("label").toString().equals("=") || n.getInDegree() == 1) {
            Edge enteringEdge = n.getEnteringEdge(0);
            for (Edge leavingEdge : n.getEachLeavingEdge()) {
                if (n.hasAttribute("mod")) {
                    if (n.getAttribute("mod").equals("sqr")) {
                        outBuffer.append(leavingEdge.getAttribute("label").toString() + "="
                                + enteringEdge.getAttribute("label").toString() + " * "
                                + enteringEdge.getAttribute("label").toString() + ";\n");
                    }

                } else if (enteringEdge.getSourceNode().getAttribute("att1").equals("call")) {
                    outBuffer.append(leavingEdge.getAttribute("label") + "= ");
                    writeCall(enteringEdge.getSourceNode());
                }
                else {
                    outBuffer.append(leavingEdge.getAttribute("label").toString() + "=" +
                            enteringEdge.getAttribute("label").toString() + ";\n");
                }

            }
            return;
        }
        String operationInC = getOperationInC(n);

        for (Edge leaveEdge : n.getEachLeavingEdge()) {
            if (!leaveEdge.getTargetNode().getAttribute("label").equals("end")) {
                String name = leaveEdge.getAttribute("label").toString();
                outBuffer.append(name + "=" + operationInC + ";\n");
            }
        }
    }
}
