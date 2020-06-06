package pt.up.fe.specs;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;

import java.io.File;
import java.io.IOException;

/**
 * pt.up.fe.specs.Launcher of application. Call the stages of the tool
 * 
 * @author Afonso
 *
 */
public class Launcher {

    public static void main(String args[]) throws IOException {
        System.out.println("Framework");
        // variables used for optimizations before configuration file
        int loadStores;
        boolean arithmetic ;
        boolean access;

        // Clock timer
        long startTime = System.currentTimeMillis();
        // Creating Graph
        Graph mainGraph = new MultiGraph("mainGraph");

        GraphsWrapper graphsWrapper = new GraphsWrapper(mainGraph);
        // Getting config file

        if(args.length < 1) {
            throw new Error("Indicate path to config file");
        }
        String configFilename = args[0];
        String path = "";
        if (configFilename.contains("\\"))
            path = configFilename.substring(0, configFilename.lastIndexOf("\\"));

        File configFile = new File(configFilename);
        if (!configFile.exists()) {
            System.out.println("File does no exist on path:");
            return;
        }
        // Creating main part of tool and reading config file
        LaunchAlgorithm launch = new LaunchAlgorithm();
        Config config = launch.getConfiguration(configFile);
        loadStores = config.loadstores;
        arithmetic = config.arithmetic;
        access = config.acc;
        // Getting Graph
        FileSource fs = FileSourceFactory
                .sourceFor(path + "\\" + config.graph);

        fs.addSink(mainGraph);

        fs.readAll(path + "\\" + config.graph);

        long initTime = System.currentTimeMillis();
        System.out.println("init time:" + (initTime - startTime));

        mainGraph = launch.Initializations(mainGraph, config);
        launch.InfoInit(mainGraph, config);
        mainGraph.addAttribute("functionName", config.outputFile);

        for (Graph graph: graphsWrapper.getAllGraphs()) {
            launch.Prune(graph);
            launch.leveling(graph);
            launch.parallelizeSums(graph);
            launch.leveling(graph);

            if (config.folding.equals("high") || config.folding.equals("medium")) {
                launch.runWeightAlgorithm(graph);
                long graphsMatchTime = System.currentTimeMillis();
                System.out.println("Starting parallel Matching");
                Graph functionGraph = launch.foldParallel(graph, config);
                graphsWrapper.addGraph(functionGraph);

                launch.runSequentialMatching(graph);

                System.out.println("Subgraphs clustering time:" + (graphsMatchTime - startTime));
                launch.leveling(graph);

            }
        }

        launch.updateVarLabels(mainGraph);
        //Update vars names with wire numbers before writing the code
        launch.writeC(graphsWrapper, path.concat("\\"+config.outputFile + ".c"), config);

        long endTime = System.currentTimeMillis();
        System.out.println("End time:" + (endTime - startTime));
        shapeGraph(mainGraph, false);
        mainGraph.display();
    }


    /**
     * Inserts shape attributes in nodes based on node types.
     * 
     * @param g
     *            graph to shape
     * @param remove
     *            boolean used to indicate wether the Start ENd nodes should be removed for the print out
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

}