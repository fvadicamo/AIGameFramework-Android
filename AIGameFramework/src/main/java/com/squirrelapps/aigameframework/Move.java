package com.squirrelapps.aigameframework;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public class Move/*<T extends Enum>*/ implements Cloneable
{
    //public static final Move NO_MOVE = new Move(0);

    protected final int id;

//    final T type;

//    final String name;

    public Move(int id/*, T type*/)
    {
        this.id = id;

//        this.type = type;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return (Move)super.clone();
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
            return true;

        if(!(o instanceof Move))
            return false;

        Move move = (Move) o;

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
        return new StringBuilder(Move.class.getSimpleName()).append('(').append(id).append(')').toString();
    }
}
