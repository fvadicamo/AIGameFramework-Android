package com.squirrelapps.aigameframework;

import java.util.Collection;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public class HumanPlayer extends Player
{
    public HumanPlayer(int id, String name)
    {
        super(id, name);
    }

    @Override
    public GameStatus makeMove(GameAnalyzer gameAnalyzer, GameStatus newGameStatus, Collection<? extends Move> moves)
    {
//        GameStatus newGameStatus;
//        try{
//            newGameStatus = (GameStatus)gameStatus.clone(); //TODO il clone potrebbe essere fatto a monte, dal chiamante di makeMove
//        }catch(CloneNotSupportedException e){
//            throw new IllegalStateException("GameStatus not clonable");
//        }

//        Collection<? extends Move> moves = gameAnalyzer.playableMoves(newGameStatus, id);
        //playable moves.. le notifica e sceglie una tra quelle col tap

        try{
            Thread.sleep(200l);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        int random = (int)(Math.random()*moves.size());

        return gameAnalyzer.makeMove(newGameStatus, moves.toArray(new Move[moves.size()])[random]);
    }
}
