package com.squirrelapps.aigameframework;

import java.util.Set;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public class OthiMove extends Move
{
    final int playerId;
    final Piece piece;
    final Cell cell;

    Set<Piece> flippedCells;

    public OthiMove(int id, int playerId, Piece piece, Cell cell)
    {
        super(id);

        this.playerId = playerId;
        this.piece = piece;
        this.cell = cell;
    }

    @Override
    public OthiMove clone() throws CloneNotSupportedException
    {
        return (OthiMove) super.clone();
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
            return true;

        if(!(o instanceof OthiMove))
            return false;

        OthiMove move = (OthiMove) o;

        if(id != move.id)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return new StringBuilder(OthiMove.class.getSimpleName()).append('(').append(id).append(')').toString();
    }
}
