package pt.up.fe.specs.graphoptimizations;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.VarLoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Algorithm that updates the variable information stored in edges for the local variables.
 *
 * @author CPU TOSH
 */
public class EdgeInfoUpdateFlat implements Algorithm {

    private Graph graph;
    private CInfo info;
    private int wireNumber;

    /**
     * Constructor for first call.
     *
     * @param info information file to update.
     */
    public EdgeInfoUpdateFlat(CInfo info) {
        // TODO Auto-generated constructor stub
        this.info = info;
        this.info.clearLocalInfo();
        this.info.clearVariablesCounter();
        wireNumber = 0;

    }

    /**
     * Constructor for calls inside the algorithm.
     *
     * @param info        information file to update.
     * @param wireNumber highest used wire number.
     */
    public EdgeInfoUpdateFlat(CInfo info, int wireNumber) {
        // TODO Auto-generated constructor stub
        this.info = info;

        this.wireNumber = wireNumber;
    }

    @Override
    /**
     * Implements main body of graph.
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        this.graph = graph;

    }

    @Override
    /**
     * Implements the main body of the algorithm. All edges are accessed and new information is stored. 
     * A wire number is also given to each local variable to ensure all variables are uniquely named.
     */
    public void compute() {
        // TODO Auto-generated method stub�
        boolean isArray;
        boolean isCont = false;
        boolean isToWrite = false;
        String tempName;
        String newLabel;
        List<VarLoc> localInfo = info.getLocalInfo();
        HashMap<String, Integer> variablesCounter = info.getVariablesCounter();

        for (Node n : graph) {
            List<Node> search = new ArrayList<>();
            search.add(n);

            for (Edge in : n.getEachEnteringEdge()) {
                if (!in.hasAttribute("skip_write_info") && in.hasAttribute("att1")
                        && in.getNode0().getAttribute("att1").toString().equals("op")) {
                    if (in.getAttribute("att1").equals("var") && !in.getNode0().getEnteringEdge(0).getNode0()
                            .getAttribute("att1").toString().equals("nop") &&
                            !in.getNode0().getEnteringEdge(0).getNode0()
                                    .getAttribute("att1").toString().equals("buff")
                            && in.getNode1().getAttribute("att1").equals("op")) {
                        newLabel = in.getAttribute("label").toString();

                        if (!newLabel.contains("[") && !newLabel.contains("_i") && !newLabel.contains("*")) {
                            in.addAttribute("write", true);
                            isToWrite = true;
                        }
                    }
                }

                if (!isToWrite && in.hasAttribute("write")) {
                    in.removeAttribute("write");
                }
                if (isToWrite) {
                    isToWrite = false;
                }

                for (Node nn : search) {
                    if (nn.getOutDegree() > 0 && nn.getInDegree() > 0) {

                        // This is hotfix for labeling in parallel
                        for (Edge inn : nn.getEachEnteringEdge()) {
                            if (inn.hasAttribute("att1")) {
                                if (inn.getAttribute("att1").equals("var") && inn.hasAttribute("write")
                                        && !inn.hasAttribute("set")) {
                                    newLabel = inn.getAttribute("label").toString();
                                    newLabel = newLabel.concat("_w" + wireNumber);
                                    inn.setAttribute("label", newLabel);
                                    inn.addAttribute("set", true);
                                    wireNumber++;
                                }
                            }

                        }

                        // Edge e = nn.getLeavingEdge(0);
                        for (Edge e : nn.getEachEnteringEdge()) {
                            if (e.hasAttribute("att1")
                                    && !nn.getEnteringEdge(0).getNode0().getAttribute("att1").equals("nop")) {
                                if (e.getAttribute("att1").equals("var")) {

                                    int dim = 0;
                                    List<Integer> indexes = new ArrayList<>();
                                    if (e.getAttribute("att2").equals("loc")) {
                                        isArray = e.getAttribute("label").toString().contains("[");

                                        if (isArray) {
                                            tempName = e.getAttribute("name");
                                            indexes = e.getAttribute("Index");
                                            dim = e.getAttribute("dim");
                                        } else {
                                            tempName = (e.getAttribute("label").toString());
                                            indexes = null;
                                        }

                                        for (VarLoc var : localInfo) {
                                            if (var.getName().equals(tempName)) {

                                                if (isArray)
                                                    var.updateSize(indexes);
                                                isCont = true;
                                                break;
                                            }
                                        }
                                        String key = e.getAttribute("label").toString();
                                        if (variablesCounter.containsKey(key)) {
                                            Integer counter = variablesCounter.get(key);
                                            variablesCounter.put(key, counter + 1);
                                        } else {
                                            variablesCounter.put(key, 1);
                                        }


                                        if (!isCont) {
                                            localInfo.add(new VarLoc(e.getAttribute("att3"), tempName, isArray, dim,
                                                            indexes));

                                        }
                                    }

                                    if (e.getAttribute("att2").equals("inte")) {
                                        String key = e.getAttribute("label").toString();
                                        if (variablesCounter.containsKey(key)) {
                                            int counter = variablesCounter.get(key);
                                            variablesCounter.put(key, counter + 1);
                                        } else {
                                            variablesCounter.put(key, 1);
                                        }
                                    }

                                }
                                isCont = false;
                            }
                        }

                    }
                }

                if (n.hasAttribute("att1")) {
                    if (n.getAttribute("att1").toString().equals("hyper")) {

                        if (!n.hasAttribute("update")) {
                            List<Graph> graphlist = graph.getAttribute(n.getId());
                            n.addAttribute("update", true);
                            Graph sub = graphlist.get(graphlist.size() - 1);
                            Algorithm infoalg = new EdgeInfoUpdateFlat(info, wireNumber);
                            infoalg.init(sub);
                            infoalg.compute();
                            wireNumber = ((EdgeInfoUpdateFlat) infoalg).getWireNumber();

                        }
                    }
                }
            }
        }

    }


    public int getWireNumber() {
        // TODO Auto-generated method stub
        return wireNumber;
    }

}
