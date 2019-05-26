package org.iii.text.chinese.token;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.io.IOException;
import java.util.List;

/**
 * Created by joe on 2018/9/6
 */
public class JiebaTextToken extends TextToken
{
    JiebaSegmenter mJiebaSegmenter = null;
    
    public JiebaTextToken()
    {
        super();
        mJiebaSegmenter = new JiebaSegmenter();
    }
    
    
    @Override
    public String tokenWords(String txt, String wordSpilt) throws IOException
    {
        StringBuilder tokenText = new StringBuilder();
        List<SegToken> segmentedList = mJiebaSegmenter.process(txt, JiebaSegmenter.SegMode.INDEX);
        for (int i = 0; i < segmentedList.size(); i++)
        {
            tokenText.append(segmentedList.get(i).word);
            if (i != segmentedList.size() - 1)
            {
                tokenText.append(wordSpilt);
            }
        }
        return tokenText.toString();
    }
    
}
