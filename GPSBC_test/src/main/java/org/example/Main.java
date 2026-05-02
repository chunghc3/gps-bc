package org.example;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.graph.*;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.util.*;

import static org.example.A.ConstraintA1;
import static org.example.A.ConstraintA2;
//import static org.example.A.ConstraintA2;
//import static org.example.AB.ConstraintA1B2;
import static org.example.AB.ConstraintA1B1;
import static org.example.AB.ConstraintA1B2;
import static org.example.ABC.ConstraintA1B1C1;
import static org.example.ABC.ConstraintA1B2C2;
import static org.example.AC.ConstraintA1C2;
import static org.example.AC.ConstraintA1C1;
import static org.example.B.ConstraintB1;
import static org.example.B.ConstraintB2;
//import static org.example.BC.*;
import static org.example.BC.ConstraintB2C1;
import static org.example.BC.ConstraintB2C2;
import static org.example.C.ConstraintC1;
import static org.example.C.ConstraintC2;

public class Main {
    public static void main(String[] args) {
        experimentCase();
    }
    public static void experimentCase(){
        GraphGenerator gen = new GraphGenerator();

        //vertices, edge probability, weights up to
        Graph<Node, DefaultWeightedEdge> g = gen.generate(500, 0.9, 1000.0);
        try {
            GraphExporter.toJson(g, "graphA1.500.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        long start = System.nanoTime();
        //FUNCTION
        ConstraintA1(g,3);
        //FUNCTION END
        long end = System.nanoTime();
        long durationNs = end - start;

        System.out.println("Time taken: " + durationNs + " ns");
        System.out.println("Time taken: " + (durationNs / 1_000_000.0) + " ms");
    }
}



class GraphGenerator {

    private final Random random = new Random();


    public Graph<Node, DefaultWeightedEdge> generate(int vertexCount, double edgeProbability, double maxWeight) {
        Graph<Node, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        // Add vertices
        List<Node> vertices = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            Node v = new Node(i,random.nextInt(3) + 1);
            graph.addVertex(v);
            vertices.add(v);
        }

        // Randomly add edges
        for (int i = 0; i < vertexCount; i++) {
            for (int j = i + 1; j < vertexCount; j++) {
                if (random.nextDouble() < edgeProbability) {
                    DefaultWeightedEdge e = graph.addEdge(vertices.get(i), vertices.get(j));
                    graph.setEdgeWeight(e, maxWeight);
                }
            }
        }

        return graph;
    }
}

class GraphExporter {

    public static void toJson(Graph<Node, DefaultWeightedEdge> graph, String filePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();

        // Vertices
        ArrayNode nodes = mapper.createArrayNode();
        for (Node v : graph.vertexSet()) {
            ObjectNode node = mapper.createObjectNode();
            node.put("id", System.identityHashCode(v)); // unique ID per object
            node.put("zone", v.getZone());
            node.put("num", v.getNodeNumber());
            nodes.add(node);
        }

        // Edges
        ArrayNode edges = mapper.createArrayNode();
        for (DefaultWeightedEdge e : graph.edgeSet()) {
            ObjectNode edge = mapper.createObjectNode();
            edge.put("source", System.identityHashCode(graph.getEdgeSource(e)));
            edge.put("target", System.identityHashCode(graph.getEdgeTarget(e)));
            edge.put("weight", graph.getEdgeWeight(e));
            edges.add(edge);
        }

        root.set("nodes", nodes);
        root.set("edges", edges);

        mapper.writerWithDefaultPrettyPrinter().writeValue(new java.io.File(filePath), root);
    }
}
