package pt.up.fe.specs.printers;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import pt.up.fe.specs.Config;
import pt.up.fe.specs.LoopNameAndIterator;
import pt.up.fe.specs.algorithms.LoopInfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class CParallelLoopPrinter extends CLoopPrinter {

    public CParallelLoopPrinter(BufferedWriter outBuffer, Graph graph, Config config, int loopLevel) {
        super(outBuffer, graph, config, loopLevel);


    }

    @Override
    protected void init() {
        String loopName = graph.getAttribute("functionName");
        graph.setAttribute("loopName", loopName);
        loopVariables.add(new LoopNameAndIterator(loopName, Character.toString((char)(105 + loopLevel))));
        this.maxLevel = graph.getAttribute("maxlevel");
        this.levelGraph = graph.getAttribute("levelgraph");
        this.plusFold = graph.getAttribute("plus_fold");
        this.multFold = graph.getAttribute("mult_fold");
        this.totalIterations = graph.getAttribute("size");
        this.loopVarInitialValue = graph.getAttribute("initv");
        this.loopVarIncrement = graph.getAttribute("+incr");
    }


    /**
     * Indexes an array access based on the loop.
     *
     * @param e    Edge containing the variable.
     * @return label of the access with updated indexes.
     */
    @Override
    protected String indexArrayOnLoop(Edge e) {
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
    @Override
    protected void initLoop() throws IOException {
        // TODO Auto-generated method stub
        int incr = this.loopVarIncrement;
        String loopIterator = loopVariables.get(loopLevel).getIterator();
        incr += plusFold;
        outBuffer.append(
                "for( int " + loopIterator + " = offset; "
                        + loopIterator + " < offset + size;"
                        + loopIterator + "=" + loopIterator + "+"
                        + incr + "){\n");

        if (graph.hasAttribute("Pipeline"))
            outBuffer.append("#pragma HLS pipeline");
        outBuffer.append("\n");
        outBuffer.append("\n\n");

    }
}
