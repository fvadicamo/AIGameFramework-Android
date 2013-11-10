package com.squirrelapps.aigameframework;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public class GameActivity extends FragmentActivity implements BoardFragment.BoardListener, Button.OnClickListener, GameManager.GameListener
{
    private static final String TAG = GameActivity.class.getSimpleName();

    protected GameManager gameManager;
//    protected Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_game);

        //TODO recuperare il game da savedInstanceState e solo se null crearlo

        //FIXME testing OthiGame > TO REMOVE!
        //REMEMBER GameActivity fa da Director del pattern GoF Builder (valutare altrimenti la creazione di una classe apposita)
        GameBuilder gameBuilder = new OthiGameBuilder(); //TODO gli vanno passati i settings che gli servono
        gameBuilder.buildBoard(/*OthiGameRules.X_DIM, OthiGameRules.Y_DIM*/);
        gameBuilder.buildHumanPlayer(0, "Human");
        gameBuilder.buildComputerPlayer(1, "CPU", null /*FIXME new AlfaBetaPruning()*/, 3/*TODO retrieve it from settings*/);
        gameBuilder.buildFirsGameStatus(0);
        Game game = gameBuilder.getGame();

        GameAnalyzer gameAnalyzer = new OthiGameAnalyzer(game); //TODO troppi riferimenti incrociati > low coupling!

        gameManager = new GameManager(game, gameAnalyzer, (GameManager.GameListener)this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    protected void onStart()
    {
        Log.d(TAG, "onStart");
        super.onStart();

        gameManager.startGame();
    }

    @Override
    protected void onResume()
    {
        Log.d(TAG, "onResume");
        super.onResume();

        gameManager.resumeGame();
    }

    @Override
    protected void onPause()
    {
        Log.d(TAG, "onPause");
        super.onPause();

        gameManager.pauseGame();
    }

    @Override
    protected void onStop()
    {
        Log.d(TAG, "onStop");
        super.onStop();

        gameManager.stopGame();
    }

    @Override
    public boolean onBoardLayout(ViewGroup rootLayout, ViewGroup boardLayout)
    {
        return false;
    }

    @Override
    public boolean onBoardCreate(Cell cell, Button btn)
    {
        btn.setTag(cell);
        btn.setOnClickListener(this);

        return true;
    }

    @Override
    public boolean onBoardUpdate(Cell cell, Button btn)
    {
        //Cell cell = (Cell)btn.getTag();
        int x = cell.x;
        int y = cell.y;

        if(/*FIXME alternate*/false){
            btn.setBackgroundColor((x + y) % 2 == 0 ? Color.BLACK : Color.WHITE);
            btn.setTextColor((x + y) % 2 == 0 ? Color.WHITE : Color.BLACK);
        }else{
            btn.setBackgroundResource(R.drawable.cell_green_dark);
            btn.setTextColor(Color.WHITE);
        }

        btn.setText(/*board.cells[x][y].toString()*/"*");

        return true;
    }

    @Override
    public void onClick(View v)
    {
        //TODO on click...
        new AsyncTask<Game, GameStatus, GameStatus>(){

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
            }

            @Override
            protected GameStatus doInBackground(Game... params)
            {
                return null;
            }

            @Override
            protected void onPostExecute(GameStatus gameStatus)
            {
                super.onPostExecute(gameStatus);
            }
        };
    }

//    @Override
//    public boolean onGameCreate(Game game, GameManager.GameRunnerState gameRunnerState)
//    {
//        //TODO onGameCreate
//        return false;
//    }

    @Override
    public boolean onGameUpdate(Game game, GameManager.GameRunnerState gameRunnerState)
    {
        //TODO onGameUpdate
        return false;
    }
}
