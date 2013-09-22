package com.squirrelapps.aigameframework;

/**
 * Created by Francesco Vadicamo on 9/8/13.
 */
public class Cell implements Cloneable, Comparable<Cell>
{
//    final byte x;
//    final byte y;
//
//    public Cell(byte x, byte y)
//    {
//        this.x = x;
//        this.y = y;
//    }

    final int x;
    final int y;

    public Cell(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    protected Object clone()
    {
        return new Cell(x, y);
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

//    //TODO si potrebbe ottimizzare realizzando un private byte[] di classe e restituire sempre quello!
//    //(forse però sarebbe non conferme al metodo toByteArray che dovrebbe creare sempre una nuova istanza, o no?) o_O'
//    public byte[] toByteArray()
//    {
//        return new byte[]{x, y};
//    }
}
