package org.iii.htsengine;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.iii.text.chinese.manager.TextManageHandler;
import org.iii.text.chinese.manager.TextManagementCallback;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    private TTSEngine ttsEngine;
    private EditText mEditText = null;
    private TextManageHandler mTextManageHandler = null;
    private Button btn = null;
    private long startTime = 0;
    private long endTime = 0;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        ttsEngine = new TTSEngine();
        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(ttsEngine.getTPVersion());
        
        btn = findViewById(R.id.button);
        mEditText = findViewById(R.id.editText);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.d("TEXT_LENGTH", String.valueOf(mEditText.getText().toString().length()));
                mTextManageHandler.textSegment(mEditText.getText().toString());
            }
        });
        Log.d("MainActivity", "mTextManageHandler初始化開始");
        
        startTime = System.currentTimeMillis();
        
        mTextManageHandler = new TextManageHandler(this);
        mTextManageHandler.setOnCallBackTextResultListener(new TextManagementCallback()
        {
            @Override
            public void textResult(Message result)
            {
                if (result.arg1 == 1)
                {
                    if (result.what == 0)
                    {
                        Log.d("TextChineseParser", "init successful!");
                        
                        
                        endTime = System.currentTimeMillis();
                        long elapseTime = (endTime - startTime) / 1000;
                        Log.d("MainActivity", "mTextManageHandler初始化結束 共花： " + String.valueOf(elapseTime) +
                                "秒");
                        
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                MainActivity.this.btn.setEnabled(true);
                            }
                        });
                        
                    }
                    else if (result.what == 1)
                    {
                        if (null != result.obj)
                        {
                            HashMap<String, String> textResult = (HashMap<String, String>) result.obj;
                            Log.d("TextChineseParser", "TOKEN_TEXT_ID: " + textResult.get("ID"));
                            Log.d("TextChineseParser", "TOKEN_TEXT: " + textResult.get("TOKEN_TEXT"));
                            Log.d("TextChineseParser", "RESULT_TEXT: " + textResult.get("RESULT_TEXT"));
                            
                        }
                        
                        
                    }
                    
                    
                }
            }
        });
        mTextManageHandler.init();
        
        
        //ttsEngine.tts("情緒/NN 障礙/NN 的/DEG 病人/NN 是否/AD 也/AD 有/VE 權利/JJ 結束/NN 自己/PN 生命/NN", "1", "1", "zh");
    }
    
    
}
