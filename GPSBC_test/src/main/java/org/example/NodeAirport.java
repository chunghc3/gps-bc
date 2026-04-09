package org.example;

public class NodeAirport {
    private String airportName;
    private int zone;

    public NodeAirport(String airportName){
        this.airportName = airportName;
    }

    public NodeAirport(String airportName, int zone){
        this.airportName = airportName;
        this.zone = zone;
    }

    public String getAirportName() {return airportName;}
    public int getAirportZone() {return zone;}
}
