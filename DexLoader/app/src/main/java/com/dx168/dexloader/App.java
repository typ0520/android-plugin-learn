package com.dx168.dexloader;

import android.app.Application;
import android.content.Context;
import android.support.multidex2.MultiDex;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 16/7/4.
 */
public class App extends Application {
    private static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ClassLoader classLoader = getClassLoader();

        Log.d(TAG,classLoader.toString());

        installDex();
        installDex1();

        Log.d(TAG,classLoader.toString());
    }

    public void copyDexFiles(String assetName,File target) {
        try {
            InputStream is = getAssets().open(assetName);
            int len = -1;
            byte[] buffer = new byte[1024];

            FileOutputStream fos = new FileOutputStream(target);
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer,0,len);
            }
            is.close();
            fos.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    private void installDex() {
        try {
            //创建 /data/data/com.example.hellodemo/code_cache/secondary-dexes
            File dexDir = getFilesDir();

            File dexFile = new File(dexDir,"com.dx168.dexloader-1.apk.classes2.zip");
            copyDexFiles("com.dx168.dexloader-1.apk.classes2.zip",dexFile);

            List dexFileList = new ArrayList();
            dexFileList.add(dexFile);

            Field pathListField = MultiDex.findField(getClassLoader(), "pathList");
            Object dexPathList = pathListField.get(getClassLoader());

            Object[] dexElements =  MultiDex.V14.makeDexElements(dexPathList, new ArrayList<File>(dexFileList), dexDir);
            MultiDex.expandFieldArray(dexPathList, "dexElements", dexElements);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void installDex1() {
        try {
            //创建 /data/data/com.example.hellodemo/code_cache/secondary-dexes
            File dexDir = getFilesDir();

            File dexFile = new File(dexDir,"hellosign1.apk");
            copyDexFiles("hellosign1.apk",dexFile);

            List dexFileList = new ArrayList();
            dexFileList.add(dexFile);

            Field pathListField = MultiDex.findField(getClassLoader(), "pathList");
            Object dexPathList = pathListField.get(getClassLoader());

            Object[] dexElements =  MultiDex.V14.makeDexElements(dexPathList, new ArrayList<File>(dexFileList), dexDir);

            //Object dexElement = dexElements[0];
            //Field dexFileField = MultiDex.findField(dexElement,"dexFile");
            //dexFileField.set(dexElement,new DexFile(dexFile));
            MultiDex.expandFieldArray(dexPathList, "dexElements", dexElements);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
