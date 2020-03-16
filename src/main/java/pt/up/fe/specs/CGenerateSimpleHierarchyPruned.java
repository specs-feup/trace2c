package pt.up.fe.specs;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Algorithm that prints out the  C code for the upper level of the hierarchy.
 * 
 * @author Afonso
 *
 */

public class CGenerateSimpleHierarchyPruned implements Algorithm {
    Graph graph;
    List<List<Node>> levelgraph = new ArrayList<>();

    CInfo info;
    BufferedWriter out_c;
    String path;
    Integer ngraphs;
    String function;
    int level;
    int loadstore;

    /**
     * 
     * @param info        information file to initialize the variables in the output function.
     * @param path        path to output function.
     * @param function    name of output function.
     * @param level       highest level of the leveled graph.
     * @param levelgraph  leveled graph.
     * @param loadstore   number of  concurrent load/stores. 
     * @throws IOException
     */
    public CGenerateSimpleHierarchyPruned(CInfo info, String path, String function, int level,
            List<List<Node>> levelgraph, int loadstore) throws IOException {
        this.level = level;
        this.info = info;
        this.ngraphs = 0;
        this.path = new String(path);
        this.function = function;
        this.levelgraph = levelgraph;
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
        this.ngraphs++;
        this.graph = graph;
        String c_filename = new String("\\" + function + ".c");
        path = path.concat(c_filename);

        try {
            File file = new File(path);
            System.out.println("Path: " + path);
            out_c = new BufferedWriter(new FileWriter(file));
            out_c.write("//Func created from graph [First implementation]");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Wrong path: " + path);
            e.printStackTrace();
            System.exit(0);
        }

    }

    @Override
    /**
     * Main method that applies the algorithms. First initializes input and local variables in the output C code
     * and then prints out the lines of code corresponding to operation. If loop detected call algorithm to wirte loop.
     * Requires leveled graph.
     */
    public void compute() {

        try {
            out_c.append("// Step by step description of process\n");
            out_c.append("// Step 1: create function header\n");

            initFunction(info);

            out_c.append("\n\n\n\n// Step 3: write code by level\n");
            out_c.append("// Currently we write attributions and simple operations between two variables\n");
            for (List<Node> list : levelgraph) {
                // out_c.append("// Start of Level " + levelgraph.indexOf(list) + ":\n");

                if (levelgraph.indexOf(list) > 0) {
                    for (Node n : list)
                        if (n.getAttribute("att1").equals("op") && levelgraph.indexOf(list) > 1)
                            checkAndWrite(n);
                        else if (n.getAttribute("att1").equals("hyper")) {
                            if (!n.hasAttribute("done")) {
                                startLoop(n);
                                n.addAttribute("done", true);
                            }

                        }

                }
                out_c.append("\n");
            }
            out_c.append("}\n");
            out_c.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Calls algorithm to print out the C code for a subgraph that repreents a loop.
     *  
     * @param n  Node containing the subgraph of the loop.
     * @throws IOException
     */
    private void startLoop(Node n) throws IOException {
        // TODO Auto-generated method stub
        out_c.append("\n // Creating Loop\n");
        List<Graph> graphlist = graph.getAttribute(n.getId().toString());
        Graph struct = graphlist.get(graphlist.size() - 1);
        int N;
        if (struct.hasAttribute("size"))
            N = struct.getAttribute("size");
        else
            N = graphlist.size() - 1;
        System.out.println("here");
        // struct.display();
        /* Algorithm leveling = new LevelingAlgorithm();
        leveling.init(struct);
        leveling.compute();*/
        int plus_fold = struct.getAttribute("plus_fold");
        int mult_fold = struct.getAttribute("mult_fold");
        // int maxlevel = ((LevelingAlgorithm) leveling).getLevel();
        List<List<Node>> looplevelgraph = new ArrayList<>();
        // looplevelgraph.addAll(((LevelingAlgorithm) leveling).getLevelGraph());

        HashMap<String, String> variables = new HashMap<>();
        variables.put(struct.getAttribute("Loopname").toString(), "i");
        Algorithm loop = new CGenerateLoopPruned(out_c, graphlist, struct, struct.getAttribute("levelgraph"),
                struct.getAttribute("maxlevel"), N, plus_fold,
                mult_fold, variables,
                0, true);
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
        out_c.append("\nvoid " + function + "( ");

        for (VarIO var : info.io_info) {
            if (var.isArray()) {
                out_c.append(var.getType() + " " + var.getName());
                List<Integer> indexes = var.getSize();
                for (int i = 0; i < var.getDim(); i++) {
                    out_c.append("[" + indexes.get(i) + "]");

                }

            } else
                out_c.append(var.getType() + " " + var.getName());

            if ((info.io_info.indexOf(var) + 1) != info.io_info.size())
                out_c.append(", ");

        }
        out_c.append("){\n");
        if (info.full_part) {
            for (VarIO var : info.io_info) {
                List<Integer> part = var.getPartFactor();
                for (int dim = 0; dim < var.getDim(); dim++) {
                    if (var.isArray()) {
                        if (part.get(dim) != 0) {
                            out_c.append(
                                    "#pragma HLS array_partition variable=" + var.getName() + " cyclic factor=");
                            if (dim < 1)
                                out_c.append((part.get(dim) + 1) / 2 + " dim= " + (dim + 1) + "\n");
                            else
                                out_c.append((part.get(dim)) + " dim= " + (dim + 1) + "\n");

                        }
                    }
                }
            }

        } else {
            if (loadstore > 2) {
                for (VarIO var : info.io_info) {
                    if (var.isArray()) {
                        out_c.append(
                                "#pragma HLS array_partition variable=" + var.getName() + " cyclic factor="
                                        + (loadstore + 2 - 1) / 2 + "\n");

                    }
                }
            }
        }
        out_c.append("// Step 2: Initialize local variables\n");
        for (VarLoc var : info.localInfo) {
            if (var.getType().equals("global")) {
                continue;
            }
            if (var.isArray())
                out_c.append(var.getType() + " " + var.getName() + "[" + var.getSize() + "];\n");
            else
                out_c.append(var.getType() + " " + var.getName() + ";\n");

        }

        out_c.append("// Initialization done\n");
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
                        out_c.append(res.getAttribute("label").toString() + n.getAttribute("label").toString()
                                + n.getEnteringEdge(0).getAttribute("label").toString() + " * "
                                + n.getEnteringEdge(0).getAttribute("label").toString() + ";\n");
                    }

                } else
                    out_c.append(res.getAttribute("label").toString() + n.getAttribute("label").toString()
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
                out_c.append(name + "=" + name0 + " " +
                        n.getAttribute("label").toString() + " " + name1 + ";\n");
            else
                out_c.append(name + "=" + name1 + " " +
                        n.getAttribute("label").toString() + " " + name0 + ";\n");
        }
        return;
    }
}
