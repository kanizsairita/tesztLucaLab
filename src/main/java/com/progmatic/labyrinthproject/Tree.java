package com.progmatic.labyrinthproject;

import java.util.ArrayList;
import java.util.List;


public class Tree<T> {

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

    public static class Node {
        private Coordinate coordinate;
        private int minStepsToReach;
        private Node parent;
        private List<Node> children;

        public Node(Coordinate coordinate, int minStepsToReach, Node parent) {
            this.coordinate = coordinate;
            this.minStepsToReach = minStepsToReach;
            this.parent = parent;
        }

        public Node() {
        }

        public int getMinStepsToReach() {
            return minStepsToReach;
        }

        public void setMinStepsToReach(int minStepsToReach) {
            this.minStepsToReach = minStepsToReach;
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
