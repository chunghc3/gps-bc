package org.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.*;

public class AC {

    public static void ConstraintA1C1(Graph<Node, DefaultWeightedEdge> graph,int specifiedZone){
        Graph<Node, DefaultWeightedEdge> graphAC = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphAC, graph);

        Set<Node> remove = new HashSet<>();
        //Constraint A1: Filters GraphAC by ZONE
        for (Node nodeX : graphAC.vertexSet()) {
            if (nodeX.getZone() != specifiedZone) {
                remove.add(nodeX);
            }
        }
        remove.forEach(graphAC::removeVertex);

        //Constraint C1: Finds the nodes with the HIGHEST BC score
        int highestDegree = 0;
        for (Node nodeX : new ArrayList<>(graphAC.vertexSet())) {
            if (graphAC.degreeOf(nodeX) >= highestDegree) {
                highestDegree = graphAC.degreeOf(nodeX);
            }
        }
        final int highest = highestDegree;
        System.out.println("Highest Degree: " + highestDegree);
        //BC Calculation
        BetweennessCentrality<Node, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphAC);
        Map<Node, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<Node, Double>comparingByValue().reversed())
                .forEach(
                        (node) -> {
                            if (graphAC.degreeOf(node.getKey()) == highest) {
                                System.out.println(node.getKey().getNodeNumber() + ", Zone: " + node.getKey().getZone() + ", degree: " + graphAC.degreeOf(node.getKey()) + " -> " + node.getValue());
                            }
                        }
                );

    }

    public static void ConstraintA1C2(Graph<Node, DefaultWeightedEdge> graph, int specifiedZone, int degreeLimit){
        Graph<Node, DefaultWeightedEdge> graphAC = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphAC, graph);

        Set<Node> remove = new HashSet<>();
        //Constraint A1: Filters GraphAC by ZONE
        for (Node nodeX : graph.vertexSet()) {
            if (nodeX.getZone() != specifiedZone) {
                remove.add(nodeX);
            }
        }
        remove.forEach(graphAC::removeVertex);


        //BC Calculation
        BetweennessCentrality<Node, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphAC);
        Map<Node, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<Node, Double>comparingByValue().reversed())
                .forEach(
                        (node) -> {
                            if (graphAC.degreeOf(node.getKey()) > degreeLimit) {
                                System.out.println(node.getKey().getNodeNumber() + ", Zone: " + node.getKey().getZone() + ", degree: " + graphAC.degreeOf(node.getKey()) + " -> " + node.getValue());
                            }
                        }
                );
    }


}
