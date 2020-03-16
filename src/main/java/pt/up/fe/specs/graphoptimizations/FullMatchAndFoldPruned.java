package pt.up.fe.specs.graphoptimizations;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.Graphs;

import java.util.ArrayList;
import java.util.List;

public class FullMatchAndFoldPruned implements Algorithm {
    public Graph structgraph;
    List<Graph> graphlist = new ArrayList<>();
    List<List<Node>> nodestomatch = new ArrayList<>();
    List<List<Node>> templist = new ArrayList<>();
    int N;
    String loopname = new String("loop_match_fold");

    public FullMatchAndFoldPruned(List<Graph> graphlist) {
        // TODO Auto-generated constructor stub
        structgraph = new DefaultGraph("structgraph");
        structgraph = Graphs.clone(graphlist.get(0));
        this.graphlist = graphlist;
        N = graphlist.size();

        // get All source Nodes
        for (Graph temg : graphlist) {
            Node end = temg.getNode("End");
            List<Node> list = new ArrayList<>();
            list.add(end.getEnteringEdge(0).getNode0());
            nodestomatch.add(list);
            templist.add(new ArrayList<>());
        }

    }

    @Override
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        graph = structgraph;

        for (int i = 0; i < nodestomatch.size() - 1; i++) {
            if (!(compareNode(nodestomatch.get(i).get(0), nodestomatch.get(i + 1).get(0))))
                System.out.println("Error");
            else if (i == 0) {
                structgraph.addAttribute("initv",
                        getIndexVec(structgraph.getNode(nodestomatch.get(0).get(0).getId().toString())
                                .getAttribute("label").toString()));
                structgraph.addAttribute("+incr",
                        structgraph.getNode(nodestomatch.get(0).get(0).getId().toString()).getAttribute("+incr")
                                .toString());

            }

        }

        for (List<Node> list : nodestomatch) {
            list.add(list.get(0).getEnteringEdge(0).getNode0());
            list.remove(0);
        }

    }

    @Override
    public void compute() {
        // TODO Auto-generated method stub
        // get Local
        boolean fullmatch = true;
        boolean localmatch = true;
        Node n;
        List<Node> tempadd = new ArrayList<>();

        //

        while (!nodestomatch.get(0).isEmpty()) {
            for (int j = 0; j < nodestomatch.get(0).size(); j++) {
                tempadd = new ArrayList<>();
                tempadd.add(nodestomatch.get(0).get(j));
                for (int i = 0; i < nodestomatch.size() - 1; i++) {
                    tempadd.add(nodestomatch.get(i + 1).get(j));
                    n = nodestomatch.get(i).get(j);
                    if (n.getAttribute("att1").toString().equals("op")) {
                        localmatch &= match(n, nodestomatch.get(i + 1).get(j));
                        if (!localmatch) {
                            System.out.println(n.getId());
                        }

                    }
                    structgraph.getNode(nodestomatch.get(0).get(j).getId().toString()).addAttribute("mirror",
                            new ArrayList<Node>());
                    structgraph.getNode(nodestomatch.get(0).get(j).getId().toString()).setAttribute("mirror", tempadd);
                }
            }

            for (int k = 0; k < nodestomatch.size(); k++) {
                nodestomatch.get(k).clear();
                for (Node nn : templist.get(k)) {
                    nodestomatch.get(k).add(nn);
                }
                templist.get(k).clear();
            }

            if (!localmatch)
                break;

        }

        if (!(localmatch && fullmatch)) {
            System.out.println("problem");
            return; // not full match after maybe different handling
        }

        for (String att : graphlist.get(0).getEachAttributeKey()) {
            structgraph.addAttribute(att, graphlist.get(0).getAttribute(att).toString());
        }

        structgraph.addAttribute("Loopname", loopname);
        structgraph.addAttribute("Pipeline", true);
        structgraph.addAttribute("size", nodestomatch.size());
        structgraph.addAttribute("plus_fold", 0);
        structgraph.addAttribute("mult_fold", 1);
        if (structgraph.hasAttribute("hyper_type")) {
            structgraph.setAttribute("hyper_type", "parallel");
        } else
            structgraph.addAttribute("hyper_type", "parallel");
        for (Node n0 : structgraph.getEachNode()) {
            if (n0.getInDegree() == 0 && !n0.equals(structgraph.getNode("Start"))
                    && !n0.equals(structgraph.getNode("End")))
                structgraph.addEdge("begin to:" + n0.getId(), structgraph.getNode("Start"), n0, true);
        }
        graphlist.add(structgraph);
       // structgraph.display();

    }

    private boolean match(Node n0, Node n1) {
        Edge res0 = n0.getLeavingEdge(0);
        Edge res1 = n1.getLeavingEdge(0);

        if (n0.getAttribute("label").toString().equals("=")) {
            if (n0.getEnteringEdge(0).getNode0().getId().toString().equals("Start"))
                return true;
            Edge input00 = n0.getEnteringEdge(0);
            Edge input10 = n1.getEnteringEdge(0);

            if (compareEdge(res0, res1) && compareEdge(input00, input10)) {
                if (!templist.get(graphlist.indexOf(input00.getNode1().getGraph()))
                        .contains(input00.getNode0())) {

                    templist.get(graphlist.indexOf(input00.getNode1().getGraph()))
                            .add(input00.getNode0());
                }

                templist.get(graphlist.indexOf(input10.getNode1().getGraph())).add(input10.getNode0());
                return true;
            }

        }

        if (n0.getAttribute("label").toString().equals(n1.getAttribute("label").toString())) {

            if (n0.getInDegree() == 0)
                if (n1.getInDegree() == 0)
                    return true;
                else
                    return false;
            if (n0.getEnteringEdge(0).getNode0().getId().toString().equals("Start"))
                return true;

            if (compareEdge(res0, res1)) {

                Edge input00 = n0.getEnteringEdge(0);
                Edge input01 = n0.getEnteringEdge(1);
                Edge input10 = n1.getEnteringEdge(0);
                Edge input11 = n1.getEnteringEdge(1);

                if (input00.getAttribute("pos").equals(input10.getAttribute("pos"))) {
                    if (compareEdge(input00, input10) && compareEdge(input01, input11)) {
                        if (!templist.get(graphlist.indexOf(input00.getNode1().getGraph()))
                                .contains(input00.getNode0())) {

                            templist.get(graphlist.indexOf(input00.getNode1().getGraph()))
                                    .add(input00.getNode0());
                            templist.get(graphlist.indexOf(input00.getNode1().getGraph()))
                                    .add(input01.getNode0());

                        }

                        templist.get(graphlist.indexOf(input11.getNode1().getGraph())).add(input10.getNode0());
                        templist.get(graphlist.indexOf(input11.getNode1().getGraph())).add(input11.getNode0());

                        return true;
                    }
                } else if (compareEdge(input00, input11) && compareEdge(input01, input10)) {
                    if (!templist.get(graphlist.indexOf(input00.getNode1().getGraph()))
                            .contains(input00.getNode0())) {
                        templist.get(graphlist.indexOf(input00.getNode1().getGraph()))
                                .add(input00.getNode0());
                        templist.get(graphlist.indexOf(input00.getNode1().getGraph()))
                                .add(input01.getNode0());

                    }

                    templist.get(graphlist.indexOf(input11.getNode1().getGraph())).add(input11.getNode0());
                    templist.get(graphlist.indexOf(input11.getNode1().getGraph())).add(input10.getNode0());
                    return true;
                }

            }
        }
        return false;
    }

    private boolean compareEdge(Edge e0, Edge e1) {
        String label0 = e0.getAttribute("label");
        String label1 = e1.getAttribute("label");
        String type0 = e0.getAttribute("att1");
        String type1 = e1.getAttribute("att1");
        String temptemp = new String();
        List<Integer> indexes0 = new ArrayList<>();
        List<Integer> indexes1 = new ArrayList<>();
        boolean array = false;

        if (!type0.equals(type1))
            return false;

        if (type0.equals("const")) {
            if (label0.equals(label1)) {
                if (e0.hasAttribute("reproducable")) {
                    if (!e1.hasAttribute("occ")) {
                        e1.addAttribute("occ", "repro");
                    }
                    e1.addAttribute("reproducable", e0.getAttribute("reproducable").toString());
                } else {
                    if (!e0.hasAttribute("occ")) {
                        e0.addAttribute("occ", "repro");
                        e1.addAttribute("occ", "repro");
                    }

                    for (String a : e0.getEachAttributeKey())
                        structgraph.getEdge(e0.getId()).addAttribute(a, e0.getAttribute(a).toString());

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
            // handle vectors and seld insert temps
            if (label0.contains("_i") && label1.contains("_i")) {
                var0 = new String(label0.substring(0, label0.indexOf("_i")));
                var1 = new String(label1.substring(0, label1.indexOf("_i")));
            } else if (label0.contains("[") && label1.contains("[")) {
                array = true;
                var0 = new String(label0.substring(0, label0.indexOf("[")));
                var1 = new String(label1.substring(0, label1.indexOf("[")));
                index0 = getIndexVec(label0);
                index1 = getIndexVec(label1);

                temptemp = e0.getAttribute("Index").toString();
                dim = getIndexes(temptemp, indexes0);
                temptemp = e1.getAttribute("Index").toString();
                getIndexes(temptemp, indexes1);

            } else {
                var0 = label0;
                var1 = label1;
            }

            if (var0.equals(var1)) {
                if (array) {
                    if (e0.hasAttribute("reproducable")) {
                        boolean tempbool = true;

                        List<Loopinfo> temp = structgraph.getEdge(e0.getAttribute("reproducable")).getAttribute("loop");
                        for (int i = 0; i < temp.get(temp.size() - 1).dim; i++) {
                            if ((indexes1.get(i) - indexes0.get(i)) != temp.get(temp.size() - 1).increments.get(i))
                                return false;
                        }
                        if (!e1.hasAttribute("occ")) {
                            e1.addAttribute("occ", "repro");
                        }
                        e1.addAttribute("reproducable", e0.getAttribute("reproducable").toString());

                    } else {
                        if (!e0.hasAttribute("occ")) {
                            e0.addAttribute("occ", "repro");
                            e1.addAttribute("occ", "repro");
                        }

                        for (String a : e0.getEachAttributeKey())
                            structgraph.getEdge(e0.getId()).addAttribute(a, e0.getAttribute(a).toString());

                        e0.addAttribute("reproducable", e0.getId());
                        e1.addAttribute("reproducable", e0.getId());

                        List<Integer> increments = new ArrayList();
                        for (int j = 0; j < dim; j++)
                            increments.add(indexes1.get(j) - indexes0.get(j));
                        Loopinfo loopinfo = new Loopinfo(loopname, new ArrayList<>(increments), dim);
                        if (!structgraph.getEdge(e0.getId()).hasAttribute("loop"))
                            structgraph.getEdge(e0.getId()).addAttribute("loop", new ArrayList<Loopinfo>());
                        List<Loopinfo> list = structgraph.getEdge(e0.getId()).getAttribute("loop");
                        list.add(loopinfo);
                        structgraph.getEdge(e0.getId()).setAttribute("loop", list);

                    }

                } else {
                    if (e0.hasAttribute("reproducable")) {
                        if (!e1.hasAttribute("occ")) {
                            e1.addAttribute("occ", "repro");

                        }
                        e1.addAttribute("reproducable", e0.getAttribute("reproducable").toString());
                    } else {
                        if (!e0.hasAttribute("occ")) {
                            e0.addAttribute("occ", "repro");
                            e1.addAttribute("occ", "repro");

                        }

                        for (String a : e0.getEachAttributeKey())
                            structgraph.getEdge(e0.getId()).addAttribute(a, e0.getAttribute(a).toString());

                        e0.addAttribute("reproducable", e0.getId());
                        e1.addAttribute("reproducable", e0.getId());
                    }

                }
                return true;
            }

        }
        return false;
    }

    private boolean compareNode(Node n0, Node n1) {
        String label0 = n0.getAttribute("label");
        String label1 = n1.getAttribute("label");
        String type0 = n0.getAttribute("att1");
        String type1 = n1.getAttribute("att1");
        boolean array = false;
        if (!type0.equals(type1))
            return false;

        if (type0.equals("op") || type0.equals("const")) {
            if (label0.equals(label1)) {
                if (n0.hasAttribute("reproducable")) {
                    if (!n1.hasAttribute("occ")) {
                        n1.addAttribute("occ", "repro");
                    }
                    n1.addAttribute("reproducable", n0.getAttribute("reproducable").toString());

                } else {
                    if (!n0.hasAttribute("occ")) {
                        n0.addAttribute("occ", "repro");
                        n1.addAttribute("occ", "repro");
                    }

                    n0.addAttribute("reproducable", n0.getId());
                    n1.addAttribute("reproducable", n0.getId());

                    for (String a : n0.getEachAttributeKey())
                        structgraph.getNode(n0.getId()).addAttribute(a, n0.getAttribute(a).toString());

                    Node out = n0.getLeavingEdge(0).getNode1();
                    if (!out.getId().toString().equals("End"))
                        structgraph.addEdge(n0.getId(), n0.getId(), out.getId(), true);
                }
                return true;
            }

        } else if (type0.equals("var")) {
            int index0 = 0;
            int index1 = 0;
            String var0 = new String();
            String var1 = new String();
            // handle vectors and seld insert temps
            if (label0.contains("_i") && label1.contains("_i")) {
                var0 = new String(label0.substring(0, label0.indexOf("_i")));
                var1 = new String(label1.substring(0, label1.indexOf("_i")));
            } else if (label0.contains("[") && label1.contains("[")) {
                array = true;
                var0 = new String(label0.substring(0, label0.indexOf("[")));
                var1 = new String(label1.substring(0, label1.indexOf("[")));
                index0 = getIndexVec(label0);
                index1 = getIndexVec(label1);
            } else {
                var0 = label0;
                var1 = label1;
            }

            if (var0.equals(var1)) {
                if (array) {
                    if (n0.hasAttribute("reproducable")) {
                        int incr = structgraph.getNode(n0.getAttribute("reproducable")).getAttribute("+incr");
                        if ((index1 - index0) == incr) {
                            if (!n1.hasAttribute("occ")) {
                                n1.addAttribute("occ", "repro");

                            }
                            n1.addAttribute("reproducable", n0.getAttribute("reproducable").toString());
                        } else
                            return false;
                    } else {
                        if (!n0.hasAttribute("occ")) {
                            n0.addAttribute("occ", "repro");
                            n1.addAttribute("occ", "repro");
                        }
                        n0.addAttribute("reproducable", n0.getId());
                        n1.addAttribute("reproducable", n0.getId());

                        for (String a : n0.getEachAttributeKey())
                            structgraph.getNode(n0.getId()).addAttribute(a, n0.getAttribute(a).toString());

                        structgraph.getNode(n0.getId()).addAttribute("+incr", index1 - index0);

                        Node out = n0.getLeavingEdge(0).getNode1();

                        if (!out.getId().toString().equals("End"))
                            structgraph.addEdge(n0.getId(), n0.getId(), out.getId(), true);
                    }

                } else {
                    if (n0.hasAttribute("reproducable")) {
                        if (!n1.hasAttribute("occ")) {
                            n1.addAttribute("occ", "repro");

                        }
                        n1.addAttribute("reproducable", n0.getAttribute("reproducable").toString());
                    } else {
                        if (!n0.hasAttribute("occ")) {
                            n0.addAttribute("occ", "repro");
                            n1.addAttribute("occ", "repro");

                        }
                        n0.addAttribute("reproducable", n0.getId());
                        n1.addAttribute("reproducable", n0.getId());

                        for (String a : n0.getEachAttributeKey())
                            structgraph.getNode(n0.getId()).addAttribute(a, n0.getAttribute(a).toString());
                        Node out = n0.getLeavingEdge(0).getNode1();
                        if (!out.getId().toString().equals("End"))
                            structgraph.addEdge(n0.getId(), n0.getId(), out.getId(), true);
                    }
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

    private static void add_start_end(Graph g) {

        g.addNode("Start");
        g.getNode("Start").addAttribute("label", "start");
        g.getNode("Start").addAttribute("att1", "nop");
        g.addNode("End");
        g.getNode("End").addAttribute("label", "end");
        g.getNode("End").addAttribute("att1", "nop");
        for (Node n : g.getEachNode()) {
            if (n.getInDegree() == 0 && !n.equals(g.getNode("Start")) && !n.equals(g.getNode("End")))
                g.addEdge("begin to:" + n.getId(), g.getNode("Start"), n, true);
            else if (n.getOutDegree() == 0 && !n.equals(g.getNode("End")) && !n.equals(g.getNode("Start")))
                g.addEdge("end from:" + n.getId(), n, g.getNode("End"), true);

        }
    }

    public String getLoopname() {
        return loopname;
    }

}
