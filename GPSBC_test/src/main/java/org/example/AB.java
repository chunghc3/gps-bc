package org.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ManyToManyShortestPathsAlgorithm;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.shortestpath.DijkstraManyToManyShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;

public class AB {

    public static void ConstraintA1B1(Graph<Node, DefaultWeightedEdge> graph, int specifiedZone, double edgeWeightLimit) {
        /* ConstraintA1B1: Find the most important airport in a specifiedZone with edges that cost less than edgePriceLimit */ /* Completed: Need to verify */
        Graph<Node, DefaultWeightedEdge> graphAB = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphAB, graph);

        //A1
        for (Node node : new ArrayList<>(graphAB.vertexSet())) {
            if (node.getZone() != specifiedZone) {
               graphAB.removeVertex(node);
            }
        }

        //B1
        Set<DefaultWeightedEdge> remove = new HashSet<>();
        for(Node node : new ArrayList<>(graphAB.vertexSet())){
            //For each edge in graphB1
            for(DefaultWeightedEdge edge: graphAB.edgesOf(node)){
                //If the cost of the edge is greater than edgePriceLimit
                if (graphAB.getEdgeWeight(edge) < edgeWeightLimit) {
                    remove.add(edge);
                }
            }
        }
        remove.forEach(graphAB::removeEdge);

        BetweennessCentrality<Node, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphAB, true);
        Map<Node, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<Node, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getNodeNumber() + ", Zone: " + e.getKey().getZone() + ": " + (e.getValue()*2) ));
    }

    public static void ConstraintA1B2(Graph<Node, DefaultWeightedEdge> graph, int specifiedZone, double pathCostLimit){
        /* Constraint A1B2: Find the most important airport with a path cost less than pathCostLimit in a specifiedZone */
        Graph<Node, DefaultWeightedEdge> graphAB = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphAB, graph);

        DijkstraManyToManyShortestPaths<Node, DefaultWeightedEdge> manyToMany = new DijkstraManyToManyShortestPaths<>(graphAB);
        Set<Node> sources = new java.util.HashSet<>(Set.of());
        Set<Node> targets = new java.util.HashSet<>(Set.of());

        for(Node node : new ArrayList<>(graphAB.vertexSet())){
            sources.add(node);
            targets.add(node);
        }

        for(Node nodeX : graph.vertexSet()){
            if(nodeX.getZone() != specifiedZone){
                sources.remove(nodeX);
                targets.remove(nodeX);
                graphAB.removeVertex(nodeX);
            }
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
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(count::get));
        for (Map.Entry<Node, Integer> entry : count.entrySet()) {
            pq.offer(entry.getKey()); // Add element
            if (pq.size() > 10) {
                pq.poll();
            }
        }
        while(!pq.isEmpty()){
            Node node = pq.poll();
            System.out.println(node.getNodeNumber() + " -> " + (count.get(node)/shortPathTemp));
        }
    }


}



