package net.ahyane.education.periodictable;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import net.ahyane.renderbase.RenderUtils;
import net.ahyane.renderbase.RenderView;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.SensorEvent;
import android.opengl.GLU;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MainRenderView extends RenderView{
	public MainRenderView(Context context) {
		super(context);
	}

	@Override
	public void onSurfaceCreated(GL10 gl1, EGLConfig config) {
		super.onSurfaceCreated(gl1, config);
		
		GL11 gl = (GL11) gl1;
		
        // Disable unused state.
    	gl.glEnable(GL11.GL_DITHER);
        gl.glDisable(GL11.GL_LIGHTING);
		gl.glDisable(GL11.GL_DEPTH_TEST);

        // Set global state.
        gl.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

        // Enable textures.
        //gl.glEnable(GL11.GL_TEXTURE_2D);
        //gl.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_REPLACE);

        // Enable Vertex Arrays
        gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        // Enable depth test.
        gl.glEnable(GL11.GL_DEPTH_TEST);
        gl.glDepthFunc(GL11.GL_LEQUAL);
        gl.glActiveTexture(GL11.GL_TEXTURE0);
        
        // Set the blend function for premultiplied alpha.
        gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Set the background color.
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
        if (mRootLayer != null) {
            mRootLayer.onSurfaceCreated(this, gl);
        }
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl1, int width, int height) {
		super.onSurfaceChanged(gl1, width, height);
		// Set the viewport and projection matrix.
		GL11 gl = (GL11)gl1;
		
		gl.glViewport(0, 0, width, height);
		
		final float zNear = 0.1f;
		final float zFar = 100.0f;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL11.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 60.0f, (float) width / height, zNear, zFar);

		RenderUtils.setPerspective(this, gl);
        
        if (mRootLayer != null) {
            mRootLayer.onSurfaceChanged(this, width, height);
        }
        gl.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
    	super.onSensorChanged(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	return super.onTouchEvent(event);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }
}
