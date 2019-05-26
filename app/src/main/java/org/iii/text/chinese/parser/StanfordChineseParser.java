package org.iii.text.chinese.parser;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.iii.htsengine.R;
import org.iii.text.chinese.tool.TextTool;

import java.io.ObjectInputStream;
import java.util.Iterator;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;

/**
 * Created by joe on 2018/9/4
 */
public class StanfordChineseParser
{
    private Context mContext = null;
    LexicalizedParser mLexicalizedParser = null;
    TreePrint myTreePrint = new TreePrint("oneline");
    public static final String QUIT_SERVER_COMMAND = "COMMAND=QUIT_SERVER";
    public static final String POS_COMMAND = "COMMAND=POS";
    public static final String PARSE_COMMAND = "COMMAND=PARSE";
    
    public static final String CHINESE_FACTORED_NAME = "chineseFactored.ser";
    public static final String CHINESE_FACTORED_PATH = "chinese/";
    
    public StanfordChineseParser(Context context)
    {
        mContext = context;
        try
        {
            init();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void init() throws Exception
    {
        Log.w("STANDFORD", "INIT_START");
        /* TextTool.copyAssets(mContext, CHINESE_FACTORED_PATH, CHINESE_FACTORED_NAME);
        String dataPath = mContext.getFilesDir().getAbsolutePath() + "/" + CHINESE_FACTORED_PATH +
                CHINESE_FACTORED_NAME;
        Log.d("StanfordChineseParser", "chineseFactored_ser.gz Path: " + dataPath);
        
        mLexicalizedParser = new LexicalizedParser(dataPath);
    
  
        mLexicalizedParser = new LexicalizedParser(new ObjectInputStream(mContext.getAssets().open
        (CHINESE_FACTORED_PATH+CHINESE_FACTORED_NAME)));
    */
        
        ObjectInputStream tmp = new ObjectInputStream(mContext.getResources().openRawResource(R.raw
                .chinese_factored));
        Log.w("STANDFORD", "ObjectInputStream OK");
        
        mLexicalizedParser = new LexicalizedParser(tmp);
        Log.w("STANDFORD", "INIT_END");
    }
    
    public String posFor(String sentence)
    {
        return posFor(parseTreeFor(sentence));
    }
    
    private static String posFor(Tree tree)
    {
        StringBuffer sb = new StringBuffer();
        
        Iterator<Tree> it = tree.iterator();
        while (it.hasNext())
        {
            Tree node = (Tree) it.next();
            if (node.isPreTerminal())
            {
                Tree[] words = node.children();
                for (Tree word : words)
                {
                    sb.append(word.label().value());
                }
                sb.append("/");
                
                sb.append(node.label().value());
                if (it.hasNext())
                {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }
    
    private Tree parseTreeFor(String sentence)
    {
        return mLexicalizedParser.apply(sentence);
    }
    
    
}
