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


/**
 * CampusMap provides a representation of all the buildings on the UW campus
 */
public class CampusMap implements ModelAPI {

    // Abstraction Function:
    //      location of the buildings = campusMap.keySet();
    //      short names of the buildings = buildings.keySet();
    //      long name of the building with short name n = buildings.get(n);
    //      location of the building with short name n = points.get(n);

    // Representation Invariant:
    //      campusMap != null
    //      buildings != null
    //      points != null;
    //      For every Point p and Double d in campusMap,
    //          p != null
    //          d != null
    //          d >= 0.0
    //      For every String s in buildings,
    //          s != null
    //      For every String s and Point p in points,
    //          s != null
    //          p != null

    private final Graph<Point, Double> campusMap;

    private final Map<String, String> buildings;

    private final Map<String, Point> points;

    /**
     * Constructs a new CampusMap containing all the buildings and paths between
     *      buildings on the UW campus
     * @spec.effects Creates a new CampusMap
     */
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

    /**
     * Returns whether the given short name exists in the campus map
     *
     * @param shortName The short name of a building to query.
     * @return {@literal true} iff the short name provided exists in this campus map.
     */
    @Override
    public boolean shortNameExists(String shortName) {
        return buildings.containsKey(shortName);
    }

    /**
     * Returns the long name of the building corresponding to the given short name
     *
     * @param shortName The short name of a building to look up.
     * @return The long name of the building corresponding to the provided short name.
     * @throws IllegalArgumentException if the short name provided does not exist.
     */
    @Override
    public String longNameForShort(String shortName) {
        if (!shortNameExists(shortName)) {
            throw new IllegalArgumentException();
        }
        return buildings.get(shortName);
    }

    /**
     * Returns a map of all buildings such that the keySet is the short names of the buildings
     *      and the long names are the corresponding values
     *
     * @return A mapping from all the buildings' short names to their long names in this campus map.
     */
    @Override
    public Map<String, String> buildingNames() {
        Map<String, String> mapping = new HashMap<>();
        for (String s : buildings.keySet()) {
            mapping.put(s, buildings.get(s));
        }
        return mapping;
    }

    /**
     * Finds the shortest path, by distance, between the two provided buildings.
     *
     * @param startShortName The short name of the building at the beginning of this path.
     * @param endShortName   The short name of the building at the end of this path.
     * @return A path between {@code startBuilding} and {@code endBuilding}, or {@literal null}
     * if none exists.
     * @throws IllegalArgumentException if {@code startBuilding} or {@code endBuilding} are
     *                                  {@literal null}, or not valid short names of buildings in
     *                                  this campus map.
     */
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
