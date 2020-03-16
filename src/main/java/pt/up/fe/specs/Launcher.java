package pt.up.fe.specs;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.stream.file.FileSinkDOT;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import pt.up.fe.specs.graphoptimizations.HierarchyHandling;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * pt.up.fe.specs.Launcher of application. Call the stages of the tool
 * 
 * @author Afonso
 *
 */
public class Launcher {

    private static int maxlevel;
    private static List<List<Node>> levelgraph = new ArrayList<>();
    // static pt.up.fe.specs.CInfo info;

    public static void main(String args[]) throws IOException {
        System.out.println("Framework");
        // variables used for optimizations before configuration file
        int loadstore = 1;
        boolean pruned = true;
        boolean pipe = true;
        boolean simple = false;
        boolean parallel = true;
        boolean arithmetic = false;
        boolean access = false;
        boolean full_part = false;

        // Clock timer
        long startTime = System.currentTimeMillis();
        // Creating Graph
        Graph g = new DefaultGraph("g");
        // Getting config file
        CInfo info = null;
        if(args.length < 1) {
            throw new Error("Indicate path to config file");
        }
        String configFilename = args[0];
        String path = "";
        if (configFilename.contains("\\"))
            path = configFilename.substring(0, configFilename.lastIndexOf("\\"));
        File configFile = new File(configFilename);
        if (!configFile.exists()) {
            System.out.println("File does no exist");
            return;
        }
        // Creating main part of tool and reading config file
        LaunchAlgorithm launch = new LaunchAlgorithm();
        Config config = launch.getConfiguration(configFile);
        loadstore = config.loadstores;
        arithmetic = config.arithmetic;
        access = config.acc;
        // Getting Graph
        FileSource fs = FileSourceFactory
                .sourceFor(path + "\\" + config.graph);
        ;
        // "..\\work\\filter_subband\\fsubba_graph2.dot"
        // "..\\work\\Autocorr\\graph_autcor.dot"
        // "..\\work\\dotprod\\dotprod_graph.dot"
        // "..\\work\\UTDSP_test_suite\\graphs\\fir_graph.dot"
        // "..\\work\\UTDSP_test_suite\\graphs\\fir_big_graph.dot"
        // "..\\work\\UTDSP_test_suite\\graphs\\latnrm_graph.dot"
        // "..\\work\\fir_basic\\fir_basic.dot"
        // "..\\work\\Sobbel\\edge_detect.dot"
        // "..\\work\\SVM\\svm_simp.dot"
        fs.addSink(g);

        fs.readAll(path + "\\" + config.graph);

        g = launch.Initializations(g);
        System.out.println("Init done");

        info = launch.InfoInit(g, config);
        System.out.println("Info done");
        System.out.println("Node count " + g.getNodeCount() + " Edge count " + g.getEdgeCount());

        g = launch.Prune(g);
        System.out.println("Prune done");
        System.out.println("Node count " + g.getNodeCount() + " Edge count " + g.getEdgeCount());
        info.print();
        long initTime = System.currentTimeMillis();
        System.out.println("init time:" + (initTime - startTime));

        if (config.folding.equals("high") || config.folding.equals("medium")) {
            g = launch.separateGraph(g, info, "s");
            long sepTime = System.currentTimeMillis();
            System.out.println("Separate time:" + (sepTime - startTime));
            g = launch.separeteFoldExt(g, true);
            System.out.println("separate done");

            long matchTime = System.currentTimeMillis();
            System.out.println("Match time:" + (matchTime - startTime));

            g = launch.parallelAccessOptimization(g, access, arithmetic);

            HierarchyHandling hierarchy = new HierarchyHandling();
            g = hierarchy.trimHierarchy(g);
            if (config.folding.equals("high"))
                g = launch.pipeline(g, "s", true);

            launch.setFoldingByLoadStore(loadstore, null, g, true);

            g = launch.unfoldAllandArithmeticOPt(g, 0, true, true, config);
        }
        info = launch.InfoUpdate(g, info);
        g = launch.Leveling(g);
        maxlevel = launch.getMaxLevel();
        levelgraph = launch.getLevelgraph();
        if (config.full_part == true) {
            // info = launch.full_part(g, info);
            info = launch.full_part_total(g, info);
        }
        launch.writeC(g, info, "..\\work\\CodeFromGraph", "result",
                loadstore);

        long endTime = System.currentTimeMillis();
        System.out.println("End time:" + (endTime - startTime));
        g.display();
    }

    /**
     * Prints out dot description of a graph. If stay true only highest level printed. If false all lower level
     * subgraphs also printed.
     * 
     * @param g
     *            Graph to print out
     * @param name
     *            name of file for the dot description
     * @param stay
     *            boolean, if true lower levels are not printed out
     * @param l
     *            integer value used to separate the description of each level
     * @throws IOException
     */
    public static void write_out_graphdot(Graph g, String name, boolean stay, int l) throws IOException {
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