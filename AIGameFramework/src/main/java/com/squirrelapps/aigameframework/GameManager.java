package com.squirrelapps.aigameframework;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public final class GameManager
{
    // Container Activity must implement this interface
    public interface GameListener {
        public boolean onGameCreate(Game game);
        public boolean onGameUpdate(Game game);
    }

    final Game game;
    final GameAnalyzer gameAnalyzer;
    //TODO da capire se è Game che deve mantenerne un riferimento oppure GameManager
    final GameListener gameListener;

//    private static GameManager instance;
//
//    private GameManager()
//    {
//    }
//
//    public static synchronized GameManager getInstance()
//    {
//        if(instance == null){
//            instance = new GameManager();
//        }
//        return instance;
//    }

    //TODO in alternativa si potrebbe realizzare un metodo attach(Activity) in cui si verifica che implementi GameListener (altrimenti thows new ex)
    public GameManager(Game game, GameAnalyzer gameAnalyzer, GameListener gameListener)
    {
        this.game = game;
        this.gameAnalyzer = gameAnalyzer;
        this.gameListener = gameListener;
    }

    public Game getGame()
    {
        return game;
    }

//    public void setGame(Game game)
//    {
//        this.game = game;
//    }

    //TODO new (?), load & store (come parcelable/serializable/bundle nei settings > VD game di android)

    public GameManager startGame()
    {
        //TODO start GameRunner and notify
        return this;
    }

    public GameManager resumeGame()
    {
        //TODO resume GameRunner and notify
        return this;
    }

    public GameManager pauseGame()
    {
        //TODO pause GameRunner and notify
        return this;
    }

    public GameManager stopGame()
    {
        //TODO stop GameRunner and notify
        return this;
    }
}
