package com.squirrelapps.aigameframework;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public interface GameBuilder
{
    public Game getGame();

    public GameBuilder buildBoard(int xDim, int yDim);

    //public GameBuilder buildPlayer(/*int type, */String name);
    public GameBuilder buildHumanPlayer(String name);
    public GameBuilder buildComputerPlayer(String name, SearchAlgorithm searchAlgorithm, int maxDept);

    public GameBuilder buildFirsGameStatus(int firstPlayerId);
}
