package pt.up.fe.specs;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class GraphsWrapper {
    private String mainGraphId;
    HashMap<String, Graph> graphs;

    public GraphsWrapper(Graph mainGraph) {
        graphs = new HashMap<>();
        mainGraphId = mainGraph.getId();
        graphs.put(mainGraphId, mainGraph);
    }

    public void addGraph(Graph graph) {
        graphs.put(graph.getId(), graph);
    }

    public Graph getGraph(String id) {
        return graphs.get(id);
    }

    public Graph getMainGraph() {
        return graphs.get(mainGraphId);
    }

    public Collection<Graph> getAllGraphs() {
        return graphs.values();
    }

    public void setGraph(String id, Graph g) {
        graphs.put(id, g);
    }
}
