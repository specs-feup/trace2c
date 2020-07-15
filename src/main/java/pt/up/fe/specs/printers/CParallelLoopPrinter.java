package pt.up.fe.specs.printers;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import pt.up.fe.specs.Config;
import pt.up.fe.specs.LoopNameAndIterator;
import pt.up.fe.specs.utils.FoldInfo;
import pt.up.fe.specs.utils.LoopInfo;
import pt.up.fe.specs.utils.SpecificLoopInfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CParallelLoopPrinter extends CLoopPrinter {

    public CParallelLoopPrinter(BufferedWriter outBuffer, Graph graph, Config config, int loopLevel) {
        super(outBuffer, graph, config, loopLevel);


    }

    @Override
    protected void init() {
        ArrayList<String> loopNames = graph.getAttribute("loopNames");
        for (String loopName: loopNames) {
            loopVariables.add(new LoopNameAndIterator(loopName, Character.toString((char)(105 + loopLevel))));
        }
        this.maxLevel = graph.getAttribute("maxlevel");
        this.levelGraph = graph.getAttribute("levelgraph");
        this.foldInfo = graph.getAttribute("foldInfo");
    }


    /**
     * Indexes an array access based on the loop.
     *
     * @param e    Edge containing the variable.
     * @return label of the access with updated indexes.
     */
    @Override
    protected String indexArrayOnLoop(Edge e) {
        String label = e.getAttribute("label").toString();
        String temp;
        if (e.hasAttribute("loopinfo")) {
            if (label.contains("[")) {
                temp = e.getAttribute("label").toString();
                List<Integer> indexes = utils.getIndexes(temp);
                String offset;
                offset = e.getAttribute("label").toString().substring(label.indexOf("["));
                String newIndex = new String();
                int novar = 0;


                LoopInfo loopInfo = e.getAttribute("loopinfo");




                for (int i = 0; i < indexes.size(); i++) {

                    newIndex = newIndex.concat("[");

                    for (LoopNameAndIterator entry : loopVariables) {
                        String loopName = entry.getLoopName();
                        String loopIterator = entry.getIterator();
                        SpecificLoopInfo specificLoopInfo = loopInfo.getLoopWithName(loopName);
                        if (specificLoopInfo != null) {
                            if (specificLoopInfo.ratios.get(i) == 0) {
                                novar++;
                                break;
                            }
                            if (specificLoopInfo.ratios.get(i) == 1) {
                                newIndex = newIndex.concat(loopIterator + "+");
                            } else {
                                newIndex = newIndex.concat("(" + specificLoopInfo.ratios.get(i) + ")" + "*" + loopIterator + "+");
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

                label = label.replace(offset, newIndex);
            }
        }

        return label;
    }

    /**
     * Prints out the lines of code to initialize the identified loop.
     *
     * @throws IOException
     */
    @Override
    protected void initLoop() throws IOException {
        // TODO Auto-generated method stub
        FoldInfo foldInfo = graph.getAttribute("foldInfo");
        String loopIterator = loopVariables.get(loopLevel).getIterator();
        outBuffer.append(
                "for( int " + loopIterator + " = " + foldInfo.getInitialValue() +"; "
                        + loopIterator + " < " + foldInfo.getWidth() + ";"
                        + loopIterator + "=" + loopIterator + "+"
                        + foldInfo.getIncrement() + "){\n");

        outBuffer.append("#pragma HLS pipeline");
        outBuffer.append("\n");
        outBuffer.append("\n\n");

    }
}
