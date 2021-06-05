package com.Night9O.Chess.Players;
// TODO: remove that line from line 67 later when you finish testing;
import com.Night9O.Chess.Board.Board;
import com.Night9O.Chess.Board.Tile;
import com.Night9O.Chess.MainHub.GraveYard;
import com.Night9O.Chess.MainHub.Turn;
import com.Night9O.Chess.Pieces.Piece;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Player implements MouseListener
{

    private Board board;

    private boolean isChoosingMove = false;

    private int squareWidth;
    private int squareHeight;

    private int pressedTileXCoordinates = 3;
    private int pressedTileYCoordinates = 3;

    public Player(Board board, int squareWidth, int squareHeight)
    {
        this.board = board;
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        int lastPressedTileX = pressedTileXCoordinates;
        int lastPressedTileY = pressedTileYCoordinates;

        int mouseX = e.getX();
        int mouseY = e.getY();

        //Prevent the user from clicking outside the grid thus causing outOfBounds Exception
        if (mouseX < (squareWidth * 8) && mouseY < (squareHeight * 8))
        {
            pressedTileXCoordinates= (mouseX / squareWidth);
            pressedTileYCoordinates = (mouseY /squareHeight);
        }

        Tile pressedTile = board.getTile(pressedTileXCoordinates, pressedTileYCoordinates);
        Tile oldTile = board.getTile(lastPressedTileX, lastPressedTileY);

        //The player is only allowed to play with a white piece, if he clicks a black one, it will not move
        if (Turn.getTurn() == 0  && (oldTile.checkIsEmpty() || oldTile.getPiece().getColor() == Piece.Color.WHITE))
        // && (oldTile.checkIsEmpty() || oldTile.getPiece().getColor() == Piece.Color.WHITE)
        {
            int[][] possibleMoves = new int[0][];
            if (!oldTile.checkIsEmpty()) {possibleMoves = oldTile.getPiece().giveLegalMovements(lastPressedTileX, lastPressedTileY, board); }
            if (isChoosingMove && possibleMoves != null)
            {
                for (int[] possibleMove : possibleMoves) // for (int x = 0; x < possibleMoves.length; x++)
                {
                    if (pressedTileXCoordinates == possibleMove[0] && pressedTileYCoordinates == possibleMove[1]) {
                        board.setTile(lastPressedTileX, lastPressedTileY,
                                new Tile.EmptyTile(lastPressedTileX, lastPressedTileY));

                        if (!board.getTile(pressedTileXCoordinates, pressedTileYCoordinates).checkIsEmpty())
                        {
                            GraveYard.addPiece(Piece.Color.BLACK, board.getTile(pressedTileXCoordinates, pressedTileYCoordinates).getPieceID());
                        }
                        board.setTile(pressedTileXCoordinates, pressedTileYCoordinates,
                                new Tile.OccupiedTile(pressedTileXCoordinates, pressedTileYCoordinates, oldTile.getPiece(), oldTile.getPieceID()));
                        Turn.switchToAi();
                    }
                    isChoosingMove = false;
                }
            }
            if (!isChoosingMove) { isChoosingMove = !board.getTile(pressedTileXCoordinates, pressedTileYCoordinates).checkIsEmpty(); }
        }
        //Turn.switchToPlayer();
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    public MouseListener getPlayerMouseListener() {return this;}

    public int getPressedTileXCoordinates() { return pressedTileXCoordinates; }

    public int getPressedTileYCoordinates() { return pressedTileYCoordinates; }
}
