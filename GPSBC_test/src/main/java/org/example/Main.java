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
import static org.example.AC.ConstraintA1C2;
import static org.example.AC.ConstraintA1C1;
import static org.example.B.ConstraintB1;
import static org.example.B.ConstraintB2;
//import static org.example.BC.*;
import static org.example.C.ConstraintC1;
import static org.example.C.ConstraintC2;

public class Main {
    public static void main(String[] args) {
        experimentCase();
    }
    public static void experimentCase(){
        GraphGenerator gen = new GraphGenerator();

        //vertices, edge probability, weights up to
        //A
        // Graph<Node, DefaultWeightedEdge> g = gen.generate(100, 0.4, 100.0);
        //B
        //Graph<Node, DefaultWeightedEdge> g = gen.generate(100, 0.6, 1000.0);
        //C
        //A1C2
        Graph<Node, DefaultWeightedEdge> g = gen.generate(100, 0.7, 1000.0);
        try {
            GraphExporter.toJson(g, "graphB2.100.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        long start = System.nanoTime();
        //FUNCTION
        ConstraintB2(g, 700);
        //FUNCTION END
        long end = System.nanoTime();
        long durationNs = end - start;

        System.out.println("Time taken: " + durationNs + " ns");
        System.out.println("Time taken: " + (durationNs / 1_000_000.0) + " ms");
    }

    public static void regularTest(){
             Graph<Node, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

     Node MUC = new Node(1,1);
     Node YVR = new Node(2,1);
     Node CDG = new Node(3,3);
     Node HND = new Node(4,2);
     Node TPE = new Node(5,3);
     Node CAI = new Node(6,1);
     Node DEN = new Node(67,2);
     Node MNL = new Node(7,3);
     Node IST = new Node(8,1);
     Node LAX = new Node(9,1);
     Node ICN = new Node(10,3);
     Node JFK = new Node(11,1);
     Node OSL = new Node(12,2);
     Node DUB = new Node(13,1);
     Node SIN = new Node(14,3);


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

        ConstraintB2(graph, 200);
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
                    graph.setEdgeWeight(e, random.nextDouble() * maxWeight);
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
