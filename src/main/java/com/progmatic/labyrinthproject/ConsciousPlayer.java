package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.util.List;
import java.util.Stack;


public class ConsciousPlayer implements Player {

    private Tree countingCells;
    private Tree.Node actualNode;
    private Stack<Tree.Node> plan = new Stack();


    @Override
    public Direction nextMove(Labyrinth l) {

        countingCells = new Tree(l.getStart());
        actualNode = countingCells.getStart();
        putChildrenCells(l, actualNode);
        Tree.Node lastNode = findNode(l.getEnd());
        plan.push(lastNode);
        for (int i = lastNode.getMinStepsToReach(); i >0 ; i--) {
            plan.push(lastNode.getParent());
            lastNode =lastNode.getParent();
        }

        return plan.pop().getCoordinate(); //Ebből még vissza lehet kapni a directiont, de sajnos nem volt rá időm.
    }


    private Tree.Node findNode(Coordinate nodeValue, Tree.Node node) {
        if (node.getCoordinate().equals(nodeValue)) return node;

        List<Tree.Node> children = node.getChildren();
        for (Tree.Node child : children) {
            //if (child.getName().equals(nodeValue)) return child;
            Tree.Node result = findNode(nodeValue, child);
            if (result != null) return result;
        }
        return null;
    }

    public Tree.Node findNode(Coordinate nodeValue) {

        Tree.Node node = findNode(nodeValue, countingCells.getStart());
        return node;
    }


    public void putChildrenCells(Labyrinth l, Tree.Node actual) {


        List<Direction> possibleMoves = l.possibleMoves(actual.getCoordinate());
        for (Direction possibleMove : possibleMoves) {
            Coordinate newcoord = newCoordinate(possibleMove, l);
            Tree.Node existingNode = findNode(newcoord);
            if (existingNode == null) {
                Tree.Node n = new Tree.Node(
                        newcoord,
                        actual.getMinStepsToReach() + 1,
                        actual);
                actual.getChildren().add(n);
                actual = n;
                putChildrenCells(l, n);
            } else if (existingNode.getMinStepsToReach() > actual.getMinStepsToReach() + 1) {
                existingNode.setMinStepsToReach(actual.getMinStepsToReach() + 1);
            }
        }
    }

    private Coordinate newCoordinate(Direction possibleMove, Labyrinth l) {

        int col = actualNode.getCoordinate().getCol();
        int row = actualNode.getCoordinate().getRow();
        if (possibleMove == Direction.NORTH && row > 0) {
            return new Coordinate(col, row - 1);
        } else if (possibleMove == Direction.SOUTH && row < l.getWidth() - 1) {
            return new Coordinate(col, row + 1);
        } else if (possibleMove == Direction.EAST && col < l.getHeight() - 1) {
            return new Coordinate(col + 1, row);
        } else if (possibleMove == Direction.WEST && col > 0) {
            return new Coordinate(col - 1, row);
        } else return null;
    }


}
