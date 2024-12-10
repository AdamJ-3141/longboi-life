package com.spacecomplexity.longboilife.game.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GraphNode {
    private HashSet<GraphNode>  connectedNodes;
    public final Vector2Int position;
    private final String nodeType;

    public GraphNode(int x, int y) {
        connectedNodes = new HashSet<>();
        this.position = new Vector2Int(x, y);
        this.nodeType = "Pathway";
    }

    public GraphNode(int x, int y, String nodeType) {
        connectedNodes = new HashSet<>();
        this.position = new Vector2Int(x, y);
        this.nodeType = nodeType;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void connectNode(GraphNode node) {
        boolean unique = connectedNodes.add(node);
        if (unique) {node.connectNode(this);}
    }

    public HashSet<GraphNode> getConnectedNodes() {
        return connectedNodes;
    }
    public String toString() {
        return nodeType + " at " + position.toString();
    }

    public void printFullGraph() {
        printFullGraph(new ArrayList<>());
    }

    public void printFullGraph(ArrayList<GraphNode> nodes) {
        System.out.println(this);
        System.out.println("{");
        for (GraphNode node : connectedNodes) {
            if (!nodes.contains(node)) {
                nodes.add(node);
                node.printFullGraph(nodes);
            }
        }
        System.out.println("}");
    }
}
