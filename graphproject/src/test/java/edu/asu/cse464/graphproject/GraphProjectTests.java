package edu.asu.cse464.graphproject;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

public class GraphProjectTests {

    // ✅ Test adding nodes and edges
    @Test
    void testAddNodeAndEdge() {
        GraphManager manager = new GraphManager();
        manager.addNode("A");
        manager.addNode("B");
        manager.addEdge("A", "B");

        Graph<String, DefaultEdge> graph = manager.getGraph();

        assertTrue(graph.containsVertex("A"), "Graph should contain node A");
        assertTrue(graph.containsVertex("B"), "Graph should contain node B");
        assertTrue(graph.containsEdge("A", "B"), "Graph should contain edge A→B");
    }

    // ✅ Test removing nodes and edges
    @Test
    void testRemoveNodeAndEdge() {
        GraphManager manager = new GraphManager();
        manager.addEdge("X", "Y");
        manager.removeEdge("X", "Y");
        manager.removeNode("Y");

        Graph<String, DefaultEdge> graph = manager.getGraph();

        assertFalse(graph.containsVertex("Y"), "Graph should not contain node Y");
        assertFalse(graph.containsEdge("X", "Y"), "Graph should not contain edge X→Y");
    }

    // ✅ Test export/import pipeline
    @Test
    void testExportAndParseDotFile() throws IOException {
        GraphManager manager = new GraphManager();
        manager.addEdge("1", "2");

        String filePath = "test_output.dot";
        manager.exportToDot(filePath);

        File file = new File(filePath);
        assertTrue(file.exists(), "DOT file should be created after export");

        GraphParser parser = new GraphParser();
        Graph<String, DefaultEdge> parsedGraph = parser.parseGraph(filePath);

        assertNotNull(parsedGraph, "Parsed graph should not be null");
        assertEquals(2, parsedGraph.vertexSet().size(), "Parsed graph should have 2 vertices");
        assertEquals(1, parsedGraph.edgeSet().size(), "Parsed graph should have 1 edge");
    }

    // ✅ Bonus: Integration test for full pipeline (optional extra credit)
    @Test
    void testFullPipelineSummary() throws IOException {
        GraphManager manager = new GraphManager();
        manager.addEdge("Alpha", "Beta");
        manager.addEdge("Beta", "Gamma");

        String dotFile = "pipeline_test.dot";
        String summaryFile = "graph_summary_test.txt";

        // Export
        manager.exportToDot(dotFile);
        assertTrue(new File(dotFile).exists(), "DOT file should be generated");

        // Parse back
        GraphParser parser = new GraphParser();
        Graph<String, DefaultEdge> parsedGraph = parser.parseGraph(dotFile);

        // Write summary
        manager.writeGraphSummary(parsedGraph, summaryFile);
        File summary = new File(summaryFile);

        assertTrue(summary.exists(), "Summary file should be created");
        assertTrue(parsedGraph.vertexSet().size() >= 2, "Graph should have vertices after re-import");
    }
}
