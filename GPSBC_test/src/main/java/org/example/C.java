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

    public static void ConstraintC1(Graph<Node, DefaultWeightedEdge> graph, int degreeLimit) {
        /* Constraint C, Type 1: DEGREE of nodes */ /* Completed */

        Graph<Node, DefaultWeightedEdge> graphC1 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        //Creates copy of graph
        Graphs.addGraph(graphC1, graph);

        Set<Node> remove = new HashSet<>();
        for (Node node : new ArrayList<>(graphC1.vertexSet())) {
            //If the degree of node is less than degreeLimit, remove the node
            if (graphC1.degreeOf(node) < degreeLimit) {
                remove.add(node);
            }
        }
        remove.forEach(graphC1::removeVertex);

        //Prints out all scores from highest to lowest
        BetweennessCentrality<Node, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphC1);
        Map<Node, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<Node, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getNodeNumber() + ": " + e.getValue()));
    }

    public static void ConstraintC2(Graph<Node, DefaultWeightedEdge> graph, int degreeLimit) {
        /* Constraint C, Type 2: DEGREE of nodes */ /* Completed */

        BetweennessCentrality<Node, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graph);
        Map<Node, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .filter(e -> graph.degreeOf(e.getKey()) <= degreeLimit)
                .sorted(Map.Entry.<Node, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getNodeNumber() + ": " + e.getValue()));
    }
}
