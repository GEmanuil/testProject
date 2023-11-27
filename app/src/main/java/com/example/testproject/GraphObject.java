package com.example.testproject;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

import android.opengl.GLES20;

import com.google.android.material.shape.TriangleEdgeTreatment;

public class GraphObject {

    int numOfRectangles;
    private final int STRIDE = 2 + 3 /* 2 - X, Y -;- 3 - R, G, B */;
    private VertexBuffer vertexBuffer;
    private float[] modelMatrix;
    private TheProgram program;
    private float[] vertecies;

    private final int mProgram;
    private int positionHandle;
    private int colorHandle;
    private int vPMatrixHandle;

    private final String fragmentShaderCode =
                    "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
                    "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    double func(double x) {
        return 5 * Math.pow(Math.E, x + (double) 1 / 2);
    }

    float f(float x) {

        return (float) func(x);
    }

    public GraphObject(int numbOfRectangles) {
        this.numOfRectangles = numbOfRectangles;
        vertecies = new float[6 * numbOfRectangles * 2];
        configureData(numbOfRectangles);

        modelMatrix = new float[16];
        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix,0, 0f, 1f, 0f);

        int vertexShader = openGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = openGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);
    }

    private void configureData(int numbOfRectangles) {
        float[] tempVertex = new float[numbOfRectangles * 6 * 2];
        //x - [-6, 0]; f(x) - [0, 8]
        float c = (float) 6 / numbOfRectangles;

        for (int i = 0; i < numbOfRectangles; i++) {

            //Triangle 1
            vertecies[i * 12 + 0] = -6 + c*i;
            vertecies[i * 12 + 1] = f(-6 + c*i);

            vertecies[i * 12 + 2] = Math.abs(-6 + c*i);
            vertecies[i * 12 + 3] = Math.abs(f((-6 + c*i)));

            vertecies[i * 12 + 4] = Math.abs(-6 + c*(i + 1));
            vertecies[i * 12 + 5] = Math.abs(f(-6 + c*(i + 1)));


            //Triangle 2
            vertecies[i * 12 + 6] = Math.abs(-6 + c*(i + 1));
            vertecies[i * 12 + 7] = Math.abs(f(-6 + c*(i + 1)));

            vertecies[i * 12 + 8] = (-6 + c*(i + 1));
            vertecies[i * 12 + 9] = f((-6 + c*(i + 1)));

            vertecies[i * 12 + 10] = (-6 + c*i);
            vertecies[i * 12 + 11] = f(-6 + c*i);
        }

        vertexBuffer = new VertexBuffer(vertecies);
    }

/*    public void bindData(TheProgram program) {
        this.program = program;
        vertexBuffer.start();
        vertexBuffer.setVertexAttribPointer(0, program.getPositionAttributeLocation(), 2, 2);

    }*/

    public void draw(float[] vPMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        vertexBuffer.start();

        // get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the triangle coordinate data
        vertexBuffer.setVertexAttribPointer(0, positionHandle, 2, 2);

        // get handle to fragment shader's vColor member
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        float[] color = {1f, 1f, 0f, 0f};
        // Set color for drawing the triangle
        GLES20.glUniform4fv(colorHandle, 1, color, 0 );


        // get handle to shape's transformation matrix
        vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, vPMatrix, 0);



        glDrawArrays(GL_TRIANGLES , 0, 6);


        vertexBuffer.end();
    }
}
