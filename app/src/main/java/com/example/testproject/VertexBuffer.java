package com.example.testproject;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_STATIC_DRAW;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glBufferData;
import static android.opengl.GLES20.glBufferSubData;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGenBuffers;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class VertexBuffer {
    private final int bufferId;
    private final int BYTES_PER_FLOAT = 4;
    int attributeLocation;

    public VertexBuffer(float[] vertexData) {
        // Allocate a buffer.
        final int[] buffers = new int[1];
        glGenBuffers(1, buffers, 0);
        if (buffers[0] == 0) {
            throw new RuntimeException("Could not create a new vertex buffer object.");
        }
        bufferId = buffers[0];

        // Bind to the buffer.
        glBindBuffer(GL_ARRAY_BUFFER, bufferId);

        // Transfer data to native memory.
        FloatBuffer buffer = ByteBuffer
                .allocateDirect(vertexData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        buffer.position(0);


        // Transfer data from native memory to the GPU buffer.
        glBufferData(GL_ARRAY_BUFFER, vertexData.length * BYTES_PER_FLOAT,
                buffer, GL_STATIC_DRAW);

        // IMPORTANT: Unbind from the buffer when we're done with it.
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        // We let vertexArray go out of scope, but it won't be released
        // until the next time the garbage collector is run.
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation, int componentCount, int stride) {
        this.attributeLocation = attributeLocation;
        glEnableVertexAttribArray(attributeLocation);
        glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT, false, stride, dataOffset);


    }

/*    public void updateBuffer(float[] vertexData) {
        buffer.clear();
        buffer.put(vertexData);
        buffer.position(0);

        glBindBuffer(GL_ARRAY_BUFFER, bufferId);
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer.capacity() * BYTES_PER_FLOAT, buffer);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }*/


    public void start() {
        glBindBuffer(GL_ARRAY_BUFFER, bufferId);
    }

    public void end() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(attributeLocation);


        glUseProgram(0);
    }
}
