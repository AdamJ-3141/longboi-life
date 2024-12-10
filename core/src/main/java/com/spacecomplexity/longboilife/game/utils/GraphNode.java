package com.spacecomplexity.longboilife.game.utils;

import java.util.HashMap;
import java.util.HashSet;

public class GraphNode {
    private HashSet<GraphNode>  connectedNodes;
    private final String nodeType;

    public GraphNode(String nodeType) {
        connectedNodes = new HashSet<>();
        this.nodeType = nodeType;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void connectNode(GraphNode node) {
        connectedNodes.add(node);
        node.connectNode(this);
    }

    public HashSet<GraphNode> getConnectedNodes() {
        return connectedNodes;
    }
}
