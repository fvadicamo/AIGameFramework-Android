package com.squirrelapps.aigameframework;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public abstract class Player
{
    final String name;

    public Player(String name)
    {
        this.name = name;
    }

    /**Make the move using the specified <code>GameAnalyzer</code> and <code>GameStatus</code>*/
    public abstract GameStatus makeMove(GameAnalyzer gameAnalyzer, GameStatus gameStatus);
}
