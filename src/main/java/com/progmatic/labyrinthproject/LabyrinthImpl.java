package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {


    private CellType[][] labyrinth;
    private int width;
    private int height;
    private Coordinate start;
    private Coordinate end;
    private Coordinate playerPosition;
    private List<Direction> possibleMoves;

    public LabyrinthImpl() {
        width = -1;
        height = -1;
        playerPosition = start;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public Coordinate getStart() {
        return start;
    }

    @Override
    public Coordinate getEnd() {
        return end;
    }

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            width = Integer.parseInt(sc.nextLine());
            height = Integer.parseInt(sc.nextLine());

            labyrinth = new CellType[width][height];

            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    switch (line.charAt(ww)) {
                        case 'W':
                            labyrinth[hh][ww] = CellType.WALL;
                            break;
                        case 'E':
                            labyrinth[hh][ww] = CellType.END;
                            end = new Coordinate(ww, hh);
                            break;
                        case 'S':
                            labyrinth[hh][ww] = CellType.START;
                            start = new Coordinate(ww, hh);
                            playerPosition = start;
                            break;
                        default:
                            labyrinth[hh][ww] = CellType.EMPTY;
                            break;

                    }
                }
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {
        checkCoordinatesThrowException(c);
        return labyrinth[c.getRow()][c.getCol()];
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        if (height != 0 && width != 0) {
            labyrinth = new CellType[width][height];
        }
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[width - 1].length; j++) {
                try {
                    setCellType(new Coordinate(j, i), CellType.EMPTY);
                } catch (CellException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        checkCoordinatesThrowException(c);
        labyrinth[c.getRow()][c.getCol()] = type;
        if (type == CellType.START) {
            playerPosition = c;
        }
    }

    private void checkCoordinatesThrowException(Coordinate c) throws CellException {
        if (c.getCol() < 0 || c.getCol() >= width || c.getRow() < 0 || c.getRow() >= height) {
            throw new CellException(c, "Az oszlopok, vagy a sorok száma nem megfelelő.");
        }
    }

    @Override
    public Coordinate getPlayerPosition() {
        return playerPosition;
    }

    @Override
    public boolean hasPlayerFinished() {
        if (labyrinth[playerPosition.getRow()][playerPosition.getCol()] == CellType.END) return true;
        else return false;
    }

    @Override
    public List<Direction> possibleMoves() {
        Coordinate coord = getPlayerPosition();
        List<Direction> directions = possibleMoves(coord);
        return directions;
    }

    public List<Direction> possibleMoves(Coordinate coord) {
        possibleMoves = new ArrayList<>();
        int r = coord.getRow();
        int c = coord.getCol();
        if (tryMoveNorth(r, c)) possibleMoves.add(Direction.NORTH);
        if (tryMoveSouth(r, c)) possibleMoves.add(Direction.SOUTH);
        if (tryMoveWest(r, c)) possibleMoves.add(Direction.WEST);
        if (tryMoveEast(r, c)) possibleMoves.add(Direction.EAST);
        return possibleMoves;
    }




    private boolean tryMoveNorth(int r, int c) {
        if (r > 1) {
            CellType up = labyrinth[r - 1][c];
            if (up != null && !up.equals(CellType.WALL)) return true;
        }
        return false;
    }

    private boolean tryMoveSouth(int r, int c) {
        if (r < width - 1) {
            CellType down = labyrinth[r + 1][c];
            if (down != null && !down.equals(CellType.WALL)) return true;
        }
        return false;
    }

    private boolean tryMoveEast(int r, int c) {
        if (c < height - 1) {
            CellType right = labyrinth[r][c + 1];
            if (right != null && !right.equals(CellType.WALL)) return true;
        }
        return false;
    }

    private boolean tryMoveWest(int r, int c) {
        if (c > 0) {
            CellType left = labyrinth[r][c - 1];
            if (left != null && !left.equals(CellType.WALL)) return true;
        }
        return false;
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {
        possibleMoves = possibleMoves();
        if (possibleMoves.contains(direction)) {
            moveToDirection(direction);
        } else throw new InvalidMoveException();
    }

    private void moveToDirection(Direction direction) throws InvalidMoveException {
        int col = playerPosition.getCol();
        int row = playerPosition.getRow();
        if (direction == Direction.NORTH && row > 0) {
            playerPosition = new Coordinate(col, row - 1);
        } else if (direction == Direction.SOUTH && row < width - 1) {
            playerPosition = new Coordinate(col, row + 1);
        } else if (direction == Direction.EAST && col < height - 1) {
            playerPosition = new Coordinate(col + 1, row);
        } else if (direction == Direction.WEST && col > 0) {
            playerPosition = new Coordinate(col - 1, row);
        } else throw new InvalidMoveException();
    }


}
