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

    private final FloatBuffer floatBuffer;
    public VertexBuffer(float[] vertexData) {
            floatBuffer = ByteBuffer.allocateDirect(vertexData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
                    //vertex coordinates!!!
                    .put(vertexData);
        }

        public void setVertexAttribPointer(int dataOffset, int attributeLocation, int componentCount, int stride) {
            floatBuffer.position(dataOffset);

            //glVertexAttribPointer(...) - kazva na opengl-a kude v pametta da sa tezi vertex-i i kak da gi chete
            //index = attributeLocation - tova e lokaciqta na koqto da pishe vertex-ite
            //size = componentCount - s kolko koordinati opisvame vertex-a
            //type = GL_FLOAT
            //normalized = false - tva ni interesuva samo ako polzvame int data??
            //stride = POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT - informaciq za tova kolko vida argumenti se pazqt vuv vertex array-a
            //Buffer ptr = flatBuffer - kude e vertex array-a
            glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT, false, stride, floatBuffer);

            //trqbav da enable-nem attributite
            glEnableVertexAttribArray(attributeLocation);
            floatBuffer.position(0);
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
        /*glBindBuffer(GL_ARRAY_BUFFER, bufferId);*/
    }

    public void end() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        /*glDisableVertexAttribArray(attributeLocation);*/


        glUseProgram(0);
    }
}
