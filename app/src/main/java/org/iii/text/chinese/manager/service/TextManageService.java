package org.iii.text.chinese.manager.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Handler;
import android.util.Log;

import org.iii.text.chinese.manager.Parameters;
import org.iii.text.chinese.parser.StanfordChineseParser;
import org.iii.text.chinese.token.MMSeg4jTextToken;
import org.iii.text.chinese.token.TextToken;

import java.util.ArrayList;


/**
 * Created by joe on 2018/9/10
 */
public class TextManageService extends Service
{
    
    private Messenger messenger;
    private StanfordChineseParser mStanfordChineseParser = null;
    private TextToken mTextToken = null;
    private Messenger reply = null;
    
    @Override
    public void onCreate()
    {
        Log.d(this.getClass().getName(), "onCreate");
        
        @SuppressLint("HandlerLeak") Handler handler = new Handler()
        {
            public void handleMessage(final Message msg)
            {
                Log.d(this.getClass().getName(), msg.what + ":" + msg.obj);
                switch (msg.what)
                {
                    case 0:
                        reply = msg.replyTo;
                        
                        mTextToken = new MMSeg4jTextToken();
                        mStanfordChineseParser = new StanfordChineseParser(TextManageService.this);
                        if (null != reply)
                        {
                            sendMessageToActivity(reply, Parameters.INIT, null, 0);
                        }
                        else
                        {
                            Log.e("Service ERROR Message", "msg.replyTo is null");
                        }
                        
                        
                        break;
                    case 1:
                        reply = msg.replyTo;
                        
                        String originText = (String) msg.obj;
                        try
                        
                        {
                            ArrayList<String> tokenTexts = mTextToken.tokenWords(originText);
                            
                            int textID = 0;
                            
                            for (final String tokenText : tokenTexts)
                            {
                                
                                final String posText = mStanfordChineseParser.posFor(tokenText);
                                // 收到來自於Activity的消息後立即響應回覆一條消息。
                                
                                String[] resultText = new String[2];
                                resultText[0] = tokenText;
                                resultText[1] = posText;
                                
                                sendMessageToActivity(reply, Parameters.TEXT_RESULT, resultText, textID++);
                                
                            }
                        }
                        catch (Exception e)
                        
                        {
                        
                        }
                        
                        break;
                    
                }
                
                
            }
        };
        
        messenger = new Messenger(handler);
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }
    
    private void sendMessageToActivity(Messenger mMessenger, int what, String[] resultText, int textID)
    {
        Message msg = Message.obtain();
        msg.what = what;
        msg.arg1 = textID;
        msg.obj = resultText;
        
        try
        {
            mMessenger.send(msg);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    @Override
    public IBinder onBind(Intent intent)
    {
        return messenger.getBinder();
    }
}
