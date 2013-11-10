package com.squirrelapps.aigameframework;

import java.util.Set;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public abstract class Board
{
//    public static enum Direction{
//        E,NE,NNE,N,NNW,NW,W,SW,SSW,S,SSE,SE
//    }

    protected final int xDim;
    protected final int yDim;
    protected final int size;

    //TODO sarebbe meglio utilizzare un array monodimensionale (in questo modo sarebbe estendibile ad n dimensioni)
    protected final Cell[][] cells;

    public Board(final int xDim, final int yDim)
    {
        this(new Cell[xDim][yDim], xDim, yDim);

        for(int x=0; x<xDim; x++){
            for(int y=0; y<yDim; y++){
                cells[x][y] = new Cell(x, y);
            }
        }
    }

    public Board(Cell[][] cells)
    {
        this(cells, cells.length, cells[0].length);
    }

    public Board(Cell[][] cells, final int xDim, final int yDim)
    {
        this.xDim = xDim;
        this.yDim = yDim;

        this.size = xDim*yDim;

        this.cells = cells;
    }

    public int getSize()
    {
        return /*xDim*yDim*/size;
    }

    //TODO distanza [di Manhattan] tra due celle
    //public int distance(Cell c1, Cell c2)

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
