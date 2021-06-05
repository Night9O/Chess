package com.Night9O.Chess.MainHub;

import com.Night9O.Chess.Board.Board;
import com.Night9O.Chess.Board.Tile;
import com.Night9O.Chess.Pieces.*;
import com.Night9O.Chess.Players.AI.AI;
import com.Night9O.Chess.Players.Player;
import com.Night9O.Chess.WinTests.CheckMateTest;

import java.awt.*;
import java.util.Arrays;

public class ChessGameHub
{

    private Board board;
    private Player player;
    private AI ai;
    private CheckMateTest checkMateTest;

    private Dimension dimension;
    private int squareWidth;
    private int squareHeight;


    public ChessGameHub(Dimension dimension)
    {
        this.dimension = dimension;
        squareWidth = (dimension.width - 100) / 8;
        squareHeight = (dimension.height - 100) / 8;

        board = new Board();



        for (int x = 0; x < 8; x++)
        {
            board.setTile(x, 1, new Tile.OccupiedTile(x, 1, new Pawn(Piece.Color.BLACK), Piece.PieceID.PAWN));
            board.setTile(x, 6, new Tile.OccupiedTile(x, 6, new Pawn(Piece.Color.WHITE), Piece.PieceID.PAWN));
        }
        //Knights
        board.setTile(1, 0, new Tile.OccupiedTile(1, 0, new Knight(Piece.Color.BLACK), Piece.PieceID.KNIGHT));
        board.setTile(6, 0, new Tile.OccupiedTile(6, 0, new Knight(Piece.Color.BLACK), Piece.PieceID.KNIGHT));
        board.setTile(1, 7, new Tile.OccupiedTile(1, 7, new Knight(Piece.Color.WHITE), Piece.PieceID.KNIGHT));
        board.setTile(6, 7, new Tile.OccupiedTile(6, 7, new Knight(Piece.Color.WHITE), Piece.PieceID.KNIGHT));

        //Bishops
        board.setTile(2, 7, new Tile.OccupiedTile(2, 7, new Bishop(Piece.Color.WHITE), Piece.PieceID.BISHOP));
        board.setTile(5, 7, new Tile.OccupiedTile(5, 7, new Bishop(Piece.Color.WHITE), Piece.PieceID.BISHOP));
        board.setTile(2, 0, new Tile.OccupiedTile(2, 0, new Bishop(Piece.Color.BLACK), Piece.PieceID.BISHOP));
        board.setTile(5, 0, new Tile.OccupiedTile(5, 0, new Bishop(Piece.Color.BLACK), Piece.PieceID.BISHOP));

        //Rooks
        board.setTile(0,7, new Tile.OccupiedTile(0,7, new Rook(Piece.Color.WHITE), Piece.PieceID.ROOK));
        board.setTile(7,7, new Tile.OccupiedTile(7, 7,new Rook(Piece.Color.WHITE), Piece.PieceID.ROOK));
        board.setTile(0, 0, new Tile.OccupiedTile(0, 0, new Rook(Piece.Color.BLACK), Piece.PieceID.ROOK));
        board.setTile(7, 0, new Tile.OccupiedTile(7, 0, new Rook(Piece.Color.BLACK), Piece.PieceID.ROOK));

        //Queens
        board.setTile(4, 7, new Tile.OccupiedTile(4, 7, new Queen(Piece.Color.WHITE), Piece.PieceID.QUEEN));
        board.setTile(4, 0, new Tile.OccupiedTile(4, 0, new Queen(Piece.Color.BLACK), Piece.PieceID.QUEEN));

        //Kings
        board.setTile(3,7, new Tile.OccupiedTile(3, 7, new King(Piece.Color.WHITE), Piece.PieceID.KING));
        board.setTile(3,0, new Tile.OccupiedTile(3, 0, new King(Piece.Color.BLACK), Piece.PieceID.KING));

        player = new Player(board, squareWidth, squareHeight);
        checkMateTest = new CheckMateTest(board);
        ai = new AI(board);
    }


    public void paint(Graphics g)
    {
        g.setColor(Color.BLACK);

        //Paint Board
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                g.drawRect(x * squareWidth, y * squareHeight, squareWidth, squareHeight);
            }
        }

        //HighLight  where the player has pressed.
        if (board.getTile(player.getPressedTileXCoordinates(), player.getPressedTileYCoordinates()).checkIsEmpty())
        {
            g.fillRect(player.getPressedTileXCoordinates() * squareWidth, player.getPressedTileYCoordinates() * squareHeight, squareWidth, squareHeight);
        }
        // HighLight where the piece can go to
        else
            {
                Tile pressedTile = board.getTile(player.getPressedTileXCoordinates(), player.getPressedTileYCoordinates());
                int[][] possibleMoves = pressedTile.getPiece().giveLegalMovements(player.getPressedTileXCoordinates(), player.getPressedTileYCoordinates(), board);
                for (int x = 0; x < possibleMoves.length; x++)
                {
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("", Font.ITALIC, 25));
                    g.drawRect(possibleMoves[x][0] * squareWidth,possibleMoves[x][1] * squareHeight, squareWidth, squareHeight );
                    g.setColor(Color.YELLOW);
                    g.fillRect(possibleMoves[x][0] * squareWidth,possibleMoves[x][1] * squareHeight, squareWidth, squareHeight );

                    //HighLight the king if he is checked.
                    if (checkMateTest.isWhiteCheck())
                    {
                        g.setColor(Color.RED);
                        g.fillRect(checkMateTest.getWhiteKingLocation()[0] * squareWidth, checkMateTest.getWhiteKingLocation()[1] * squareHeight, squareWidth, squareHeight);
                    }
                    if (checkMateTest.isBlackCheck())
                    {
                        g.setColor(Color.RED);
                        g.fillRect(checkMateTest.getBlackKingLocation()[0] * squareWidth, checkMateTest.getBlackKingLocation()[1] * squareHeight, squareWidth, squareHeight);
                    }

                    //HighLight the Tiles where the King can be checkMated
                    for(int i = 0; i < checkMateTest.getCheckMateX().size(); i++)
                    {
                        g.setColor(Color.RED);
                        g.fillRect(checkMateTest.getCheckMateX().get(i) * squareWidth, checkMateTest.getCheckMateY().get(i) * squareHeight, squareWidth, squareHeight);
                    }
                }
            }


        //Paint Pieces
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                if (!board.getTile(x, y).checkIsEmpty())
                {
                    if (board.getTile(x, y).getPiece().getColor() == Piece.Color.WHITE) {g.setColor(Color.BLUE); }
                    else g.setColor(Color.RED);
                }
                g.setFont(new Font("", Font.ITALIC, 25));
                if (board.getTile(x,y).getPieceID() == Piece.PieceID.PAWN) { g.drawString("P", x * squareWidth, (y * squareHeight) + squareHeight); }
                if (board.getTile(x,y).getPieceID() == Piece.PieceID.KNIGHT) { g.drawString("Kn", x * squareWidth, (y * squareHeight) + squareHeight); }
                if (board.getTile(x,y).getPieceID() == Piece.PieceID.BISHOP) { g.drawString("Bi", x * squareWidth, (y * squareHeight) + squareHeight); }
                if (board.getTile(x,y).getPieceID() == Piece.PieceID.ROOK) { g.drawString("Ro", x * squareWidth, (y * squareHeight) + squareHeight); }
                if (board.getTile(x,y).getPieceID() == Piece.PieceID.QUEEN) { g.drawString("Qu", x * squareWidth, (y * squareHeight) + squareHeight); }
                if (board.getTile(x,y).getPieceID() == Piece.PieceID.KING) { g.drawString("Ki", x * squareWidth, (y * squareHeight) + squareHeight); }
            }
        }

    }

    public void update()
    {
        checkMateTest.update();
        checkMateTest.whiteCheck();
        checkMateTest.blackCheck();
        checkMateTest.whiteCheckMate();
        checkMateTest.blackCheckMate();
        ai.AIMove(board, 2);

        boolean whiteKing = false;
        boolean blackKing = false;
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                if (!board.getTile(x,y).checkIsEmpty())
                {
                    if (board.getTile(x,y).getPieceID() == Piece.PieceID.KING
                            && board.getTile(x,y).getPiece().getColor() == Piece.Color.WHITE)
                    {
                        whiteKing = true;
                    }
                }
                if (!board.getTile(x,y).checkIsEmpty())
                {
                    if (board.getTile(x,y).getPieceID() == Piece.PieceID.KING
                            && board.getTile(x,y).getPiece().getColor() == Piece.Color.BLACK)
                    {
                        blackKing = true;
                    }
                }
            }
        }
        //if (!blackKing || !whiteKing) { StaticFields.gameOver = true; }
        //if (checkMateTest.whiteCheckMate()) { StaticFields.gameOver = true;}
        //if (checkMateTest.blackCheckMate()) { StaticFields.gameOver = true;}
        //System.out.println(StaticFields.gameOver);
    }


    public Player getPlayer() { return player; }

}
