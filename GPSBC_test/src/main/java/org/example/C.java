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

    static{
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

        Graphs.addEdge(graph,MUC,HND,60);
        Graphs.addEdge(graph,MUC,OSL,100);
        Graphs.addEdge(graph,YVR,SIN,230);
        Graphs.addEdge(graph,YVR,TPE,500);
        Graphs.addEdge(graph,YVR,IST,160);
        Graphs.addEdge(graph,CDG,HND,130);
        Graphs.addEdge(graph,CDG,MNL,130);
        Graphs.addEdge(graph,HND,TPE,90);
        Graphs.addEdge(graph,TPE,JFK,320);
        Graphs.addEdge(graph,TPE,LAX,240);
        Graphs.addEdge(graph,CDG,HND,130);
        Graphs.addEdge(graph,CAI,DEN,310);
        Graphs.addEdge(graph,CAI,DUB,80);
        Graphs.addEdge(graph,CAI,ICN,110);
        Graphs.addEdge(graph,ICN,DEN,220);
        Graphs.addEdge(graph,DEN,OSL,320);
        Graphs.addEdge(graph,LAX,ICN,430);
        Graphs.addEdge(graph,LAX,OSL,270);
        Graphs.addEdge(graph,LAX,IST,290);
        Graphs.addEdge(graph,IST,DUB,180);
        Graphs.addEdge(graph,LAX,DUB,190);
        Graphs.addEdge(graph,JFK,OSL,300);
        Graphs.addEdge(graph,OSL,SIN,310);
    }

    public static void ConstraintC1(){
        /* Constraint C, Type 1: DEGREE of nodes */

        //Filter out nodes by degree first, then run BC
        Graph<NodeAirport, DefaultWeightedEdge> C1Graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(C1Graph, graph);
        BetweennessCentrality<NodeAirport, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graph, true);
        Map<NodeAirport, Double> scores = bc.getScores();

        //For each node in graph
        NodeAirport greatest = MUC;
        for(NodeAirport node : new ArrayList<>(C1Graph.vertexSet())){
            int degree = C1Graph.degreeOf(node);
            //If the degree and score of node is greater than the greatest node, node become greatest
            if(degree >= C1Graph.degreeOf(node)){
                greatest = node;
            }
        }

        System.out.println("Constraint C1: Among all the airports, which is the most busiest airport?");
        System.out.println(greatest.getAirportName() + ": " + scores.get(greatest));
    }

    public static void ConstraintC2(){
        BetweennessCentrality<NodeAirport, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graph, true);
        Map<NodeAirport, Double> scores = bc.getScores();
        scores.forEach(
                (node, score) -> {
                    /* Filters by degree*/
                    if(graph.degreeOf(node) <= 3) {
                        System.out.println(node.getAirportName() + ": " + score);
                    }
                }
        );
    }
}
