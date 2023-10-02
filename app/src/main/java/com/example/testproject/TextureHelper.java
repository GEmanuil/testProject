package com.example.testproject;

import static android.opengl.GLES10.glGenTextures;
import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLUtils.texImage2D;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;


public class TextureHelper {
	private static final String TAG = "TextureHelper";
	public static int loadTexture(final Context context, final int resourceId) {
/*		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false; // No pre-scaling
		final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
		
		return loadTexture(bitmap);*/

		final int[] textureObjectIds = new int[1];

		//generating a texture object
		glGenTextures(1, textureObjectIds, 0);
		if (textureObjectIds[0] == 0) {

			return 0;
		}


		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

		if (bitmap == null) {

			glDeleteTextures(1, textureObjectIds, 0);
			return 0;
		}

		//kazva na Opengl che texturite shte sa 2D i che sledvashti texure callove shte sa za tozi texture
		glBindTexture(GL_TEXTURE_2D, textureObjectIds[0]);

		//minification i magnification filters
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		// Load the bitmap into the bound texture.
		texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

		//generira mipmapove
		glGenerateMipmap(GL_TEXTURE_2D);

		// Recycle the bitmap, since its data has been loaded into
		// OpenGL.
		bitmap.recycle();

		// Unbind from the texture kato texture object pointer = 0
		glBindTexture(GL_TEXTURE_2D, 0);

		return textureObjectIds[0];

	}
	public static int loadTexture(Bitmap bitmap)
	{
	    final int[] textureHandle = new int[1];
	 
	    GLES20.glGenTextures(1, textureHandle, 0);
	 
	    if (textureHandle[0] != 0)
	    {
//	        final BitmapFactory.Options options = new BitmapFactory.Options();
//	        options.inScaled = false;   // No pre-scaling
	 
	        // Read in the resource
//	        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
	 
	        // Bind to the texture in OpenGL
	        glBindTexture(GL_TEXTURE_2D, textureHandle[0]);
	 
	        // Set filtering
	        GLES20.glTexParameteri(GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
	        GLES20.glTexParameteri(GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
	        GLES20.glTexParameterf(GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE );  // Set U Wrapping
	        GLES20.glTexParameterf(GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE );  // Set V Wrapping

	        // Load the bitmap into the bound texture.
	        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
	 
	        // Recycle the bitmap, since its data has been loaded into OpenGL.
	        bitmap.recycle();
	    }
	 
	    if (textureHandle[0] == 0)
	    {
	        throw new RuntimeException("Error loading texture.");
	    }
	 
	    return textureHandle[0];
	}
}
