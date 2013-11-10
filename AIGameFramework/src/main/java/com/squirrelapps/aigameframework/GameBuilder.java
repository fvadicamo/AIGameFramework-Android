package com.squirrelapps.aigameframework;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public interface GameBuilder
{
    public Game getGame();

    public GameBuilder buildBoard(/*int xDim, int yDim*/);

    //public GameBuilder buildPlayer(/*int type, */String name);
    public GameBuilder buildHumanPlayer(int id, String name);
    public GameBuilder buildComputerPlayer(int id, String name, SearchAlgorithm searchAlgorithm, int maxDept);

    public GameBuilder buildFirsGameStatus(int firstPlayerId);
}
