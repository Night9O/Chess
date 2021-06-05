package com.Night9O.Chess.Misc;

public class NextInRange
{
    public static int NextIntInRange(int min, int max)  //used in NameLengthProbability Method
//simple algorithm to generate a random integer between a min and max variables (min <= x <= max)
    {

        if (min > max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

}
