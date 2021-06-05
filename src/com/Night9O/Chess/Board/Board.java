package com.Night9O.Chess.Board;

import com.Night9O.Chess.Pieces.Piece;

public class Board implements Cloneable
{
    private Tile[][] board;

    public Board()
    {
        board = new Tile[8][8];
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                board[x][y] = new Tile.EmptyTile(x, y);
            }
        }
    }

    public Tile getTile(int x, int y)
    {
        return board[x][y];
    }

    public Tile setTile(int x, int y, Tile tile)
    {
        return board[x][y] = tile;
    }

    public Tile[][] getBoard()
    {
        return board;
    }

    public void setBoard(Tile[][] board) { this.board = board; }

    /**
     *
     * @return clone of an instance Board.
     */
    @Override
    public Board clone()
    {
        Board testBoard = new Board();
        Tile[][] chessBoardClone = new Tile[8][8];
        for (int x = 0; x < 8; x++)
        {
            System.arraycopy(board[x], 0, chessBoardClone[x], 0, 8);
        }
        testBoard.board = chessBoardClone;
        return testBoard;
    }
}
