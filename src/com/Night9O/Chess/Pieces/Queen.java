package com.Night9O.Chess.Pieces;

import com.Night9O.Chess.Board.Board;

public class Queen extends Piece
{

    public Queen(Color color) { this.color = color; }

    @Override
    public int[][] giveLegalMovements(int pieceX, int pieceY, Board board)
    {
        int[][] diagonalMoves = diagonalMoves(pieceX, pieceY, board);
        int[][] straightMoves = straightMoves(pieceX, pieceY, board);

        int[][] queenLegalMoves = new int[diagonalMoves.length + straightMoves.length][2];

        System.arraycopy(diagonalMoves, 0, queenLegalMoves, 0, diagonalMoves.length);
        System.arraycopy(straightMoves, 0, queenLegalMoves, diagonalMoves.length, straightMoves.length);

        return queenLegalMoves;

    }

    @Override
    public Color getColor() { return color; }
}
