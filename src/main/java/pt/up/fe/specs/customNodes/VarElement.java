package pt.up.fe.specs.customNodes;

import org.graphstream.graph.Element;
import pt.up.fe.specs.utils.Utils;

import java.util.ArrayList;

public class VarElement {

    private Element element;

    public VarElement(Element e) {
        this.element = e;
    }

    public String getScope() {
        return element.getAttribute("att2");
    }

    public String getType() {
        return element.getAttribute("att3");
    }

    public String getName() {
        return element.getAttribute("name");
    }

    public Integer getDim() {
        return element.getAttribute("dim");
    }

    public boolean isArray() {
        return element.hasAttribute("array") && element.getAttribute("array").equals(true);
    }

    public String getLabel() {
        return element.getAttribute("label");
    }

    public ArrayList<Integer> getIndexes() {
        if (element.hasAttribute("indexes")) {
            return element.getAttribute("indexes");
        } else {
            ArrayList<Integer> indexes = Utils.getIndexes(getLabel());
            element.setAttribute("indexes", indexes);
            element.setAttribute("dim", indexes.size());
            return indexes;
        }

    }


}
