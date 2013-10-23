package com.squirrelapps.aigameframework;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public abstract class Piece //TODO implements Cloneable
{
    protected final int id;

    public Piece(int id)
    {
        this.id = id;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return (Piece)super.clone();
    }

    @Override
    public boolean equals(Object o)
    {
        return (o instanceof Piece) && (((Piece) o).id == id);
    }

    @Override
    public int hashCode()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return new StringBuilder(Piece.class.getSimpleName()).append('(').append(id).append(')').toString();
    }
}
