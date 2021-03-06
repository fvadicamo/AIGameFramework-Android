package com.squirrelapps.aigameframework;

import android.util.Log;

import java.util.Collection;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public final class GameManager implements Runnable
{
    private static final String TAG = GameManager.class.getSimpleName();

    public static enum GameRunnerState{
        Ready,
        Playing,
        Paused,
        Stopped,
    }

    // Container Activity must implement this interface
    public interface GameListener {
//        public void onGameCreate(Game game, GameRunnerState gameRunnerState);
        public void onGameUpdate(Game game, GameRunnerState gameRunnerState);
        public void onMovesAvailable(Collection<? extends Move> moves);
    }

    final Game game;
    final GameAnalyzer gameAnalyzer;
    //TODO da capire se è Game che deve mantenerne un riferimento oppure GameManager
    final GameListener gameListener;

    Thread gameRunner;
    GameRunnerState gameRunnerState;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

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

    //TODO in realtà lo stesso GamaManager potrebbe essere usato su più game (new game non dovrebbe richiedere un nuovo manager)
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
        if(gameRunner != null && gameRunner.isAlive()){
            //throw new IllegalStateException("Game already started"); //REMIND startGame may be called more times
            Log.w(TAG, "Game already started");
            return this;
        }

//        synchronized(game){
        lock.lock();
        try{
            gameRunner = new Thread(this, "GameRunner");
            //TODO set thread priority
            gameRunnerState = GameRunnerState.Ready;
//            gameListener.onGameCreate(game, gameRunnerState);

            gameRunner.start();
        }finally{
            lock.unlock();
        }
        return this;
    }

    public GameManager resumeGame()
    {
//        synchronized(game){
        lock.lock();
        try{
            if(gameRunnerState == GameRunnerState.Paused){
                gameRunnerState = GameRunnerState.Playing;
            }
//            game.notifyAll();
            condition.signalAll();
        }finally{
            lock.unlock();
        }
        return this;
    }

    public GameManager pauseGame()
    {
//        synchronized(game){
        lock.lock();
        try{
            if(gameRunnerState == GameRunnerState.Playing){
                gameRunnerState = GameRunnerState.Paused;
            }
//            game.notifyAll();
            condition.signalAll();
        }finally{
            lock.unlock();
        }
        return this;
    }

    public GameManager stopGame()
    {
        lock.lock();
        try{
            if(gameRunnerState != GameRunnerState.Stopped){
                gameRunnerState = GameRunnerState.Stopped;
            }
//            game.notifyAll();
            condition.signalAll();

            if(gameRunner != null && !gameRunner.isInterrupted()){
                gameRunner.interrupt();
            }
        }finally{
            lock.unlock();
        }
        return this;
    }

    @Override
    public void run()
    {
        for(Thread t=Thread.currentThread(); t != gameRunner || Thread.interrupted(); ){
            return;
        }

        try{
            GameStatus gameStatus = game.gameHistory.getLast(); //REMIND could be the first one or a saved game status
//            Move move;
            int playerId;
            Player player;
            while(!gameStatus.isGameOver() /*!gameAnalyzer.isFinalGameStatus(gameStatusCoded)*/ && gameRunner != null && !gameRunner.isInterrupted()){
//                synchronized(game){
                lock.lock();
                try{
//                    while(gameRunnerState != GameRunnerState.Playing){
//                        game.wait();
//                    }

                    gameListener.onGameUpdate(game, gameRunnerState);

                    switch(gameRunnerState){
                        case Ready:
                            gameRunnerState = GameRunnerState.Playing;
                            break;

                        case Playing:
                            playerId = gameStatus.getNextPlayer();
                            player = game.players[playerId];

                            GameStatus newGameStatus;
                            try{
                                newGameStatus = (GameStatus)gameStatus.clone();
                            }catch(CloneNotSupportedException e){
                                throw new IllegalStateException("GameStatus not clonable"); //TODO check...
                            }

                            Collection<? extends Move> moves = gameAnalyzer.playableMoves(newGameStatus, playerId);
                            gameListener.onMovesAvailable(moves);

                            lock.unlock();
                            gameStatus = player.makeMove(gameAnalyzer, newGameStatus, moves); //blocking call
                            lock.lock();

                            //TODO andrebbe effettuato un check sul nuovo gameStatus

                            game.gameHistory.add(newGameStatus);

                            break;

                        case Stopped:
                            throw new InterruptedException("GameRunner has been stopped");
                            //break;

                        default:
//                            game.wait();
                            condition.await();
                            break;
                    }
                }finally{
                    lock.unlock();
                }
            }

            //TODO notifica vincitori
//            if(gameStatus.getWinner() != null)
//                "Player " + gameStatus.getWinner() + " won the game."
//            else
//                "It's a draw!"
        }catch(InterruptedException ie){
            Log.w(TAG, "GameManager interrupted");
            //TODO winnePlayers = null;
        }finally{
            Log.d(TAG, "GameManager terminated");
            //TODO finally...
            gameListener.onGameUpdate(game, /*gameRunnerState*/GameRunnerState.Stopped); //FIXME fuori dal mutex gameRunnerState potrebbe non valere più Stopped
        }
    }
}
