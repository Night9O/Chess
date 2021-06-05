package com.Night9O.Chess.Pieces;

import com.Night9O.Chess.Board.Board;

import java.util.ArrayList;

public class King extends Piece
{
    public King(Color color) { this.color = color; }

    @Override
    public int[][] giveLegalMovements(int pieceX, int pieceY, Board board)
    {
        ArrayList<Integer> legalXMovements = new ArrayList<>();
        ArrayList<Integer> legalYMovements = new ArrayList<>();

        for (int x = 1; x > -2; x--)
        {
            for (int y = 1; y > -2; y--)
            {
                if (x == 0 && y== 0) { y--; }
                //make sure nothing goes OutOfBound
                if (pieceX + x >= 0 && pieceX + x < 8 && pieceY + y >= 0 && pieceY + y < 8)
                {
                    //if tile is empty or there is an enemy in the tile you can move there.
                    if (board.getTile(pieceX + x, pieceY + y).checkIsEmpty() || color != board.getTile(pieceX + x, pieceY +  y).getPiece().getColor())
                    {
                        legalXMovements.add(pieceX + x);
                        legalYMovements.add(pieceY + y);
                    }
                }
            }
        }

        int[][] finalLegalMovements = new int[legalXMovements.size()][2];
        for (int x = 0; x < legalXMovements.size(); x++)
        {
            finalLegalMovements[x][0] = legalXMovements.get(x);
            finalLegalMovements[x][1] = legalYMovements.get(x);
        }

        return finalLegalMovements;
    }

    @Override
    public Color getColor() { return color; }
}
