package com.dx168.dexloader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getResources()

        try {
            Class clazz = Class.forName("com.example.hellodemo.UserInfo");

            if (clazz != null) {
                Toast.makeText(this,clazz.toString(),Toast.LENGTH_LONG).show();
            }

            clazz = Class.forName("com.example.hellodemo.App");

            if (clazz != null) {
                Toast.makeText(this,clazz.toString(),Toast.LENGTH_LONG).show();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
