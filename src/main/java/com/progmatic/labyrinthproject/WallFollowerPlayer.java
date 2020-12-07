package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.util.List;

public class WallFollowerPlayer implements Player {

    private CellType onMyRight;
    private Direction pointer=null;
    private List<Direction> possibleMoves;




    @Override
    public Direction nextMove(Labyrinth l) {
        possibleMoves = l.possibleMoves();
        setUpPointer();

        if(possibleMoves.contains(whatIsOnMyRight())) {
            turnRight();
        }else if(possibleMoves.contains(pointer)) {
            return pointer;
        }else if (possibleMoves.contains(whatIsOnMyLeft())){
            turnLeft();
        } else {
            turnBack();
        }


        return pointer;
    }

    private void turnBack() {
        for (int i = 0; i < 2; i++) {
            turnRight();
        }
    }

    private void setUpPointer(){
       if(pointer==null){
           pointer = possibleMoves.get(0);
       }
    }

    private Direction whatIsOnMyRight(){
        if(pointer==Direction.NORTH) return Direction.EAST;
        else if(pointer==Direction.EAST) return Direction.SOUTH;
        else if(pointer==Direction.SOUTH) return Direction.WEST;
        else if(pointer==Direction.WEST) return Direction.NORTH;
        return null;
    }

    private Direction whatIsOnMyLeft(){
        if(pointer==Direction.NORTH) return Direction.WEST;
        else if(pointer==Direction.WEST) return Direction.SOUTH;
        else if(pointer==Direction.SOUTH) return Direction.EAST;
        else if(pointer==Direction.EAST) return Direction.NORTH;
        return null;
    }

    private void turnRight(){
        pointer = whatIsOnMyRight();
    }

    private void turnLeft(){
        for (int i = 0; i < 3; i++) {
            turnRight();
        }
    }


}
