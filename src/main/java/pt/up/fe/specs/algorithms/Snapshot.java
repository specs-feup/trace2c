package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;
import org.graphstream.stream.file.FileSinkDOT;

import java.io.IOException;

public class Snapshot implements Algorithm {
    private Graph graph;
    private static int counter = 0;
    @Override
    public void init(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void compute() {
        FileSinkDOT fsDot = new FileSinkDOT();
        try {
            graph.write(fsDot, "snaphshot" + counter++ + ".dot");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
