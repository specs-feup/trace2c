package pt.up.fe.specs.printers;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.Config;
import pt.up.fe.specs.LoopNameAndIterator;
import pt.up.fe.specs.graphoptimizations.LoopInfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Algorithm that generates the C output of a subgraph in the context of a loop.
 *
 * @author Afonso
 */
public class CLoopPrinter extends CPrinter {
    Graph subgraph;
    List<List<Node>> levelGraph = new ArrayList<>();
    int plus_fold;
    int mult_fold;
    int total_its;
    int maxLevel;
    int initv;
    int incr;
    int loopLevel; // loop level in the nested loop hierarchy (starts in 0)
    ArrayList<LoopNameAndIterator> loopVariables = new ArrayList<LoopNameAndIterator>();
    boolean isToFold = true;


    public CLoopPrinter(BufferedWriter outBuffer, Graph graph, Config config, int loopLevel) {
        super(outBuffer, graph, config);
        this.loopLevel = loopLevel;
        init();
    }


    /**
     * Initialize the algorithm.
     *
     */
    private void init() {
        Node hyperNode = graph.getNode("HyperNode");
        loopVariables.add(new LoopNameAndIterator(hyperNode.getAttribute("loopname"), Character.toString((char)(105 + loopLevel))));
        plus_fold = hyperNode.getAttribute("plus_fold");
        mult_fold = hyperNode.getAttribute("mult_fold");
        initv = hyperNode.getAttribute("initv");
        incr = hyperNode.getAttribute("+incr");
        total_its = hyperNode.getAttribute("size");
        this.subgraph = ((ArrayList<Graph>) graph.getAttribute("HyperNode")).get(0);
        maxLevel = subgraph.getAttribute("maxlevel");
        this.levelGraph = subgraph.getAttribute("levelgraph");
    }

    /**
     * Main method implementing the algorithm. First initializes the loop, The writes the operation.
     * If inner loop node  detected call algorithm to print out the inner loop. Requires leveld graph.
     *
     */
    public void print() {

        try {

            outBuffer.append("// starting Loop\n");
            initLoop();

            writeCodeByLevel(levelGraph, 0);
            outBuffer.append("}\n");


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected String getLabel(Edge edge) throws IOException {
        String name;
        if (edge.getAttribute("att1").equals("call")) {
            name = getCallStatement(edge.getSourceNode());
        } else {
            name = indexArrayOnLoop(edge);
        }

        if (edge.hasAttribute("cast")) {
            name = edge.getAttribute("cast") + name;
        }
        if (edge.hasAttribute("mod")) {
            name = edge.getAttribute("mod") + name + ")";
        }
        return name;
    }

    @Override
    protected String getAssignmentLabel(Edge edge) throws IOException {
        return indexArrayOnLoop(edge);
    }

    /**
     * Indexes an array access based on the loop.
     *
     * @param e    Edge containing the variable.
     * @return label of the access with updated indexes.
     */
    private String indexArrayOnLoop(Edge e) {
        String name = e.getAttribute("label").toString();
        String temp;
        if (name.contains("[")) {
            temp = e.getAttribute("label").toString();
            List<Integer> indexes = utils.getIndexes(temp);
            String offset;
            offset = e.getAttribute("label").toString().substring(name.indexOf("["));
            String newIndex = new String();
            int novar = 0;

                LoopInfo loopInfo = e.getAttribute("loopinfo");

                for (int i = 0; i < indexes.size(); i++) {

                    newIndex = newIndex.concat("[");

                    for (LoopNameAndIterator entry : loopVariables) {
                        String loopName = entry.getLoopName();
                        String loopIterator = entry.getIterator();
                        if (loopInfo.name.equals(loopName)) {
                            if (loopInfo.ratios.get(i) == 0) {
                                novar++;
                                break;
                            }
                            if (loopInfo.ratios.get(i) == 1) {
                                newIndex = newIndex.concat(loopIterator + "+");
                            } else {
                                newIndex = newIndex.concat("(" + loopInfo.ratios.get(i) + ")" + "*" + loopIterator + "+");
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

        return name;
    }

    /**
     * Prints out the lines of code to initialize the identified loop.
     *
     * @throws IOException
     */
    private void initLoop() throws IOException {
        // TODO Auto-generated method stub
        int incr = this.incr;
        int plus_fold;
        int mult_fold;
        if (isToFold) {
            plus_fold = this.plus_fold;
            mult_fold = this.mult_fold;
        } else {
            plus_fold = 0;
            mult_fold = 1;
        }
        String loopIterator = loopVariables.get(loopLevel).getIterator();
        incr += plus_fold;
        outBuffer.append(
                "for( int " + loopIterator + " =" + initv + "; "
                        + loopIterator + " < "
                        + total_its / (mult_fold) + "; "
                        + loopIterator + "=" + loopIterator + "+"
                        + incr + "){\n");

        System.out.println("graph attributes -------");
        for (String s : subgraph.getEachAttributeKey())
            System.out.println("key " + s + " value : " + subgraph.getAttribute(s).toString());
        if (subgraph.hasAttribute("Pipeline"))
            outBuffer.append("#pragma HLS pipeline");
        outBuffer.append("\n");
        outBuffer.append("\n\n");

    }



}
