package org.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class C {

    public static void ConstraintC1(Graph<Node, DefaultWeightedEdge> graph) {
        /* Constraint C, Type 1: DEGREE of nodes */ /* Completed */

        Graph<Node, DefaultWeightedEdge> graphC1 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        //Creates copy of graph
        Graphs.addGraph(graphC1, graph);

        int busiestDeg = 0;
        for(Node node : graphC1.vertexSet()){
            if(graphC1.degreeOf(node) >= busiestDeg){
                busiestDeg = graphC1.degreeOf(node);
            }
        }

        final int highestDegree = busiestDeg;
        System.out.println("Highest Degree: " + busiestDeg);
        //Prints out all scores from highest to lowest
        BetweennessCentrality<Node, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphC1);
        Map<Node, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .filter(e -> graphC1.degreeOf(e.getKey()) == highestDegree)
                .sorted(Map.Entry.<Node, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getNodeNumber() + ": " + e.getValue() + "...Deg: " + graphC1.degreeOf(e.getKey())));
    }

    public static void ConstraintC2(Graph<Node, DefaultWeightedEdge> graph, int degreeLimit) {
        /* Constraint C, Type 2: DEGREE of nodes */ /* Completed */

        BetweennessCentrality<Node, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graph);
        Map<Node, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .filter(e -> graph.degreeOf(e.getKey()) <= degreeLimit)
                .sorted(Map.Entry.<Node, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getNodeNumber() + ": " + e.getValue()+ "...Deg: " + graph.degreeOf(e.getKey())));
    }
}
