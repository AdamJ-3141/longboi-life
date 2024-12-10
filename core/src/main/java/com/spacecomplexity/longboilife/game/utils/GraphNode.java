package com.spacecomplexity.longboilife.game.utils;

import com.spacecomplexity.longboilife.game.building.Building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GraphNode {
    private HashMap<GraphNode, Integer>  connectedNodes;
    private final Building buildingRef;

    public GraphNode(Building buildingRef) {
        connectedNodes = new HashMap<>();
        this.buildingRef = buildingRef;
    }

    public Building getBuildingRef() {
        return buildingRef;
    }

    public void connectNode(GraphNode node, int distance) {
        connectedNodes.putIfAbsent(node, distance);
        if (!node.getConnectedNodes().containsKey(this)) {
            node.connectNode(this, distance);
        }
    }

    public HashMap<GraphNode, Integer> getConnectedNodes() {
        return connectedNodes;
    }

//    public void printFullGraph() {
//        printFullGraph(new ArrayList<>());
//    }
//
//    public void printFullGraph(ArrayList<GraphNode> nodes) {
//        System.out.println(this);
//        System.out.println("{");
//        for (GraphNode node : connectedNodes) {
//            if (!nodes.contains(node)) {
//                nodes.add(node);
//                node.printFullGraph(nodes);
//            }
//        }
//        System.out.println("}");
//    }
}
