package com.Night9O.Chess.Players.AI;

import com.Night9O.Chess.Board.Board;
import com.Night9O.Chess.Board.Tile;
import com.Night9O.Chess.MainHub.GraveYard;
import com.Night9O.Chess.MainHub.StaticFields;
import com.Night9O.Chess.MainHub.Turn;
import com.Night9O.Chess.Pieces.Pawn;
import com.Night9O.Chess.Pieces.Piece;
import com.Night9O.Chess.Players.PlayerPossibleMoves;
import com.Night9O.Chess.WinTests.CheckMateTest;


public class AI
{
    private CheckMateTest checkMateTest;
    private Scoring scoring;

    public AI(Board board)
    {
        checkMateTest = new CheckMateTest(board);
        scoring = new Scoring(checkMateTest);

    }

    public void AIMove(Board board, int depth)
    {
        //let the AI be the maximizer, and the player is the minimizer
        if (Turn.getTurn() == 1)
        {
            int bestScore = Integer.MIN_VALUE;
            Tile bestMove1 = null; //Clear the Tile where the moving piece had been sitting
            Tile bestMove2 = null; //nex tile where the Piece went to

            int[][] allAiMoves = PlayerPossibleMoves.getPlayerPossibleMoves(board, Piece.Color.BLACK);
            for (int[] AiMove : allAiMoves)
            {
                //Gathering information about the piece we are going to move
                int[] currentPieceLocation = PlayerPossibleMoves.getPieceLocationFromMove(AiMove, board, Piece.Color.BLACK);
                if (currentPieceLocation != null)
                {
                    Tile chosenTile = board.getTile(currentPieceLocation[0], currentPieceLocation[1]);
                    Piece.PieceID pieceID = chosenTile.getPieceID();
                    Piece piece = chosenTile.getPiece();

                    //Gathering information about the Tile we are going to move to
                    Tile engagedTile = board.getTile(AiMove[0], AiMove[1]);

                    //Start Testing Moves
                    Tile move1 = board.setTile(chosenTile.getTileX(), chosenTile.getTileY(), new Tile.EmptyTile(chosenTile.getTileX(), chosenTile.getTileY()));
                    Tile move2;

                    //The Ai messes around with the field (firstMove) in every pawn instance, so i had to make a new test instance
                    // for each Pawn :(, oh and these 3 lines made the code as slow as my problem solving skills
                    if (!(piece instanceof Pawn))
                    {
                        move2 = board.setTile(AiMove[0], AiMove[1], new Tile.OccupiedTile(AiMove[0], AiMove[1], piece, pieceID));
                    }
                    else
                    {
                        move2 = board.setTile(AiMove[0], AiMove[1], new Tile.OccupiedTile(AiMove[0], AiMove[1], new Pawn(Piece.Color.BLACK), pieceID));
                    }
                    if (!engagedTile.checkIsEmpty()) { GraveYard.addPiece(Piece.Color.WHITE, engagedTile.getPieceID()); }

                    int score = miniMax(board, depth, false);

                    //Revert the changes
                    board.setTile(currentPieceLocation[0], currentPieceLocation[1], chosenTile);
                    board.setTile(AiMove[0], AiMove[1], engagedTile);
                    if (!engagedTile.checkIsEmpty()) { GraveYard.removeLastPiece(Piece.Color.WHITE); }

                    //Calculate the results
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove1 = move1;
                        bestMove2 = move2;
                    }
                }
            }
            //Play the best move possible in the actual game.
            if (bestMove1 != null && bestMove2 != null)
            {
                board.setTile(bestMove1.getTileX(), bestMove1.getTileY(), bestMove1);
                board.setTile(bestMove2.getTileX(), bestMove2.getTileY(), bestMove2);
            }
            Turn.switchToPlayer();
        }
    }

    private int miniMax(Board board, int depth, boolean isMaximizer)
    {
        if (depth == 0 && !StaticFields.gameOver)
        {
            return scoring.calculateScore(board);
        }

        if (isMaximizer)
        {
            int bestScore = Integer.MIN_VALUE;

            int[][] allAiMoves = PlayerPossibleMoves.getPlayerPossibleMoves(board, Piece.Color.BLACK);
            for (int[] AiMove : allAiMoves)
            {
                //Gathering information about the piece we are going to move
                int[] currentPieceLocation = PlayerPossibleMoves.getPieceLocationFromMove(AiMove, board, Piece.Color.BLACK);
                if (currentPieceLocation != null)
                {
                    Tile chosenTile = board.getTile(currentPieceLocation[0], currentPieceLocation[1]);
                    Piece.PieceID pieceID = chosenTile.getPieceID();
                    Piece piece = chosenTile.getPiece();

                    //Gathering information about the Tile we are going to move to
                    Tile engagedTile = board.getTile(AiMove[0], AiMove[1]);

                    //Start Testing Moves
                    board.setTile(chosenTile.getTileX(), chosenTile.getTileY(), new Tile.EmptyTile(chosenTile.getTileX(), chosenTile.getTileY()));
                    if (!(piece instanceof Pawn))
                    {
                        board.setTile(AiMove[0], AiMove[1], new Tile.OccupiedTile(AiMove[0], AiMove[1], piece, pieceID));
                    }
                    else
                    {
                        board.setTile(AiMove[0], AiMove[1], new Tile.OccupiedTile(AiMove[0], AiMove[1], new Pawn(Piece.Color.BLACK), pieceID));
                    }
                    if (!engagedTile.checkIsEmpty()) { GraveYard.addPiece(Piece.Color.WHITE, engagedTile.getPieceID()); }

                    int score = miniMax(board,depth - 1, false);

                    //Revert the changes
                    board.setTile(currentPieceLocation[0], currentPieceLocation[1], chosenTile);
                    board.setTile(AiMove[0], AiMove[1], engagedTile);
                    if (!engagedTile.checkIsEmpty()) { GraveYard.removeLastPiece(Piece.Color.WHITE); }

                    //Calculate the results
                    if (score > bestScore)
                    {
                        bestScore = score;
                    }
                }
            }
            return bestScore;
        }
        else
        {
            int bestScore = Integer.MAX_VALUE;

            int[][] allPlayerMoves = PlayerPossibleMoves.getPlayerPossibleMoves(board, Piece.Color.WHITE);
            for (int[] playerMove : allPlayerMoves)
            {
                //Gathering information about the piece we are going to move
                int[] currentPieceLocation = PlayerPossibleMoves.getPieceLocationFromMove(playerMove, board, Piece.Color.WHITE);
                if (currentPieceLocation != null)
                {
                    Tile chosenTile = board.getTile(currentPieceLocation[0], currentPieceLocation[1]);
                    Piece.PieceID pieceID = chosenTile.getPieceID();
                    Piece piece = chosenTile.getPiece();

                    //Gathering information about the Tile we are going to move to
                    Tile engagedTile = board.getTile(playerMove[0], playerMove[1]);

                    //Start Testing Moves
                    board.setTile(chosenTile.getTileX(), chosenTile.getTileY(), new Tile.EmptyTile(chosenTile.getTileX(), chosenTile.getTileY()));

                    if (!(piece instanceof Pawn))
                    {
                        board.setTile(playerMove[0], playerMove[1], new Tile.OccupiedTile(playerMove[0], playerMove[1], piece, pieceID));
                    }
                    else
                    {
                        board.setTile(playerMove[0], playerMove[1], new Tile.OccupiedTile(playerMove[0], playerMove[1], new Pawn(Piece.Color.BLACK), pieceID));
                    }
                    if (!engagedTile.checkIsEmpty()) { GraveYard.addPiece(Piece.Color.BLACK, engagedTile.getPieceID()); }

                    int score = miniMax(board,depth - 1, true);

                    //Revert the changes
                    board.setTile(currentPieceLocation[0], currentPieceLocation[1], chosenTile);
                    board.setTile(playerMove[0], playerMove[1], engagedTile);
                    if (!engagedTile.checkIsEmpty()) { GraveYard.removeLastPiece(Piece.Color.BLACK); }

                    //Calculate the results
                    if (score < bestScore)
                    {
                        bestScore = score;
                    }
                }
            }
            return bestScore;
        }
    }



}

