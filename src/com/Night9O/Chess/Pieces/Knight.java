package com.Night9O.Chess.Pieces;

import com.Night9O.Chess.Board.Board;

import java.util.ArrayList;

public class Knight extends Piece
{

    public Knight(Color color) { this.color = color; }

    /**
     * for the knight case, each of each moves has a mirror move, which makes things easier unlike the damn pawn
     * also the Knight form a Rectangle when he moves, and never a square.
     * one positive side = abs(negative mirrorX) and abs(negative mirrorY)
     * @param pieceX X location
     * @param pieceY Y Location
     * @param board: Board instance to get the locations of all the pieces
     * @return possible legal moves for the knight.
     */
    @Override
    public int[][] giveLegalMovements(int pieceX, int pieceY, Board board)
    {
        ArrayList<Integer> legalXMovements = new ArrayList<>();
        ArrayList<Integer> legalYMovements = new ArrayList<>();

        for (int x = 2; x > -3; x--)
        {
            for (int y = 2; y > -3; y--)
            {
                int rectangle = Math.abs(x) * Math.abs(y);
                if (rectangle != Math.pow(x,2) && rectangle != Math.pow(y,2) )
                {
                    //Make sure things doesn't go OutOfBounds
                    if (pieceX + x >= 0 && pieceX + x < 8)
                    {
                        if (pieceY + y >= 0 && pieceY + y < 8)
                        {
                            //make sure that the knight doesn't eat his allies
                            if (board.getTile(pieceX + x, pieceY + y).checkIsEmpty())
                            {
                                legalXMovements.add(pieceX + x);
                                legalYMovements.add(pieceY + y);
                            }
                            else
                                {
                                    //if enemy.
                                    if (color != board.getTile(pieceX + x, pieceY + y).getPiece().getColor())
                                    {
                                        legalXMovements.add(pieceX + x);
                                        legalYMovements.add(pieceY + y);
                                    }
                                }
                        }
                    }
                }
            }
        }

        // legalXMovements.size == legalYMovements.size, if not don't be surprised, this has been coded by a dumbAss
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
