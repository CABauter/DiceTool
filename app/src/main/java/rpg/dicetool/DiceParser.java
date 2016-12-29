package rpg.dicetool;

import java.util.HashMap;

/**
 * Created by Corbi on 29.12.2016.
 */

public class DiceParser
{
    public static HashMap<String, Object> parseString(String expression)
    {
        //preparing variables
        HashMap<String, Object> parsedMap = new HashMap<String, Object>();

        String dicepart = "";
        String droppart = "";
        String shiftpart = "";

        //splitting String in substrings
        if(expression.contains("D") && expression.contains("+")) //Drop and shift
        {
            dicepart = expression.substring(0, expression.indexOf("D"));
            droppart = expression.substring(expression.indexOf("D")+1, expression.indexOf("+"));
            shiftpart = expression.substring(expression.indexOf("+")+1, expression.length());
        }
        else if(expression.contains("D"))
        {
            dicepart = expression.substring(0, expression.indexOf("D"));
            droppart = expression.substring(expression.indexOf("D")+1, expression.length());
        }
        else if(expression.contains("+"))
        {
            dicepart = expression.substring(0, expression.indexOf("+"));
            shiftpart = expression.substring(expression.indexOf("+")+1, expression.length());
        }
        else
        {
            dicepart = expression;
        }

        //set dicetypes in array
        String[] dices;

        if(dicepart.contains("(") && dicepart.contains(")"))
        {
            dicepart = dicepart.replace("(", "");
            dicepart = dicepart.replace(")", "");
            dices = dicepart.split(";");
        }
        else
        {
            dices = new String[1];
            dices[0] = dicepart;
        }

        int numdices = 0;

        for( String diceset : dices)
        {
            String num = diceset.substring(0, dicepart.indexOf("d"));

            numdices += Integer.parseInt(num);
        }

        int[] dicetypes = new int[numdices];
        int pos = 0;

        for( String diceset : dices)
        {
            int num = Integer.parseInt(diceset.substring(0, diceset.indexOf("d")));
            int sides = Integer.parseInt(diceset.substring(diceset.indexOf("d")+1, diceset.length()));

            for( int i = 0; i < num; i++)
            {
                dicetypes[pos]  = sides;
                pos++;
            }
        }

        parsedMap.put("dicetypes", dicetypes);

        if(droppart != "")
        {
            parsedMap.put("drop", droppart.replace("D",""));
        }

        if(shiftpart != "")
        {
            parsedMap.put("shift", shiftpart.replace("+",""));
        }

        return parsedMap;
    }

    public static String parseToString(String[] dices, int drop, int shift)
    {
        String parsedString = "";


        return parsedString;
    }
}
