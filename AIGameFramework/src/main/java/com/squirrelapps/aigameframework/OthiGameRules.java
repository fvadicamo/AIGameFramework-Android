package com.squirrelapps.aigameframework;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public final class OthiGameRules
{
    public static final int X_DIM = 8;
    public static final int Y_DIM = 8;
    public static final int CELLS_NUM = X_DIM*Y_DIM;

    public static final int PLAYERS_NUM = 2;

    public static final int PIECES_PER_PLAYER = CELLS_NUM; //1 piece for each cell*/
    public static final int MOVES_PER_PIECE = 1;
    public static final int MOVES_PER_PLAYER = PIECES_PER_PLAYER*MOVES_PER_PIECE;

    public static final int PIECES_NUM = PIECES_PER_PLAYER*PLAYERS_NUM;
    public static final int MOVES_NUM = MOVES_PER_PLAYER*PLAYERS_NUM;

//    public static final Class<?> BOARD_STATUS_CLASS = SimpleBoardStatus.class;
//    public static final Class<?> PLAYER_STATUS_CLASS = SimplePlayerStatus.class;

    private OthiGameRules()
    {
        throw new AssertionError();
    }
}
