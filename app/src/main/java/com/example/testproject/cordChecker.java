package com.example.testproject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.opengl.Matrix;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowInsets;
import android.view.WindowMetrics;

import androidx.annotation.NonNull;

public class cordChecker extends Activity {
    static Context ac;
    static Activity ac2;
    public cordChecker(Activity ac) {
        this.ac = ac.getApplicationContext();
        this.ac2 = ac;
    }

    static boolean chekCord(float x, float y){

        float[] objCords = Square.squareCoords;

        //cheking x
        float topLeftX = objCords[0];
        float topRightX = objCords[9];
        float topLeftY = objCords[1];
        float bottomLeftY = objCords[4];

        if(x >= topLeftX && x <= topRightX && y >= bottomLeftY && y <= topLeftY) {
            return true;
        }
        else {
            return false;
        }
    }

    static Vec2 wordCords(float x, float y){

        float[] objCords = Square.squareCoords;
        Vec2 worldPos = new Vec2();

        //cheking x
        float topLeftX = objCords[0];
        float topRightX = objCords[9];
        float topLeftY = objCords[1];
        float bottomLeftY = objCords[4];



        int screenW  = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenH = Resources.getSystem().getDisplayMetrics().heightPixels;


        System.out.println("displayHeight: " + screenH);

        float[] invertedMatrix, transformMatrix,
                normalizedInPoint, outPoint;
        invertedMatrix = new float[16];
        transformMatrix = new float[16];
        normalizedInPoint = new float[4];
        outPoint = new float[4];

/*
        int oglTouchY = (int) (screenH - y);
*/

               /* Transform the screen point to clip
       space in ogl (-1,1) */
        normalizedInPoint[0] =
                (float) (x * 2.0f / screenW - 1.0);
        normalizedInPoint[1] =
                //  -- (-1 (original), -1.073 hard coded to fix onTouch height problem) --
                //         BACK TO ORIGINAL !?!?!?!
                - (float) ((y) / 2160 * 2 - 1.0);
        normalizedInPoint[2] = -1.0f;
        normalizedInPoint[3] = 1.0f;

//        System.out.println("Points you touched: x=" + x + " y= " + y);
//
//        System.out.println("Transformed coordinates: x=" + normalizedInPoint[0] + " y= " + normalizedInPoint[1]);

        Matrix.multiplyMM(transformMatrix, 0, openGLRenderer.projectionMatrix, 0, openGLRenderer.viewMatrix, 0);

        Matrix.invertM(invertedMatrix, 0, transformMatrix, 0);

        Matrix.multiplyMV(outPoint, 0, invertedMatrix, 0, normalizedInPoint, 0);


        if (outPoint[3] == 0.0)
        {
            // Avoid /0 error.
            Log.e("World coords", "ERROR!");
            return worldPos;
        }

        // Divide by the 3rd component to find
        // out the real position.
        worldPos.Set(
                outPoint[0] / outPoint[3],
                outPoint[1] / outPoint[3]);

        return worldPos;

    }

}