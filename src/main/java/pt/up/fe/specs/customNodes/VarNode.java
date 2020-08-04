package pt.up.fe.specs.customNodes;

import org.graphstream.graph.Node;

public class VarNode {

    private Node node;

    public VarNode(Node n) {
        this.node = n;
    }

    public String getScope() {
        return node.getAttribute("att2");
    }

    public String getType() {
        return node.getAttribute("att3");
    }


}
