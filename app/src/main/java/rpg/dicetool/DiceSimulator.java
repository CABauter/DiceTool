package rpg.dicetool;

import java.util.Random;
import java.util.Arrays;

/**
 * Created by Julia on 29.12.2016.
 */

public class DiceSimulator
{
    public static int[] simulate( int[] diceTypes, int drop )
    {
        int diceCount = diceTypes.length;
        int maximumDiceSum = 0;

        for( int i = drop; i < diceCount; i++ )
        {
            maximumDiceSum += diceTypes[i];
        }
        int[] count = new int[ maximumDiceSum ];

        int iterations = 10000;

        for( int i = 0; i < iterations; i++ )
        {
            int[] numbersOnDice = new int[ diceCount ];

            for( int j = 0; j < diceCount; j++ )
            {
                Random randomNumber = new Random();
                int numberOnDie = randomNumber.nextInt( diceTypes[i] );
                numbersOnDice[j] = numberOnDie;
            }

            Arrays.sort( numbersOnDice );
            int sum = 0;

            for( k = drop; k < diceCount; k++ )
            {
                sum += numbersOnDice[k];
            }
        }

        return count;
    }
}
