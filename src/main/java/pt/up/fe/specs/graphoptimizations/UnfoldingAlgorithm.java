package pt.up.fe.specs.graphoptimizations;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Algorithm that unfolds a loop.
 * 
 * TODO: merge parallel_compute and parallel_seq_compute
 * 
 * @author CPU TOSH
 *
 */
public class UnfoldingAlgorithm implements Algorithm {

    Graph graph;
    String loopname;
    String looptype;
    List<Node> check = new ArrayList<>();
    int fold;
    boolean inner;
    String id;
    String connect_v;
    boolean full_connect;

    /**
     * 
     * @param loopname
     *            name of the loop to unfold along
     * @param fold
     *            unfolding factor
     * @param looptype
     *            type of loop
     * @param inner
     *            boolean indicating if the unfolded loop is an inner loop from
     * @param id
     *            Integer used to append to new variables to ensure uniqueness in names.
     * @param connect_v
     *            If loop iterations are sequential has name of variable that is not independent between iterations.
     * @param full_connect
     *            Boolean that determines if separate connection of memory optimized loops should be fully connected or
     *            if simply the iterations should appear one after another, connected by and intermediate node.
     */
    public UnfoldingAlgorithm(String loopname, int fold, String looptype, boolean inner, String id, String connect_v,
            boolean full_connect) {
        // TODO Auto-generated constructor stub

        this.loopname = loopname;
        this.fold = fold;
        this.looptype = looptype;
        this.inner = inner;
        this.id = id;
        this.full_connect = full_connect;
        if (looptype.equals("pipeline")) {
            this.connect_v = connect_v;
            System.out.println("Connecting variable " + connect_v);
        } else
            this.connect_v = "nothing";
    }

    @Override
    /**
     * Initializes the algorithm.
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        // graph.addAttribute("flat", true);
        this.graph = Graphs.clone(graph);
        for (Edge e : this.graph.getEachEdge()) {
            if (e.hasAttribute("reproducable"))
                e.setAttribute("reproducable", e.getId());
            else
                e.addAttribute("reproducable", e.getId());

        }

    }

    @Override
    /**
     * Main method implementing the algorithm. The type of unfolding depends on the given type of loop.
     */
    public void compute() {
        System.out.println("looptype : " + looptype);
        if (looptype.equals("parallel") || looptype.equals("pipeline"))
            parallelcompute();
        else if (looptype.equals("parallel_sequential") || looptype.equals("pipeline_sequential"))
            parallel_seq_compute();
    }

    /**
     * Unfolds loops that have not been modified by memory optimizations. The initial dataflow is replicated by the
     * indicated unrolling factor. After replicating nodes the graph is structured to maintain dependencies between
     * iterations.
     * 
     */
    public void parallelcompute() {
        if (fold == 0) {
            return;
        }
        List<Node> addNode = new ArrayList();
        for (Edge e : graph.getNode("Start").getEachLeavingEdge()) {
            addNode.add(e.getNode1());
        }
        List<Node> templist = new ArrayList();
        List<Node> connect = new ArrayList<>();
        int t = 0;

        while (!addNode.isEmpty()) {
            for (Node n : addNode) {

                if (n.hasAttribute("connect") && (looptype.equals("pipeline"))) {
                    if (n.getAttribute("connect").equals(connect_v)) {
                        // System.out.println("connect found ");
                        connect.add(n);
                    }

                }

                for (int i = 0; i < fold; i++) {
                    if (graph.getNode(n.getId() + "_" + id + "" + i) == null
                            && !n.getAttribute("att1").equals("hyper"))
                        graph.addNode(n.getId() + "_" + id + "" + i);

                    if (n.getAttribute("att1").equals("hyper") && i == 0) {

                        List<Graph> graphlist = new ArrayList<>();
                        graphlist = graph.getAttribute(n.getId());
                        Graph temg = graphlist.get(graphlist.size() - 1);
                        Algorithm unfold = new UnfoldingAlgorithm(loopname, fold, looptype, true, id, connect_v,
                                full_connect);
                        unfold.init(temg);
                        unfold.compute();
                        temg = ((UnfoldingAlgorithm) unfold).getGraph();
                        graphlist.set(graphlist.size() - 1, temg);
                        graph.setAttribute(n.getId(), graphlist);

                    }

                    for (Edge e : n.getEachLeavingEdge()) {
                        if (graph.getNode(e.getNode1().getId() + "_" + id + "" + i) == null) {
                            if (!e.getNode1().getAttribute("att1").equals("nop")
                                    && !e.getNode1().getAttribute("att1").equals("hyper")) {
                                graph.addNode(e.getNode1().getId() + "_" + id + "" + i);
                            }
                            if (e.getNode1().getAttribute("att1").equals("nop")
                                    && !n.getAttribute("att1").equals("hyper")) {
                                if (graph.getEdge(e.getId() + "_" + id + "" + i) == null) {
                                    graph.addEdge(e.getId() + "_" + id + "" + i,
                                            n.getId() + "_" + id + "" + i, "End",
                                            true);
                                    Graphs.copyAttributes(graph.getEdge(e.getId()),
                                            graph.getEdge(e.getId() + "_" + id + "" + i));
                                    if (e.hasAttribute("array")) {
                                        shiftIndex(e, i);
                                    } else
                                        newName(e, i);
                                }
                            }
                            if (e.getNode1().getAttribute("att1").equals("hyper")) {
                                if (graph.getEdge(e.getId() + "_" + id + "" + i) == null) {
                                    graph.addEdge(e.getId() + "_" + id + "" + i,
                                            n.getId() + "_" + id + "" + i, e.getNode1().getId(),
                                            true);
                                    Graphs.copyAttributes(graph.getEdge(e.getId()),
                                            graph.getEdge(e.getId() + "_" + id + "" + i));
                                    if (e.hasAttribute("array")) {
                                        shiftIndex(e, i);
                                    } else
                                        newName(e, i);
                                }
                            }
                        }
                    }
                    if (!n.getAttribute("att1").equals("hyper")) {
                        Graphs.copyAttributes(graph.getNode(n.getId()),
                                graph.getNode(n.getId() + "_" + id + "" + i));
                    }
                    if (n.getEnteringEdge(0).getNode0().getAttribute("att1").equals("nop")) {
                        if (graph.getEdge(n.getEnteringEdge(0).getId() + "_" + id + "" + i) == null) {
                            graph.addEdge(n.getEnteringEdge(0).getId() + "_" + id + "" + i, "Start",
                                    n.getId() + "_" + id + "" + i, true);

                            Graphs.copyAttributes(graph.getEdge(n.getEnteringEdge(0).getId()),
                                    graph.getEdge(n.getEnteringEdge(0).getId() + "_" + id + "" + i));
                            if (n.hasAttribute("connect") && looptype.equals("pipeline")) {
                                if (n.getAttribute("connect").equals(connect_v)) {
                                    connect.add(graph.getNode(n.getId() + "_" + id + "" + i));
                                }
                            }
                            continue;
                        }
                    }

                    for (Edge e : n.getEachEnteringEdge()) {
                        Node prev = e.getNode0();

                        if (graph.getEdge(e.getId() + "_" + id + "" + i) == null) {
                            if (prev.getAttribute("att1").equals("hyper")
                            /* || prev.getAttribute("att1").equals("const")*/) {
                                graph.addEdge(e.getId() + "_" + id + "" + i,
                                        prev.getId(), n.getId() + "_" + id + "" + i,
                                        true);
                                Graphs.copyAttributes(graph.getEdge(e.getId()),
                                        graph.getEdge(e.getId() + "_" + id + "" + i));
                                check.add(graph.getNode(n.getId() + "_" + id + "" + i));

                            } else if (!n.getAttribute("att1").equals("hyper")) {
                                if (graph.getNode(prev.getId() + "_" + id + "" + i) == null) {
                                    graph.addNode(prev.getId() + "_" + id + "" + i);
                                }
                                graph.addEdge(e.getId() + "_" + id + "" + i,
                                        prev.getId() + "_" + id + "" + i, n.getId() + "_" + id + "" + i,
                                        true);
                                Graphs.copyAttributes(graph.getEdge(e.getId()),
                                        graph.getEdge(e.getId() + "_" + id + "" + i));

                            }

                            if (e.hasAttribute("array")) {

                                shiftIndex(e, i);
                            } else {

                                if (prev.getEnteringEdge(0).getNode0().getAttribute("att1").equals("nop") && !inner)
                                    continue;

                                newName(e, i);
                            }
                        }
                    }

                }
                for (Edge e : n.getEachLeavingEdge()) {
                    if (!e.getNode1().getAttribute("att1").equals("nop")
                            && !check.contains(e.getNode1())) {
                        templist.add(e.getNode1());
                        check.add(e.getNode1());

                    }
                }
            }

            addNode.clear();
            addNode.addAll(templist);
            templist.clear();

        }

        // updated label on input nodes
        Node start = graph.getNode("Start");
        for (Edge update : start.getEachLeavingEdge()) {
            if (update.getNode1().getAttribute("att1").equals("var"))
                graph.getNode(update.getNode1().getId()).setAttribute("label",
                        update.getNode1().getLeavingEdge(0).getAttribute("label").toString());
        }

        if (!connect.isEmpty() && fold != 0 && looptype.equals("pipeline")) {
            System.out.println("Dependant variable detected: " + connect_v);
            Node end = graph.getNode("End");
            Node aux = graph.getNode(end.getEnteringEdge(0).getNode0().getId());
            graph.removeNode("End");

            for (int k = 1; k < connect.size(); k++) {
                graph.removeEdge(connect.get(k).getEnteringEdge(0).getId());
                graph.addEdge("connect_l" + k, aux.getId(), connect.get(k).getLeavingEdge(0).getNode1().getId(), true);
                Graphs.copyAttributes(connect.get(k).getLeavingEdge(0), graph.getEdge("connect_l" + k));
                aux = connect.get(k).getLeavingEdge(0).getNode1();
                if (k + 1 == connect.size()) {
                    graph.addNode("End");
                    graph.getNode("End").addAttribute("label", "end");
                    graph.getNode("End").addAttribute("att1", "nop");
                    graph.addEdge("End from: " + aux.getId(), aux.getId(), "End", true);
                    Graphs.copyAttributes(connect.get(k).getLeavingEdge(0), graph.getEdge("End from: " + aux.getId()));
                }
                graph.removeNode(connect.get(k).getId());

            }

        }

        List<Node> removelist = new ArrayList<>();
        for (Node n : graph)
            if (n.getInDegree() == 0 && n.getOutDegree() == 0)
                removelist.add(n);

        for (Node n : removelist) {
            graph.removeNode(n.getId());
        }

    }

    /**
     * Unfolds loops that have been modified by memory optimizations. Unfolds loops that have not been modified by
     * memory optimizations. The initial dataflow is replicated by the indicated unrolling factor. After replicating
     * nodes the graph is structured to maintain dependencies between iterations. Memory optimization restructure the
     * graph needing a separate unfolding strategy.
     */
    public void parallel_seq_compute() {

        if (fold == 0)
            return;
        // create buff node for sequence
        Node end = graph.getNode("End");
        List<Edge> addlist = new ArrayList<>();
        for (Edge e : end.getEachEnteringEdge()) {
            addlist.add(e);
        }

        graph.removeNode("End");

        graph.addNode("buffer_end_0");
        graph.getNode("buffer_end_0").addAttribute("label", "buff");
        graph.getNode("buffer_end_0").addAttribute("att1", "nop");

        for (Edge e : addlist) {
            graph.addEdge("buffer_end_" + e.getNode0().getId(), e.getNode0().getId(), "buffer_end_0", true);
            Graphs.copyAttributes(e, graph.getEdge("buffer_end_" + e.getNode0().getId()));

        }

        for (int i = 0; i < fold; i++) {
            graph.addNode("buffer_end_" + (i + 1));
            graph.getNode("buffer_end_" + (i + 1)).addAttribute("label", "buff");
            graph.getNode("buffer_end_" + (i + 1)).addAttribute("att1", "buff");

        }

        List<Node> addNode = new ArrayList();
        for (Edge e : graph.getNode("Start").getEachLeavingEdge()) {
            addNode.add(e.getNode1());
        }
        List<Node> templist = new ArrayList();
        List<Node> connect = new ArrayList<>();
        int t = 0;

        while (!addNode.isEmpty()) {
            for (Node n : addNode) {

                if (n.hasAttribute("connect") && (looptype.equals("pipeline"))) {
                    System.out.println("here");
                    connect.add(n);
                }

                for (int i = 0; i < fold; i++) {
                    if (graph.getNode(n.getId() + "_" + id + "" + i) == null
                            && !n.getAttribute("att1").equals("hyper"))
                        graph.addNode(n.getId() + "_" + id + "" + i);

                    if (n.getAttribute("att1").equals("hyper") && i == 0) {

                        List<Graph> graphlist = new ArrayList<>();
                        graphlist = graph.getAttribute(n.getId());
                        Graph temg = graphlist.get(graphlist.size() - 1);
                        Algorithm unfold = new UnfoldingAlgorithm(loopname, fold, looptype, true, id, connect_v,
                                full_connect);
                        unfold.init(temg);
                        unfold.compute();
                        temg = ((UnfoldingAlgorithm) unfold).getGraph();
                        graphlist.set(graphlist.size() - 1, temg);
                        graph.setAttribute(n.getId(), graphlist);

                    }

                    for (Edge e : n.getEachLeavingEdge()) {
                        if (graph.getNode(e.getNode1().getId() + "_" + id + "" + i) == null) {
                            if (!e.getNode1().getAttribute("att1").equals("nop")
                                    && !e.getNode1().getAttribute("att1").equals("hyper")) {
                                graph.addNode(e.getNode1().getId() + "_" + id + "" + i);
                            }
                            if (e.getNode1().getAttribute("att1").equals("nop")
                                    && !n.getAttribute("att1").equals("hyper")) {
                                if (graph.getEdge(e.getId() + "_" + id + "" + i) == null) {
                                    graph.addEdge(e.getId() + "_" + id + "" + i,
                                            n.getId() + "_" + id + "" + i, "buffer_end_" + (i + 1),
                                            true);
                                    Graphs.copyAttributes(graph.getEdge(e.getId()),
                                            graph.getEdge(e.getId() + "_" + id + "" + i));
                                    if (e.hasAttribute("array")) {
                                        shiftIndex(e, i);
                                    } else if (!e.hasAttribute("inter_buffer"))
                                        newName(e, i);
                                }
                            }
                            if (e.getNode1().getAttribute("att1").equals("hyper")) {
                                if (graph.getEdge(e.getId() + "_" + id + "" + i) == null) {
                                    graph.addEdge(e.getId() + "_" + id + "" + i,
                                            n.getId() + "_" + id + "" + i, e.getNode1().getId(),
                                            true);
                                    Graphs.copyAttributes(graph.getEdge(e.getId()),
                                            graph.getEdge(e.getId() + "_" + id + "" + i));
                                    if (e.hasAttribute("array")) {
                                        shiftIndex(e, i);
                                    } else if (!e.hasAttribute("inter_buffer"))
                                        newName(e, i);
                                }
                            }
                        }
                    }
                    if (!n.getAttribute("att1").equals("hyper")) {
                        Graphs.copyAttributes(graph.getNode(n.getId()),
                                graph.getNode(n.getId() + "_" + id + "" + i));
                    }
                    if (n.getEnteringEdge(0).getNode0().getAttribute("att1").equals("nop")) {
                        if (graph.getEdge(n.getEnteringEdge(0).getId() + "_" + id + "" + i) == null) {
                            graph.addEdge(n.getEnteringEdge(0).getId() + "_" + id + "" + i, "buffer_end_" + (i),
                                    n.getId() + "_" + id + "" + i, true);

                            Graphs.copyAttributes(graph.getEdge(n.getEnteringEdge(0).getId()),
                                    graph.getEdge(n.getEnteringEdge(0).getId() + "_" + id + "" + i));
                            if (n.hasAttribute("connect") && looptype.equals("pipeline"))
                                connect.add(graph.getNode(n.getId() + "_" + id + "" + i));
                            continue;
                        }
                    }

                    for (Edge e : n.getEachEnteringEdge()) {
                        Node prev = e.getNode0();

                        if (graph.getEdge(e.getId() + "_" + id + "" + i) == null) {
                            if (prev.getAttribute("att1").equals("hyper")
                            /* || prev.getAttribute("att1").equals("const")*/) {
                                graph.addEdge(e.getId() + "_" + id + "" + i,
                                        prev.getId(), n.getId() + "_" + id + "" + i,
                                        true);
                                Graphs.copyAttributes(graph.getEdge(e.getId()),
                                        graph.getEdge(e.getId() + "_" + id + "" + i));
                                check.add(graph.getNode(n.getId() + "_" + id + "" + i));

                            } else if (!n.getAttribute("att1").equals("hyper")) {
                                if (graph.getNode(prev.getId() + "_" + id + "" + i) == null) {
                                    graph.addNode(prev.getId() + "_" + id + "" + i);
                                }
                                graph.addEdge(e.getId() + "_" + id + "" + i,
                                        prev.getId() + "_" + id + "" + i, n.getId() + "_" + id + "" + i,
                                        true);
                                Graphs.copyAttributes(graph.getEdge(e.getId()),
                                        graph.getEdge(e.getId() + "_" + id + "" + i));

                            }

                            if (e.hasAttribute("array")) {

                                shiftIndex(e, i);
                            } else {

                                if (prev.getEnteringEdge(0).getNode0().getAttribute("att1").equals("nop") && !inner)
                                    continue;
                                if (!e.hasAttribute("inter_buffer"))
                                    newName(e, i);
                            }
                        }
                    }

                }
                for (Edge e : n.getEachLeavingEdge()) {
                    if (!e.getNode1().getAttribute("att1").equals("nop") && !check.contains(e.getNode1())) {
                        templist.add(e.getNode1());
                        check.add(e.getNode1());

                    }
                }
            }

            addNode.clear();
            addNode.addAll(templist);
            templist.clear();

        }

        graph.addNode("End");
        graph.getNode("End").addAttribute("label", "end");
        graph.getNode("End").addAttribute("att1", "nop");

        // section that unites separate parts
        // Added after thesis leads to different results in filter_subband
        // Can be commented to revert back t original form.
        if (full_connect) {
            for (int j = 0; j < fold; j++) {
                Node start = graph.getNode("buffer_end_" + j);
                while (start.getOutDegree() > 0) {
                    Edge update = start.getLeavingEdge(0);
                    Edge outedge = update.getNode1().getLeavingEdge(0);
                    boolean check_connect = false;
                    for (Edge in : start.getEachEnteringEdge()) {
                        if (in.getAttribute("label").equals(outedge.getAttribute("label"))) {
                            graph.addEdge("in_" + in.getId() + "_to_out_" + outedge.getId(), in.getNode0().getId(),
                                    outedge.getNode1().getId(), true);
                            Graphs.copyAttributes(outedge,
                                    graph.getEdge("in_" + in.getId() + "_to_out_" + outedge.getId()));
                            check_connect = true;
                            break;
                        }
                    }
                    if (update.getNode1().getAttribute("att1").equals("var")) {
                        graph.getNode(update.getNode1().getId()).setAttribute("label",
                                outedge.getAttribute("label").toString());
                    }

                    if (!check_connect) {
                        graph.addEdge("in_Start_to_out_" + update.getId(), "Start", update.getNode1().getId(), true);
                        Graphs.copyAttributes(update, graph.getEdge("in_Start_to_out_" + update.getId()));
                        graph.removeEdge(update.getId());
                    } else
                        graph.removeNode(update.getNode1().getId());
                }
                while (start.getInDegree() > 0) {
                    Edge enter_to_out = start.getEnteringEdge(0);
                    graph.addEdge("in_" + enter_to_out.getId() + "to_end", enter_to_out.getNode0().getId(), "End",
                            true);
                    Graphs.copyAttributes(enter_to_out, graph.getEdge("in_" + enter_to_out.getId() + "to_end"));
                    graph.removeEdge(enter_to_out.getId());

                }
                graph.removeNode(start.getId());
            }
        }
        if (!full_connect) {
            for (int j = 0; j < fold; j++) {
                Node start = graph.getNode("buffer_end_" + j);
                for (Edge temp_edge : start.getEachLeavingEdge())
                    if (temp_edge.getNode1().getAttribute("att1").equals("var")) {
                        graph.getNode(temp_edge.getNode1().getId()).setAttribute("label",
                                temp_edge.getNode1().getLeavingEdge(0).getAttribute("label").toString());
                    }
            }
        }
        Node buff = graph.getNode("buffer_end_" + fold);
        addlist.clear();
        for (Edge e : buff.getEachEnteringEdge()) {
            addlist.add(e);
        }

        graph.removeNode(buff);

        for (Edge e : addlist) {

            graph.addEdge("end_" + e.getNode0().getId(), e.getNode0().getId(), "End", true);
            Graphs.copyAttributes(e, graph.getEdge("end_" + e.getNode0().getId()));

        }

    }

    /**
     * Isolate indexes of an array access.
     * 
     * @param name
     *            label of array access,
     * @param indexes
     *            List to store identified indexes of array access.
     * @return dimension of array access.
     */
    public int getIndexes(String name, List<Integer> indexes) {
        String temp = name;
        int dim = 0;
        while (temp.lastIndexOf("[") != -1) {

            temp = temp.substring(temp.indexOf("["));
            String temp2 = temp.substring(1, temp.indexOf("]"));

            indexes.add(Integer.parseInt(temp2));
            temp = temp.substring(temp.indexOf("]"));

            dim++;
        }
        return dim;

    }

    /**
     * Append a label to a variable based on the iteration number.
     * 
     * @param e
     *            edge containing variable.
     * @param i
     *            iteration number
     */
    public void newName(Edge e, int i) {
        if (e.hasAttribute("att1") && graph.getEdge(e.getId() + "_" + id + "" + i) != null) {

            if (e.getAttribute("att1").equals("var")) {
                String newlabel = graph.getEdge(e.getId() + "_" + id + "" + i).getAttribute("label");
                newlabel = newlabel.concat(
                        "_" + id + "" + i);
                graph.getEdge(e.getId() + "_" + id + "" + i).setAttribute("label", newlabel);

            }
        }
    }

    /**
     * Shifts the indexes of an array by the iteration number.
     * 
     * @param e
     *            edge containing array
     * @param i
     *            iteration number
     */
    public void shiftIndex(Edge e, int i) {

        List<Loopinfo> looplist = e.getAttribute("loop");
        if (looplist != null) {
            for (Loopinfo loop : looplist) {

                if (loopname.equals(loop.name)) {

                    String label = e.getAttribute("label");
                    String offset = e.getAttribute("label").toString();
                    List<Integer> off = new ArrayList<>();
                    int dim = getIndexes(offset, off);
                    for (int j = 0; j < dim; j++) {
                        Integer shift = (loop.increments.get(j) * (i + 1) + off.get(j));
                        label = replaceIndex(label, j + 1, shift, off.get(j).toString());
                        off.set(j, shift);

                    }

                    graph.getEdge(e.getId() + "_" + id + "" + i).setAttribute("label", label);
                    graph.getEdge(e.getId() + "_" + id + "" + i).setAttribute("Index", off);

                }
            }
        }

    }

    /**
     * Replaces a specific index of a dimension of an array with a new given value.
     * 
     * @param name
     *            access of array
     * @param i
     *            dimension of array to change
     * @param shift
     *            new index value
     * @param index
     *            old index value
     * @return the changed name
     */
    public String replaceIndex(String name, int i, Integer shift, String index) {
        int j = 0;
        int n = 0;
        char bracket = '[';
        for (j = 0; j < name.length(); j++) {
            char c = name.charAt(j);

            if (Character.compare(c, bracket) == 0) {
                n++;

            }
            if (n == i)
                break;
        }
        String cut = name.substring(j);

        cut = cut.replaceFirst(index, shift.toString());
        name = name.substring(0, j);
        name = name.concat(cut);

        return name;

    }

    /**
     * Returns the unfolded graph.
     * 
     * @return
     */
    public Graph getGraph() {
        return graph;
    }

}
