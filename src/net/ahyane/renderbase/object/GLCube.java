package net.ahyane.renderbase.object;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import net.ahyane.renderbase.RenderView;
import android.opengl.GLES11;

public class GLCube extends GLObject{
	private static final float cubeVertices[] = {
		 -0.5f, -0.5f,  0.5f,  0.5f, -0.5f,  0.5f,
		 -0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f,
		 
		 -0.5f,  0.5f, -0.5f,  0.5f,  0.5f, -0.5f,
		 -0.5f, -0.5f, -0.5f,  0.5f, -0.5f, -0.5f, 
		 
		 -0.5f, -0.5f,  0.5f, -0.5f,  0.5f,  0.5f,
		 -0.5f, -0.5f, -0.5f, -0.5f,  0.5f, -0.5f,
		 
		  0.5f, -0.5f, -0.5f,  0.5f,  0.5f, -0.5f,
		  0.5f, -0.5f,  0.5f,  0.5f,  0.5f,  0.5f,
		 
		  0.5f,  0.5f,  0.5f,  0.5f,  0.5f, -0.5f,
		 -0.5f,  0.5f,  0.5f, -0.5f,  0.5f, -0.5f, 
		 
		 -0.5f, -0.5f,  0.5f, -0.5f, -0.5f, -0.5f,
		  0.5f, -0.5f,  0.5f,  0.5f, -0.5f, -0.5f,
		  
		  //---------------------------------

		 -0.51f, -0.51f,  0.50f,  0.51f, -0.51f,  0.5f,
		 -0.51f,  0.51f,  0.50f,  0.51f,  0.51f,  0.5f,
		 
	};
		
	private static final float cubeTexCoords[] = {
		0.0f, 1.0f, 1.0f, 1.0f,
		0.0f, 0.0f, 1.0f, 0.0f,
		  
		1.0f, 1.0f, 1.0f, 0.0f,
		0.0f, 1.0f, 0.0f, 0.0f,
		  
		1.0f, 1.0f, 1.0f, 0.0f,
		0.0f, 1.0f, 0.0f, 0.0f,
		  
		1.0f, 1.0f, 1.0f, 0.0f,
		0.0f, 1.0f, 0.0f, 0.0f,
		  
		1.0f, 0.0f, 0.0f, 0.0f,
		1.0f, 1.0f, 0.0f, 1.0f,
		
		0.0f, 0.0f, 0.0f, 1.0f,
		1.0f, 0.0f, 1.0f, 1.0f,			
		
		//-------------------

		0.0f, 1.0f, 1.0f, 1.0f,
		0.0f, 0.0f, 1.0f, 0.0f,
		  
	};
		
	private static final FloatBuffer cubeVertexBuffer = getFloatBuffer(cubeVertices);
	private static final FloatBuffer cubeTexCoordBuffer = getFloatBuffer(cubeTexCoords);
	
	public GLCube(RenderView renderView){
		super(renderView);
	}

	@Override
	public void init(GL10 gl){
		super.init(gl);
	}
	
	@Override
	public void release(GL10 gl){
		super.release(gl);
	}
	
	@Override
	public void render(GL10 gl){
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeVertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, cubeTexCoordBuffer);

		gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		
		gl.glLineWidth(0.5f);
		//gl.glNormal3f(0.0f, 0.0f, 1.0f);
		gl.glDrawArrays(GL10.GL_LINES, 0, 4);
		//gl.glNormal3f(0.0f, 0.0f, -1.0f);
		gl.glDrawArrays(GL10.GL_LINES, 4, 4);
		
		//gl.glNormal3f(-1.0f, 0.0f, 0.0f);
		gl.glDrawArrays(GL10.GL_LINES, 8, 4);
		//gl.glNormal3f(1.0f, 0.0f, 0.0f);
		gl.glDrawArrays(GL10.GL_LINES, 12, 4);
		
		//gl.glNormal3f(0.0f, 1.0f, 0.0f);
		gl.glDrawArrays(GL10.GL_LINES, 16, 4);
		//gl.glNormal3f(0.0f, -1.0f, 0.0f);
		gl.glDrawArrays(GL10.GL_LINES, 20, 4);
		
		
		gl.glDisable(GL11.GL_DEPTH_TEST);
		gl.glColor4f(0.8f, 0.8f, 0.8f, 0.4f);

		//gl.glNormal3f(0.0f, 0.0f, 1.0f);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
//		//gl.glNormal3f(0.0f, 0.0f, -1.0f);
//		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);
//		
//		//gl.glNormal3f(-1.0f, 0.0f, 0.0f);
//		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
//		//gl.glNormal3f(1.0f, 0.0f, 0.0f);
//		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);
//		
//		//gl.glNormal3f(0.0f, 1.0f, 0.0f);
//		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
//		//gl.glNormal3f(0.0f, -1.0f, 0.0f);
//		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
		
		gl.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	@Override
	public void render(){
		GLES11.glVertexPointer(3, GLES11.GL_FLOAT, 0, cubeVertexBuffer);
		GLES11.glTexCoordPointer(2, GLES11.GL_FLOAT, 0, cubeTexCoordBuffer);

//		GLES11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		
		GLES11.glLineWidth(1.0f);
		//GLES11.glNormal3f(0.0f, 0.0f, 1.0f);
		GLES11.glDrawArrays(GLES11.GL_LINES, 0, 4);
		//GLES11.glNormal3f(0.0f, 0.0f, -1.0f);
		GLES11.glDrawArrays(GLES11.GL_LINES, 4, 4);
		
		//GLES11.glNormal3f(-1.0f, 0.0f, 0.0f);
		GLES11.glDrawArrays(GLES11.GL_LINES, 8, 4);
		//GLES11.glNormal3f(1.0f, 0.0f, 0.0f);
		GLES11.glDrawArrays(GLES11.GL_LINES, 12, 4);
		
		//GLES11.glNormal3f(0.0f, 1.0f, 0.0f);
		GLES11.glDrawArrays(GLES11.GL_LINES, 16, 4);
		//GLES11.glNormal3f(0.0f, -1.0f, 0.0f);
		GLES11.glDrawArrays(GLES11.GL_LINES, 20, 4);

//		float[] color = new float[4];
//		GLES11.glGetFloatv(GLES11.GL_COLOR_MATERIAL, color, 0);
		GLES11.glColor4f(0.87f, 0.93f, 0.95f, 0.75f);
//		GLES11.glDisable(GLES11.GL_DEPTH_TEST);
//		GLES11.glColor4f(0.8f, 0.8f, 0.8f, 0.4f);
//		//GLES11.glNormal3f(0.0f, 0.0f, 1.0f);
		GLES11.glDrawArrays(GLES11.GL_TRIANGLE_STRIP, 24, 4);
//		//GLES11.glNormal3f(0.0f, 0.0f, -1.0f);
//		GLES11.glDrawArrays(GLES11.GL_TRIANGLE_STRIP, 4, 4);
//		
//		//GLES11.glNormal3f(-1.0f, 0.0f, 0.0f);
//		GLES11.glDrawArrays(GLES11.GL_TRIANGLE_STRIP, 8, 4);
//		//GLES11.glNormal3f(1.0f, 0.0f, 0.0f);
//		GLES11.glDrawArrays(GLES11.GL_TRIANGLE_STRIP, 12, 4);
//		
//		//GLES11.glNormal3f(0.0f, 1.0f, 0.0f);
//		GLES11.glDrawArrays(GLES11.GL_TRIANGLE_STRIP, 16, 4);
//		//GLES11.glNormal3f(0.0f, -1.0f, 0.0f);
//		GLES11.glDrawArrays(GLES11.GL_TRIANGLE_STRIP, 20, 4);
//		
//		GLES11.glEnable(GLES11.GL_DEPTH_TEST);
		
//		GLES11.glColor4f(color[0], color[1], color[2], color[3]);
	}
	
	public void renderWire(){
		GLES11.glVertexPointer(3, GLES11.GL_FLOAT, 0, cubeVertexBuffer);
		GLES11.glTexCoordPointer(2, GLES11.GL_FLOAT, 0, cubeTexCoordBuffer);

		GLES11.glLineWidth(1.0f);
		GLES11.glDrawArrays(GLES11.GL_LINES, 0, 4);
		GLES11.glDrawArrays(GLES11.GL_LINES, 4, 4);
		
		GLES11.glDrawArrays(GLES11.GL_LINES, 8, 4);
		GLES11.glDrawArrays(GLES11.GL_LINES, 12, 4);
		
		GLES11.glDrawArrays(GLES11.GL_LINES, 16, 4);
		GLES11.glDrawArrays(GLES11.GL_LINES, 20, 4);
	}
	
	public void renderFace(){
		GLES11.glVertexPointer(3, GLES11.GL_FLOAT, 0, cubeVertexBuffer);
		GLES11.glTexCoordPointer(2, GLES11.GL_FLOAT, 0, cubeTexCoordBuffer);

		GLES11.glDrawArrays(GLES11.GL_TRIANGLE_STRIP, 24, 4);
	}
}
