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
public class SimplePlayerStatus implements PlayerStatus, Cloneable
{
    /** Available player pieces*/
    protected Set<Piece> availablePieces;

    /**Cell mapping foreach piece*/
    protected Map<Piece, Cell> cellsMap;

    public SimplePlayerStatus()
    {
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    protected Object clone() throws CloneNotSupportedException
    {
        SimplePlayerStatus ps = new SimplePlayerStatus();
        Map<Piece, Cell> newCellsMap;
        if(cellsMap instanceof HashMap) //TODO semplificare o usare reflection
            newCellsMap = (Map<Piece, Cell>)( (HashMap<Piece, Cell>)cellsMap ).clone();
        else if(cellsMap instanceof TreeMap)
            newCellsMap = (Map<Piece, Cell>)( (TreeMap<Piece, Cell>)cellsMap ).clone();
        else
            newCellsMap = new HashMap<Piece, Cell>(cellsMap);

        Set<Piece> newAvailablePieces;
        if(availablePieces instanceof HashSet) //TODO semplificare o usare reflection
            newAvailablePieces = (Set<Piece>)( (HashSet<Piece>)availablePieces ).clone();
        else if(availablePieces instanceof TreeSet)
            newAvailablePieces = (Set<Piece>)( (TreeSet<Piece>)availablePieces ).clone();
        else
            newAvailablePieces = new HashSet<Piece>(availablePieces);

        ps.setAvailablePieces(newAvailablePieces);
        ps.setCellsMap(newCellsMap);
//        ps.setAlive(alive);
//        ps.setLifes(lifes);
//        ps.setPenalities(penalities);

        return ps;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj)
    {
        if(obj == null || !(obj instanceof SimplePlayerStatus))
            return false;

        if(obj == this)
            return true;

        SimplePlayerStatus ps = (SimplePlayerStatus)obj;
        return ps.getAvailablePieces().equals(getAvailablePieces()) && ps.getCellsMap().equals(getCellsMap());
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        int result = 1;
        result = 31 * result + availablePieces.hashCode();
        result = 31 * result + cellsMap.hashCode();

        return result;
    }

//TODO toString
//    /* (non-Javadoc)
//     * @see java.lang.Object#toString()
//     */
//    public String toString()
//    {
//        return "";
//    }

    public Set<Piece> getAvailablePieces()
    {
        return availablePieces;
    }

    public void setAvailablePieces(Set<Piece> availablePieces)
    {
        this.availablePieces = availablePieces;
    }

    public Map<Piece, Cell> getCellsMap()
    {
        return cellsMap;
    }

    public void setCellsMap(Map<Piece, Cell> cellsMap)
    {
        this.cellsMap = cellsMap;
    }

    /**
     * Return all pieces already played by players
     *
     * @return set of pieces already played by players
     */
    public Set<Piece> playedPieces()
    {
        return getCellsMap().keySet();
    }

    /**
     * Return all cells occupied by players
     *
     * @return set of cells occupied by players
     */
    public Set<Cell> playedCells()
    {
        return new HashSet<Cell>(getCellsMap().values());
    }

    /**
     * Return all player pieces (available + played)
     *
     * @return set of all player pieces (available + played)
     */
    public Set<Piece> allPieces()
    {
        Set<Piece> allCells = new HashSet<Piece>(100); //100 > 70/0.75 --> no rehash!
        allCells.addAll(getAvailablePieces());
        allCells.addAll(playedPieces());
        return allCells;
    }
}
