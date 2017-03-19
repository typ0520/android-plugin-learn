package com.dx168.epmyg.app.trade;

import android.app.Application;
import android.util.Log;

/**
 * Created by tong on 16/7/8.
 */
public class App extends Application {
    private static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG,"app.main: onCreate");
    }
}
