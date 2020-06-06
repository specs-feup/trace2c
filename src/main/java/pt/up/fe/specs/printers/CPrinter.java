package pt.up.fe.specs.printers;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pt.up.fe.specs.Config;
import pt.up.fe.specs.utils.Utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

abstract public class CPrinter {
    protected Utils utils = new Utils();
    protected Graph graph;
    protected BufferedWriter outBuffer;
    protected Config config;

    public CPrinter(BufferedWriter outBuffer, Graph graph, Config config) {
        this.outBuffer = outBuffer;
        this.graph = graph;
        this.config = config;
    }

    abstract public void print();

    protected void writeCodeByLevel(List<List<Node>> levelGraph, int loopLevel) throws IOException {
        for (int i = 1; i < levelGraph.size() - 1; i++) {
            List<Node> list = levelGraph.get(i);
            for (Node n : list) {
                String att1 = n.getAttribute("att1");
                if (att1.equals("op")) {
                    writeAssignment(n);
                } else if (att1.equals("hyper")) {
                    if (n.getAttribute("type").equals("parallel")) {
                        for (Edge edge: n.getEachEnteringEdge()) {
                            outBuffer.append(getCallStatement(edge) + ";\n");
                        }
                    } else {
                        CLoopPrinter cLoopPrinter = new CLoopPrinter(outBuffer, graph, config, loopLevel);
                        cLoopPrinter.print();
                    }
                    n.addAttribute("done", true);
                }
            }
            outBuffer.append( "\n");
        }
    }

    abstract protected String getLabel(Edge edge) throws IOException;
    abstract protected String getAssignmentLabel(Edge edge) throws IOException;



    protected void writeExpression(Node n, Edge leavingEdge) throws IOException {
        String att1 = n.getAttribute("att1");
        if (att1.equals("mux")) {
            writeTernaryStatement(n);
        } else if (att1.equals("op")) {
            writeOperation(n, leavingEdge);
        } else {
            outBuffer.append(n.getAttribute("label").toString());
        }
    }

    protected void writeAssignment(Node n) throws IOException {
        for (Edge leavingEdge : n.getEachLeavingEdge()) {
            String label = getAssignmentLabel(leavingEdge);
            outBuffer.append(label + " = ");
            writeExpression(n, leavingEdge);
            outBuffer.append(";\n");
        }
    }

    protected void writeOperation(Node n, Edge leavingEdge) throws IOException {

        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(n.getEnteringEdge(0));
        edges.add(n.getEnteringEdge(1));
        ArrayList<String> operands = new ArrayList<>();

        for (Edge edge: edges) {
            String name = getLabel(edge);
            operands.add(name);
        }

        boolean hasMod = leavingEdge.hasAttribute("mod");

        String operationType = n.getAttribute("label").toString();
        String operation;
        if (edges.get(0).getAttribute("pos").toString().equals("l")) {
            operation = operands.get(0) + " " + operationType + " " + operands.get(1);
        } else {
            operation = operands.get(1) + " " + operationType + " " + operands.get(0);
        }
        if (hasMod) {
            operation = leavingEdge.getAttribute("mod").toString() + operation + ")";
        }
        outBuffer.append(operation);
    }

    protected String getCallStatement(Edge edge) {
        StringBuffer buffer = new StringBuffer();
        if (edge.getAttribute("att1").equals("call")) {
            String functionName = edge.getAttribute("att2");
            buffer.append(functionName);
            buffer.append("(");
            boolean isFirstParam = true;
            ArrayList<String> params = edge.getAttribute("att3");
            for (String param : params) {
                if (isFirstParam) {
                    buffer.append(param);
                    isFirstParam = false;
                } else {
                    buffer.append("," + param);
                }
            }
            buffer.append(")");
        }

        return buffer.toString();
    }

    protected void writeTernaryStatement(Node n) throws IOException {
        Node selectorNode = null;
        Node trueNode = null;
        Node falseNode = null;
        for (Edge e : n.getEachEnteringEdge()) {
            String position = e.getAttribute("pos");
            if (position.equals("sel")) {
                selectorNode = e.getSourceNode();
            } else if (position.equals("t")) {
                trueNode = e.getSourceNode();
            } else if (position.equals("f")) {
                falseNode = e.getSourceNode();
            }
        }
        if (selectorNode == null || trueNode == null || falseNode == null) {
            throw new IOException("Error in graph: " + n.getAttribute("label"));
        }
        //outBuffer.append(getOperationStatement(selectorNode) + " ? " + getExpression(trueNode) + " : " + getExpression(falseNode));
    }
}
