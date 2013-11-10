package com.squirrelapps.aigameframework;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public abstract class Player
{
    final int id;
    final String name;

    public Player(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**Make the move using the specified <code>GameAnalyzer</code> and <code>GameStatus</code>*/
    public abstract GameStatus makeMove(GameAnalyzer gameAnalyzer, GameStatus gameStatus);
}
