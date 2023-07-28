package graph;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

/**
 * This object represents a directed labeled graph. In this representation,
 * each node contains unique data, and each edge that connects the same nodes
 * in the same direction has unique labels.
 */
public class Graph {
    // Rep invariant:
    // this != null, all nodes != null, all edges != null

    // Abstract function:
    // AF(this) = a labeled directed graph g such that
    // all nodes = g.keySet()
    // list of all edges with parent node n = g.get(n)
    // label of edge e = e.getKey()
    // child node of edge e = e.getValue()

    private final HashMap<String, Set<SimpleEntry<String, String>>> graph;
    private final boolean DEBUG = false;

    /**
     * Constructs a new graph
     * @spec.effects creates an empty graph
     */
    public Graph() {
        graph = new HashMap<>();
    }

    /**
     * Adds a new node containing the given data to the graph
     * @spec.requires data must be a string that is different from the data of
     *      every other node in the graph
     * @spec.modifies this
     * @spec.effects adds a node to this graph
     * @param data - the node's data
     * @throws IllegalArgumentException if data is the same as another existing
     *      node's data
     */
    public void addNode(String data) {
        checkRep();
        if (graph.containsKey(data)) {
            throw new IllegalArgumentException();
        }
        graph.put(data, new HashSet<>());
        checkRep();
    }

    /**
     * Adds a new labeled edge that points from the parent node to the child node
     * @spec.requires parent and child must both be nodes that exist in the graph,
     *      and there must not exist another edge with the same label that points
     *      from parent to child
     * @spec.modifies this
     * @spec.effects adds a directed edge between the specified nodes
     * @param parent - the parent node's data
     * @param child - the child node's data
     * @param label - the edge's label
     * @throws IllegalArgumentException if label is the same as the label of
     *      another edge with the same parent and child, or if the parent or child
     *      does not exist in the graph
     */
    public void addEdge(String parent, String child, String label) {
        checkRep();
        if (!graph.containsKey(parent) || !graph.containsKey(child) ||
            graph.get(parent).contains(new SimpleEntry<>(label, child))) {
            throw new IllegalArgumentException();
        }
        graph.get(parent).add(new SimpleEntry<>(label, child));
        checkRep();
    }

    /**
     * Changes the data contained in a node to a new value
     * @spec.requires oldVal must be the string representation of a node that
     *      exists in the graph, and newVal must be different from the data of
     *      every other node
     * @spec.modifies this
     * @spec.effects changes the value of the node with data oldVal
     * @param oldVal - the old data that will be replaced
     * @param newVal- the new data that will replace the old node's data
     * @throws IllegalArgumentException if newVal is the same as another existing
     *      node's data, or if oldVal does not exist in the graph
     */
    public void changeNode(String oldVal, String newVal) {
        checkRep();
        if (!graph.containsKey(oldVal) || graph.containsKey(newVal)) {
            throw new IllegalArgumentException();
        }
        Set<SimpleEntry<String, String>> edges = graph.remove(oldVal);
        graph.put(newVal, edges);
        checkRep();
    }

    /**
     * Changes the label contained in an edge to a new value
     * @spec.requires oldVal must be the string representation of an edge that
     *      goes from parent to child, newVal must be different from the data of
     *      every other edge that goes from parent to child, and parent and child
     *      must both be string representations of nodes that exist
     * @spec.modifies this
     * @spec.effects changes the value of the edge with label oldVal
     * @param parent - the parent node's data
     * @param child - the child node's data
     * @param oldVal - the old label that will be replaced
     * @param newVal- the new label that will replace the old edge's label
     * @throws IllegalArgumentException if newVal is the same as another existing
     *      edge's label with the same parent and child, oldVal does not exist
     *      between the parent and child, or if the parent or child are not in
     *      the graph
     */
    public void changeEdge(String parent, String child, String oldVal, String newVal) {
        checkRep();
        Set<SimpleEntry<String, String>> edges = graph.get(parent);
        if (!graph.containsKey(parent) || !graph.containsKey(child) ||
            !edges.contains(new SimpleEntry<>(oldVal, child)) ||
            edges.contains(new SimpleEntry<>(newVal, child))) {
            throw new IllegalArgumentException();
        }
        for (SimpleEntry<String, String> edge : edges) {
            if (edge.getKey().equals(oldVal) && edge.getValue().equals(child)) {
                edges.remove(edge);
                edges.add(new SimpleEntry<>(newVal, child));
                break;
            }
        }
        checkRep();
    }

    /**
     * Returns a set containing all nodes in this
     * @return a set of nodes in this
     */
    public Set<String> getNodes() {
        checkRep();
        return graph.keySet();
    }

    /**
     * Returns a set containing all child nodes of the given node
     * @spec.requires data must be the string representation of a node that
     *      exists in the graph
     * @param data - the data of the node whose children will be returned
     * @return a set containing all the child nodes of data
     * @throws IllegalArgumentException if data is not the string representation
     *      of a node that exists in the graph
     */
    public Set<String> getChildren(String data) {
        checkRep();
        if (!graph.keySet().contains(data)) {
            throw new IllegalArgumentException();
        }
        HashSet<String> children = new HashSet<>();
        for (SimpleEntry<String, String> edge : graph.get(data)) {
            children.add(edge.getValue());
        }
        checkRep();
        return children;
    }

    /**
     * Returns a set containing all outgoing edges of the given node
     * @spec.requires data must be the string representation of a node that
     *      exists in the graph
     * @param data - the data of the node whose outgoing edges will be returned
     * @return a set of containing all the outgoing edges of data such that each
     *      element is a SimpleEntry where the key is the edge's label and the
     *      value is the corresponding child node
     * @throws IllegalArgumentException if data is not the string representation
     *      of a node that exists in the graph
     */
    public Set<SimpleEntry<String, String>> getEdges(String data) {
        checkRep();
        if (!graph.keySet().contains(data)) {
            throw new IllegalArgumentException();
        }
        return graph.get(data);
    }

    /**
     * Returns the number of nodes in the graph
     * @return the number of nodes in this
     */
    public int size() {
        checkRep();
        return graph.keySet().size();
    }

    /**
     * Returns true if the graph is empty, false if the graph contains at least
     * one element
     * @return true if the graph is empty, false if not
     */
    public boolean isEmpty() {
        checkRep();
        return graph.isEmpty();
    }

    // Checks the representation invariant
    private void checkRep() {
        if (DEBUG) {
            assert graph != null;
            for (String node : graph.keySet()) {
                assert node != null;
            }
            for (Set<SimpleEntry<String, String>> edge : graph.values()) {
                assert edge != null;
            }
        }
    }
}
