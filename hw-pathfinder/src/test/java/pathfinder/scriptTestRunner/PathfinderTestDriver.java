/*
 * Copyright (C) 2023 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.scriptTestRunner;

import graph.Graph;
import pathfinder.Dijkstra;
import java.util.AbstractMap.SimpleEntry;
import pathfinder.datastructures.Path;

import java.io.*;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {

    private final BufferedReader input;

    private final PrintWriter output;

    private final Map<String, Graph<String, Double>> graphs = new HashMap<>();

    // Leave this constructor public
    public PathfinderTestDriver(Reader r, Writer w) {
        // DONE: Implement this, reading commands from `r` and writing output to `w`.
        input = new BufferedReader(r);
        output = new PrintWriter(w);
        // See GraphTestDriver as an example.
    }

    // Leave this method public
    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        // DONE Insert your code here.
        graphs.put(graphName, new Graph<>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        // DONE Insert your code here.
        graphs.get(graphName).addNode(nodeName);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        Double edgeLabel = Double.valueOf(arguments.get(3));

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         Double edgeLabel) {
        // DONE Insert your code here.
        graphs.get(graphName).addEdge(parentName, childName, edgeLabel);
        output.println("added edge " + String.format("%.3f", edgeLabel) +
                " from " + parentName + " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        // DONE Insert your code here.
        List<String> nodes = new ArrayList<>(graphs.get(graphName).getNodes());
        Collections.sort(nodes);
        output.print(graphName + " contains:");
        for (String node : nodes) {
            output.print(" " + node);
        }
        output.println();
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        // DONE Insert your code here.
        Set<AbstractMap.SimpleEntry<Double, String>> edges = graphs.get(graphName).getEdges(parentName);
        TreeSet<AbstractMap.SimpleEntry<Double, String>> sorted =
                new TreeSet<>(new Comparator<AbstractMap.SimpleEntry<Double, String>>() {
                    public int compare(AbstractMap.SimpleEntry<Double, String> e1, AbstractMap.SimpleEntry<Double, String> e2) {
                        if(e1.getValue().equals(e2.getValue())) {
                            return e1.getValue().compareTo(e2.getValue());
                        }
                        if (!e1.getKey().equals(e2.getKey())) {
                            return e1.getKey().compareTo(e2.getKey());
                        }
                        return 0;
                    }
                });
        sorted.addAll(edges);
        output.print("the children of " + parentName + " in " + graphName + " are:");
        for (AbstractMap.SimpleEntry<Double, String> edge : sorted) {
            output.print(" " + edge.getValue() + "(" + String.format("%.3f", edge.getKey()) + ")");
        }
        output.println();
    }

    private void findPath(List<String> arguments) {
        if(arguments.size() != 3) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String start = arguments.get(1);
        String dest = arguments.get(2);
        findPath(graphName, start, dest);
    }

    private void findPath(String graphName, String start, String dest) {
        if (!graphs.get(graphName).getNodes().contains(start)) {
            output.println("unknown: " + start);
        }
        if (!graphs.get(graphName).getNodes().contains(dest)) {
            output.println("unknown: " + dest);
        }
        if (graphs.get(graphName).getNodes().contains(start) && graphs.get(graphName).getNodes().contains(dest)) {
            Dijkstra<String> dijkstra = new Dijkstra<>();
            Path<String> path =
                    dijkstra.minCostPath(graphs.get(graphName), start, dest);
            output.println("path from " + start + " to " + dest + ":");
            if (path == null) {
                if (start.equals(dest)) {
                    output.println("total cost: " + String.format("%.3f", 0.0));
                } else {
                    output.println("no path found");
                }
            } else {
                String parent = start;
                Double cost = 0.0;
                for (Path<String>.Segment edge : path) {
                    output.println(parent + " to " + edge.getEnd() + " with weight "
                            + String.format("%.3f", edge.getCost()));
                    parent = edge.getEnd();
                    cost += edge.getCost();
                }
                output.println("total cost: " + String.format("%.3f", cost));
            }
        }
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
