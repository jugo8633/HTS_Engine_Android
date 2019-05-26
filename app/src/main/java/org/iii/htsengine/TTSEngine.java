package org.iii.htsengine;

/**
 * Created by Jugo on 2018/8/22
 */
public class TTSEngine
{
    /**
     * TTS NDK Call
     */
    static
    {
        System.loadLibrary("TTS");
    }
    
    public native short[] synthesize(String strUtterance, String strRate, String strPath, String strLanType);
    
    public native String TextProcessVersion();
    
    
    //=========================== Java Call Function ===============================================//
    public short[] tts(String strUtterance, String strRate, String strPath, String strLanType)
    {
        return synthesize(strUtterance, strRate, strPath, strLanType);
    }
    
    public String getTPVersion()
    {
        return TextProcessVersion();
    }
    
    
}
