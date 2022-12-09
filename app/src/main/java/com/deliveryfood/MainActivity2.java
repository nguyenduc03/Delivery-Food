package com.deliveryfood;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Thread td = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (Exception e) {

                } finally {
                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                                        startActivity(intent);


                    finish();
                }


            }
        };
        td.start();
    }
}