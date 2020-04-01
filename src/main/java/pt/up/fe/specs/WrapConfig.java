package pt.up.fe.specs;

public class WrapConfig {

    private Integer levels;
    private String end_node;

    public WrapConfig(Integer levels, String endNode) {
        this.setLevels(levels);
        this.setEndNode(endNode);
    }


    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    public String getEndNode() {
        return end_node;
    }

    public void setEndNode(String endNode) {
        this.end_node = endNode;
    }
}
