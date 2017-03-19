package com.tong.runplugactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startPlug(View v) {
        Intent intent = new Intent(this,ProxyActivity.class);
        intent.putExtra(ProxyActivity.KEY_ACTIVITY_CLASS,"com.tong.plug1.MainActivity");
        startActivity(intent);
    }
}
