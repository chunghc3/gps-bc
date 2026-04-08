package org.example;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ManyToManyShortestPathsAlgorithm;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.shortestpath.DijkstraManyToManyShortestPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
        public static void main(String[] args) {
            ConstraintC2();
        }

        public static void ConstraintA(){
            //Constraint A: Type 1
//            Graph<NodeA, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
//            NodeA A = new NodeA("A","red");
//            NodeA B = new NodeA("B","blue");
//            NodeA C = new NodeA("C","red");
//            NodeA D = new NodeA("D","green");
//            NodeA E = new NodeA("E","red");
//            NodeA F = new NodeA("F","blue");
//            NodeA G = new NodeA("G","blue");
//            NodeA H = new NodeA("H","red");
//            NodeA I = new NodeA("I","green");
//            NodeA J = new NodeA("J","red");
//            NodeA K = new NodeA("K","blue");
//            NodeA L = new NodeA("L","green");
//
//            graph.addVertex(A);
//            graph.addVertex(B);
//            graph.addVertex(C);
//            graph.addVertex(D);
//            graph.addVertex(E);
//            graph.addVertex(F);
//            graph.addVertex(G);
//            graph.addVertex(H);
//            graph.addVertex(I);
//            graph.addVertex(J);
//            graph.addVertex(K);
//            graph.addVertex(L);
//
//            graph.addEdge(A, B);
//            graph.addEdge(A, C);
//            graph.addEdge(B, D);
//            graph.addEdge(C, D);
//            graph.addEdge(D, E);
//            graph.addEdge(D, F);
//            graph.addEdge(E, G);
//            graph.addEdge(F, G);
//            graph.addEdge(G, H);
//            graph.addEdge(H, I);
//            graph.addEdge(H, J);
//            graph.addEdge(I, K);
//            graph.addEdge(J, K);
//            graph.addEdge(K, L);
//            graph.addEdge(L, A);
//
//            graph.addEdge(D, L);
//            graph.addEdge(L, I);
//            graph.addEdge(I, L);
//            graph.addEdge(I, L);
//            graph.addEdge(L, D);

            //Constraint A: Type 2
            Graph<NodeA, DefaultEdge> graph2 = new SimpleGraph<>(DefaultEdge.class);
            NodeA A1 = new NodeA("A","red");
            NodeA B1 = new NodeA("B","red");
            NodeA C1 = new NodeA("C","green");
            NodeA D1 = new NodeA("D","green");

            graph2.addVertex(A1);
            graph2.addVertex(B1);
            graph2.addVertex(C1);
            graph2.addVertex(D1);
            graph2.addEdge(A1, C1);
            graph2.addEdge(C1, B1);
            graph2.addEdge(B1, D1);

            //First sort color
            Set<NodeA> filteredNodes = graph2.vertexSet().stream()
                    .filter(n -> n.getColor() == "green")
                    .collect(Collectors.toSet());

            //Post filtering
            Graph<NodeA, DefaultEdge> subgraph = new AsSubgraph<>(graph2, filteredNodes);
            Graph<NodeA, DefaultEdge> wholeGraph = new AsSubgraph<>(graph2, graph2.vertexSet());

            BetweennessCentrality<NodeA, DefaultEdge> alg = new BetweennessCentrality<>(subgraph);

            Map<NodeA, Double> scores = alg.getScores();
            scores.forEach(
                    (node, score) -> {
                        if (node.getColor() == "green") {
                            System.out.println(node.getName() + " -> " + score);
                        }
                    }
            );

            NodeA mostCentral = scores.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            System.out.println("Most central: " + mostCentral.getName());
        }

        public static void ConstraintB1(){
            Graph<NodeB, DefaultWeightedEdge> graphB1 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

            //Graph B
            NodeB MUC = new NodeB("MUC");
            NodeB YVR = new NodeB("YVR");
            NodeB CDG = new NodeB("CDG");
            NodeB HND = new NodeB("HND");
            NodeB TPE = new NodeB("TPE");
            NodeB CAI = new NodeB("CAI");
            NodeB DEN = new NodeB("DEN");
            NodeB MNL = new NodeB("MNL");
            NodeB IST = new NodeB("IST");
            NodeB LAX = new NodeB("LAX");
            NodeB ICN = new NodeB("ICN");
            NodeB JFK = new NodeB("JFK");
            NodeB OSL = new NodeB("OSL");
            NodeB DUB = new NodeB("DUB");
            NodeB SIN = new NodeB("SIN");

            graphB1.addVertex(MUC);
            graphB1.addVertex(YVR);
            graphB1.addVertex(CDG);
            graphB1.addVertex(HND);
            graphB1.addVertex(TPE);
            graphB1.addVertex(CAI);
            graphB1.addVertex(DEN);
            graphB1.addVertex(MNL);
            graphB1.addVertex(IST);
            graphB1.addVertex(LAX);
            graphB1.addVertex(ICN);
            graphB1.addVertex(JFK);
            graphB1.addVertex(OSL);
            graphB1.addVertex(DUB);
            graphB1.addVertex(SIN);

            Graphs.addEdge(graphB1,MUC,HND,60);
            Graphs.addEdge(graphB1,MUC,OSL,100);
            Graphs.addEdge(graphB1,YVR,SIN,230);
            Graphs.addEdge(graphB1,YVR,TPE,500);
            Graphs.addEdge(graphB1,YVR,IST,160);
            Graphs.addEdge(graphB1,CDG,HND,130);
            Graphs.addEdge(graphB1,CDG,MNL,130);
            Graphs.addEdge(graphB1,HND,TPE,90);
            Graphs.addEdge(graphB1,TPE,JFK,320);
            Graphs.addEdge(graphB1,TPE,LAX,240);
            Graphs.addEdge(graphB1,CDG,HND,130);
            Graphs.addEdge(graphB1,CAI,DEN,310);
            Graphs.addEdge(graphB1,CAI,DUB,80);
            Graphs.addEdge(graphB1,CAI,ICN,110);
            Graphs.addEdge(graphB1,ICN,DEN,220);
            Graphs.addEdge(graphB1,DEN,OSL,320);
            Graphs.addEdge(graphB1,LAX,ICN,430);
            Graphs.addEdge(graphB1,LAX,OSL,270);
            Graphs.addEdge(graphB1,LAX,IST,290);
            Graphs.addEdge(graphB1,IST,DUB,180);
            Graphs.addEdge(graphB1,LAX,DUB,190);
            Graphs.addEdge(graphB1,JFK,OSL,300);
            Graphs.addEdge(graphB1,OSL,SIN,310);

            //ConstraintB1
            double pathPriceLimit = 1000;

            //For each node in graphB1
            for(NodeB node : new ArrayList<>(graphB1.vertexSet())) {
                //For each edge in node
                for (DefaultWeightedEdge edge : graphB1.edgesOf(node)) {
                    //If edge weight is greater than pathPriceLimit
                    if (graphB1.getEdgeWeight(edge) > pathPriceLimit) {
                        //Remove the node
                        graphB1.removeVertex(node);
                        break;
                    }
                }
            }
            //RUNBC?
        }

    public static void ConstraintB2(){
        Graph<NodeB, DefaultWeightedEdge> graphB2 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        //Graph B
        NodeB MUC = new NodeB("MUC");
        NodeB YVR = new NodeB("YVR");
        NodeB CDG = new NodeB("CDG");
        NodeB HND = new NodeB("HND");
        NodeB TPE = new NodeB("TPE");
        NodeB CAI = new NodeB("CAI");
        NodeB DEN = new NodeB("DEN");
        NodeB MNL = new NodeB("MNL");
        NodeB IST = new NodeB("IST");
        NodeB LAX = new NodeB("LAX");
        NodeB ICN = new NodeB("ICN");
        NodeB JFK = new NodeB("JFK");
        NodeB OSL = new NodeB("OSL");
        NodeB DUB = new NodeB("DUB");
        NodeB SIN = new NodeB("SIN");

        graphB2.addVertex(MUC);
        graphB2.addVertex(YVR);
        graphB2.addVertex(CDG);
        graphB2.addVertex(HND);
        graphB2.addVertex(TPE);
        graphB2.addVertex(CAI);
        graphB2.addVertex(DEN);
        graphB2.addVertex(MNL);
        graphB2.addVertex(IST);
        graphB2.addVertex(LAX);
        graphB2.addVertex(ICN);
        graphB2.addVertex(JFK);
        graphB2.addVertex(OSL);
        graphB2.addVertex(DUB);
        graphB2.addVertex(SIN);

        Graphs.addEdge(graphB2,MUC,HND,60);
        Graphs.addEdge(graphB2,MUC,OSL,100);
        Graphs.addEdge(graphB2,YVR,SIN,230);
        Graphs.addEdge(graphB2,YVR,TPE,500);
        Graphs.addEdge(graphB2,YVR,IST,160);
        Graphs.addEdge(graphB2,CDG,HND,130);
        Graphs.addEdge(graphB2,CDG,MNL,130);
        Graphs.addEdge(graphB2,HND,TPE,90);
        Graphs.addEdge(graphB2,TPE,JFK,320);
        Graphs.addEdge(graphB2,TPE,LAX,240);
        Graphs.addEdge(graphB2,CDG,HND,130);
        Graphs.addEdge(graphB2,CAI,DEN,310);
        Graphs.addEdge(graphB2,CAI,DUB,80);
        Graphs.addEdge(graphB2,CAI,ICN,110);
        Graphs.addEdge(graphB2,ICN,DEN,220);
        Graphs.addEdge(graphB2,DEN,OSL,320);
        Graphs.addEdge(graphB2,LAX,ICN,430);
        Graphs.addEdge(graphB2,LAX,OSL,270);
        Graphs.addEdge(graphB2,LAX,IST,290);
        Graphs.addEdge(graphB2,IST,DUB,180);
        Graphs.addEdge(graphB2,LAX,DUB,190);
        Graphs.addEdge(graphB2,JFK,OSL,300);
        Graphs.addEdge(graphB2,OSL,SIN,310);

        double pathCostLimit = 400;

        //Djikstra
//        DijkstraShortestPath<NodeB, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graphB2);
//        GraphPath<NodeB, DefaultWeightedEdge> path = dijkstra.getPath(OSL, SIN);

        DijkstraManyToManyShortestPaths<NodeB, DefaultWeightedEdge> manyToMany = new DijkstraManyToManyShortestPaths<>(graphB2);
        Set<NodeB> sources = Set.of(MUC, YVR, CDG, HND, TPE, CAI, DEN, MNL, IST, LAX, ICN, JFK, OSL, DUB, SIN);
        Set<NodeB> targets = Set.of(MUC, YVR, CDG, HND, TPE, CAI, DEN, MNL, IST, LAX, ICN, JFK, OSL, DUB, SIN);
        ManyToManyShortestPathsAlgorithm.ManyToManyShortestPaths<NodeB, DefaultWeightedEdge> paths = manyToMany.getManyToManyPaths(sources, targets);

        double numShortestPath = 0;
        Map<NodeB, Integer> count = new HashMap<>();
        for(NodeB source : sources){
            for(NodeB target : targets){
                //Can you find the top-10 busiest airports given there’s no connecting ticket price higher than pathCostLimit
                if(source != target && paths.getWeight(source, target) <= pathCostLimit){
                    numShortestPath++;
                    for(NodeB vertex : paths.getPath(source, target).getVertexList()){
                        //Update vertex count
                        count.put(vertex, count.getOrDefault(vertex, 0) + 1);
                    }
                }
            }
        }

//        for(Map.Entry<NodeB, Integer> entry : count.entrySet()){
//            System.out.println(entry.getKey().getAirportName() + ": " + entry.getValue()/numShortestPath);
//        }

        final double shortPathTemp = numShortestPath;
        count.entrySet().stream()
                .sorted(Map.Entry.<NodeB, Integer>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getAirportName() + ": " + e.getValue()/shortPathTemp));
        }

        public static void ConstraintC1(){
            Graph<NodeB, DefaultWeightedEdge> graphC1 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
            //Graph B
            NodeB MUC = new NodeB("MUC");
            NodeB YVR = new NodeB("YVR");
            NodeB CDG = new NodeB("CDG");
            NodeB HND = new NodeB("HND");
            NodeB TPE = new NodeB("TPE");
            NodeB CAI = new NodeB("CAI");
            NodeB DEN = new NodeB("DEN");
            NodeB MNL = new NodeB("MNL");
            NodeB IST = new NodeB("IST");
            NodeB LAX = new NodeB("LAX");
            NodeB ICN = new NodeB("ICN");
            NodeB JFK = new NodeB("JFK");
            NodeB OSL = new NodeB("OSL");
            NodeB DUB = new NodeB("DUB");
            NodeB SIN = new NodeB("SIN");

            graphC1.addVertex(MUC);
            graphC1.addVertex(YVR);
            graphC1.addVertex(CDG);
            graphC1.addVertex(HND);
            graphC1.addVertex(TPE);
            graphC1.addVertex(CAI);
            graphC1.addVertex(DEN);
            graphC1.addVertex(MNL);
            graphC1.addVertex(IST);
            graphC1.addVertex(LAX);
            graphC1.addVertex(ICN);
            graphC1.addVertex(JFK);
            graphC1.addVertex(OSL);
            graphC1.addVertex(DUB);
            graphC1.addVertex(SIN);

            Graphs.addEdge(graphC1,MUC,HND,60);
            Graphs.addEdge(graphC1,MUC,OSL,100);
            Graphs.addEdge(graphC1,YVR,SIN,230);
            Graphs.addEdge(graphC1,YVR,TPE,500);
            Graphs.addEdge(graphC1,YVR,IST,160);
            Graphs.addEdge(graphC1,CDG,HND,130);
            Graphs.addEdge(graphC1,CDG,MNL,130);
            Graphs.addEdge(graphC1,HND,TPE,90);
            Graphs.addEdge(graphC1,TPE,JFK,320);
            Graphs.addEdge(graphC1,TPE,LAX,240);
            Graphs.addEdge(graphC1,CDG,HND,130);
            Graphs.addEdge(graphC1,CAI,DEN,310);
            Graphs.addEdge(graphC1,CAI,DUB,80);
            Graphs.addEdge(graphC1,CAI,ICN,110);
            Graphs.addEdge(graphC1,ICN,DEN,220);
            Graphs.addEdge(graphC1,DEN,OSL,320);
            Graphs.addEdge(graphC1,LAX,ICN,430);
            Graphs.addEdge(graphC1,LAX,OSL,270);
            Graphs.addEdge(graphC1,LAX,IST,290);
            Graphs.addEdge(graphC1,IST,DUB,180);
            Graphs.addEdge(graphC1,LAX,DUB,190);
            Graphs.addEdge(graphC1,JFK,OSL,300);
            Graphs.addEdge(graphC1,OSL,SIN,310);

            /* FIX */
            //ConstraintC1
            //Filter out nodes by degree first, then run BC
            Graph<NodeB, DefaultWeightedEdge> C1Graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
            Graphs.addGraph(C1Graph, graphC1);
            BetweennessCentrality<NodeB, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphC1, true);
            Map<NodeB, Double> scores = bc.getScores();

            //For each node in graphB1
            NodeB greatest = MUC;
            for(NodeB node : new ArrayList<>(C1Graph.vertexSet())){
                int degree = C1Graph.degreeOf(node);
                //If the degree and score of node is greater than the greatest node, node become greatest
                if(degree >= C1Graph.degreeOf(node)){
                    greatest = node;
                }
            }

            System.out.println("Constraint C1: Among all the airports, which is the most busiest airport?");
            System.out.println(greatest.getAirportName() + ": " + scores.get(greatest));

            //TESTING
            /* SOUT all C1Graph BC scores for each node */
//            System.out.println("-ALL C1Graph scores-");
//            runBC(C1Graph);
            /* SOUT degrees for each node */
//            System.out.println("-Degree of Nodes-");
//            for(NodeB node : new ArrayList<>(C1Graph.vertexSet())){
//                System.out.println(node.getAirportName() + ": " + C1Graph.degreeOf(node));
//            }
        }

    public static void ConstraintC2(){
        Graph<NodeB, DefaultWeightedEdge> graphB1 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        //Graph B
        NodeB MUC = new NodeB("MUC");
        NodeB YVR = new NodeB("YVR");
        NodeB CDG = new NodeB("CDG");
        NodeB HND = new NodeB("HND");
        NodeB TPE = new NodeB("TPE");
        NodeB CAI = new NodeB("CAI");
        NodeB DEN = new NodeB("DEN");
        NodeB MNL = new NodeB("MNL");
        NodeB IST = new NodeB("IST");
        NodeB LAX = new NodeB("LAX");
        NodeB ICN = new NodeB("ICN");
        NodeB JFK = new NodeB("JFK");
        NodeB OSL = new NodeB("OSL");
        NodeB DUB = new NodeB("DUB");
        NodeB SIN = new NodeB("SIN");

        graphB1.addVertex(MUC);
        graphB1.addVertex(YVR);
        graphB1.addVertex(CDG);
        graphB1.addVertex(HND);
        graphB1.addVertex(TPE);
        graphB1.addVertex(CAI);
        graphB1.addVertex(DEN);
        graphB1.addVertex(MNL);
        graphB1.addVertex(IST);
        graphB1.addVertex(LAX);
        graphB1.addVertex(ICN);
        graphB1.addVertex(JFK);
        graphB1.addVertex(OSL);
        graphB1.addVertex(DUB);
        graphB1.addVertex(SIN);

        Graphs.addEdge(graphB1,MUC,HND,60);
        Graphs.addEdge(graphB1,MUC,OSL,100);
        Graphs.addEdge(graphB1,YVR,SIN,230);
        Graphs.addEdge(graphB1,YVR,TPE,500);
        Graphs.addEdge(graphB1,YVR,IST,160);
        Graphs.addEdge(graphB1,CDG,HND,130);
        Graphs.addEdge(graphB1,CDG,MNL,130);
        Graphs.addEdge(graphB1,HND,TPE,90);

        Graphs.addEdge(graphB1,TPE,JFK,320);
        Graphs.addEdge(graphB1,TPE,JFK,200);

        Graphs.addEdge(graphB1,TPE,LAX,240);
        Graphs.addEdge(graphB1,CDG,HND,130);
        Graphs.addEdge(graphB1,CAI,DEN,310);
        Graphs.addEdge(graphB1,CAI,DUB,80);
        Graphs.addEdge(graphB1,CAI,ICN,110);
        Graphs.addEdge(graphB1,ICN,DEN,220);
        Graphs.addEdge(graphB1,DEN,OSL,320);
        Graphs.addEdge(graphB1,LAX,ICN,430);
        Graphs.addEdge(graphB1,LAX,OSL,270);
        Graphs.addEdge(graphB1,LAX,IST,290);
        Graphs.addEdge(graphB1,IST,DUB,180);
        Graphs.addEdge(graphB1,LAX,DUB,190);
        Graphs.addEdge(graphB1,JFK,OSL,300);
        Graphs.addEdge(graphB1,OSL,SIN,310);

        //Modified BC calculation
        BetweennessCentrality<NodeB, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphB1, true);
        Map<NodeB, Double> scores = bc.getScores();
        scores.forEach(
                (node, score) -> {
                    /* Filters by degree*/
                    if(graphB1.degreeOf(node) <= 3) {
                        System.out.println(node.getAirportName() + ": " + score);
                    }
                }
        );
    }

    //Betweenness Centrality Function
    public static void runBC(Graph graphX){
        BetweennessCentrality<NodeB, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphX, true);
        Map<NodeB, Double> scores = bc.getScores();
        scores.entrySet().stream()
            .sorted(Map.Entry.<NodeB, Double>comparingByValue().reversed())
            .forEach(e -> System.out.println(e.getKey().getAirportName() + ": " + e.getValue()));
    }

        //Node Classes
        /* Used in graph B1*/
        public static class NodeB{
            private String airportName;

            public NodeB(String airportName){
                this.airportName = airportName;
            }

            public String getAirportName() {return airportName;}
        }

        /* Used in graph A*/
        public static class NodeA{
            private String color;
            private String name;

            public NodeA(String name, String color)
            {
                this.color = color;
                this.name = name;
            }
            public String getColor() {return color;}
            public String getName() { return name;}
        }
}
