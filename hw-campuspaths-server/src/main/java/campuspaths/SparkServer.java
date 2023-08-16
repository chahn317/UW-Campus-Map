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

package campuspaths;

import campuspaths.utils.CORSFilter;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.*;
import java.util.*;

import com.google.gson.Gson;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        // DONE: Create all the Spark Java routes you need here.
        CampusMap map = new CampusMap();
        Gson gson = new Gson();
        Spark.get("/paths", new Route() {
            /**
             * Returns the path as a sequential list of edges
             */
            @Override
            public Object handle(Request request, Response response) {
                String start = request.queryParams("start");
                String end = request.queryParams("end");
                if (start == null || end == null) {
                    Spark.halt(400, "Please enter start and end buildings");
                }

                Path<Point> path = map.findShortestPath(start, end);
                List<Edge> edges = new ArrayList<>();
                for (Path<Point>.Segment s : path) {
                    Edge edge = new Edge(s.getStart().getX(), s.getStart().getY(),
                            s.getEnd().getX(), s.getEnd().getY());
                    edges.add(edge);
                }
                return gson.toJson(edges.toArray());
            }
        });

        Spark.get("/cost", new Route() {
            /**
             * Returns the cost (distance in feet)
             */
            @Override
            public Object handle(Request request, Response response) {
                String start = request.queryParams("start");
                String end = request.queryParams("end");
                if (start == null || end == null) {
                    Spark.halt(400, "Please enter start and end buildings");
                }

                Path<Point> path = map.findShortestPath(start, end);
                return gson.toJson(Math.round(path.getCost()));
            }
        });

        Spark.get("/buildings", new Route() {
            /**
             * Returns a list of buildings in the format "shortName: longName"
             */
            @Override
            public Object handle(Request request, Response response) {
                Map<String, String> buildings = map.buildingNames();
                List<String> names = new ArrayList<>();
                for (String shortName : buildings.keySet()) {
                    String line = shortName + ": " + buildings.get(shortName);
                    names.add(line);
                }
                Object[] buildingNames = names.toArray();
                Arrays.sort(buildingNames);
                return gson.toJson(buildingNames);
            }
        });
    }

    /**
     * This class represents an Edge containing a start point and an end point.
     */
    private static class Edge {
        double x1;
        double y1;
        double x2;
        double y2;

        public Edge(double x1, double y1, double x2, double y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

}
