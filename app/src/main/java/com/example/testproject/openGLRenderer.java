package com.example.testproject;

import static android.opengl.Matrix.orthoM;
import static android.opengl.Matrix.setLookAtM;

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

    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    static float[] vPMatrix = new float[16];
    static float[] projectionMatrix = new float[16];
    static float[] viewMatrix = new float[16];
    float[] scratch = new float[16];
    static float[] rotationMatrix = new float[16];

    Square square;
    TextureObjet textureObjet;
    GraphObject gO;

    TheProgram program;
    private int texture;

    public volatile float vert;
    public volatile float horizon;

    public openGLRenderer (Context context) {
        super();
        this.context = context;                         // Save Specified Context
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glClearColor(1f, 1f, 1f, 1f);

        program = new TheProgram(context);
        gO = new GraphObject(20);

        textureObjet = new TextureObjet();
        texture = TextureHelper.loadTexture(context, R.drawable.atlas);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;                             // Save Current Width
        this.height = height;                           // Save Current Height

        GLES20.glViewport(0, 0, width, height);
        final float ratio = width > height ?
                (float) width / (float) height :
                (float) height / (float) width;


        if (width > height) {
            // Landscape
            orthoM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, -1f, 1f);
        }
        else {
            // Portrait or square
            orthoM(projectionMatrix, 0, -1f, 1f, -ratio, ratio, -1f, 1f);
        }
        setLookAtM(viewMatrix, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);


        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);


        program.useProgram();
        program.setUniforms(vPMatrix);

        /*gO.bindData(program);*/
        gO.draw();

        /*square.draw(vPMatrix);*/

/*        textureObjet.bindData(program);
        textureObjet.draw();*/

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
