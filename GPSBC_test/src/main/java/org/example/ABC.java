package org.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ManyToManyShortestPathsAlgorithm;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.shortestpath.DijkstraManyToManyShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

public class ABC {


    public static void ConstraintA1B1C1(Graph<Node, DefaultWeightedEdge> graph, int specifiedZone, double edgeWeightLimit) {
        Graph<Node, DefaultWeightedEdge> graphABC = new SimpleGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphABC, graph);

        //A1
        for (Node nodeX : graph.vertexSet()) {
            if (nodeX.getZone() != specifiedZone) {
                graphABC.removeVertex(nodeX);
            }
        }

        //B1
        Set<DefaultWeightedEdge> removeEdge = new HashSet<>();
        for (DefaultWeightedEdge edge : graphABC.edgeSet()) {
            if (graphABC.getEdgeWeight(edge) >= edgeWeightLimit) {
                removeEdge.add(edge);
            }
        }
        removeEdge.forEach(graphABC::removeEdge);

        //C1
        int busiestDeg = 0;
        for (Node node : graphABC.vertexSet()) {
            if (graphABC.degreeOf(node) >= busiestDeg) {
                busiestDeg = graphABC.degreeOf(node);
            }
        }
        System.out.println("Highest Degree: " + busiestDeg);
        final int highestDegree = busiestDeg;
        BetweennessCentrality<Node, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphABC);
        Map<Node, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<Node, Double>comparingByValue().reversed())
                .forEach(
                        (node) -> {
                            //C1 cont.
                            if (graphABC.degreeOf(node.getKey()) == highestDegree) {
                                System.out.println(node.getKey().getNodeNumber() + " -> " + node.getValue() + "...Zone:  " + node.getKey().getZone() + "...Deg: " + graphABC.degreeOf(node.getKey()));
                            }
                        }
                );
    }


//    public static void A1B1C2(Graph<Node, DefaultWeightedEdge> graph, int specifiedZone, double edgeWeightLimit, int degreeLimit){
//        Graph<Node, DefaultWeightedEdge> graphABC = new SimpleGraph<>(DefaultWeightedEdge.class);
//        Graphs.addGraph(graphABC, graph);
//
//        //A1
//        for (Node nodeX : graph.vertexSet()) {
//            if (nodeX.getZone() != specifiedZone) {
//                graphABC.removeVertex(nodeX);
//            }
//        }
//
//        //B1
//        Set<DefaultWeightedEdge> removeEdge = new HashSet<>();
//        for(DefaultWeightedEdge edge: graphABC.edgeSet()){
//            //If the cost of the edge is greater than edgePriceLimit
//            if (graphABC.getEdgeWeight(edge) <= edgeWeightLimit) {
//                removeEdge.add(edge);
//            }
//        }
//        removeEdge.forEach(graphABC::removeEdge);
//
//        //C2
//
//        BetweennessCentrality<Node, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphABC, true);
//        Map<Node, Double> scores = bc.getScores();
//        scores.entrySet().stream()
//                .sorted(Map.Entry.<Node, Double>comparingByValue())
//                .forEach(
//                        (node) -> {
//                            //C1 cont.
//                            if (graphABC.degreeOf(node.getKey()) >) {
//                                System.out.println(node.getKey().getNodeNumber() + " -> " + node.getValue() + "...Zone:  " + node.getKey().getZone());
//                            }
//                        }
//                );
//
//    }

    //
//    public static void A1B2C1(Graph<Node, DefaultWeightedEdge> graph, int specifiedZone, double edgePriceLimit, double pathCostLimit){
//        Graph<Node, DefaultWeightedEdge> graphABC = new SimpleGraph<>(DefaultWeightedEdge.class);
//        Graphs.addGraph(graphABC, graph);
//
//        //A1
//        for (Node nodeX : graph.vertexSet()) {
//            if (nodeX.getZone() != specifiedZone) {
//                graphABC.removeVertex(nodeX);
//            }
//        }
//
//        //C1
//        int busiestDeg = 0;
//        for(Node node : graphABC.vertexSet()){
//            if(graphABC.degreeOf(node) >= busiestDeg){
//                busiestDeg = graphABC.degreeOf(node);
//            }
//        }
//
//        //B2
//        //Note: Custom sources and targets
//        DijkstraManyToManyShortestPaths<Node, DefaultWeightedEdge> manyToMany = new DijkstraManyToManyShortestPaths<>(graphABC);
//        Set<Node> sources = Set.of();
//        Set<Node> targets = Set.of();
//
//        for(Node node : graphABC.vertexSet()){
//            sources.add(node); //Immutable object is modified?
//            targets.add(node); //Immutable object is modified?
//        }
//
//        ManyToManyShortestPathsAlgorithm.ManyToManyShortestPaths<Node, DefaultWeightedEdge> paths = manyToMany.getManyToManyPaths(sources, targets);
//
//        double numShortestPath = 0;
//        Map<Node, Integer> count = new HashMap<>();
//        for(Node source : sources){
//            for(Node target : targets){
//                //Can you find the top-10 busiest airports given there’s no connecting ticket price higher than pathCostLimit
//                if(source != target && paths.getWeight(source, target) <= pathCostLimit){
//                    numShortestPath++;
//                    for(Node vertex : paths.getPath(source, target).getVertexList()){
//                        //Update vertex count
//                        count.put(vertex, count.getOrDefault(vertex, 0) + 1);
//                    }
//                }
//            }
//        }
//
//        final double shortPathTemp = numShortestPath;
//        final double BusiestDegree = busiestDeg;
//        count.entrySet().stream()
//                .sorted(Map.Entry.<Node, Integer>comparingByValue().reversed())
//                .forEach(
//                        (node) -> {
//                            if (graphABC.degreeOf(node.getKey()) == BusiestDegree) {
//                                System.out.println(node.getKey().getNodeNumber() + ": " + node.getValue()/shortPathTemp);                          }
//                        }
//                );
//    }
//
    public static void ConstraintA1B2C2(Graph<Node, DefaultWeightedEdge> graph, int specifiedZone, double degreeLimit, double pathCostLimit) {
        Graph<Node, DefaultWeightedEdge> graphABC = new SimpleGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphABC, graph);

        //A1
        for (Node nodeX : graph.vertexSet()) {
            if (nodeX.getZone() != specifiedZone) {
                graphABC.removeVertex(nodeX);
            }
        }

        //B2
        DijkstraManyToManyShortestPaths<Node, DefaultWeightedEdge> manyToMany = new DijkstraManyToManyShortestPaths<>(graphABC);
        Set<Node> sources = new HashSet<>(Set.of());
        Set<Node> targets = new HashSet<>(Set.of());

        for(Node node : new ArrayList<>(graphABC.vertexSet())){
            sources.add(node);
            targets.add(node);
        }

        ManyToManyShortestPathsAlgorithm.ManyToManyShortestPaths<Node, DefaultWeightedEdge> paths = manyToMany.getManyToManyPaths(sources, targets);

        double numShortestPath = 0;
        Map<Node, Integer> count = new HashMap<>();
        for(Node source : sources){
            for(Node target : targets){
                if(source != target && paths.getWeight(source, target) <= pathCostLimit){
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
                .forEach(
                        (node) -> {
                            //C2
                            if (graphABC.degreeOf(node.getKey()) <= degreeLimit) {
                                System.out.println(node.getKey().getNodeNumber() + ": " + node.getValue() / shortPathTemp);
                            }
                        }
                );
    }
}
