package org.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ManyToManyShortestPathsAlgorithm;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.shortestpath.DijkstraManyToManyShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;

public class B {
    public static void ConstraintB1(Graph<Node, DefaultWeightedEdge> graph, int edgeWeightLimit){
        /* Constraint B, Type 1: Weight Restriction on EDGE*/
        Graph<Node, DefaultWeightedEdge> graphB1 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphB1, graph);

        //Set to target nodes for removal
        Set<DefaultWeightedEdge> remove = new HashSet<>();
        for(Node node : new ArrayList<>(graphB1.vertexSet())){
            //For each edge in graphB1
            for(DefaultWeightedEdge edge: graphB1.edgesOf(node)){
                //If the cost of the edge is greater than edgePriceLimit
                if (graphB1.getEdgeWeight(edge) > edgeWeightLimit) {
                    remove.add(edge);
                }
            }
        }
        remove.forEach(graphB1::removeEdge);

        BetweennessCentrality<Node, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphB1, true);
        Map<Node, Double> scores = bc.getScores();
//        scores.entrySet().stream()
//                .sorted(Map.Entry.<Node, Double>comparingByValue().reversed())
//                .forEach(e -> System.out.println(e.getKey().getNodeNumber() + ": " + e.getValue()));
//        System.out.println("----------------------------------------");
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(scores::get));
        for (Map.Entry<Node, Double> entry : scores.entrySet()) {
            pq.offer(entry.getKey()); // Add element
            if (pq.size() > 10) {
                pq.poll();
            }
        }

        while(!pq.isEmpty()){
            Node node = pq.poll();
            System.out.println(node.getNodeNumber() + " -> " + scores.get(node));
        }
    }

    public static void ConstraintB2(Graph<Node, DefaultWeightedEdge> graph, double pathWeightLimit){
        /* Constraint B, Type 2: Weight Restriction on PATH */ /* Completed */

        //Note: Custom sources and targets
        DijkstraManyToManyShortestPaths<Node, DefaultWeightedEdge> manyToMany = new DijkstraManyToManyShortestPaths<>(graph);
        Set<Node> sources = new HashSet<>(Set.of());
        Set<Node> targets = new HashSet<>(Set.of());
        for(Node node : graph.vertexSet()){
            sources.add(node);
            targets.add(node);
        }
        ManyToManyShortestPathsAlgorithm.ManyToManyShortestPaths<Node, DefaultWeightedEdge> paths = manyToMany.getManyToManyPaths(sources, targets);

        double numShortestPath = 0;
        Map<Node, Integer> count = new HashMap<>();
        for(Node source : sources){
            for(Node target : targets){
                if(source != target && paths.getWeight(source, target) > pathWeightLimit){
                    numShortestPath++;
                    for(Node vertex : paths.getPath(source, target).getVertexList()){
                        //Update vertex count
                        count.put(vertex, count.getOrDefault(vertex, 0) + 1);
                    }
                }
            }
        }
        final double shortPathTemp = numShortestPath;
        count.entrySet().stream()
                .sorted(Map.Entry.<Node, Integer>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getNodeNumber() + ": " + e.getValue()/shortPathTemp));
    }
}
