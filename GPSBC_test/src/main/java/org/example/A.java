package org.example;

import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class A {
    public static void initGraphA(){
        Graph<NodeColor, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        NodeColor A = new NodeColor("A","red");
        NodeColor B = new NodeColor("B","blue");
        NodeColor C = new NodeColor("C","red");
        NodeColor D = new NodeColor("D","green");
        NodeColor E = new NodeColor("E","red");
        NodeColor F = new NodeColor("F","blue");
        NodeColor G = new NodeColor("G","blue");
        NodeColor H = new NodeColor("H","red");
        NodeColor I = new NodeColor("I","green");
        NodeColor J = new NodeColor("J","red");
        NodeColor K = new NodeColor("K","blue");
        NodeColor L = new NodeColor("L","green");

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);
        graph.addVertex(E);
        graph.addVertex(F);
        graph.addVertex(G);
        graph.addVertex(H);
        graph.addVertex(I);
        graph.addVertex(J);
        graph.addVertex(K);
        graph.addVertex(L);

        graph.addEdge(A, B);
        graph.addEdge(A, C);
        graph.addEdge(B, D);
        graph.addEdge(C, D);
        graph.addEdge(D, E);
        graph.addEdge(D, F);
        graph.addEdge(E, G);
        graph.addEdge(F, G);
        graph.addEdge(G, H);
        graph.addEdge(H, I);
        graph.addEdge(H, J);
        graph.addEdge(I, K);
        graph.addEdge(J, K);
        graph.addEdge(K, L);
        graph.addEdge(L, A);

        graph.addEdge(D, L);
        graph.addEdge(L, I);
        graph.addEdge(I, L);
        graph.addEdge(I, L);
        graph.addEdge(L, D);
    }
    public static void ConstraintA1(Graph graph){
        /* Finish */
    }

    public static void ConstraintA(){



        //Constraint A: Type 2
        Graph<NodeColor, DefaultEdge> graph2 = new SimpleGraph<>(DefaultEdge.class);

        NodeColor A1 = new NodeColor("A","red");
        NodeColor B1 = new NodeColor("B","red");
        NodeColor C1 = new NodeColor("C","green");
        NodeColor D1 = new NodeColor("D","green");

        graph2.addVertex(A1);
        graph2.addVertex(B1);
        graph2.addVertex(C1);
        graph2.addVertex(D1);
        graph2.addEdge(A1, C1);
        graph2.addEdge(C1, B1);
        graph2.addEdge(B1, D1);

        //First sort color
        Set<NodeColor> filteredNodes = graph2.vertexSet().stream()
                .filter(n -> n.getColor() == "green")
                .collect(Collectors.toSet());

        //Post filtering
        Graph<NodeColor, DefaultEdge> subgraph = new AsSubgraph<>(graph2, filteredNodes);
        Graph<NodeColor, DefaultEdge> wholeGraph = new AsSubgraph<>(graph2, graph2.vertexSet());

        BetweennessCentrality<NodeColor, DefaultEdge> alg = new BetweennessCentrality<>(subgraph);

        Map<NodeColor, Double> scores = alg.getScores();
        scores.forEach(
                (node, score) -> {
                    if (node.getColor() == "green") {
                        System.out.println(node.getName() + " -> " + score);
                    }
                }
        );

        NodeColor mostCentral = scores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        System.out.println("Most central: " + mostCentral.getName());
    }
}
