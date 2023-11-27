package com.example.testproject;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class glSurfaceView extends GLSurfaceView {

    openGLRenderer renderer;

    Vec2 wordCords = new Vec2();

    Context context;

    private final float TOUCH_SCALE_FACTOR = 1.0f / 1080;
    private float previousX;
    private float previousY;

    public glSurfaceView(Context context) {
        super(context);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        renderer = new openGLRenderer(context);
        init(context);
    }

    public glSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        renderer = new openGLRenderer(context);
        init(context);
    }

    private void init (Context context) {
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);

        this.context = context;

        setRenderer(renderer);
    }


   /* @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        int a = getHeight();

        System.out.println("a height - " + a);

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - previousX;
                float dy = y - previousY;


                renderer.setHorizon(renderer.getHorizon() + ((-dx) * TOUCH_SCALE_FACTOR));
                renderer.setVert(renderer.getVert() + ((dy) * TOUCH_SCALE_FACTOR));

                requestRender();

                break;

            case  MotionEvent.ACTION_DOWN:

                wordCords = cordChecker.wordCords(x, y);
                System.out.println("Clicked on coordinates: " + wordCords.X() + ", " + wordCords.Y());
                if(cordChecker.chekCord((float) wordCords.X(), (float) wordCords.Y())){

//                               DO  SOMETHING!!!!!!!!!
                    System.out.println("Clicked on the right place!!!!!!!!!!!!!!");

                    *//*renderer.drawAddBar();*//*
                    requestRender();

                }
                break;
        }

        previousX = x;
        previousY = y;
        return true;
    }*/


}
