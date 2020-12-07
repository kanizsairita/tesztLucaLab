package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;

import java.util.ArrayList;
import java.util.List;


public class Tree {

    private Node start;

    public Node getStart() {
        return start;
    }

    public Tree(Coordinate coordinate) {
        start = new Node();
        start.coordinate = coordinate;
        start.minStepsToReach =0;
        start.children = new ArrayList<Node>();


    }

    private Tree.Node findFirstNode(Coordinate nodeValue, Tree.Node node) {
        if (node.getCoordinate().equals(nodeValue)) return node;

        List<Tree.Node> children = node.getChildren();
        for (Tree.Node child : children) {
            //if (child.getName().equals(nodeValue)) return child;
            Tree.Node result = findFirstNode(nodeValue, child);
            if (result != null) return result;
        }
        return null;
    }

    public Tree.Node findFirstNode(Coordinate nodeValue) {

        Tree.Node node = findFirstNode(nodeValue, start);
        return node;
    }


    public Node addAsChild(Node parent, Node child) {
        parent.getChildren().add(child);
        return child;

    }

    public static class Node {
        private Coordinate coordinate;
        private int minStepsToReach;
        private Node parent;
        private List<Node> children;
        private Direction direction;

        public Node(Coordinate coordinate, int minStepsToReach, Node parent, Direction direction) {
            this.direction = direction;
            this.coordinate = coordinate;
            this.minStepsToReach = minStepsToReach;
            this.parent = parent;
            children = new ArrayList<>();
        }

        public Node() {
        }

        public int getMinStepsToReach() {
            return minStepsToReach;
        }

        public void setMinStepsToReach(int minStepsToReach) {
            this.minStepsToReach = minStepsToReach;
        }

        public Direction getDirection() {
            return direction;
        }

        public List<Node> getChildren() {
            return children;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public Node getParent() {
            return parent;
        }
    }
}
