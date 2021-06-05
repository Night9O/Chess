package com.Night9O.Chess.Players;

import com.Night9O.Chess.Board.Board;
import com.Night9O.Chess.Pieces.Piece;

import java.util.ArrayList;

public class PlayerPossibleMoves
{
    //Used for minimax Algorithm
    public static int[][] getPlayerPossibleMoves(Board board, Piece.Color color)
    {
        ArrayList<Integer> possibleMovesX = new ArrayList<>();
        ArrayList<Integer> possibleMovesY = new ArrayList<>();

        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                if (!board.getTile(x,y).checkIsEmpty() && board.getTile(x,y).getPiece().getColor() == color)
                {
                    int[][] moves = board.getTile(x,y).getPiece().giveLegalMovements(x,y,board);
                    for (int[] move: moves)
                    {
                        possibleMovesX.add(move[0]);
                        possibleMovesY.add(move[1]);
                    }
                }
            }
        }
        int[][] allPossibleMoves = new int[possibleMovesX.size()][2];
        for (int x = 0; x < possibleMovesX.size(); x++)
        {
            allPossibleMoves[x][0] = possibleMovesX.get(x);
            allPossibleMoves[x][1] = possibleMovesY.get(x);
        }

        return allPossibleMoves;
    }

    public static int[] getPieceLocationFromMove(int[] theMove, Board board, Piece.Color color)
    {
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                if (!board.getTile(x,y).checkIsEmpty() && board.getTile(x,y).getPiece().getColor() == color)
                {
                    int[][] moves = board.getTile(x,y).getPiece().giveLegalMovements(x,y,board);
                    for (int[] move: moves)
                    {
                        if (theMove[0] == move[0] &&  theMove[1] == move[1]) { return new int[] {x, y};}
                    }
                }
            }
        }
        return null;
    }
}
