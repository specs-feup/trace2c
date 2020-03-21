package pt.up.fe.specs;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.stream.file.FileSinkDOT;
import pt.up.fe.specs.graphoptimizations.Loopinfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Algorithm that generates the C output of a subgraph in the context of a loop.
 *
 * @author Afonso
 */
public class CGenerateLoopPruned implements Algorithm {

    Graph graph;
    List<List<Node>> levelgraph = new ArrayList<>();
    List<Graph> graphlist;
    BufferedWriter out_c;
    int plus_fold;
    int mult_fold;
    Integer ngraphs;
    int total_its;
    int level;
    int initv;
    int looplevel;
    ArrayList<LoopNameAndIterator> loopVariables;
    boolean fold;
    boolean fullunroll;


    /**
     * @param out_c
     * @param graphList
     * @param loop          graph of the loop.
     * @param levelGraph    leveled graph.
     * @param level         max level of the leveled graph.
     * @param total_its     total number of loop iterations
     * @param plus_fold
     * @param mult_fold
     * @param loopVariables name of the variable used to indicate iterations.
     * @param loopLevel     level of the loop.
     * @param fold
     * @throws IOException
     */
    public CGenerateLoopPruned(BufferedWriter out_c, List<Graph> graphList, Graph loop,
                               List<List<Node>> levelGraph, int level, int total_its, int plus_fold, int mult_fold,
                               ArrayList<LoopNameAndIterator> loopVariables, int loopLevel,
                               boolean fold)
            throws IOException {
        this.looplevel = loopLevel;
        this.graphlist = graphList;
        this.level = level;
        this.graph = loop;
        this.ngraphs = 0;
        this.levelgraph = levelGraph;
        this.loopVariables = loopVariables;
        this.plus_fold = plus_fold;
        this.mult_fold = mult_fold;
        this.initv = loop.getAttribute("initv");
        this.out_c = out_c;
        this.total_its = total_its;
        this.fold = fold;
        fullunroll = false;
    }

    @Override
    /**
     * Initialize the algorithm.
     *
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        this.ngraphs++;

    }

    @Override
    /**
     * Main method implementing the algorithm. First initializes the loop, The writes the operation.
     * If inner loop node  detected call algorithm to print out the inner loop. Requires leveld graph.
     *
     */
    public void compute() {

        try {

            out_c.append("// starting Loop\n");
            if (!fullunroll)
                initLoop();
            out_c.append(
                    "// Currently we write attributions and simple operations between two variables and unrolling with directive\n");

            for (List<Node> list : levelgraph) {
                out_c.append("// Start of Level " + levelgraph.indexOf(list) + ":\n");
                // if (graph.hasAttribute("flat")) {
                if (levelgraph.indexOf(list) > 1) {
                    for (Node n : list) {
                        if (n.getAttribute("att1").equals("op")) {
                            checkAndWrite(n);
                        } else if (n.getAttribute("att1").equals("hyper")) {
                            if (!n.hasAttribute("done")) {
                                System.out.println("get here loop");
                                startLoop(n);
                                n.addAttribute("done", true);
                            }

                        }
                    }

                }
                //Old method of generating graph
                 /* } else {
                    for (int i = 0; i < mult_fold; i++) {
                        for (int j = 0; j <= plus_fold; j++) {
                            if (levelgraph.indexOf(list) > 1 && levelgraph.indexOf(list) < (levelgraph.size() - 1)) {
                                for (Node n : list) {
                                    if (n.getAttribute("att1").equals("op")) {

                                        List<Node> tmp = n.getAttribute("mirror");
                                        checkAndWrite(tmp.get(j + (total_its / mult_fold) * i));
                                    } else if (n.getAttribute("att1").equals("hyper")) {
                                        if (!n.hasAttribute("done")) {
                                            System.out.println("second loop");
                                            startLoop(n);
                                            n.addAttribute("done", true);
                                        }

                                    }
                                }

                            }
                            // out_c.append("\n");
                        }
                    }
                }*/
            }
            if (!fullunroll)
                out_c.append("}\n");

            // write_out_graphdot(graph, "loop_indexed", true, looplevel);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Prints out the lines of code to initialize the identified loop.
     *
     * @throws IOException
     */
    private void initLoop() throws IOException {
        // TODO Auto-generated method stub
        int incr = Integer.parseInt(graph.getAttribute("+incr").toString());
        int plus_fold;
        int mult_fold;
        if (fold) {
            plus_fold = this.plus_fold;
            mult_fold = this.mult_fold;
        } else {
            plus_fold = 0;
            mult_fold = 1;
        }
        String loopIterator = loopVariables.get(looplevel).getIterator();
        incr += plus_fold;
        out_c.append(
                "for( int " + loopIterator + " =" + initv + "; "
                        + loopIterator + " < "
                        + total_its / (mult_fold) + "; "
                        + loopIterator + "=" + loopIterator + "+"
                        + incr + "){\n");

        System.out.println("graph attributes -------");
        for (String s : graph.getEachAttributeKey())
            System.out.println("key " + s + " value : " + graph.getAttribute(s).toString());
        if (graph.hasAttribute("Pipeline"))
            out_c.append("#pragma HLS pipeline");
        out_c.append("\n");
        /*if (plus_fold != 0) {
            out_c.append("#pragma HLS unroll factor=" + (plus_fold + 1));
        }*/
        out_c.append("\n\n");

    }

    /**
     * Calls the algorithm to print out an identified inner loop.
     *
     * @param n NOde containing the subgraph of inner loop.
     * @throws IOException
     */
    private void startLoop(Node n) throws IOException {
        // TODO Auto-generated method stub
        out_c.append("\n // CreatingLoop\n");
        List<Graph> graphlist = graph.getAttribute(n.getId().toString());
        Graph struct = graphlist.get(graphlist.size() - 1);

        int N;
        if (struct.hasAttribute("size"))
            N = struct.getAttribute("size");
        else
            N = graphlist.size() - 1;
        int plus_fold = 0;
        int mult_fold = 1;
        int t_looplevel = 0;
        boolean fold;
        String type = graph.getAttribute("hyper_type");
        /*if (type == "parallel") {
        
        } else {
        }*/
        /*
        plus_fold = graph.getAttribute("plus_fold");
        mult_fold = graph.getAttribute("mult_fold");
        t_looplevel = looplevel + 1;
        fold = false;
        */

        plus_fold = struct.getAttribute("plus_fold");
        mult_fold = struct.getAttribute("mult_fold");
        t_looplevel = looplevel + 1;
        System.out.println("check");
        fold = true;

        loopVariables.add(new LoopNameAndIterator(struct.getAttribute("Loopname").toString(), "j"));
        Algorithm loop = new CGenerateLoopPruned(out_c, graphlist, struct, struct.getAttribute("levelgraph"),
                struct.getAttribute("maxlevel"), N, plus_fold,
                mult_fold,
                loopVariables,
                t_looplevel, fold);
        loop.init(struct);
        loop.compute();
        return;

    }

    /**
     * Prints out the line of code corresponding to an operation.
     *
     * @param n Node describing the operation.
     * @throws IOException
     */
    private void checkAndWrite(Node n) throws IOException {
        if (n.getAttribute("label").toString().equals("div"))
            n.setAttribute("label", "/");

        if (n.getAttribute("label").toString().equals("=") || n.getInDegree() < 2) {
            Edge res = n.getLeavingEdge(0);
            Edge in0 = n.getEnteringEdge(0);

            String name = "res";
            String name0 = "name0";
            if (res.hasAttribute("label"))
                name = res.getAttribute("label").toString();
            if (in0.hasAttribute("label"))
                name0 = in0.getAttribute("label").toString();
            else
                return;
            if (!fullunroll) {
                name = IndexVec(name, res);
                name0 = IndexVec(name0, in0);
            }
            if (n.hasAttribute("mod")) {
                if (n.getAttribute("mod").equals("sqr")) {
                    out_c.append(name + "="
                            + name0 + "*" + name0 + ";\n");
                }
            } else
                out_c.append(name + "="
                        + name0 + ";\n");
            return;
        }

        Edge res = n.getLeavingEdge(0);
        Edge in0 = n.getEnteringEdge(0);

        Edge in1 = n.getEnteringEdge(1);
        String name = "nop";
        if (res.hasAttribute("label")) {
            name = res.getAttribute("label").toString();

        } else {
            System.out.println("bad");
            for (String a : res.getEachAttributeKey()) {
                System.out.println(a);
            }
        }
        String name0 = in0.getAttribute("label").toString();
        String name1 = in1.getAttribute("label").toString();

        if (!fullunroll) {
            name = IndexVec(name, res);
            name0 = IndexVec(name0, in0);
            name1 = IndexVec(name1, in1);
        }
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

        if (in0.getAttribute("pos").toString().equals("l"))
            out_c.append(name + "=" + name0 + " " +
                    n.getAttribute("label").toString() + " " + name1 + ";\n");
        else
            out_c.append(name + "=" + name1 + " " +
                    n.getAttribute("label").toString() + " " + name0 + ";\n");
        return;
    }


    /**
     * Indexes an array access based on the loop.
     *
     * @param name label of the access.
     * @param e    Edge containing the variable.
     * @return label of the access with updated indexes.
     */
    private String IndexVec(String name, Edge e) {

        List<Integer> indexes = new ArrayList<>();
        String temp = null;
        if (name.contains("[")) {
            temp = e.getAttribute("label").toString();
            int dim = getIndexes(temp, indexes);
            String offset;
            offset = e.getAttribute("label").toString().substring(name.indexOf("["));
            String newIndex = new String();
            Edge struc_e = null;
            int novar = 0;
            if (!graph.hasAttribute("flat")) {
                struc_e = graph.getEdge(e.getAttribute("reproducable").toString());

            } else {
                struc_e = e;

            }
            if (struc_e != null) {

                List<Loopinfo> list = struc_e.getAttribute("loop");

                for (int i = 0; i < dim; i++) {

                    newIndex = newIndex.concat("[");
                    for (Loopinfo loop : list) {
                        for (LoopNameAndIterator entry : loopVariables) {
                            String loopName = entry.getLoopName();
                            String loopIterator = entry.getIterator();
                            if (loop.name.equals(loopName)) {
                                if (loop.increments.get(i) == 0) {
                                    novar++;
                                    break;
                                }
                                if (loop.increments.get(i) == 1) {
                                    newIndex = newIndex.concat(loopIterator + "+");
                                } else {
                                    newIndex = newIndex.concat("(" + loop.increments.get(i) + ")" + "*" + loopIterator + "+");
                                }


                            }
                        }

                    }

                    if (indexes.get(i) != 0 || (novar == loopVariables.size()))
                        newIndex = newIndex.concat(indexes.get(i).toString());
                    else
                        newIndex = newIndex.substring(0, newIndex.length() - 1);
                    novar = 0;
                    newIndex = newIndex.concat("]");
                }

                name = name.replace(offset, newIndex);
            }
        }

        return name;
    }


    /**
     * Isolate indexes of array access. Works for multiple dimensions.
     *
     * @param name    label of the access.
     * @param indexes list to store  identified indexes.
     * @return dimension of array.
     */
    public int getIndexes(String name, List<Integer> indexes) {
        String temp = name;
        int dim = 0;
        while (temp.lastIndexOf("[") != -1) {

            temp = temp.substring(temp.indexOf("["));
            String temp2 = temp.substring(1, temp.indexOf("]"));

            indexes.add(Integer.parseInt(temp2));
            temp = temp.substring(temp.indexOf("]"));

            dim++;
        }
        return dim;

    }

    public void write_out_graphdot(Graph g, String name, boolean stay, int l) throws IOException {
        FileSinkDOT output_dot = new FileSinkDOT();
        String filename = "..\\work\\Graph_snapshot\\";
        filename = filename.concat(name + "_" + l + ".dot");
        Graph snapshot = Graphs.clone(g);
        if (snapshot.hasAttribute("size"))
            snapshot.removeAttribute("size");
        snapshot.write(output_dot, filename);
        if (stay)
            return;
        else if (g.hasAttribute("hierarchy")) {
            List<String> hire = new ArrayList<>();
            hire = g.getAttribute("hierarchy");

            for (String s : hire) {
                List<Graph> graphlist = g.getAttribute(s);
                Graph temg = graphlist.get(graphlist.size() - 1);
                write_out_graphdot(temg, name, stay, l + 1);
            }
        }

        return;
    }
}
