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
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.graphoptimizations.*;
import pt.up.fe.specs.utils.AddStartAndEnd;
import pt.up.fe.specs.printers.CFunctionPrinter;
import pt.up.fe.specs.printers.CPrinter;
import pt.up.fe.specs.utils.GraphComparator;

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
     * Method that applies the initialization algorithms for the graph before the pruning. 
     * Currently adds the start and end nodes, removes local vectors, and sets att3 on operations. 
     * 
     * @param graph
     *            Graph to be manipulated
     * @return changed Graph
     */
    public Graph Initializations(Graph graph, Config config) {
        AddStartAndEnd addStartAndEnd = new AddStartAndEnd();
        addStartAndEnd.init(graph);
        addStartAndEnd.compute();
        Algorithm lvecprun = new LocalVectorPruning();
        lvecprun.init(graph);
        lvecprun.compute();
        Algorithm setOpTypesAlgo = new SetOperationTypes();
        setOpTypesAlgo.init(graph);
        setOpTypesAlgo.compute();
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
    public CInfo InfoInit(Graph graph, Config config) throws IOException {

        CInfo info;
        // Potential alternative Info method tested (not used)

        Algorithm getinfo = new CInfoAlgorithm();
        getinfo.init(graph);
        getinfo.compute();
        info = ((CInfoAlgorithm) getinfo).getInfo();

        info = ioFromGson(info, config);
        graph.setAttribute("info", info);
        return info;

    }

    public Graph runParallelMatching(Graph graph) {
        Node endNode = graph.getNode("End");
        runAllSubgraphsAlgorithm(graph);
        orderSubgraphs(graph);

        return graph;
    }

    public Graph runSequencialMatching(Graph graph) {
        return graph;
    }

    public Graph runWeightAlgorithm(Graph graph) {
        Algorithm weightAlgorithm = new WeightAlgorithm();
        weightAlgorithm.init(graph);
        weightAlgorithm.compute();
        return graph;
    }

    public Graph runAllSubgraphsAlgorithm(Graph graph) {
        Algorithm allSubgraphs = new AllSubgraphsAlgorithm();
        allSubgraphs.init(graph);
        allSubgraphs.compute();
        return graph;
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

        Algorithm prune = new PruneInterNode();
        prune.init(graph);
        prune.compute();
        return graph;
    }


    /**
     * Call the graph leveling algorithm.
     * 
     * @param graph
     *            Graph to be leveled
     * @return leveled Graph
     */
    public Graph leveling(Graph graph) {

        Algorithm level = new LevelingAlgorithmExt();
        level.init(graph);
        level.compute();
        if (graph.hasAttribute("HyperNode")) {
            leveling(((ArrayList<Graph>) graph.getAttribute("HyperNode")).get(0));
        }

        return graph;

    }

    public void runFolding(Graph graph) {
        FoldingAlgorithm foldingAlgorithm = new FoldingAlgorithm();
        foldingAlgorithm.init(graph);
        foldingAlgorithm.compute();
    }

    public void orderSubgraphs(Graph graph) {
        ArrayList<Graph> subgraphs = graph.getAttribute("HyperNode");
        subgraphs.sort(new GraphComparator());
    }



    /**
     * Call the algorithm to generate C output.
     * 
     * @param graphs
     *            Graphs to output. Each graph represents a function that is the upper level of the hierarchy.
     * @param path
     *            path to place result
     * @param config
     *            user defined config.
     * @throws IOException
     */
    public void writeC(GraphsWrapper graphs, String path, Config config) throws IOException {

        try {
            File file = new File(path);
            System.out.println("Path: " + path);
            BufferedWriter outBuffer = new BufferedWriter(new FileWriter(file));
            for(Graph graph : graphs.getAllGraphs()) {
                CPrinter printer = new CFunctionPrinter(outBuffer, graph, config);
                printer.print();
            }
            outBuffer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Wrong path: " + path);
            e.printStackTrace();
            System.exit(0);
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

    private String parseVarName(String name) {
        if (name.contains("["))
            return name.substring(0, name.indexOf("["));
        else
            return name;
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
    public CInfo ioFromGson(CInfo info, Config config) throws IOException {
        List<Integer> indexes;
        int dim;
        if (config.inputs.size() != config.input_types.size()) {
            throw new IOException("Inputs and Input types do not match");
        }
        if (config.outputs.size() != config.output_types.size()) {
            throw  new IOException("Outputs and Output types do not match");
        }
        for (int i = 0; i < config.inputs.size(); i++) {
            String input = config.inputs.get(i);
            String inputVarName = parseVarName(input);
            indexes = new ArrayList<>();
            dim = getIndexes(input, indexes);
            VarIO in = new VarIO(config.input_types.get(i), inputVarName, dim > 0, dim, indexes);
            info.addInput(in);
        }
        for (int i = 0; i < config.outputs.size(); i++) {
            String output = config.outputs.get(i);
            String outputVarName = parseVarName(output);
            indexes = new ArrayList<>();
            dim = getIndexes(output, indexes);
            VarIO out = new VarIO(config.output_types.get(i), outputVarName, dim > 0, dim, indexes);
            info.addOutput(out);
        }

        return info;
    }


    public Graph parallelizeSums(Graph graph) {
        Algorithm parallelizeSumsAlgo = new ParallelizeSums();
        parallelizeSumsAlgo.init(graph);
        parallelizeSumsAlgo.compute();
        return graph;
    }

    public Graph updateVarLabels(Graph graph) {
        Algorithm updateVarLabels = new UpdateVarLabels();
        updateVarLabels.init(graph);
        updateVarLabels.compute();
        return graph;
    }



}
