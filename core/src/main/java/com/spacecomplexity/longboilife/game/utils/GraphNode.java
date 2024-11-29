package com.spacecomplexity.longboilife.game.utils;

import java.util.HashMap;

public class GraphNode {
    private HashMap<GraphNode,Integer>  connectedNodes;
    private final String nodeType;

    public GraphNode(String nodeType) {
        connectedNodes = new HashMap<GraphNode,Integer>();
        this.nodeType = nodeType;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void connectNode(GraphNode node, Integer distance) {
        if (connectedNodes.containsKey(node)) {
            if (connectedNodes.get(node) > distance) {
                connectedNodes.put(node, distance);
            }
        } else {
            connectedNodes.put(node, distance);
            node.connectNode(this, distance);
        }
    }

    public HashMap<GraphNode,Integer> getConnectedNodes() {
        return connectedNodes;
    }
}
