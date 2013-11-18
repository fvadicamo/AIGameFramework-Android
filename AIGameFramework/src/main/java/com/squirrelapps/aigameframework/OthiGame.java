package com.squirrelapps.aigameframework;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public class OthiGame extends Game
{
    static final Cell[][] CELLS = new Cell[OthiGameRules.X_DIM][OthiGameRules.Y_DIM];
    static{
        for(int x=0; x<OthiGameRules.X_DIM; x++){
            for(int y=0; y<OthiGameRules.Y_DIM; y++){
                CELLS[x][y] = new Cell(x, y);
            }
        }
    }

    static final int cellId(int x, int y)
    {
        return y*OthiGameRules.X_DIM + x;
    }

    static final Cell cell(int cellId)
    {
        //FIXME verificare...
        final int x = cellId%OthiGameRules.X_DIM;
        final int y = cellId/OthiGameRules.X_DIM;
        return cell(x, y);
    }

    static final Cell cell(int x, int y)
    {
        //REMIND for OthiGame we know all cells have already been created
        return CELLS[x][y];
    }

    static final Piece[] PIECES = new Piece[OthiGameRules.PIECES_NUM];
    static{
//        for(int index = 0; index < PIECES.length; index++){
//            PIECES[index] = Pawn.valueOf(index);
//        }
        for(int playerId = 0; playerId < OthiGameRules.PLAYERS_NUM; playerId++){
            //for(int cellId = 0; cellId < ....)
            for(int x=0; x<OthiGameRules.X_DIM; x++){
                for(int y=0; y<OthiGameRules.Y_DIM; y++){
                    final int pieceId = pieceId(playerId, x, y);

                    //REMIND we assume that pieceIndex == pieceId
                    PIECES[pieceId] = Pawn.valueOf(pieceId);
                }
            }
        }
    }

    static final int pieceId(int playerId, int x, int y)
    {
        return playerId*OthiGameRules.PIECES_PER_PLAYER + cellId(x, y);
    }

    static final int pieceId(int playerId, int cellId)
    {
        return playerId*OthiGameRules.PIECES_PER_PLAYER + cellId;
    }

    static final Piece piece(int playerId, int x, int y)
    {
        return piece(pieceId(playerId, x, y));
    }

    static final Piece piece(int pieceId)
    {
        //REMIND for OthiGame we know all pieces have already been created
        return PIECES[pieceId];
    }

    static final Cell pieceCell(int playerId, int pieceId)
    {
        //REMIND in OthiGame we set pieceId = playerId*OthiGameRules.PIECES_PER_PLAYER + cellId(x, y)
        return cell(pieceId - playerId*OthiGameRules.PIECES_PER_PLAYER);
    }

    static final OthiMove MOVE_NONE = new OthiMove(~0, ~0, (Piece)null, (Cell)null);

    static final OthiMove[] MOVES = new OthiMove[OthiGameRules.MOVES_NUM];
    static{
        for(int playerId = 0; playerId < OthiGameRules.PLAYERS_NUM; playerId++){
            //for(Piece piece : PIECES)
            for(int x = 0; x < OthiGameRules.X_DIM; x++){
                for(int y = 0; y < OthiGameRules.Y_DIM; y++){
                    final int cellId = cellId(x, y);
                    final int pieceId = pieceId(playerId, cellId);
                    final int moveId = moveId(playerId, pieceId);

                    //REMIND we assume that moveIndex == moveId
                    MOVES[moveId] = new OthiMove(moveId, playerId, piece(pieceId), cell(cellId));
                }
            }
        }
    }

    static final int moveId(int playerId, int pieceId)
    {
        return /*playerId*OthiGameRules.MOVES_PER_PLAYER +*/ pieceId; //REMIND we know that pieceId already includes playerId info
    }

    static final Move move(int playerId, int x, int y)
    {
        return move(moveId(playerId, pieceId(playerId, x, y)));
    }

    static final Move move(int moveId)
    {
        //REMIND for OthiGame we know all moves have already been created
        return MOVES[moveId];
    }

    static final int currentPlayerAtMove(int moveNumber)
    {
        //(moveNumber %2 == 0)?1 :0
        return (moveNumber & 1) ^ 1; //REMIND for OthiGame we know that player 0 plays at even moves
    }

    static final int nextPlayerAtMove(int moveNumber)
    {
        //(moveNumber %2 == 0)?0 :1
        return (moveNumber & 1); //REMIND for OthiGame we know that player 0 plays at even moves
    }


    public OthiGame(Board board, Player[] players, GameStatus firstGameStatus)
    {
        super(board, players, firstGameStatus);
    }
}
