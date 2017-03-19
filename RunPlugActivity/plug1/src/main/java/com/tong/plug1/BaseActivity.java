package com.tong.plug1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

/**
 * Created by tong on 16/7/6.
 */
public class BaseActivity extends Activity {
    private Activity mProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

    }

    public void setProxy(Activity proxy) {
        this.mProxy = proxy;
    }

    @Override
    public void setContentView(int layoutResID) {
        mProxy.setContentView(layoutResID);
    }

    @Override
    public View findViewById(int id) {
        return mProxy.findViewById(id);
    }

    @Override
    public Resources getResources() {
        return mProxy.getResources();
    }

    @Override
    public void finish() {
        mProxy.finish();
    }

    public void startActivity(String className) {
        Intent intent = new Intent("com.tong.runplugactivity.ProxyActivity");
        intent.putExtra("ACTIVITY_CLASS", className);
        mProxy.startActivity(intent);
    }
}
