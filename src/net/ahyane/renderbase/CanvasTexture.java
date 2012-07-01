package net.ahyane.renderbase;

import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.opengl.GLUtils;
import android.util.Log;

public abstract class CanvasTexture{
    private static final String TAG = "CanvasTexture";

    public int mWidth;
    public int mHeight;
    public int mTextureId;
    public int mTextureWidth;
    public int mTextureHeight;
    public float mNormalizedWidth;
    public float mNormalizedHeight;

    public final Canvas mCanvas = new Canvas();
    public final Bitmap.Config mBitmapConfig;
    public Bitmap mBitmap = null;
    public boolean mNeedsDraw = false;
    public boolean mNeedsResize = false;
    public GL11 mCachedGL = null;
    
    public CanvasTexture(Bitmap.Config bitmapConfig) {
        mBitmapConfig = bitmapConfig;
    }

    public void clear() {
        if(mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }

    public final void setNeedsDraw() {
        mNeedsDraw = true;
    }

    public final int getWidth() {
        return mWidth;
    }

    public final int getHeight() {
        return mHeight;
    }

    public final float getNormalizedWidth() {
        return mNormalizedWidth;
    }

    public final float getNormalizedHeight() {
        return mNormalizedHeight;
    }

    public void setSize(int width, int height) {
        mWidth = width;
        mHeight = height;
        mNeedsResize = true;
        mTextureWidth = -1;
        mTextureHeight = -1;
        onSizeChanged();
    }

    public void resetTexture() {
        mTextureId = 0;
        mNeedsResize = true;
    }

    public void beginMask(RenderView view, GL11 gl, int x, int y, int width, int height) {
    	gl.glEnable(GL11.GL_SCISSOR_TEST);
    	gl.glScissor(x, y, width, height);
    }

    public void endMask(RenderView view, GL11 gl) {
    	gl.glDisable(GL11.GL_SCISSOR_TEST);
    }

    public boolean bind(GL11 gl) {
        if (mCachedGL != gl) {
            mCachedGL = gl;
            resetTexture();
        }
        int width = (int) mWidth;
        int height = (int) mHeight;
        int textureId = mTextureId;
        int textureWidth = mTextureWidth;
        int textureHeight = mTextureHeight;
        Canvas canvas = mCanvas;
        Bitmap bitmap = mBitmap;
	
    	try {
            if (mNeedsResize || mTextureId == 0) {
                mNeedsDraw = true;

                // Compute the power-of-2 padded size for the texture.
                int newTextureWidth = Shared.nextPowerOf2(width);
                int newTextureHeight = Shared.nextPowerOf2(height);

                // Reallocate the bitmap only if the padded size has changed.
                // TODO: reuse same texture if it is already large enough, just
                // change clip rect.
                if (textureWidth != newTextureWidth || textureHeight != newTextureHeight || mTextureId == 0) {
                    // Allocate a texture if needed.
                    if (textureId == 0) {
                        int[] textureIdOut = new int[1];
                        gl.glGenTextures(1, textureIdOut, 0);
                        textureId = textureIdOut[0];
                        mNeedsResize = false;
                        mTextureId = textureId;

                        // Set texture parameters.
                        gl.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
                        gl.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP_TO_EDGE);
                        gl.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP_TO_EDGE);
                        gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
                        gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
                    }

                    // Set the new texture width and height.
                    textureWidth = newTextureWidth;
                    textureHeight = newTextureHeight;
                    mTextureWidth = newTextureWidth;
                    mTextureHeight = newTextureHeight;
                    mNormalizedWidth = (float) width / textureWidth;
                    mNormalizedHeight = (float) height / textureHeight;

                    // Recycle the existing bitmap and create a new one.
                    if (bitmap != null)
                        bitmap.recycle();
                    if (textureWidth > 0 && textureHeight > 0) {
                        bitmap = Bitmap.createBitmap(textureWidth, textureHeight, mBitmapConfig);
                        canvas.setBitmap(bitmap);
                        mBitmap = bitmap;
                    }
                }
            }
    
            // Bind the texture to the context.
            if (textureId == 0) {
                return false;
            }
            gl.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

            // Redraw the contents of the texture if needed.
            if (mNeedsDraw) {
                mNeedsDraw = false;
                renderCanvas(canvas, bitmap, width, height);
                int[] cropRect = { 0, height, width, -height };
                gl.glTexParameteriv(GL11.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, cropRect, 0);
                GLUtils.texImage2D(GL11.GL_TEXTURE_2D, 0, bitmap, 0);

            }
    	} catch (Exception e) {
    	    mBitmap = null;
        } catch (OutOfMemoryError eMem) {
            Log.i(TAG, "Bitmap power of 2 creation fail, outofmemory");
        }

        return true;
    }

    public void draw(RenderView view, GL11 gl, int x, int y) {
        if (bind(gl)) {
//            view.draw2D(x, y, 0, mWidth, mHeight);
        }
    }

    public void drawWithEffect(RenderView view, GL11 gl, float x, float y, float anchorX, float anchorY, float alpha, float scale) {
        if (bind(gl)) {
            float width = mWidth;
            float height = mHeight;

            // Apply scale transform if not identity.
            if (scale != 1) { // CR: 1.0f
                float originX = x + anchorX * width;
                float originY = y + anchorY * height;
                width *= scale;
                height *= scale;
                x = originX - anchorX * width;
                y = originY - anchorY * height;
            }

            // Set alpha if needed.
            if (alpha != 1f) { // CR: 1.0f
//                view.setAlpha(alpha);
            }
//            view.draw2D(x, y, 0, width, height);
            if (alpha != 1f) {
//                view.resetColor();
            }
        }
    }

    protected abstract void onSizeChanged();
    
    protected abstract void renderCanvas(Canvas canvas, Bitmap backing, int width, int height);

} // CR: superfluous newline above.
