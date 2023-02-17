package com.example.testproject;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private glSurfaceView CuV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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