package com.squirrelapps.aigameframework;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public class Cell implements Cloneable, Comparable<Cell>
{
    final int x;
    final int y;

    public Cell(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Cell(Cell c)
    {
        this.x = c.x;
        this.y = c.y;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        return new Cell(/*this*/x, y);
    }

    @Override
    public boolean equals(Object o)
    {
        try{
            Cell c = (Cell)o;
            return this.x == c.x && this.y == c.y;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public int hashCode()
    {
        int result = 1;
        result = 31 * result + x;
        result = 31 * result + y;

        return result;
    }

    @Override
    public String toString()
    {
        return new StringBuilder(9/*max 3 digits expected*/).append("(").append(x).append(",").append(y).append(")").toString();
    }

    @Override
    public int compareTo(Cell cell)
    {
        if(this.x == cell.x){
            if(this.y == cell.y){
                return 0;
            }else if(this.y > cell.y){
                return 1;
            }else{ //this.y < cell.y
                return -1;
            }
        }else if(this.x > cell.x){
            return 1;
        }else{ //this.x < cell.x
            return -1;
        }
    }
}
