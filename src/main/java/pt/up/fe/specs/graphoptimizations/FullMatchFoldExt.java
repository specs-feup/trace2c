package pt.up.fe.specs.graphoptimizations;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.Graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Algorithm that implements the parallel folding of the tool. It matches given parallel sequences and checks if they can be
 * implemented by a loop.
 * @author CPU TOSH
 *
 */
public class FullMatchFoldExt implements Algorithm {

    public Graph subgraph;
    List<Graph> graphlist = new ArrayList<>();
    List<Graph> graphlist_orig = new ArrayList<>();
    List<List<Node>> nodestomatch = new ArrayList<>();
    List<List<List<Node>>> nodestomatchlist = new ArrayList<>();
    List<List<Node>> templist = new ArrayList<>();
    List<Integer> confilct_seq = new ArrayList<>();
    int N;
    String loopname = new String("loop_match_fold");
    boolean success = false;
    private int ind;
    boolean complete_fold = true;
    boolean unsuccsefull  = false;

    /**
     * 
     * @param graphlist List  containing the separate graphs to match.
     * @param i Integer used to create unique name for loop.
     */
    public FullMatchFoldExt(List<Graph> graphlist, int i) {
        // TODO Auto-generated constructor stub
        loopname = loopname.concat("" + i);
        subgraph = new DefaultGraph("subgraph");
        ind = i;
        this.graphlist = graphlist;
        graphlist_orig.addAll(graphlist);
        N = graphlist.size();

        // get All source Nodes
        for (Graph temg : graphlist) {
            Node end = temg.getNode("End");

            for (Edge e : end.getEachEnteringEdge()) {
                List<Node> list = new ArrayList<>();
                list.add(e.getNode0());
                nodestomatch.add(list);
                templist.add(new ArrayList<>());
            }
        }

    }

    @Override
    /**
     * Initializes the algorithm by comparing the bottom nodes of each sepearte sequence.
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        List<List<Integer>> start_end = new ArrayList<>();
        start_end.add(new ArrayList<>());
        int nl = 0;
        int nxt = 0;
        boolean newloop = true;

        Graphs.copyAttributes(graphlist.get(0), subgraph);
        for (int i = 0; i < nodestomatch.size() - 1; i++) {
            if (!(compareEdge(nodestomatch.get(i).get(0).getEnteringEdge(0),
                    nodestomatch.get(i + 1).get(0).getEnteringEdge(0)))) {
                start_end.get(nl).add(i);
                start_end.add(new ArrayList<>());
                nl++;

                newloop = true;
            } else if (newloop) {
                start_end.get(nl).add(i);
                nodestomatchlist.add(new ArrayList<>());
                nodestomatchlist.get(nl).add(new ArrayList<>());
                nodestomatchlist.get(nl).add(new ArrayList<>());
                Edge e = nodestomatch.get(i).get(0).getEnteringEdge(0);

                nodestomatchlist.get(nl).get(0).add(nodestomatch.get(i).get(0).getEnteringEdge(0).getNode0());
                nodestomatchlist.get(nl).get(1).add(nodestomatch.get(1 + i).get(0).getEnteringEdge(0).getNode0());
                nxt = 1;
                subgraph.addAttribute("initv",
                        0);

                List<Loopinfo> loop = subgraph.getEdge(e.getId())
                        .getAttribute("loop");
                if (loop != null) {

                    subgraph.addAttribute("+incr", loop.get(ind).increments.get(ind));

                }
                newloop = false;
            } else {
                nxt++;
                nodestomatchlist.get(nl).add(new ArrayList<>());
                nodestomatchlist.get(nl).get(nxt)
                        .add(nodestomatch.get(1 + i).get(0).getEnteringEdge(0).getNode0());
            }

        }
       
        for (List<Node> list : nodestomatch) {
            list.add(list.get(0).getEnteringEdge(0).getNode0());
            list.remove(0);
        }

    }

    @Override
    /**
     * Main method that implements the algorithm.
     */
    public void compute() {

        boolean localmatch = true;
        List<Node> tempadd = new ArrayList<>();
        templist = new ArrayList<>();
        for (int i = 0; i < nodestomatch.size(); i++) {
            List<Node> list1 = new ArrayList<>();
            // List<Node> list2 = new ArrayList<>();
            templist.add(list1);
            // inputnodes.add(list2);
        }
        confilct_seq.add(0);
        confilct_seq.add(graphlist_orig.size() - 1);

        int i;
        Node n = null;
        boolean end = false;
        while (!nodestomatchlist.get(0).get(0).isEmpty() && !end) {
            for (int k = 0; k < nodestomatchlist.size(); k++) {
                for (int j = 0; j < nodestomatchlist.get(k).get(0).size(); j++) {

                    tempadd = new ArrayList<>();
                    tempadd.add(nodestomatchlist.get(k).get(0).get(j));
                    for (i = 0; i < nodestomatchlist.get(k).size() - 1; i++) {

                        tempadd.add(nodestomatchlist.get(k).get(i + 1).get(j));
                        n = nodestomatchlist.get(k).get(i).get(j);
                        if (n.getAttribute("att1").toString().equals("op")) {
                            localmatch &= match(n, nodestomatchlist.get(k).get(i + 1).get(j), i);
                        }
                        /*if (!localmatch) {
                            System.out.println(n.getId() + "local");
                            break;
                        }*/
                        if (localmatch) {
                            subgraph.getNode(nodestomatchlist.get(k).get(0).get(j).getId().toString()).addAttribute(
                                    "mirror",
                                    new ArrayList<Node>());
                            subgraph.getNode(nodestomatchlist.get(k).get(0).get(j).getId().toString()).setAttribute(
                                    "mirror",
                                    tempadd);
                        }
               
                    }
                    if (!localmatch) {
                        complete_fold = false;
                        if (i == 0) {
                            // conflictnodes.addAll(nodestomatch.get(0));
                            nodestomatchlist.get(k).remove(0);
                            confilct_seq.set(0, confilct_seq.get(0) + 1);
                            templist.remove(0);
                            // inputnodes.remove(0);
                            System.out.println("remove here" + i + " " + nodestomatchlist.get(k).get(0).size());
                            // subgraph.clear();
                            j = -1;

                        }

                        if (i > nodestomatchlist.get(k).size() / 2) {
                            for (int p = nodestomatchlist.get(k).size() - 1; p > i; p--) {
                                // conflictnodes.addAll(nodestomatch.get(p));
                                confilct_seq.set(1, confilct_seq.get(1) - 1);
                                nodestomatchlist.get(k).remove(p);
                                templist.remove(p);
                                // inputnodes.remove(p);
                                System.out.println("remove " + i);
                            }
                        } else {
                            for (int p = 0; p < i; p++) {

                                // conflictnodes.addAll(nodestomatch.get(p));
                                confilct_seq.set(0, confilct_seq.get(0) + 1);
                                nodestomatchlist.get(k).remove(p);
                                templist.remove(p);
                                // inputnodes.remove(p);
                                System.out.println("remove here" + i + " " + nodestomatchlist.get(k).get(0).size());
                                j = -1;
                            }

                        }
                        if(nodestomatchlist.get(k).size()==1) {
                            System.out.println("Failed matching");
                            unsuccsefull=true;  
                            return;
                        }
                        localmatch = true;
                    }

                }

                for (int r = 0; r < nodestomatchlist.get(k).size(); r++) {
                    nodestomatchlist.get(k).get(r).clear();
                    for (Node nn : templist.get(r)) {
                        nodestomatchlist.get(k).get(r).add(nn);
                    }
                    templist.get(r).clear();
                }

            }
            
        }
        

        success = true;
        System.out.println("Matching complete");
        //System.out.println("Matched " + nodestomatchlist.size() + " sequences");
        if(complete_fold)
            System.out.println("All sequences matched");
        else
        System.out.println("not all sequnces matched");
        add_start_end(subgraph);

        subgraph.addAttribute("Loopname", loopname);
        subgraph.addAttribute("Pipeline", true);
        subgraph.addAttribute("size", nodestomatchlist.get(0).size());
        subgraph.addAttribute("plus_fold", 0);
        subgraph.addAttribute("mult_fold", 1);
        if (subgraph.hasAttribute("hyper_type")) {
            subgraph.setAttribute("hyper_type", "parallel");
        } else
            subgraph.addAttribute("hyper_type", "parallel");
        if (ind == 0) {
            graphlist.clear();

            graphlist.add(subgraph);
        }


    }

    /**
     * Algorithm that matches given nodes. First the nodes are compared and if they matc the edges are compared.
     * If the edges match the parent nodes are added to list of nodes to match.
     * @param n0 First node to compare
     * @param n1 Second node to compare
     * @param i  Integer indicating the location of n0 in the list of nodes to match.
     * @return true if nodes match.
     */
    private boolean match(Node n0, Node n1, int i) {
        //Edge res0 = n0.getLeavingEdge(0);
        //Edge res1 = n1.getLeavingEdge(0);

        if (n0.getInDegree() != n1.getInDegree()) {
            return false;
        }

        if (n0.getEnteringEdge(0).getNode0().getId().toString().equals("Start")) {

            // matchInputs(n0, n1, i);
            return true;
        }

        if (n0.getAttribute("label").toString().equals("=")) {
            if (n0.getEnteringEdge(0).getNode0().getId().toString().equals("Start"))
                return true;
            Edge input00 = n0.getEnteringEdge(0);
            Edge input10 = n1.getEnteringEdge(0);
            boolean not0 = false;

            if (input00.getNode0().hasAttribute("occ"))
                if (input00.getNode0().getAttribute("occ").equals("common"))
                    not0 = true;

            if (compareEdge(input00, input10)) {

                if (!templist.get(i)
                        .contains(input00.getNode0())) {
                    if (!not0)
                        templist.get(i)
                                .add(input00.getNode0());
                }
                if (!not0)
                    templist.get(i + 1).add(input10.getNode0());
                return true;
            }

            return false;
        }

        if (n0.getAttribute("label").toString().equals(n1.getAttribute("label").toString())) {

            if (n0.getInDegree() == 0)
                return true;

            Edge input00 = n0.getEnteringEdge(0);
            Edge input01 = n0.getEnteringEdge(1);
            Edge input10 = n1.getEnteringEdge(0);
            Edge input11 = n1.getEnteringEdge(1);
            boolean not00 = false;
            boolean not11 = false;
            boolean not01 = false;
            boolean not10 = false;

            if (input00.getNode0().hasAttribute("occ"))
                if (input00.getNode0().getAttribute("occ").equals("common"))
                    not00 = true;

            if (input10.getNode0().hasAttribute("occ"))
                if (input10.getNode0().getAttribute("occ").equals("common"))
                    not10 = true;

            if (input01.getNode0().hasAttribute("occ"))
                if (input01.getNode0().getAttribute("occ").equals("common"))
                    not01 = true;
            if (input11.getNode0().hasAttribute("occ"))
                if (input11.getNode0().getAttribute("occ").equals("common"))
                    not11 = true;
            // System.out.println(n0.getId());

            if (input00.getAttribute("pos").equals(input10.getAttribute("pos"))) {
                if (compareEdge(input00, input10) && compareEdge(input01, input11)) {

                    if (!templist.get(i)
                            .contains(input00.getNode0())
                            || !templist.get(i)
                                    .contains(input01.getNode0())) {

                        if (!not00 && input00.getNode0().getAttribute("att1").toString().equals("op"))
                            templist.get(i)
                                    .add(input00.getNode0());

                        if (!not01 && input01.getNode0().getAttribute("att1").toString().equals("op"))
                            templist.get(i)
                                    .add(input01.getNode0());

                    }
                    if (!not10 && input10.getNode0().getAttribute("att1").toString().equals("op"))
                        templist.get(i + 1).add(input10.getNode0());
                    if (!not11 && input11.getNode0().getAttribute("att1").toString().equals("op"))
                        templist.get(i + 1).add(input11.getNode0());

                    return true;
                }
            } else if (input00.getAttribute("pos").equals(input11.getAttribute("pos"))) {
                if (compareEdge(input00, input11) && compareEdge(input01, input10)) {
                    if (!templist.get(i)
                            .contains(input00.getNode0())
                            || !templist.get(i)
                                    .contains(input01.getNode0())) {

                        if (!not00 && input00.getNode0().getAttribute("att1").toString().equals("op"))
                            templist.get(i)
                                    .add(input00.getNode0());
                        if (!not01 && input01.getNode0().getAttribute("att1").toString().equals("op"))
                            templist.get(i)
                                    .add(input01.getNode0());

                    }

                    if (!not11 && input11.getNode0().getAttribute("att1").toString().equals("op"))
                        templist.get(i + 1).add(input11.getNode0());
                    if (!not10 && input00.getNode0().getAttribute("att1").toString().equals("op"))
                        templist.get(i + 1).add(input10.getNode0());
                    return true;
                }
            }

        }

        return false;

    }
    
    /**
     * Algorithm that compares edges. Comparison depends on type of edges. and in case these variables,
     * on whether they are arrays.
     * 
     * @param e0 first Edge to compare
     * @param e1 second Edge to compare
     * @return true if edges match
     */
    private boolean compareEdge(Edge e0, Edge e1) {

        String label0 = e0.getAttribute("label");
        String label1 = e1.getAttribute("label");
        String type0 = e0.getAttribute("att1");
        String type1 = e1.getAttribute("att1");

        String temptemp = new String();
        List<Integer> indexes0 = new ArrayList<>();
        List<Integer> indexes1 = new ArrayList<>();
        boolean array = false;
        Node add = null;

        if (!type0.equals(type1)) {

            return false;
        }

        if (type0.equals("const")) {
            if (label0.equals(label1)) {

                if (e0.hasAttribute("reproducable")) {
                    e1.addAttribute("reproducable", e0.getAttribute("reproducable").toString());

                } else {

                    add = e0.getNode0();
                    if (subgraph.getNode(add.getId()) == null) {
                        subgraph.addNode(add.getId());
                        Graphs.copyAttributes(e0.getNode0(), subgraph.getNode(add.getId()));
                    }

                    add = e0.getNode1();
                    if (subgraph.getNode(add.getId()) == null) {
                        subgraph.addNode(add.getId());
                        Graphs.copyAttributes(e0.getNode1(), subgraph.getNode(add.getId()));
                    }

                    subgraph.addEdge(e0.getId(), e0.getNode0().getId(), e0.getNode1().getId(), true);
                    for (String a : e0.getEachAttributeKey())
                        subgraph.getEdge(e0.getId()).addAttribute(a, e0.getAttribute(a).toString());
                    subgraph.getEdge(e0.getId()).addAttribute("marked", "pipe");

                    e0.addAttribute("reproducable", e0.getId());
                    e1.addAttribute("reproducable", e0.getId());

                }

                return true;

            }

        } else if (type0.equals("var")) {

            int index0 = 0;
            int index1 = 0;
            int dim = 0;
            String var0 = new String();
            String var1 = new String();
            String vtype0 = e0.getAttribute("att3");
            String vtype1 = e1.getAttribute("att3");
            // handle vectors and seld insert temps
            if (label0.contains("[") && label1.contains("[")) {

                array = true;
                var0 = new String(label0.substring(0, label0.indexOf("[")));
                var1 = new String(label1.substring(0, label1.indexOf("[")));
                // index0 = getIndexVec(label0);
                // index1 = getIndexVec(label1);

                temptemp = e0.getAttribute("label").toString();
                dim = getIndexes(temptemp, indexes0);
                temptemp = e1.getAttribute("label").toString();
                getIndexes(temptemp, indexes1);

            } else if (label0.contains("_i") && label1.contains("_i")) {
                var0 = new String(label0.substring(0, label0.indexOf("_i")));
                var1 = new String(label1.substring(0, label1.indexOf("_i")));
            }

            else {
                var0 = label0;
                var1 = label1;
            }

            if (var0.equals(var1) && array) {

                if (e0.hasAttribute("reproducable")) {

                    List<Loopinfo> temp = subgraph.getEdge(e0.getAttribute("reproducable")).getAttribute("loop");
                    for (int i = 0; i < temp.get(temp.size() - 1).dim; i++) {
                        if ((indexes1.get(i) - indexes0.get(i)) != temp.get(temp.size() - 1).increments.get(i))
                            return false;
                    }
                    e1.addAttribute("reproducable", e0.getAttribute("reproducable").toString());

                } else {

                    add = e0.getNode0();
                    if (subgraph.getNode(add.getId()) == null) {
                        subgraph.addNode(add.getId());
                        Graphs.copyAttributes(e0.getNode0(), subgraph.getNode(add.getId()));
                    }

                    add = e0.getNode1();
                    if (subgraph.getNode(add.getId()) == null) {
                        subgraph.addNode(add.getId());
                        Graphs.copyAttributes(e1.getNode1(), subgraph.getNode(add.getId()));
                    }

                    subgraph.addEdge(e0.getId(), e0.getNode0().getId(), e0.getNode1().getId(), true);
                    Graphs.copyAttributes(e0, subgraph.getEdge(e0.getId()));
                    subgraph.getEdge(e0.getId()).addAttribute("marked", "pipe");

                    List<Integer> increments = new ArrayList<Integer>();
                    for (int j = 0; j < dim; j++) {
                        increments.add(indexes1.get(j) - indexes0.get(j));
                    }

                    Loopinfo loopinfo = new Loopinfo(loopname, new ArrayList<>(increments), dim);
                    List<Loopinfo> list = new ArrayList<>();
                    if (!subgraph.getEdge(e0.getId()).hasAttribute("loop"))
                        subgraph.getEdge(e0.getId()).addAttribute("loop", new ArrayList<Loopinfo>());

                    if (!e0.hasAttribute("loop"))
                        list.addAll(subgraph.getEdge(e0.getId()).getAttribute("loop"));

                    else
                        list.addAll(e0.getAttribute("loop"));

                    list.add(loopinfo);
                    subgraph.getEdge(e0.getId()).setAttribute("loop", list);

                    e0.addAttribute("reproducable", e0.getId());
                    e1.addAttribute("reproducable", e0.getId());

                }

                return true;
            }

            if (vtype0.equals(vtype1)) {
                if (e0.hasAttribute("reproducable")) {
                    e1.addAttribute("reproducable", e0.getAttribute("reproducable").toString());
                } else {

                    add = e0.getNode0();
                    if (subgraph.getNode(add.getId()) == null) {
                        subgraph.addNode(add.getId());
                        Graphs.copyAttributes(e0.getNode0(), subgraph.getNode(add.getId()));
                    }

                    add = e0.getNode1();
                    if (subgraph.getNode(add.getId()) == null) {
                        subgraph.addNode(add.getId());
                        Graphs.copyAttributes(e0.getNode1(), subgraph.getNode(add.getId()));
                    }

                    subgraph.addEdge(e0.getId(), e0.getNode0().getId(), e0.getNode1().getId(), true);
                    for (String a : e0.getEachAttributeKey())
                        subgraph.getEdge(e0.getId()).addAttribute(a, e0.getAttribute(a).toString());
                    subgraph.getEdge(e0.getId()).addAttribute("marked", "pipe");
                    e0.addAttribute("reproducable", e0.getId());
                    e1.addAttribute("reproducable", e0.getId());
                }

                return true;
            }

        }

        return false;
    }

    public int getIndexVec(String name) {
        String temp = name.substring(name.indexOf("["));
        String temp2 = temp.substring(1, temp.length() - 1);

        return Integer.parseInt(temp2);

    }

    /**
     * Isolate indexes of an array access.
     * @param name    label of array access,
     * @param indexes List to store identified indexes of array access.
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
     * Adds start and end nodes to a graph. 
     * @param g graph to modify.
     */
    private static void add_start_end(Graph g) {
        g.addNode("Start");
        g.getNode("Start").addAttribute("label", "start");
        g.getNode("Start").addAttribute("att1", "nop");
        g.addNode("End");
        g.getNode("End").addAttribute("label", "end");
        g.getNode("End").addAttribute("att1", "nop");
        for (Node n : g.getEachNode()) {
            if (n.getInDegree() == 0 && !n.equals(g.getNode("Start")) && !n.equals(g.getNode("End")))
                g.addEdge("fold begin to:" + n.getId(), g.getNode("Start"), n, true);
            if (n.getOutDegree() == 0 && !n.equals(g.getNode("End")) && !n.equals(g.getNode("Start"))) {
                g.addEdge("fold end from:" + n.getId(), n, g.getNode("End"), true);
                Graphs.copyAttributes(n.getEnteringEdge(0), g.getEdge("fold end from:" + n.getId()));
            }
        }
    }

    /**
     * Replaces the subgraph of loop with a subgraph containing a node that calls the inner loop. 
     * @param graph Graph to change.
     * @return changed graph containing inner loop.
     */
    public Graph mergeInnerLoop(Graph graph) {
        Graph newg = new DefaultGraph("Parallel0");
        Graphs.copyAttributes(graph, newg);
        newg.addNode("Nop0");
        newg.getNode("Nop0").addAttribute("label", "Nop");
        newg.getNode("Nop0").addAttribute("att1", "nop");
        newg.addNode("HyperNode");
        newg.getNode("HyperNode").addAttribute("label", "hyper0");
        newg.getNode("HyperNode").addAttribute("att1", "hyper");
        // newg.getNode("HyperNode").addAttribute("hyper_type", "separate");
        newg.getNode("HyperNode").addAttribute("name", "hyper0");
        List<Graph> tmp = new ArrayList<>();
        tmp.add(subgraph);
        newg.addAttribute("HyperNode", tmp);

        List<String> temp = new ArrayList<>();

        if (!newg.hasAttribute("hierarchy")) {
            newg.addAttribute("hierarchy", new ArrayList<String>());
        }

        temp = newg.getAttribute("hierarchy");
        temp.add("HyperNode");
        newg.setAttribute("hierarchy", temp);
        newg.addEdge("edge_nop0", "Nop0", "HyperNode", true);
        newg.getEdge("edge_nop0").addAttribute("label", "nop");
        newg.getEdge("edge_nop0").addAttribute("att1", "nop");
        add_start_end(newg);
        return newg;
    }

    /**
     * Handles sequences that sequences that do not match. These are placed outside the loop on a higher level. 
     * @param graph Graph to insert the conflicting sequences.
     * @return      updated Graph.
     */
    public Graph handleConflict(Graph graph) {
        boolean add = false;

        for (int i = 0; i < graphlist_orig.size(); i++) {
            if (i < confilct_seq.get(0))
                add = true;
            else if (i > confilct_seq.get(1))
                add = true;

            if (add) {
                Graph add_graph;
                System.out.println("New merge " + i);
                add_graph = graphlist_orig.get(i);
                add_graph.removeNode("Start");
                graph = Graphs.merge(graph, add_graph);
                add = false;
            }
        }
        reconnect_start(graph);
        return graph;
    }
    
    public Graph revertSeparation(Graph graph) {
        System.out.println("Reverting Graph ");
        graph.removeNode("HyperNode");
        for(Graph g : graphlist_orig) {
            g.removeNode("Start");
            graph = Graphs.merge(graph, g);
        }
        graph.display();
        reconnect_start(graph);
        return graph;
    }

    /**
     * Gets matched subgraph
     * @return
     */
    public Graph getSubgraph() {
        return subgraph;
    }

    /**
     * Returns true if match succesfull.
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns true if all sequences matched.
     * @return
     */
    public boolean isComplete() {
        return complete_fold;
    }

    /**
     * Connects inputs nodes and output nodes wit the start and end node respectively.
     * @param g graph to modify.
     */
    private static void reconnect_start(Graph g) {
        for (Node n : g.getEachNode()) {
            if (n.getInDegree() == 0 && !n.equals(g.getNode("Start")) && !n.equals(g.getNode("End")))
                g.addEdge("begin to:" + n.getId(), g.getNode("Start"), n, true);
            else if (n.getOutDegree() == 0 && !n.equals(g.getNode("End")) && !n.equals(g.getNode("Start")))
                g.addEdge("end from:" + n.getId(), n, g.getNode("End"), true);

        }
    }

}
