package com.squirrelapps.aigameframework;

import java.util.Collection;

/**
 * TODO si potrebbe rendere generic con <T> che rappresenta il tipo di move (Move o byte[] praticamente)
 *
 * Created by Francesco Vadicamo on 9/21/13.
 */
public abstract class SearchAlgorithm
{
    protected final GameAnalyzer gameAnalyzer;

    protected GameEvaluator gameEvaluator;

    protected byte[] solution;

    protected int maxDepth;

    public SearchAlgorithm(GameAnalyzer gameAnalyzer)
    {
        this.gameAnalyzer = gameAnalyzer;
    }

    public int getMaxDepth()
    {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth)
    {
        this.maxDepth = maxDepth;
    }

    public byte[] getSolution()
    {
        return solution;
    }

    //    /**
//     * Restituisce la collezione degli operatori (mosse) applicabili allo stato del gioco passato
//     *
//     * @param status stato del gioco dal quale calcolare gli operatori (mosse) possibili
//     * @return la collezione degli operatori (mosse) applicabili allo stato del gioco passato
//     */
//    protected Collection operators(GameStatus status)
//    {
//        return gameAnalyzer.nextPlayableMoves(status);
//    }

    /**
     * Restituisce la collezione degli operatori (mosse) applicabili allo stato del gioco passato
     *
     * @param status stato del gioco in binario dal quale calcolare gli operatori (mosse) possibili
     * @return la collezione degli operatori (mosse) applicabili allo stato del gioco passato
     */
    protected Collection<byte[]> operators(byte[] status)
    {
        return gameAnalyzer.playableMoves(status, gameAnalyzer.nextPlayer(status));
    }

//    /**
//     * Applica l'operatore mossa allo stato del gioco passato e restituisce lo status risultante
//     *
//     * @param operator operatore mossa da applicare al game status
//     * @param status stato del gioco su cui applicare l'operatore mossa
//     * @return lo stato del gioco risultante dall'applicazione dell'operatore mossa allo status passato
//     */
//    protected GameStatus apply(Move operator, GameStatus status)
//    {
//        return gameAnalyzer.makeMove(status, operator);
//    }

//    /**
//     * Applica l'operatore mossa allo stato del gioco passato e restituisce lo status risultante
//     *
//     * @param operator operatore mossa da applicare al game status
//     * @param status stato del gioco in binario su cui applicare l'operatore mossa
//     * @return lo stato del gioco risultante dall'applicazione dell'operatore mossa allo status passato
//     */
//    protected byte[] apply(Move operator, byte[] status)
//    {
//        return gameAnalyzer.makeMove(status, operator);
//    }

    /**
     * Applica l'operatore mossa allo stato del gioco passato e restituisce lo status risultante
     *
     * @param operator operatore mossa da applicare al game status
     * @param status stato del gioco in binario su cui applicare l'operatore mossa
     * @return lo stato del gioco risultante dall'applicazione dell'operatore mossa allo status passato
     */
    protected byte[] apply(byte[] operator, byte[] status)
    {
        return gameAnalyzer.makeMove(status, operator);
    }

//    protected boolean isFinalStatus(GameStatus status)
//    {
//        return gameAnalyzer.isFinalGameStatus(status);
//    }

    protected boolean isFinalStatus(byte[] status)
    {
        return gameAnalyzer.isFinalGameStatus(status);
    }

    protected float evaluate(byte[] status, int playerMax)
    {
        return gameEvaluator.evaluate(status, playerMax);
    }

    /**
     * Controllo lo stato della ricerca
     *
     * @throws IllegalSearchStateException se il thread searcher e' interrupted
     */
    protected void checkState() throws IllegalSearchStateException
    {
        if(Thread.interrupted()){
            throw new IllegalSearchStateException("Searcher Interrupted!");
        }
    }

    protected abstract void search(byte[] currStatus) throws IllegalSearchStateException;

    class IllegalSearchStateException extends Exception
    {
        public IllegalSearchStateException()
        {
            super();
        }

        public IllegalSearchStateException(String message)
        {
            super(message);
        }
    }
}
