package com.Night9O.Chess.WinTests;

import com.Night9O.Chess.Board.Board;
import com.Night9O.Chess.Board.Tile;
import com.Night9O.Chess.GUI.ChessGUI;
import com.Night9O.Chess.Pieces.King;
import com.Night9O.Chess.Pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


public class CheckMateTest
{
    private Board board;

    boolean whiteCheck = false;
    boolean blackCheck = false;

    private ArrayList<Integer> whiteXMoves;
    private ArrayList<Integer> whiteYMoves;
    private ArrayList<Integer> blackXMoves;
    private ArrayList<Integer> blackYMoves;

    private int[] whiteKingLocation;
    private int[] blackKingLocation;

    private ArrayList<Integer> checkMateX;
    private ArrayList<Integer> checkMateY;

    public CheckMateTest(Board board)
    {
        this.board = board;

        whiteXMoves = new ArrayList<>();
        whiteYMoves = new ArrayList<>();
        blackXMoves = new ArrayList<>();
        blackYMoves = new ArrayList<>();

        checkMateX = new ArrayList<>();
        checkMateY = new ArrayList<>();

        whiteKingLocation = new int[2];
        blackKingLocation = new int[2];

        initialize();
    }

    private void initialize()
    {
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                if (!board.getTile(x,y).checkIsEmpty())
                    {
                        if (board.getTile(x,y).getPiece().getColor() == Piece.Color.WHITE)
                        {
                            if (board.getTile(x,y).getPieceID() == Piece.PieceID.KING)
                            {
                                whiteKingLocation[0] = x;
                                whiteKingLocation[1] = y;
                            }
                            int[][] pieceMoves = board.getTile(x,y).getPiece().giveLegalMovements(x, y, board);
                            //int z = 0; z < pieceMoves.length; z++
                            for (int[] pieceMove : pieceMoves)
                            {
                                whiteXMoves.add(pieceMove[0]);
                                whiteYMoves.add(pieceMove[1]);
                            }
                        }
                        if (board.getTile(x, y).getPiece().getColor() == Piece.Color.BLACK)
                        {
                            if (board.getTile(x,y).getPieceID() == Piece.PieceID.KING)
                            {
                                blackKingLocation[0] = x;
                                blackKingLocation[1] = y;
                            }
                            int[][] pieceMoves = board.getTile(x,y).getPiece().giveLegalMovements(x, y, board);
                            for (int[] pieceMove : pieceMoves) //int z = 0; z < pieceMoves.length; z++
                            {
                                blackXMoves.add(pieceMove[0]);
                                blackYMoves.add(pieceMove[1]);
                            }
                        }
                    }
            }
        }
    }

    /**
     * update the pieces locations and possible movements every turn.
     */
    public void update()
    {
        whiteXMoves.clear();
        whiteYMoves.clear();
        blackXMoves.clear();
        blackYMoves.clear();
        initialize();
    }

    private void updateMoves()
    {
        whiteXMoves.clear();
        whiteYMoves.clear();
        blackXMoves.clear();
        blackYMoves.clear();

        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                if (!board.getTile(x,y).checkIsEmpty())
                {
                    if (board.getTile(x,y).getPiece().getColor() == Piece.Color.WHITE)
                    {
                        int[][] pieceMoves = board.getTile(x,y).getPiece().giveLegalMovements(x, y, board);
                        //int z = 0; z < pieceMoves.length; z++
                        for (int[] pieceMove : pieceMoves)
                        {
                            whiteXMoves.add(pieceMove[0]);
                            whiteYMoves.add(pieceMove[1]);
                        }
                    }
                    if (board.getTile(x, y).getPiece().getColor() == Piece.Color.BLACK)
                    {
                        int[][] pieceMoves = board.getTile(x,y).getPiece().giveLegalMovements(x, y, board);
                        for (int[] pieceMove : pieceMoves) //int z = 0; z < pieceMoves.length; z++
                        {
                            blackXMoves.add(pieceMove[0]);
                            blackYMoves.add(pieceMove[1]);
                        }
                    }
                }
            }
        }
    }

    public boolean whiteCheck()
    {
        for (int x = 0; x < blackXMoves.size(); x++)
        {
            if (whiteKingLocation[0] == blackXMoves.get(x) && whiteKingLocation[1] == blackYMoves.get(x)) {return whiteCheck = true; }
        }
        return whiteCheck = false;
    }


    public boolean blackCheck()
    {
        for (int x = 0; x < whiteXMoves.size(); x++)
        {
            if (blackKingLocation[0] == whiteXMoves.get(x) && blackKingLocation[1] == whiteYMoves.get(x)) {return blackCheck = true; }
        }
        return blackCheck = false;
    }

    public boolean whiteCheckMate()
    {
        boolean canEvade = canEvade(whiteKingLocation, Piece.Color.WHITE, whiteCheck, blackXMoves, blackYMoves);
        boolean canBlock = canBlock(whiteKingLocation, Piece.Color.WHITE, whiteXMoves, whiteYMoves);
        boolean canEat = canEat(whiteKingLocation, Piece.Color.WHITE, whiteXMoves, whiteYMoves);

        return !canEvade && !canBlock && !canEat;
    }

    public boolean blackCheckMate()
    {
        boolean canEvade = canEvade(blackKingLocation, Piece.Color.BLACK, blackCheck, whiteXMoves, whiteYMoves);
        boolean canBlock = canBlock(blackKingLocation, Piece.Color.BLACK, blackXMoves, blackYMoves);
        boolean canEat = canEat(blackKingLocation, Piece.Color.BLACK, blackXMoves, blackYMoves);

        return !canEvade && !canBlock && !canEat;
    }

    private boolean canEvade(int[] kingLocation, Piece.Color kingColor, boolean isCheck, ArrayList<Integer> enemyXMoves, ArrayList<Integer> enemyYMoves)
    {
        if (isCheck)
        {
            checkMateX.clear();
            checkMateY.clear();

            ArrayList<Boolean> results = new ArrayList<>();

            int[] potentialKingMove = new int[2];
            potentialKingMove[0] = kingLocation[0];
            potentialKingMove[1] = kingLocation[1];


            int[][] kingMoves = board.getTile(kingLocation[0], kingLocation[1]).getPiece().giveLegalMovements(kingLocation[0], kingLocation[1], board);

            //(int i = 0; i < kingMoves.length; i++)
            for (int[] kingMove : kingMoves)
            {
                Tile potentialMoveBackup = board.getTile(kingMove[0], kingMove[1]);
                Tile kingTileBackup = board.getTile(kingLocation[0], kingLocation[1]);

                updateMoves();

                board.setTile(kingLocation[0], kingLocation[1], new Tile.EmptyTile(kingLocation[0], kingLocation[1]));
                potentialKingMove[0] = kingMove[0];
                potentialKingMove[1] = kingMove[1];
                board.setTile(potentialKingMove[0], potentialKingMove[1], new Tile.OccupiedTile(potentialKingMove[0], potentialKingMove[1],new King(kingColor), Piece.PieceID.KING));

                updateMoves();

                boolean canFlee = true;
                for (int x = 0; x < enemyXMoves.size(); x++) {
                    if (enemyXMoves.get(x) == potentialKingMove[0] && enemyYMoves.get(x) == potentialKingMove[1]) {
                        canFlee = false;
                        results.add(false);
                        //sends the coordinates of the tiles where the king can't escape
                        checkMateX.add(potentialKingMove[0]);
                        checkMateY.add(potentialKingMove[1]);
                        break;
                    }
                }
                if (canFlee) {
                    results.add(true);
                }

                //Restore the previous changes.
                board.setTile(kingLocation[0], kingLocation[1], kingTileBackup);
                board.setTile(kingMove[0], kingMove[1], potentialMoveBackup);

                updateMoves();

            }
            return results.contains(true);
        }
        return true;
    }

    /**
     * check if another piece can block the threatening Piece path to save the king.
     * @return true if another piece can Block else returns false.
     */
    private boolean canBlock(int[] kingLocation, Piece.Color kingColor, ArrayList<Integer> allyXMoves, ArrayList<Integer> allyYMoves)
    {
        ArrayList<Boolean> results = new ArrayList<>();

        int[][] threateningPieces = findThreateningPiecesLocations(kingLocation, kingColor);
        for (int[] threateningPiece: threateningPieces)
        {
            int[][] threateningPieceMoves = board.getTile(threateningPiece[0], threateningPiece[1]).getPiece().giveLegalMovements(threateningPiece[0], threateningPiece[1], board);
            for (int[] threateningPieceMove : threateningPieceMoves)
            {
                for (int j = 0; j < allyXMoves.size(); j++)
                {
                    if (allyXMoves.get(j) == threateningPieceMove[0] && allyYMoves.get(j) == threateningPieceMove[0]) {
                        results.add(true);
                    } else results.add(false);
                }
            }
        }
        return !results.contains(false);
    }

    /**
     * check if the threatening piece can be eaten by another piece or not.
     */
    private boolean canEat(int[] kingLocation, Piece.Color kingColor, ArrayList<Integer> allyXMoves, ArrayList<Integer> allyYMoves)
    {
        int[][] threateningPieces = findThreateningPiecesLocations(kingLocation, kingColor);
        int threatLevel = threateningPieces.length;
        boolean canEat = false;
        if (threatLevel > 1) { return false; } //you can't eat more than 1 piece in one turn...
        if (threatLevel == 0) { return true; } // there is no threat in the first place...
        else
            {
                for (int x = 0; x < allyXMoves.size(); x++)
                {
                    if (threateningPieces[0][0] == allyXMoves.get(x) && threateningPieces[0][1] == allyYMoves.get(x))
                    {
                        canEat = true;
                        break;
                    }
                }
            }
        return canEat;
    }

    private int[][] findThreateningPiecesLocations(int[] kingLocation, Piece.Color kingColor)
    {
        ArrayList<Integer> threateningPieceX = new ArrayList<>();
        ArrayList<Integer> threateningPieceY = new ArrayList<>();

        for (int x = 0; x <  8; x++)
        {
            for (int y = 0; y <  8; y++)
            {
                if (!board.getTile(x,y).checkIsEmpty() && board.getTile(x,y).getPiece().getColor() != kingColor)
                {
                    int[][] enemyPieceMoves = board.getTile(x,y).getPiece().giveLegalMovements(x,y,board);
                    for (int[] enemyPieceMove : enemyPieceMoves)
                    {
                        if (kingLocation[0] == enemyPieceMove[0] && kingLocation[1] == enemyPieceMove[1])
                        {
                            threateningPieceX.add(x);
                            threateningPieceY.add(y);
                        }
                    }
                }
            }
        }
        int[][] threateningPieces = new int[threateningPieceX.size()][2];
        for (int x = 0; x < threateningPieceX.size(); x++)
        {
            threateningPieces[x][0] = threateningPieceX.get(x);
            threateningPieces[x][1] = threateningPieceY.get(x);
        }

        return threateningPieces;
    }

    public ArrayList<Integer> getCheckMateX() {
        return checkMateX;
    }

    public ArrayList<Integer> getCheckMateY() {
        return checkMateY;
    }

    public boolean isBlackCheck() {
        return blackCheck;
    }

    public boolean isWhiteCheck() {
        return whiteCheck;
    }

    public int[] getBlackKingLocation() {
        return blackKingLocation;
    }

    public int[] getWhiteKingLocation() {
        return whiteKingLocation;
    }
}
