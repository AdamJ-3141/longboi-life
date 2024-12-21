package com.spacecomplexity.longboilife.game.utils;

import com.spacecomplexity.longboilife.game.building.Building;

import java.util.HashMap;

/**
 * A class representing a graph's node.
 */
public class GraphNode {
    private final HashMap<GraphNode, Integer>  connectedNodes;
    private final Building buildingRef;

    /**
     * Initialise the node with a connected building.
     * @param buildingRef   The building that the node is representing.
     */
    public GraphNode(Building buildingRef) {
        connectedNodes = new HashMap<>();
        this.buildingRef = buildingRef;
    }

    public Building getBuildingRef() {
        return buildingRef;
    }

    /**
     * Connect a node to another node, and vice versa.
     * @param node      The node to be connected.
     * @param distance  The distance between the nodes.
     */
    public void connectNode(GraphNode node, int distance) {
        connectedNodes.putIfAbsent(node, distance);
        if (!node.getConnectedNodes().containsKey(this)) {
            node.connectNode(this, distance);
        }
    }

    public HashMap<GraphNode, Integer> getConnectedNodes() {
        return connectedNodes;
    }

}
