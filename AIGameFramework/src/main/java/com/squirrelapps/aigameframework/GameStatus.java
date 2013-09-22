package com.squirrelapps.aigameframework;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Francesco Vadicamo on 9/10/13.
 */
public /*abstract*/ class GameStatus //implements Parcelable
{
    //TODO pezzi per ogni giocatore
    //TODO

    int currentPlayer;
    int nextPlayer;

    int moveNumber;
    byte[] move;

    byte[][] playersStatus;
    byte[] boardStatus;

    public GameStatus()
    {

    }

//    private GameStatus(Parcel in)
//    {
//
//    }

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
