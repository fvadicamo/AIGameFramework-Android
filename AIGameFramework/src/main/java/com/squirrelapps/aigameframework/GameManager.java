package com.squirrelapps.aigameframework;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public final class GameManager implements Runnable
{
    public static enum GameRunnerState{
        Ready,
        Playing,
        //Paused,
        Stopped,
    }

    // Container Activity must implement this interface
    public interface GameListener {
        public boolean onGameCreate(Game game, GameRunnerState gameRunnerState);
        public boolean onGameUpdate(Game game, GameRunnerState gameRunnerState);
    }

    final Game game;
    final GameAnalyzer gameAnalyzer;
    //TODO da capire se è Game che deve mantenerne un riferimento oppure GameManager
    final GameListener gameListener;

    Thread gameRunner;
    GameRunnerState gameRunnerState;

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
            throw new IllegalStateException("Game already started!"); //TODO custom exception
        }
        gameRunner = new Thread(this, "GameRunner");
        //TODO set thread priority
        gameRunnerState = GameRunnerState.Ready;
        gameListener.onGameCreate(game, gameRunnerState);
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
            while(!gameStatus.isGameOver() /*!gameAnalyzer.isFinalGameStatus(gameStatusCoded)*/ && !gameRunner.isInterrupted()){
                synchronized(game){
//                    while(gameRunnerState != GameRunnerState.Playing){
//                        game.wait();
//                    }

                    switch(gameRunnerState){
                        case Playing:
                            playerId = gameStatus.getNextPlayer();
                            player = game.players[playerId];

                            gameStatus = player.makeMove(gameAnalyzer, gameStatus); //blocking call

                            //TODO andrebbe effettuato un check sul nuovo gameStatus

                            //move = gameStatus.move; //TODO notify!? ma a questo punto tutto il gamestatus!

                            game.gameHistory.add(gameStatus);

                            gameListener.onGameUpdate(game, GameRunnerState.Playing);

                            //...gameStatus.moveNumber;
                            break;

                        default:
                            game.wait();
                            break;
                    }
                }
            }

            //TODO notifica vincitori
//            if(gameStatus.getWinner() != null)
//                "Player " + gameStatus.getWinner() + " won the game."
//            else
//                "It's a draw!"
        }catch(InterruptedException ie){
            //TODO winnePlayers = null;
        }finally{
            //TODO finally...
        }
    }
}
