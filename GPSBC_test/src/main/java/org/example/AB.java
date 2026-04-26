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
    //Constraints: Color <zone> + Edge weight restriction
//    static Graph<Node, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
//
//    static Node MUC = new Node("MUC", 3);
//    static Node YVR = new Node("YVR", 2);
//    static Node CDG = new Node("CDG", 3);
//    static Node HND = new Node("HND", 2);
//    static Node TPE = new Node("TPE", 3);
//    static Node CAI = new Node("CAI", 1);
//    static Node DEN = new Node("DEN", 2);
//    static Node MNL = new Node("MNL", 2);
//    static Node IST = new Node("IST", 3);
//    static Node LAX = new Node("LAX", 1);
//    static Node ICN = new Node("ICN", 3);
//    static Node JFK = new Node("JFK", 1);
//    static Node OSL = new Node("OSL", 3);
//    static Node DUB = new Node("DUB", 3);
//    static Node SIN = new Node("SIN", 3);
//
//    static {
//        graph.addVertex(MUC);
//        graph.addVertex(YVR);
//        graph.addVertex(CDG);
//        graph.addVertex(HND);
//        graph.addVertex(TPE);
//        graph.addVertex(CAI);
//        graph.addVertex(DEN);
//        graph.addVertex(MNL);
//        graph.addVertex(IST);
//        graph.addVertex(LAX);
//        graph.addVertex(ICN);
//        graph.addVertex(JFK);
//        graph.addVertex(OSL);
//        graph.addVertex(DUB);
//        graph.addVertex(SIN);
//
//        Graphs.addEdge(graph, MUC, HND, 60);
//        Graphs.addEdge(graph, MUC, OSL, 100);
//        Graphs.addEdge(graph, YVR, SIN, 230);
//        Graphs.addEdge(graph, YVR, TPE, 500);
//        Graphs.addEdge(graph, YVR, IST, 160);
//        Graphs.addEdge(graph, CDG, HND, 130);
//        Graphs.addEdge(graph, CDG, MNL, 130);
//        Graphs.addEdge(graph, HND, TPE, 90);
//        Graphs.addEdge(graph, TPE, JFK, 320);
//        Graphs.addEdge(graph, TPE, LAX, 240);
//        Graphs.addEdge(graph, CDG, HND, 130);
//        Graphs.addEdge(graph, CAI, DEN, 310);
//        Graphs.addEdge(graph, CAI, DUB, 80);
//        Graphs.addEdge(graph, CAI, ICN, 110);
//        Graphs.addEdge(graph, ICN, DEN, 220);
//        Graphs.addEdge(graph, DEN, OSL, 320);
//        Graphs.addEdge(graph, LAX, ICN, 430);
//        Graphs.addEdge(graph, LAX, OSL, 270);
//        Graphs.addEdge(graph, LAX, IST, 290);
//        Graphs.addEdge(graph, IST, DUB, 180);
//        Graphs.addEdge(graph, LAX, DUB, 190);
//        Graphs.addEdge(graph, JFK, OSL, 300);
//        Graphs.addEdge(graph, OSL, SIN, 310);
//    }

    public static void ConstraintA1B1(Graph<Node, DefaultWeightedEdge> graph) {
        /* ConstraintA1B1: Find the most important airport in a specifiedZone with edges that cost less than edgePriceLimit */ /* Completed: Need to verify */
        Graph<Node, DefaultWeightedEdge> graphAB = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphAB, graph);
        double edgePriceLimit = 500;
        int specifiedZone = 3;

        Set<Node> remove = new HashSet<>();
        //For each node in graphA1B1
        for (Node node : new ArrayList<>(graphAB.vertexSet())) {
            //If the node is in the specified Zone
            if (node.getZone() == specifiedZone) {
                //For each edge in node
                for (DefaultWeightedEdge edge : graphAB.edgesOf(node)) {
                    //If edge weight is greater than pathPriceLimit
                    if (graphAB.getEdgeWeight(edge) <= edgePriceLimit) {
                        //Remove the node
                        remove.add(node);
                        break;
                    }
                }
            } else {
                remove.add(node);
            }
        }
        remove.forEach(graphAB::removeVertex);
        BetweennessCentrality<Node, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphAB, true);
        Map<Node, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<Node, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getNodeNumber() + ", Zone: " + e.getKey().getZone() + ": " + e.getValue()));
    }

    public static void ConstraintA1B2(Graph<Node, DefaultWeightedEdge> graph){
        /* INCOMPLETE */
        /* Constraint A1B2: Find the most important airport with a path cost less than pathCostLimit in a specifiedZone */
        Graph<Node, DefaultWeightedEdge> graphAB = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphAB, graph);

        double pathCostLimit = 400;
        int specifiedZone = 3;

        //Note: Custom sources and targets
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
                //Can you find the top-10 busiest airports given there’s no connecting ticket price higher than pathCostLimit
                if(source != target && paths.getWeight(source, target) <= pathCostLimit){
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



