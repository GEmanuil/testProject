package com.example.testproject;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class openGLRenderer extends Thread implements GLSurfaceView.Renderer {
    Context context;
    private int width = 100;                           // Updated to the Current Width + Height in onSurfaceChanged()
    private int height = 100;
    private Square mSquare;

    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    static float[] vPMatrix = new float[16];
    static float[] projectionMatrix = new float[16];
    static float[] viewMatrix = new float[16];
    float[] scratch = new float[16];
    static float[] rotationMatrix = new float[16];

    public volatile float vert;
    public volatile float horizon;

    public openGLRenderer (Context context) {
        super();
        this.context = context;                         // Save Specified Context
    }

    public float getVert() {
        return vert;
    }

    public void setVert(float angle) {
        vert = angle;
    }

    public float getHorizon() {
        return horizon;
    }

    public void setHorizon(float angle) {
        horizon = angle;
    }

    public float[] getProjection() {
        return projectionMatrix;
    }

    public float[] getViewMatrix() {
        return viewMatrix;
    }

    public float[] getVPMatrix() {
        return vPMatrix;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//
        GLES20.glEnable(GLES20.GL_BLEND);
//        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        //Setting white background
        GLES20.glClearColor(1f, 1f, 1f, 1f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;


        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        mSquare = new Square();

        Matrix.setLookAtM(viewMatrix, 0, horizon, vert, 3, horizon, vert, 0f, 0f, 1.0f, 0.0f);


        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        // Draw shape
        mSquare.draw(vPMatrix);
    }


    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
