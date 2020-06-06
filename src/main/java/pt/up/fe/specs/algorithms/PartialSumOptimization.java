package pt.up.fe.specs.algorithms;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.Graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Algorithm that implements an accumulation chain as a sequence of parallel partial sums.
 * 
 * @author CPU TOSH
 *
 */
public class PartialSumOptimization implements Algorithm {
    boolean up = false;
    Graph graph;
    Graph graph_ori;
    boolean chain_detected = true;
    Graph balance = new DefaultGraph("balance");
    List<Edge> connect = new ArrayList<>();
    Edge finaledge;
    Edge startedge;
    int nOP;
    int nmax = 0;
    int levels;
    int u_id = 0;

    /**
     * 
     */
    public PartialSumOptimization() {
        // TODO Auto-generated constructor stub
    }

    @Override
    /**
     * Initializes the algorithm.
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        this.graph = Graphs.clone(graph);
        this.graph_ori = graph;
        // this.graph = graph;
      
       /*if (graph.hasAttribute("Loopname")) {
            System.out.println("loop :" + graph.getAttribute("Loopname"));
        }*/

        nOP = 0;
    }

    @Override
    /**
     * Main body of the algorithm. First a chain is identified. Then removed and replaced with partial sums. 
     */
    public void compute() {
        // TODO Auto-generated method stub
        // Detect Chain
  
        if (graph.hasAttribute("partial_op")) {
            u_id = graph.getAttribute("partial_op");
        } else
            u_id = 0;
        boolean once = false;
        int n_nodes = 0;
        for (Node n : graph) {
            if (n.getAttribute("att1").equals("op") && !n.getAttribute("label").equals("=")) {
                if (n.getInDegree() < 2) {
                    continue;
                }

                Edge in1 = n.getEnteringEdge(0);
                Edge in2 = n.getEnteringEdge(1);
                Edge res = n.getLeavingEdge(0);

                if (n.getAttribute("label").equals("+") && ((in1.getAttribute("label").equals(res.getAttribute("label"))
                        || in2.getAttribute("label").equals(res.getAttribute("label")))) && !once) {
                    
                    nOP++;
                   
                    finaledge = res;
                    if (res.getNode1().getAttribute("label").equals(n.getAttribute("label"))) {
                        getChaindownward(res.getNode1(), res.getAttribute("label"), n.getAttribute("label"));
                    }
                    
                    
                    if (in1.getAttribute("label").equals(res.getAttribute("label"))) {
                        connect.add(in2);
                        startedge = in1;
                        getChainupward(in1.getNode0(), in1.getAttribute("label"), n.getAttribute("label"), true);

                    } else {
                        connect.add(in1);
                        startedge = in2;
                        getChainupward(in2.getNode0(), in2.getAttribute("label"), n.getAttribute("label"), true);

                    }
                 
                    // getChaindownward(res.getNode1(), res.getAttribute("label"), n.getAttribute("label"));
                    n.addAttribute("chain", true);
                    if (nOP == 1) {
                        //System.out.println("no chain here");
                        connect.clear();
                        nOP = 0;
                        continue;
                    }
                    if (graph.getNode(res.getNode0().getId()) != null)
                        graph.removeNode(res.getNode0().getId());
                    levels = (int) Math.sqrt(nOP);
                    n_nodes = (connect.size() + 1) / 2;
                    System.out.println("Detected chain to separate in partial sums");
                    System.out.println(
                            "chain ops: " + nOP + " || variable: " + res.getAttribute("label") + " || levels: " + levels
                                    + "|| n_node: " + n_nodes);

                    once = true;
                    break;
                }
            }
        }
        
        if (nOP < 2) {
            chain_detected = false;
            System.out.println("No accumulation chain detected");
            return;
        }
        
        Node extraNode = null;
        Edge extraEdge = null;
        boolean extra_clear = false;
        boolean connect_exception = false;

        if (nOP == 2) {
            //special case handle separately
            change_nodes(connect);
            return;
        }


        if ((connect.size() % 2) != 0) {
           
            extraNode = connect.get(connect.size() - 1).getNode0();
            extraEdge = connect.get(connect.size() - 1);
            connect.remove(connect.get(connect.size() - 1));
            connect_exception = true;
            // complete = false;
            // return;
        }
        List<Node> nextlevel = new ArrayList<>();
        List<Node> temp = new ArrayList<>();
        boolean isArray=false;
        if(finaledge.hasAttribute("array")) {
            System.out.println("Is array");
            finaledge.removeAttribute("array");
            isArray=true;
        }
       
        for (int i = 0; i < connect.size(); i = i + 2) {
            graph.addNode("partial_sum_" + "_0" + "_" + i + "_" + u_id);
            graph.getNode("partial_sum_" + "_0" + "_" + i + "_" + u_id).addAttribute("label", "+");
            graph.getNode("partial_sum_" + "_0" + "_" + i + "_" + u_id).addAttribute("att1", "op");

            graph.addEdge(connect.get(i).getId(), connect.get(i).getNode0().getId(),
                    "partial_sum_" + "_0" + "_" + i + "_" + u_id,
                    true);
            Graphs.copyAttributes(connect.get(i), graph.getEdge(connect.get(i).getId()));
            graph.getEdge(connect.get(i).getId()).setAttribute("pos", "l");

            graph.addEdge( connect.get(i + 1).getId(), connect.get(i + 1).getNode0().getId(),
                    "partial_sum_" + "_0" + "_" + i + "_" + u_id,
                    true);
            Graphs.copyAttributes(connect.get(i + 1), graph.getEdge(connect.get(i + 1).getId()));
            graph.getEdge(connect.get(i + 1).getId()).setAttribute("pos", "r");
            nextlevel.add(graph.getNode("partial_sum_" + "_0" + "_" + i + "_" + u_id));
        }

        for (int j = 1; j <= levels; j++) {
            if ((nextlevel.size() % 2) != 0) {
                
                if (extraNode == null) {
                    extraNode = nextlevel.get(nextlevel.size() - 1);
                    nextlevel.remove(nextlevel.size() - 1);
                } else {
                    
                    nextlevel.add(extraNode);
                    extra_clear = true;
                }
                // complete = false;
                // return;
            }
            for (int k = 0; k < nextlevel.size(); k = k + 2) {
               
                graph.addNode("partial_sum_" + j + "_" + k + "_" + u_id);
                graph.getNode("partial_sum_" + j + "_" + k + "_" + u_id).addAttribute("label", "+");
                graph.getNode("partial_sum_" + j + "_" + k + "_" + u_id).addAttribute("att1", "op");

                graph.addEdge("edge1_partial_sum_" + j + "_" + k + "_" + u_id, nextlevel.get(k).getId(),
                        "partial_sum_" + j + "_" + k + "_" + u_id, true);
                Graphs.copyAttributes(finaledge, graph.getEdge("edge1_partial_sum_" + +j + "_" + k + "_" + u_id));
                graph.getEdge("edge1_partial_sum_" + j + "_" + k + "_" + u_id).setAttribute("pos", "l");
                graph.getEdge("edge1_partial_sum_" + j + "_" + k + "_" + u_id).setAttribute("label",
                        "partial_" + j + "_" + (k));
                graph.getEdge("edge1_partial_sum_" + j + "_" + k + "_" + u_id).setAttribute("att2", "loc");


                if (extra_clear && connect_exception && k + 1 == nextlevel.size() - 1) {
                    //System.out.println(extraEdge.getAttribute("label").toString());
                    graph.addEdge(extraEdge.getId(), nextlevel.get(k + 1).getId(),
                            "partial_sum_" + j + "_" + k + "_" + u_id,
                            true);                    
                    Graphs.copyAttributes(extraEdge, graph.getEdge(extraEdge.getId()));
                    graph.getEdge(extraEdge.getId()).setAttribute("pos", "r");
                } else {
                    graph.addEdge("edge2_partial_sum_" + j + "_" + k + "_" + u_id, nextlevel.get(k + 1).getId(),
                            "partial_sum_" + j + "_" + k + "_" + u_id,
                            true);
                    Graphs.copyAttributes(finaledge, graph.getEdge("edge2_partial_sum_" + j + "_" + k + "_" + u_id));
                    graph.getEdge("edge2_partial_sum_" + j + "_" + k + "_" + u_id).setAttribute("pos", "r");
                    graph.getEdge("edge2_partial_sum_" + j + "_" + k + "_" + u_id).setAttribute("label",
                            "partial_" + j + "_" + (k + 1));
                    graph.getEdge("edge2_partial_sum_" + j + "_" + k + "_" + u_id).setAttribute("att2", "loc");

                }
                temp.add(graph.getNode("partial_sum_" + j + "_" + k + "_" + u_id));
            }

            nextlevel.clear();
            if (extra_clear) {
                extra_clear = false;
                extraNode = null;
            }
            nextlevel.addAll(temp);
            temp.clear();

            if (nextlevel.size() == 1 && extraNode == null)
                break;

        }

        graph.addNode("finalop" + "_" + u_id);
        graph.getNode("finalop" + "_" + u_id).addAttribute("label", "+");
        graph.getNode("finalop" + "_" + u_id).addAttribute("att1", "op");

        graph.addEdge("finaledge1_partial_sum_" + "_" + u_id, nextlevel.get(0), graph.getNode("finalop" + "_" + u_id),
                true);
        Graphs.copyAttributes(finaledge, graph.getEdge("finaledge1_partial_sum_" + "_" + u_id));
        graph.getEdge("finaledge1_partial_sum_" + "_" + u_id).setAttribute("pos", "r");
        graph.getEdge("finaledge1_partial_sum_" + "_" + u_id).setAttribute("label", "final_partial_v");
        graph.getEdge("finaledge1_partial_sum_" + "_" + u_id).setAttribute("att2", "loc");
        
        if(isArray)
            finaledge.addAttribute("array", true);
        graph.addEdge("finaledge_sum" + "_" + u_id, graph.getNode("finalop" + "_" + u_id).getId(),
                finaledge.getNode1().getId(), true);
        Graphs.copyAttributes(finaledge, graph.getEdge("finaledge_sum" + "_" + u_id));

        graph.addEdge("finaledge" + "_" + u_id, startedge.getNode0().getId(),
                graph.getNode("finalop" + "_" + u_id).getId(), true);
        Graphs.copyAttributes(finaledge, graph.getEdge("finaledge" + "_" + u_id));
        graph.getEdge("finaledge" + "_" + u_id).addAttribute("pos", "l");

    }

    /**
     * Checks if node is part of current chain. If true calls the parent node to be checked.
     * @param n   Node to check.
     * @param s   name of the variable that creates the chain.
     * @param op  type of operation.
     */
    public void getChaindownward(Node n, String s, String op) {

        if (n.getOutDegree() == 0)
            return;
        if (n.getInDegree() < 2)
            return;

        Edge res = n.getLeavingEdge(0);
        if (!res.hasAttribute("label"))
            return;
        Edge in1 = n.getEnteringEdge(0);
        Edge in2 = n.getEnteringEdge(1);

        if (res.getAttribute("label").equals(s)
                && n.getAttribute("label").equals(op)) {
            n.addAttribute("chain", true);
            nOP++;
            if (in1.getAttribute("label").equals(s))
                connect.add(in2);
            else
                connect.add(in1);

            finaledge = res;
            //System.out.println("label " + (res.getNode1().getAttribute("label").toString()));
            if (res.getNode1().getAttribute("label").equals(op)) {
                getChaindownward(res.getNode1(), s, res.getNode1().getAttribute("label"));
            }

        }

        graph.removeNode(res.getNode0().getId());
        return;
    }

    
    /**
     * Checks if node is part of current chain. If true calls the child node to be checked.
     * @param n Node to check.
     * @param s name of the variable that creates the chain.
     * @param op type of operation.
     * @param cont
     */
    public void getChainupward(Node n, String s, String op, boolean cont) {
        up = true;
        if (n.getOutDegree() == 0)
            return;
        if (n.getInDegree() < 2)
            return;
        if (!n.getAttribute("att1").equals("op") && op.equals("+"))
            return;

        Edge res = n.getLeavingEdge(0);
        if (!res.hasAttribute("label"))
            return;
        Edge in1 = n.getEnteringEdge(0);
        Edge in2 = n.getEnteringEdge(1);

        if (in1.getAttribute("label").equals(s)) {
            connect.add(in2);
            n.addAttribute("chain", true);
            nOP++;
            startedge = in1;

            getChainupward(in1.getNode0(), s, in1.getNode1().getAttribute("label"), true);

        } else {
            connect.add(in1);
            n.addAttribute("chain", true);
            nOP++;
            startedge = in2;
            getChainupward(in2.getNode0(), s, in2.getNode1().getAttribute("label"), true);
        }
        if (graph.getNode(n.getId()) != null)
            graph.removeNode(n.getId());
        return;
    }

    /**
     * Separate method that implements the partial sums in case of chain being only two sums.
     * @param edges List of the two edges that create the chain.
     */
    public void change_nodes(List<Edge> edges) {

        graph.addNode("partial_sum_" + "_0" + "_" + 0 + "_" + u_id);
        graph.getNode("partial_sum_" + "_0" + "_" + 0 + "_" + u_id).addAttribute("label", "+");
        graph.getNode("partial_sum_" + "_0" + "_" + 0 + "_" + u_id).addAttribute("att1", "op");

        graph.addEdge("edge1_partial_sum_" + 0 + "_" + u_id, edges.get(0).getNode0().getId(),
                "partial_sum_" + "_0" + "_" + 0 + "_" + u_id,
                true);
        Graphs.copyAttributes(connect.get(0), graph.getEdge("edge1_partial_sum_" + 0 + "_" + u_id));
        graph.getEdge("edge1_partial_sum_" + 0 + "_" + u_id).setAttribute("pos", "l");

        graph.addEdge("edge2_partial_sum_" + 0 + "_" + u_id, edges.get(1).getNode0().getId(),
                "partial_sum_" + "_0" + "_" + 0 + "_" + u_id,
                true);
        Graphs.copyAttributes(connect.get(1), graph.getEdge("edge2_partial_sum_" + 0 + "_" + u_id));
        graph.getEdge("edge2_partial_sum_" + 0 + "_" + u_id).setAttribute("pos", "r");

        graph.addNode("finalop" + "_" + u_id);
        graph.getNode("finalop" + "_" + u_id).addAttribute("label", "+");
        graph.getNode("finalop" + "_" + u_id).addAttribute("att1", "op");

        graph.addEdge("finaledge1_partial_sum_" + "_" + u_id,
                "partial_sum_" + "_0" + "_" + 0 + "_" + u_id, "finalop" + "_" + u_id,
                true);
        Graphs.copyAttributes(finaledge, graph.getEdge("finaledge1_partial_sum_" + "_" + u_id));
        graph.getEdge("finaledge1_partial_sum_" + "_" + u_id).setAttribute("pos", "r");
        graph.getEdge("finaledge1_partial_sum_" + "_" + u_id).setAttribute("label", "final_partial_v");
        graph.getEdge("finaledge1_partial_sum_" + "_" + u_id).setAttribute("att2", "loc");
        if( graph.getEdge("finaledge1_partial_sum_" + "_" + u_id).hasAttribute("array"))
            graph.getEdge("finaledge1_partial_sum_" + "_" + u_id).removeAttribute("array");
        graph.addEdge("finaledge_sum" + "_" + u_id, graph.getNode("finalop" + "_" + u_id).getId(),
                finaledge.getNode1().getId(), true);
        Graphs.copyAttributes(finaledge, graph.getEdge("finaledge_sum" + "_" + u_id));

        graph.addEdge("finaledge" + "_" + u_id, startedge.getNode0().getId(),
                graph.getNode("finalop" + "_" + u_id).getId(), true);
        Graphs.copyAttributes(finaledge, graph.getEdge("finaledge" + "_" + u_id));
        graph.getEdge("finaledge" + "_" + u_id).addAttribute("pos", "l");

        return;
    }

    /**
     * Return the modified graph in case of success. If no success original graph is returned.
     * @return
     */
    public Graph getGraph() {

        if (chain_detected) {
            graph.addAttribute("partial_op", u_id + 1);
            return graph;

        } else
            return graph_ori;
    }
    
    /**
     * @return true if algorithm detected a chain.
     */
    public boolean chainDetected() {
        return chain_detected;
    }

}
