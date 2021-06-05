package com.Night9O.Chess.Board;

import com.Night9O.Chess.Pieces.Piece;

public abstract class Tile
{
    protected boolean isEmpty;
    protected int tileX;
    protected int tileY;

    public abstract boolean checkIsEmpty();
    public abstract Piece getPiece();
    public abstract Piece.PieceID getPieceID();

    public abstract int getTileX();

    public abstract int getTileY();

    public static class EmptyTile extends Tile
    {
        public EmptyTile(int x, int y)
        {
            this.tileX = x;
            this.tileY = y;
        }

        @Override
        public boolean checkIsEmpty() { return isEmpty = true; }

        @Override
        public Piece getPiece()
        {
            return null;
        }

        @Override
        public Piece.PieceID getPieceID() { return null; }

        @Override
        public int getTileX() {
            return tileX;
        }

        @Override
        public int getTileY() {
            return tileY;
        }

    }

    public static class OccupiedTile extends Tile
    {
        private Piece piece;
        private Piece.PieceID pieceID;

        public OccupiedTile(int x, int y, Piece piece, Piece.PieceID pieceID)
        {
            this.tileX = x;
            this.tileY = y;
            this.piece = piece;
            this.pieceID = pieceID;
        }

        @Override
        public boolean checkIsEmpty() { return isEmpty = false; }

        @Override
        public Piece getPiece()
        {
            return piece;
        }

        @Override
        public Piece.PieceID getPieceID() { return pieceID; }

        @Override
        public int getTileX() {
            return tileX;
        }

        @Override
        public int getTileY()
        {
            return tileY;
        }

        public Piece putPiece(Piece piece)
        {
            return this.piece = piece;
        }

        public Piece removePiece()
        {
            return this.piece = null;
        }

        public Piece.PieceID changePieceID(Piece.PieceID pieceID) { return this.pieceID = pieceID; }
    }
}
