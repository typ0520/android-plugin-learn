package com.dx168.epmyg.app.trade.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dx168.epmyg.app.trade.R;

import net.wequick.small.Small;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startIM(View v) {
        Small.openUri("chat",MainActivity.this);
    }
}
