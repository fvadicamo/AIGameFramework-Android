package com.squirrelapps.aigameframework;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public class HumanPlayer extends Player
{
    public HumanPlayer(String name)
    {
        super(name);
    }

    @Override
    public GameStatus makeMove(GameAnalyzer gameAnalyzer, GameStatus gameStatus)
    {
        GameStatus newGameStatus;
        try{
            newGameStatus = (GameStatus)gameStatus.clone(); //TODO il clone potrebbe essere fatto a monte, dal chiamante di makeMove
        }catch(CloneNotSupportedException e){
            throw new IllegalStateException("GameStatus not clonable!"); //TODO check...
        }

        //playable moves.. le notifica e sceglie una tra quelle col tap

        return newGameStatus;
    }
}
