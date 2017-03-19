package com.tong.plug1;

import android.app.Activity;
import android.content.res.Resources;

/**
 * Created by tong on 16/7/6.
 */
public class BaseActivity extends Activity {
    private Resources resources;

    @Override
    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }
}
