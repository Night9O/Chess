package com.Night9O.Chess.Pieces;

import com.Night9O.Chess.Board.Board;

import java.util.ArrayList;

public class Bishop extends Piece
{
    public Bishop(Color color)
    {
        this.color = color;
    }

    /**
     * We notice that the bishop has 2 functions :
     * f(x) = abs(x)
     * g(x) = -abs(x)
     * so programming these function will lead to our desired  behaviour.
     *@param pieceX: X coordinates of the piece
     * @param pieceY : Y Coordinates of the piece
     * @param board: Board instance to get the locations of all the pieces
     * @return legal movements of the bishop
     */
    @Override
    public int[][] giveLegalMovements(int pieceX, int pieceY, Board board)
    {
        return diagonalMoves(pieceX, pieceY, board);
    }

    @Override
    public Color getColor() { return color; }
}
