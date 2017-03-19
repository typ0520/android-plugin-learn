package com.tong.runplugactivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import net.mobctrl.hostapk.AssetsManager;
import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by tong on 16/7/3.
 */
public class DxInstrumentation extends Instrumentation {
    private Instrumentation mBase;

    public DxInstrumentation(Instrumentation mBase) {
        this.mBase = mBase;
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        boolean isBundleIntent = className.equals(ProxyActivity.class.getName());
        if (isBundleIntent) {
            intent = intent.getParcelableExtra("oldIntent");
            className = intent.getComponent().getClassName();
        }
        Activity activity = super.newActivity(cl, className, intent);

        if (isBundleIntent) {
            File apkDir = App.context.getDir(AssetsManager.APK_DIR, Context.MODE_PRIVATE);
            //替换resource
            Resources resources = getBundleResource(App.context,new File(apkDir,"plug1.apk").getAbsolutePath());
            try {
                Method method = activity.getClass().getMethod("setResources",Resources.class);
                method.invoke(activity,resources);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return activity;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        Log.d(DxInstrumentation.class.getSimpleName(), "DxInstrumentation execStartActivity");

        intent = replaceIntent(who,intent);
        try {
            Method execStartActivity = Instrumentation.class.getDeclaredMethod(
                    "execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Activity.class,
                    Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            return (ActivityResult) execStartActivity.invoke(mBase, who,
                    contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Intent replaceIntent(Context who, Intent intent) {
        Intent newIntent = intent;
        if (intent.getComponent().getClassName().contains("com.tong.plug1")) {
            newIntent = new Intent(who,ProxyActivity.class);
            newIntent.putExtra("oldIntent",intent);
        }
        return newIntent;
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
