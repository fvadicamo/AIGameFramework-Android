package com.squirrelapps.aigameframework;

import android.util.Log;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public class OthiGameAnalyzer extends GameAnalyzer
{
    private static final String TAG = OthiGameAnalyzer.class.getSimpleName();

    private static final byte ALL_ONES = ~0; //(byte)-1

//    public static final int X_DIM = 8;
//    public static final int Y_DIM = 8;
//    public static final int PLAYERS_NUM = 2;

    //CODED_GAME_STATUS = GAME_STATUS_MOVENUMBER | GAME_STATUS_MOVE | GAME_STATUS_PLAYER0 | GAME_STATUS_PLAYER1

    protected static final int CODED_MOVENUMBER_SIZE = 1; //almost 64 moves

    protected static final int CODED_MOVE_ID_INDEX = 0; /*id*/
    protected static final int CODED_MOVE_PIECE_INDEX = 1; /*piece*/
    protected static final int CODED_MOVE_CELL_X_INDEX = 2; /*cell x*/
    protected static final int CODED_MOVE_CELL_Y_INDEX = 3; /*cell y*/
    protected static final int CODED_MOVE_SIZE = 4; //TODO potremmo realizzare una cache che usa solo 1byte per il mapping

    protected static final int CODED_PLAYER_SIZE = OthiGameRules.Y_DIM; //1 byte for each row

    public static final int GAME_STATUS_MOVENUMBER_OFFSET = 0;
    public static final int GAME_STATUS_MOVE_OFFSET = GAME_STATUS_MOVENUMBER_OFFSET + CODED_MOVENUMBER_SIZE;
    public static final int GAME_STATUS_PLAYER0_OFFSET = GAME_STATUS_MOVE_OFFSET + CODED_MOVE_SIZE;
    public static final int GAME_STATUS_PLAYER1_OFFSET = GAME_STATUS_PLAYER0_OFFSET + CODED_PLAYER_SIZE;
    //LIMITE GAME STATUS STANDARD (SENZA ANALYTICS)
//    public final static int GAME_STATUS_SCORES_OFFSET = GAME_STATUS_PLAYER1_OFFSET + OthiGameRules.Y_DIM; // = 18

    public static final int[] GAME_STATUS_PLAYERS_OFFSET = new int[]{GAME_STATUS_PLAYER0_OFFSET, GAME_STATUS_PLAYER1_OFFSET};

    protected static final byte[] CODED_MOVE_NONE = encode(OthiGame.MOVE_NONE); //new byte[]{ALL_ONES, ALL_ONES, ALL_ONES, ALL_ONES};
    protected static final byte[][] CODED_MOVES = new byte[OthiGameRules.MOVES_NUM][CODED_MOVE_SIZE];
//    static{
////        MOVES[0] = OthiGame.MOVE_NONE;
////        CODED_MOVES[0] = encode(MOVES[0]); //new byte[]{0, ALL_ONES, ALL_ONES, ALL_ONES};
//        for(int x = 0; x < OthiGameRules.X_DIM; x++){
//            for(int y = 0; y < OthiGameRules.Y_DIM; y++){
//                for(int playerId = 0; playerId < OthiGameRules.PLAYERS_NUM; playerId++){
//                    final int index = playerId * OthiGameRules.X_DIM*OthiGameRules.Y_DIM + y * OthiGameRules.X_DIM + x;
//
////                    CODED_MOVES[index][CODED_MOVE_ID_INDEX] = (byte)index; //piece
////                    CODED_MOVES[index][CODED_MOVE_PIECE_INDEX] = (byte)index; //piece
////                    CODED_MOVES[index][CODED_MOVE_CELL_X_INDEX] = (byte)x; //cell x
////                    CODED_MOVES[index][CODED_MOVE_CELL_Y_INDEX] = (byte)y; //cell y
//
//                    MOVES[index] = new OthiMove(index, playerId, Pawn.valueOf(index), new Cell(x, y));
//                    CODED_MOVES[index] = encode(MOVES[index]);
//                }
//            }
//        }
//    }
    static{
        //REMIND we know all moves have already been created
        for(int moveId = 0; moveId < OthiGame.MOVES.length; moveId++){
            CODED_MOVES[moveId] = encode(OthiGame.move(moveId));
        }
    }

    public OthiGameAnalyzer(Game game)
    {
        super(game);

    }

    //TODO spostare tutti i metodi encode e decode in una classe [Othi]GameCoder
    public static byte[] encode(Move move)
    {
        assert(move != null):"OthiGame's player move cannot be null";
        assert(move instanceof OthiMove):"OthiGame's moves must be instance of OthiMove";

        byte[] coded = new byte[CODED_MOVE_SIZE];
        //if(move != null /*&& !move.equals(OthiGame.MOVE_NONE)*/){
        OthiMove m = (OthiMove)move;

        if(!move.equals(OthiGame.MOVE_NONE)){
            coded[CODED_MOVE_ID_INDEX] = (byte)m.id; //id
        }else{
            coded[CODED_MOVE_ID_INDEX] = ALL_ONES; //id
        }

        if(m.piece != null){
            coded[CODED_MOVE_PIECE_INDEX] = (byte)m.piece.id; //piece
        }else{
            coded[CODED_MOVE_PIECE_INDEX] = ALL_ONES; //piece
        }

        if(m.cell != null){
            coded[CODED_MOVE_CELL_X_INDEX] = (byte)m.cell.x; //cell x
            coded[CODED_MOVE_CELL_Y_INDEX] = (byte)m.cell.y; //cell y
        }else{
            coded[CODED_MOVE_CELL_X_INDEX] = ALL_ONES; //cell x
            coded[CODED_MOVE_CELL_Y_INDEX] = ALL_ONES; //cell y
        }
        //}
        return coded;
    }

    public static byte[] encode(PlayerStatus playerStatus, int playerId)
    {
        assert(playerStatus != null):"OthiGame's player status cannot be null";
        //assert(OthiGameRules.PLAYER_STATUS_CLASS.isInstance(playerStatus)):"OthiGame's player status must be instance of "+OthiGameRules.PLAYER_STATUS_CLASS.getSimpleName();
        assert(playerStatus instanceof SimplePlayerStatus):"OthiGame's player status must be instance of SimplePlayerStatus";

        byte[] coded = new byte[CODED_PLAYER_SIZE];
        //if(playerStatus != null){
        SimplePlayerStatus sps = (SimplePlayerStatus)playerStatus;

        //Pawn pawn;
        Cell cell;
        Set<Piece> playedPieces = sps.playedPieces();
        for(Piece piece : playedPieces){
            assert(piece instanceof Pawn):"OthiGame's player pieces must be instance of Pawn";
            //pawn = (Pawn)piece;
            cell = OthiGame.pieceCell(playerId, piece.id);
            coded[cell.y] = (byte)(coded[cell.y] | (1<<(7-cell.x)));
        }
        return coded;
    }

//    public static byte[] encode(PlayerStatus[] playersStatus)
//    {
//        byte[] coded = new byte[CODED_PLAYER_SIZE*playersStatus.length];
//        for(int playerId = 0; playerId < playersStatus.length; playerId++){
//            System.arraycopy(encode(playersStatus[playerId], playerId), 0, coded, GAME_STATUS_PLAYERS_OFFSET[playerId], CODED_PLAYER_SIZE);
//        }
//        return coded;
//    }

    public static byte[] encode(GameStatus gameStatus)
    {
        assert(gameStatus != null):"GameStatus cannot be null";

        //TODO potremmo includere subito il byte con lo score
        byte[] coded = new byte[CODED_MOVENUMBER_SIZE + CODED_MOVE_SIZE + CODED_PLAYER_SIZE*gameStatus.playersStatus.length];

        coded[GAME_STATUS_MOVENUMBER_OFFSET] = (byte)(gameStatus.moveNumber);

        byte[] coded_move = encode(gameStatus.move);
        System.arraycopy(coded_move, 0, coded, GAME_STATUS_MOVE_OFFSET, CODED_MOVE_SIZE);

        byte[] coded_player;
        for(int playerId = 0; playerId < gameStatus.playersStatus.length/*OthiGameRules.PLAYERS_NUM*/; playerId++){
            coded_player = encode(gameStatus.playersStatus[playerId], playerId);
            System.arraycopy(coded_player, 0, coded, GAME_STATUS_PLAYERS_OFFSET[playerId], CODED_PLAYER_SIZE);
        }

        return coded;
    }

    public static Move decodeMove(byte[] coded)
    {
        final int move_id = coded[CODED_MOVE_ID_INDEX];
        if(move_id == ALL_ONES){
            return OthiGame.MOVE_NONE;
        }else{
            return OthiGame.move(coded[CODED_MOVE_ID_INDEX]); //TODO gli altri bytes non servono e occupano solo spazio
        }
    }

    public static PlayerStatus decodePlayerStatus(byte[] coded, int playerId)
    {
        SimplePlayerStatus playerStatus = new SimplePlayerStatus();
        Set<Piece> availablePieces = new HashSet<Piece>(128, 0.51f); //128 > 64/0.51 --> no rehash!
        Map<Piece, Cell> cellsMap = new HashMap<Piece, Cell>(32, 0.75f); //32*2 > 64/0.75 --> 1 solo rehash!

        Cell cell;
        Piece piece;
        for(int y = 0; y < OthiGameRules.Y_DIM; y++){
            for(int x = 0; x < OthiGameRules.X_DIM; x++){
                piece = OthiGame.piece(playerId, x, y);
                if((coded[y] & (1<<(7-x))) > 0){
                    cell = OthiGame.cell(x, y);
                    cellsMap.put(piece, cell);
                }else{
                    availablePieces.add(piece);
                }
            }
        }

        playerStatus.setAvailablePieces(availablePieces);
        playerStatus.setCellsMap(cellsMap);
        return playerStatus;
    }

    public static GameStatus decodeGameStatus(byte[] coded)
    {
        GameStatus gs = new GameStatus();

//        final int p0 = 0;
//        final int p1 = 1;
        final int xDim = OthiGameRules.X_DIM; //board.xDim; //8
        final int yDim = OthiGameRules.Y_DIM; //board.yDim; //8

        final int moveNumber = coded[GAME_STATUS_MOVENUMBER_OFFSET];
        gs.setMoveNumber(moveNumber);

        final byte[] coded_move = Arrays.copyOfRange(coded, GAME_STATUS_MOVE_OFFSET, GAME_STATUS_MOVE_OFFSET + CODED_MOVE_SIZE);
        final Move move = decodeMove(coded_move);
        gs.setMove(move);

        final int curr_player = OthiGame.currentPlayerAtMove(moveNumber);
        final int next_player = 1 - curr_player; //OthiGame.nextPlayerAtMove(moveNumber);
        gs.setCurrentPlayer(curr_player);
        gs.setNextPlayer(next_player);

        //PLAYERS STATUS
        byte[] coded_player;
        PlayerStatus[] playersStatus = new PlayerStatus[OthiGameRules.PLAYERS_NUM];
        for(int playerId = 0; playerId < OthiGameRules.PLAYERS_NUM; playerId++){
            coded_player = Arrays.copyOfRange(coded, GAME_STATUS_PLAYERS_OFFSET[playerId], GAME_STATUS_PLAYERS_OFFSET[playerId] + CODED_PLAYER_SIZE);
            playersStatus[playerId] = decodePlayerStatus(coded_player, playerId);
        }
        gs.setPlayersStatus(playersStatus);

        //BOARD STATUS
        SimpleBoardStatus boardStatus = new SimpleBoardStatus();

        Set<Cell> availableCells = new HashSet<Cell>(128, 0.51f); //128 > 64/0.51 --> no rehash!
        Map<Cell, Piece> pieceMap = new HashMap<Cell, Piece>(64, 0.75f); //128 > 64/0.75 --> no rehash!
        Cell cell;
        Piece piece;
        for(int y = 0; y < yDim; y++){
            for(int x = 0; x < xDim; x++){
                cell = OthiGame.cell(x, y);
                availableCells.add(cell);
                for(int playerId = 0; playerId < OthiGameRules.PLAYERS_NUM; playerId++){
                    piece = OthiGame.piece(playerId, x, y);
                    if(((SimplePlayerStatus)playersStatus[playerId]).playedPieces().contains(piece)){
                        pieceMap.put(cell, piece);
                        availableCells.remove(cell);
                    }
                }
            }
        }
        boardStatus.setAvailableCells(availableCells);
        boardStatus.setPiecesMap(pieceMap);
        gs.setBoardStatus(boardStatus);

        return gs;
    }

    protected boolean isPlayableCell(byte[] gameStatus, int currPlayer, int nextPlayer, int column, int row)
    {
        int i, j;

        int curr_begin = GAME_STATUS_PLAYERS_OFFSET[currPlayer]; //(currPlayer == 0) ? GAME_STATUS_PLAYER0_OFFSET : GAME_STATUS_PLAYER1_OFFSET;
        int next_begin = GAME_STATUS_PLAYERS_OFFSET[nextPlayer]; //(nextPlayer == 0) ? GAME_STATUS_PLAYER0_OFFSET : GAME_STATUS_PLAYER1_OFFSET;

        if((gameStatus[curr_begin + row] & (1 << (7 - column))) != 0 || (gameStatus[next_begin + row] & (1 << (7 - column))) != 0){    //cella non vuota
            return false;
        }else{
            if(column > 1 && (gameStatus[next_begin + row] & (1 << (7 - column + 1))) != 0){
                for(i = column - 2; i > 0 && (gameStatus[next_begin + row] & (1 << (7 - i))) != 0; i--){
                    //SKIP;
                }
                if((gameStatus[curr_begin + row] & (1 << (7 - i))) != 0){
                    return true;
                }
            }
            if(column < 6 && (gameStatus[next_begin + row] & (1 << (7 - column - 1))) != 0){
                for(i = column + 2; i < 7 && (gameStatus[next_begin + row] & (1 << (7 - i))) != 0; i++){
                    //SKIP;
                }
                if((gameStatus[curr_begin + row] & (1 << (7 - i))) != 0){
                    return true;
                }
            }
            if(row > 1 && (gameStatus[next_begin + row - 1] & (1 << (7 - column))) != 0){
                for(j = row - 2; j > 0 && (gameStatus[next_begin + j] & (1 << (7 - column))) != 0; j--){
                    //SKIP;
                }
                if((gameStatus[curr_begin + j] & (1 << (7 - column))) != 0){
                    return true;
                }
            }
            if(row < 6 && (gameStatus[next_begin + row + 1] & (1 << (7 - column))) != 0){
                for(j = row + 2; j < 7 && (gameStatus[next_begin + j] & (1 << (7 - column))) != 0; j++){
                    //SKIP;
                }
                if((gameStatus[curr_begin + j] & (1 << (7 - column))) != 0){
                    return true;
                }
            }
            if(column > 1 && row > 1 && (gameStatus[next_begin + row - 1] & (1 << (7 - column + 1))) != 0){
                for(i = column - 2, j = row - 2; i > 0 && j > 0 && (gameStatus[next_begin + j] & (1 << (7 - i))) != 0; i--, j--){
                    //SKIP;
                }
                if((gameStatus[curr_begin + j] & (1 << (7 - i))) != 0){
                    return true;
                }
            }
            if(column < 6 && row > 1 && (gameStatus[next_begin + row - 1] & (1 << (7 - column - 1))) != 0){
                for(i = column + 2, j = row - 2; i < 7 && j > 0 && (gameStatus[next_begin + j] & (1 << (7 - i))) != 0; i++, j--){
                    //SKIP;
                }
                if((gameStatus[curr_begin + j] & (1 << (7 - i))) != 0){
                    return true;
                }
            }
            if(column < 6 && row < 6 && (gameStatus[next_begin + row + 1] & (1 << (7 - column - 1))) != 0){
                for(i = column + 2, j = row + 2; i < 7 && j < 7 && (gameStatus[next_begin + j] & (1 << (7 - i))) != 0; i++, j++){
                    //SKIP;
                }
                if((gameStatus[curr_begin + j] & (1 << (7 - i))) != 0){
                    return true;
                }
            }
            if(column > 1 && row < 6 && (gameStatus[next_begin + row + 1] & (1 << (7 - column + 1))) != 0){
                for(i = column - 2, j = row + 2; i > 0 && j < 7 && (gameStatus[next_begin + j] & (1 << (7 - i))) != 0; i--, j++){
                    //SKIP;
                }
                if((gameStatus[curr_begin + j] & (1 << (7 - i))) != 0){
                    return true;
                }
            }

            return false;
        }
    }

    public Collection<Cell> playableCells(byte[] gameStatus, int player)
    {
        int /*pla_begin, adv_begin,*/ adv_player;

        /*
        if(player == 0){
            adv_player = 1;
            pla_begin = GAME_STATUS_PLAYER0_OFFSET;
            adv_begin = GAME_STATUS_PLAYER1_OFFSET;
        }else{
            adv_player = 0;
            pla_begin = GAME_STATUS_PLAYER1_OFFSET;
            adv_begin = GAME_STATUS_PLAYER0_OFFSET;
        }
        */

        adv_player = 1 - player;
        //pla_begin = GAME_STATUS_PLAYERS_OFFSET[player];
        //adv_begin = GAME_STATUS_PLAYERS_OFFSET[adv_player];

        int x, y;
        //boolean[] direction;

        //Cell cell;
        Collection<Cell> playable_cells = new LinkedList<Cell>();

        Cell[][] cells = game.board.cells;

/*
 *  *  * PLAYABLE CELLs ORDERING *  *  *
 * 		   0  1  2  3  4  5  6  7	   *
 * 		0 01 09 17 29 33 24 16 04	   *
 * 		1 13 05 37 45 49 44 08 12      *
 * 		2 21 41 25 53 57 28 40 20      *
 * 		3 34 50 58 ## ## 56 48 32      *
 * 		4 30 46 54 ## ## 60 52 36      *
 * 		5 18 38 26 59 55 27 43 23      *
 * 		6 10 06 42 51 47 39 07 15      *
 * 		7 02 14 22 35 31 19 11 03      *
 */
        //Corners
        x = 0; y = 0;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 0; y = 7;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 7; y = 7;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 7; y = 0;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);

        //PRE-C Square
        x = 2; y = 0;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 0; y = 5;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 5; y = 7;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 7; y = 2;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);

        x = 0; y = 2;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 2; y = 7;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 7; y = 5;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 5; y = 0;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);

        //PRE-X Squares
        x = 2; y = 2;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 2; y = 5;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 5; y = 5;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 5; y = 2;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);

        //Remaining Egdes
        x = 3; y = 0;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 0; y = 4;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 4; y = 7;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 7; y = 3;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);

        x = 4; y = 0;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 0; y = 3;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 3; y = 7;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 7; y = 4;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);

        x = 2; y = 1;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 1; y = 5;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 5; y = 6;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 6; y = 2;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);

        x = 1; y = 2;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 2; y = 6;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 6; y = 5;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 5; y = 1;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);

        x = 3; y = 1;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 1; y = 4;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 4; y = 6;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 6; y = 3;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);

        x = 4; y = 1;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 1; y = 3;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 3; y = 6;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 6; y = 4;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);

        x = 3; y = 2;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 2; y = 4;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 4; y = 5;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 5; y = 3;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);

        x = 4; y = 2;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 2; y = 3;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 3; y = 5;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 5; y = 4;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);

        //C Squares
        x = 1; y = 0;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 0; y = 6;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 6; y = 7;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 7; y = 1;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);

        x = 0; y = 1;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 1; y = 7;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 7; y = 6;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 6; y = 0;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);

        //X Squares
        x = 1; y = 1;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 1; y = 6;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 6; y = 6;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);
        x = 6; y = 1;
        if(isPlayableCell(gameStatus, player, adv_player, x, y))
            playable_cells.add(cells[x][y]);

        return playable_cells;
    }

    @Override
    public Collection<? extends Move> playableMoves(GameStatus gameStatus, int playerId)
    {
        //TODO realizzare un metodo playableCells che riceve GameStatus invece di byte[]
        Collection<Cell> cells = playableCells(encode(gameStatus), playerId);

        if(cells.isEmpty()){
            return Collections.singleton(OthiGame.MOVE_NONE);
        }

        Collection<Move> moves = new LinkedList<Move>();
        for(Cell cell : cells){
            //moves.add( MoveFactory.getSingletonMove(new Integer(player*64 + cell.y*8 + cell.x)) );
//            final int cellId = OthiGame.cellId(cell.x, cell.y);
//            final int pieceId = OthiGame.pieceId(playerId, cellId);
//            final int moveId = OthiGame.moveId(playerId, pieceId);
            moves.add(OthiGame.move(playerId, cell.x, cell.y));
        }

        return moves;
    }

    @Override
    public Collection<byte[]> playableMoves(byte[] gameStatus, int playerId)
    {
        Collection<Cell> cells = playableCells(gameStatus, playerId);

        if(cells.isEmpty())
            return Collections.singleton(CODED_MOVE_NONE);

        Collection<byte[]> moves = new LinkedList<byte[]>();
        for(Cell cell : cells){
            //moves.add( MoveFactory.getSingletonMove(new Integer(player*64 + cell.y*8 + cell.x)) );
            moves.add(CODED_MOVES[playerId * 64 + cell.y * 8 + cell.x]);
        }

        return moves;
    }

    //TODO sempre più convinto che servirebbe uno o più bytes di analytics
    public int currentPlayer(byte[] gameStatus)
    {
        //return OthiGame.currentPlayerAtMove(gameStatus[GAME_STATUS_MOVENUMBER_OFFSET]);
        return (gameStatus[GAME_STATUS_MOVENUMBER_OFFSET] & 1) ^ 1; //(move# %2 == 0)?1 :0
    }

    public int nextPlayer(byte[] gameStatus)
    {
        //return OthiGame.nextPlayerAtMove(gameStatus[GAME_STATUS_MOVENUMBER_OFFSET]);
        return (gameStatus[GAME_STATUS_MOVENUMBER_OFFSET] & 1); //(move# %2 == 0)?0 :1
    }

//    public Collection<byte[]> currentPlayableMoves(byte[] gameStatus)
//    {
//        return playableMoves( gameStatus, (gameStatus[GAME_STATUS_MOVENUMBER_OFFSET] & 1) ^ 1 );
//    }
//
//    public Collection<byte[]> nextPlayableMoves(byte[] gameStatus)
//    {
//        return playableMoves( gameStatus, gameStatus[GAME_STATUS_MOVENUMBER_OFFSET] & 1 );
//    }

    public GameStatus makeMove(GameStatus gameStatus, Move move)
    {
        return decodeGameStatus(makeMove(encode(gameStatus), encode(move)));
    }

    @Override
    public byte[] makeMove(byte[] gameStatus, byte[] move)
    {
        return gameStatus; //TODO makeMove...
    }

    @Override
    public int[] playersScores(byte[] gameStatus)
    {
        int[] scores = new int[2];
        byte tmp;

        for(int i = 0; i < 8; i++){
            tmp = gameStatus[GAME_STATUS_PLAYER0_OFFSET + i];
            if(tmp == ALL_ONES){
                scores[0] += 8;
                continue;
            }
            while(tmp != 0){
                tmp &= tmp - 1;
                scores[0]++;
            }

            tmp = gameStatus[GAME_STATUS_PLAYER1_OFFSET + i];
            if(tmp == ALL_ONES){
                scores[1] += 8;
                continue;
            }
            while(tmp != 0){
                tmp &= tmp - 1;
                scores[1]++;
            }
/*
            for(int j = 0; j < 8; j++)
            {
	            if( (gameStatus[GAME_STATUS_PLAYER0_OFFSET+i] & (1<<j)) != 0)
	                scores[0]++;
	            else if( (gameStatus[GAME_STATUS_PLAYER1_OFFSET+i] & (1<<j)) != 0)
	                scores[1]++;
            }
*/
        }
        return scores;
    }

    public boolean isFinalGameStatus(byte[] gameStatus)
    {
//        if(gameStatus[GAME_STATUS_MOVENUMBER_OFFSET] >= 60){
//            byte[] gameFieldStatus = gameFieldStatus(gameStatus);
//            int i;
//            for(i = 0; i < gameFieldStatus.length && gameFieldStatus[i] == ALL_ONES; i++)
//                ;
//
//            if(i == gameFieldStatus.length - 1)
//                return true;
//        }
//        return playableCellsNumber(gameStatus, currentPlayer(gameStatus)) == 0 && playableCellsNumber(gameStatus, nextPlayer(gameStatus)) == 0;


//        int i = 0;
//        //if(gameStatus[GAME_STATUS_MOVENUMBER_OFFSET] >= 60){
//            byte curr_row;
//            do{
//                curr_row = (byte)(gameStatus[GAME_STATUS_PLAYER0_OFFSET + i] | gameStatus[GAME_STATUS_PLAYER1_OFFSET + i]);
//                ++i;
//            }while(i < OthiGameRules.Y_DIM && curr_row == ALL_ONES);
//
//            if(i == OthiGameRules.Y_DIM && curr_row == ALL_ONES){
//                Log.v(TAG, "All bits are 1 so this game status is final!"); //FIXME rimuovere quando saremo certi che è corretto
//                return true;
//            }
//        //}

        int curr_player = currentPlayer(gameStatus);
        int next_player = 1 - curr_player;//nextPlayer(gameStatus);

        int playable_cells = 0;
        byte curr_row;
        for(int y = 0; y < OthiGameRules.Y_DIM; y++){
            curr_row = (byte)(gameStatus[GAME_STATUS_PLAYER0_OFFSET + y] | gameStatus[GAME_STATUS_PLAYER1_OFFSET + y]);
            if(curr_row == ALL_ONES){
                continue; //SKIP ROW...
            }else{
                for(int x = 0; x < OthiGameRules.X_DIM; x++){
                    if((curr_row & (1 << (7 - x))) == 0 && isPlayableCell(gameStatus, curr_player, next_player, x, y)){
                        playable_cells++; //TODO TESTARE CHE SIA TUTTO OK!
                    }
                }
            }
        }

        Log.v(TAG, "Playable cells: "+playable_cells); //FIXME rimuovere a fine test
        return playable_cells == 0;
    }

//    public byte[] gameFieldStatus(byte[] gameStatus)
//    {
//        byte[] gameFieldStatus = new byte[OthiGameRules.Y_DIM];
//        for(int i = 0; i < gameFieldStatus.length; i++){
//            gameFieldStatus[i] = (byte) (gameStatus[GAME_STATUS_PLAYER0_OFFSET + i] | gameStatus[GAME_STATUS_PLAYER1_OFFSET + i]);
//        }
//        return gameFieldStatus;
//    }

//    public int playableCellsNumber(byte[] gameStatus, int player)
//    {
//        int pla_begin, adv_begin, adv_player;
//
//        if(player == 0){
//            adv_player = 1;
//            pla_begin = GAME_STATUS_PLAYER0_OFFSET;
//            adv_begin = GAME_STATUS_PLAYER1_OFFSET;
//        }else{
//            adv_player = 0;
//            pla_begin = GAME_STATUS_PLAYER1_OFFSET;
//            adv_begin = GAME_STATUS_PLAYER0_OFFSET;
//        }
//
//        int counter = 0;
//
//        for(int x = 0; x < 8; x++)
//            for(int y = 0; y < 8; y++)
//                if(isPlayableCell(gameStatus, player, adv_player, x, y))
//                    counter++;
//        return counter;
//    }

}
