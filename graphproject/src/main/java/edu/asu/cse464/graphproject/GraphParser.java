package edu.asu.cse464.graphproject;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.dot.DOTImporter;

import java.io.FileReader;
import java.io.IOException;
import java.util.function.Supplier;

/**
 * Feature 1 – Parse a DOT graph file and create a directed graph.
 */
public class GraphParser {

    public Graph<String, DefaultEdge> parseGraph(String filePath) throws IOException {
        Supplier<String> vertexSupplier = new Supplier<>() {
            private int count = 0;
            @Override
            public String get() {
                return "v" + (++count);
            }
        };
        Supplier<DefaultEdge> edgeSupplier = DefaultEdge::new;

        Graph<String, DefaultEdge> graph =
                new DefaultDirectedGraph<>(vertexSupplier, edgeSupplier, false);

        DOTImporter<String, DefaultEdge> importer = new DOTImporter<>();
        importer.importGraph(graph, new FileReader(filePath));
        return graph;
    }

    public String toString(Graph<String, DefaultEdge> graph) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nodes (" + graph.vertexSet().size() + "): " + graph.vertexSet()).append("\n");
        sb.append("Edges (" + graph.edgeSet().size() + "): ");
        for (DefaultEdge e : graph.edgeSet()) {
            sb.append(graph.getEdgeSource(e)).append(" -> ").append(graph.getEdgeTarget(e)).append("; ");
        }
        return sb.toString();
    }

    public void outputGraph(Graph<String, DefaultEdge> graph, String filePath) throws IOException {
        java.nio.file.Files.writeString(java.nio.file.Paths.get(filePath), toString(graph));
    }
}
