package graph;

import java.util.List;

public class Graph {

    /**
     * Constructs a new graph
     * @spec.effects creates an empty graph
     */
    public Graph() {
        throw new RuntimeException();
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
        throw new RuntimeException();
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
        throw new RuntimeException();
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
        throw new RuntimeException();
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
        throw new RuntimeException();
    }

    /**
     * Returns a list containing all nodes in this
     * @return a list of nodes in this
     */
    public List<String> getNodes() {
        throw new RuntimeException();
    }

    /**
     * Returns a list containing all child nodes of the given node
     * @spec.requires data must be the string representation of a node that
     *      exists in the graph
     * @param data - the data of the node whose children will be returned
     * @return a list containing all the child nodes of data
     * @throws IllegalArgumentException if data is not the string representation
     *      of a node that exists in the graph
     */
    public List<String> getChildren(String data) {
        throw new RuntimeException();
    }

    /**
     * Returns a list containing all outgoing edges of the given node
     * @spec.requires data must be the string representation of a node that
     *      exists in the graph
     * @param data - the data of the node whose outgoing edges will be returned
     * @return a list containing all the outgoing edges of data
     * @throws IllegalArgumentException if data is not the string representation
     *      of a node that exists in the graph
     */
    public List<String> getEdges(String data) {
        throw new RuntimeException();
    }

    /**
     * Returns the number of nodes in the graph
     * @return the number of nodes in this
     */
    public int size() {
        throw new RuntimeException();
    }

    /**
     * Returns true if the graph is empty, false if the graph contains at least
     * one element
     * @return true if the graph is empty, false if not
     */
    public boolean isEmpty() {
        throw new RuntimeException();
    }

}
