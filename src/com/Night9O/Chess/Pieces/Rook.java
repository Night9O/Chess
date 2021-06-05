package com.Night9O.Chess.Pieces;

import com.Night9O.Chess.Board.Board;

public class Rook extends Piece
{
    public Rook(Color color) { this.color = color; }

    @Override
    public int[][] giveLegalMovements(int pieceX, int pieceY, Board board) { return straightMoves(pieceX, pieceY, board); }

    @Override
    public Color getColor() { return color; }
}
