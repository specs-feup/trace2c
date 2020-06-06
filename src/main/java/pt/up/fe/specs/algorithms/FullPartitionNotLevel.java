package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.VarIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Algorithm that identifies the maximum necessary partitioning factor for every input and output array 
 * based on the number of separate array accesses in a every loop.
 * @author CPU TOSH
 *
 */
public class FullPartitionNotLevel implements Algorithm {

    private Graph graph;
    private List<List<Node>> levelGraph;
    private CInfo info;
    private HashMap<String, List<Edge>> ioList = new HashMap<>();
    private boolean isToUnroll;
    private ArrayList<VarIO> inputsAndOutputs = new ArrayList<>();

    
    /**
     * 
     * @param levelGraph
     * @param info        information file to update.
     * @param isToUnroll
     */
    public FullPartitionNotLevel(List<List<Node>> levelGraph, CInfo info, boolean isToUnroll) {
        // TODO Auto-generated constructor stub
        this.isToUnroll = isToUnroll;
        this.levelGraph = levelGraph;
        this.info = info;
        inputsAndOutputs.addAll(info.getInputs());
        inputsAndOutputs.addAll(info.getOutputs());
        for (VarIO io : inputsAndOutputs) {
            ioList.put(io.getName(), new ArrayList<>());
        }
    }

    @Override
    /**
     * Initializes the Algorithm. Applies the same algorithm to lower levels. 
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub

        this.graph = graph;
        if (graph.hasAttribute("hierarchy")) {
            List<String> hire = graph.getAttribute("hierarchy");
            for (String h : hire) {
                List<Graph> graphlist = graph.getAttribute(h);
                Graph struct = graphlist.get(graphlist.size() - 1);
                boolean nxt_unroll = false;
                if (graph.hasAttribute("Pipeline")) {
                    nxt_unroll = true;
                    System.out.println("multiply");
                }
                Algorithm part = new FullPartitionNotLevel(struct.getAttribute("levelgraph"), info, nxt_unroll);
                part.init(struct);
                part.compute();
                info = ((FullPartitionNotLevel) part).getInfo();
            }

        }

    }

    @Override
    /**
     * Main method that implements the algorithm. The method identifies all accesses to arrays, and then counts the
     * number of unique accesses separately for each dimension. 
     */
    public void compute() {
        // TODO Auto-generated method stub

        for (List<Node> level : levelGraph) {
            for (Node n : level) {
                if (n.getAttribute("att1").toString().equals("op")) {
                    for (Edge e : n.getEachEnteringEdge()) {
                        if (e.hasAttribute("att2")) {
                            if (e.getAttribute("att2").equals("inte") && e.hasAttribute("array")) {
                                // WARNING, FOR LOOP HERE BEFORE, PLEASE DEBUG
                                String key = e.getAttribute("name");
                                if (ioList.containsKey(key)) {
                                    ioList.get(key).add(e);
                                }
                            }
                        }
                    }
                    Edge out = n.getLeavingEdge(0);
                    if (out.hasAttribute("att2")) {
                        if (out.getAttribute("att2").equals("inte") && out.hasAttribute("array")) {
                            // WARNING, FOR LOOP HERE BEFORE, PLEASE DEBUG
                            String key = out.getAttribute("name");
                            if (ioList.containsKey(key)) {
                                ioList.get(key).add(out);
                            }
                        }
                    }

                }

            }

        }

        for (Map.Entry<String, List<Edge>> pair : ioList.entrySet()) {
            List<Edge> edgeList = pair.getValue();
            List<List<Integer>> allIndexes = new ArrayList<>();
            List<Integer> accesses = new ArrayList<>();
            VarIO ioComp = null;
            for (VarIO io : inputsAndOutputs) {
                if (io.getName().equals(pair.getKey()))
                    ioComp = io;
            }
            if (!edgeList.isEmpty()) {
                System.out.println(ioComp.getName() + " size of list " + edgeList.size());
            }


            for (Edge e : edgeList) {
                List<Integer> indexes = getIndexes(e.getAttribute("label"));

                for (int j = 0; j < indexes.size(); j++) {
                    if (allIndexes.size() < ioComp.getDim()) {
                        allIndexes.add(new ArrayList<>());
                        allIndexes.get(j).add(indexes.get(j));
                        accesses.add(1);
                    } else if (!allIndexes.get(j).contains(indexes.get(j))) {
                        allIndexes.get(j).add(indexes.get(j));
                        accesses.set(j, accesses.get(j) + 1);

                    }
                }
            }
            if (isToUnroll && graph.hasAttribute("size") && !accesses.isEmpty()) {
                for (int k = 0; k < accesses.size(); k++) {
                    if (accesses.get(k) != null)
                        accesses.set(k, accesses.get(k) * Integer.parseInt(graph.getAttribute("size").toString()));
                }
            }
            if (!accesses.isEmpty())
                ioComp.updatePartitionFactor(accesses);
            accesses.clear();
            allIndexes.clear();
            pair.getValue().clear();
        }

    }

    /**
     * Returns the updated variable information.
     * @return
     */
    public CInfo getInfo() {
        return info;
    }

    public List<Integer> getIndexes(String name) {
        List<Integer> indexes = new ArrayList<>();
        String temp = name;
        while (temp.lastIndexOf("[") != -1) {

            temp = temp.substring(temp.indexOf("["));
            String temp2 = temp.substring(1, temp.indexOf("]"));

            indexes.add(Integer.parseInt(temp2));
            temp = temp.substring(temp.indexOf("]"));
        }
        return indexes;
    }

}
