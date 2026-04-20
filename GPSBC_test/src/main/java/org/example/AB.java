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

public class AB {
    //Constraints: Color <zone> + Edge weight restriction
    static Graph<NodeAirport, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

    static NodeAirport MUC = new NodeAirport("MUC", 3);
    static NodeAirport YVR = new NodeAirport("YVR", 2);
    static NodeAirport CDG = new NodeAirport("CDG", 3);
    static NodeAirport HND = new NodeAirport("HND", 2);
    static NodeAirport TPE = new NodeAirport("TPE", 3);
    static NodeAirport CAI = new NodeAirport("CAI", 1);
    static NodeAirport DEN = new NodeAirport("DEN", 2);
    static NodeAirport MNL = new NodeAirport("MNL", 2);
    static NodeAirport IST = new NodeAirport("IST", 3);
    static NodeAirport LAX = new NodeAirport("LAX", 1);
    static NodeAirport ICN = new NodeAirport("ICN", 3);
    static NodeAirport JFK = new NodeAirport("JFK", 1);
    static NodeAirport OSL = new NodeAirport("OSL", 3);
    static NodeAirport DUB = new NodeAirport("DUB", 3);
    static NodeAirport SIN = new NodeAirport("SIN", 3);

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

    public static void ConstraintA1B1() {
        /* ConstraintA1B1: Find the most important airport in a specifiedZone with edges that cost less than edgePriceLimit */ /* Completed: Need to verify */
        Graph<NodeAirport, DefaultWeightedEdge> graphAB = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphAB, graph);
        double edgePriceLimit = 500;
        int specifiedZone = 3;

        Set<NodeAirport> remove = new HashSet<>();
        //For each node in graphA1B1
        for (NodeAirport node : new ArrayList<>(graphAB.vertexSet())) {
            //If the node is in the specified Zone
            if (node.getAirportZone() == specifiedZone) {
                //For each edge in node
                for (DefaultWeightedEdge edge : graphAB.edgesOf(node)) {
                    //If edge weight is greater than pathPriceLimit
                    if (graphAB.getEdgeWeight(edge) > edgePriceLimit) {
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
        BetweennessCentrality<NodeAirport, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphAB, true);
        Map<NodeAirport, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<NodeAirport, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getAirportName() + ", Zone: " + e.getKey().getAirportZone() + ": " + e.getValue()));
    }

    public static void ConstraintA1B2(){
        /* INCOMPLETE */
        /* Constraint A1B2: Find the most important airport with a path cost less than pathCostLimit in a specifiedZone */
        Graph<NodeAirport, DefaultWeightedEdge> graphAB = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphAB, graph);

        double pathCostLimit = 400;
        int specifiedZone = 3;

        //Note: Custom sources and targets
        DijkstraManyToManyShortestPaths<NodeAirport, DefaultWeightedEdge> manyToMany = new DijkstraManyToManyShortestPaths<>(graphAB);

        Set<NodeAirport> sources = new java.util.HashSet<>(Set.of(MUC, YVR, CDG, HND, TPE, CAI, DEN, MNL, IST, LAX, ICN, JFK, OSL, DUB, SIN));
        Set<NodeAirport> targets = new java.util.HashSet<>(Set.of(MUC, YVR, CDG, HND, TPE, CAI, DEN, MNL, IST, LAX, ICN, JFK, OSL, DUB, SIN));

        for(NodeAirport nodeX : graph.vertexSet()){
            if(nodeX.getAirportZone() != specifiedZone){
                sources.remove(nodeX);
                targets.remove(nodeX);
                graphAB.removeVertex(nodeX);
            }
        }

        ManyToManyShortestPathsAlgorithm.ManyToManyShortestPaths<NodeAirport, DefaultWeightedEdge> paths = manyToMany.getManyToManyPaths(sources, targets);

        double numShortestPath = 0;
        Map<NodeAirport, Integer> count = new HashMap<>();
        for(NodeAirport source : sources){
            for(NodeAirport target : targets){
                //Can you find the top-10 busiest airports given there’s no connecting ticket price higher than pathCostLimit
                if(source != target && paths.getWeight(source, target) <= pathCostLimit){
                    numShortestPath++;
                    for(NodeAirport vertex : paths.getPath(source, target).getVertexList()){
                        //Update vertex count
                        count.put(vertex, count.getOrDefault(vertex, 0) + 1);
                    }
                }
            }
        }

        final double shortPathTemp = numShortestPath;
        count.entrySet().stream()
                .sorted(Map.Entry.<NodeAirport, Integer>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getAirportName() + ": " + e.getValue()/shortPathTemp));
    }


}



