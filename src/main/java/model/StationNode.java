package model;

public class StationNode {

    private int nodeId;
    private String name;
    private String floor;
    private String type;
    private String nodeCode;
    


    public StationNode(int nodeId, String name, String floor, String type, String nodecode) {
        this.nodeId = nodeId;
        this.name = name;
        this.floor = floor;
        this.type = type;
        this.nodeCode = nodecode;
    }

    public int getNodeId() {
        return nodeId;
    }

    public String getName() {
        return name;
    }

    public String getFloor() {
        return floor;
    }

    public String getType() {
        return type;
    }
    public String getNodeCode() {
        return nodeCode;
    }
}
