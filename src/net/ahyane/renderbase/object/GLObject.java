package net.ahyane.renderbase.object;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import net.ahyane.renderbase.RenderView;
import android.opengl.GLES11;
import android.view.MotionEvent;

public class GLObject{
	
	private static final float vertices[] = {
		-0.5f, -0.5f,  0.5f, -0.5f,
		-0.5f,  0.5f,  0.5f,  0.5f,
	};
	
	private static final float texCoord[] = {
		0.0f, 1.0f, 1.0f, 1.0f,
		0.0f, 0.0f, 1.0f, 0.0f,
	};
	
	public static final FloatBuffer vertexBuffer = getFloatBuffer(vertices);
	public static final FloatBuffer texCoordBuffer = getFloatBuffer(texCoord);
	
	public static final FloatBuffer getFloatBuffer(float[] vertices){
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
		floatBuffer.put(vertices);
		floatBuffer.position(0);
		return floatBuffer;
	}
	
	private RenderView mRenderView;

	public GLObject(RenderView renderView){
		this.mRenderView = renderView;
	}
	
	public RenderView getLayer(){
		return mRenderView;
	}

	public void init(GL10 gl){
		// TODO Auto-generated method stub
		
	}

	public void release(GL10 gl){
		// TODO Auto-generated method stub
		
	}

	public void render(GL10 gl){
		// TODO Auto-generated method stub
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, GLObject.vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, GLObject.texCoordBuffer);
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
	
	public void render(){
		// TODO Auto-generated method stub
		GLES11.glVertexPointer(2, GLES11.GL_FLOAT, 0, GLObject.vertexBuffer);
		GLES11.glTexCoordPointer(2, GLES11.GL_FLOAT, 0, GLObject.texCoordBuffer);
		
		GLES11.glDrawArrays(GLES11.GL_TRIANGLE_STRIP, 0, 4);
	}
	
	public boolean onTouchEvent(MotionEvent event){
		// TODO
		return false;
	}

	//inheritance variable
	protected float left;
	protected float top;
	protected float depth;
	protected float width;
	protected float height;
	
	public float getLeft(){
		return left;
	}

	public void setLeft(float left){
		this.left = left;
	}

	public float getTop(){
		return top;
	}

	public void setTop(float top){
		this.top = top;
	}

	public float getDepth(){
		return depth;
	}

	public void setDepth(float depth){
		this.depth = depth;
	}

	public float getWidth(){
		return width;
	}

	public void setWidth(float width){
		this.width = width;
	}

	public float getHeight(){
		return height;
	}

	public void setHeight(float height){
		this.height = height;
	}

	public void resize(float width, float height) {
		this.width = width;
		this.height = height;		
	}

}
