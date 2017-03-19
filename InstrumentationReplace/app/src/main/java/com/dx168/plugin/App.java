package com.dx168.plugin;

import android.app.Application;
import android.app.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by tong on 16/7/3.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        try {
            //获取ActivityThread引用
            Class clazz = Class.forName("android.app.ActivityThread");
            Method method = clazz.getMethod("currentActivityThread");
            Object activityThread = method.invoke(null);

            //替换Instrumentation
            Field field = clazz.getDeclaredField("mInstrumentation");
            field.setAccessible(true);
            Instrumentation oldInstrumentation = (Instrumentation) field.get(activityThread);

            DxInstrumentation dxInstrumentation = new DxInstrumentation(oldInstrumentation);
            field.set(activityThread,dxInstrumentation);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
