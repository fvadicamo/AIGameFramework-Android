package com.squirrelapps.aigameframework;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public /*abstract*/ class GameStatus //implements Parcelable
{
    int currentPlayer;
    int nextPlayer;

    int moveNumber;
    Move move;

    BoardStatus boardStatus;
    PlayerStatus[] playersStatus;

    public GameStatus()
    {

    }

//    private GameStatus(Parcel in)
//    {
//
//    }

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
