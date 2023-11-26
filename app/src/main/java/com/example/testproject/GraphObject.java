package com.example.testproject;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

import com.google.android.material.shape.TriangleEdgeTreatment;

public class GraphObject {

    float r, g, b = 0.5f;

    int numOfRectangles;

    private final int STRIDE = 2 + 3 /* 2 - X, Y -;- 3 - R, G, B */;

    private VertexBuffer vertexBuffer;
    private float[] modelMatrix;
    private TheProgram program;

    private float[] vertecies;

    double func(double x) {
        return 5 * Math.pow(Math.E, x + (double) 1 / 2);
    }

    float f(float x) {

        return (float) func(x);
    }

    public GraphObject(int numbOfRectangles) {
        this.numOfRectangles = numbOfRectangles;
        vertecies = new float[6 * numbOfRectangles * STRIDE];
        configureData(numbOfRectangles);

        modelMatrix = new float[16];

        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix,0, 0f, 1f, 0f);
    }

    private void configureData(int numbOfRectangles) {
        float[] tempVertex = new float[numbOfRectangles * 6 * 2];
        //x - [-6, 0]; f(x) - [0, 8]
        float c = (float) 6 / numbOfRectangles;

        for (int i = 0; i < numbOfRectangles; i++) {

            //Triangle 1
            tempVertex[i * 12 + 0] = -6 + c*i;
            tempVertex[i * 12 + 1] = f(-6 + c*i);

            tempVertex[i * 12 + 2] = Math.abs(-6 + c*i);
            tempVertex[i * 12 + 3] = Math.abs(f((-6 + c*i)));

            tempVertex[i * 12 + 4] = Math.abs(-6 + c*(i + 1));
            tempVertex[i * 12 + 5] = Math.abs(f(-6 + c*(i + 1)));


            //Triangle 2
            tempVertex[i * 12 + 6] = Math.abs(-6 + c*(i + 1));
            tempVertex[i * 12 + 7] = Math.abs(f(-6 + c*(i + 1)));

            tempVertex[i * 12 + 8] = (-6 + c*(i + 1));
            tempVertex[i * 12 + 9] = f((-6 + c*(i + 1)));

            tempVertex[i * 12 + 10] = (-6 + c*i);
            tempVertex[i * 12 + 11] = f(-6 + c*i);
        }

        vertexBuffer = new VertexBuffer(tempVertex);
    }

    public void bindData(TheProgram program) {
        this.program = program;
        vertexBuffer.start();
        vertexBuffer.setVertexAttribPointer(0, program.getPositionAttributeLocation(), 2, 2);

    }

    public void draw() {

        vertexBuffer.start();

        glDrawArrays(GL_TRIANGLES , 0, 3);
        vertexBuffer.end();
    }
}
