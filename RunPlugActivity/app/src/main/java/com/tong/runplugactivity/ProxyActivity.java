package com.tong.runplugactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import net.mobctrl.hostapk.AssetsManager;
import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by tong on 16/7/6.
 */
public class ProxyActivity extends Activity {
    public static final String KEY_ACTIVITY_CLASS = "ACTIVITY_CLASS";

    private Resources mBundleResource;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

        File dexDir = newBase.getDir(AssetsManager.APK_DIR, Context.MODE_PRIVATE);
        mBundleResource = getBundleResource(newBase,new File(dexDir,"plug1.apk").getAbsolutePath());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String className = getIntent().getStringExtra(KEY_ACTIVITY_CLASS);
        try {
            Class clazz = Class.forName(className);
            Object instance = clazz.newInstance();

            setPlugProxy(instance);

            Method method = clazz.getDeclaredMethod("onCreate",Bundle.class);
            method.setAccessible(true);
            method.invoke(instance,savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPlugProxy(Object instance) {
        Method method = null;
        try {
            method = instance.getClass().getMethod("setProxy",Activity.class);
            method.invoke(instance,this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        Intent intent2 = new Intent(this,ProxyActivity.class);
        intent2.putExtra(ProxyActivity.KEY_ACTIVITY_CLASS,intent.getStringExtra(KEY_ACTIVITY_CLASS));
        super.startActivity(intent2);
    }

    @Override
    public Resources getResources() {
        return mBundleResource;
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
