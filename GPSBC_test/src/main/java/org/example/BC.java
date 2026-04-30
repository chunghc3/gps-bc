package org.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ManyToManyShortestPathsAlgorithm;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.shortestpath.DijkstraManyToManyShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.sql.Array;
import java.util.*;

public class BC {

    public static void ConstraintB1C1(Graph<Node, DefaultWeightedEdge> graph, double edgeWeightLimit) {
        /* ConstraintB1C1: Find the busiest airport that has an edge less than edgePriceLimit */

        Graph<Node, DefaultWeightedEdge> graphB1C1 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphB1C1, graph);

        Set<Node> remove = new HashSet<>();
        for (Node nodeX : new ArrayList<>(graphB1C1.vertexSet())) {
            for (DefaultWeightedEdge edgeX : graphB1C1.edgesOf(nodeX)) {
                if (graphB1C1.getEdgeWeight(edgeX) <= edgeWeightLimit) {
                    remove.add(nodeX);
                }
            }
        }
        remove.forEach(graphB1C1::removeVertex);

        int highestEdgeCt = 0;
        Node highestNode = null;
        for (Node nodeX : graphB1C1.vertexSet()) {
            if (graphB1C1.degreeOf(nodeX) >= highestEdgeCt) {
                highestNode = nodeX;
                highestEdgeCt = graphB1C1.degreeOf(nodeX);
            }
        }

        BetweennessCentrality<Node, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphB1C1, true);
        Map<Node, Double> scores = bc.getScores();
        System.out.println("BC score of " + highestNode.getNodeNumber() + " -> " + scores.get(highestNode));
    }

//    public static void ConstraintB1C2() {
//        /* NEED TO VERIFY */
//        /* ConstraintB1C2: No edge weight greater than <?> */
//
//        Graph<Node, DefaultWeightedEdge> graphB1 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
//        Graphs.addGraph(graphB1, graph);
//
//        //Note: Custom limit for edge
//        double edgePriceLimit = 430;
//        int edgeNumberLimit = 3;
//
//
//        Set<Node> remove = new HashSet<>();
//        for (Node nodeX : new ArrayList<>(graphB1.vertexSet())) {
//            for (DefaultWeightedEdge edgeX : graphB1.edgesOf(nodeX)) {
//                if (graphB1.getEdgeWeight(edgeX) < edgePriceLimit && graphB1.degreeOf(nodeX) < edgeNumberLimit) {
//                    remove.add(nodeX);
//                }
//            }
//        }
//        remove.forEach(graphB1::removeVertex);
//
//        BetweennessCentrality<Node, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphB1, true);
//        Map<Node, Double> scores = bc.getScores();
//        scores.entrySet().stream()
//                .sorted(Map.Entry.<Node, Double>comparingByValue().reversed())
//                .forEach(e -> System.out.println(e.getKey().getNodeNumber() + ": " + e.getValue()));
//
//    }

    public static void ConstraintB2C1(Graph<Node, DefaultWeightedEdge> graph, double pathCostLimit) {

        //B2
        DijkstraManyToManyShortestPaths<Node, DefaultWeightedEdge> manyToMany = new DijkstraManyToManyShortestPaths<>(graph);
        Set<Node> sources = new java.util.HashSet<>(Set.of());
        Set<Node> targets = new java.util.HashSet<>(Set.of());

        for(Node node : new ArrayList<>(graph.vertexSet())){
            sources.add(node);
            targets.add(node);
        }

        ManyToManyShortestPathsAlgorithm.ManyToManyShortestPaths<Node, DefaultWeightedEdge> paths = manyToMany.getManyToManyPaths(sources, targets);

        double numShortestPath = 0;
        Map<Node, Integer> count = new HashMap<>();
        for(Node source : sources){
            for(Node target : targets){
                if(source != target && paths.getWeight(source, target) >= pathCostLimit){
                    numShortestPath++;
                    for(Node vertex : paths.getPath(source, target).getVertexList()){
                        count.put(vertex, count.getOrDefault(vertex, 0) + 1);
                    }
                }
            }
        }

        //C1
        int highestDegree = 0;
        for (Node nodeX : graph.vertexSet()) {
            if (graph.degreeOf(nodeX) >= highestDegree) {
                highestDegree = graph.degreeOf(nodeX);
            }
        }
        System.out.println("HighestDegree: " + highestDegree);
        final int HighestDegree = highestDegree;
        final double shortPathTemp = numShortestPath;
        count.entrySet().stream()
                .sorted(Map.Entry.<Node, Integer>comparingByValue().reversed())
                .forEach(e -> {
                            if (graph.degreeOf(e.getKey()) == HighestDegree) {
                                System.out.println(e.getKey().getNodeNumber() + ": " + e.getValue() / shortPathTemp + "...deg: " + graph.degreeOf(e.getKey()));
                            }
                        }
                );
    }

    public static void ConstraintB2C2(Graph<Node, DefaultWeightedEdge> graph, double pathCostLimit, int degreeLimit) {

        //B2
        DijkstraManyToManyShortestPaths<Node, DefaultWeightedEdge> manyToMany = new DijkstraManyToManyShortestPaths<>(graph);
        Set<Node> sources = new java.util.HashSet<>(Set.of());
        Set<Node> targets = new java.util.HashSet<>(Set.of());

        for(Node node : new ArrayList<>(graph.vertexSet())){
            sources.add(node);
            targets.add(node);
        }

        ManyToManyShortestPathsAlgorithm.ManyToManyShortestPaths<Node, DefaultWeightedEdge> paths = manyToMany.getManyToManyPaths(sources, targets);

        double numShortestPath = 0;
        Map<Node, Integer> count = new HashMap<>();
        for(Node source : sources){
            for(Node target : targets){
                if(source != target && paths.getWeight(source, target) >= pathCostLimit){
                    numShortestPath++;
                    for(Node vertex : paths.getPath(source, target).getVertexList()){
                        count.put(vertex, count.getOrDefault(vertex, 0) + 1);
                    }
                }
            }
        }

        final double shortPathTemp = numShortestPath;
        count.entrySet().stream()
                .sorted(Map.Entry.<Node, Integer>comparingByValue().reversed())
                .forEach(e -> {
                            if (graph.degreeOf(e.getKey()) <= degreeLimit) {
                                System.out.println(e.getKey().getNodeNumber() + ": " + e.getValue() / shortPathTemp);
                            }
                        }

                );
    }

}
