package rpg.dicetool;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Corbi on 29.12.2016.
 */
public class ParserTest
{
    public static void main(String[] args)
    {
        String expr = "(3d6;2d8)D1";

        System.out.println("Expression: " + expr);

        HashMap<String, Object> map  = DiceParser.parseString(expr);

        int[] dices = (int[]) map.get("dicetypes");

        System.out.print("Dices: [");
        for( int dice : dices )
        {
            System.out.print(dice+"; ");
        }
        System.out.println("]");

        if(map.containsKey("drop"))
            System.out.println("Drop: " + map.get("drop"));

        if(map.containsKey("shift"))
            System.out.println("Shift: " + map.get("shift"));

    }
}
