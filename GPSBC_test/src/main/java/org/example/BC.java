package org.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ManyToManyShortestPathsAlgorithm;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.shortestpath.DijkstraManyToManyShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.w3c.dom.Node;

import java.util.*;

public class BC {
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

    public static void ConstraintB1C1() {
        /* ConstraintB1C1: Find the busiest airport that has an edge less than edgePriceLimit */ /* NEED TO VERIFY */

        Graph<NodeAirport, DefaultWeightedEdge> graphB1 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphB1, graph);

        //Note: Custom limit for edge
        double edgePriceLimit = 430;

        Set<NodeAirport> remove = new HashSet<>();
        for (NodeAirport nodeX : new ArrayList<>(graphB1.vertexSet())) {
            boolean markDelete = false;
            for (DefaultWeightedEdge edgeX : graphB1.edgesOf(nodeX)) {
                if (graphB1.getEdgeWeight(edgeX) > edgePriceLimit) {
                    remove.add(nodeX);
                }
            }
        }
        remove.forEach(graphB1::removeVertex);

        int highestEdgeCt = 0;
        NodeAirport highestNode = null;
        for (NodeAirport nodeX : graphB1.vertexSet()) {
            if (graphB1.degreeOf(nodeX) >= highestEdgeCt) {
                highestNode = nodeX;
                highestEdgeCt = graphB1.degreeOf(nodeX);
            }
        }

        BetweennessCentrality<NodeAirport, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphB1, true);
        Map<NodeAirport, Double> scores = bc.getScores();
        System.out.println("BC score of " + highestNode.getAirportName() + " -> " + scores.get(highestNode));
    }

    public static void ConstraintB1C2() {
        /* NEED TO VERIFY */
        /* ConstraintB1C2: No edge weight greater than <?> */

        Graph<NodeAirport, DefaultWeightedEdge> graphB1 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphB1, graph);

        Graph<NodeAirport, DefaultWeightedEdge> test = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(test, graph);

        //Note: Custom limit for edge
        double edgePriceLimit = 430;
        int edgeNumberLimit = 3;


        Set<NodeAirport> remove = new HashSet<>();
        for (NodeAirport nodeX : new ArrayList<>(graphB1.vertexSet())) {
            boolean markDelete = false;
            for (DefaultWeightedEdge edgeX : graphB1.edgesOf(nodeX)) {
                if (graphB1.getEdgeWeight(edgeX) > edgePriceLimit && graphB1.degreeOf(nodeX) > edgeNumberLimit) {
                    remove.add(nodeX);
                }
            }
        }
        remove.forEach(graphB1::removeVertex);

//        for(NodeAirport nodeX : graphB1.vertexSet()){
//            System.out.print(nodeX.getAirportName() + " ");
//        }
//        System.out.println();

        BetweennessCentrality<NodeAirport, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphB1, true);
        Map<NodeAirport, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<NodeAirport, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getAirportName() + ": " + e.getValue()));

    }

    public static void ConstraintB2C1() {
        /* ConstraintB2C1: Finding the airport with the highest amount of edges w/ no path costing higher than pathCostLimit*/ /* NEED TO VERIFY */
        //Note: Custom limit for path cost
        double pathCostLimit = 400;

        //Note: Custom sources and targets
        DijkstraManyToManyShortestPaths<NodeAirport, DefaultWeightedEdge> manyToMany = new DijkstraManyToManyShortestPaths<>(graph);
        Set<NodeAirport> sources = Set.of(MUC, YVR, CDG, HND, TPE, CAI, DEN, MNL, IST, LAX, ICN, JFK, OSL, DUB, SIN);
        Set<NodeAirport> targets = Set.of(MUC, YVR, CDG, HND, TPE, CAI, DEN, MNL, IST, LAX, ICN, JFK, OSL, DUB, SIN);
        ManyToManyShortestPathsAlgorithm.ManyToManyShortestPaths<NodeAirport, DefaultWeightedEdge> paths = manyToMany.getManyToManyPaths(sources, targets);

        double numShortestPath = 0;
        Map<NodeAirport, Integer> count = new HashMap<>();
        for (NodeAirport source : sources) {
            for (NodeAirport target : targets) {
                //Can you find the top-10 busiest airports given there’s no connecting ticket price higher than pathCostLimit
                if (source != target && paths.getWeight(source, target) <= pathCostLimit) {
                    numShortestPath++;
                    for (NodeAirport vertex : paths.getPath(source, target).getVertexList()) {
                        //Update vertex count
                        count.put(vertex, count.getOrDefault(vertex, 0) + 1);
                    }
                }
            }
        }

        int highestEdgeCt = 0;
        NodeAirport highestNode = null;
        for (NodeAirport nodeX : graph.vertexSet()) {
            if (graph.degreeOf(nodeX) >= highestEdgeCt) {
                highestNode = nodeX;
                highestEdgeCt = graph.degreeOf(nodeX);
            }
        }

        final double shortPathTemp = numShortestPath;
        final NodeAirport finHighNode = highestNode;
        count.entrySet().stream()
                .sorted(Map.Entry.<NodeAirport, Integer>comparingByValue().reversed())
                .forEach(e -> {
                            if (e.getKey().equals(finHighNode)) {
                                System.out.println(e.getKey().getAirportName() + ": " + e.getValue() / shortPathTemp);
                            }
                        }

                );
    }

    public static void ConstraintB2C2() {
//Note: Custom limit for path cost
        double pathCostLimit = 400;

        //Note: Custom sources and targets
        DijkstraManyToManyShortestPaths<NodeAirport, DefaultWeightedEdge> manyToMany = new DijkstraManyToManyShortestPaths<>(graph);
        Set<NodeAirport> sources = Set.of(MUC, YVR, CDG, HND, TPE, CAI, DEN, MNL, IST, LAX, ICN, JFK, OSL, DUB, SIN);
        Set<NodeAirport> targets = Set.of(MUC, YVR, CDG, HND, TPE, CAI, DEN, MNL, IST, LAX, ICN, JFK, OSL, DUB, SIN);
        ManyToManyShortestPathsAlgorithm.ManyToManyShortestPaths<NodeAirport, DefaultWeightedEdge> paths = manyToMany.getManyToManyPaths(sources, targets);

        double numShortestPath = 0;
        Map<NodeAirport, Integer> count = new HashMap<>();
        for (NodeAirport source : sources) {
            for (NodeAirport target : targets) {
                //Can you find the top-10 busiest airports given there’s no connecting ticket price higher than pathCostLimit
                if (source != target && paths.getWeight(source, target) <= pathCostLimit) {
                    numShortestPath++;
                    for (NodeAirport vertex : paths.getPath(source, target).getVertexList()) {
                        //Update vertex count
                        count.put(vertex, count.getOrDefault(vertex, 0) + 1);
                    }
                }
            }
        }

        final double shortPathTemp = numShortestPath;
        count.entrySet().stream()
                .sorted(Map.Entry.<NodeAirport, Integer>comparingByValue().reversed())
                .forEach(e -> {
                            if (graph.degreeOf(e.getKey()) > 2) {
                                System.out.println(e.getKey().getAirportName() + ": " + e.getValue() / shortPathTemp);
                            }
                        }

                );
    }

}
