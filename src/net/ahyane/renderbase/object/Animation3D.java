package net.ahyane.renderbase.object;

import android.opengl.GLES11;
import android.util.Log;

public class Animation3D {
	public static class Element3D{
		float x = 0.0f;
		float y = 0.0f;
		float z = 0.0f;
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
	
	public Animation3D() {
		super();
		pivot = new Element3D();
		targetPivot = new Element3D();
		position = new Element3D();
		targetPosition = new Element3D();
		angle = new Element3D();
		targetAngle = new Element3D();
	}

	public boolean isNeedPlay(){
		return isNeedMovingPivot || isNeedMovingPosition || isNeedRotatingAngle;
	}
	
	public void play(){
		if(isNeedMovingPivot){
			moveToTarget(pivot, targetPivot);
		}
		if(isNeedMovingPosition){
			moveToTarget(position, targetPosition);
		}
		if(isNeedRotatingAngle){
			moveToTarget(angle, targetAngle);
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
				break;
			case POSITION:
				snapElement3D(targetPosition, unitValue);
				break;
			case PIVOT_ANGLE:
				break;
			case OBJECT_ANGLE:
				snapElement3D(targetAngle, unitValue);
				break;
		}
	}

	private void moveToTarget(Element3D current, Element3D target){
		current.x += (target.x - current.x) * 0.2f;
		current.y += (target.y - current.y) * 0.2f;
		current.z += (target.z - current.z) * 0.2f;
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
