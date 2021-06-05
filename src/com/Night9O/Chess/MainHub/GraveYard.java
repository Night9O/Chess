package com.Night9O.Chess.MainHub;

import com.Night9O.Chess.Pieces.Piece;

import java.util.ArrayList;

//Stores all the lost pieces
public class GraveYard
{
    private static ArrayList<Piece.PieceID> whiteGraveYard = new ArrayList<>();
    private static ArrayList<Piece.PieceID> blackGraveYard = new ArrayList<>();

    public static void addPiece(Piece.Color color, Piece.PieceID pieceID)
    {
        if (color == Piece.Color.WHITE) { whiteGraveYard.add(pieceID);}
        else blackGraveYard.add(pieceID);
    }

    public static void removeLastPiece(Piece.Color color)
    {
        if (color == Piece.Color.WHITE) { whiteGraveYard.remove(whiteGraveYard.size() - 1);}
        else blackGraveYard.remove(blackGraveYard.size() - 1);
    }

    public static ArrayList<Piece.PieceID> getWhiteGraveYard()
    {
        return whiteGraveYard;
    }

    public static ArrayList<Piece.PieceID> getBlackGraveYard()
    {
        return blackGraveYard;
    }
}
