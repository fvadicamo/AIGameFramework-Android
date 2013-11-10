package com.squirrelapps.aigameframework;

import org.apache.http.client.utils.CloneUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public class SimpleBoardStatus implements BoardStatus//, Cloneable
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
    public Object clone() throws CloneNotSupportedException
    {
        final SimpleBoardStatus bs = (SimpleBoardStatus)super.clone();

        //REMIND Second statement of clone() suggest that original and cloned objects should have same class type, but it is not mandatory
        final Map<Cell, Piece> pm;
//        if(piecesMap instanceof HashMap)
//            pm = (Map<Cell, Piece>)( (HashMap<Cell, Piece>)piecesMap ).clone();
//        else if(piecesMap instanceof TreeMap)
//            pm = (Map<Cell, Piece>)( (TreeMap<Cell, Piece>)piecesMap ).clone();
//        else
//            pm = new HashMap<Cell, Piece>(piecesMap);
        pm = (Map<Cell, Piece>)CloneUtils.clone(this.piecesMap);
        bs.setPiecesMap(pm);

        //REMIND Second statement of clone() suggest that original and cloned objects should have same class type, but it is not mandatory
        final Set<Cell> ac;
//        if(availableCells instanceof HashSet)
//            ac = (Set<Cell>)( (HashSet<Cell>)availableCells ).clone();
//        else if(availableCells instanceof TreeSet)
//            ac = (Set<Cell>)( (TreeSet<Cell>)availableCells ).clone();
//        else
//            ac = new HashSet<Cell>(availableCells);
        ac = (HashSet<Cell>)CloneUtils.clone(this.availableCells);
        bs.setAvailableCells(ac);

        return bs;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj == this){
            return true;
        }
        if(obj == null || !(obj instanceof SimpleBoardStatus)){
            return false;
        }

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
