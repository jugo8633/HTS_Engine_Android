package org.iii.text.chinese.tool;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by joe on 2018/9/4
 */
public class TextTool
{
    
    /**
     * Copies assets needed for speech synthesis to the data folder.
     *
     * @param context the context used
     */
    public static synchronized void copyAssets(Context context, String assetDataPath, String assetDataName)
    {
        copyFile(context, assetDataName, assetDataPath);
    }
    
    /**
     * Copy file.
     *
     * @param context  the context
     * @param filename the filename
     */
    private static synchronized void copyFile(Context context, String filename, String modelPath)
    {
        AssetManager assetManager = context.getAssets();
       
        InputStream in = null;
        OutputStream out = null;
        String newFileName = null;
        try
        {
            
            in = assetManager.open(modelPath + filename);
            
            
            newFileName = modelPath + filename;
            Log.d("newFileName", newFileName);
            
            final File newFile = new File(context.getFilesDir().getAbsolutePath() + "/" + modelPath);
            newFile.mkdir();
            
            out = new FileOutputStream(context.getFilesDir().getAbsolutePath() + "/" + newFileName);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1)
            {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        }
        catch (Exception e)
        {
            Log.e("ftw", "Exception in copyFile() " + e.toString());
        }
    }
    
    
}
