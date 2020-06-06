package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Calls that stores information for a node that implements a division.
 * @author CPU TOSH
 *
 */
class divisionNode {
    public String div;
    public Integer level;
    public String type;

    /**
     * 
     * @param n_id
     * @param level
     * @param type
     */
    public divisionNode(String n_id, int level, String type) {
        div = n_id;
        this.level = level;
        this.type = type;
    }
}

/**
 * Algorithm that optimizes divisions by identifying the possibility of implementing them as multiplications or logical shifts.  
 * 
 * @author Afonso
 *
 */
public class DivisionOptAlg implements Algorithm {
    List<Graph> hierarchy = new ArrayList<>();
    Graph original;
    boolean found = false;
    String div_id;
    String op;
    int div_count = 1;
    List<divisionNode> div_list = new ArrayList<>();

    public DivisionOptAlg() {
        // TODO Auto-generated constructor stub
    }

    @Override
    /**
     * Initializes the algorithm by identifying all the division in the complete dataflow. 
     * Stores copies of all the levels of the hierarchy.
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        original = graph;
        Graph search;
        Graph next_search = Graphs.clone(graph);
        int level = 0;
        do {
            search = next_search;
            hierarchy.add(Graphs.clone(search));
            for (Node n : search.getEachNode()) {
                if (n.getAttribute("att1").equals("op")) {
                    if (n.getAttribute("label").equals("div") || n.getAttribute("label").equals("/")) {
                        op = (n.getAttribute("label").toString());
                        System.out.println("found a division level " + hierarchy.size());
                        div_id = n.getId();
                        Edge in0 = n.getEnteringEdge(0);
                        Edge in1 = n.getEnteringEdge(1);
                        String type = "var";
                        if (in0.getAttribute("pos").equals("r") && in0.getAttribute("att1").equals("const")) {
                            type = "const";
                        }
                        if (in1.getAttribute("pos").equals("r") && in1.getAttribute("att1").equals("const")) {
                            type = "const";
                        }
                        divisionNode t = new divisionNode(div_id, level, type);
                        div_list.add(t);
                        found = true;

                    }
                }
            }
            if (search.hasAttribute("hierarchy")) {
                List<String> hire = new ArrayList<>();
                hire = search.getAttribute("hierarchy");
                List<Graph> graphlist = search.getAttribute(hire.get(0));
                next_search = Graphs.clone(graphlist.get(graphlist.size() - 1));
                level++;
            }

        } while (search.hasAttribute("hierarchy"));
    }

    @Override
    /**
     * Implements the main body of the algorithm. If the divisor is a variable the source of the variable is detected. 
     * Nodes are inserted to calculate the inverse of the divisor. And the division is changed to a multiplication with the inverse value.
     * If the variable is a constant the algorithm checks if its a power of two. If true its implemented as a ligical shift.
     * If not it is implemented as the multiplication with the inverse value.
     */
    public void compute() {
        // TODO Auto-generated method stub
        if (!found)
            return;
        Graph curr = original;
        for (divisionNode d : div_list) {
            if (d.type.equals("var")) {
                curr = hierarchy.get(d.level);
                Node div = curr.getNode(d.div);
                div.setAttribute("label", "*");
                Edge in0 = div.getEnteringEdge(0);
                Edge in1 = div.getEnteringEdge(1);
                Edge con = null;
                if (in0.getAttribute("pos").equals("r")) {
                    in0.setAttribute("label", in0.getAttribute("label").toString().concat("_inv"));
                    con = in0;
                    System.out.println("new label " + in0.getAttribute("label"));
                }
                if (in1.getAttribute("pos").equals("r")) {
                    in1.setAttribute("label", in1.getAttribute("label").toString().concat("_inv"));
                    System.out.println("new label " + in1.getAttribute("label"));
                    con = in1;
                }

                Node prev = con.getNode0();
                String prev_id = prev.getId();
                if (prev.getEnteringEdge(0).getNode0().getId().equals("Start")) {
                    System.out.println("var created outside curr_loop");
                }
                Integer multiplier = Integer.parseInt(curr.getAttribute("size").toString());
                div_count = div_count * multiplier;

                for (int i = d.level - 1; i >= 0; i--) {
                    Graph prev_graph = hierarchy.get(i);
                    List<String> hire = new ArrayList<>();
                    hire = prev_graph.getAttribute("hierarchy");
                    List<Graph> graphlist = prev_graph.getAttribute(hire.get(0));
                    graphlist.set((graphlist.size() - 1), curr);
                    prev_graph.setAttribute(hire.get(0), graphlist);

                    if (prev_graph.getNode(prev_id) == null) {
                        System.out.println("not cal her go back further");
                        multiplier = Integer.parseInt(prev_graph.getAttribute("size").toString());
                        div_count = div_count * multiplier;
                        curr = Graphs.clone(prev_graph);
                    } else {
                        System.out.println("node here");
                        prev = prev_graph.getNode(prev_id);

                        if (prev.getEnteringEdge(0).getNode0().getAttribute("att1").equals("op")) {
                            System.out.println("calc here");
                            prev.addAttribute("inverted", true);
                            if (div_count < 10) {
                                System.out.println("too little count");
                                return;
                            }
                            String out_id = null;
                            for (Edge e : prev.getEachLeavingEdge()) {
                                if (e.getNode1().getAttribute("att1").equals("hyper")) {
                                    out_id = e.getId();
                                    System.out.println("edge acq");
                                    break;
                                }
                            }
                            Node nxt = prev_graph.getNode(prev_graph.getEdge(out_id).getNode1().getId());
                            Edge out = prev_graph.removeEdge(out_id);

                            prev_graph.addNode("extra_const");
                            prev_graph.getNode("extra_const").addAttribute("label", 1);
                            prev_graph.getNode("extra_const").addAttribute("att1", "const");
                            prev_graph.addEdge("extra_const_conn", "Start", "extra_const", true);

                            prev_graph.addNode("extra_div");
                            prev_graph.getNode("extra_div").addAttribute("label", op);
                            prev_graph.getNode("extra_div").addAttribute("att1", "op");

                            prev_graph.addEdge("extra_const_div", "extra_const", "extra_div", true);
                            prev_graph.getEdge("extra_const_div").addAttribute("label", 1);
                            prev_graph.getEdge("extra_const_div").addAttribute("att1", "const");
                            prev_graph.getEdge("extra_const_div").addAttribute("pos", "l");

                            prev_graph.addEdge("extra_conn_div", prev_id, "extra_div", true);
                            Graphs.copyAttributes(out, prev_graph.getEdge("extra_conn_div"));
                            prev_graph.getEdge("extra_conn_div").addAttribute("pos", "r");
                            prev_graph.getEdge("extra_conn_div").addAttribute("cast", "(float)");

                            prev_graph.addEdge("extra_div_hyper", "extra_div", nxt.getId(), true);
                            Graphs.copyAttributes(out, prev_graph.getEdge("extra_div_hyper"));
                            prev_graph.getEdge("extra_div_hyper").setAttribute("label",
                                    prev_graph.getEdge("extra_div_hyper").getAttribute("label").toString()
                                            .concat("_inv"));
                            prev_graph.getEdge("extra_div_hyper").setAttribute("att3", "float");

                        }
                        curr = Graphs.clone(prev_graph);
                    }
                }
            } else {// in case of constant
                curr = hierarchy.get(d.level);
                Node div = curr.getNode(d.div);
                Edge in0 = div.getEnteringEdge(0);
                Edge in1 = div.getEnteringEdge(1);
                Edge con = null;
                if (in0.getAttribute("pos").equals("r"))
                    con = in0;

                if (in1.getAttribute("pos").equals("r"))
                    con = in1;
                Integer value = Integer.parseInt(con.getAttribute("label").toString());
                if (IsPowerOfTwo(value)) {
                    
                    curr.getNode(div.getId()).setAttribute("label", ">>");
                    curr.getEdge(con.getId()).setAttribute("label", Math.sqrt(value));

                } else {
                    System.out.println("Is not power of two");
                    curr.getNode(div.getId()).setAttribute("label", "*");
                    curr.getEdge(con.getId()).setAttribute("label", (float) 1 / (float) value);
                }
                
                //connects the changed graph to the rest of the hieracrhy.
                for (int i = d.level - 1; i >= 0; i--) {
                    Graph prev_graph = hierarchy.get(i);
                    List<String> hire = new ArrayList<>();
                    hire = prev_graph.getAttribute("hierarchy");
                    List<Graph> graphlist = prev_graph.getAttribute(hire.get(0));
                    graphlist.set((graphlist.size() - 1), curr);
                    prev_graph.setAttribute(hire.get(0), graphlist);
                   
                    curr = Graphs.clone(prev_graph);
                }
            }
        }
        original = hierarchy.get(0);

    }
    
    /**
     * Returns the updated graph
     * @return
     */
    public Graph getGraph() {
        return original;

    }

    /**
     * Checks if value is power of two.
     * @param x Integer to be checked
     * @return true if x is power of two.
     */
    private boolean IsPowerOfTwo(Integer x) {
        return (x != 0) && ((x & (x - 1)) == 0);
    }

}
