package graph.junitTests;

import graph.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class GraphTest {

    @Test
    public void testChangeNode() {
        Graph G = new Graph();
        List<String> expected = new ArrayList<>();

        // Add 100 nodes
        for (int i = 1; i < 100; i++) {
            String old = String.valueOf(i);
            expected.add(old);
            G.addNode(old);
        }

        // Change each node one by one and test
        for (int i = 1; i < 100; i++) {
            String old = String.valueOf(i);
            expected.set(i - 1, expected.get(i - 1) + "-new");
            G.changeNode(old, old + "-new");
            assertEquals(G.getNodes(), expected);
        }
    }

    @Test
    public void testGetEdges() {
        Graph G = new Graph();
        List<String> expected = new ArrayList<>();

        // Add 100 nodes
        for (int i = 1; i < 100; i++) {
            G.addNode(String.valueOf(i));
        }

        // Add 100 edges one by one and test
        String parent = String.valueOf(1);
        for (int i = 1; i < 100; i++) {
            String edge = String.valueOf(i);
            expected.add(edge);
            G.addEdge(parent, edge, edge);
            assertEquals(G.getEdges(parent), expected);
        }
    }

    @Test
    public void testChangeEdge() {
        Graph G = new Graph();
        List<String> expected = new ArrayList<>();

        // Add 100 nodes
        for (int i = 1; i < 100; i++) {
            G.addNode(String.valueOf(i));
        }

        // Add 100 edges
        String parent = String.valueOf(1);
        for (int i = 1; i < 100; i++) {
            String edge = String.valueOf(i);
            expected.add(edge);
            G.addEdge(parent, edge, edge);
        }

        // Change each edge one by one and test
        for (int i = 1; i < 100; i++) {
            String old = String.valueOf(i);
            expected.set(i - 1, expected.get(i - 1) + "-new");
            G.changeEdge(parent, old, old, old + "-new");
            assertEquals(G.getEdges(parent), expected);
        }
    }

    @Test
    public void testIsEmpty() {
        // Should be true if empty
        Graph G = new Graph();
        assertTrue(G.isEmpty());

        // Should be false if not empty
        G.addNode("n1");
        assertFalse(G.isEmpty());
    }

    @Test
    public void testSize() {
        Graph G = new Graph();
        assertEquals(G.size(), 0);

        // Add 100 nodes one by one and test size
        for (int i = 1; i < 100; i++) {
            G.addNode(String.valueOf(i));
            assertEquals(G.size(), i);
        }
    }

    @Test
    public void testExceptions() {
        Graph G = new Graph();
        G.addNode("n1");
        G.addNode("n2");
        G.addEdge("n1", "n2", "e12");
        G.addEdge("n1", "n2", "e122");

        // addNode()
        assertThrows(IllegalArgumentException.class, () -> {G.addNode("n1");});

        // addEdge()
        assertThrows(IllegalArgumentException.class, () -> {G.addEdge("n1", "n2", "e12");});
        assertThrows(IllegalArgumentException.class, () -> {G.addEdge("n3", "n2", "e12");});
        assertThrows(IllegalArgumentException.class, () -> {G.addEdge("n1", "n3", "e12");});

        // changeNode()
        assertThrows(IllegalArgumentException.class, () -> {G.changeNode("n1", "n2");});
        assertThrows(IllegalArgumentException.class, () -> {G.changeNode("n4", "n-new");});

        // changeEdge()
        assertThrows(IllegalArgumentException.class, () -> {G.changeEdge("n1", "n2", "e12", "e122");});
        assertThrows(IllegalArgumentException.class, () -> {G.changeEdge("n1", "n2", "e13", "e14");});
        assertThrows(IllegalArgumentException.class, () -> {G.changeEdge("n3", "n2", "e12", "e13");});
        assertThrows(IllegalArgumentException.class, () -> {G.changeEdge("n1", "n3", "e12", "e13");});

        // getChildren()
        assertThrows(IllegalArgumentException.class, () -> {G.getChildren("n3");});

        // getEdges()
        assertThrows(IllegalArgumentException.class, () -> {G.getEdges("n3");});
    }
}
