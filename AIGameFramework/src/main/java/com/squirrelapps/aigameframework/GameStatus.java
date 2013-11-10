package com.squirrelapps.aigameframework;

import org.apache.http.client.utils.CloneUtils;

import java.util.Arrays;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public /*abstract*/ class GameStatus implements Cloneable //Parcelable
{
    int currentPlayer;
    int nextPlayer;

    int moveNumber;
    Move move;

    boolean gameOver; //TODO o finalStatus o lastStatus o lastMove?!

    BoardStatus boardStatus;
    PlayerStatus[] playersStatus;

    public GameStatus()
    {

    }

//    private GameStatus(Parcel in)
//    {
//
//    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        //REMIND By convention, the returned object should be obtained by calling super.clone
        //GameStatus gs = new GameStatus();

        final GameStatus gs = (GameStatus)super.clone();

        //REMIND If a class contains only primitive fields or references to immutable objects,
        // then it is usually the case that no fields in the object returned by super.clone need to be modified
        //gs.currentPlayer = this.currentPlayer;
        //gs.nextPlayer = this.nextPlayer;
        //gs.moveNumber = this.moveNumber;
        //gs.gameOver = this.gameOver;

        final Move m;
//        if(this.move == null){
//            m = null;
//        }else{
//            m = (Move)this.move.clone();
//        }
        m = (Move)CloneUtils.clone(this.move);
        gs.move = m;

        final BoardStatus bs;
//        if(this.boardStatus == null){
//            bs = null;
//        }else if(this.boardStatus instanceof SimpleBoardStatus){
//            bs = (BoardStatus)((SimpleBoardStatus)this.boardStatus).clone();
//        }else{
//            throw new CloneNotSupportedException();
//        }
        bs = (BoardStatus)CloneUtils.clone(this.boardStatus);
        gs.boardStatus = bs;

        //REMIND this.playersStatus.clone(); performs a "shallow copy" of this object, not a "deep copy" operation
        final PlayerStatus[] pss;
        if(gs.playersStatus == null){
            pss = null;
        }else{
            pss = new PlayerStatus[this.playersStatus.length];
            for(int i = 0; i < pss.length; i++){
//                PlayerStatus ps = this.playersStatus[i];
//                if(ps == null){
//                    pss[i] = null;
//                }else if(pss[i] instanceof SimplePlayerStatus){
//                    pss[i] = (PlayerStatus)((SimplePlayerStatus)ps).clone();
//                }else{
//                    throw new CloneNotSupportedException();
//                }
                pss[i] = (PlayerStatus)CloneUtils.clone(this.playersStatus[i]);
            }
        }
        gs.playersStatus = pss;

        //TODO altro?!

        return gs;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(!(o instanceof GameStatus)) return false;

        GameStatus that = (GameStatus) o;

        if(currentPlayer != that.currentPlayer) return false;
        if(gameOver != that.gameOver) return false;
        if(moveNumber != that.moveNumber) return false;
        if(nextPlayer != that.nextPlayer) return false;
        if(boardStatus != null ? !boardStatus.equals(that.boardStatus) : that.boardStatus != null)
            return false;
        if(move != null ? !move.equals(that.move) : that.move != null) return false;
        if(!Arrays.equals(playersStatus, that.playersStatus)) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = currentPlayer;
        result = 31 * result + nextPlayer;
        result = 31 * result + moveNumber;
        result = 31 * result + (move != null ? move.hashCode() : 0);
        result = 31 * result + (gameOver ? 1 : 0);
        result = 31 * result + (boardStatus != null ? boardStatus.hashCode() : 0);
        result = 31 * result + (playersStatus != null ? Arrays.hashCode(playersStatus) : 0);
        return result;
    }

    public int getCurrentPlayer()
    {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer)
    {
        this.currentPlayer = currentPlayer;
    }

    public int getNextPlayer()
    {
        return nextPlayer;
    }

    public void setNextPlayer(int nextPlayer)
    {
        this.nextPlayer = nextPlayer;
    }

    public int getMoveNumber()
    {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber)
    {
        this.moveNumber = moveNumber;
    }

    public Move getMove()
    {
        return move;
    }

    public void setMove(Move move)
    {
        this.move = move;
    }

    public boolean isGameOver()
    {
        return gameOver;
    }

    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }

    public BoardStatus getBoardStatus()
    {
        return boardStatus;
    }

    public void setBoardStatus(BoardStatus boardStatus)
    {
        this.boardStatus = boardStatus;
    }

    public PlayerStatus[] getPlayersStatus()
    {
        return playersStatus;
    }

    public void setPlayersStatus(PlayerStatus[] playersStatus)
    {
        this.playersStatus = playersStatus;
    }

    //    public final static GameStatus parseGameStatus(String s)
//    {
//        final GameStatus gameStatus = new GameStatus();
//        //...
//        return gameStatus;
//    }

//    @Override
//    public int describeContents()
//    {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel out, int flags)
//    {
//        //...parcel.writeByteArray();
//    }
//
//    public static final Creator<GameStatus> CREATOR = new Creator<GameStatus>(){
//        @Override
//        public GameStatus createFromParcel(Parcel in)
//        {
//            return new GameStatus(in);
//        }
//
//        @Override
//        public GameStatus[] newArray(int size)
//        {
//            return new GameStatus[size];
//        }
//    };

//    public abstract byte[] toByteArray();
}
