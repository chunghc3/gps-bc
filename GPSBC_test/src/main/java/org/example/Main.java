package org.example;
import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        //Type 1
//        Graph<Node, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
//        Node A = new Node("A","red");
//        Node B = new Node("B","blue");
//        Node C = new Node("C","red");
//        Node D = new Node("D","green");
//        Node E = new Node("E","red");
//        Node F = new Node("F","blue");
//        Node G = new Node("G","blue");
//        Node H = new Node("H","red");
//        Node I = new Node("I","green");
//        Node J = new Node("J","red");
//        Node K = new Node("K","blue");
//        Node L = new Node("L","green");
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

        //Type 2
        Graph<Node, DefaultEdge> graph2 = new SimpleGraph<>(DefaultEdge.class);
        Node A1 = new Node("A","red");
        Node B1 = new Node("B","red");
        Node C1 = new Node("C","green");
        Node D1 = new Node("D","green");

        graph2.addVertex(A1);
        graph2.addVertex(B1);
        graph2.addVertex(C1);
        graph2.addVertex(D1);
        graph2.addEdge(A1, C1);
        graph2.addEdge(C1, B1);
        graph2.addEdge(B1, D1);

        //First sort color
        Set<Node> filteredNodes = graph2.vertexSet().stream()
                .filter(n -> n.getColor() == "green")
                .collect(Collectors.toSet());

        //Post filtering
        Graph<Node, DefaultEdge> subgraph = new AsSubgraph<>(graph2, filteredNodes);
        Graph<Node, DefaultEdge> wholeGraph = new AsSubgraph<>(graph2, graph2.vertexSet());

        BetweennessCentrality<Node, DefaultEdge> alg = new BetweennessCentrality<>(subgraph);

        Map<Node, Double> scores = alg.getScores();
        scores.forEach(
                (node, score) -> {
                    if (node.getColor() == "green") {
                        System.out.println(node.getName() + " -> " + score);
                    }
                }
        );

        Node mostCentral = scores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        System.out.println("Most central: " + mostCentral.getName());
        System.out.println("Most central: " + mostCentral.getName());
    }

    public static class Node{
        private String color;
        private String name;

        public Node(String name, String color)
        {
            this.color = color;
            this.name = name;
        }
        public String getColor() {return color;}
        public String getName() { return name;}
    }
}
