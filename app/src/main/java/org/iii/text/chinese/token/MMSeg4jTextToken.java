package org.iii.text.chinese.token;


import com.chenlb.mmseg4j.ComplexSeg;
import com.chenlb.mmseg4j.Dictionary;

import com.chenlb.mmseg4j.MMSeg;
import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.Word;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by joe on 2018/9/6
 */
public class MMSeg4jTextToken extends TextToken
{
    private Dictionary dic = null;
    
    public MMSeg4jTextToken()
    {
        super();
        dic = Dictionary.getInstance();
        
    }
    
    @Override
    public String tokenWords(String txt, String wordSpilt) throws IOException
    {
        Reader input = new StringReader(txt);
        StringBuilder sb = new StringBuilder();
        Seg seg = getSeg();
        MMSeg mmSeg = new MMSeg(input, seg);
        Word word = null;
        boolean first = true;
        while ((word = mmSeg.next()) != null)
        {
            if (!first)
            {
                sb.append(wordSpilt);
            }
            String tmp = word.getString();
            sb.append(tmp);
            first = false;
        }
        return sb.toString();
    }
    
    private Seg getSeg()
    {
        return new ComplexSeg(dic);
    }

}
