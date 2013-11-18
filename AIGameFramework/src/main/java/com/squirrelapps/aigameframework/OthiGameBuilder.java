package com.squirrelapps.aigameframework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Copyright (C) 2013 Francesco Vadicamo.
 */
public class OthiGameBuilder implements GameBuilder
{
    SquareBoard board;
    final ArrayList<Player> players = new ArrayList<Player>(OthiGameRules.PLAYERS_NUM);
    GameStatus firstGS;

    @Override
    public Game getGame()
    {
        return new OthiGame(board, players.toArray(new Player[players.size()]), firstGS);
    }

    @Override
    public GameBuilder buildBoard(/*int xDim, int yDim*/)
    {
        board = new SquareBoard(OthiGame.CELLS);
        return this;
    }

    @Override
    public GameBuilder buildHumanPlayer(int id, String name)
    {
        players.add(id, new HumanPlayer(id, name));
        return this;
    }

    @Override
    public GameBuilder buildComputerPlayer(int id, String name, SearchAlgorithm searchAlgorithm, int maxDept)
    {
        players.add(id, new HumanPlayer(id, name)); //FIXME change with ComputerPlayer
        return this;
    }

    @Override
    public GameBuilder buildFirsGameStatus(int firstPlayerId)
    {
        firstGS = new GameStatus();
        //TODO inizializzare tutti i parametri di gameStatus

        //final Cell[][] cells = OthiGame.CELLS; //board.cells;
        final int p0 = 0;
        final int p1 = 1;
        final int xDim = board.xDim; //8
        final int yDim = board.yDim; //8

        firstGS.setMove(OthiGame.MOVE_NONE);
        firstGS.setMoveNumber(0);
        firstGS.setCurrentPlayer(~0); //TODO ALL_ONE
        firstGS.setNextPlayer(p0);

        //BOARD STATUS
        SimpleBoardStatus boardStatus = new SimpleBoardStatus();

        Set<Cell> availableCells = new HashSet<Cell>(128, 0.51f); //128 > 64/0.51 --> no rehash!
        Map<Cell, Piece> pieceMap = new HashMap<Cell, Piece>(64, 0.75f); //128 > 64/0.75 --> no rehash!
        for(int y = 0; y < yDim; y++){ //TODO utilizzare xDim e yDim (xDim/2 - 1)
            for(int x = 0; x < xDim; x++){
                availableCells.add( OthiGame.cell(x, y) );
            }
        }

        availableCells.remove( OthiGame.cell(3, 3) );
        availableCells.remove( OthiGame.cell(4, 3) );
        availableCells.remove( OthiGame.cell(4, 4) );
        availableCells.remove( OthiGame.cell(3, 4) );

        //PieceFactory.getSingletonPiece(Integer.parseInt (0*64 + 3*8 + 4))
        pieceMap.put( OthiGame.cell(4, 3), OthiGame.piece(p0, 4, 3) );
        pieceMap.put( OthiGame.cell(3, 4), OthiGame.piece(p0, 3, 4) );
        pieceMap.put( OthiGame.cell(3, 3), OthiGame.piece(p1, 3, 3) );
        pieceMap.put( OthiGame.cell(4, 4), OthiGame.piece(p1, 4, 4) );

        boardStatus.setAvailableCells(availableCells);
        boardStatus.setPiecesMap(pieceMap);

        //PLAYERS STATUS
        SimplePlayerStatus[] playerStatus = new SimplePlayerStatus[]{ new SimplePlayerStatus(), new SimplePlayerStatus() };
        Set<Piece>[] availablePieces = new HashSet[2];
        availablePieces[0] = new HashSet<Piece>(128, 0.51f); //128 > 64/0.51 --> no rehash!
        availablePieces[1] = new HashSet<Piece>(128, 0.51f); //128 > 64/0.51 --> no rehash!
        Map<Piece, Cell>[] cellsMap = new HashMap[2];
        cellsMap[0] = new HashMap<Piece, Cell>(32, 0.75f); //32*2 > 64/0.75 --> 1 solo rehash!
        cellsMap[1] = new HashMap<Piece, Cell>(32, 0.75f); //32*2 > 64/0.75 --> 1 solo rehash!

        for(int y = 0; y < yDim; y++){
            for(int x = 0; x < xDim; x++){
                for(int k = 0; k < playerStatus.length; k++){
                    availablePieces[k].add( OthiGame.piece(k, x, y) );
                }
            }
        }

        availablePieces[0].remove( OthiGame.piece(p0, 4, 3) );
        availablePieces[0].remove( OthiGame.piece(p0, 3, 4) );
        availablePieces[1].remove( OthiGame.piece(p1, 3, 3) );
        availablePieces[1].remove( OthiGame.piece(p1, 4, 4) );

        cellsMap[0].put( OthiGame.piece(p0, 4, 3), OthiGame.cell(4, 3) );
        cellsMap[0].put( OthiGame.piece(p0, 3, 4), OthiGame.cell(3, 4) );
        cellsMap[1].put( OthiGame.piece(p1, 3, 3), OthiGame.cell(3, 3) );
        cellsMap[1].put( OthiGame.piece(p1, 4, 4), OthiGame.cell(4, 4) );

        playerStatus[0].setAvailablePieces(availablePieces[0]);
        playerStatus[0].setCellsMap(cellsMap[0]);
        playerStatus[1].setAvailablePieces(availablePieces[1]);
        playerStatus[1].setCellsMap(cellsMap[1]);

        firstGS.setBoardStatus(boardStatus);
        firstGS.setPlayersStatus(playerStatus);

        return this;
    }
}
