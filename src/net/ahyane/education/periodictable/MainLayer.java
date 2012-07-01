package net.ahyane.education.periodictable;

import javax.microedition.khronos.opengles.GL11;

import net.ahyane.education.periodictable.PeriodicTable.Atom;
import net.ahyane.renderbase.LinearScroll;
import net.ahyane.renderbase.RenderView;
import net.ahyane.renderbase.RenderView.Lists;
import net.ahyane.renderbase.RootLayer;
import net.ahyane.renderbase.object.Animation3D;
import android.opengl.GLES11;
import android.opengl.GLES11Ext;
import android.util.Log;
import android.view.MotionEvent;

public class MainLayer extends RootLayer{
	private LinearScroll mXScroller;
	private LinearScroll mYScroller;
	
	private PeriodicTable mPeriodicTable;
	private RenderManager mRenderManager;

	private float mRatio = 1.0f;
	private float mPixelateRatio = 1.0f;
	
	public MainLayer(RenderView renderView) {
		super();
	
		mPeriodicTable = new PeriodicTable();
		mPeriodicTable.initialize();

		mRenderManager = new RenderManager(renderView);
		
	}

	@Override
	public void onSurfaceCreated(RenderView renderView, GL11 gl) {
		super.onSurfaceCreated(renderView, gl);
	}

	@Override
	public void onSurfaceChanged(RenderView view, int width, int height) {
		super.onSurfaceChanged(view, width, height);

		float ratio;
		ratio = (float)Math.tan((60.0f * Math.PI / 180.0f) / 2) * 2;
		ratio = ratio / getHeight();
		ratio = ratio * 4;
		mRatio = ratio;
		mPixelateRatio = 1.0f / ratio;
				
		mRenderManager.setRatio(mRatio);
		
		setSize(width, height);
		setPosition(0.0f, 0.0f);

		mXScroller = new LinearScroll(this);
		mXScroller.setLayerSize(18 * mPixelateRatio);
		mXScroller.setSize(width);
		mXScroller.setMargin(width * 0.5f, width * 0.5f - mPixelateRatio);
//		mXScroller.setMargin(0, 0);

		mYScroller = new LinearScroll(this);
		mYScroller.setLayerSize((float)7 * mPixelateRatio);
		mYScroller.setSize(height);
		mYScroller.setMargin(height * 0.5f, height * 0.5f - mPixelateRatio);
//		mYScroller.setMargin(0, 0);
		
		
	}

	@Override
	public void generate(RenderView view, Lists lists) {
		lists.opaqueList.add(this);
		lists.hitTestList.add(this);
	}

	@Override
	public void renderOpaque(RenderView view, GL11 gl) {
		float x = mXScroller.getScrolledPosition();
		float y = mYScroller.getScrolledPosition();

		gl.glMatrixMode(GLES11.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -4.0f);
		
		gl.glTranslatef((x - 480 / 2) * mRatio, (320 / 2 -y) * mRatio, 0.0f);
		
		gl.glEnable(GLES11.GL_BLEND);
		gl.glTranslatef(0.0f, 0.0f, -0.5f);
		
		int l = 0, t = 0;
		for(int i = 0; i < PeriodicTable.periodicTableData.length; i++){
			int e = PeriodicTable.periodicTableData[i];

			if(i > 0 && i % 18 == 0){
				l = 0;
				t++;
			}
			
			if(e > 0 && e <= 118){
				
				Atom atom =  mPeriodicTable.atoms.get(e - 1);
				
				gl.glPushMatrix();
				gl.glTranslatef(l, -t, 0.0f);
				
//				if(focusedIndex == i){
//					if(isItemSelected){
//						drawRectR((int)x - 10 + (int)(l / mRatio), (320 - (int)y) - 10 - (int)(t / mRatio), 20, 20);
//					}else{
//						drawRectC((int)x - 10 + (int)(l / mRatio), (320 - (int)y) - 10 - (int)(t / mRatio), 20, 20);
//					}
//				}else if(downedIndex == i){
//					drawRect((int)x - 10 + (int)(l / mRatio), (320 - (int)y) - 10 - (int)(t / mRatio), 20, 20);
//				}else{
//					drawRect((int)x - 20 + (int)(l / mRatio), (320 - (int)y) - 20 - (int)(t / mRatio), 40, 40);
//				}
				if(focusedIndex == i){
					if(isItemSelected){
						gl.glColor4f(0.0f, 0.90f, 1.0f, 1.0f);
					}else{
						gl.glColor4f(0.0f, 0.65f, 0.8f, 1.0f);
					}
				}else{
					gl.glColor4f(0.4f, 0.4f, 0.4f, 1.0f);
				}
				mRenderManager.drawAtom(atom);
				gl.glPopMatrix();
			}
			
			l++;			
		}
		
		mXScroller.play();
		mYScroller.play();

		
		drawRect(touchedX - 2, (320 - touchedY) - 2, 4, 4);
		
	}

	public void drawRectR(int x, int y, int w, int h){
		GLES11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		GLES11Ext.glDrawTexfOES(x, y, 0, w, h);
		GLES11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
		GLES11Ext.glDrawTexfOES(x + 1, y + 1, 0, w - 2, h - 2);
	}

	public void drawRectC(int x, int y, int w, int h){
		GLES11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		GLES11Ext.glDrawTexfOES(x, y, 0, w, h);
		GLES11.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
		GLES11Ext.glDrawTexfOES(x + 1, y + 1, 0, w - 2, h - 2);
	}

	public void drawRect(int x, int y, int w, int h){
		GLES11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		GLES11Ext.glDrawTexfOES(x, y, 0, w, h);
		GLES11.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
		GLES11Ext.glDrawTexfOES(x + 1, y + 1, 0, w - 2, h - 2);
	}

	@Override
	public boolean containsPoint(float x, float y) {
		return true;
	}

	int downedIndex;
	int focusedIndex;
	int downedX;
	int downedY;
	int prevTouchedX;
	int prevTouchedY;
	int touchedX;
	int touchedY;
	boolean isItemSelected = false;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		prevTouchedX = touchedX;
		prevTouchedY = touchedY;
		touchedX = x;
		touchedY = y;
		
		int l = (int)((touchedX - mXScroller.getScrolledPosition()) * mRatio + 0.5f);
		int t = (int)((touchedY - mYScroller.getScrolledPosition()) * mRatio + 0.5f);
		int index;
		if(l < 0 || l >= 18)index = -1;
		else if(t < 0 || t >=7)index = -1;
		else index = t * 18 + l;
			
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				downedIndex = index;
				downedX = x;
				downedY = y;
				if(downedIndex >= 0 && focusedIndex == downedIndex){
					isItemSelected = true;
				}else{
					isItemSelected = false;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if(Math.abs(downedX - touchedX) > 10){
					downedIndex = -1;
				}else if(Math.abs(downedY - touchedY) > 10){
					downedIndex = -1;
				}
				break;
			case MotionEvent.ACTION_UP:
				if(downedIndex == index){
					if(focusedIndex == index){
						;
					}else{
						if(focusedIndex < 0 || focusedIndex >= PeriodicTable.periodicTableData.length){
							;
						}else{
							int e = PeriodicTable.periodicTableData[focusedIndex];
							if(e > 0 && e <= 118){
								Atom atom = mPeriodicTable.atoms.get(e - 1);
								Animation3D animation3d = atom.getAnimation3d();
								animation3d.snap(Animation3D.OBJECT_ANGLE, 360.0f);
							}
						}
						if(index >= 0){
							focusedIndex = index;
						}
					}
					downedIndex = -1;
				}else{
					downedIndex = -1;
				}
				break;
		}
		
		if(isItemSelected){
			if(focusedIndex < 0 || focusedIndex >= PeriodicTable.periodicTableData.length){
				;
			}else{
				int e = PeriodicTable.periodicTableData[focusedIndex];
				if(e > 0 && e <= 118){
					Atom atom = mPeriodicTable.atoms.get(e - 1);
					Animation3D animation3d = atom.getAnimation3d();
					Log.e("ATOM", "atom : " + atom.name);
					switch(event.getAction()){
						case MotionEvent.ACTION_DOWN:
							animation3d.stop(Animation3D.OBJECT_ANGLE);
							break;
						case MotionEvent.ACTION_MOVE:
							animation3d.rotate(Animation3D.OBJECT_ANGLE, 0.0f, touchedX - prevTouchedX, 0.0f);
							break;
						case MotionEvent.ACTION_UP:
							animation3d.snap(Animation3D.OBJECT_ANGLE, 90.0f);
							break;
					}
				}
			}
		}else{
			switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					mXScroller.touchDown(x);
					mYScroller.touchDown(y);
					break;
				case MotionEvent.ACTION_MOVE:
					mXScroller.touchMove(x);
					mYScroller.touchMove(y);
					break;
				case MotionEvent.ACTION_UP:
					mXScroller.touchUp(x);
					mYScroller.touchUp(y);		
					break;
			}
		}
		
		return true;
	}

	

}
