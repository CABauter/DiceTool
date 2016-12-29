package rpg.dicetool;

import org.junit.Test;

import static org.junit.Assert.*;
import static rpg.dicetool.DiceSimulator.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public static void main(String[] args) {
        int[] dice = new int[6];
        dice[0] = dice[1] = dice[2] = dice[3] = dice[4] = dice[5] = 6;

        int[] results;
        results = simulate( dice, 3, 1000000 );
        int sumResults = 0;
        double percentage = 0;

        for( int i = 0; i < results.length; i++)
        {
            System.out.println( i+" mit Wahrscheinlichkeit "+(((double) results[i])/10000) );
            sumResults += results[i];
            percentage += (((double) results[i])/10000);
        }

        System.out.println( "Die Gesamtzahl der Würfe ist: " + sumResults + "und Prozent: " + percentage );
    }
}