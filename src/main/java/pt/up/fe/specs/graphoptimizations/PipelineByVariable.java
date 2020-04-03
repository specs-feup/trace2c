package pt.up.fe.specs.graphoptimizations;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.Graphs;

import java.util.*;

/**
 * Algorithm that idetifies and implements a pipeline in the graph.
 * 
 * @author Afonso TODO: Expand the merging to handle more complex hierarchy. Update unrolling forward in conflict to
 *         have a similar method as UnrollBackwardTop. To handle unrolling forward in case of unrolling looped nodes
 *         into upper level.
 */
public class PipelineByVariable implements Algorithm {

    String variable;
    Graph graph;
    List<Edge> pipelineOutputs = new ArrayList<>();
    public Graph subGraph;;
    List<List<List<Node>>> nodesToMatchList = new ArrayList<>();
    List<List<Node>> nodesToMatch = new ArrayList<>();
    List<List<Node>> inputNodes = new ArrayList<>();
    HashMap<Node, Boolean> conflictNodes = new HashMap<>();
    String loopName = "pipeline";
    String n_id;
    int endLevel = 0;
    int startLevel = 0;
    int currLevel = 0;
    int nPipelines = 0;
    boolean loop = true;
    boolean traversal = false;
    List<List<Node>> tempList;
    boolean cut_top = false;
    boolean cut_bottom = false;
    boolean unsuccessful = false;
    List<Integer> prevLoopSize = new ArrayList<>();
    List<String> prevLoopNames = new ArrayList<>();

    /**
     * 
     * @param variable
     *            name of the variable that the graph is pipelined along.
     * @param hierarchy
     * @param traversal
     *            boolean to indicate if the pipelining should traverse the hierarchy,
     * @param level
     *            current level of the dataflow being matched.
     * @param id
     *            Integer to create unique loop name
     */
    public PipelineByVariable(String variable, boolean hierarchy, boolean traversal, int level, String id) {
        // TODO Auto-generated constructor stub
        this.variable = variable;
        this.loop = hierarchy;
        this.traversal = traversal;
        this.currLevel = level;
        subGraph = new DefaultGraph("subgraph_" + level);
        loopName = loopName.concat(id);
        n_id = id;
    }

    @Override
    /**
     * Initializes the algorithm by descending the hierarchy, and calling the algorithm for lower levels. It identifies
     * the writes of the chosen variable and orders them in a list of nodes to match. The higher levels receive a list
     * of nodes to match from the lower levels.
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        this.graph = graph;
        Graphs.copyAttributes(graph, subGraph);
        // get all writes of variable
        Node n0 = graph.getNode("End");
        List<Node> templist_2 = new ArrayList<>();
        List<Node> addlist = new ArrayList<>();
        templist_2.add(n0);
        // !templist.isEmpty()

        while (!templist_2.isEmpty()) {
            for (Node n : templist_2) {
                if (n.getAttribute("att1").toString().equals("hyper") && traversal) {
                    Algorithm pipe = new PipelineByVariable(variable, true, true, currLevel + 1, n_id);
                    List<Graph> temp = null;
                    temp = graph.getAttribute(n.getId());
                    pipe.init(temp.get(temp.size() - 1));
                    if (unsuccessful == true)
                        return;
                    pipe.compute();

                    // subgraph = ((PipelineByVariable) pipe).getSubGraph();
                    // npipelined += ((PipelineByVariable) pipe).getNPipelined();
                    prevLoopNames = ((PipelineByVariable) pipe).getMatchedLoopname();
                    prevLoopSize = ((PipelineByVariable) pipe).getMatchedLoopsize();
                    nodesToMatch = ((PipelineByVariable) pipe).getInputs();
                    startLevel = ((PipelineByVariable) pipe).getStartLevel();
                    pipelineOutputs = ((PipelineByVariable) pipe).getOutputs();
                    String tempid = null;
                    subGraph.addNode(n.getId());

                    Graphs.copyAttributes(n, subGraph.getNode(n.getId()));
                    // ((PipelineByVariable) pipe).getSubGraph().display();
                    subGraph.getNode(n.getId()).addAttribute("merge", ((PipelineByVariable) pipe).getSubGraph());

                    for (List<Node> list : nodesToMatch) {
                        for (Node nn : list) {
                            tempid = nn.getId();
                            Node aux = graph.getNode(tempid);

                            list.set(list.indexOf(nn), aux);
                        }
                    }

                    for (Node nn : nodesToMatch.get(0)) {
                        // System.out.println("Cont " + nn.getId());
                        subGraph.addNode(nn.getId());
                        Graphs.copyAttributes(nn, subGraph.getNode(nn.getId()));

                        subGraph.addEdge(nn.getLeavingEdge(0).getId(),
                                subGraph.getNode(nn.getId()).getIndex(),
                                subGraph.getNode(n.getId()).getIndex(), true);
                        Graphs.copyAttributes(nn.getLeavingEdge(0),
                                subGraph.getEdge(nn.getLeavingEdge(0).getId()));

                    }
                    nodesToMatchList.add(nodesToMatch);
                    conflictNodes = ((PipelineByVariable) pipe).getConflictNodes();

                }

                for (Edge e : n.getEachEnteringEdge()) {
                    if (!n.getId().equals("End")) {
                        if (e.hasAttribute("name")) {
                            if (e.getNode0().getAttribute("att1").toString().equals("op")
                                    && e.getAttribute("name").toString().equals(variable)) {
                                pipelineOutputs.add(e);

                            }
                        } else if (e.hasAttribute("label")) {
                            if (e.getNode0().getAttribute("att1").toString().equals("op")
                                    && e.getAttribute("label").toString().equals(variable)) {
                                pipelineOutputs.add(e);

                            }

                        }
                    }


                    if (!e.getNode0().getAttribute("att1").toString().equals("nop")) {
                        addlist.add(e.getNode0());
                    }

                }

            }

            templist_2.clear();
            templist_2.addAll(addlist);
            addlist.clear();

        }

        if (nodesToMatch.isEmpty()) {
            Collections.reverse(pipelineOutputs);
            for (Edge e : pipelineOutputs) {

                List<Node> temp = new ArrayList<>();
                temp.add(e.getNode0());
                nodesToMatch.add(temp);

            }
            // System.out.println("Nodes to match " + nodestomatch.size());
            // compareEdge(nodestomatch.get(0).get(0).getLeavingEdge(0), nodestomatch.get(1).get(0).getLeavingEdge(0));
        }
        nodesToMatchList.add(nodesToMatch);

        if (subGraph.getNodeCount() == 0 && !nodesToMatch.isEmpty()) {
            startLevel = currLevel;
            // subgraph.addNode(pipelineoutputs.get(0).getNode0().getId());
            // Graphs.copyAttributes(pipelineoutputs.get(0).getNode0(),
            // subgraph.getNode(pipelineoutputs.get(0).getNode0().getId()));

        }

    }

    @Override
    /**
     * Main method that implements the algorithm.
     */
    public void compute() {
        // TODO Auto-generated method stub
        // Start by checking succes of matching of lower levels
        if (unsuccessful == true)
            return;
        int t = 0;

        boolean localmatch = true;
        List<Node> tempadd = new ArrayList<>();
        tempList = new ArrayList<>();
        for (int i = 0; i < nodesToMatch.size(); i++) {
            List<Node> list1 = new ArrayList<>();
            List<Node> list2 = new ArrayList<>();
            tempList.add(list1);
            inputNodes.add(list2);
        }

        int i;
        Node n = null;
        boolean end = false;
        // graph.display();
        // System.out.println("nodestomatchlist " + nodestomatchlist.size());
        while (!nodesToMatchList.get(0).get(0).isEmpty() && !end) {
            for (int k = 0; k < nodesToMatchList.size(); k++) {
                for (int j = 0; j < nodesToMatchList.get(k).get(0).size(); j++) {

                    tempadd = new ArrayList<>();
                    tempadd.add(nodesToMatchList.get(k).get(0).get(j));
                    for (i = 0; i < nodesToMatchList.get(k).size() - 1; i++) {

                        tempadd.add(nodesToMatchList.get(k).get(i + 1).get(j));
                        n = nodesToMatchList.get(k).get(i).get(j);
                        if (n.getAttribute("att1").toString().equals("op")) {
                            localmatch &= match(n, nodesToMatchList.get(k).get(i + 1).get(j), i);
                        }
                        if (!localmatch) {
                            // System.out.println(n.getId() + "local");
                            break;
                        }

                    }
                    if (!localmatch) {
                        if (i == 0) {
                            for (Node n_conf : nodesToMatch.get(0)) {
                                conflictNodes.put(n_conf, false);
                            }
                            // conflictnodes.addAll(nodestomatch.get(0)); To revert change confilctnodes to list of just
                            // nodes and not pair
                            nodesToMatchList.get(k).remove(0);
                            tempList.remove(0);
                            inputNodes.remove(0);
                            // System.out.println("remove here 0 " + i + " " + nodestomatchlist.get(k).get(0).size());

                            // subgraph.clear();
                            j = -1;

                        } else if (i > nodesToMatchList.get(k).size() / 2) {
                            for (int p = nodesToMatchList.get(k).size() - 1; p > i; p--) {
                                // conflictnodes.addAll(nodestomatch.get(p));
                                for (Node n_conf : nodesToMatch.get(p)) {
                                    conflictNodes.put(n_conf, true);
                                }
                                nodesToMatchList.get(k).remove(p);
                                tempList.remove(p);
                                inputNodes.remove(p);
                                // System.out.println("remove from upper " + i);
                                // cut_top = true;
                            }
                        } else {
                            for (int p = 0; p < i; p++) {
                                // Multiple mismatch at bottom.
                                for (Node n_conf : nodesToMatch.get(p)) {
                                    conflictNodes.put(n_conf, false);
                                }
                                // conflictnodes.addAll(nodestomatch.get(p));
                                nodesToMatchList.get(k).remove(p);
                                tempList.remove(p);
                                inputNodes.remove(p);
                                System.out.println("remove here" + i + " " + nodesToMatchList.get(k).get(0).size());

                                subGraph.clear();
                                j--;
                            }

                        }
                        if (nodesToMatchList.get(k).size() == 1) {
                            System.out.println("Failed matching");
                            unsuccessful = true;
                            return;
                        }
                        localmatch = true;
                    }

                }

                for (int r = 0; r < nodesToMatchList.get(k).size(); r++) {
                    nodesToMatchList.get(k).get(r).clear();
                    for (Node nn : tempList.get(r)) {
                        nodesToMatchList.get(k).get(r).add(nn);
                    }
                    tempList.get(r).clear();
                }

            }

        }
        if (graph.hasAttribute("Loopname"))
            prevLoopNames.add(graph.getAttribute("Loopname"));
        if (graph.hasAttribute("size"))
            prevLoopSize.add(graph.getAttribute("size"));
        System.out.println("End of pipelining Level: " + currLevel);
    }

    /**
     * Algorithm that matches given nodes. First the nodes are compared and if they matc the edges are compared. If the
     * edges match the parent nodes are added to list of nodes to match.
     * 
     * @param n0
     *            First node to compare
     * @param n1
     *            Second node to compare
     * @param i
     *            Integer indicating the location of n0 in the list of nodes to match.
     * @return true if nodes match.
     */
    private boolean match(Node n0, Node n1, int i) {

        if (n0.getInDegree() != n1.getInDegree()) {

            return false;

        }

        if (n0.getEnteringEdge(0).getNode0().getId().equals("Start")) {

            matchInputs(n0, n1, i);
            return true;
        }

        if (n0.getAttribute("label").toString().equals("=")) {
            if (n0.getEnteringEdge(0).getNode0().getId().equals("Start"))
                return true;
            Edge input00 = n0.getEnteringEdge(0);
            Edge input10 = n1.getEnteringEdge(0);
            boolean not0 = false;
            if (input00.hasAttribute("name"))
                if (input00.getAttribute("name").toString().equals(variable))
                    not0 = true;

            if (compareEdge(input00, input10)) {
                if (!tempList.get(i)
                        .contains(input00.getNode0())) {
                    if (!not0)
                        tempList.get(i)
                                .add(input00.getNode0());
                }
                if (!not0)
                    tempList.get(i + 1).add(input10.getNode0());
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
            // System.out.println(n0.getId());
            if (input00.hasAttribute("name")) {
                if (input00.getAttribute("name").toString().equals(variable))
                    not00 = true;
            } else if (input00.hasAttribute("label"))
                if (input00.getAttribute("label").toString().equals(variable))
                    not00 = true;

            if (input01.hasAttribute("name")) {
                if (input01.getAttribute("name").toString().equals(variable))
                    not01 = true;
            } else if (input01.hasAttribute("label"))
                if (input01.getAttribute("label").toString().equals(variable))
                    not01 = true;

            if (input11.hasAttribute("name")) {
                if (input11.getAttribute("name").toString().equals(variable))
                    not11 = true;
            } else if (input11.hasAttribute("label"))
                if (input11.getAttribute("label").toString().equals(variable))
                    not11 = true;

            if (input10.hasAttribute("name")) {
                if (input10.getAttribute("name").toString().equals(variable))
                    not10 = true;
            } else if (input10.hasAttribute("label"))
                if (input10.getAttribute("label").toString().equals(variable))
                    not10 = true;

            if (input00.getAttribute("pos").equals(input10.getAttribute("pos"))) {
                if (compareEdge(input00, input10) && compareEdge(input01, input11)) {

                    if (!tempList.get(i)
                            .contains(input00.getNode0())
                            || !tempList.get(i)
                                    .contains(input01.getNode0())) {
                        if (not00 && subGraph.getEdge(input00.getId()) != null) {
                            subGraph.getNode(input00.getNode0().getId()).addAttribute("connect", variable);
                            // System.out.println("connect1 " + input00.getNode0().getId());
                        }

                        if (not01 && subGraph.getEdge(input01.getId()) != null) {
                            subGraph.getNode(input01.getNode0().getId()).addAttribute("connect", variable);
                            // System.out.println("connect2 " + input01.getNode0().getId());
                        }

                        if (!not00 && input00.getNode0().getAttribute("att1").toString().equals("op"))
                            tempList.get(i)
                                    .add(input00.getNode0());

                        if (!not01 && input01.getNode0().getAttribute("att1").toString().equals("op"))
                            tempList.get(i)
                                    .add(input01.getNode0());

                    }
                    if (!not10 && input10.getNode0().getAttribute("att1").toString().equals("op"))
                        tempList.get(i + 1).add(input10.getNode0());
                    if (!not11 && input11.getNode0().getAttribute("att1").toString().equals("op"))
                        tempList.get(i + 1).add(input11.getNode0());

                    return true;
                }
            } else if (input00.getAttribute("pos").equals(input11.getAttribute("pos"))) {
                if (compareEdge(input00, input11) && compareEdge(input01, input10)) {
                    if (!tempList.get(i)
                            .contains(input00.getNode0())
                            || !tempList.get(i)
                                    .contains(input01.getNode0())) {

                        if (not00 && subGraph.getEdge(input00.getId()) != null) {
                            subGraph.getNode(input00.getNode0().getId()).addAttribute("connect", variable);
                            // System.out.println("connect3 " + input00.getNode0().getId());
                        }

                        if (not01 && subGraph.getEdge(input01.getId()) != null) {
                            subGraph.getNode(input01.getNode0().getId()).addAttribute("connect", variable);
                            // System.out.println("connect4 " + input01.getNode0().getId());
                        }

                        if (!not00 && input00.getNode0().getAttribute("att1").toString().equals("op"))
                            tempList.get(i)
                                    .add(input00.getNode0());
                        if (!not01 && input01.getNode0().getAttribute("att1").toString().equals("op"))
                            tempList.get(i)
                                    .add(input01.getNode0());

                    }

                    if (!not11 && input11.getNode0().getAttribute("att1").toString().equals("op"))
                        tempList.get(i + 1).add(input11.getNode0());
                    if (!not10 && input00.getNode0().getAttribute("att1").toString().equals("op"))
                        tempList.get(i + 1).add(input10.getNode0());
                    return true;
                }
            }

        }

        return false;

    }

    /**
     * Algorithm that compares edges. Comparison depends on type of edges. and in case these variables, on whether they
     * are arrays.
     * 
     * @param e0
     *            first Edge to compare
     * @param e1
     *            second Edge to compare
     * @return true if edges match
     */
    private boolean compareEdge(Edge e0, Edge e1) {

        String label0 = e0.getAttribute("label").toString();
        String label1 = e1.getAttribute("label").toString();
        String type0 = e0.getAttribute("att1").toString();
        String type1 = e1.getAttribute("att1").toString();

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
                    e0.addAttribute("reproducable", e0.getId());
                    e1.addAttribute("reproducable", e0.getId());

                    add = e0.getNode0();
                    if (subGraph.getNode(add.getId()) == null) {
                        subGraph.addNode(add.getId());
                        Graphs.copyAttributes(e0.getNode0(), subGraph.getNode(add.getId()));
                    }

                    add = e0.getNode1();
                    if (subGraph.getNode(add.getId()) == null) {
                        subGraph.addNode(add.getId());
                        Graphs.copyAttributes(e0.getNode1(), subGraph.getNode(add.getId()));
                    }

                    subGraph.addEdge(e0.getId(), e0.getNode0().getId(), e0.getNode1().getId(), true);
                    for (String a : e0.getEachAttributeKey())
                        subGraph.getEdge(e0.getId()).addAttribute(a, e0.getAttribute(a).toString());
                    subGraph.getEdge(e0.getId()).addAttribute("marked", "pipe");
                }

                return true;

            }

        } else if (type0.equals("var")) {

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
            } else {
                var0 = label0;
                var1 = label1;
            }

            if (var0.equals(var1) && array) {

                if (e0.hasAttribute("reproducable")) {

                    List<Loopinfo> temp = subGraph.getEdge(e0.getAttribute("reproducable")).getAttribute("loop");
                    for (int i = 0; i < temp.get(temp.size() - 1).dim; i++) {
                        if ((indexes1.get(i) - indexes0.get(i)) != temp.get(temp.size() - 1).increments.get(i))
                            return false;
                    }
                    e1.addAttribute("reproducable", e0.getAttribute("reproducable").toString());

                } else {

                    add = e0.getNode0();
                    if (subGraph.getNode(add.getId()) == null) {
                        subGraph.addNode(add.getId());
                        Graphs.copyAttributes(e0.getNode0(), subGraph.getNode(add.getId()));
                    }

                    add = e0.getNode1();
                    if (subGraph.getNode(add.getId()) == null) {
                        subGraph.addNode(add.getId());
                        Graphs.copyAttributes(e1.getNode1(), subGraph.getNode(add.getId()));
                    }

                    subGraph.addEdge(e0.getId(), e0.getNode0().getId(), e0.getNode1().getId(), true);
                    Graphs.copyAttributes(e0, subGraph.getEdge(e0.getId()));
                    subGraph.getEdge(e0.getId()).addAttribute("marked", "pipe");

                    List<Integer> increments = new ArrayList<Integer>();
                    for (int j = 0; j < dim; j++)
                        increments.add(indexes1.get(j) - indexes0.get(j));

                    Loopinfo loopinfo = new Loopinfo(loopName, new ArrayList<>(increments), dim);
                    List<Loopinfo> list = new ArrayList<>();
                    if (!subGraph.getEdge(e0.getId()).hasAttribute("loop"))
                        subGraph.getEdge(e0.getId()).addAttribute("loop", new ArrayList<Loopinfo>());

                    if (!e0.hasAttribute("loop"))
                        list.addAll(subGraph.getEdge(e0.getId()).getAttribute("loop"));

                    else
                        list.addAll(e0.getAttribute("loop"));

                    // loopinfo.print();
                    list.add(loopinfo);
                    subGraph.getEdge(e0.getId()).setAttribute("loop", list);

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
                    if (subGraph.getNode(add.getId()) == null) {
                        subGraph.addNode(add.getId());
                        Graphs.copyAttributes(e0.getNode0(), subGraph.getNode(add.getId()));
                    }

                    add = e0.getNode1();
                    if (subGraph.getNode(add.getId()) == null) {
                        subGraph.addNode(add.getId());
                        Graphs.copyAttributes(e0.getNode1(), subGraph.getNode(add.getId()));
                    }

                    subGraph.addEdge(e0.getId(), e0.getNode0().getId(), e0.getNode1().getId(), true);
                    for (String a : e0.getEachAttributeKey())
                        subGraph.getEdge(e0.getId()).addAttribute(a, e0.getAttribute(a).toString());
                    subGraph.getEdge(e0.getId()).addAttribute("marked", "pipe");
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
     * matches input nodes and stores them in list to give back to higher levels.
     * 
     * @param n0
     *            First node to compare
     * @param n1
     *            Second node to compare
     * @param i
     *            Integer indicating the location of n0 in the list of nodes to match.
     * @return true if nodes match.
     */
    public boolean matchInputs(Node n0, Node n1, int i) {

        if (n0.getAttribute("att1").toString().equals("op")) {
            if (n0.getAttribute("label").toString().equals(n1.getAttribute("label").toString())) {
                if (!inputNodes.get(i)
                        .contains(n0)) {

                    inputNodes.get(i)
                            .add(n0);
                }

                inputNodes.get(i + 1).add(n1);
            }
        }
        return true;
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
     * merge the identified subgraph with the previous dataflow. The merging depends on the starting and end level of
     * the matching.
     * 
     * @return the merged graph containing the pipeline.
     */
    public Graph mergeResult() {

        Graph pipeline = new DefaultGraph("pipeline");
        Graphs.copyAttributes(graph, pipeline);
        System.out.println("Pipeline detected. Restructuring dataflow");
        System.out.println("Start level of matching " + startLevel + " || End level of matching" + currLevel);

        if (startLevel != currLevel || startLevel == 0) {

            pipeline.addNode("HyperNode");
            pipeline.getNode("HyperNode").addAttribute("label", "hyper2");
            pipeline.getNode("HyperNode").addAttribute("att1", "hyper");
            pipeline.getNode("HyperNode").addAttribute("hyper_type", "pipe");
            pipeline.getNode("HyperNode").addAttribute("name", "hyper2");

            for (Map.Entry<Node, Boolean> n_pair : conflictNodes.entrySet()) {

                /*if (n.hasAttribute("mirror")) {
                    List<Node> tmp = n.getAttribute("mirror");
                    for (Node nn : tmp)
                        unrollBackwards(pipeline, nn, "Start");
                
                } else {*/
                if (!n_pair.getValue()) {// Check if unrolBackward or forwards
                    // Unrolling to upper level with Top method not based on mirror nodes
                    // Top method better. Should be extend to unroll forwards. and imprved to scale with upper levels.

                    if (prevLoopSize.size() > 0 && currLevel == 0) {// Check if nodes placed at upper level need
                                                                    // unrolling
                        pipeline = unrollBackwardsTop(pipeline, n_pair.getKey(), "Start", prevLoopSize.get(0),
                                prevLoopNames.get(0));
                    } else
                        unrollBackwards(pipeline, n_pair.getKey(), "Start");

                } else {
                    unrollForwards_conflict(pipeline, n_pair.getKey());
                }
                // }

            }

            Node remainder = pipelineOutputs.get(pipelineOutputs.size() - 1).getNode1();
            Node nxtremainder = remainder.getLeavingEdge(0).getNode1();

            if (!nxtremainder.getAttribute("att1").toString().equals("nop")) {

                if (remainder.hasAttribute("mirror")) {
                    List<Node> tmp = remainder.getAttribute("mirror");
                    for (Node nn : tmp)
                        unrollForwards(pipeline, nn);

                } else {
                    unrollForwards(pipeline, remainder);
                }
                for (Edge e : nxtremainder.getEachEnteringEdge()) {
                    if (!e.getNode0().getId().equals(remainder.getId())) {
                        if (e.getNode0().hasAttribute("mirror")) {
                            List<Node> tmp = e.getNode0().getAttribute("mirror");
                            for (Node nn : tmp)
                                unrollBackwards(pipeline, nn, "Start");

                        } else
                            unrollBackwards(pipeline, e.getNode0(), "Start");
                    }
                }

            }

            List<Graph> graphlist = new ArrayList<>();
            graphlist.add(graph);
            graphlist.add(subGraph);
            pipeline.addAttribute("HyperNode", graphlist);

            List<String> temp = new ArrayList<>();

            if (!pipeline.hasAttribute("hierarchy")) {
                pipeline.addAttribute("hierarchy", new ArrayList<String>());
            }

            temp = pipeline.getAttribute("hierarchy");
            temp.clear();
            temp.add("HyperNode");
            pipeline.setAttribute("hierarchy", temp);
            subGraph.addAttribute("Loopname", loopName);
            subGraph.addAttribute("initv", 0);
            subGraph.addAttribute("+incr", 1);
            subGraph.addAttribute("size", nodesToMatch.size());
            subGraph.addAttribute("plus_fold", 0);
            subGraph.addAttribute("mult_fold", 1);
            subGraph.addAttribute("connect", variable);
            subGraph.addAttribute("Pipeline", true);
            if (subGraph.hasAttribute("hyper_type")) {
                subGraph.setAttribute("hyper_type", "pipeline");
            } else
                subGraph.addAttribute("hyper_type", "pipeline");

            add_start_end(pipeline);

            absorbHierarchy(subGraph);

            return pipeline;
        } else {// Here the loop is inner

            pipeline.addNode("HyperNode");
            pipeline.getNode("HyperNode").addAttribute("label", "hyper2");
            pipeline.getNode("HyperNode").addAttribute("att1", "hyper");
            pipeline.getNode("HyperNode").addAttribute("hyper_type", "pipe");
            pipeline.getNode("HyperNode").addAttribute("name", "hyper2");

            for (Map.Entry<Node, Boolean> n_pair : conflictNodes.entrySet())
                if (!n_pair.getValue())
                    unrollBackwards(pipeline, n_pair.getKey(), "Start");
                else
                    unrollForwards_conflict(pipeline, n_pair.getKey());

            Node remainder = pipelineOutputs.get(pipelineOutputs.size() - 1).getNode1();
            Node nxtremainder = remainder.getLeavingEdge(0).getNode1();
            if (!nxtremainder.getAttribute("att1").toString().equals("nop")) {
                appendOperation(remainder, pipelineOutputs.get(pipelineOutputs.size() - 1), pipeline, "HyperNode");
                // System.out.println("unroll this " + remainder);
                // System.out.println("unroll forwards " + nxtremainder);
                unrollForwards(pipeline, nxtremainder);
                unrollBackwards(pipeline, remainder,
                        pipelineOutputs.get(pipelineOutputs.size() - 1).getNode0().getId());

            }
            List<Graph> graphlist = new ArrayList<>();
            graphlist.add(graph);
            graphlist.add(subGraph);
            pipeline.addAttribute("HyperNode", graphlist);

            List<String> temp = new ArrayList<>();

            if (!pipeline.hasAttribute("hierarchy")) {
                pipeline.addAttribute("hierarchy", new ArrayList<String>());
            }

            temp = pipeline.getAttribute("hierarchy");
            temp.add("HyperNode");
            pipeline.setAttribute("hierarchy", temp);
            subGraph.addAttribute("Loopname", loopName);
            subGraph.addAttribute("initv", 0);
            subGraph.addAttribute("+incr", 1);
            subGraph.addAttribute("size", nodesToMatch.size());
            subGraph.addAttribute("plus_fold", 0);
            subGraph.addAttribute("mult_fold", 1);
            subGraph.addAttribute("Pipeline", true);
            subGraph.addAttribute("connect", variable);
            if (pipeline.hasAttribute("Pipeline"))
                pipeline.removeAttribute("Pipeline");
            if (graph.hasAttribute("level"))
                subGraph.addAttribute("level", true);
            if (subGraph.hasAttribute("hyper_type")) {
                subGraph.setAttribute("hyper_type", "pipeline");
            } else
                subGraph.addAttribute("hyper_type", "pipeline");
            add_start_end(pipeline);
            absorbHierarchy(subGraph);

            // graph.display();
            // pipeline.display();
            /*if (pipeline.getAttribute("hyper_type").equals("pipeline")) {
                System.out.println("Need fixes");
                for (Node n : graph.getEachNode())
                    if (n.hasAttribute("connect")) {
                        System.out.println(" connecting node " + n.getAttribute("connect") + "  " + n.getId());
                    }
            }*/
            return pipeline;
        }
    }

    /**
     * Updates the subgraphs to be included as correct dataflows in the hierarchy.
     * 
     * @param graph
     *            Graph to be absorbed.
     */
    public void absorbHierarchy(Graph graph) {

        add_start_end(graph);
        // graph.display();

        for (Node n : graph) {
            if (n.hasAttribute("connect")) {
                Node end = graph.getNode("End");
                // System.out.println("connect final " + n.getId());
                Graphs.copyAttributes(n.getLeavingEdge(0), graph.getEdge(end.getEnteringEdge(0).getId()));

            }

            if (n.getAttribute("att1").toString().equals("hyper")) {
                Graph tempg = graph.getNode(n.getId()).getAttribute("merge");
                absorbHierarchy(tempg);
                List<Graph> temp = new ArrayList<>();

                temp = graph.getAttribute(n.getId());
                Graph auxtem = temp.get(temp.size() - 1);
                temp.clear();
                Graphs.copyAttributes(auxtem, tempg);
                temp.add(auxtem);
                temp.add(tempg);
                graph.setAttribute(n.getId(), temp);
            }
            for (Edge e : n.getEachEdge()) {
                if (e.hasAttribute("newloop")) {
                    if (!e.hasAttribute("reproducable"))
                        e.addAttribute("reproducable", new String());

                    e.setAttribute("reproducable", e.getAttribute("newloop").toString());
                }
            }
        }

        // System.out.println(graph.getId());
        if (!graph.getAttribute("hyper_type").equals("pipeline")) {
            graph.removeAttribute("Pipeline");
            // System.out.println(graph.getId());
        }

    }

    /**
     * Returns the created subgraph
     * 
     * @return
     */
    public Graph getSubGraph() {
        return subGraph;
    }

    /**
     * Return the nodes that caused conflicts the matching.
     * 
     * @return
     */
    public HashMap<Node, Boolean> getConflictNodes() {
        return conflictNodes;
    }

    /**
     * Get list of nodes to match.
     * 
     * @return
     */
    public List<List<Node>> getNodesToMatch() {
        return nodesToMatch;
    }

    /**
     * Get list of list of nodes to match.
     * 
     * @return
     */
    public List<List<List<Node>>> getNodesToMatchList() {
        return nodesToMatchList;
    }

    /**
     * Get input nodes.
     * 
     * @return
     */
    public List<List<Node>> getInputs() {
        return inputNodes;
    }

    /**
     * Get number o pipelines.
     * 
     * @return
     */
    public int getNPipelines() {
        return nPipelines;
    }

    /**
     * Get list of edges that were the original separate pipeline outputs.
     * 
     * @return
     */
    public List<Edge> getOutputs() {
        return pipelineOutputs;
    }

    /**
     * Get loopname.
     * 
     * @return
     */
    public String getLoopName() {
        return loopName;
    }

    /**
     * Return starting level of algorithm.
     * 
     * @return
     */
    public int getStartLevel() {
        return startLevel;
    }

    /**
     * Add start and end nodes to graph.
     * 
     * @param g
     *            graph to change.
     */
    private static void add_start_end(Graph g) {

        g.addNode("Start");
        g.getNode("Start").addAttribute("label", "start");
        g.getNode("Start").addAttribute("att1", "nop");
        if (g.getNode("End") == null) {
            g.addNode("End");
        }
        g.getNode("End").addAttribute("label", "end");
        g.getNode("End").addAttribute("att1", "nop");

        for (Node n : g.getEachNode()) {
            if (n.getInDegree() == 0 && !n.equals(g.getNode("Start")) && !n.equals(g.getNode("End")))
                g.addEdge("pipeline begin to:" + n.getId(), g.getNode("Start"), n, true);
            else if (n.getOutDegree() == 0 && !n.equals(g.getNode("End")) && !n.equals(g.getNode("Start")))
                g.addEdge("pipeline end from:" + n.getId(), n, g.getNode("End"), true);

        }
    }

    /**
     * Copy a sequence starting at a a given node and traveling backwards through its parents to the given graph.
     * 
     * @param pipeline
     *            Graph to take unrolled nodes.
     * @param n
     *            Node to add.
     * @param stop
     *            name of Node that indicates the stopping point of unrolling.
     */
    private void unrollBackwards(Graph pipeline, Node n, String stop) {
        List<Node> unroll = new ArrayList<>();
        List<Node> addlist = new ArrayList<>();
        unroll.add(n);
        while (!unroll.isEmpty())
            while (!unroll.isEmpty()) {
                for (int i = 0; i < unroll.size(); i++) {
                    if (pipeline.getNode(unroll.get(i).getId()) == null) {
                        pipeline.addNode(unroll.get(i).getId());
                        Graphs.copyAttributes(unroll.get(i), pipeline.getNode(unroll.get(i).getId()));
                    }

                    for (Edge e : unroll.get(i).getEachEnteringEdge()) {
                        if (!e.getNode0().getId().toString().equals("Start")
                                && !e.getNode0().getId().toString().equals(stop))
                            addlist.add(e.getNode0());
                    }

                    for (Edge e : unroll.get(i).getEachLeavingEdge()) {
                        if (pipeline.getEdge(e.getId()) == null) {
                            if (pipeline.getNode(e.getNode1().getId()) == null) {
                                pipeline.addEdge(e.getId().toString(), unroll.get(i).getId().toString(),
                                        pipeline.getNode("HyperNode").toString(), true);

                                Graphs.copyAttributes(e, pipeline.getEdge(e.getId()));
                                break;
                            }
                            pipeline.addEdge(e.getId().toString(), unroll.get(i).getId().toString(),
                                    pipeline.getNode(e.getNode1().getId()).getId().toString(),
                                    true);
                            Graphs.copyAttributes(e, pipeline.getEdge(e.getId()));
                        }
                    }

                }
                unroll.clear();
                unroll.addAll(addlist);
                addlist.clear();
            }

    }

    /**
     * 
     * Copy a sequence starting at a a given node and traveling backwards through its parents to the given graph. This
     * version unrolls nodes to place in higher levels.
     * 
     * TODO: Improve method to handle multiple upper loops
     * 
     * @param pipeline
     *            Graph to take unrolled nodes.
     * @param n
     *            Node to add.
     * @param stop
     *            name of Node that indicates the stopping point of unrolling.
     * @param size
     *            size of upper loop.
     * @param loopname
     *            name of upper loop.
     * @return changed graph.
     */
    private Graph unrollBackwardsTop(Graph pipeline, Node n, String stop, int size, String loopname) {
        List<Node> unroll = new ArrayList<>();
        List<Node> addlist = new ArrayList<>();
        unroll.add(n);
        while (!unroll.isEmpty()) {
            for (int i = 0; i < unroll.size(); i++) {
                boolean addonce = true;
                for (int j = 0; j < size; j++) {
                    if (pipeline.getNode(unroll.get(i).getId() + "_" + j) == null) {

                        pipeline.addNode(unroll.get(i).getId() + "_" + j);
                        Graphs.copyAttributes(unroll.get(i), pipeline.getNode(unroll.get(i).getId() + "_" + j));
                    }

                    for (Edge e : unroll.get(i).getEachEnteringEdge()) {
                        if (!e.getNode0().getId().toString().equals("Start")
                                && !e.getNode0().getId().toString().equals(stop) && addonce)
                            addlist.add(e.getNode0());
                    }

                    addonce = false;

                    for (Edge e : unroll.get(i).getEachLeavingEdge()) {
                        if (pipeline.getEdge(e.getId() + "_" + j) == null) {
                            if (pipeline.getNode(e.getNode1().getId() + "_" + j) == null) {
                                pipeline.addEdge(e.getId().toString() + "_" + j,
                                        unroll.get(i).getId().toString() + "_" + j,
                                        pipeline.getNode("HyperNode").toString(), true);
                                Graphs.copyAttributes(e, pipeline.getEdge(e.getId() + "_" + j));
                                if (e.hasAttribute("array")) {
                                    shiftIndex(pipeline, pipeline.getEdge(e.getId() + "_" + j), j - 1, loopname);
                                }
                                break;
                            }
                            pipeline.addEdge(e.getId().toString() + "_" + j, unroll.get(i).getId().toString() + "_" + j,
                                    pipeline.getNode(e.getNode1().getId() + "_" + j).getId().toString(),
                                    true);
                            Graphs.copyAttributes(e, pipeline.getEdge(e.getId() + "_" + j));
                            if (e.hasAttribute("array")) {
                                shiftIndex(pipeline, pipeline.getEdge(e.getId() + "_" + j), j - 1, loopname);
                            }
                        }
                    }
                }
            }
            unroll.clear();
            unroll.addAll(addlist);
            addlist.clear();
        }
        // pipeline.display();
        return pipeline;
    }

    /**
     * Shifts the indexes of an array by the iteration number.
     * 
     * @param e
     *            edge containing array
     * @param i
     *            iteration number
     */
    public void shiftIndex(Graph graph, Edge e, int i, String loopname) {

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

                    graph.getEdge(e.getId()).setAttribute("label", label);
                    graph.getEdge(e.getId()).setAttribute("Index", off);

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
     * Copy a sequence of nodes starting at a specific node and going forward with its children to the given graph.
     * 
     * @param pipeline
     *            Graph to take unrolled nodes.
     * @param n
     *            Node to add.
     */
    private void unrollForwards(Graph pipeline, Node n) {
        List<Node> unroll = new ArrayList<>();
        List<Node> addlist = new ArrayList<>();
        unroll.add(n);

        while (!unroll.isEmpty()) {

            for (int i = 0; i < unroll.size(); i++) {

                pipeline.addNode(unroll.get(i).getId());
                Graphs.copyAttributes(unroll.get(i), pipeline.getNode(unroll.get(i).getId()));
                for (Edge e : unroll.get(i).getEachEnteringEdge()) {
                    if (pipeline.getNode(e.getNode0().getId()) == null) {

                        if (e.getNode0().getAttribute("att1").equals("var")) {
                            pipeline.addNode(e.getNode0().getId());
                            Graphs.copyAttributes(e.getNode0(), pipeline.getNode(e.getNode0().getId()));
                            pipeline.addEdge(e.getId(), pipeline.getNode(e.getNode0().getId()).toString(),
                                    unroll.get(i).getId(), true);
                            Graphs.copyAttributes(e, pipeline.getEdge(e.getId()));
                        } else {
                            pipeline.addEdge(e.getId(), pipeline.getNode("HyperNode").toString(),
                                    unroll.get(i).getId(), true);

                            Graphs.copyAttributes(e, pipeline.getEdge(e.getId()));

                        }
                        continue;
                    }

                    pipeline.addEdge(e.getId().toString(), pipeline.getNode(e.getNode0().getId()).getId().toString(),
                            unroll.get(i).getId().toString(),
                            true);
                    Graphs.copyAttributes(e, pipeline.getEdge(e.getId()));

                }

                for (Edge e : unroll.get(i).getEachLeavingEdge()) {
                    addlist.add(e.getNode1());
                }

            }
            unroll.clear();
            unroll.addAll(addlist);
            addlist.clear();
        }
    }

    /**
     * Copies the nodes that appear after after a conflicting node.
     * 
     * @param pipeline
     *            Graph to take unrolled nodes.
     * @param n
     *            conflicting node to add and starting point to unroll forward.
     */
    private void unrollForwards_conflict(Graph pipeline, Node n) {
        pipeline.addNode(n.getId());
        Graphs.copyAttributes(n, pipeline.getNode(n.getId()));
        for (Edge e : n.getEachEnteringEdge()) {
            if (e.getAttribute("label").equals(variable)) {
                pipeline.addEdge(e.getId().toString(), pipeline.getNode("HyperNode").toString(),
                        n.getId().toString(), true);
                Graphs.copyAttributes(e, pipeline.getEdge(e.getId()));
            } else {
                unrollBackwards(pipeline, e.getNode0(), "Start");
            }
        }
        unrollForwards(pipeline, n.getLeavingEdge(0).getNode1());
    }

    /**
     * Adds operation node to given graph connecting it to specified node.
     * 
     * @param n
     *            operation node to append
     * @param e
     *            edge to copy attributes.
     * @param pipeline
     *            graph to take the added node.
     * @param hyper
     *            Name of the node in pipeline to connect to n by e.
     */
    private void appendOperation(Node n, Edge e, Graph pipeline, String hyper) {

        pipeline.addNode(n.getId());
        Graphs.copyAttributes(n, pipeline.getNode(n.getId()));
        pipeline.addEdge(e.getId(), hyper, n.getId(), true);
        Graphs.copyAttributes(e, pipeline.getEdge(e.getId()));
        return;
    }

    public boolean getSuccess() {
        return !unsuccessful;
    }

    public List<Integer> getMatchedLoopsize() {
        return prevLoopSize;
    }

    public List<String> getMatchedLoopname() {
        return prevLoopNames;
    }

}
