package org.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class A {
    static Graph<NodeColor, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

    static {
        NodeColor A = new NodeColor("A", "red");
        NodeColor B = new NodeColor("B", "blue");
        NodeColor C = new NodeColor("C", "red");
        NodeColor D = new NodeColor("D", "green");
        NodeColor E = new NodeColor("E", "red");
        NodeColor F = new NodeColor("F", "blue");
        NodeColor G = new NodeColor("G", "blue");
        NodeColor H = new NodeColor("H", "red");
        NodeColor I = new NodeColor("I", "green");
        NodeColor J = new NodeColor("J", "red");
        NodeColor K = new NodeColor("K", "blue");
        NodeColor L = new NodeColor("L", "green");

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

    public static void ConstraintA1() {
        //Constraint A, Type 1: Finding BC for all nodes of <color> /* Most BC values are 0.0? Not tested yet */
        Graph<NodeColor, DefaultEdge> graphA1 = new SimpleGraph<>(DefaultEdge.class);
        Graphs.addGraph(graphA1, graph);

        for (NodeColor nodeX : graph.vertexSet()) {
            if (nodeX.getColor() != "green") {
                graphA1.removeVertex(nodeX);
            }
        }
        BetweennessCentrality<NodeColor, DefaultEdge> bc = new BetweennessCentrality<>(graphA1);
        Map<NodeColor, Double> scores = bc.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<NodeColor, Double>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey().getName() + ": " + e.getValue()));

    }

    public static void ConstraintA2() {
        //Constraint A, Type 2: Finding most important node that is <color> /* Completed: Need verification, high BC values */

        Graph<NodeColor, DefaultEdge> graphA2 = new SimpleGraph<>(DefaultEdge.class);

        //Copy graph into graphA2
        Graphs.addGraph(graphA2, graph);

        //Calculate BC for graphA2
        BetweennessCentrality<NodeColor, DefaultEdge> alg = new BetweennessCentrality<>(graphA2);

        //Filter by <color>
        //Note: Custom color can be used to filter
        Map<NodeColor, Double> scores = alg.getScores();
        scores.entrySet().stream()
                .sorted(Map.Entry.<NodeColor, Double>comparingByValue().reversed())
                .forEach(
                        (node) -> {
                            if (node.getKey().getColor() == "red") {
                                System.out.println(node.getKey().getName() + " -> " + node.getValue());
                            }
                        }
                );
    }
}
