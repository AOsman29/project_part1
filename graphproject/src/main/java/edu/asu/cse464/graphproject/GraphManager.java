package edu.asu.cse464.graphproject;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Feature 2 – Manage and modify a directed graph (add/remove nodes/edges)
 * and export to DOT format.
 */
public class GraphManager {

    private final Graph<String, DefaultEdge> graph;

    public GraphManager() {
        graph = new SimpleDirectedGraph<>(DefaultEdge.class);
    }

    public void addNode(String nodeName) {
        graph.addVertex(nodeName);
    }

    public void addEdge(String source, String target) {
        graph.addVertex(source);
        graph.addVertex(target);
        graph.addEdge(source, target);
    }

    public void removeNode(String nodeName) {
        graph.removeVertex(nodeName);
    }

    public void removeEdge(String source, String target) {
        graph.removeEdge(source, target);
    }

    public void exportToDot(String filename) {
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>();
        exporter.setVertexAttributeProvider(v -> {
            Map<String, Attribute> map = new HashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v));
            return map;
        });
        exporter.setEdgeAttributeProvider(e -> {
            Map<String, Attribute> map = new HashMap<>();
            map.put("label", DefaultAttribute.createAttribute(
                    graph.getEdgeSource(e) + "->" + graph.getEdgeTarget(e)
            ));
            return map;
        });

        try (FileWriter writer = new FileWriter(filename)) {
            exporter.exportGraph(graph, writer);
            System.out.println("✅ Graph exported to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Graph<String, DefaultEdge> getGraph() {
        return graph;
    }
}
