package org.iii.text.chinese.token;


import android.util.Log;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by joe on 2018/9/4
 */
public class TextToken
{
    
    private final static String DEFAULT_WORD_SPILT_SYMBOL = " ";
    private final static String DEFAULT_WORD_SPILT_SYMBOLS = "[。、，？！?.!]";
    
    
    public TextToken()
    {
    
    }
    
    public ArrayList<String> tokenWords(String text) throws IOException
    {
        Log.d("TEXT", text);
    
        ArrayList<String> tokenWordsArrayList = new ArrayList<>();
        
        for (String txt : text.split(DEFAULT_WORD_SPILT_SYMBOLS))
        {
            tokenWordsArrayList.add(tokenWords(txt, DEFAULT_WORD_SPILT_SYMBOL));
        }
        
        for (String tmp : tokenWordsArrayList)
        {
            Log.d("TOKEN_TEXT", tmp);
        }
    
        
        return tokenWordsArrayList;
        
    }
 
    
    public String tokenWords(String txt, String wordSpilt) throws IOException
    {
        return txt;
    }
    
}
