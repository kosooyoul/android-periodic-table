package net.ahyane.renderbase.object;

import android.opengl.GLES11;
import android.util.Log;

public class Animation3D {
	public static class Element3D{
		float x = 0.0f;
		float y = 0.0f;
		float z = 0.0f;
	}
	
	public interface OnAnimationListener{
		public void onArrived(Animation3D animation3d, int element);
	}
	
	Element3D pivot;
	Element3D targetPivot;
	boolean isNeedMovingPivot = false;
	
	Element3D position;
	Element3D targetPosition;
	boolean isNeedMovingPosition = false;

	Element3D angle;
	Element3D targetAngle;
	boolean isNeedRotatingAngle = false;
	
	OnAnimationListener mOnAnimationListener = null;
	
	public Animation3D() {
		super();
		pivot = new Element3D();
		targetPivot = new Element3D();
		position = new Element3D();
		targetPosition = new Element3D();
		angle = new Element3D();
		targetAngle = new Element3D();
	}
	
	public void setOnAnimationListener(OnAnimationListener onAnimationListener){
		mOnAnimationListener = onAnimationListener;
	}

	public boolean isNeedPlay(){
		return isNeedMovingPivot || isNeedMovingPosition || isNeedRotatingAngle;
	}
	
	public void play(){
		if(isNeedMovingPivot){
			if(moveToTarget(pivot, targetPivot)){
				isNeedMovingPivot = false;
				if(mOnAnimationListener != null)
					mOnAnimationListener.onArrived(this, PIVOT);
			}
		}
		if(isNeedMovingPosition){
			if(moveToTarget(position, targetPosition)){
				isNeedMovingPosition = false;
				if(mOnAnimationListener != null)
					mOnAnimationListener.onArrived(this, POSITION);
			}
		}
		if(isNeedRotatingAngle){
			if(moveToTarget(angle, targetAngle, 360 * 0.005f)){
				isNeedRotatingAngle = false;
				if(mOnAnimationListener != null)
					mOnAnimationListener.onArrived(this, OBJECT_ANGLE);
			}
		}
	}
	
	public void translate(){
		GLES11.glTranslatef(pivot.x, pivot.y, pivot.z);
		GLES11.glTranslatef(position.x, position.y, position.z);
		GLES11.glRotatef(angle.x, 1.0f, 0.0f, 0.0f);
		GLES11.glRotatef(angle.y, 0.0f, 1.0f, 0.0f);
		GLES11.glRotatef(angle.z, 0.0f, 0.0f, 1.0f);
	}
	
	public static final int PIVOT = 0;
	public static final int POSITION = 1;
	public static final int PIVOT_ANGLE = 10;
	public static final int OBJECT_ANGLE = 11;
	
	public void move(int element, float x, float y, float z){
		switch(element){
			case PIVOT:
				targetPivot.x += x;
				targetPivot.y += y;
				targetPivot.z += z;
				isNeedMovingPivot = true;
				break;
			case POSITION:
				targetPosition.x += x;
				targetPosition.y += y;
				targetPosition.z += z;
				isNeedMovingPosition = true;
				break;
		}
		
	}

	public void moveTo(int element, float x, float y, float z){
		switch(element){
			case PIVOT:
				targetPivot.x = x;
				targetPivot.y = y;
				targetPivot.z = z;
				isNeedMovingPivot = true;
				break;
			case POSITION:
				targetPosition.x = x;
				targetPosition.y = y;
				targetPosition.z = z;
				isNeedMovingPosition = true;
				break;
		}
	}

	public void rotate(int element, float x, float y, float z){
		Log.e("ASD", "H: " + hashCode());
		switch(element){
			case PIVOT_ANGLE:
				break;
			case OBJECT_ANGLE:
				targetAngle.x += x;
				targetAngle.y += y;
				targetAngle.z += z;
				isNeedRotatingAngle = true;
				break;
		}
		
	}

	public void rotateTo(int element, float x, float y, float z){
		switch(element){
			case PIVOT_ANGLE:
				break;
			case OBJECT_ANGLE:
				targetAngle.x = x;
				targetAngle.y = y;
				targetAngle.z = z;
				isNeedRotatingAngle = true;
				break;
		}
	}
	
	public void stop(int element){
		switch(element){
			case PIVOT:
				targetPivot.x = pivot.x;
				targetPivot.y = pivot.y;
				targetPivot.z = pivot.z;
				isNeedMovingPivot = false;
				break;
			case POSITION:
				targetPosition.x = position.x;
				targetPosition.y = position.y;
				targetPosition.z = position.z;
				isNeedMovingPosition = false;
				break;
			case PIVOT_ANGLE:
				break;
			case OBJECT_ANGLE:
				while(-180 > angle.x)angle.x += 360; 
				while(angle.x >= 180)angle.x -= 360;
				while(-180 > angle.y)angle.y += 360; 
				while(angle.y >= 180)angle.y -= 360;
				while(-180 > angle.z)angle.z += 360; 
				while(angle.z >= 180)angle.z -= 360;
				targetAngle.x = angle.x;
				targetAngle.y = angle.y;
				targetAngle.z = angle.z;
				isNeedRotatingAngle = false;
				break;
		}
	}
	
	public void snap(int element, float unitValue){
		switch(element){
			case PIVOT:
				snapElement3D(targetPivot, unitValue);
				isNeedMovingPivot = true;
				break;
			case POSITION:
				snapElement3D(targetPosition, unitValue);
				isNeedMovingPosition = true;
				break;
			case PIVOT_ANGLE:
				break;
			case OBJECT_ANGLE:
				snapElement3D(targetAngle, unitValue);
				isNeedRotatingAngle = true;
				break;
		}
	}

	private boolean moveToTarget(Element3D current, Element3D target){
		float dx = (target.x - current.x) * 0.2f;
		float dy = (target.y - current.y) * 0.2f;
		float dz = (target.z - current.z) * 0.2f;
		
		if(dx < 0){
			if(dx > -0.01f)dx = -0.01f;
		}else if(dx > 0){
			if(dx < 0.01f)dx = 0.01f;
		}
		
		if(dy < 0){
			if(dy > -0.01f)dy = -0.01f;
		}else if(dy > 0){
			if(dy < 0.01f)dy = 0.01f;
		}
		
		if(dz < 0){
			if(dz > -0.01f)dz = -0.01f;
		}else if(dz > 0){
			if(dz < 0.01f)dz = 0.01f;
		}
		
		current.x += dx;
		current.y += dy;
		current.z += dz;
		
		if(Math.abs(target.x - current.x) < 0.01f){
			current.x = target.x;
		}else{
			return false;
		}
		if(Math.abs(target.y - current.y) < 0.01f){
			current.y = target.y;
		}else{
			return false;
		}
		if(Math.abs(target.z - current.z) < 0.01f){
			current.z = target.z;
		}else{
			return false;
		}
		return true;
		
	}
	
	private boolean moveToTarget(Element3D current, Element3D target, float stopRange){
		float dx = (target.x - current.x) * 0.2f;
		float dy = (target.y - current.y) * 0.2f;
		float dz = (target.z - current.z) * 0.2f;
		
		if(dx < 0){
			if(dx > -stopRange)dx = -stopRange;
		}else if(dx > 0){
			if(dx < stopRange)dx = stopRange;
		}
		
		if(dy < 0){
			if(dy > -stopRange)dy = -stopRange;
		}else if(dy > 0){
			if(dy < stopRange)dy = stopRange;
		}
		
		if(dz < 0){
			if(dz > -stopRange)dz = -stopRange;
		}else if(dz > 0){
			if(dz < stopRange)dz = stopRange;
		}
		
		current.x += dx;
		current.y += dy;
		current.z += dz;
		
		if(Math.abs(target.x - current.x) < stopRange){
			current.x = target.x;
		}else{
			return false;
		}
		if(Math.abs(target.y - current.y) < stopRange){
			current.y = target.y;
		}else{
			return false;
		}
		if(Math.abs(target.z - current.z) < stopRange){
			current.z = target.z;
		}else{
			return false;
		}
		return true;
		
	}
	private void snapElement3D(Element3D element, float unitValue){
		if(element.x < 0){
			element.x = (int)(element.x / unitValue - 0.5f) * unitValue;
		}else{
			element.x = (int)(element.x / unitValue + 0.5f) * unitValue;
		}
		if(element.y < 0){
			element.y = (int)(element.y / unitValue - 0.5f) * unitValue;
		}else{
			element.y = (int)(element.y / unitValue + 0.5f) * unitValue;
		}
		if(element.z < 0){
			element.z = (int)(element.z / unitValue - 0.5f) * unitValue;
		}else{
			element.z = (int)(element.z / unitValue + 0.5f) * unitValue;
		}
		
	}

	
}
