package pt.up.fe.specs;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import pt.up.fe.specs.algorithms.*;
import pt.up.fe.specs.utils.AddStartAndEnd;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
        // Getting Graph
        FileSource fs = FileSourceFactory
                .sourceFor(path + "\\" + config.graph);

        fs.addSink(mainGraph);
        fs.readAll(path + "\\" + config.graph);
        mainGraph.addAttribute("config", config);
        mainGraph.addAttribute("functionName", config.outputFile);

        long initTime = System.currentTimeMillis();
        System.out.println("init time:" + (initTime - startTime));


        Queue<Algorithm> algorithmsQueue = new LinkedList<>();
        algorithmsQueue.add(new AddStartAndEnd());
        algorithmsQueue.add(new LocalVectorPruning());
        algorithmsQueue.add(new SetVarsAttributes());
        algorithmsQueue.add(new InfoFromConfig());
        algorithmsQueue.add(new Pruning());
        algorithmsQueue.add(new Leveling());
        algorithmsQueue.add(new ParallelizeSums());

        while(!algorithmsQueue.isEmpty()) {
            Algorithm algorithm = algorithmsQueue.remove();
            algorithm.init(mainGraph);
            algorithm.compute();
        }

        if (config.folding.equals("high") || config.folding.equals("medium")) {
            launch.runWeightAlgorithm(mainGraph);
            long graphsMatchTime = System.currentTimeMillis();
            System.out.println("Starting parallel Matching");
            boolean isFolded = launch.foldParallel(mainGraph, config);
            System.out.println("Fold parallel time:" + (graphsMatchTime - startTime));
            List<Graph> subgraphs = mainGraph.getAttribute("subgraphs");

            //launch.createPrologue(mainGraph);
            if (isFolded) {
                Graph epilogue = launch.createEpilogue(mainGraph);
                //fsDOT.writeAll(epilogue, "./dotprod_N10_epilogue.dot");
            }


        }

        launch.arithmeticOptimizations(mainGraph);
        launch.updateVarLabels(mainGraph);
        launch.orderLevelGraph(mainGraph);

        launch.writeC(mainGraph, path.concat("\\"+config.outputFile + ".c"), config);

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