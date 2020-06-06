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

public class FullPartition implements Algorithm {
    private List<List<Node>> levelGraph;
    private CInfo info;
    private HashMap<String, List<Edge>> ioMap = new HashMap<>();
    private ArrayList<VarIO> inputsAndOutputs = new ArrayList<>();

    public FullPartition(List<List<Node>> levelgraph, CInfo info) {
        // TODO Auto-generated constructor stub
        this.levelGraph = levelgraph;
        this.info = info;
        inputsAndOutputs.addAll(info.getInputs());
        inputsAndOutputs.addAll(info.getOutputs());
        for (VarIO io : inputsAndOutputs) {
            ioMap.put(io.getName(), new ArrayList<>());
        }
    }

    @Override
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        System.out.println("Start full part");

        if (graph.hasAttribute("hierarchy")) {
            List<String> hire = graph.getAttribute("hierarchy");
            for (String h : hire) {
                List<Graph> graphlist = graph.getAttribute(h);
                Graph struct = graphlist.get(graphlist.size() - 1);
                Algorithm part = new FullPartition(struct.getAttribute("levelgraph"), info);
                part.init(struct);
                part.compute();
                info = ((FullPartition) part).getInfo();
            }

        }

    }

    @Override
    public void compute() {
        // TODO Auto-generated method stub

        for (List<Node> level : levelGraph) {
            for (Node n : level) {
                if (n.getAttribute("att1").toString().equals("op")) {
                    for (Edge e : n.getEachEnteringEdge()) {
                        if (e.hasAttribute("att2")) {
                            if (e.getAttribute("att2").equals("inte") && e.hasAttribute("array")) {
                                // DEBUG THIS TO BE SURE THAT THE MAP IS SUFFICIENT.
                                // THERE WAS A FOR LOOP HERE BEFORE WITHOUT A BREAK
                                String key = e.getAttribute("name").toString();
                                if (ioMap.containsKey(key)) {
                                    ioMap.get(key).add(e);
                                }
                            }
                        }
                    }
                }

            }

            for (Map.Entry<String, List<Edge>> pair : ioMap.entrySet()) {
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
                if (!accesses.isEmpty())
                    ioComp.updatePartitionFactor(accesses);
                accesses.clear();
                allIndexes.clear();

                pair.getValue().clear();
            }

        }

    }

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
