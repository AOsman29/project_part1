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

    // Add a vertex
    public void addNode(String nodeName) {
        graph.addVertex(nodeName);
    }

    // Add an edge between two vertices
    public void addEdge(String source, String target) {
        graph.addVertex(source);
        graph.addVertex(target);
        graph.addEdge(source, target);
    }

    // Remove a vertex
    public void removeNode(String nodeName) {
        graph.removeVertex(nodeName);
    }

    // Remove an edge between two vertices
    public void removeEdge(String source, String target) {
        graph.removeEdge(source, target);
    }

    // Export graph to DOT format
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

    // Returns the underlying graph
    public Graph<String, DefaultEdge> getGraph() {
        return graph;
    }

    /**
     * Writes a simple summary of the graph to a text file.
     * Used by testFullPipelineSummary().
     */
    public void writeGraphSummary(Graph<String, DefaultEdge> graph, String filename) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nodes (" + graph.vertexSet().size() + "): " + graph.vertexSet()).append("\n");
        sb.append("Edges (" + graph.edgeSet().size() + "): ");
        for (DefaultEdge e : graph.edgeSet()) {
            sb.append(graph.getEdgeSource(e))
              .append(" -> ")
              .append(graph.getEdgeTarget(e))
              .append("; ");
        }

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(sb.toString());
            System.out.println("📝 Graph summary written to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
