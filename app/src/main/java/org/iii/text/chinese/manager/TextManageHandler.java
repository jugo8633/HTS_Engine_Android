package org.iii.text.chinese.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import java.util.HashMap;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.iii.text.chinese.manager.service.TextManageIntentService;

/**
 * Created by joe on 2018/9/4
 */
public class TextManageHandler
{
    private Context mContext = null;
    
    
    private TextManagementCallback mCallback = null;
    
    public TextManageHandler(Context context)
    {
        mContext = context;
    }
    
    public void setOnCallBackTextResultListener(TextManagementCallback callback)
    {
        mCallback = callback;
    }
    
    
    public void init()
    {
        EventBus.getDefault().register(this);
        Intent m = new Intent(mContext, TextManageIntentService.class);
        Bundle data = new Bundle();
        data.putInt("STATE", Parameters.INIT);
        m.putExtras(data);
        mContext.startService(m);
        
        
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TextMessageEvent event)
    {
        switch (event.commandID)
        {
            case Parameters.INIT:
                new Thread(new StandfordInitRunnable("")).start();
                break;
            case Parameters.TEXT_RESULT:
                new Thread(new StandfordTextRunnable(event.textID, event.tokenText, event.resultText))
                        .start();
                break;
            
        }
        
    }
    
    
    public void textSegment(final String text)
    {
        Intent m = new Intent(mContext, TextManageIntentService.class);
        Bundle data = new Bundle();
        data.putInt("STATE", Parameters.TEXT_RESULT);
        data.putString("ORIGIN_STRING", text);
        m.putExtras(data);
        mContext.startService(m);
        
    }
    
    
    class StandfordInitRunnable implements Runnable
    {
        private String errorMessage = null;
        
        public StandfordInitRunnable(String errorMessage)
        {
            this.errorMessage = errorMessage;
        }
        
        @Override
        public void run()
        {
            Message message = new Message();
            message.what = Parameters.INIT;
            message.arg1 = 1;
            message.obj = errorMessage;
            mCallback.textResult(message);
        }
    }
    
    class StandfordTextRunnable implements Runnable
    {
        private int textID = 0;
        private String tokenText = null;
        private String resultText = null;
        
        public StandfordTextRunnable(int textID, String tokenText, String resultText)
        {
            this.textID = textID;
            this.tokenText = tokenText;
            this.resultText = resultText;
        }
        
        
        @Override
        public void run()
        {
            Message message = new Message();
            message.what = 1;
            message.arg1 = 1;
            HashMap<String, String> textHashMap = new HashMap<>();
            textHashMap.put("TOKEN_TEXT", tokenText);
            textHashMap.put("RESULT_TEXT", resultText);
            textHashMap.put("ID", String.valueOf(textID));
            message.obj = textHashMap;
            mCallback.textResult(message);
        }
    }
    
    
}

