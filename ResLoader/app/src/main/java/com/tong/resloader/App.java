package com.tong.resloader;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by tong on 16/7/6.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 插件化的资源加载需要做到这三点
         * 宿主加载插件资源
         * 插件加载自身资源
         * 插件加载宿主资源
         */
        loadPlugRes();
        loadPlugRes2();
    }

    private void loadPlugRes2() {
        copyDexFiles("ResTest1-debug.apk",getFilesDir());

        AssetManager assetManager = getAssets();
        try {
            AssetManager.class.getDeclaredMethod("addAssetPath", String.class)
                    .invoke(assetManager, new File(getFilesDir(),"ResTest1-debug.apk").getAbsolutePath());

            int strId = getResources().getIdentifier("test_str", "string", "com.tong.restest1");
            String str = getResources().getString(strId);
            Log.d("App","str: " + str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //加载插件资源
    private void loadPlugRes() {
        copyDexFiles("ResTest1-debug.apk",getFilesDir());

        Resources resources = getBundleResource(this,new File(getFilesDir(),"ResTest1-debug.apk").getAbsolutePath());
        int strId = resources.getIdentifier("test_str", "string", "com.tong.restest1");
        String str = resources.getString(strId);
        Log.d("App","str: " + str);
    }

    public void copyDexFiles(String assetName,File dir) {
        try {
            InputStream is = getAssets().open(assetName);
            int len = -1;
            byte[] buffer = new byte[1024];

            FileOutputStream fos = new FileOutputStream(new File(dir,assetName));
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer,0,len);
            }
            is.close();
            fos.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static Resources getBundleResource(Context context, String apkPath){
        AssetManager assetManager = createAssetManager(apkPath);
        return new Resources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
    }

    private static AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(
                    assetManager, apkPath);
            return assetManager;
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return null;
    }
}
