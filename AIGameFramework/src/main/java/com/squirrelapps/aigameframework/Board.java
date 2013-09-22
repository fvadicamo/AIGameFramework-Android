package com.squirrelapps.aigameframework;

import java.util.Set;

/**
 * Created by Francesco Vadicamo on 9/8/13.
 */
public abstract class Board
{
//    public static enum Direction{
//        E,NE,NNE,N,NNW,NW,W,SW,SSW,S,SSE,SE
//    }

    protected final int xDim;
    protected final int yDim;
    protected final int size;

    protected Cell[][] cells;

    public Board(final int xDim, final int yDim)
    {
        this.xDim = xDim;
        this.yDim = yDim;

        this.size = xDim*yDim;

        this.cells = new Cell[xDim][yDim];
        for(int x=0; x<xDim; x++){
            for(int y=0; y<yDim; y++){
                cells[x][y] = new Cell(x, y);
            }
        }
    }

    public int getSize()
    {
        return /*xDim*yDim*/size;
    }

//    TODO distanza [di Manhattan] tra due celle
//    public int distance(Cell c1, Cell c2)

    public boolean isValidPosition(int x, int y)
    {
        return x >= 0 && y >= 0 && x < xDim && y < yDim;
    }

    public Set<Cell> neighbors(Cell cell)
    {
        return neighbors(cell, 1);
    }

    public abstract Set<Cell> neighbors(Cell cell, int distance);

    public abstract Set<Cell> borderNeighbors(Cell cell, int distance);
}
