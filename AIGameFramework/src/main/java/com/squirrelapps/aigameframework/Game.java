package com.squirrelapps.aigameframework;

import java.util.LinkedList;
import java.util.List;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public abstract class Game
{
    protected final Board board;

    protected final Player[] players;

    //TODO si potrebbe realizzare una classe apposita GameHistory con tutti i metodi necessari anche per ricercare all'interno dei GameStatus, etc..
    protected final LinkedList<GameStatus> gameHistory;

    public Game(Board board, Player[] players, GameStatus firstGameStatus)
    {
        this.board = board;
        this.players = players;

        this.gameHistory = new LinkedList<GameStatus>();
        gameHistory.add(firstGameStatus);
    }

    public Board getBoard()
    {
        return board;
    }

    public Player[] getPlayers()
    {
        return players;
    }

    public List<GameStatus> getGameHistory()
    {
        return gameHistory;
    }

    //TODO undoLastMove > va in GameManager? > prob. sì visto che c'è da annullare una move e serve l'analyzer
    //TODO redoLastMove > va in GameManager? > prob. sì visto che c'è da fare una move e serve l'analyzer
}
