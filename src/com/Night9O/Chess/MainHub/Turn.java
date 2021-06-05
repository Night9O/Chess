package com.Night9O.Chess.MainHub;

public class Turn
{
    /**
     * 0: playerTurn
     * 1: AiTurn
     */
    private static int turn = 0;

    private Turn(){}

    public static int switchToAi()
    {
        if (turn == 0) {return turn += 1; }
        else return turn;
    }

    public static int switchToPlayer()
    {
        if (turn == 1) { return turn -= 1; }
        else return turn;
    }

    public static int getTurn()
    {
        return turn;
    }
}
