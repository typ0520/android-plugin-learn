package com.example.hellodemo;

import android.app.Activity;
import android.os.Bundle;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        UserInfo userInfo = new UserInfo("typ","xxx");
//
//        TextView tv = (TextView) findViewById(R.id.tv);
//        Toast.makeText(this,userInfo.getUser(),0).show();
//        Toast.makeText(this,userInfo.getPassword(),0).show();
//
//        tv.setText(userInfo.getUser() + " | " + userInfo.getPassword());
    }
}
