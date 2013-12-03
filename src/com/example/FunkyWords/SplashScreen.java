package com.example.FunkyWords;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Copyright (c) 2013 MadeByMany. All rights reserved.
 * Project: FunkyWords
 * Package: com.example.FunkyWords
 * User: Nelson Sachse
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                startActivity(new Intent(SplashScreen.this,BaseActivity.class));
                finish();
            }
        }).start();
    }
}
