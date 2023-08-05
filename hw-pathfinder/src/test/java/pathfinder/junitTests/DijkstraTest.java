package pathfinder.junitTests;

import graph.Graph;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import pathfinder.Dijkstra;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DijkstraTest {
    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    @Test
    public void testNegativeCostEdgeException() {
        Graph<String, Double> G = new Graph<>();
        Dijkstra<String> d = new Dijkstra<>();
        G.addNode("A");
        G.addNode("B");
        G.addEdge("A", "B", -10.0);

        // Should throw exception if Negative cost edge found
        assertThrows(IllegalArgumentException.class, () -> {d.minCostPath(G, "A", "B");});
    }
}
