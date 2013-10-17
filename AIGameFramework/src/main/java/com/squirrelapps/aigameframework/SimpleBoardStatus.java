package com.squirrelapps.aigameframework;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public class SimpleBoardStatus implements BoardStatus, Cloneable
{
    /** Available board cells*/
    protected Set<Cell> availableCells;

    /**Piece mapping foreach cell*/
    protected Map<Cell, Piece> piecesMap;

    public SimpleBoardStatus()
    {
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        SimpleBoardStatus bs = new SimpleBoardStatus(); //(SimpleBoardStatus)super.clone();
        Map<Cell, Piece> newPiecesMap;
        if(piecesMap instanceof HashMap) //TODO semplificare o usare reflection
            newPiecesMap = (Map<Cell, Piece>)( (HashMap<Cell, Piece>)piecesMap ).clone();
        else if(piecesMap instanceof TreeMap)
            newPiecesMap = (Map<Cell, Piece>)( (TreeMap<Cell, Piece>)piecesMap ).clone();
        else
            newPiecesMap = new HashMap<Cell, Piece>(piecesMap);

        Set<Cell> newAvailableCells;
        if(availableCells instanceof HashSet) //TODO semplificare o usare reflection
            newAvailableCells = (Set<Cell>)( (HashSet<Cell>)availableCells ).clone();
        else if(availableCells instanceof TreeSet)
            newAvailableCells = (Set<Cell>)( (TreeSet<Cell>)availableCells ).clone();
        else
            newAvailableCells = new HashSet<Cell>(availableCells);

        bs.setPiecesMap(newPiecesMap);
        bs.setAvailableCells(newAvailableCells);
        return bs;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if( obj == null || !(obj instanceof SimpleBoardStatus) )
            return false;

        if( obj == this )
            return true;

        SimpleBoardStatus bs = (SimpleBoardStatus)obj;
        return bs.getAvailableCells().equals(availableCells) && bs.getPiecesMap().equals(piecesMap);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int result = 1;
        result = 31 * result + availableCells.hashCode();
        result = 31 * result + piecesMap.hashCode();

        return result;
    }

//TODO toString
//    /* (non-Javadoc)
//     * @see java.lang.Object#toString()
//     */
//    @Override
//    public String toString()
//    {
//        return "...";
//    }

    public Set<Cell> getAvailableCells()
    {
        return availableCells;
    }

    public void setAvailableCells(Set<Cell> availableCells)
    {
        this.availableCells = availableCells;
    }

    public Map<Cell, Piece> getPiecesMap()
    {
        return piecesMap;
    }

    public void setPiecesMap(Map<Cell, Piece> piecesMap)
    {
        this.piecesMap = piecesMap;
    }

    /**
     * Return all pieces already played by players
     *
     * @return set of pieces already played by players
     */
    public Set<Piece> playedPieces()
    {
        return new HashSet<Piece>(getPiecesMap().values());
    }

    /**
     * Return all cells occupied by players
     *
     * @return set of cells occupied by players
     */
    public Set<Cell> playedCells()
    {
        return getPiecesMap().keySet();
    }

    /**
     * Return all board cells (available + played)
     *
     * @return set of all board cells (available + played)
     */
    public Set<Cell> allCells()
    {
        Set<Cell> allCells = new HashSet<Cell>(100); //100 > 70/0.75 --> no rehash!
        allCells.addAll(getAvailableCells());
        allCells.addAll(playedCells());
        return allCells;
    }
}
