package net.ahyane.education.periodictable;

import javax.microedition.khronos.opengles.GL11;

import net.ahyane.education.periodictable.PeriodicTable.Atom;
import net.ahyane.renderbase.LinearScroll;
import net.ahyane.renderbase.RenderView;
import net.ahyane.renderbase.RenderView.Lists;
import net.ahyane.renderbase.RootLayer;
import net.ahyane.renderbase.object.Animation3D;
import net.ahyane.renderbase.object.Animation3D.OnAnimationListener;
import android.opengl.GLES11;
import android.opengl.GLES11Ext;
import android.util.Log;
import android.view.MotionEvent;

public class MainLayer extends RootLayer{
	//Scroll
	private LinearScroll mXScroll;
	private LinearScroll mYScroll;
	private LinearScroll mZScroll;
	
	//Data
	private PeriodicTable mPeriodicTable;
	
	//This Layer Renderer
	private RenderManager mRenderManager;
	
	//Child Layers
	private FunctionLayer mFunctionLayer;

	//Values
	private float mBaseRatio = 1.0f;
	private float mRatio = 1.0f;
	private float mPixelateRatio = 1.0f;
	private float mDepth = 4.0f;
	
	public MainLayer(RenderView renderView) {
		super();

		//Scroll
		mXScroll = new LinearScroll(this);
		mYScroll = new LinearScroll(this);
		mZScroll = new LinearScroll(this);
		
		//Data
		mPeriodicTable = new PeriodicTable();
		mPeriodicTable.initialize();

		//This Layer Renderer
		mRenderManager = new RenderManager(renderView);
		
		//Child Layers
		mFunctionLayer = new FunctionLayer();

	}

	@Override
	public void onSurfaceCreated(RenderView renderView, GL11 gl) {
		super.onSurfaceCreated(renderView, gl);
	}

	@Override
	public void onSurfaceChanged(RenderView view, int width, int height) {
		super.onSurfaceChanged(view, width, height);

		//Calculate Ratios
		updateRatio();
		mBaseRatio = mRatio;
		mRenderManager.setRatio(mRatio);
		
		//This Layer Layout
		setSize(width, height);
		setPosition(0.0f, 0.0f);

		//Main Scroll
		mXScroll.setLayerSize((18 - 1) * mPixelateRatio);
		mXScroll.setSize(width);
		mXScroll.setMargin(width * 0.5f, width * 0.5f);
		mXScroll.setPosition(-width * 0.5f);

		mYScroll.setLayerSize((7 - 1) * mPixelateRatio);
		mYScroll.setSize(height);
		mYScroll.setMargin(height * 0.5f, height * 0.5f);
		mYScroll.setPosition(-height * 0.5f);
		
		mZScroll.setLayerSize(1.0f);
		mZScroll.setSize(1.0f);
		mZScroll.setMargin(2.0f, 8.0f);
		
		//Function Layer Layout
		mFunctionLayer.setSize(width, height);
		mFunctionLayer.setPosition(0.0f, 0.0f);
	}
	
	private void updateRatio(){
		float ratio;
		ratio = (float)Math.tan((60.0f * Math.PI / 180.0f) / 2) * 2;
		ratio = ratio / getHeight();
		ratio = ratio * mDepth;
		mRatio = ratio;
		mPixelateRatio = 1.0f / ratio;

		float xScrollRatio = mXScroll.getScrolledRatio();
		float yScrollRatio = mYScroll.getScrolledRatio();
		
		mXScroll.setLayerSize((18 - 1) * mPixelateRatio);
		mYScroll.setLayerSize((7 - 1) * mPixelateRatio);
		
		if(xScrollRatio != Float.NaN){
			mXScroll.setScrolledRatio(xScrollRatio);
		}
		if(yScrollRatio != Float.NaN){
			mYScroll.setScrolledRatio(yScrollRatio);
		}
	}

	@Override
	public void generate(RenderView view, Lists lists) {
		//Background Layers
		;
		
		//This Layer
		lists.blendedList.add(this);
		lists.hitTestList.add(this);
		lists.updateList.add(this);
		
		//Child Layers
		mFunctionLayer.generate(view, lists);
	}

	@Override
	public boolean update(RenderView view, float frameInterval) {
		
//		//Child Layers Status
//		boolean isItemNoFocused = focusedIndex == -1;
//		mFunctionLayer.setHidden(isItemNoFocused);
		
		return true;
	}

	@Override
	public void renderBlended(RenderView view, GL11 gl) {
		float x = mXScroll.getScrolledPosition();
		float y = mYScroll.getScrolledPosition();

		//Child Layers Status
		boolean isItemNoFocused = focusedIndex == -1;
		mFunctionLayer.setHidden(isItemNoFocused);
		
		//
		gl.glMatrixMode(GLES11.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -mDepth);
		
		gl.glTranslatef((x - 480 / 2) * mRatio, (320 / 2 -y) * mRatio, 0.0f);
		
		gl.glTranslatef(0.0f, 0.0f, -0.5f);
		
		float xcount = mWidth * mRatio;
		float ycount = mHeight * mRatio;
		
		float xl = -x * mRatio - 1;
		float xr = xl + xcount + 2;

		float yt = -y * mRatio - 1;
		float yb = yt + ycount + 2;
		
		int l = 0, t = 0;
		for(int i = 0; i < PeriodicTable.periodicTableData.length; i++){
			int e = PeriodicTable.periodicTableData[i];

			if(i > 0 && i % 18 == 0){
				l = 0;
				t++;
			}
			
			if(e > 0 && e <= 118){
				Atom atom =  mPeriodicTable.atoms.get(e - 1);
				
				if(xl < l && l < xr && yt < t && t < yb){
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
				
				atom.animation3d.play();
			}

			l++;			
		}
		
		if(isZoomScrolling == false){
			mXScroll.play();
			mYScroll.play();
		}
		
		mZScroll.play();
		if(isZoomScrolling == true || mZScroll.isArrived() == false){
			mDepth = 4.0f - mZScroll.getScrolledPosition();
			updateRatio();
		}
		
		drawRect((int)touchedX - 2, (320 - (int)touchedY) - 2, 4, 4);
		
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
	float downedX;
	float downedY;
	float prevTouchedX;
	float prevTouchedY;
	float touchedX;
	float touchedY;
	boolean isItemSelected = false;

	float prevDistance;
	float prevPrevDistance;
	boolean isZoomScrolling = false;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
    	int touchPointerCount = event.getPointerCount();
    	
    	//Zoom
		if(touchPointerCount >= 2){
	    	int touchPointerId1 = event.getPointerId(0);
	    	int touchPointerId2 = event.getPointerId(1);
	    	
			float dx = event.getX(touchPointerId1) - event.getX(touchPointerId2);
			float dy = event.getY(touchPointerId1) - event.getY(touchPointerId2);
			float distance = (float)Math.sqrt(dx * dx + dy + dy) * mBaseRatio;

			switch(event.getAction()){
				case MotionEvent.ACTION_POINTER_1_DOWN:
				case MotionEvent.ACTION_POINTER_2_DOWN:
				case MotionEvent.ACTION_POINTER_3_DOWN:
				case MotionEvent.ACTION_DOWN:
					mZScroll.touchDown(distance);
					prevDistance = distance;
					isZoomScrolling = true;
					break;
				case MotionEvent.ACTION_MOVE:
					mZScroll.touchMove(distance);
					prevPrevDistance = prevDistance;
					prevDistance = distance;
					break;
			}
			
			Log.e("asd", "dis = " + distance);
			
			
			
			return true;
		}
    	
		float x = event.getX();
		float y = event.getY();
		prevTouchedX = touchedX;
		prevTouchedY = touchedY;
		touchedX = x;
		touchedY = y;
		
		int l = (int)((touchedX - mXScroll.getScrolledPosition()) * mRatio + 0.5f);
		int t = (int)((touchedY - mYScroll.getScrolledPosition()) * mRatio + 0.5f);
		int index;
		if(l < 0 || l >= 18)index = -1;
		else if(t < 0 || t >=7)index = -1;
		else{
			index = t * 18 + l;
			if(PeriodicTable.periodicTableData[index] == 0){
				index = -1;
			}
		}
			
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
				if(isItemSelected == false){//
					if(Math.abs(downedX - touchedX) > 10){
						downedIndex = -1;
					}else if(Math.abs(downedY - touchedY) > 10){
						downedIndex = -1;
					}
				}//
				break;
			case MotionEvent.ACTION_POINTER_1_UP:
			case MotionEvent.ACTION_POINTER_2_UP:
			case MotionEvent.ACTION_POINTER_3_UP:
			case MotionEvent.ACTION_UP:
				if(isZoomScrolling){
			    	int touchPointerId1 = event.getPointerId(0);
			    	int touchPointerId2 = event.getPointerId(1);
			    	Log.e("Asd", " asd " + touchPointerId1 + " , " + touchPointerId2);
//					float dx = event.getX(touchPointerId1) - event.getX(touchPointerId2);
//					float dy = event.getY(touchPointerId1) - event.getY(touchPointerId2);
					mZScroll.touchUp(prevDistance + (prevDistance - prevPrevDistance));
					isZoomScrolling = false;
					break;
				}
				
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
								animation3d.setOnAnimationListener(new OnAnimationListener() {
									@Override
									public void onArrived(Animation3D animation3d, int element) {
										if(element == Animation3D.OBJECT_ANGLE){
											animation3d.moveTo(Animation3D.PIVOT, 0.0f, 0.0f, 0.0f); 
											animation3d.setOnAnimationListener(null);
										}
									}
								});
							}
						}
						if(0 <= index && index < PeriodicTable.periodicTableData.length){
							focusedIndex = index;
							//Focused Animation
							int e = PeriodicTable.periodicTableData[focusedIndex];
							if(e > 0 && e <= 118){
								Atom atom = mPeriodicTable.atoms.get(e - 1);
								Animation3D animation3d = atom.getAnimation3d();
								animation3d.moveTo(Animation3D.PIVOT, 0.0f, 0.0f, 1.0f);
							}
						}else{
							if(downedIndex == -1){//
								focusedIndex = -1;//
							}//
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
					mXScroll.touchDown(x);
					mYScroll.touchDown(y);
					break;
				case MotionEvent.ACTION_MOVE:
					mXScroll.touchMove(x);
					mYScroll.touchMove(y);
					break;
				case MotionEvent.ACTION_UP:
					mXScroll.touchUp(x);
					mYScroll.touchUp(y);		
					break;
			}
		}
		
		return true;
	}

	

}
