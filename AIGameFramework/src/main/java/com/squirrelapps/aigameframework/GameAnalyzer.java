package com.squirrelapps.aigameframework;

import java.util.Collection;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public abstract class GameAnalyzer
{
    final Game game;

    public GameAnalyzer(Game game)
    {
        this.game = game;
    }

    public abstract byte[] firstGameStatus(); //TODO andrebbe forse spostato in Game[Status]Builder

    public abstract int currentPlayer(byte[] gameStatus);
    public abstract int nextPlayer(byte[] gameStatus);

//    public abstract Collection<byte[]> currentPlayableMoves(byte[] gameStatus);

    public abstract Collection<byte[]> playableMoves(byte[] gameStatus, int playerId);

    public abstract byte[] makeMove(byte[] gameStatus, byte[] move);

    //public abstract int[] playerScores();
    public abstract int[] playersScores(byte[] gameStatus);

    public int playerScore(byte[] gameStatus, int playerId)
    {
        return playersScores(gameStatus)[playerId];
    }

    public abstract boolean isFinalGameStatus(byte[] gameStatus);
}
