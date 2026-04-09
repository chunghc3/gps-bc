package org.example;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ManyToManyShortestPathsAlgorithm;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.shortestpath.DijkstraManyToManyShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;

public class B {
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

    static{
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

        Graphs.addEdge(graph,MUC,HND,60);
        Graphs.addEdge(graph,MUC,OSL,100);
        Graphs.addEdge(graph,YVR,SIN,230);
        Graphs.addEdge(graph,YVR,TPE,500);
        Graphs.addEdge(graph,YVR,IST,160);
        Graphs.addEdge(graph,CDG,HND,130);
        Graphs.addEdge(graph,CDG,MNL,130);
        Graphs.addEdge(graph,HND,TPE,90);
        Graphs.addEdge(graph,TPE,JFK,320);
        Graphs.addEdge(graph,TPE,LAX,240);
        Graphs.addEdge(graph,CDG,HND,130);
        Graphs.addEdge(graph,CAI,DEN,310);
        Graphs.addEdge(graph,CAI,DUB,80);
        Graphs.addEdge(graph,CAI,ICN,110);
        Graphs.addEdge(graph,ICN,DEN,220);
        Graphs.addEdge(graph,DEN,OSL,320);
        Graphs.addEdge(graph,LAX,ICN,430);
        Graphs.addEdge(graph,LAX,OSL,270);
        Graphs.addEdge(graph,LAX,IST,290);
        Graphs.addEdge(graph,IST,DUB,180);
        Graphs.addEdge(graph,LAX,DUB,190);
        Graphs.addEdge(graph,JFK,OSL,300);
        Graphs.addEdge(graph,OSL,SIN,310);
    }

    public static void ConstraintB1(){
        /* Constraint B, Type 1: Weight Restriction on EDGE*/ /* Completed: edge removal verified*/
        Graph<NodeAirport, DefaultWeightedEdge> graphB1 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphB1, graph);

        //Note: Custom limit for edge
        double edgePriceLimit = 500;

        //Set to target nodes for removal
        Set<NodeAirport> remove = new HashSet<>();

        //For each edge in graphB1
        for(DefaultWeightedEdge edge: graphB1.edgeSet()){
            //If the cost of the edge is greater than edgePriceLimit
            if (graphB1.getEdgeWeight(edge) > edgePriceLimit) {
                //Mark edge source node for removal
                remove.add(graphB1.getEdgeSource(edge));
                //Mark edge target node for removal
                remove.add(graphB1.getEdgeTarget(edge));
            }
        }
        remove.forEach(graphB1::removeVertex);

        BetweennessCentrality<NodeAirport, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphB1, true);
        Map<NodeAirport, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<NodeAirport, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getAirportName() + ": " + e.getValue()));
    }

    public static void ConstraintB2(){
        /* Constraint B, Type 2: Weight Restriction on PATH */ /* Completed */

        //Note: Custom limit for path cost
        double pathCostLimit = 400;

        //Note: Custom sources and targets
        DijkstraManyToManyShortestPaths<NodeAirport, DefaultWeightedEdge> manyToMany = new DijkstraManyToManyShortestPaths<>(graph);
        Set<NodeAirport> sources = Set.of(MUC, YVR, CDG, HND, TPE, CAI, DEN, MNL, IST, LAX, ICN, JFK, OSL, DUB, SIN);
        Set<NodeAirport> targets = Set.of(MUC, YVR, CDG, HND, TPE, CAI, DEN, MNL, IST, LAX, ICN, JFK, OSL, DUB, SIN);
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
