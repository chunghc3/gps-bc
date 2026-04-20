package org.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ManyToManyShortestPathsAlgorithm;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.shortestpath.DijkstraManyToManyShortestPaths;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ABC {
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

    public static void A1B1C1(int specifiedZone, double edgePriceLimit){
        Graph<NodeAirport, DefaultWeightedEdge> graphABC = new SimpleGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphABC, graph);

        //A1
        for (NodeAirport nodeX : graph.vertexSet()) {
            if (nodeX.getAirportZone() != specifiedZone) {
                graphABC.removeVertex(nodeX);
            }
        }

        //B1
        Set<NodeAirport> remove = new HashSet<>();
        for(DefaultWeightedEdge edge: graphABC.edgeSet()){
            //If the cost of the edge is greater than edgePriceLimit
            if (graphABC.getEdgeWeight(edge) > edgePriceLimit) {
                //Mark edge source node for removal
                remove.add(graphABC.getEdgeSource(edge));
                //Mark edge target node for removal
                remove.add(graphABC.getEdgeTarget(edge));
            }
        }
        remove.forEach(graphABC::removeVertex);

        //C1
        Set<NodeAirport> busiest = new HashSet<>();
        int busiestDeg = 0;
        for(NodeAirport node : graphABC.vertexSet()){
            if(graphABC.degreeOf(node) >= busiestDeg){
                busiest.add(node);
                busiestDeg = graphABC.degreeOf(node);
            }
        }
        for(NodeAirport node : graphABC.vertexSet()){
            for(NodeAirport busiestNode : busiest){
                if(node != busiestNode){
                    remove.add(node);
                }
            }
        }
        remove.forEach(graphABC::removeVertex);

        BetweennessCentrality<NodeAirport, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphABC, true);
        Map<NodeAirport, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<NodeAirport, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getAirportName() + ": " + e.getValue()));
    }

    public static void A1B1C2(int specifiedZone, double edgePriceLimit){
        Graph<NodeAirport, DefaultWeightedEdge> graphABC = new SimpleGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphABC, graph);

        //A1
        for (NodeAirport nodeX : graph.vertexSet()) {
            if (nodeX.getAirportZone() != specifiedZone) {
                graphABC.removeVertex(nodeX);
            }
        }

        //B1
        Set<NodeAirport> remove = new HashSet<>();
        for(DefaultWeightedEdge edge: graphABC.edgeSet()){
            //If the cost of the edge is greater than edgePriceLimit
            if (graphABC.getEdgeWeight(edge) > edgePriceLimit) {
                //Mark edge source node for removal
                remove.add(graphABC.getEdgeSource(edge));
                //Mark edge target node for removal
                remove.add(graphABC.getEdgeTarget(edge));
            }
        }
        remove.forEach(graphABC::removeVertex);

        //C2
        Set<NodeAirport> busiest = new HashSet<>();
        int busiestDeg = 0;
        for(NodeAirport node : graphABC.vertexSet()){
            //C2.1
            if(graphABC.degreeOf(node) > 3){
                remove.add(node);
            }
            //C2.2
//            if(graphABC.degreeOf(node) < 3){
//                remove.add(node);
//            }
        }
        remove.forEach(graphABC::removeVertex);

        BetweennessCentrality<NodeAirport, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphABC, true);
        Map<NodeAirport, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<NodeAirport, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getAirportName() + ": " + e.getValue()));
    }

    public static void A1B2C1(int specifiedZone, double edgePriceLimit, double pathCostLimit){
        Graph<NodeAirport, DefaultWeightedEdge> graphABC = new SimpleGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphABC, graph);

        //A1
        for (NodeAirport nodeX : graph.vertexSet()) {
            if (nodeX.getAirportZone() != specifiedZone) {
                graphABC.removeVertex(nodeX);
            }
        }

        //C1
        Set<NodeAirport> remove = new HashSet<>();
        Set<NodeAirport> busiest = new HashSet<>();
        int busiestDeg = 0;
        for(NodeAirport node : graphABC.vertexSet()){
            if(graphABC.degreeOf(node) >= busiestDeg){
                busiest.add(node);
                busiestDeg = graphABC.degreeOf(node);
            }
        }
        for(NodeAirport node : graphABC.vertexSet()){
            for(NodeAirport busiestNode : busiest){
                if(node != busiestNode){
                    remove.add(node);
                }
            }
        }
        remove.forEach(graphABC::removeVertex);

        //B2
        //Note: Custom sources and targets
        DijkstraManyToManyShortestPaths<NodeAirport, DefaultWeightedEdge> manyToMany = new DijkstraManyToManyShortestPaths<>(graphABC);
        Set<NodeAirport> sources = Set.of();
        Set<NodeAirport> targets = Set.of();

        for(NodeAirport node : graphABC.vertexSet()){
            sources.add(node); //Immutable object is modified?
            targets.add(node); //Immutable object is modified?
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

    public static void A1B2C2(int specifiedZone, double edgePriceLimit, double pathCostLimit){
        Graph<NodeAirport, DefaultWeightedEdge> graphABC = new SimpleGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphABC, graph);

        //A1
        for (NodeAirport nodeX : graph.vertexSet()) {
            if (nodeX.getAirportZone() != specifiedZone) {
                graphABC.removeVertex(nodeX);
            }
        }

        //C1
        Set<NodeAirport> busiest = new HashSet<>();
        Set<NodeAirport> remove = new HashSet<>();
        int busiestDeg = 0;
        for(NodeAirport node : graphABC.vertexSet()){
            //C2.1
            if(graphABC.degreeOf(node) > 3){
                remove.add(node);
            }
            //C2.2
//            if(graphABC.degreeOf(node) < 3){
//                remove.add(node);
//            }
        }
        remove.forEach(graphABC::removeVertex);

        //B2
        //Note: Custom sources and targets
        DijkstraManyToManyShortestPaths<NodeAirport, DefaultWeightedEdge> manyToMany = new DijkstraManyToManyShortestPaths<>(graphABC);
        Set<NodeAirport> sources = Set.of();
        Set<NodeAirport> targets = Set.of();

        for(NodeAirport node : graphABC.vertexSet()){
            sources.add(node); //Immutable object is modified?
            targets.add(node); //Immutable object is modified?
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
