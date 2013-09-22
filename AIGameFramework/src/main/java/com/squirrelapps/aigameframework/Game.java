package com.squirrelapps.aigameframework;

import java.util.List;

/**
 * Created by Francesco Vadicamo on 9/11/13.
 */
public abstract class Game
{
    final Board board;

    //List<GameStatus> gameHistory;
    //[] di players
    //game rules

    //TODO undoLastMove
    //TODO redoLastMove


    public Game(Board board)
    {
        this.board = board;
    }

    //TODO questo metodo potrebbe essere anche qui ma solo richiamando l'analyzer
    //public abstract byte[] firstGameStatus(); //TODO andrebbe forse spostato in un Game[Status]Builder
}
