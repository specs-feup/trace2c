package pt.up.fe.specs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import pt.up.fe.specs.algorithms.*;
import pt.up.fe.specs.printers.CFunctionPrinter;
import pt.up.fe.specs.printers.CFileHeaderPrinter;
import pt.up.fe.specs.printers.CPrinter;
import pt.up.fe.specs.algorithms.AddStartAndEnd;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * pt.up.fe.specs.Launcher of application. Call the stages of the tool
 * 
 * @author Afonso
 *
 */
public class Launcher {

    public static void main(String[] args) throws IOException {
        System.out.println("Framework");

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
        if (configFilename.contains("/"))
            path = configFilename.substring(0, configFilename.lastIndexOf("/"));

        File configFile = new File(configFilename);
        if (!configFile.exists()) {
            System.out.println("File does no exist on path:" + configFile.getAbsolutePath() );
            return;
        }

        loadConfiguration(configFile);
        // Getting Graph`
        String graphPath = path + "/" + Config.getGraph();
        System.out.println("Graph path:" + graphPath);
        FileSource fs = FileSourceFactory
                .sourceFor(graphPath);

        fs.addSink(mainGraph);
        fs.readAll(graphPath);
        mainGraph.addAttribute("functionName", Config.getOutputFile());

        long initTime = System.currentTimeMillis();
        System.out.println("init time:" + (initTime - startTime));


        Queue<Algorithm> algorithmsQueue = new LinkedList<>();

        algorithmsQueue.add(new AddStartAndEnd());
        if (Config.isPruneLocalArrays()) {
            // should only be used if there are no arrays accesses that depend on input
            algorithmsQueue.add(new LocalVectorPruning());
        }
        algorithmsQueue.add(new SetVarsAttributes());
        algorithmsQueue.add(new InfoFromConfig());
        algorithmsQueue.add(new Pruning());
        algorithmsQueue.add(new Leveling());
        if (Config.isToParallelizeSums()) algorithmsQueue.add(new ParallelizeSums());


        if (Config.isToFold()) {
            algorithmsQueue.add(new WeightAlgorithm());
            algorithmsQueue.add(new AllSubgraphsAlgorithm());
            algorithmsQueue.add(new FoldParallelSubgraphs());
            //algorithmsQueue.add(new Snapshot());
            algorithmsQueue.add(new CreatePrologue());
            algorithmsQueue.add(new CreateEpilogue());
        }

        algorithmsQueue.add(new UpdateVarLabels());

        if (Config.isArithmetic()) algorithmsQueue.add(new ArithmeticOptimizations());

        algorithmsQueue.add(new UpdateLocalInfo());
        algorithmsQueue.add(new OrderLevelGraph());
        //algorithmsQueue.add(new Snapshot());

        while(!algorithmsQueue.isEmpty()) {
            Algorithm algorithm = algorithmsQueue.remove();
            algorithm.init(mainGraph);
            algorithm.compute();
        }

        writeC(mainGraph, path.concat("/"+Config.getOutputFile() + ".c"));

        long endTime = System.currentTimeMillis();
        System.out.println("End time:" + (endTime - startTime));
        //shapeGraph(mainGraph, false);
        //mainGraph.display();
    }

    /**
     * Call the algorithm to generate C output.
     *
     * @param graph
     *            Graph to print.
     * @param path
     *            path to place result
     * @throws IOException
     */
    private static void writeC(Graph graph, String path) {

        try {
            File file = new File(path);
            System.out.println("Path: " + path);
            BufferedWriter outBuffer = new BufferedWriter(new FileWriter(file));
            CFileHeaderPrinter cIncludesPrinter = new CFileHeaderPrinter(outBuffer);
            cIncludesPrinter.print();
            CPrinter printer = new CFunctionPrinter(outBuffer, graph);
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
     * Method that read the configuration JSON file
     *
     * @param file
     *            configuration Gson file
     *
     * @throws FileNotFoundException
     */
    private static void loadConfiguration(File file) throws FileNotFoundException {
        Reader reader = new FileReader(file);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT);
        Gson gson = gsonBuilder.create();
        gson.fromJson(reader, Config.StaticConfig.class);
    }


    /**
     * Inserts shape attributes in nodes based on node types.
     * 
     * @param g
     *            graph to shape
     * @param remove
     *            boolean used to indicate whether the Start and End nodes should be removed for the print out
     */
    private static void shapeGraph(Graph g, boolean remove) {
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
            switch (att1) {
                case "nop":
                    n.addAttribute("shape", "house");
                    break;
                case "var":
                    n.addAttribute("shape", "box");
                    break;
                case "hyper":
                    n.addAttribute("shape", "hexagon");
                    break;
            }

        }
    }

}