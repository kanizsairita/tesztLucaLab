package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.util.List;
import java.util.Stack;


public class ConsciousPlayer implements Player {

    private Tree countingCells;
    private Stack<Direction> plan = new Stack();





    @Override
    public Direction nextMove(Labyrinth l) {

        if(countingCells==null) {
            countingCells = new Tree(l.getStart());
            putChildrenNodes(l, countingCells.getStart());
            Tree.Node lastNode = countingCells.findFirstNode(l.getEnd());
            plan.push(lastNode.getDirection());
            for (int i = lastNode.getMinStepsToReach(); i > 0; i--) {
                plan.push(lastNode.getParent().getDirection());
                lastNode = lastNode.getParent();
            }
            plan.pop();
        }
        return plan.pop();
    }




    public void putChildrenNodes(Labyrinth l, Tree.Node node) {


        List<Direction> possibleMoves = l.possibleMoves(node.getCoordinate());
        for (Direction possibleMove : possibleMoves) {
            Coordinate possibleCoord = newCoordinate(node, possibleMove, l);
            if (!hasNodeThisCoordinateAsParent(possibleCoord, node)) {
                Tree.Node n = new Tree.Node(
                        possibleCoord,
                        node.getMinStepsToReach() + 1,
                        node,
                        possibleMove);
                countingCells.addAsChild(node, n);
                putChildrenNodes(l, n);
            }
        }
    }

    private boolean hasNodeThisCoordinateAsParent(Coordinate possibleCoord, Tree.Node node) {
        Tree.Node n = node;
        while (n.getParent()!=null){
            if(n.getParent().getCoordinate().equals(possibleCoord)) return true;
            n=n.getParent();
        }
        return false;
    }

    private Coordinate newCoordinate(Tree.Node actual, Direction possibleMove, Labyrinth l) {

        int col = actual.getCoordinate().getCol();
        int row = actual.getCoordinate().getRow();
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
