package com.example.testproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private glSurfaceView CuV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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