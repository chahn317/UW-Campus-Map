package pathfinder;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import graph.Graph;
import pathfinder.datastructures.Path;

import java.lang.Double;

/**
 * Dijkstra contains the method minCostPath that will search for a path of
 * minimum cost between two nodes using Dijkstra's algorithm
 *
 * @param <N> The data type of the graph's nodes
 */
public class Dijkstra<N> {
    /**
     * This class is not an ADT
     */

    /**
     * This method traverses the given graph to find the minimum cost path between
     *      the two given nodes using Dijkstra's algorithm.
     *
     * @spec.requires Edge weights must be >= 0.0
     * @param graph - The graph that is to be traversed
     * @param start - The start node
     * @param dest - The destination node
     * @throws IllegalArgumentException if an edge < 0.0 is found
     * @return Returns the minimum-cost path from the start node to
     *      the end node in the given graph
     */
    public Path<N> minCostPath(Graph<N, Double> graph, N start, N dest) {
        // List is empty if start/
        if (!graph.getNodes().contains(start) || !graph.getNodes().contains(dest)) {
            return null;
        }

        Queue<Path<N>> active = new PriorityQueue<>(new Comparator<Path<N>>() {
            @Override
            public int compare(Path<N> o1, Path<N> o2) {
                return Double.compare(o1.getCost(), o2.getCost());
            }
        });

        Set<N> finished = new HashSet<>();
        active.add(new Path<> (start));

        if (start.equals(dest)) {
            return new Path<> (start);
        }

        while (!active.isEmpty()) {
            Path<N> min = active.remove();
            N minDest = min.getEnd();
            if (minDest.equals(dest)) {
                return min;
            } else if (!finished.contains(minDest)) {
                Set<SimpleEntry<Double, N>> destChildren = graph.getEdges(minDest);
                for (SimpleEntry<Double, N> edge : destChildren) {
                    if (edge.getKey() < 0.0) {
                        throw new IllegalArgumentException();
                    }
                    N child = edge.getValue();
                    if (!finished.contains(child)) {
                        Path<N> newPath = min.extend(child, edge.getKey());
                        active.add(newPath);
                    }
                }
                finished.add(minDest);
            }
        }
        return null;
    }
}
