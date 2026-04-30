package org.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.graph.*;

import java.util.Map;

public class A {
    static Graph<Node, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

    static {
        Node A = new Node(1, 1);
        Node B = new Node(2, 2);
        Node C = new Node(3, 1);
        Node D = new Node(4, 3);
        Node E = new Node(5, 1);
        Node F = new Node(6, 2);
        Node G = new Node(7, 2);
        Node H = new Node(8, 1);
        Node I = new Node(9, 3);
        Node J = new Node(10, 1);
        Node K = new Node(11, 2);
        Node L = new Node(12, 3);

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

    public static void ConstraintA1(Graph<Node, DefaultWeightedEdge> graph, int specifiedZone) {
        //Constraint A1: Filter by vertex color, then calculate Betweenness Centrality
        Graph<Node, DefaultWeightedEdge> graphA1 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Graphs.addGraph(graphA1, graph);

        for (Node nodeX : graph.vertexSet()) {
            if (nodeX.getZone() != specifiedZone) {
                graphA1.removeVertex(nodeX);
            }
        }
        BetweennessCentrality<Node, DefaultWeightedEdge> bc = new BetweennessCentrality<>(graphA1);
        Map<Node, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<Node, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getNodeNumber() + ": " + e.getValue()+ "...Zone:  " + e.getKey().getZone()));
    }

    public static void ConstraintA2(Graph<Node, DefaultWeightedEdge> graph, int specifiedZone) {
        //Constraint A, Type 2: Finding most important node that is <color> /* Completed: Need verification, high BC values */

        Graph<Node, DefaultWeightedEdge> graphA2 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        //Copy graph into graphA2
        Graphs.addGraph(graphA2, graph);

        //Calculate BC for graphA2
        BetweennessCentrality<Node, DefaultWeightedEdge> alg = new BetweennessCentrality<>(graphA2);
        //Filter by <color>
        //Note: Custom color can be used to filter
        Map<Node, Double> scores = alg.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<Node, Double>comparingByValue())
                .forEach(
                        (node) -> {
                            if (node.getKey().getZone() == specifiedZone) {
                                System.out.println(node.getKey().getNodeNumber() + " -> " + node.getValue() + "...Zone:  " + node.getKey().getZone());
                            }
                        }
                );
    }
}
