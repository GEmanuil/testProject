package com.example.testproject;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class glSurfaceView extends GLSurfaceView {

    openGLRenderer renderer;

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
        setRenderer(renderer);
    }


}
