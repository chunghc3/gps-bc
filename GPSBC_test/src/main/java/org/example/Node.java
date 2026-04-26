package org.example;

public class Node {
    private int nodeNumber;
    private int zone;

    public Node(int zone){
        this.zone = zone;
    }

    public Node(int nodeNumber, int zone){
        this.nodeNumber = nodeNumber;
        this.zone = zone;
    }

    public String getNodeNumber() {return "#" + nodeNumber;}
    public int getZone() {return zone;}
    //public int getNodeNumber(){return nodeNumber;}

}
