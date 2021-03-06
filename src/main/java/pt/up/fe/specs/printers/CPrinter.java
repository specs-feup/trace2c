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

    public CPrinter(BufferedWriter outBuffer, Graph graph) {
        this.outBuffer = outBuffer;
        this.graph = graph;
    }

    abstract public void print();

    protected void writeCodeByLevel(List<List<Node>> levelGraph, int loopLevel) throws IOException {
        for (int i = 1; i < levelGraph.size() - 1; i++) {
            List<Node> list = levelGraph.get(i);
            for (Node n : list) {
                String att1 = n.getAttribute("att1");
                if (att1.equals("function")) {

                    for (Edge edge : n.getEachEnteringEdge()) {
                        outBuffer.append(getCallStatement(edge) + ";\n");
                    }

                } else if (att1.equals("hyper")) {
                    CLoopPrinter cLoopPrinter = new CLoopPrinter(outBuffer, graph, loopLevel);
                    cLoopPrinter.print();
                } else if (att1.equals("call")){
                    continue;
                } else if (!Utils.isNOP(n)) {
                    writeAssignment(n);
                }
                n.addAttribute("done", true);
            }
            outBuffer.append("\n");
        }
    }

    abstract protected String getLabel(Edge edge) throws IOException;

    abstract protected String getAssignmentLabel(Edge edge) throws IOException;

    protected void writeComplexAssignmentRHS(Node n) throws IOException {
        Edge rhs;
        for (Edge inEdge : n.getEachEnteringEdge()) {
            if (inEdge.getAttribute("pos").equals("r")) {
                rhs = inEdge;
                outBuffer.append(getLabel(rhs));
                break;
            }
        }
    }


    protected void writeExpressionRHS(Node n) throws IOException {
        if (Utils.isMux(n)) {
            writeTernaryStatementRHS(n);
        } else if (Utils.isOperation(n)) {
            writeOperationRHS(n);
        } else if (Utils.isComplexAssignment(n)) {
            writeComplexAssignmentRHS(n);
        } else if (Utils.isAssignment(n)) {
            outBuffer.append(getLabel(n.getEnteringEdge(0)));
        } else {
            outBuffer.append(n.getAttribute("label").toString());
        }
    }

    protected void writeAssignment(Node n) throws IOException {
        Edge leavingEdge = n.getLeavingEdge(0);

        String label = getAssignmentLabel(leavingEdge);
        outBuffer.append(label + " = ");
        writeExpressionRHS(n);
        outBuffer.append(";\n");

    }

    protected void writeOperationRHS(Node n) throws IOException {

        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(n.getEnteringEdge(0));
        edges.add(n.getEnteringEdge(1));
        ArrayList<String> operands = new ArrayList<>();

        for (Edge edge : edges) {
            String name = getLabel(edge);
            operands.add(name);
        }

        String operationType = Utils.getLabel(n);
        String operation;
        if (Utils.getPos(edges.get(0)).equals("l")) {
            operation = operands.get(0) + " " + operationType + " " + operands.get(1);
        } else {
            operation = operands.get(1) + " " + operationType + " " + operands.get(0);
        }

        outBuffer.append(operation);
    }

    protected String getCallStatement(Edge edge) {
        StringBuffer buffer = new StringBuffer();

        if (Utils.isCall(edge)) {
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

    protected void writeTernaryStatementRHS(Node n) throws IOException {
        Edge selectorEdge = null;
        Edge trueEdge = null;
        Edge falseEdge = null;
        for (Edge e : n.getEachEnteringEdge()) {
            String position = Utils.getPos(e);
            if (position.equals("sel")) {
                selectorEdge = e;
            } else if (position.equals("t")) {
                trueEdge = e;
            } else if (position.equals("f")) {
                falseEdge = e;
            }
        }
        if (selectorEdge == null || trueEdge == null || falseEdge == null) {
            throw new IOException("Error in graph: " + n.getAttribute("label"));
        }

        outBuffer.append(getLabel(selectorEdge) + " ? ");
        outBuffer.append(getLabel(trueEdge) + ":" + getLabel(falseEdge));
    }
}
