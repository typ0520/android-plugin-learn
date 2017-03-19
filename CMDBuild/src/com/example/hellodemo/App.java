package com.example.hellodemo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tong on 16/4/21.
 */
public class App extends Application {
    private static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        ClassLoader classLoader = getClassLoader();
        Log.e(TAG,"onCreate: " + classLoader.toString());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        //copyDexFiles();

        //MultiDex.install(this);
        ClassLoader classLoader = getClassLoader();
        Log.e(TAG,"attachBaseContext: " + classLoader.toString());
    }




































    private void copyDexFiles() {
        try {
            //创建 /data/data/com.example.hellodemo/code_cache/secondary-dexes
            File dexDir = new File(getFilesDir(),"mydexes");
            if (!dexDir.exists()) {
                dexDir.mkdirs();
            }

            InputStream is = getAssets().open("classes2.dex.zip");
            int len = -1;
            byte[] buffer = new byte[1024];

            File dexFile = new File(dexDir,"com.example.hellodemo-1.apk.classes2.zip");
            FileOutputStream fos = new FileOutputStream(dexFile);
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer,0,len);
            }
            is.close();
            fos.close();

            if (dexFile.exists()) {
                Log.d(TAG,"copy classes2.dex success");
            }
            else {
                Log.d(TAG,"copy classes2.dex fail");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
