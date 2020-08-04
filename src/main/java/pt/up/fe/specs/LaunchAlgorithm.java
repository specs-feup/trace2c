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
import pt.up.fe.specs.algorithms.*;
import pt.up.fe.specs.printers.CFunctionPrinter;
import pt.up.fe.specs.printers.CPrinter;
import pt.up.fe.specs.utils.NodeComparator;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * The main method of the tool. In charge of correctly calling and applying correctly the algorithms of the the tool.
 * 
 * @author Afonso
 *
 */
public class LaunchAlgorithm {

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


    public boolean foldParallel(Graph graph, Config config) {
        boolean hasFolded = false;
        List<HashSet<Node>> parallelSubgraphs = runAllSubgraphsAlgorithm(graph, config.maxNodesPerSubgraph);
        if (parallelSubgraphs.size() > 3) {
            FoldParallelSubgraphs foldParallelAlgorithm = new FoldParallelSubgraphs(parallelSubgraphs, config);
            foldParallelAlgorithm.init(graph);
            foldParallelAlgorithm.compute();
            leveling(graph);
            List<Graph> subgraphs = graph.getAttribute("subgraphs");
            for (Graph g: subgraphs) {
                System.out.println("Created subgraph with " + g.getAttribute("maxlevel") + " levels and " + g.getNodeCount() + " nodes");
            }
            hasFolded = true;
            /*
            if (graph.hasAttribute("subgraphs")) {

                ArrayList<Graph> subgraphs = graph.getAttribute("subgraphs");
                subgraphs.forEach( subgraph -> {
                    leveling(subgraph);
                    foldParallel(subgraph, config);
                });
            }*/
        }
        return hasFolded;
    }

    public Graph runWeightAlgorithm(Graph graph) {
        Algorithm weightAlgorithm = new WeightAlgorithm();
        weightAlgorithm.init(graph);
        weightAlgorithm.compute();
        return graph;
    }

    public  List<HashSet<Node>> runAllSubgraphsAlgorithm(Graph graph, int maxNodesPerSubgraph) {
        AllSubgraphsAlgorithm allSubgraphs = new AllSubgraphsAlgorithm(maxNodesPerSubgraph);
        allSubgraphs.init(graph);
        allSubgraphs.compute();
        return allSubgraphs.getMostRepeatableParallelSubgraph();
    }


    /**
     * Call the graph leveling algorithm.
     * 
     * @param graph
     *            Graph to be leveled
     * @return leveled Graph
     */
    public Graph leveling(Graph graph) {

        Algorithm level = new Leveling();
        level.init(graph);
        level.compute();
        if (graph.hasAttribute("subgraphs")) {
            ArrayList<Graph> subgraphs = graph.getAttribute("subgraphs");
            for (Graph subgraph: subgraphs) {
                leveling(subgraph);
            }
        }

        return graph;

    }



    /**
     * Call the algorithm to generate C output.
     * 
     * @param graph
     *            Graph to print.
     * @param path
     *            path to place result
     * @param config
     *            user defined config.
     * @throws IOException
     */
    public void writeC(Graph graph, String path, Config config) throws IOException {

        try {
            File file = new File(path);
            System.out.println("Path: " + path);
            BufferedWriter outBuffer = new BufferedWriter(new FileWriter(file));
            CPrinter printer = new CFunctionPrinter(outBuffer, graph, config);
            printer.print();

            outBuffer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Wrong path: " + path);
            e.printStackTrace();
            System.exit(0);
        }

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


    public Graph updateVarLabels(Graph graph) {
        Algorithm updateVarLabels = new UpdateVarLabels();
        updateVarLabels.init(graph);
        updateVarLabels.compute();
        return graph;
    }


    public Graph createEpilogue(Graph mainGraph) {
        CreateEpilogue createEpilogueAlgorithm = new CreateEpilogue();
        createEpilogueAlgorithm.init(mainGraph);
        createEpilogueAlgorithm.compute();
        leveling(mainGraph);
        return createEpilogueAlgorithm.getEpilogueGraph();
    }

    public Graph createPrologue(Graph mainGraph) {
        CreatePrologue createPrologueAlgorithm = new CreatePrologue();
        createPrologueAlgorithm.init(mainGraph);
        createPrologueAlgorithm.compute();
        leveling(mainGraph);
        return createPrologueAlgorithm.getPrologueGraph();
    }

    public void arithmeticOptimizations(Graph graph) {
        Algorithm arithmeticOpt = new ArithmeticOptimizations();
        arithmeticOpt.init(graph);
        arithmeticOpt.compute();
        if (graph.hasAttribute("subgraphs")) {
            List<Graph> subgraphs = graph.getAttribute("subgraphs");
            subgraphs.forEach(subgraph -> arithmeticOptimizations(subgraph));
        }
    }

    public void orderLevelGraph(Graph mainGraph) {
        List<List<Node>> levelGraph = mainGraph.getAttribute("levelgraph");
        for (List<Node> nodes : levelGraph) {
            nodes.sort(new NodeComparator());
        }
        List<Graph> subgraphs = mainGraph.getAttribute("subgraphs");
        if (subgraphs != null && subgraphs.size() > 0) {
            for (Graph subgraph: subgraphs) {
                orderLevelGraph(subgraph);
            }
        }
    }
}
