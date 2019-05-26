package org.iii.text.chinese.manager.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;
import org.iii.text.chinese.manager.Parameters;
import org.iii.text.chinese.manager.TextMessageEvent;
import org.iii.text.chinese.parser.StanfordChineseParser;
import org.iii.text.chinese.token.JiebaTextToken;
import org.iii.text.chinese.token.MMSeg4jTextToken;
import org.iii.text.chinese.token.TextToken;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by joe on 2018/9/11
 */
public class TextManageIntentService extends IntentService
{
    private static StanfordChineseParser mStanfordChineseParser = null;
    private static TextToken mTextToken = null;
    
    public TextManageIntentService()
    {
        super("TextManageIntentService");
    }
    
    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        if (intent != null)
        {
            
            Bundle data = intent.getExtras();
            if (data != null)
            {
                TextMessageEvent textMessageEvent = new TextMessageEvent();
                switch (data.getInt("STATE"))
                {
                    case Parameters.INIT:
                        if (null == mStanfordChineseParser)
                        {
                            mStanfordChineseParser = new StanfordChineseParser(this);
                        }
                        
                        if (null == mTextToken)
                        {
                            mTextToken = new MMSeg4jTextToken();
                        }
    
                        
                        textMessageEvent.commandID = Parameters.INIT;
                        EventBus.getDefault().post(textMessageEvent);
                        
                        break;
                    case Parameters.TEXT_RESULT:
                        String originString = data.getString("ORIGIN_STRING", "");
                        ArrayList<String> tokenTexts = null;
                        try
                        {
                            tokenTexts = mTextToken.tokenWords(originString);
                            
                            
                            int textID = 0;
                            
                            for (final String tokenText : tokenTexts)
                            {
                                
                                final String posText = mStanfordChineseParser.posFor(tokenText);
                                // 收到來自於Activity的消息後立即響應回覆一條消息。
                                
                                textMessageEvent.commandID = Parameters.TEXT_RESULT;
                                textMessageEvent.resultText = posText;
                                textMessageEvent.textID = textID++;
                                textMessageEvent.tokenText = tokenText;
                                
                                EventBus.getDefault().post(textMessageEvent);
                                
                            }
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        
                        break;
                    
                    
                }
            }
        }
        
        
    }
}
