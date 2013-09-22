package com.squirrelapps.aigameframework;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.Collection;

/**
 * Created by Francesco Vadicamo on 9/8/13.
 */
public abstract class GameAnalyzer
{
    final Game game;

//    ByteBuffer gameStatus;
//    byte[] gameStatus;

    //TODO player status

    //TODO playable cells
    //TODO playable moves
    //TODO player score

    //TODO estendendo questa classe lo sviluppatore del gioco X dovrà "semplicemente" implementare:
    // 1) un metodo che codifichi il game status [iniziale] direttamente in array di byte
    // 2) un metodo che codifichi le possibili mosse / celle giocabili (visto che dipende dal gioco, direi mosse soltanto) > una possibile mossa è PutPiece o simile
    // 3) un metodo che ricevuto un game status (codificato) e una mossa (codificata) restituisca il game status risultante
    // 4) un metodo che ricevuto un game status (codificato) e l'id (numero) del giocatore, restituisca un ranking/evaluation per quel giocatore
    // 5) un metodo che ricevuto un game status (codificato) restituisca un ranking/evaluation per tutti i giocatori (l'array di bytes dei ranking potrebbe essere passato) > nella classe abstract potrebbe già essere implementato richiamando 4) ma nelle sottoclassi potrebbe essere ottimizzato
    //    > se l'array viene passato il metodo potrebbe restituire un boolean per.. boh!
    // 6) un metodo che ricevuto un game status (codificato) e l'id (numero) del giocatore, restituisca il punteggio corrente del giocatore
    // 7) un metodo che ricevuto un game status (codificato) restituisca i punteggi correnti di tutti i giocatori (l'array di bytes degli scores potrebbe essere passato) > nella classe abstract potrebbe già essere implementato richiamando 5) ma nelle sottoclassi potrebbe essere ottimizzato
    //    > se l'array viene passato il metodo potrebbe restituire un boolean per dire che la partita è finita

    // >> piuttosto che richiamare un metodo per dire il ranking, uno per il punteggio, uno per lo score e uno per calcolare se ha vinto qualcuno, convine avere delle variabili locali
    // >> i valori calcolati potrebbero quindi appartenere direttamente al gamestatus (codificato e non) purchè in byte opzionali (non servono durante la ricerca, in tal caso se occorre sapere il vincitore lo si deve calcolare)
    // >> nella fattispecie sapere se lo stato è finale occupa un bit quindi lo si può mettere sempre e sapere il punteggio occupa poco (comunque conviene avere un bit per sapere se ci sono questi byte extra

    //come mosse standard abbiamo PutPiece(Piece p, Cell dest) MovePiece[To](Piece p, /*Cell src,*/ Cell dest) EatPiece etc. > di queste viene proposta già la codifica, FlipCell

//    protected final Board board;

//    public GameAnalyzer(Board board)
//    {
//        this.board = board;
//    }

    public GameAnalyzer(Game game)
    {
        this.game = game;
    }

//    //analyze(byte[] gameStatus)
//    public GameAnalyzer setGameStatus(byte[] gameStatus)
//    {
//        this.gameStatus = gameStatus;
//
////        this.gameStatus = ByteBuffer.wrap(gameStatus);
//        //TODO reset() c'è qualche variabile locale da resettare!?
//        return this;
//    }

//    public byte[] cell(int x, int y)
//    {
//        return new byte[]{(byte)x, (byte)y};
//    }

    public abstract byte[] firstGameStatus(); //TODO andrebbe forse spostato in un Game[Status]Builder

    public abstract int currentPlayer(byte[] gameStatus);
    public abstract int nextPlayer(byte[] gameStatus);

//    public abstract Collection<byte[]> currentPlayableMoves(byte[] gameStatus);
//
    public abstract Collection<byte[]> playableMoves(byte[] gameStatus, int playerId);

    public abstract byte[] makeMove(byte[] gameStatus, byte[] move);

    //NB nel game status c'è scritto chi ha giocato per ultimo e chi deve giocare

    //public abstract int[] playerScores();
    public abstract int[] playersScores(byte[] gameStatus);

    public int playerScore(byte[] gameStatus, int playerId)
    {
        return playersScores(gameStatus)[playerId];
    }

    public abstract boolean isFinalGameStatus(byte[] gameStatus);

//    public void ToRemove()
//    {
//        gameStatus = ByteBuffer.allocate(100);
//        int position = gameStatus.position() /*0*/; //game status offset
//        gameStatus.put(firstGameStatus());
//        position = gameStatus.position(); //playable moves offset
//        gameStatus.put(playableMoves(0));
//    }
}
