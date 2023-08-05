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

package pathfinder;

import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import graph.Graph;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampusMap implements ModelAPI {

    private final Graph<Point, Double> campusMap;

    private final Map<String, String> buildings;

    private final Map<String, Point> points;

    public CampusMap() {
        campusMap = new Graph<>();
        buildings = new HashMap<>();
        points = new HashMap<>();

        List<CampusBuilding> buildingsList = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        for (CampusBuilding c : buildingsList) {
            buildings.put(c.getShortName(), c.getLongName());
            points.put(c.getShortName(), new Point(c.getX(), c.getY()));
        }

        List<CampusPath> pathsList = CampusPathsParser.parseCampusPaths("campus_paths.csv");
        for (CampusPath c : pathsList) {
            Point p1 = new Point(c.getX1(), c.getY1());
            Point p2 = new Point(c.getX2(), c.getY2());
            if (!campusMap.getNodes().contains(p1)) {
                campusMap.addNode(p1);
            }
            if (!campusMap.getNodes().contains(p2)) {
                campusMap.addNode(p2);
            }
            campusMap.addEdge(p1, p2, c.getDistance());
        }
    }

    @Override
    public boolean shortNameExists(String shortName) {
        return buildings.containsKey(shortName);
    }

    @Override
    public String longNameForShort(String shortName) {
        if (!shortNameExists(shortName)) {
            throw new IllegalArgumentException();
        }
        return buildings.get(shortName);
    }

    @Override
    public Map<String, String> buildingNames() {
        Map<String, String> mapping = new HashMap<>();
        for (String s : buildings.keySet()) {
            mapping.put(s, buildings.get(s));
        }
        return mapping;
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        if (startShortName == null || endShortName == null || !shortNameExists(startShortName)
                || !shortNameExists(endShortName)) {
            throw new IllegalArgumentException();
        }
        Dijkstra<Point> dijkstra = new Dijkstra<>();
        return dijkstra.minCostPath(campusMap, points.get(startShortName), points.get(endShortName));
    }

}
