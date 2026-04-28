//package org.example;
//
//import org.jgrapht.Graph;
//import org.jgrapht.Graphs;
//import org.jgrapht.alg.interfaces.ManyToManyShortestPathsAlgorithm;
//import org.jgrapht.alg.scoring.BetweennessCentrality;
//import org.jgrapht.alg.shortestpath.DijkstraManyToManyShortestPaths;
//import org.jgrapht.graph.DefaultWeightedEdge;
//import org.jgrapht.graph.SimpleWeightedGraph;
//
//import java.util.*;
//
//public class BC {
//    static Graph<Node, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
//
//    static Node MUC = new Node("MUC");
//    static Node YVR = new Node("YVR");
//    static Node CDG = new Node("CDG");
//    static Node HND = new Node("HND");
//    static Node TPE = new Node("TPE");
//    static Node CAI = new Node("CAI");
//    static Node DEN = new Node("DEN");
//    static Node MNL = new Node("MNL");
//    static Node IST = new Node("IST");
//    static Node LAX = new Node("LAX");
//    static Node ICN = new Node("ICN");
//    static Node JFK = new Node("JFK");
//    static Node OSL = new Node("OSL");
//    static Node DUB = new Node("DUB");
//    static Node SIN = new Node("SIN");
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
//
//    public static void ConstraintB1C1(double edgeCostLimit) {
//        /* ConstraintB1C1: Find the busiest airport that has an edge less than edgePriceLimit */ /* NEED TO VERIFY */
//
//        Graph<Node, DefaultWeightedEdge> graphB1 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
//        Graphs.addGraph(graphB1, graph);
//
//        //Note: Custom limit for edge
//
//        Set<Node> remove = new HashSet<>();
//        for (Node nodeX : new ArrayList<>(graphB1.vertexSet())) {
//            for (DefaultWeightedEdge edgeX : graphB1.edgesOf(nodeX)) {
//                if (graphB1.getEdgeWeight(edgeX) <= edgeCostLimit) {
//                    remove.add(nodeX);
//                }
//            }
//        }
//        remove.forEach(graphB1::removeVertex);
//
//        int highestEdgeCt = 0;
//        Node highestNode = null;
//        for (Node nodeX : graphB1.vertexSet()) {
//            if (graphB1.degreeOf(nodeX) >= highestEdgeCt) {
//                highestNode = nodeX;
//                highestEdgeCt = graphB1.degreeOf(nodeX);
//            }
//        }
//
//        BetweennessCentrality<Node, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphB1, true);
//        Map<Node, Double> scores = bc.getScores();
//        System.out.println("BC score of " + highestNode.getNodeNumber() + " -> " + scores.get(highestNode));
//    }
//
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
//
//    public static void ConstraintB2C1() {
//        /* ConstraintB2C1: Finding the airport with the highest amount of edges w/ no path costing higher than pathCostLimit*/ /* NEED TO VERIFY */
//        //Note: Custom limit for path cost
//        double pathCostLimit = 400;
//
//        //Note: Custom sources and targets
//        DijkstraManyToManyShortestPaths<Node, DefaultWeightedEdge> manyToMany = new DijkstraManyToManyShortestPaths<>(graph);
//        Set<Node> sources = Set.of(MUC, YVR, CDG, HND, TPE, CAI, DEN, MNL, IST, LAX, ICN, JFK, OSL, DUB, SIN);
//        Set<Node> targets = Set.of(MUC, YVR, CDG, HND, TPE, CAI, DEN, MNL, IST, LAX, ICN, JFK, OSL, DUB, SIN);
//        ManyToManyShortestPathsAlgorithm.ManyToManyShortestPaths<Node, DefaultWeightedEdge> paths = manyToMany.getManyToManyPaths(sources, targets);
//
//        double numShortestPath = 0;
//        Map<Node, Integer> count = new HashMap<>();
//        for (Node source : sources) {
//            for (Node target : targets) {
//                //Can you find the top-10 busiest airports given there’s no connecting ticket price higher than pathCostLimit
//                if (source != target && paths.getWeight(source, target) <= pathCostLimit) {
//                    numShortestPath++;
//                    for (Node vertex : paths.getPath(source, target).getVertexList()) {
//                        //Update vertex count
//                        count.put(vertex, count.getOrDefault(vertex, 0) + 1);
//                    }
//                }
//            }
//        }
//
//        int highestEdgeCt = 0;
//        Node highestNode = null;
//        for (Node nodeX : graph.vertexSet()) {
//            if (graph.degreeOf(nodeX) >= highestEdgeCt) {
//                highestNode = nodeX;
//                highestEdgeCt = graph.degreeOf(nodeX);
//            }
//        }
//
//        final double shortPathTemp = numShortestPath;
//        final Node finHighNode = highestNode;
//        count.entrySet().stream()
//                .sorted(Map.Entry.<Node, Integer>comparingByValue().reversed())
//                .forEach(e -> {
//                            if (e.getKey().equals(finHighNode)) {
//                                System.out.println(e.getKey().getNodeNumber() + ": " + e.getValue() / shortPathTemp);
//                            }
//                        }
//
//                );
//    }
//
//    public static void ConstraintB2C2() {
////Note: Custom limit for path cost
//        double pathCostLimit = 400;
//
//        //Note: Custom sources and targets
//        DijkstraManyToManyShortestPaths<Node, DefaultWeightedEdge> manyToMany = new DijkstraManyToManyShortestPaths<>(graph);
//        Set<Node> sources = Set.of(MUC, YVR, CDG, HND, TPE, CAI, DEN, MNL, IST, LAX, ICN, JFK, OSL, DUB, SIN);
//        Set<Node> targets = Set.of(MUC, YVR, CDG, HND, TPE, CAI, DEN, MNL, IST, LAX, ICN, JFK, OSL, DUB, SIN);
//        ManyToManyShortestPathsAlgorithm.ManyToManyShortestPaths<Node, DefaultWeightedEdge> paths = manyToMany.getManyToManyPaths(sources, targets);
//
//        double numShortestPath = 0;
//        Map<Node, Integer> count = new HashMap<>();
//        for (Node source : sources) {
//            for (Node target : targets) {
//                //Can you find the top-10 busiest airports given there’s no connecting ticket price higher than pathCostLimit
//                if (source != target && paths.getWeight(source, target) <= pathCostLimit) {
//                    numShortestPath++;
//                    for (Node vertex : paths.getPath(source, target).getVertexList()) {
//                        //Update vertex count
//                        count.put(vertex, count.getOrDefault(vertex, 0) + 1);
//                    }
//                }
//            }
//        }
//
//        final double shortPathTemp = numShortestPath;
//        count.entrySet().stream()
//                .sorted(Map.Entry.<Node, Integer>comparingByValue().reversed())
//                .forEach(e -> {
//                            if (graph.degreeOf(e.getKey()) < 2) {
//                                System.out.println(e.getKey().getNodeNumber() + ": " + e.getValue() / shortPathTemp);
//                            }
//                        }
//
//                );
//    }
//
//}
