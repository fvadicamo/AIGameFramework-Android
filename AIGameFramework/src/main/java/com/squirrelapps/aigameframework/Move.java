package com.squirrelapps.aigameframework;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public class Move implements Cloneable
{
    final String name;

    public Move(String name)
    {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        final Move m = (Move)super.clone();
        //TODO clone()...

        return m;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(!(o instanceof Move)) return false;

        Move move = (Move) o;

        if(!name.equals(move.name)) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }
}
