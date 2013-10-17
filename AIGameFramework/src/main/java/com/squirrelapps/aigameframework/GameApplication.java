package com.squirrelapps.aigameframework;

import android.app.Application;
import android.util.Log;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public class GameApplication extends Application
{
    private static final String TAG = GameApplication.class.getSimpleName();

//    GameManager gameManager;

    @Override
    public void onCreate()
    {
        Log.d(TAG, "onCreate");
        super.onCreate();

//        this.gameManager = GameManager.getInstance();
    }

    @Override
    /**
    * This method is for use in emulated process environments.  It will
    * never be called on a production Android device, where processes are
    * removed by simply killing them; no user code (including this callback)
    * is executed when doing so.
    */
    public void onTerminate()
    {
        Log.d(TAG, "onTerminate");
        super.onTerminate();
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig)
//    {
//        super.onConfigurationChanged(newConfig);
//    }

    @Override
    public void onLowMemory()
    {
        Log.d(TAG, "onLowMemory");
        super.onLowMemory();

        //TODO pause and save the game
    }
}
