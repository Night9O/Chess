package com.Night9O.Chess.Players.AI;

import com.Night9O.Chess.Board.Board;
import com.Night9O.Chess.Pieces.Piece;
import com.Night9O.Chess.Players.PlayerPossibleMoves;
import com.Night9O.Chess.WinTests.CheckMateTest;

public class Scoring
{
    private CheckMateTest checkMateTest;

    public Scoring(CheckMateTest checkMateTest)
    {
        this.checkMateTest = checkMateTest;
    }

    private final int[] scoreList = new int[]
            {
                    42069911, //0: KingScore
                    1000, //1: QueenScore
                    750, //2: Bishop
                    500, //3: Rook
                    250, //4: Knights
                    100 //5: Pawn

            };

    public int calculateScore(Board board)
    {
        int score = 0;

        //MaximizerSection
        if (checkMateTest.whiteCheckMate()) { score += 42069911; }
        if (checkMateTest.isWhiteCheck()) { score += 50;}
        score += calculateFieldScore(board, Piece.Color.BLACK);
        score += calculateStrategyScore(board, Piece.Color.BLACK);

        //MinimizeSection
        if (checkMateTest.blackCheckMate()) { score -= 42069911; }
        if (checkMateTest.isBlackCheck()) { score -= 50;}
        score -= calculateFieldScore(board, Piece.Color.WHITE);
        score -= calculateStrategyScore(board, Piece.Color.WHITE);

        return score;
    }

    private int calculateFieldScore(Board board, Piece.Color color)
    {

        int currentFieldScore = 0;
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                if (!board.getTile(x,y).checkIsEmpty())
                {
                    Piece.PieceID pieceID = board.getTile(x,y).getPieceID();
                    Piece.Color pieceColor = board.getTile(x,y).getPiece().getColor();
                    if (pieceColor == color)
                    {
                        if (pieceID == Piece.PieceID.KING) { currentFieldScore += scoreList[0]; }
                        if (pieceID == Piece.PieceID.QUEEN) { currentFieldScore += scoreList[1]; }
                        if (pieceID == Piece.PieceID.BISHOP) { currentFieldScore += scoreList[2]; }
                        if (pieceID == Piece.PieceID.ROOK) { currentFieldScore += scoreList[3]; }
                        if (pieceID == Piece.PieceID.KNIGHT) { currentFieldScore += scoreList[4]; }
                        if (pieceID == Piece.PieceID.PAWN) { currentFieldScore += scoreList[5]; }
                    }
                }
            }
        }
        return currentFieldScore;
    }

    private int calculateStrategyScore(Board board, Piece.Color color)
    {
        int[][] playerMoves = PlayerPossibleMoves.getPlayerPossibleMoves(board, color);

        return 25 * playerMoves.length;
    }
}
