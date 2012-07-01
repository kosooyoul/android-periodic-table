package net.ahyane.renderbase;

import javax.microedition.khronos.opengles.GL11;

import android.view.MotionEvent;

public abstract class Layer {
	public boolean is2D(){return false;}
	
    public static final float TRACKING_MARGIN = 30.0f;

	public float mX = 0f;
	public float mY = 0f;
	public float mZ = 0f;
	
	public float mAngleX = 0.0f;
	public float mAngleY = 0.0f;
	public float mAngleZ = 0.0f;
	
	public float mWidth = 0;
	public float mHeight = 0;
	public boolean mHidden = false;
	public float mAlpha = 1.0f;
	public LayerAnimation mLayerAnimation = null;
	public int mFrame = 0;
	
	public int mHitArea = 0;
	
	public void setLayerAnimation(LayerAnimation layerAnimation){
		if(layerAnimation != null){
			mLayerAnimation = layerAnimation;
			layerAnimation.setLayer(this);
			mFrame = 0;
		}
	}
	
	public void setAlpha(float alpha) {
		mAlpha = alpha;
	}
	
	public final float getAlpha() {
		return mAlpha;
	}
    
    public final float getX() {
        return mX;
    }

    public final float getY() {
        return mY;
    }

    public final float getZ() {
        return mZ;
    }

    public final void setPosition(float x, float y) {
    	if(mX != x || mY != y) {
        	mX = x;
	        mY = y;
	        onPositionChanged();
        }
    }
    
    public final void setAngle(float angleX, float angleY, float angleZ) {
    	if(mAngleX != angleX || mAngleY != angleY || mAngleZ != angleZ) {
    		mAngleX = angleX;
    		mAngleY = angleY;
    		mAngleZ = angleZ;
	        onAngleChanged();
        }
    }

    public final void setZ(float z) {
    	if(mZ != z) {
	        mZ = z;
	        onPositionChanged();
        }
    }

    public final void setPosition(float x, float y, float z) {
        if(mX != x || mY != y || mZ != z) {
        	mX = x;
	        mY = y;
	        mZ = z;
	        onPositionChanged();
        }
    }

    public final void setDepth(float z) {
        if(mZ != z) {
	        mZ = z;
	        onPositionChanged();
        }
    }

    public float getWidth() {
        return mWidth;
    }

    public float getHeight() {
        return mHeight;
    }

    public final void setSize(float width, float height) {
        if (mWidth != width || mHeight != height) {
            mWidth = width;
            mHeight = height;
            onSizeChanged();
        }
    }

    public boolean isHidden() {
        return mHidden;
    }

    public void setHidden(boolean hidden) {
        if (mHidden != hidden) {
            mHidden = hidden;
            onHiddenChanged();
        }
    }

    public abstract void generate(RenderView view, RenderView.Lists lists);

    // Returns true if something is animating.
    public boolean update(RenderView view, float frameInterval) {
        return false;
    }

    public void renderOpaque(RenderView view, GL11 gl) {
    }

    public void renderBlended(RenderView view, GL11 gl) {
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    // Allows subclasses to further constrain the hit test defined by layer
    // bounds.
    public boolean containsPoint(float x, float y) {
        return true;
    }

    public void onSurfaceCreated(RenderView view, GL11 gl) {
    }
    
    public void onPositionChanged() {
    }
    
    public void onAngleChanged() {
    }
    
    public void onSizeChanged() {
    }

    public void onHiddenChanged() {
    }
    
    public void setHitArea(int hitArea){
    	mHitArea = hitArea;
    }
    
    public void startAnimation(){
    	mLayerAnimation.startAnimation();
    	mLayerAnimation.onStart();
    }
    
    public void endAnimation(){
    	mLayerAnimation.endAnimation();
    	mLayerAnimation.onEnd();
    }
}
