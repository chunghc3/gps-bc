package org.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ManyToManyShortestPathsAlgorithm;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.shortestpath.DijkstraManyToManyShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class B {
    static Graph<NodeAirport, DefaultWeightedEdge> graphB = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

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
        graphB.addVertex(MUC);
        graphB.addVertex(YVR);
        graphB.addVertex(CDG);
        graphB.addVertex(HND);
        graphB.addVertex(TPE);
        graphB.addVertex(CAI);
        graphB.addVertex(DEN);
        graphB.addVertex(MNL);
        graphB.addVertex(IST);
        graphB.addVertex(LAX);
        graphB.addVertex(ICN);
        graphB.addVertex(JFK);
        graphB.addVertex(OSL);
        graphB.addVertex(DUB);
        graphB.addVertex(SIN);

        Graphs.addEdge(graphB,MUC,HND,60);
        Graphs.addEdge(graphB,MUC,OSL,100);
        Graphs.addEdge(graphB,YVR,SIN,230);
        Graphs.addEdge(graphB,YVR,TPE,500);
        Graphs.addEdge(graphB,YVR,IST,160);
        Graphs.addEdge(graphB,CDG,HND,130);
        Graphs.addEdge(graphB,CDG,MNL,130);
        Graphs.addEdge(graphB,HND,TPE,90);
        Graphs.addEdge(graphB,TPE,JFK,320);
        Graphs.addEdge(graphB,TPE,LAX,240);
        Graphs.addEdge(graphB,CDG,HND,130);
        Graphs.addEdge(graphB,CAI,DEN,310);
        Graphs.addEdge(graphB,CAI,DUB,80);
        Graphs.addEdge(graphB,CAI,ICN,110);
        Graphs.addEdge(graphB,ICN,DEN,220);
        Graphs.addEdge(graphB,DEN,OSL,320);
        Graphs.addEdge(graphB,LAX,ICN,430);
        Graphs.addEdge(graphB,LAX,OSL,270);
        Graphs.addEdge(graphB,LAX,IST,290);
        Graphs.addEdge(graphB,IST,DUB,180);
        Graphs.addEdge(graphB,LAX,DUB,190);
        Graphs.addEdge(graphB,JFK,OSL,300);
        Graphs.addEdge(graphB,OSL,SIN,310);
    }

    public static void ConstraintB1(){
        /* Constrint B, Type 1: Weight Restriction on EDGE*/

        double pathPriceLimit = 1000;

        //For each node in graphB1
        for(NodeAirport node : new ArrayList<>(graphB.vertexSet())) {
            //For each edge in node
            for (DefaultWeightedEdge edge : graphB.edgesOf(node)) {
                //If edge weight is greater than pathPriceLimit
                if (graphB.getEdgeWeight(edge) > pathPriceLimit) {
                    //Remove the node
                    graphB.removeVertex(node);
                    break;
                }
            }
        }

        BetweennessCentrality<NodeAirport, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphB, true);
        Map<NodeAirport, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<NodeAirport, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getAirportName() + ": " + e.getValue()));
    }

    public static void ConstraintB2(){
        /* Constraint B, Type 2: Weight Restriction on PATH */

        double pathCostLimit = 400;

        DijkstraManyToManyShortestPaths<NodeAirport, DefaultWeightedEdge> manyToMany = new DijkstraManyToManyShortestPaths<>(graphB);
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
