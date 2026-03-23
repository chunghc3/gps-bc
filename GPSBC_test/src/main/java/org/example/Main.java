package org.example;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.graph.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ConstraintBType1();
    }

    public static void ConstraintA(){
        //Constraint A: Type 1
//        Graph<NodeA, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
//        NodeA A = new NodeA("A","red");
//        NodeA B = new NodeA("B","blue");
//        NodeA C = new NodeA("C","red");
//        NodeA D = new NodeA("D","green");
//        NodeA E = new NodeA("E","red");
//        NodeA F = new NodeA("F","blue");
//        NodeA G = new NodeA("G","blue");
//        NodeA H = new NodeA("H","red");
//        NodeA I = new NodeA("I","green");
//        NodeA J = new NodeA("J","red");
//        NodeA K = new NodeA("K","blue");
//        NodeA L = new NodeA("L","green");
//
//        graph.addVertex(A);
//        graph.addVertex(B);
//        graph.addVertex(C);
//        graph.addVertex(D);
//        graph.addVertex(E);
//        graph.addVertex(F);
//        graph.addVertex(G);
//        graph.addVertex(H);
//        graph.addVertex(I);
//        graph.addVertex(J);
//        graph.addVertex(K);
//        graph.addVertex(L);
//
//        graph.addEdge(A, B);
//        graph.addEdge(A, C);
//        graph.addEdge(B, D);
//        graph.addEdge(C, D);
//        graph.addEdge(D, E);
//        graph.addEdge(D, F);
//        graph.addEdge(E, G);
//        graph.addEdge(F, G);
//        graph.addEdge(G, H);
//        graph.addEdge(H, I);
//        graph.addEdge(H, J);
//        graph.addEdge(I, K);
//        graph.addEdge(J, K);
//        graph.addEdge(K, L);
//        graph.addEdge(L, A);
//
//        graph.addEdge(D, L);
//        graph.addEdge(L, I);
//        graph.addEdge(I, L);
//        graph.addEdge(I, L);
//        graph.addEdge(L, D);

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

    public static void ConstraintBType1(){
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

        //ConstraintB: Type 1
        double pathPriceLimit = 1000;

        //For each node in graphB1
        for(NodeB node : new ArrayList<>(graphB1.vertexSet())){
            //For each edge in node
            for (DefaultWeightedEdge edge : graphB1.edgesOf(node)){
                //If edge weight is greater than pathPriceLimit
                if (graphB1.getEdgeWeight(edge) > pathPriceLimit){
                    //Remove the node
                    graphB1.removeVertex(node);
                    break;
                }
            }
        }

        //Initialize BC
        BetweennessCentrality<NodeB, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphB1, true);
        Map<NodeB, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<NodeB, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getAirportName() + ": " + e.getValue()));
//        for(NodeB node : new ArrayList<>(graphB1.vertexSet())){
//            System.out.println(node.getAirportName() + " = " + scores.get(node));
//        }
    }

    //Node Classes

    public static class NodeB{
        private String airportName;

        public NodeB(String airportName){
            this.airportName = airportName;
        }

        public String getAirportName() {return airportName;}
    }

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
