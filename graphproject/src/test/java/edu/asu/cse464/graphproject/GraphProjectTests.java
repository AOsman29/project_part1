package edu.asu.cse464.graphproject;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class GraphProjectTests {

    @Test
    void testAddNodeAndEdge() {
        GraphManager manager = new GraphManager();
        manager.addNode("A");
        manager.addNode("B");
        manager.addEdge("A", "B");

        Graph<String, DefaultEdge> graph = manager.getGraph();
        assertTrue(graph.containsVertex("A"));
        assertTrue(graph.containsVertex("B"));
        assertTrue(graph.containsEdge("A", "B"));
    }

    @Test
    void testRemoveNodeAndEdge() {
        GraphManager manager = new GraphManager();
        manager.addEdge("X", "Y");
        manager.removeEdge("X", "Y");
        manager.removeNode("Y");

        Graph<String, DefaultEdge> graph = manager.getGraph();
        assertFalse(graph.containsVertex("Y"));
        assertFalse(graph.containsEdge("X", "Y"));
    }

    @Test
    void testExportAndParseDotFile() throws IOException {
        GraphManager manager = new GraphManager();
        manager.addEdge("1", "2");

        String path = "test_output.dot";
        manager.exportToDot(path);

        assertTrue(new File(path).exists());

        GraphParser parser = new GraphParser();
        Graph<String, DefaultEdge> g = parser.parseGraph(path);

        assertNotNull(g);
        assertEquals(2, g.vertexSet().size());
        assertEquals(1, g.edgeSet().size());
    }

    @Test
    void testFullPipelineSummary() throws IOException {
        GraphManager manager = new GraphManager();
        manager.addEdge("Alpha", "Beta");
        manager.addEdge("Beta", "Gamma");

        String dot = "pipeline_test.dot";
        String txt = "graph_summary_test.txt";

        manager.exportToDot(dot);
        assertTrue(new File(dot).exists());

        GraphParser parser = new GraphParser();
        Graph<String, DefaultEdge> g = parser.parseGraph(dot);

        manager.writeGraphSummary(g, txt);
        assertTrue(new File(txt).exists());
        assertTrue(g.vertexSet().size() >= 2);
    }

    @Test
    void testOutputGraphWrite() throws IOException {
        GraphManager manager = new GraphManager();
        manager.addNode("A");
        manager.addNode("B");
        manager.addEdge("A", "B");

        String file = "output_graph_test.txt";
        manager.outputGraph(file);

        File f = new File(file);
        assertTrue(f.exists());

        String contents = java.nio.file.Files.readString(f.toPath());
        assertTrue(contents.contains("Nodes (2)"));
        assertTrue(contents.contains("A -> B"));

        f.delete();
    }

    @Test
    void testRemoveScenario1Correct() {
        GraphManager manager = new GraphManager();
        manager.addNodes(new String[]{"A", "B", "C"});
        manager.addEdge("A", "B");
        manager.addEdge("B", "C");

        manager.removeNode("C");
        manager.removeEdge("A", "B");

        Graph<String, DefaultEdge> g = manager.getGraph();
        assertFalse(g.containsVertex("C"));
        assertFalse(g.containsEdge("A", "B"));
    }

    @Test
    void testRemoveScenario2NodeMissing() {
        GraphManager manager = new GraphManager();
        manager.addNode("A");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            manager.removeNode("Z");
        });

        assertTrue(ex.getMessage().contains("does not exist"));
    }

    @Test
    void testRemoveScenario3EdgeMissing() {
        GraphManager manager = new GraphManager();
        manager.addNodes(new String[]{"A", "B"});

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            manager.removeEdge("A", "B");
        });

        assertTrue(ex.getMessage().contains("does not exist"));
    }
}
