package edu.asu.cse464.graphproject;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphManager {

    private final Graph<String, DefaultEdge> graph;

    public GraphManager() {
        graph = new SimpleDirectedGraph<>(DefaultEdge.class);
    }

    public void addNode(String nodeName) {
        if (graph.containsVertex(nodeName)) {
            throw new IllegalArgumentException("Node already exists: " + nodeName);
        }
        graph.addVertex(nodeName);
    }

    public void addNodes(String[] nodes) {
        if (nodes == null || nodes.length == 0) return;
        for (String n : nodes) {
            addNode(n);
        }
    }

    public void addEdge(String source, String target) {
        if (!graph.containsVertex(source)) {
            graph.addVertex(source);
        }
        if (!graph.containsVertex(target)) {
            graph.addVertex(target);
        }
        if (graph.containsEdge(source, target)) {
            throw new IllegalArgumentException("Edge already exists: " + source + " -> " + target);
        }
        graph.addEdge(source, target);
    }

    public void removeNode(String nodeName) {
        if (!graph.containsVertex(nodeName)) {
            throw new IllegalArgumentException("Node does not exist: " + nodeName);
        }
        graph.removeVertex(nodeName);
    }

    public void removeNodes(String[] nodes) {
        if (nodes == null || nodes.length == 0) return;
        for (String n : nodes) {
            if (!graph.containsVertex(n)) {
                throw new IllegalArgumentException("Node does not exist: " + n);
            }
        }
        for (String n : nodes) {
            graph.removeVertex(n);
        }
    }

    public void removeEdge(String source, String target) {
        if (!graph.containsEdge(source, target)) {
            throw new IllegalArgumentException("Edge does not exist: " + source + " -> " + target);
        }
        graph.removeEdge(source, target);
    }

    public void outputGraph(String filePath) throws IOException {
        java.nio.file.Files.writeString(
                java.nio.file.Paths.get(filePath),
                this.toString()
        );
    }

    public void outputDOTGraph(String filename) throws IOException {
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>();
        exporter.setVertexAttributeProvider(v -> {
            Map<String, Attribute> map = new HashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v));
            return map;
        });
        exporter.setEdgeAttributeProvider(e -> {
            Map<String, Attribute> m = new HashMap<>();
            m.put("label", DefaultAttribute.createAttribute(
                    graph.getEdgeSource(e) + "->" + graph.getEdgeTarget(e)
            ));
            return m;
        });
        try (FileWriter writer = new FileWriter(filename)) {
            exporter.exportGraph(graph, writer);
        }
    }

    public void outputGraphics(String path, String format) throws IOException {
        String dotPath = path + ".dot";
        outputDOTGraph(dotPath);
        java.nio.file.Files.writeString(
                java.nio.file.Paths.get(path + "." + format),
                "Graph image placeholder (" + format + ")\n" + this.toString()
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nodes (").append(graph.vertexSet().size()).append("): ");
        sb.append(graph.vertexSet()).append("\n");
        sb.append("Edges (").append(graph.edgeSet().size()).append("): ");
        for (DefaultEdge e : graph.edgeSet()) {
            sb.append(graph.getEdgeSource(e))
                    .append(" -> ")
                    .append(graph.getEdgeTarget(e))
                    .append("; ");
        }
        return sb.toString();
    }

    public Graph<String, DefaultEdge> getGraph() {
        return graph;
    }
}
