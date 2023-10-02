package com.example.testproject;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

import static android.opengl.Matrix.translateM;

import android.content.Context;

public class TextureObjet {

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * 4;

    private int objectIndex;
    boolean moved;

    private static final float[] dataForTextures = {
            // Order of coordinates: X, Y, S, T
            // Triangle Fan
            0f, 0f,        0.5f, 0.5f,
            -1f, -0.25f,    0f, 1f,
            1f, -0.25f,    1f, 1f,
            1f, 0.25f,     1f, 0.0f,
            -1f, 0.25f,     0f, 0.0f,
            -1f, -0.25f,    0f, 1f,

    };
    private final VertexBuffer vertexBuffer;

    private TheProgram program;
    private final int BYTES_PER_FLOAT = 4;
    public float[] modelMatrix = new float[16];

    public TextureObjet() {
        moved = true;
        vertexBuffer = new VertexBuffer(dataForTextures);


        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix,0, 0f, 1f, 0f);
    }
     public void bindData(TheProgram program) {

        this.program = program;
        vertexBuffer.start();
        vertexBuffer.setVertexAttribPointer(0, program.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, STRIDE);
        vertexBuffer.setVertexAttribPointer(POSITION_COMPONENT_COUNT*BYTES_PER_FLOAT, program.getTextureCoordinatesAttributeLocation(), TEXTURE_COORDINATES_COMPONENT_COUNT, STRIDE);
    }

    public void draw() {

        vertexBuffer.start();
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
        vertexBuffer.end();
    }

}
