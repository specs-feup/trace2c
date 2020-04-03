package pt.up.fe.specs.graphoptimizations;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.Graphs;
import pt.up.fe.specs.LevelingAlgorithmExt;

import java.util.ArrayList;
import java.util.List;


/**
 * Algorithm that given a list of separates sequences identifies the unique and common nodes, and separates the accordingly.
 * @author CPU TOSH
 *
 */
public class SeparatePruned implements Algorithm {
    List<Graph> separategraphlist;
    List<List<List<Node>>> listlevelgraph = new ArrayList<>();
    Graph commongraph = new DefaultGraph("commongraph");

    /**
     * 
     * @param basicgraphlist list of graphs.
     */
    public SeparatePruned(List<Graph> basicgraphlist) {
        this.separategraphlist = new ArrayList<>(basicgraphlist);
    }

    @Override
    /**
     * Initializes the Algorithm
     * 
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        Algorithm level;

        for (Graph temg : separategraphlist) {
            level = new LevelingAlgorithmExt();
            level.init(temg);
            level.compute();
            listlevelgraph.add((((LevelingAlgorithmExt) level).getLevelGraph()));
        }

    }

    @Override
    /**
     * Implements the main method of the algorithm.
     */
    public void compute() {
        // TODO Auto-generated method stub
        // From one of graphs start algorithm

        for (List<Node> list : listlevelgraph.get(0)) {

            for (Node n : list) {
                int common = 0;
                for (List<List<Node>> levelgraph : listlevelgraph) {
                    for (Node tmpn : levelgraph.get(listlevelgraph.get(0).indexOf(list))) {
                        if (n.getId().equals(tmpn.getId()) && !n.getId().equals("Start") && !n.getId().equals("End"))
                            common++;
                    }
                }
                if (common == listlevelgraph.size())
                    for (Graph temg : separategraphlist)
                        temg.getNode(n.getId()).addAttribute("occ", "common");
            }
        }

        for (Node n : separategraphlist.get(0)) {
            if (n.hasAttribute("occ")) {
                commongraph.addNode(n.getId());
                for (String a : n.getEachAttributeKey())
                    commongraph.getNode(n.getId()).addAttribute(a, n.getAttribute(a).toString());

            }

        }

        for (Edge e : separategraphlist.get(0).getEachEdge()) {
            Node n0 = e.getNode0();
            Node n1 = e.getNode1();
            Node temp0 = commongraph.getNode(n0.getId());
            Node temp1 = commongraph.getNode(n1.getId());
            if (temp0 != null && temp1 != null) {
                // System.out.println(temp0.getId());
                commongraph.addEdge(e.getId().toString(), commongraph.getNode(temp0.getId()).getId().toString(),
                        commongraph.getNode(temp1.getId()).getId().toString(), true);
                for (String a : e.getEachAttributeKey())
                    commongraph.getEdge(e.getId()).addAttribute(a, e.getAttribute(a).toString());
            }

        }

        List<String> removelist = new ArrayList<>();

        for (Node n : separategraphlist.get(0)) {
            boolean cut = true;
            if (!n.hasAttribute("occ"))
                cut = false;
            else {
                for (Edge e : n.getEachEnteringEdge()) {
                    if (!e.getNode0().hasAttribute("occ") && !e.getNode0().getId().equals("Start")) {

                        cut = false;
                    }
                }

                for (Edge e : n.getEachLeavingEdge()) {
                    if (!e.getNode1().hasAttribute("occ")) {
                        cut = false;

                    }
                }

            }
            if (cut) {
                removelist.add(n.getId());
            }
        }

        for (String n : removelist) {
            for (Graph temg : separategraphlist) {
                temg.removeNode(n);
            }

        }

        for (String att : separategraphlist.get(0).getEachAttributeKey()) {
            commongraph.addAttribute(att, separategraphlist.get(0).getAttribute(att).toString());
        }

        commongraph.addNode("Start");
        commongraph.getNode("Start").addAttribute("label", "start");
        commongraph.getNode("Start").addAttribute("att1", "nop");

        for (Node n : commongraph.getEachNode()) {

            if (n.getInDegree() == 0 && !n.equals(commongraph.getNode("Start")))
                commongraph.addEdge("begin to:" + n.getId(), commongraph.getNode("Start"), n, true);

        }

        commongraph.addNode("HyperNode");
        commongraph.getNode("HyperNode").addAttribute("label", "hyper1");
        commongraph.getNode("HyperNode").addAttribute("att1", "hyper");
        // commongraph.getNode("HyperNode").addAttribute("hyper_type", "separate");
        commongraph.getNode("HyperNode").addAttribute("name", "hyper1");
        commongraph.addAttribute("HyperNode", separategraphlist);

        List<String> temp = new ArrayList<>();

        if (!commongraph.hasAttribute("hierarchy")) {
            commongraph.addAttribute("hierarchy", new ArrayList<String>());
        }

        temp = commongraph.getAttribute("hierarchy");
        temp.add("HyperNode");
        commongraph.setAttribute("hierarchy", temp);

        for (Node n : commongraph.getEachNode()) {

            if (separategraphlist.get(0).getNode(n.getId()) != null && !n.getId().toString().equals("Start")) {
                commongraph.addEdge("end from:" + n.getId(), n, commongraph.getNode("HyperNode"), true);

                Graphs.copyAttributes(separategraphlist.get(0).getNode(n.getId()).getLeavingEdge(0),
                        commongraph.getEdge("end from:" + n.getId()));

            }

        }

        commongraph.addNode("End");
        commongraph.getNode("End").addAttribute("label", "end");
        commongraph.getNode("End").addAttribute("att1", "nop");

        for (Node n : commongraph.getEachNode()) {
            if (n.getOutDegree() == 0 && !n.equals(commongraph.getNode("End")))
                commongraph.addEdge("ends from:" + n.getId(), n, commongraph.getNode("End"), true);

        }
    }

    /**
     * Returns a list of graphs with unique nodes.
     * @return
     */
    public List<Graph> getListGraph() {
        return separategraphlist;
    }

    /**
     * Returns the graph with common nodes.
     * @return
     */
    public Graph getCommonGraph() {
        return commongraph;
    }

}
