package com.squirrelapps.aigameframework;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public class SquareBoard extends Board
{
//    public static final short E = 0;
//    public static final short NE = 45;
//    public static final short NNE = 60;   N2E
//    public static final short N = 90;
//    public static final short NNW = 120;  N2W
//    public static final short NW = 135;
//    public static final short W = 180;
//    public static final short SW = 225;
//    public static final short SSW = 240;  S2W
//    public static final short S = 270;
//    public static final short SSE = 300;  S2E
//    public static final short SE = 315;

    public SquareBoard(int xDim, int yDim)
    {
        super(xDim, yDim);
    }

    private void insertCellIfValidPosition(int x, int y, Set<Cell> cellSet)
    {
        if(isValidPosition(x, y)){
            cellSet.add(cells[x][y]);
        }
    }

    @Override
    public Set<Cell> neighbors(Cell cell, int distance)
    {
        if(distance <= 0){
            return Collections.emptySet();
        }

        Set<Cell> neighbors = new HashSet(8*distance*10/8); //avoiding rehash FIXME va ricalcolato: così considera solo i borderNeighbors
        final int x = cell.x;
        final int y = cell.y;

        for(int dx = 1; dx <= distance; dx++){
            insertCellIfValidPosition(x+dx, y, neighbors);          //E*
            insertCellIfValidPosition(x-dx, y, neighbors);          //W*

            for(int dy = 1; dy <= distance; dy++){
//                insertCellIfValidPosition(x, y+dy, neighbors);      //N*
//                insertCellIfValidPosition(x, y-dy, neighbors);      //S*

                insertCellIfValidPosition(x+dx, y+dy, neighbors);   //N*E*
                insertCellIfValidPosition(x+dx, y-dy, neighbors);   //S*E*
                insertCellIfValidPosition(x-dx, y-dy, neighbors);   //S*W*
                insertCellIfValidPosition(x-dx, y+dy, neighbors);   //N*W*
            }
        }

        for(int dy = 1; dy <= distance; dy++){
            insertCellIfValidPosition(x, y+dy, neighbors);          //N*
            insertCellIfValidPosition(x, y-dy, neighbors);          //S*
        }

        return neighbors;
    }

    @Override
    public Set<Cell> borderNeighbors(Cell cell, int distance)
    {
        if(distance <= 0){
            return Collections.emptySet();
        }

        Set<Cell> neighbors = new HashSet(8*distance*10/8); //avoiding rehash
        final int x = cell.x;
        final int y = cell.y;

        int dx, dy;

        dx = distance;
        insertCellIfValidPosition(x+dx, y, neighbors);          //E*
        insertCellIfValidPosition(x-dx, y, neighbors);          //W*

        for(dy = 1; dy <= distance; dy++){
            insertCellIfValidPosition(x+dx, y+dy, neighbors);   //N*E*
            insertCellIfValidPosition(x+dx, y-dy, neighbors);   //S*E*
            insertCellIfValidPosition(x-dx, y-dy, neighbors);   //S*W*
            insertCellIfValidPosition(x-dx, y+dy, neighbors);   //N*W*
        }

        dy = distance;
        insertCellIfValidPosition(x, y+dy, neighbors);          //N*
        insertCellIfValidPosition(x, y-dy, neighbors);          //S*
        for(dx = 1; dx <= distance; dx++){
            insertCellIfValidPosition(x+dx, y+dy, neighbors);   //N*E*
            insertCellIfValidPosition(x+dx, y-dy, neighbors);   //S*E*
            insertCellIfValidPosition(x-dx, y-dy, neighbors);   //S*W*
            insertCellIfValidPosition(x-dx, y+dy, neighbors);   //N*W*
        }

        return neighbors;
    }
}
