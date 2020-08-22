package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import pt.up.fe.specs.CInfo;
import pt.up.fe.specs.customNodes.VarElement;
import pt.up.fe.specs.utils.Utils;
import pt.up.fe.specs.utils.Var;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateLocalInfo implements Algorithm {

    Graph graph;
    CInfo info;
    HashMap<String, ArrayList<Integer>> arraysToMaxIndexes = new HashMap<>();
    HashMap<String, String> arrayNamesToTypes = new HashMap<>();

    @Override
    public void init(Graph graph) {
        this.graph = graph;
        info = graph.getAttribute("info");
    }

    @Override
    public void compute() {
        System.out.println("Starting to update local info on graph: " + graph.getId());
        System.out.println("Need to process: " + graph.getEdgeCount() + " edges");
        for (Edge edge : graph.getEachEdge()) {

            if (Utils.isLocalVar(edge)) {
                VarElement varElement = new VarElement(edge);
                String varName = varElement.getName();
                if (!info.hasInput(varName) && !info.hasOutput(varName)) {
                    if (!Utils.isArray(edge)) {
                        Var newLocalVar = new Var(varElement);
                        if (!info.hasLocalVar(newLocalVar)) {
                            info.addLocal(newLocalVar);
                        }
                    } else {
                        // compute arrays max indexes
                        ArrayList<Integer> currentVarIndexes = varElement.getIndexes();
                        if (!arraysToMaxIndexes.containsKey(varElement.getName())) {
                            arraysToMaxIndexes.putIfAbsent(varElement.getName(), createZerosArrayList(varElement.getDim()));
                        }

                        arrayNamesToTypes.putIfAbsent(varElement.getName(), varElement.getType());
                        ArrayList<Integer> currentMaxIndexes = arraysToMaxIndexes.get(varElement.getName());
                        for (int i = 0; i < varElement.getDim(); i++) {
                            if (currentVarIndexes.get(i) != null) { // used when index is a variable
                                if (currentVarIndexes.get(i) > currentMaxIndexes.get(i)) {
                                    currentMaxIndexes.set(i, currentVarIndexes.get(i));
                                }
                            }

                        }
                    }
                }

            }
        }

        // Add arrays as local vars
        for (Map.Entry<String, ArrayList<Integer>> mapEntry : arraysToMaxIndexes.entrySet()) {
            String name = mapEntry.getKey();
            ArrayList<Integer> maxIndexes = mapEntry.getValue();
            ArrayList<Integer> sizes = new ArrayList<>();
            for (Integer index : maxIndexes) {
                sizes.add(index + 1);
            }
            String type = arrayNamesToTypes.get(name);
            Var newLocalArray = new Var(type, name, true, sizes);
            if (!info.hasLocalVar(newLocalArray)) {
                info.addLocal(newLocalArray);
            }
        }
        if (graph.hasAttribute("subgraphs")) {
            for (Graph subgraph : (ArrayList<Graph>) graph.getAttribute("subgraphs")) {
                UpdateLocalInfo updateLocalInfo = new UpdateLocalInfo();
                updateLocalInfo.init(subgraph);
                updateLocalInfo.compute();
            }
        }
        System.out.println("Finished update local info on graph: " + graph.getId());
    }

    private ArrayList<Integer> createZerosArrayList(int size) {
        ArrayList<Integer> newArray = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            newArray.add(0);
        }
        return newArray;
    }
}
