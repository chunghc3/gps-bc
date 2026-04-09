package org.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.Map;

public class C {
    static Graph<NodeAirport, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

    static NodeAirport MUC = new NodeAirport("MUC");
    static NodeAirport YVR = new NodeAirport("YVR");
    static NodeAirport CDG = new NodeAirport("CDG");
    static NodeAirport HND = new NodeAirport("HND");
    static NodeAirport TPE = new NodeAirport("TPE");
    static NodeAirport CAI = new NodeAirport("CAI");
    static NodeAirport DEN = new NodeAirport("DEN");
    static NodeAirport MNL = new NodeAirport("MNL");
    static NodeAirport IST = new NodeAirport("IST");
    static NodeAirport LAX = new NodeAirport("LAX");
    static NodeAirport ICN = new NodeAirport("ICN");
    static NodeAirport JFK = new NodeAirport("JFK");
    static NodeAirport OSL = new NodeAirport("OSL");
    static NodeAirport DUB = new NodeAirport("DUB");
    static NodeAirport SIN = new NodeAirport("SIN");

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

    public static void ConstraintC1() {
        /* Constraint C, Type 1: DEGREE of nodes */ /* Completed */

        Graph<NodeAirport, DefaultWeightedEdge> graphC1 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        //Creates copy of graph
        Graphs.addGraph(graphC1, graph);

        //Note: Custom limit of degrees for each node
        int degreeLimit = 3;
        for (NodeAirport node : new ArrayList<>(graphC1.vertexSet())) {
            //If the degree of node is less than degreeLimit, remove the node
            if (graphC1.degreeOf(node) < degreeLimit) {
                graphC1.removeVertex(node);
            }
        }

        //Prints out all scores from highest to lowest
        BetweennessCentrality<NodeAirport, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphC1, true);
        Map<NodeAirport, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<NodeAirport, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getAirportName() + ": " + e.getValue()));
    }

    public static void ConstraintC2() {
        /* Constraint C, Type 2: DEGREE of nodes */ /* Completed */

        BetweennessCentrality<NodeAirport, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graph, true);
        Map<NodeAirport, Double> scores = bc.getScores();
        scores.forEach(
                (node, score) -> {
                    /* Filters by degree*/
                    if (graph.degreeOf(node) <= 3) {
                        System.out.println(node.getAirportName() + ": " + score);
                    }
                }
        );
    }
}
