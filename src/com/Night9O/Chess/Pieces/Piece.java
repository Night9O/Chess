package com.Night9O.Chess.Pieces;

import com.Night9O.Chess.Board.Board;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Piece
{
    public enum PieceID
    {
        KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN
    }

    public enum Color
    {
        WHITE, BLACK
    }

    /**
     * 0: white
     * 1: black
     */
    protected Color color;

    /**
     * check all movements a piece can have then returns the final legal movements a piece can have.
     * @param pieceX: X coordinates of the piece
     * @param pieceY : Y Coordinates of the piece
     * @param board: Board instance to get the locations of all the pieces
     * @return final 2D array of all legal possible movements
     */
    public abstract int[][] giveLegalMovements(int pieceX, int pieceY, Board board);

    public abstract Color getColor();

    //************************************* Non Abstract ************************************************//

    /**
     * we have 2 functions that we can work with:
     * y = abs(x)
     * y = -abs(x)
     * and as abs(x) equals:
     * 1/ x
     * 2/ - x
     * we work with 4 cases;
     * Case 1: y = x and x >= 0
     * Case 2: y = -x and x <= 0;
     * Case 3: y = -x and x >= 0
     * Case 4: y = x and x <= 0
     * @param pieceX: X coordinates of the piece
     * @param pieceY : Y Coordinates of the piece
     * @param board: Board instance to get the locations of all
     * @return final 2D array of all legal possible movements
     */
    protected int[][] diagonalMoves(int pieceX, int pieceY, Board board)
    {
        ArrayList<Integer> legalXMovements = new ArrayList<>();
        ArrayList<Integer> legalYMovements = new ArrayList<>();
        boolean breakLoop = false;

        //Case 1: y = x and x > 0, thus y> 0
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                if (x == 0 && y == 0) { y++; }
                if (y == x)
                {
                    //make sure nothing goes OutOfBound
                    if (pieceX + x >= 0 && pieceX + x < 8 && pieceY + y >= 0 && pieceY + y < 8)
                    {
                        //if tile is empty or there is an enemy in the tile you can move there.
                        if (board.getTile(pieceX + x, pieceY + y).checkIsEmpty() || color != board.getTile(pieceX + x, pieceY +  y).getPiece().getColor())
                        {
                            legalXMovements.add(pieceX + x);
                            legalYMovements.add(pieceY + y);
                        }
                        //BreakLoop so the piece cannot jump over others which is illegal in chess.
                        if (!board.getTile(pieceX + x, pieceY + y).checkIsEmpty()) { breakLoop = true; break; }
                    }
                }
            }
            if (breakLoop) { breakLoop = false; break;}
        }

        //Case 2: y = -x and x =< 0; thus y >= 0
        for (int x = 0; x > -8; x--)
        {
            for (int y = 0; y < 8; y++)
            {
                if (x == 0 && y == 0) {y++;}
                if (y == -x)
                {
                    //make sure nothing goes OutOfBound
                    if (pieceX + x >= 0 && pieceX + x < 8 && pieceY + y >= 0 && pieceY + y < 8)
                    {
                        //if tile is empty or there is an enemy in the tile you can move there.
                        if (board.getTile(pieceX + x, pieceY + y).checkIsEmpty() || color != board.getTile(pieceX + x, pieceY +  y).getPiece().getColor())
                        {
                            legalXMovements.add(pieceX + x);
                            legalYMovements.add(pieceY + y);
                        }
                        //BreakLoop so the piece cannot jump over others which is illegal in chess.
                        if (!board.getTile(pieceX + x, pieceY + y).checkIsEmpty()) { breakLoop = true; break; }
                    }
                }
            }
            if (breakLoop) { breakLoop = false; break;}
        }

        //Case 3: y = -x and x >= 0 thus y <= 0
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y > -8; y--)
            {
                if (x == 0 && y == 0) {y--;}
                if (y == -x)
                {
                    //make sure nothing goes OutOfBound
                    if (pieceX + x >= 0 && pieceX + x < 8 && pieceY + y >= 0 && pieceY + y < 8)
                    {
                        //if tile is empty or there is an enemy in the tile you can move there.
                        if (board.getTile(pieceX + x, pieceY + y).checkIsEmpty() || color != board.getTile(pieceX + x, pieceY +  y).getPiece().getColor())
                        {
                            legalXMovements.add(pieceX + x);
                            legalYMovements.add(pieceY + y);
                        }
                        //BreakLoop so the piece cannot jump over others which is illegal in chess.
                        if (!board.getTile(pieceX + x, pieceY + y).checkIsEmpty()) { breakLoop = true; break; }
                    }
                }
            }
            if (breakLoop) { breakLoop = false; break;}
        }

        //Case 4: y = x and x <= 0 thus y <= 0
        for (int x = 0; x > -8; x--)
        {
            for (int y = 0; y > -8; y--)
            {
                if (x == 0 && y == 0) {y--;}
                if (y == x)
                {
                    //make sure nothing goes OutOfBound
                    if (pieceX + x >= 0 && pieceX + x < 8 && pieceY + y >= 0 && pieceY + y < 8)
                    {
                        //if tile is empty or there is an enemy in the tile you can move there.
                        if (board.getTile(pieceX + x, pieceY + y).checkIsEmpty() || color != board.getTile(pieceX + x, pieceY +  y).getPiece().getColor())
                        {
                            legalXMovements.add(pieceX + x);
                            legalYMovements.add(pieceY + y);
                        }
                        //BreakLoop so the piece cannot jump over others which is illegal in chess.
                        if (!board.getTile(pieceX + x, pieceY + y).checkIsEmpty()) { breakLoop = true; break; }
                    }
                }
            }
            if (breakLoop) { break;}
        }

        int[][] finalLegalMovements = new int[legalXMovements.size()][2];
        for (int x = 0; x < legalXMovements.size(); x++)
        {
            finalLegalMovements[x][0] = legalXMovements.get(x);
            finalLegalMovements[x][1] = legalYMovements.get(x);
        }

        return finalLegalMovements;
    }

    protected int[][] straightMoves(int pieceX, int pieceY, Board board)
    {
        ArrayList<Integer> legalXMovements = new ArrayList<>();
        ArrayList<Integer> legalYMovements = new ArrayList<>();

        //Case 1, x > 0; and y = 0;
        for (int x = 1; x < 8; x++)
        {
            if (pieceX + x >= 0 && pieceX + x < 8)
            {
                //if tile is empty or there is an enemy in the tile you can move there.
                if (board.getTile(pieceX + x, pieceY).checkIsEmpty() || color != board.getTile(pieceX + x, pieceY).getPiece().getColor())
                {
                    legalXMovements.add(pieceX + x);
                    legalYMovements.add(pieceY);
                }
                //BreakLoop so the piece cannot jump over others which is illegal in chess.
                if (!board.getTile(pieceX + x, pieceY).checkIsEmpty()) { break; }
            }
        }

        //Case 2: x < 0; y = 0;
        for (int x = -1; x > -8; x--)
        {
            if (pieceX + x >= 0 && pieceX + x < 8)
            {
                //if tile is empty or there is an enemy in the tile you can move there.
                if (board.getTile(pieceX + x, pieceY).checkIsEmpty() || color != board.getTile(pieceX + x, pieceY).getPiece().getColor())
                {
                    legalXMovements.add(pieceX + x);
                    legalYMovements.add(pieceY);
                }
                //BreakLoop so the piece cannot jump over others which is illegal in chess.
                if (!board.getTile(pieceX + x, pieceY).checkIsEmpty()) { break; }
            }
        }

        //Case 3: y > 0, x = 0;
        for (int y = 1; y < 8; y++)
        {
            if (pieceY + y >= 0 && pieceY + y < 8)
            {
                //if tile is empty or there is an enemy in the tile you can move there.
                if (board.getTile(pieceX, pieceY + y).checkIsEmpty() || color != board.getTile(pieceX, pieceY + y).getPiece().getColor())
                {
                    legalXMovements.add(pieceX);
                    legalYMovements.add(pieceY + y);
                }
                //BreakLoop so the piece cannot jump over others which is illegal in chess.
                if (!board.getTile(pieceX, pieceY + y).checkIsEmpty()) { break; }
            }
        }

        //Case 4: y < 0, x = 0;
        for (int y = -1; y > -8; y--)
        {
            if (pieceY + y >= 0 && pieceY + y < 8)
            {
                //if tile is empty or there is an enemy in the tile you can move there.
                if (board.getTile(pieceX, pieceY + y).checkIsEmpty() || color != board.getTile(pieceX, pieceY + y).getPiece().getColor())
                {
                    legalXMovements.add(pieceX);
                    legalYMovements.add(pieceY + y);
                }
                //BreakLoop so the piece cannot jump over others which is illegal in chess.
                if (!board.getTile(pieceX, pieceY + y).checkIsEmpty()) { break; }
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
}
