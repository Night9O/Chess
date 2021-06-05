package com.Night9O.Chess.Pieces;
// TODO: en passant move maybe?, you choose.

import com.Night9O.Chess.Board.Board;
import com.Night9O.Chess.Board.Tile;

import java.util.ArrayList;

public class Pawn extends Piece
{
    private boolean firstMove = true;


    public Pawn(Color color)
    {
        this.color = color;
    }

    /**
     * THIS IS WHAT HAPPENS WHEN YOU SKIP DATA STRUCTURE CLASSES!!!!!!!!!
     * MY LORD IM SORRY FOR THIS CODE GORE I HAVE SINNED, FORGIVE ME FOR I AM A SIMP
     * @param pieceX X coordinate of the pawn piece
     * @param pieceY Y coordinate of the pawn piece
     * @param board: Board instance to get the locations of all the pieces
     * @return my miserable life
     */
    @Override
    public int[][] giveLegalMovements(int pieceX, int pieceY, Board board)
    {
        ArrayList<Integer> legalXMovements = new ArrayList<>();
        ArrayList<Integer> legalYMovements = new ArrayList<>();

        if (color == Color.BLACK)
        {
            //if the pawn has been moved, remove the firstMove buff
            if (pieceY != 1) { firstMove = false;}

            //Move the pawn one square ahead
            if (pieceY + 1 < 8)
            {
                //make sure that the tile ahead is not occupied by another piece.
                if (board.getTile(pieceX, pieceY + 1).checkIsEmpty()) { legalXMovements.add(pieceX); legalYMovements.add(pieceY + 1); }
            }


            //move the pawn 2 square ahead when it has the firstMove buff
            if (firstMove)
            {
                if (board.getTile(pieceX, pieceY + 2).checkIsEmpty() && board.getTile(pieceX, pieceY + 1).checkIsEmpty()) { legalXMovements.add(pieceX); legalYMovements.add(pieceY + 2); }
            }

            //The pawn can eat from it's left diagonal when there is an enemy
            if (pieceX - 1 >= 0 && pieceY + 1 < 8)
            {
                //when there is an occupied tile at the left diagonal of the pawn by the enemy
                if (!board.getTile(pieceX -  1, pieceY + 1).checkIsEmpty() && color != board.getTile(pieceX -  1, pieceY + 1).getPiece().getColor())
                {
                    legalXMovements.add(pieceX - 1);
                    legalYMovements.add(pieceY + 1);
                }
            }

            //The pawn can eat from it's Right diagonal when there is an enemy
            if (pieceX + 1 < 8  && pieceY + 1 < 8)
            {
                //when there is an occupied tile at the Right diagonal of the pawn by the enemy
                if (!board.getTile(pieceX +  1, pieceY + 1).checkIsEmpty() && color != board.getTile(pieceX +  1, pieceY + 1).getPiece().getColor())
                {
                    legalXMovements.add(pieceX + 1);
                    legalYMovements.add(pieceY + 1);
                }
            }

            //make the Pawn become a Queen
            if (pieceY == 7)
            {
                board.setTile(pieceX, pieceY, new Tile.OccupiedTile(pieceX, pieceY, new Queen(Color.BLACK), PieceID.QUEEN));
            }
        }

        if (color == Color.WHITE)
        {
            if (pieceY != 6) { firstMove = false; }

            //move 1 square ahead
            if (pieceY - 1 >= 0)
            {
                //make sure that the tile ahead is not occupied by another piece.
                if (board.getTile(pieceX, pieceY - 1).checkIsEmpty()) { legalXMovements.add(pieceX); legalYMovements.add(pieceY - 1); }
            }

            //move the pawn 2 square ahead when it has the firstMove buff
            if (firstMove)
            {
                if (board.getTile(pieceX, pieceY - 2).checkIsEmpty() && board.getTile(pieceX, pieceY - 1).checkIsEmpty()) { legalXMovements.add(pieceX); legalYMovements.add(pieceY - 2); }
            }

            //The pawn can eat from it's left diagonal when there is an enemy
            if (pieceX - 1 >= 0 && pieceY - 1  >= 0)
            {
                //when there is an occupied tile at the left diagonal of the pawn by the enemy
                if (!board.getTile(pieceX -  1, pieceY - 1).checkIsEmpty() && color != board.getTile(pieceX -  1, pieceY - 1).getPiece().getColor())
                {
                    legalXMovements.add(pieceX - 1);
                    legalYMovements.add(pieceY - 1);
                }
            }

            //The pawn can eat from it's Right diagonal when there is an enemy
            if (pieceX + 1 < 8  && pieceY - 1 >= 0)
            {
                //when there is an occupied tile at the Right diagonal of the pawn by the enemy
                if (!board.getTile(pieceX +  1, pieceY - 1).checkIsEmpty() && color != board.getTile(pieceX +  1, pieceY - 1).getPiece().getColor())
                {
                    legalXMovements.add(pieceX + 1);
                    legalYMovements.add(pieceY - 1);
                }
            }

            //make the Pawn become a Queen
            if (pieceY == 0)
            {
                board.setTile(pieceX, pieceY, new Tile.OccupiedTile(pieceX, pieceY, new Queen(Color.WHITE), PieceID.QUEEN));
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
