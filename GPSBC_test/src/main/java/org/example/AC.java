package org.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.w3c.dom.Node;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.*;

public class AC {
    static Graph<NodeAirport, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

    static NodeAirport MUC = new NodeAirport("MUC", 3);
    static NodeAirport YVR = new NodeAirport("YVR", 2);
    static NodeAirport CDG = new NodeAirport("CDG", 3);
    static NodeAirport HND = new NodeAirport("HND", 2);
    static NodeAirport TPE = new NodeAirport("TPE", 3);
    static NodeAirport CAI = new NodeAirport("CAI", 1);
    static NodeAirport DEN = new NodeAirport("DEN", 2);
    static NodeAirport MNL = new NodeAirport("MNL", 2);
    static NodeAirport IST = new NodeAirport("IST", 3);
    static NodeAirport LAX = new NodeAirport("LAX", 1);
    static NodeAirport ICN = new NodeAirport("ICN", 3);
    static NodeAirport JFK = new NodeAirport("JFK", 1);
    static NodeAirport OSL = new NodeAirport("OSL", 3);
    static NodeAirport DUB = new NodeAirport("DUB", 3);
    static NodeAirport SIN = new NodeAirport("SIN", 3);

    static {
        graph.addVertex(MUC);
        graph.addVertex(YVR);
        graph.addVertex(CDG);
        graph.addVertex(HND);
        graph.addVertex(TPE);
        graph.addVertex(CAI);
        graph.addVertex(DEN);
        graph.addVertex(MNL);
        graph.addVertex(IST);
        graph.addVertex(LAX);
        graph.addVertex(ICN);
        graph.addVertex(JFK);
        graph.addVertex(OSL);
        graph.addVertex(DUB);
        graph.addVertex(SIN);

        Graphs.addEdge(graph, MUC, HND, 60);
        Graphs.addEdge(graph, MUC, OSL, 100);
        Graphs.addEdge(graph, YVR, SIN, 230);
        Graphs.addEdge(graph, YVR, TPE, 500);
        Graphs.addEdge(graph, YVR, IST, 160);
        Graphs.addEdge(graph, CDG, HND, 130);
        Graphs.addEdge(graph, CDG, MNL, 130);
        Graphs.addEdge(graph, HND, TPE, 90);
        Graphs.addEdge(graph, TPE, JFK, 320);
        Graphs.addEdge(graph, TPE, LAX, 240);
        Graphs.addEdge(graph, CDG, HND, 130);
        Graphs.addEdge(graph, CAI, DEN, 310);
        Graphs.addEdge(graph, CAI, DUB, 80);
        Graphs.addEdge(graph, CAI, ICN, 110);
        Graphs.addEdge(graph, ICN, DEN, 220);
        Graphs.addEdge(graph, DEN, OSL, 320);
        Graphs.addEdge(graph, LAX, ICN, 430);
        Graphs.addEdge(graph, LAX, OSL, 270);
        Graphs.addEdge(graph, LAX, IST, 290);
        Graphs.addEdge(graph, IST, DUB, 180);
        Graphs.addEdge(graph, LAX, DUB, 190);
        Graphs.addEdge(graph, JFK, OSL, 300);
        Graphs.addEdge(graph, OSL, SIN, 310);
    }


    public static void ConstraintA1C1(){
        Graph<NodeAirport, DefaultWeightedEdge> graphAC = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphAC, graph);

        //Constraint A1: Filters GraphAC by ZONE
        for (NodeAirport nodeX : graph.vertexSet()) {
            if (nodeX.getAirportZone() != 3) {
                graphAC.removeVertex(nodeX);
            }
        }


        //Constraint C1: Finds the nodes with the HIGHEST BC score
        int highestDegree = 0;
        for (NodeAirport nodeX : new ArrayList<>(graphAC.vertexSet())) {
            //System.out.print(nodeX.getAirportName() + " d: " + graphAC.degreeOf(nodeX) + "  ");

            if (graphAC.degreeOf(nodeX) >= highestDegree) {
                highestDegree = graphAC.degreeOf(nodeX);
            }
        }

        Set<NodeAirport> remove = new HashSet<>();
        for (NodeAirport nodeX : new ArrayList<>(graphAC.vertexSet())) {
            if(graphAC.degreeOf(nodeX) != highestDegree){
                remove.add(nodeX);
            }
        }
        remove.forEach(graphAC::removeVertex);

        //BC Calculation
        BetweennessCentrality<NodeAirport, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphAC, true);
        Map<NodeAirport, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<NodeAirport, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getAirportName() + ", Zone: " + e.getKey().getAirportZone() + "; Score: " + e.getValue()));


    }

    public static void ConstraintA1C2(){
        Graph<NodeAirport, DefaultWeightedEdge> graphAC = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphAC, graph);

        //Constraint A1: Filters GraphAC by ZONE
        for (NodeAirport nodeX : graph.vertexSet()) {
            if (nodeX.getAirportZone() != 2) {
                graphAC.removeVertex(nodeX);
            }
        }

        //Constraint C2: Removes node is degree is less than 2
        int nodeDegreeLimit = 2;
        Set<NodeAirport> remove = new HashSet<>();
        for (NodeAirport nodeX : new ArrayList<>(graphAC.vertexSet())) {
            if(graphAC.degreeOf(nodeX) > nodeDegreeLimit){
                remove.add(nodeX);
            }
        }
        remove.forEach(graphAC::removeVertex);

        //BC Calculation
        BetweennessCentrality<NodeAirport, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphAC, true);
        Map<NodeAirport, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<NodeAirport, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getAirportName() + ", Zone: " + e.getKey().getAirportZone() + "; Score: " + e.getValue()));
    }


}
