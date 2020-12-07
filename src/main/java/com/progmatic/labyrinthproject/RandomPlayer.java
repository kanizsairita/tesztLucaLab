package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.util.List;
import java.util.Random;

public class RandomPlayer implements Player {

    private List<Direction> possibleMoves;

    @Override
    public Direction nextMove(Labyrinth l) {
        possibleMoves = l.possibleMoves();
        Random random = new Random();
        int i = random.nextInt(possibleMoves.size());
        return possibleMoves.get(i);

    }
}
