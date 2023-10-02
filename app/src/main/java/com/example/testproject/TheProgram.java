package com.example.testproject;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.content.Context;

public class TheProgram extends ShaderProgram{
        private final int uMatrixLocation;
        private final int uTextureUnitLocation;

        // Attribute locations
        private final int aPositionLocation;
        private final int aTextureCoordinatesLocation;
        private final int uBoolIsTexture;

        //colors for custom objets
        private float r, g, b;
        private int itIsATexture;

    public TheProgram(Context context){
            super(context, R.raw.vertex_shader_for_everyhing, R.raw.fragment_shader_for_everything);

            // Retrieve uniform locations for the shader program.
            uMatrixLocation = glGetUniformLocation(program, U_MATRIX);


            aPositionLocation = glGetAttribLocation(program, A_POSITION);

            uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);
            aTextureCoordinatesLocation = glGetAttribLocation(program, A_TEXTURE_COORDINATES);

            uBoolIsTexture = glGetUniformLocation(program, U_ISTEXTURE);
            itIsATexture = 0;
        }

        public int getPositionAttributeLocation() {
            return aPositionLocation;
        }

        public int getTextureCoordinatesAttributeLocation() { return aTextureCoordinatesLocation; }

        public void setUniforms(float[] matrix, int textureId) {
            // Pass the matrix into the shader program.
            glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
            /*glUniform4f(uColorLocation, r, g, b, 1f);*/

            // Set the active texture unit to texture unit 0.
            glActiveTexture(GL_TEXTURE0);

            // Bind the texture to this unit.
            glBindTexture(GL_TEXTURE_2D, textureId);

            // Tell the texture uniform sampler to use this texture in the shader by
            // telling it to read from texture unit 0.
            glUniform1i(uTextureUnitLocation, 0);

        }


        public void isTexture(boolean texture) {
/*        if (texture) {
            itIsATexture = 1;
        }
        else {
            itIsATexture = 0;
        }*/

            itIsATexture = texture ? 1 : 0;
            glUniform1i(uBoolIsTexture, itIsATexture);


        }

        public void end() {
            glDisableVertexAttribArray(getPositionAttributeLocation());
            glDisableVertexAttribArray(getTextureCoordinatesAttributeLocation());
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
}
