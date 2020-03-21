package pt.up.fe.specs; /**
 * Copyright 2018 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.stream.file.FileSinkDOT;
import pt.up.fe.specs.graphoptimizations.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The main method of the tool. In charge of correctly calling and applying correctly the algorithms of the the tool.
 * 
 * @author Afonso
 *
 */
public class LaunchAlgorithm {

    private static int maxlevel;
    private static List<List<Node>> levelgraph = new ArrayList<>();
    private boolean separated = false;
    private boolean parallel_access_optimized = false;
    private boolean only_IO = false;
    private static int outputdim = 0;
    private boolean done_div = false;
    private boolean pipe_again = false;
    private String output_name = null;
    private boolean complete_fold = true;
    private int maxDSP = 200;

    /**
     * Method that read the configuration JSON file
     * 
     * @param file
     *            configuration Gson file
     * @return the configuration file
     * @throws FileNotFoundException
     */
    public Config getConfiguration(File file) throws FileNotFoundException {
        Config config;
        Reader reader = new FileReader(file);
        Gson gson = new GsonBuilder().create();
        config = gson.fromJson(reader, Config.class);
        System.out.println(config.outputs);
        return config;

    }

    /**
     * Method that applies the initialization algorithms for the graph before the pruning. Currently the only applied
     * algorithm is removing local vectors.
     * 
     * @param graph
     *            Graph to be manipulated
     * @return changed Graph
     */
    public Graph Initializations(Graph graph) {

        Algorithm lvecprun = new LocalVectorPruning();
        lvecprun.init(graph);
        lvecprun.compute();

        return graph;

    }

    /**
     * Calls the Algorithm to get information on variables. Gets input information from config file.
     * 
     * @param graph
     *            Graph to get information
     * @param config
     *            configuration file for the Graph
     * @return The obtained information
     */
    public CInfo InfoInit(Graph graph, Config config) {

        CInfo info;
        // Potential alternative Info method tested (not used)

        Algorithm getinfo = new CInfoAlgorithm();
        getinfo.init(graph);
        getinfo.compute();
        info = ((CInfoAlgorithm) getinfo).getInfo();

        info = io_fromGson(info, config);
        return info;

    }

    /**
     * 
     * launches the pruning algorithm.
     * 
     * @param graph
     *            Graph to be pruned
     * @return pruned Graph
     */
    public Graph Prune(Graph graph) {

        add_start_end(graph);
        Algorithm prune = new PruneInterNode();
        prune.init(graph);
        prune.compute();
        return graph;
    }

    /**
     * Calls the algorithm to update the information file on a graph.
     * 
     * @param graph
     *            Graph to analyze
     * @param info
     *            Cinfo object to update
     * @return updated information
     */
    public CInfo InfoUpdate(Graph graph, CInfo info) {
        System.out.println(" ");
        System.out.println("--------Updating information for writing--------------");
        Algorithm getinfo = new EdgeInfoUpdateFlat(info);
        getinfo.init(graph);
        getinfo.compute();
        System.out.println("-----Finished updating information for writing--------------");
        System.out.println(" ");
        return info;

    }

    /**
     * Call the graph leveling algorithm.
     * 
     * @param graph
     *            Graph to be leveled
     * @return leveled Graph
     */
    public Graph Leveling(Graph graph) {

        Algorithm level = new LevelingAlgorithmExt();
        level.init(graph);
        level.compute();
        maxlevel = ((LevelingAlgorithmExt) level).getLevel();
        levelgraph.addAll(((LevelingAlgorithmExt) level).getLevelGraph());

        return graph;

    }

    /**
     * Checks viability for input graph to be pipelined. If demanded identifies the variable to lead the pipelining.
     * Then applies the pipelining algorithm at correct graph level.
     * 
     * @param graph
     *            Graph to be pipelined.
     * @param var
     *            name of variable to lead pipelining if declared.
     * @param check
     *            signal that the variable to lead pipelining should identified through searching.
     * @return resulting Graph.
     */
    public Graph pipeline(Graph graph, String var, boolean check) {
        System.out.println("");
        System.out.println("----------Starting Graph pipelining--------------");

        if (parallel_access_optimized) {
            System.out.println("Redundant memory accesses optimized. Cannot pipeline");
            System.out.println("");
            System.out.println("----------Finished Graph pipelining--------------");
            return graph;

        }
        if (outputdim > 1) {
            System.out.println(" Cannot pipeline 2D");
            System.out.println("");
            System.out.println("----------Finished Graph pipelining--------------");
            return graph;
        }

        if (check) {

            System.out.println("Acquiring pipeline variable ");
            var = get_pipeline_var(graph, var);
        }

        System.out.println("Starting pipelining ");
        if (!graph.hasAttribute("trim") && !pipe_again && complete_fold) {

            graph = pipelineAlg(graph, var, 0, "_upper");
            pipe_again = true;
        } else {

            List<Graph> list = graph.getAttribute("HyperNode");
            Graph graph2 = list.get(list.size() - 1);
            graph2 = pipelineAlg(graph2, var, 1, "_inner");
            list.set(list.size() - 1, graph2);
            graph.setAttribute("HyperNode", list);

        }
        System.out.println("");
        System.out.println("----------Finished Graph pipelining--------------");
        return graph;
    }

    /**
     * Selects the lowest hierarchy of the graph and call the algorithm to detect the variable for pipelining.
     * 
     * @param g
     *            Graph to be analyzed.
     * @param var
     *            user indicated variable.
     * @return the variable chosen by the algorithm.
     */
    private String get_pipeline_var(Graph g, String var) {
        // TODO Auto-generated method stub

        if (g.hasAttribute("hierarchy")) {
            List<String> hire = new ArrayList<>();
            hire = g.getAttribute("hierarchy");

            for (String s : hire) {
                List<Graph> graphlist = g.getAttribute(s);
                Graph temg = graphlist.get(graphlist.size() - 1);
                var = get_pipeline_var(temg, var);
            }
        } else {
            Algorithm check_var = new DetectMostWrittenSeqVariable();
            check_var.init(g);
            check_var.compute();
            var = ((DetectMostWrittenSeqVariable) check_var).getVar();

        }
        return var;
    }

    /**
     * Separates the input graph in subgraphs for every output value. First basic sequences are separated and ordered.
     * Then common nodes are separated from unique nodes and the hierarchy is established.
     * 
     * @param graph
     *            Graph to be separated.
     * @param info
     * @param out
     *            Output of the graph defined by the user in the configurations.
     * @return A graph with the common nodes and a node storing the unique graphs.
     * @throws IOException
     */
    public Graph separateGraph(Graph graph, CInfo info, String out) throws IOException {
        System.out.println("");
        System.out.println("----------Acquiring separate dataflows per output--------------");
        out = output_name;
        if (graph.getNode("End").getInDegree() > 1) {
            List<Graph> basicgraphlist = new ArrayList<>();
            Algorithm sequencing = new DetectBasicSequence();
            while (!((DetectBasicSequence) sequencing).isComplete()) {
                sequencing.init(graph);
                if (!((DetectBasicSequence) sequencing).isComplete()) {
                    sequencing.compute();
                    basicgraphlist.add(((DetectBasicSequence) sequencing).getGraph());

                }
            }
            System.out.println("trimming sequence that do no end in valid outputs");
            trimNonOutputs(basicgraphlist, out);
            System.out.println(basicgraphlist.size() + " separate sequences identified");
            System.out.println("ordering sequences");
            OrderSequences(info, basicgraphlist);

            int c = 0;
            // for (Graph ga : basicgraphlist) {
            // write_out_graphdot(ga, "Improved_graphs\\slide_basic" + c, false, 0);
            // c++;
            // }
            System.out.println("Identifying common and unique nodes per sequence");
            Algorithm separate = new SeparatePruned(basicgraphlist);
            separate.init(graph);
            separate.compute();

            List<Graph> separategraphlist = new ArrayList<>();
            separategraphlist = ((SeparatePruned) separate).getListGraph();
            graph = ((SeparatePruned) separate).getCommonGraph();
            // write_out_graphdot(graph, "Improved_graphs\\slide_common", true, 0);
            System.out.println("Start end for separate disabled");
            separated = true;
            c = 0;
            for (Graph ga : separategraphlist) {
                // add_start_end(ga);
                // write_out_graphdot(ga, "Improved_graphs\\slide_sep" + c, false, 0);
                c++;
            }

        }
        System.out.println("--- Finalized acquiring separate dataflows per output--------------");
        System.out.println("");
        return graph;

    }

    /*Out-dated  separet folding algorithhm
    public Graph separeteFold(Graph graph) {
    
        if (separated) {
            Graph struct = new DefaultGraph("struct");
            Algorithm match = new FullMatchAndFoldPruned(
                    graph.getAttribute("HyperNode"));
            match.init(struct);
            match.compute();
    
        }
        return graph;
    }*/

    /**
     * Calls the parallel folding algorithm.
     * 
     * 
     * @param graph
     *            Graph to match.
     * @param opt
     * @return matched graph.
     * @throws IOException
     */
    public Graph separeteFoldExt(Graph graph, boolean opt) throws IOException {
        boolean full = true;
        if (separated) {
            System.out.println("");
            System.out.println("----------Started parallel matching--------------");
            /*
            Graph struct = new DefaultGraph("struct");
            List<Graph> graphlist = graph.getAttribute("HyperNode");
            Algorithm match = new FullMatchFoldExt(
                    graphlist, 0);
            match.init(struct);
            match.compute();
            struct = ((FullMatchFoldExt) match).getSubgraph();
            if (!((FullMatchFoldExt) match).isComplete()) {
                complete_fold = false;
                System.out.println("Heree");
                graph = ((FullMatchFoldExt) match).handleConflict(graph);
             */
            System.out.println("----Starting fold 1�----");
            graph = firstFold(graph);
            System.out.println("----Finished fold 1�----");
            // }
            // write_out_graphdot(graph, "Improved_graphs\\slide_first_fold", false, 0);
            // struct.display();
            // Second folding for outputs with multiple dimensions. Currently always done.
            // if (outputdim > 1 && full) {
            /*
            System.out.println("Second folding");
            Graph test = Graphs.clone(graph);
            Graph t_struct = new DefaultGraph("struct");
            List<Graph> t_graphlist = test.getAttribute("HyperNode");
            match = new FullMatchFoldExt(t_graphlist, 1);
            struct.removeAttribute("Pipeline");
            match.init(struct);
            match.compute();
            t_struct = ((FullMatchFoldExt) match).getSubgraph();
            t_struct = ((FullMatchFoldExt) match).mergeInnerLoop(t_graphlist.get(0));
            graphlist.set(graphlist.size() - 1, t_struct);
            graph.setAttribute("HyperNode", graphlist);
            // write_out_graphdot(graph, "Improved_graphs\\second_fold", false, 0);
            // t_struct.display();*/
            for (int i = 1; i < outputdim; i++) {
                System.out.println("----Starting fold " + i + "�----");
                graph = innerFold(graph);
                System.out.println("----Finished fold " + i + "�----");
            }

        }

        System.out.println("----------Finished parallel matching--------------");
        System.out.println("");
        return graph;

    }

    /**
     * Calls the folding algorithm for parallel sequences. Used specifically to develop first loop.
     * 
     * @param graph
     *            Graph containing node with sequences to match
     * @return changed Graph
     */
    public Graph firstFold(Graph graph) {

        Graph struct = new DefaultGraph("struct");
        List<Graph> graphlist = graph.getAttribute("HyperNode");
        Algorithm match = new FullMatchFoldExt(
                graphlist, 0);
        match.init(struct);
        match.compute();
        if (!((FullMatchFoldExt) match).isSuccess()) {
            graph = ((FullMatchFoldExt) match).revertSeparation(graph);
            graph.display();
            return graph;
        }

        struct = ((FullMatchFoldExt) match).getSubgraph();
        if (!((FullMatchFoldExt) match).isComplete()) {
            complete_fold = false;
            graph = ((FullMatchFoldExt) match).handleConflict(graph);
        }
        return graph;
    }

    /**
     * Calls the parallel folding algorithm to generate inner loops.
     * 
     * @param graph
     *            Graph to match
     * @return changed Graph
     */
    public Graph innerFold(Graph graph) {
        if (!graph.hasAttribute("hierarchy")) {
            Graph t_struct = new DefaultGraph("struct");
            List<Graph> t_graphlist = new ArrayList<>();
            t_graphlist.add(graph);
            Algorithm match = new FullMatchFoldExt(t_graphlist, 1);
            match.init(t_struct);
            match.compute();
            t_struct = ((FullMatchFoldExt) match).getSubgraph();
            t_struct = ((FullMatchFoldExt) match).mergeInnerLoop(graph);
            if (t_struct.hasAttribute("Pipeline"))
                t_struct.removeAttribute("Pipeline");
            return t_struct;
        }

        List<Graph> graphlist = new ArrayList<>();
        List<String> hierarchy = graph.getAttribute("hierarchy");
        for (String s : hierarchy) {

            Node n = graph.getNode(s);
            graphlist = graph.getAttribute(n.getId());
            Graph temg = graphlist.get(graphlist.size() - 1);

            temg = innerFold(temg);
            graphlist.set(graphlist.size() - 1, temg);
            graph.setAttribute(s, graphlist);
        }
        return graph;
    }

    /**
     * Calls the algorithm to optimize the redundant accesses. Selects the lowest level of hierarchy.
     * 
     * @param graph
     *            Graph to optimize.
     * @param opt
     * @param arithmetic
     *            boolean to apply arithmetic optimization before optimizing the memory accesses.
     * @return
     */
    public Graph parallelAccessOptimization(Graph graph, boolean opt, boolean arithmetic) {
        if (!opt)
            return graph;
        System.out.println("");
        System.out.println("----------Checking loop for redundant memory  accesses--------------");
        System.out.println("");
        System.out.println("--------------Applying dataflow for optimizations--------------");
        if (arithmetic) {
            graph = ArithmeticOpt(graph, true);
        } else {
            System.out.println("");
            System.out.println("No new optimizations needed to be checked");
        }
        System.out.println("---------- Finalized applying for dataflow Optimizations ---------");
        System.out.println("");
        // Hierarchical list of all subgraphs

        System.out.println("");
        System.out.println("----------Checking for memory redundancy--------------");
        List<Graph> hierarchy = new ArrayList<>();
        hierarchy.add(graph);
        while (hierarchy.get(hierarchy.size() - 1).getNode("HyperNode") != null) {
            List<Graph> graphlist = hierarchy.get(hierarchy.size() - 1).getAttribute("HyperNode");
            if (graphlist.get(graphlist.size() - 1).getNode("HyperNode") != null) {
                hierarchy.add(graphlist.get(graphlist.size() - 1));
                continue;
            }

            Algorithm acc = new RedundantAccessOptimization(
                    graphlist.get(graphlist.size() - 1).getAttribute("Loopname"), "HyperNode", hierarchy);
            acc.init(graphlist.get(graphlist.size() - 1));
            if (((RedundantAccessOptimization) acc).isPossible()) {
                parallel_access_optimized = true;
                System.out.println("redundancy requiring buffer addition detected");
            }
            acc.compute();
            break;
        }

        if (parallel_access_optimized) {
            System.out.println("-----Finalized Checking loop for redundant memory  accesses---------");
            System.out.println("");
            return hierarchy.get(0);
        } else {
            System.out.println("No redundancy requiring buffer addition detected");
            System.out.println("Reverting loop to state before checking loop for memory redundancy");
            System.out.println("-----Finalized Checking loop for redundant memory  accesses---------");
            System.out.println("");
            return graph;
        }
    }

    /**
     * Calls pipelining algorithm.
     * 
     * @param graph
     *            Graph to be pipelined.
     * @param var
     *            name of variable to lead pipeline.
     * @param level
     *            starting level of pipelining.
     * @param id
     *            String used to name the pipeline.
     * @return
     */
    public Graph pipelineAlg(Graph graph, String var, int level, String id) {
        Algorithm pipeline = new PipelineByVariable(var, false, true, level, id);
        pipeline.init(graph);
        pipeline.compute();
        if (((PipelineByVariable) pipeline).getSuccess()) {
            Graph result = ((PipelineByVariable) pipeline).mergeResult();
            return result;
        } else {
            System.out.println("Failed pipelining");
            return graph;
        }
    }

    /**
     * Call the algorithm to generate C output.
     * 
     * @param graph
     *            Graph to output. Has to be the upper level of the hierarchy.
     * @param info
     *            Variable information obtained for the variable initializations.
     * @param path
     *            path to place result
     * @param file
     *            name of the output C code.
     * @param loadstore
     *            user defined number of concurrent load/stores.
     * @throws IOException
     */
    public void writeC(Graph graph, CInfo info, String path, String file, int loadstore) throws IOException {
        Algorithm genClevel = new CGenerateSimpleHierarchyPruned(info, path,
                file, graph.getAttribute("maxlevel"),
                graph.getAttribute("levelgraph"), loadstore);
        genClevel.init(graph);
        genClevel.compute();
    }

    /**
     * Add Start and End nodes to beginning and end of the dataflow.
     * 
     * @param g
     *            Graph to change.
     */
    private static void add_start_end(Graph g) {
        Node startNode = g.getNode("Start");
        Node endNode = g.getNode("End");
        if (startNode == null) {
            startNode = g.addNode("Start");
            startNode.addAttribute("label", "start");
            startNode.addAttribute("att1", "nop");
        }
        if (endNode == null) {
            endNode = g.addNode("End");
            endNode.addAttribute("label", "end");
            endNode.addAttribute("att1", "nop");
        }
        for (Node n : g.getEachNode()) {
            if (n.getInDegree() == 0 && !n.equals(startNode) && !n.equals(endNode))
                g.addEdge("begin to:" + n.getId(), startNode, n, true);
            else if (n.getOutDegree() == 0 && !n.equals(endNode) && !n.equals(startNode))
                g.addEdge("end from:" + n.getId(), n, endNode, true);

        }
    }

    /**
     * Removes edges between Star and End node and cons nodes.
     * 
     * @param g
     *            Graph to change.
     */
    private static void removeStartEndBetweenConst(Graph g) {
        for (Node n : g.getEachNode()) {
            if (n.hasAttribute("att1")) {
                if (n.getAttribute("att1").toString().equals("const")
                        && n.hasEdgeFrom(g.getNode("Start").getIndex())) {
                    Edge e = g.getEdge("begin to:" + n.getId());
                    g.removeEdge(e);

                }

                else if (n.getAttribute("att1").toString().equals("const")
                        && n.hasEdgeToward(g.getNode("End").getIndex())) {
                    Edge e = g.getEdge("end from:" + n.getId());
                    g.removeEdge(e);
                }
            }
        }
    }

    /**
     * Removes basic sequences that do not end in a user defined output.
     * 
     * @param graphlist
     *            list of separate dataflows.
     * @param output
     *            output of the graph from the user configurations.
     */
    private void trimNonOutputs(List<Graph> graphlist, String output) {
        List<Graph> removelist = new ArrayList<>();

        for (Graph graph : graphlist) {
            Node out = graph.getNode("End").getEnteringEdge(0).getNode0();
            // System.out.println(out.getAttribute("name").toString());
            if (!out.getAttribute("name").equals(output)) {
                removelist.add(graph);
            }

        }

        for (Graph i : removelist)
            graphlist.remove(i);

    }

    /**
     * Orders basic sequences in case of multiple outputs. In case of outputs being array accesses these are oder based
     * on the index
     * 
     * @param info
     * @param graphlist
     *            list of separate sequences to order.
     */
    private static void OrderSequences(CInfo info, List<Graph> graphlist) {
        List<VarIO> outputs = new ArrayList<>();
        for (VarIO io : info.io_info) {

            if (io.getOutput())
                outputs.add(io);
        }

        if (outputs.get(0).getDim() == 1) {
            outputdim = 1;
            int startindex = 0;
            for (VarIO io : outputs) {
                List<Integer> size = io.getSize();
                for (int j = 0; j < size.get(0); j++) {
                    for (int i = startindex; i < graphlist.size(); i++) {
                        Node out = graphlist.get(i).getNode("End").getEnteringEdge(0).getNode0();
                        String name;
                        int index;
                        if (io.isArray()) {
                            // System.out.println(io.getName());
                            name = out.getAttribute("label").toString().substring(0,
                                    out.getAttribute("label").toString().indexOf("["));
                            index = getIndexVec(out.getAttribute("label").toString());
                            if (index == j && name.equals(io.getName())) {
                                graphlist.set(startindex, out.getGraph());
                                break;
                            }

                        } else
                            name = out.getAttribute("label").toString();

                        if (name.equals(io.getName())) {
                            graphlist.set(startindex, out.getGraph());
                            break;
                        }
                    }
                    startindex++;
                }
            }

        } else if (outputs.get(0).getDim() == 2 && outputs.size() == 1) {
            outputdim = 2;
            int startindex = 0;

            int size = graphlist.size();

            for (int j = 0; j < size; j++) {
                int min = 100000000;
                for (int i = startindex; i < graphlist.size(); i++) {
                    Node out = graphlist.get(i).getNode("End").getEnteringEdge(0).getNode0();
                    String name;

                    // int index;
                    // System.out.println(io.getName());
                    List<Integer> indexes = new ArrayList<>();
                    name = out.getAttribute("label").toString().substring(0,
                            out.getAttribute("label").toString().indexOf("["));
                    getIndexes(out.getAttribute("label").toString(), indexes);
                    int pos = indexes.get(0) + (size / 2) * indexes.get(1);

                    if (pos < min && name.equals(outputs.get(0).getName())) {

                        Graph aux = graphlist.get(j);
                        graphlist.set(j, out.getGraph());
                        graphlist.set(i, aux);
                        min = pos;
                        // break;

                    }
                }
                startindex++;
            }
        }

    }

    /**
     * Acquire integer indexes from array access. Works for multiple dimensions.
     * 
     * @param name
     *            String of array access.
     * @param indexes
     *            list to store identified indexes.
     * @return the dimension of the array.
     */
    public static int getIndexes(String name, List<Integer> indexes) {
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

    /**
     * Acquire integer indexes from array access. Works for one dimensions.
     * 
     * @param name
     * @return
     */
    private static int getIndexVec(String name) {
        String temp = name.substring(name.indexOf("["));
        String temp2 = temp.substring(1, temp.length() - 1);

        return Integer.parseInt(temp2);

    }

    /**
     * prints all the nodes in a leveled graph
     * 
     * @param levelgraph
     *            the leveled graph to print.
     * @param graph
     *            the Graph of the the dataflow to traverse the hierarchy.
     */
    private static void printLevelGraph(List<List<Node>> levelgraph, Graph graph) {

        for (List<Node> list : levelgraph) {
            System.out.println("Nodes of level " + levelgraph.indexOf(list) + ":");
            for (Node n : list) {
                System.out.println("     " + n.getId());
            }

        }

        if (!graph.hasAttribute("hierarchy"))
            return;

        List<String> hierarchy = graph.getAttribute("hierarchy");
        List<Graph> graphlist = new ArrayList<>();
        for (String h : hierarchy) {

            Node n = graph.getNode(h);
            graphlist = graph.getAttribute(n.getId());
            Graph temg = graphlist.get(graphlist.size() - 1);

            System.out.println(temg.getAttribute("Loopname").toString());
            printLevelGraph(temg.getAttribute("levelgraph"), temg);
        }

    }

    /**
     * Sets the folding factor based on user indicated minimum concurrent load/stores.
     * 
     * 
     * @param load_reads
     *            the user defined concurrent load/stores.
     * @param loopname
     *            The name of the loop to set the folding factor.
     * @param graph
     *            the dataflow Graph to analyze.
     * @param upper
     *            boolean to indicate if the Graph is the upper level of the dataflow.
     * @return
     */
    public boolean setFoldingByLoadStore(int load_reads, String loopname, Graph graph, boolean upper) {
        if (load_reads < 2)
            return true;
        int N = 0;
        int plus_fold = 0;
        List<Graph> graphlist = new ArrayList<>();
        if (!graph.hasAttribute("hierarchy"))
            return true;
        if (upper) {
            graph = Leveling(graph);
        }
        List<String> hierarchy = graph.getAttribute("hierarchy");
        if (!graph.hasAttribute("Pipeline")) {
            for (String s : hierarchy) {

                Node n = graph.getNode(s);
                graphlist = graph.getAttribute(n.getId());
                Graph temg = graphlist.get(graphlist.size() - 1);
                int mult = 0;
                List<Integer> check;
                check = checkAccess(temg);
                mult = check.get(0);
                plus_fold = check.get(1);
                N = temg.getAttribute("size");
                // System.out.println(N + " temg " + plus_fold + " mult " + mult);

                if (plus_fold == 0)
                    temg.setAttribute("plus_fold", plus_fold);
                else if (plus_fold > load_reads) {
                    temg.setAttribute("plus_fold", 0);
                    // System.out.println("No need to unfold");
                } else {
                    plus_fold = ((load_reads + plus_fold - 1) / plus_fold) - 1;
                    if (mult * plus_fold > maxDSP) {
                        for (int it = plus_fold; it > 0; it--) {
                            if (N % it == 0 && (mult * plus_fold < maxDSP)) {
                                plus_fold = it - 1;
                                break;
                            }
                        }

                    } else if (N % (plus_fold + 1) != 0) {
                        for (int it = plus_fold; it <= N; it++) {
                            if (N % it == 0) {
                                plus_fold = it - 1;
                                break;
                            }
                        }

                    }
                    temg.setAttribute("plus_fold", plus_fold);
                    // System.out.println(temg.getId() + " plus_fold " + plus_fold);
                }
                // temg.setAttribute("plus_fold", plus_fold);
                // temg.setAttribute("mult_fold", mult_fold);

                setFoldingByLoadStore(load_reads, loopname, temg, false);

            }
        }
        // System.out.println("done LoadStore folding");
        return true;
    }

    /**
     * Checks the amount of concurrent memory accesses of in a dataflow graph.
     * 
     * @param graph
     *            Graph to analyze.
     * @return List containing the folding factors.
     */
    public List<Integer> checkAccess(Graph graph) {
        int loc_maxlevel;
        int mult = 0;
        List<Integer> retunrlist = new ArrayList<>();
        int mult_temp = 0;
        List<List<Node>> loc_levelgraph = new ArrayList<>();
        loc_levelgraph = graph.getAttribute("levelgraph");
        loc_maxlevel = graph.getAttribute("maxlevel");
        int min = 1000;
        List<String> names = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        List<Integer> tempcount = new ArrayList<>();
        String name = null;
        String type = null;
        for (List<Node> level : loc_levelgraph) {
            mult_temp = 0;
            for (Node n : level) {

                if (n.getAttribute("att1").toString().equals("op")) {
                    if (n.getAttribute("label").equals("*")) {
                        mult_temp++;
                        type = n.getLeavingEdge(0).getAttribute("att3");
                    }
                    for (Edge e : n.getEachEnteringEdge()) {
                        if (e.hasAttribute("att2")) {
                            if (e.getAttribute("att2").equals("inte") && e.hasAttribute("array")) {
                                name = e.getAttribute("name").toString().concat("_in");
                                if (names.contains(name)) {
                                    tempcount.set(names.indexOf(name),
                                            tempcount.get(names.indexOf(name)) + 1);

                                } else {

                                    names.add(name);
                                    tempcount.add(1);
                                    count.add(1);
                                }
                            }
                        }
                    }
                    if (n.getOutDegree() > 0) {
                        Edge out = n.getLeavingEdge(0);
                        if (out.hasAttribute("att2")) {
                            if (out.getAttribute("att2").equals("inte") && out.hasAttribute("array")) {
                                name = out.getAttribute("name").toString().concat("_out");
                                if (names.contains(name)) {
                                    tempcount.set(names.indexOf(name),
                                            tempcount.get(names.indexOf(name)) + 1);

                                } else {

                                    names.add(name);
                                    tempcount.add(1);
                                    count.add(1);
                                }
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < count.size(); i++) {
                if (tempcount.get(i) > count.get(i)) {
                    count.set(i, tempcount.get(i));
                }
                tempcount.set(i, 0);
            }
            if (mult_temp > mult) {
                mult = mult_temp;
                // System.out.println("mult " + mult);
            }

        }
        min = 1000;
        retunrlist.add(mult);
        retunrlist.add(min);

        for (int j = 0; j < names.size(); j++) {
            if (count.get(j) < min) {
                min = count.get(j);
                retunrlist.set(1, min);
            }
            // System.out.println("name " + names.get(j) + " count " + count.get(j));
        }
        if (min == 1000) {
            retunrlist.set(1, 0);
            return retunrlist;
        } else
            return retunrlist;

    }

    /**
     * Manually set folding factor for specific loop. If loopname is null the factor is applied to all loops.
     * 
     * @param plus_fold
     *            factor of unfolding.
     * @param mult_fold
     *            (outdated factor unnecessary)
     * @param loopname
     *            name of loop to set the folding factors.
     * @param graph
     *            Graph apply the factors.
     * @return true if successful.
     */

    public boolean setFolding(int plus_fold, int mult_fold, String loopname, Graph graph) {

        if (mult_fold < 1) {
            System.out.println("Error mult_fold must be one");
            return false;
        }
        int N = 0;
        List<Graph> graphlist = new ArrayList<>();
        if (!graph.hasAttribute("hierarchy"))
            return true;

        List<String> hierarchy = graph.getAttribute("hierarchy");

        for (String s : hierarchy) {
            Node n = graph.getNode(s);
            graphlist = graph.getAttribute(n.getId());
            Graph temg = graphlist.get(graphlist.size() - 1);

            if (temg.getAttribute("Loopname").equals(loopname) || loopname == null) {
                N = temg.getAttribute("size");
                if ((N % (mult_fold + plus_fold)) != 0) {
                    System.out.println("WARNING: Inadequate folding");
                }
                temg.setAttribute("plus_fold", plus_fold);
                temg.setAttribute("mult_fold", mult_fold);
                if (loopname == null)
                    if (!graph.hasAttribute("hyper_type"))
                        setFolding(plus_fold, mult_fold, loopname, temg);
                    else if (!graph.getAttribute("hyper_type").equals("pipeline")
                            || !temg.getAttribute("hyper_type").equals("pipeline"))
                        setFolding(plus_fold, mult_fold, loopname, temg);
                return true;
            } else
                setFolding(plus_fold, mult_fold, loopname, temg);

        }

        System.out.println("done folding");
        return true;
    }

    // Nt up
    /**
     * Calls the unfolding algorithm for the graph. The unfolding is done from the lowest level to the highest.
     * 
     * @param g
     *            Graph to unfold.
     * @param i
     *            Integer expressing level of the graph.
     * @param upper
     *            boolean indicating if graph is of upper level.
     * @param unfold
     *            boolean indicating if graph should be unfolded.
     * @return unfolded graph.
     */
    public Graph unfoldAll(Graph g, int i, boolean upper, boolean unfold, Config config) {
        g.addAttribute("flat", true);
        if (g.hasAttribute("hierarchy")) {

            List<String> hire = new ArrayList<>();
            hire = g.getAttribute("hierarchy");

            if (g.hasAttribute("hierarchy") && !parallel_access_optimized) {
                this.parallelAccessOptimization(g, config.acc, false);
            }

            for (String s : hire) {
                List<Graph> graphlist = g.getAttribute(s);
                Graph temg = graphlist.get(graphlist.size() - 1);

                if (g.hasAttribute("hyper_type")) {
                    if (!g.getAttribute("hyper_type").equals("pipeline")
                            || temg.getAttribute("hyper_type").equals("pipeline")) {

                        temg = unfoldAll(temg, i + 1, false, true, config);
                        graphlist.set(graphlist.size() - 1, temg);
                        g.setAttribute(s, graphlist);

                    } else {
                        temg = unfoldAll(temg, i + 1, false, false, config);
                        graphlist.set(graphlist.size() - 1, temg);
                        g.setAttribute(s, graphlist);
                    }
                } else {

                    temg = unfoldAll(temg, i + 1, false, true, config);
                    graphlist.set(graphlist.size() - 1, temg);
                    g.setAttribute(s, graphlist);
                }

            }
        }

        if (g.hasAttribute("Loopname") && unfold) {
            System.out
                    .println("Unfolding " + g.getAttribute("Loopname") + "  plus_fold " + g.getAttribute("plus_fold"));
            String variable = null;
            if (g.getAttribute("hyper_type").equals("pipeline")) {

                variable = g.getAttribute("connect");
            }

            Algorithm unfolding = new UnfoldingAlgorithm(g.getAttribute("Loopname"), g.getAttribute("plus_fold"),
                    g.getAttribute("hyper_type"), upper, "a" + i, variable, config.full_connect);
            unfolding.init(g);
            unfolding.compute();
            g = ((UnfoldingAlgorithm) unfolding).getGraph();

        }

        return g;

    }

    /**
     * Calls the algorithms for arithmetic optimizations. Currently these optimize divisions and accumulations chains.
     * 
     * @param graph
     *            Graph to be optimized
     * @param upper
     *            boolean indicating if Graph is of upper level.
     * @return changed Graph.
     */
    public Graph ArithmeticOpt(Graph graph, boolean upper) {
        if (upper) {
            if (!done_div) {
                Algorithm div = new DivisionOptAlg();
                div.init(graph);
                div.compute();
                graph = ((DivisionOptAlg) div).getGraph();
                done_div = true;
            }
        }

        if (!upper) {
            System.out.println("");
            System.out.println("--------------Checking dataflow for accumulations--------------");
            System.out.println("loop :" + graph.getAttribute("Loopname"));
            while (true) {
                Algorithm assoc = new PartialSumOptimization();
                assoc.init(graph);
                assoc.compute();
                if (((PartialSumOptimization) assoc).chainDetected()) {
                    graph = ((PartialSumOptimization) assoc).getGraph();
                    System.out.println("Accumulation balanced");
                } else {
                    System.out.println("--------------Stopping checking for accumalations-------------");
                    System.out.println("");
                    break;
                }
            }

        }
        if (!graph.hasAttribute("hierarchy"))
            return graph;

        List<Graph> graphlist = new ArrayList<>();
        List<String> hierarchy = graph.getAttribute("hierarchy");

        for (String s : hierarchy) {

            Node n = graph.getNode(s);
            graphlist = graph.getAttribute(n.getId());
            Graph temg = graphlist.get(graphlist.size() - 1);

            temg = ArithmeticOpt(temg, false);
            graphlist.set(graphlist.size() - 1, temg);
            graph.setAttribute(s, graphlist);
        }
        return graph;
    }

    /**
     * Calls the algorithms to unfold the graph and checks for arithmetic optimizations. The unfolding is done from the
     * lowest level to the highest.
     * 
     * @param g
     *            Graph to unfold and optimize.
     * @param i
     *            Integer expressing level of the graph.
     * @param upper
     *            boolean indicating if graph is of upper level.
     * @param unfold
     *            boolean indicating if graph should be unfolded.
     * @param config
     *            configuration file
     * @return unfolded graph.
     * @throws IOException
     */
    public Graph unfoldAllandArithmeticOPt(Graph g, int i, boolean upper, boolean unfold, Config config)
            throws IOException {
        g.addAttribute("flat", true);
        if (upper && config.arithmetic) {
            System.out.println("--- Checking for arithmetic Optimizations before folding ---");
            g = ArithmeticOpt(g, upper);

            System.out.println("---------- Finalized Checking for aritmetic Optimizations ---------");
            System.out.println("");
        }

        if (g.hasAttribute("hierarchy") && !parallel_access_optimized) {
            this.parallelAccessOptimization(g, config.acc, false);
        }

        if (g.hasAttribute("hierarchy")) {

            List<String> hire = new ArrayList<>();
            hire = g.getAttribute("hierarchy");
            for (String s : hire) {
                List<Graph> graphlist = g.getAttribute(s);
                Graph temg = graphlist.get(graphlist.size() - 1);

                if (g.hasAttribute("hyper_type")) {
                    if (!g.getAttribute("hyper_type").equals("pipeline")
                            || temg.getAttribute("hyper_type").equals("pipeline")) {
                        temg = unfoldAllandArithmeticOPt(temg, i + 1, false, unfold, config);
                        graphlist.set(graphlist.size() - 1, temg);
                        g.setAttribute(s, graphlist);
                    } else {
                        temg = unfoldAllandArithmeticOPt(temg, i + 1, false, false, config);
                        graphlist.set(graphlist.size() - 1, temg);
                        g.setAttribute(s, graphlist);
                    }
                } else {
                    temg = unfoldAllandArithmeticOPt(temg, i + 1, false, unfold, config);
                    graphlist.set(graphlist.size() - 1, temg);
                    g.setAttribute(s, graphlist);
                }
            }
        }

        if (g.hasAttribute("Loopname") && unfold) {

            System.out.println(" ");
            System.out.println("-------- Starting Loop unfolding -----------------------------");
            System.out
                    .println("Loopname :" + g.getAttribute("Loopname") + " || Looptype :" + g.getAttribute("hyper_type")
                            + " || Unfolding factor: " + g.getAttribute("plus_fold"));

            String variable = null;
            if (g.getAttribute("hyper_type").equals("pipeline")) {
                // System.out.println(" Time to connect ");
                variable = g.getAttribute("connect");
            }

            Algorithm unfolding = new UnfoldingAlgorithm(g.getAttribute("Loopname"), g.getAttribute("plus_fold"),
                    g.getAttribute("hyper_type"), upper, "a" + i, variable, config.full_connect);
            unfolding.init(g);
            unfolding.compute();
            g = ((UnfoldingAlgorithm) unfolding).getGraph();

            System.out.println("-------- Finalized Loop unfolding ------------------------------");
            System.out.println(" ");
        }

        System.out.println("---------- Checking for aritmetic Optimizations ------------------");
        if (config.arithmetic)
            g = ArithmeticOpt(g, upper);
        System.out.println("--------- Finalized Checking for aritmetic Optimizations ---------");
        System.out.println("");
        return g;

    }

    /**
     * Calls algorithm to check the graph for necessary memory partitioning based on concurrent memory accesses in the
     * dataflow.
     * 
     * @param g
     *            graph to analyze.
     * @param info
     *            current information to update file.
     * @return updated information file.
     */
    public CInfo fullPartition(Graph g, CInfo info) {
        Algorithm part = new FullPartition(g.getAttribute("levelgraph"), info);
        part.init(g);
        part.compute();
        info = ((FullPartition) part).getInfo();

        return info;
    }

    /**
     * Calls algorithm to check the graph for necessary memory partitioning based on total memory accesses.
     * 
     * @param g
     *            Graph to analyze
     * @param info
     *            information file to update.
     * @return updated information file.
     */
    public CInfo fullPartitionTotal(Graph g, CInfo info) {
        System.out.println("-------Checking graph for full memory partitioning-----------");
        Algorithm part = new FullPartitionNotLevel(g.getAttribute("levelgraph"), info, false);
        part.init(g);
        part.compute();
        info = ((FullPartitionNotLevel) part).getInfo();

        System.out.println("---Printing partition factors-------");

        for (VarIO io : info.io_info) {
            io.printPartitionFactors();
        }

        System.out.println("-----Finished Checking graph for full memory partitioning-----------");
        return info;
    }

    /**
     * Prints out dot description of a graph. If stay true only highest level printed. If false all lower level
     * subgraphs also printed.
     * 
     * @param g
     *            Graph to print out
     * @param name
     *            name of resulting dot file
     * @param stay
     *            boolean that indicates if lower levels should be also printed out.
     * @param l
     *            level of graph being printed out.
     * @throws IOException
     */
    public void write_out_graphdot(Graph g, String name, boolean stay, int l) throws IOException {
        FileSinkDOT output_dot = new FileSinkDOT(true);
        String filename = "..\\work\\Graph_snapshot\\";
        filename = filename.concat(name + "_" + l + ".dot");
        Graph snapshot = Graphs.clone(g);
        if (snapshot.hasAttribute("size"))
            snapshot.removeAttribute("size");
        shapeGraph(snapshot, false);
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

    /**
     * Inserts shape attributes in nodes based on node types.
     * 
     * @param g
     *            graph to shape
     * @param remove
     *            shaped graph
     */
    public static void shapeGraph(Graph g, boolean remove) {
        if (remove) {
            g.removeNode("Start");
            g.removeNode("End");
        }

        for (Node n : g.getEachNode()) {
            String att1 = n.getAttribute("att1");

            if (att1.equals("const")) {
                n.addAttribute("shape", "circle");
                continue;
            } else if (n.getInDegree() > 0) {
                if (n.getEnteringEdge(0).getNode0().getAttribute("att1").equals("nop")) {
                    n.addAttribute("shape", "square");
                    continue;
                }
            }
            if (att1.equals("nop"))
                n.addAttribute("shape", "house");
            else if (att1.equals("const"))
                n.addAttribute("shape", "circle");
            else if (att1.equals("var"))
                n.addAttribute("shape", "box");
            else if (att1.equals("hyper"))
                n.addAttribute("shape", "hexagon");

        }
        return;
    }

    /**
     * Get max level of leveled graph
     * 
     * @return the max level.
     */
    public int getMaxLevel() {
        return maxlevel;
    }

    /**
     * Get leveled graph.
     * 
     * @return
     */
    public List<List<Node>> getLevelgraph() {
        return levelgraph;
    }

    /**
     * Acquire input information from configuration file.
     * 
     * @param info
     *            info object to update.
     * @param config
     *            configurations
     * @return updated information.
     */
    public CInfo io_fromGson(CInfo info, Config config) {
        if (config.outputs.contains("["))
            output_name = new String(config.outputs.substring(0,
                    config.outputs.indexOf("[")));
        else
            output_name = new String(config.outputs);
        List<Integer> indexes = new ArrayList<>();
        int dim = getIndexes(config.outputs, indexes);
        VarIO out = new VarIO(config.output_type, output_name, config.outputs.contains("["), dim, indexes);
        out.setOutput(true);
        info.io_info.add(out);
        for (int i = 0; i < config.inputs.size(); i++) {
            String in_name;
            if (config.inputs.get(i).contains("["))
                in_name = new String(config.inputs.get(i).substring(0,
                        config.inputs.get(i).indexOf("[")));
            else
                in_name = new String(config.inputs.get(i));
            indexes = new ArrayList<>();
            dim = getIndexes(config.inputs.get(i), indexes);

            VarIO in = new VarIO(config.output_type, in_name, config.inputs.get(i).contains("["), dim, indexes);
            in.setInput(true);
            info.io_info.add(in);
        }
        return info;
    }

    /**
     * Isolates the name of an array.
     * 
     * @param name
     *            label of access.
     * @return concatenated label.
     */
    public String conc_vec(String name) {

        return name.substring(0,
                name.toString().indexOf("["));

    }
}
