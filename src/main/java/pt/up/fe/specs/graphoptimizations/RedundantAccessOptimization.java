package pt.up.fe.specs.graphoptimizations;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author CPU TOSH
 *
 */

public class RedundantAccessOptimization implements Algorithm {
    String loopname;
    String hypernode;
    Graph result;
    List<Graph> hierarchy = new ArrayList<>();
    List<String> curraccess = new ArrayList<>();
    List<String> edgeID = new ArrayList<>();
    List<String> nextaccess = new ArrayList<>();
    List<String> buffer = new ArrayList<>();
    List<List<String>> curraccess_list = new ArrayList<>();
    List<List<String>> edgeID_list = new ArrayList<>();
    List<List<String>> nextaccess_list = new ArrayList<>();
    List<List<String>> buffer_list = new ArrayList<>();
    List<Integer> shifts = new ArrayList<>();
    List<Integer> naccess_list = new ArrayList<>();
    List<Integer> redundant = new ArrayList<>();
    List<List<Integer>> redundant_list = new ArrayList<>();
    boolean shift_not_possible = false;
    boolean no_shift_buffer = true;

    public RedundantAccessOptimization(String loopname, String hyper, List<Graph> graphlist) {
        // TODO Auto-generated constructor stub
        this.loopname = loopname;
        hierarchy.addAll(graphlist);
        hypernode = hyper;
    }

    @Override
    /**
     * Initializes the algorithm by identifying the redundant memory accesses.
     */
    public void init(Graph graph) {
        // TODO Auto-generated method stub
        int naccess = 0;
        result = graph;
        for (Edge e : graph.getEachEdge()) {
            int shift_perm = 0;
            if (e.hasAttribute("array") && e.hasAttribute("loop")
                    && e.getNode0().getEnteringEdge(0).getNode0().getId().toString().equals("Start")) {

                List<Loopinfo> looplist = new ArrayList<>();
                List<Integer> indexes = new ArrayList<>();
                int dim = getIndexes(e.getAttribute("label"), indexes);
                String oldlabel = new String(e.getAttribute("label").toString());
                /*
                if (curraccess.contains(oldlabel)) {
                    System.out.println("Cannot handle 2 unique access too equal index " + oldlabel);
                    shift_not_possible = true;
                    return;
                }*/

                String label = new String(oldlabel);

                looplist = e.getAttribute("loop");
                for (Loopinfo loop : looplist) {
                    if (loopname.equals(loop.name)) {

                        for (int j = 0; j < dim; j++) {
                            if (loop.increments.get(j) != 0) {

                                Integer shift = (loop.increments.get(j) + indexes.get(j));
                                shift_perm = loop.increments.get(j);
                                label = replaceIndex(label, j + 1, shift, indexes.get(j).toString());
                                // label = label.replace(indexes.get(j).toString(), shift.toString());
                                indexes.set(j, shift);
                            }

                        }

                    }
                }
                int list_ind = 0;
                if (!shifts.contains(shift_perm)) {
                    shifts.add(shift_perm);
                    curraccess_list.add(new ArrayList<>());
                    edgeID_list.add(new ArrayList<>());
                    nextaccess_list.add(new ArrayList<>());
                    buffer_list.add(new ArrayList<>());
                    redundant_list.add(new ArrayList<>());
                    naccess_list.add(0);
                }
                list_ind = shifts.indexOf(shift_perm);
                curraccess_list.get(list_ind).add(new String(oldlabel));
                String trim = new String(oldlabel);
                trim = trim.replace("[", "_");
                trim = trim.replace("]", "");
                trim = trim.concat("_b" + list_ind);
                buffer_list.get(list_ind).add(trim);
                nextaccess_list.get(list_ind).add(label);
                edgeID_list.get(list_ind).add(e.getId());
                int index = nextaccess_list.get(list_ind).indexOf(oldlabel);
                System.out.println(oldlabel + "--------->" + label);

                if (index > (-1) && index != naccess_list.get(list_ind) && (1 + index) != naccess_list.get(list_ind)) {
                    // System.out.println(oldlabel + " index " + index + " curr " + naccess_list.get(list_ind));
                    Collections.swap(curraccess_list.get(list_ind), naccess_list.get(list_ind), index + 1);
                    Collections.swap(buffer_list.get(list_ind), naccess_list.get(list_ind), index + 1);
                    Collections.swap(nextaccess_list.get(list_ind), naccess_list.get(list_ind), index + 1);
                    Collections.swap(edgeID_list.get(list_ind), naccess_list.get(list_ind), index + 1);
                }

                index = curraccess_list.get(list_ind).indexOf(label);

                if (index > (-1) && index != naccess) {
                    // System.out.println(oldlabel + " index " + index + " curr " + naccess_list.get(list_ind));
                    String aux = new String();
                    aux = curraccess_list.get(list_ind).remove(curraccess_list.get(list_ind).size() - 1);
                    curraccess_list.get(list_ind).add(index, aux);

                    aux = new String();
                    aux = nextaccess_list.get(list_ind).remove(nextaccess_list.get(list_ind).size() - 1);
                    nextaccess_list.get(list_ind).add(index, aux);

                    aux = new String();
                    aux = buffer_list.get(list_ind).remove(buffer_list.get(list_ind).size() - 1);
                    buffer_list.get(list_ind).add(index, aux);

                    aux = new String();
                    aux = edgeID_list.get(list_ind).remove(edgeID_list.get(list_ind).size() - 1);
                    edgeID_list.get(list_ind).add(index, aux);
                }

                naccess_list.set(list_ind, naccess_list.get(list_ind) + 1);

            }
        }

        for (int k = 0; k < shifts.size(); k++) {
            for (String a : curraccess_list.get(k))
                if (curraccess_list.get(k).contains(nextaccess_list.get(k).get(curraccess_list.get(k).indexOf(a)))) {
                    redundant_list.get(k).add(curraccess_list.get(k).indexOf(a));
                    if (!a.equals(nextaccess_list.get(k).get(curraccess_list.get(k).indexOf(a)))) {
                        no_shift_buffer = false;
                    }
                }

        }

        for (int k = 0; k < shifts.size(); k++) {
            // System.out.println(k + " shift list " + shifts.get(k));
            int size_add = 0;
            size_add = curraccess.size();
            if (size_add != 0) {
                for (int p = 0; p < redundant_list.get(k).size(); p++)
                    redundant_list.get(k).set(p, redundant_list.get(k).get(p) + size_add);
            }
            curraccess.addAll(curraccess_list.get(k));
            nextaccess.addAll(nextaccess_list.get(k));
            buffer.addAll(buffer_list.get(k));
            edgeID.addAll(edgeID_list.get(k));
            redundant.addAll(redundant_list.get(k));
        }

        if ((float) redundant.size() / curraccess.size() > 0.5) {
            System.out.println((float) redundant.size() / curraccess.size() + " Redundant acesses");
        }
    }

    @Override
    /**
     * Implements the main method of the algorithm. Restructures the graph to add the buffers.
     */
    public void compute() {
        // TODO Auto-generated method stub
        // add initial Nodes
        if (shift_not_possible)
            return;
        // System.out.println("completed shift buffer");
        int level = 0;
        for (int i : redundant) {

            // connect to start
            if (curraccess.get(i).equals(nextaccess.get(i)))
                level = 0;
            else
                level = hierarchy.size() - 1;

            /* if (hierarchy.get(level).getNode("redun_" + curraccess.get(i)) != null)
                continue;*/
            String input_id;
            Node input_node = hierarchy.get(level).getNode(result.getEdge(edgeID.get(i)).getNode0().getId());
            // New addition test on old Sobbel
            if (input_node != null) {
                System.out.println("Existing input: " + input_node.getAttribute("label").toString());
                input_id = input_node.getId();
                // Removes prior connection to hyper node.
                // Optional can be removed if causes problems
                for (Edge temp_e : hierarchy.get(level).getNode(input_id).getEachLeavingEdge()) {
                    if (!temp_e.getNode1().getAttribute("att1").equals("op"))
                        hierarchy.get(level).removeEdge(temp_e);
                }
            } else {
                input_id = "redun_" + i + "_" + curraccess.get(i);
                hierarchy.get(level).addNode(input_id);
                Graphs.copyAttributes(result.getEdge(edgeID.get(i)),
                        hierarchy.get(level).getNode(input_id));
                hierarchy.get(level).addEdge("redun_start_" + i + "_" + curraccess.get(i), "Start",
                        input_id,
                        true);
            }
            // Equal
            hierarchy.get(level).addNode("redun_eq" + i + "_" + curraccess.get(i));
            hierarchy.get(level).getNode("redun_eq" + i + "_" + curraccess.get(i)).addAttribute("label", "=");
            hierarchy.get(level).getNode("redun_eq" + i + "_" + curraccess.get(i)).addAttribute("att1", "op");
            hierarchy.get(level).getNode("redun_eq" + i + "_" + curraccess.get(i)).addAttribute("ord", 0);

            // Connect to old values
            hierarchy.get(level).addEdge("edge_redun_eq" + i + "_" + curraccess.get(i),
                    input_id,
                    "redun_eq" + i + "_" + curraccess.get(i), true);
            Graphs.copyAttributes(result.getEdge(edgeID.get(i)),
                    hierarchy.get(level).getEdge("edge_redun_eq" + i + "_" + curraccess.get(i)));

            // New value
            hierarchy.get(level).addNode("redun_" + buffer.get(i));
            Graphs.copyAttributes(result.getEdge(edgeID.get(i)),
                    hierarchy.get(level).getNode("redun_" + buffer.get(i)));
            hierarchy.get(level).getNode("redun_" + buffer.get(i)).addAttribute("label", buffer.get(i));

            // connect to equal
            hierarchy.get(level).addEdge("edge_redun_" + buffer.get(i), "redun_eq" + i + "_" + curraccess.get(i),
                    "redun_" + buffer.get(i), true);

            Graphs.copyAttributes(result.getEdge(edgeID.get(i)),
                    hierarchy.get(level).getEdge("edge_redun_" + buffer.get(i)));
            hierarchy.get(level).getEdge("edge_redun_" + buffer.get(i)).setAttribute("label", buffer.get(i));
            hierarchy.get(level).getEdge("edge_redun_" + buffer.get(i)).setAttribute("att2", "loc");
            hierarchy.get(level).getEdge("edge_redun_" + buffer.get(i)).removeAttribute("array");

            // connect to hyper
            hierarchy.get(level).addEdge("edge_redun_hyper" + buffer.get(i), "redun_" + buffer.get(i),
                    hypernode, true);
            Graphs.copyAttributes(result.getEdge(edgeID.get(i)),
                    hierarchy.get(level).getEdge("edge_redun_hyper" + buffer.get(i)));
            hierarchy.get(level).getEdge("edge_redun_hyper" + buffer.get(i)).setAttribute("label", buffer.get(i));
            hierarchy.get(level).getEdge("edge_redun_hyper" + buffer.get(i)).setAttribute("att2", "loc");
            hierarchy.get(level).getEdge("edge_redun_hyper" + buffer.get(i)).removeAttribute("array");
        }

        // update Edges from graph
        int ind = 0;
        boolean connect = false;
        String prevbuffnodeid = null;
        for (String id : edgeID) {
            ind = edgeID.indexOf(id);
            Edge e = result.getEdge(id);
            if (redundant.size() == 0)
                return;
            if (!redundant.contains(ind)) {
                if (result.getNode("input_" + curraccess.get(ind)) != null)
                    continue;

                result.addNode("input_" + curraccess.get(ind));
                Graphs.copyAttributes(result.getEdge(edgeID.get(ind)), result.getNode("input_" + curraccess.get(ind)));
                result.addEdge("edge_input_" + curraccess.get(ind), "Start", "input_" + curraccess.get(ind), true);

                result.removeEdge(e.getNode0().getEnteringEdge(0).getId());

                result.addEdge("edge_connect_" + curraccess.get(ind), "input_" + curraccess.get(ind),
                        e.getNode0().getId(), true);

                Graphs.copyAttributes(result.getEdge(edgeID.get(ind)),
                        result.getEdge("edge_connect_" + curraccess.get(ind)));

                result.getNode(e.getNode0().getId()).clearAttributes();

                result.getNode(e.getNode0().getId()).addAttribute("label", "=");
                result.getNode(e.getNode0().getId()).addAttribute("att1", "op");
                result.getNode(e.getNode0().getId()).addAttribute("ord", 0);
                if (!no_shift_buffer)
                    result.getEdge(id).addAttribute("skip_write_info", true);
                result.getNode(e.getNode0().getId()).addAttribute("connect", true);

            }

            result.getEdge(id).setAttribute("label", buffer.get(ind));
            result.getEdge(id).setAttribute("att2", "loc");
            if (result.getEdge(id).hasAttribute("array")) {
                result.getEdge(id).removeAttribute("array");
            }

            Node temp_father = result.getNode(result.getEdge(id).getNode0().getId());
            if (temp_father.getAttribute("att1").equals("var"))
                result.getNode(temp_father.getId()).setAttribute("label", buffer.get(ind));

            if (redundant.contains(ind)) {

                if (!curraccess.get(ind).equals(nextaccess.get(ind))) {

                    result.getNode(e.getNode0().getId()).addAttribute("connect", true);
                    result.addNode("eq_" + buffer.get(ind));
                    result.addNode("buff_" + buffer.get(ind));

                    result.getNode("eq_" + buffer.get(ind)).addAttribute("label", "=");
                    result.getNode("eq_" + buffer.get(ind)).addAttribute("att1", "op");
                    result.getNode("eq_" + buffer.get(ind)).addAttribute("ord", 0);

                    result.getNode("buff_" + buffer.get(ind)).addAttribute("label", "buff");
                    result.getNode("buff_" + buffer.get(ind)).addAttribute("att1", "buff");

                    result.addEdge("1_eq_redun_" + curraccess.get(ind), e.getNode1().getId(),
                            "buff_" + buffer.get(ind), true);
                    Graphs.copyAttributes(result.getEdge(e.getNode1().getLeavingEdge(0).getId()),
                            result.getEdge("1_eq_redun_" + curraccess.get(ind)));

                    result.addEdge("2_eq_redun_" + curraccess.get(ind), "buff_" + buffer.get(ind),
                            "eq_" + buffer.get(ind), true);
                    Graphs.copyAttributes(e, result.getEdge("2_eq_redun_" + curraccess.get(ind)));

                    result.getEdge("2_eq_redun_" + curraccess.get(ind)).setAttribute("att2", "loc");
                    result.getEdge("2_eq_redun_" + curraccess.get(ind)).setAttribute("label", buffer.get(ind));
                    // new

                    result.addEdge("3_eq_redun_" + curraccess.get(ind), "eq_" + buffer.get(ind), "End", true);
                    Graphs.copyAttributes(e, result.getEdge("3_eq_redun_" + curraccess.get(ind)));
                    // int aux = curraccess.indexOf(nextaccess.get(ind));
                    int aux = getNextPos(ind);
                    // int aux = ind + 1;
                    result.getEdge("3_eq_redun_" + curraccess.get(ind)).setAttribute("att2", "loc");
                    result.getEdge("2_eq_redun_" + curraccess.get(ind)).setAttribute("label", buffer.get(aux));

                    if (redundant.contains(aux)) {
                        result.getEdge("2_eq_redun_" + curraccess.get(ind)).addAttribute("inter_buffer", true);
                    }

                    result.getEdge("3_eq_redun_" + curraccess.get(ind)).addAttribute("inter_buffer", true);
                    if (connect) {
                        result.addEdge("buff_connect_" + curraccess.get(ind), prevbuffnodeid, "buff_" + buffer.get(ind),
                                true);
                        result.getEdge("buff_connect_" + curraccess.get(ind)).setAttribute("att1", "nop");
                        result.getEdge("buff_connect_" + curraccess.get(ind)).setAttribute("label", "nop");

                    }

                    prevbuffnodeid = new String("buff_" + buffer.get(ind));
                    if (!connect)
                        connect = true;

                }
            }
            if (!result.getEdge(id).hasAttribute("skip_write_info")) {
                result.getEdge(id).addAttribute("inter_buffer", true);
            }
        }
        System.out.println("completed shift buffer");
        if (!no_shift_buffer) {
            String type = result.getAttribute("hyper_type");
            result.setAttribute("hyper_type", type.concat("_sequential"));
        }

    }

    /**
     * Based on given index identifies the index that points to the correct buffer to connect the value.
     * 
     * @param ind
     *            given index of the current buffer storing a value.
     * @return index of the buffer that should get the value for the next loop iteration.
     */
    public int getNextPos(int ind) {
        int pos = 0;
        String name = buffer.get(ind).substring(buffer.get(ind).indexOf("_b"));
        String name2 = "empty";
        String check = nextaccess.get(ind);
        int index = 0;
        for (String comp : curraccess) {
            if (comp.equals(check)) {
                name2 = buffer.get(index).substring(buffer.get(index).indexOf("_b"));
                if (name2.equals(name)) {
                    pos = index;
                    break;
                }
            }

            index++;
        }

        return pos;
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
     * Replaces a specific index of a dimension of an array with a new given value.
     * 
     * @param name
     *            label of the array access
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
     * return true if possible to use redundant values.
     * 
     * @return
     */
    public boolean isPossible() {
        // TODO Auto-generated method stub
        return !shift_not_possible;
    }

}
