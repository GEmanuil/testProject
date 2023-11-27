package com.example.testproject;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private glSurfaceView CuV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


/*        //FULL SCREEN

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/


        super.onCreate(savedInstanceState);

        setContentView(R.layout.mainactivity);
        CuV = findViewById(R.id.middle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CuV.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CuV.onPause();
    }
}