package com.tong.plug1;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by tong on 16/7/6.
 */
public class Plug1Activity extends BaseActivity {
    private static final String TAG = Plug1Activity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plug1);

        Log.d(TAG,"onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG,"onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG,"onDestroy");
    }
}
