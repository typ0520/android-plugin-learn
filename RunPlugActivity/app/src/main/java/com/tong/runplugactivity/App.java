package com.tong.runplugactivity;

import android.app.Application;
import android.content.Context;

import net.mobctrl.hostapk.AssetsMultiDexLoader;

/**
 * Created by tong on 16/7/6.
 */
public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        AssetsMultiDexLoader.install(this);
    }
}
