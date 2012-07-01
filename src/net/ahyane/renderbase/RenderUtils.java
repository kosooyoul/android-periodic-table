package net.ahyane.renderbase;

import javax.microedition.khronos.opengles.GL11;


public class RenderUtils {
	private static final float constantDefaultDepth = -1.0f;
	private static final float constantForRadian45 = -constantDefaultDepth * 100f / 120f;
	//private static final float constantForRadian60 = -constantDefaultDepth * 100f / 72f;	//fovy 60일 경우 사용
	
	private static float pixelMatrix[] = null;
	
	public static void setPixelCoordinate(RenderView view, GL11 gl){
		gl.glLoadIdentity();
		if(pixelMatrix == null){
			gl.glTranslatef(0.0f, 0.0f, constantDefaultDepth);
			gl.glScalef(
				1.0f / (float)view.getHeight() * constantForRadian45,
				1.0f / (float)view.getHeight() * constantForRadian45,
				1.0f / (float)view.getHeight() * constantForRadian45
			);
			//여기까지 픽셀 좌표계에 중앙
			gl.glTranslatef(-(float)view.getWidth() * 0.5f, (float)view.getHeight() * 0.5f, 0.0f);
			//여기까지 픽셀 좌표계에 좌측상단
			
			//현재 매트릭스를 저장함
			//pixelMatrix = new float[16];
			//MatrixGrabber.getCurrentModelView(gl, pixelMatrix);
		}else{
			//저장해둔 매트릭스로 변환
			((GL11)gl).glLoadMatrixf(pixelMatrix, 0);
		}
	}
	
	public static void setPixelCoordinate(RenderView view, GL11 gl, boolean center){
		gl.glLoadIdentity();
		if(pixelMatrix == null){
			gl.glTranslatef(0.0f, 0.0f, constantDefaultDepth);
			gl.glScalef(
				1.0f / (float)view.getHeight() * constantForRadian45,
				1.0f / (float)view.getHeight() * constantForRadian45,
				1.0f / (float)view.getHeight() * constantForRadian45
			);
			//여기까지 픽셀 좌표계에 중앙
			if(!center){
				gl.glTranslatef(-(float)view.getWidth() * 0.5f, (float)view.getHeight() * 0.5f, 0.0f);
				//여기까지 픽셀 좌표계에 좌측상단
			}
		}
	}
	
	public static void m3dFrustumEx(float[] matrix, float left, float right, float bottom, float top, float nearZ, float farZ, float x_offset, float y_offset)
	{
		float deltaX = right - left;
		float deltaY = top - bottom;
		float deltaZ = farZ - nearZ;

		matrix[0*4+0] = 2.0f * nearZ / deltaX;
		matrix[0*4+1] = matrix[0*4+2] = matrix[0*4+3] = 0.0f;

		matrix[1*4+1] = 2.0f * nearZ / deltaY;
		matrix[1*4+0] = matrix[1*4+2] = matrix[1*4+3] = 0.0f;

		matrix[2*4+0] = (right + left - 2.0f * x_offset) / deltaX;
		matrix[2*4+1] = (top + bottom - 2.0f * y_offset) / deltaY;
		matrix[2*4+2] = -(nearZ + farZ) / deltaZ;
		matrix[2*4+3] = -1.0f;

		matrix[3*4+2] = -2.0f * nearZ * farZ / deltaZ;
		matrix[3*4+0] = -(2.0f / deltaX) * nearZ * x_offset;
		matrix[3*4+1] = -(2.0f / deltaY) * nearZ * y_offset;
		matrix[3*4+3] = 0.0f;
	}
	
	public static boolean isPerspective = true;
	
	public static final void setPerspective(RenderView view, GL11 gl){
		if(isPerspective)return;
		
		gl.glMatrixMode(GL11.GL_PROJECTION);
		gl.glLoadIdentity();
		
		//TODO:Perspective를 중앙으로 위치시킬 때 사용
		//GLU.gluPerspective(gl, 45.0f, (float) view.getWidth() / view.getHeight(), 0.1f, 10.0f);
		//gl.glTranslatef(0.0f, -0.5f, 0.0f);
		//gl.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
		
		float[] matrix = new float[16];
		float ratio = (float) view.getWidth() / view.getHeight();
		RenderUtils.setPixelCoordinate(view, gl);
		
		RenderUtils.m3dFrustumEx(matrix, -ratio * 0.125f, ratio * 0.125f, 0.17f * 0.5f, 0.67f * 0.5f, 0.3f, 10.0f, 0.0f, 0.30f);
		gl.glLoadMatrixf(matrix, 0);
		gl.glMatrixMode(GL11.GL_MODELVIEW);
		
		isPerspective = true;
	}
	
	public static final void setOrtho(RenderView view, GL11 gl){
		if(!isPerspective)return;
		
		float ratioHHalf = constantForRadian45 * 0.5f;
		float ratioWHalf = (float)view.getWidth() / view.getHeight() * ratioHHalf;
		gl.glMatrixMode(GL11.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(-ratioWHalf, ratioWHalf, -ratioHHalf, ratioHHalf, 0.1f, 10.0f);
		gl.glMatrixMode(GL11.GL_MODELVIEW);
		
		isPerspective = false;
	}

	//line intersection
	public static boolean isIntersection(int lineAX1, int lineAY1, int lineAX2, int lineAY2, int lineBX1, int lineBY1, int lineBX2, int lineBY2){
        if((isDirection(lineAX1, lineAY1, lineAX2, lineAY2, lineBX1, lineBY1) * isDirection(lineAX1, lineAY1, lineAX2, lineAY2, lineBX2, lineBY2) <= 0)
        && (isDirection(lineBX1, lineBY1, lineBX2, lineBY2, lineAX1, lineAY1) * isDirection(lineBX1, lineBY1, lineBX2, lineBY2, lineAX2, lineAY2) <= 0)){
            return true;
        }else{
            return false;
        }
	}

	//for line intersection
	private static final int isDirection(int x1, int y1, int x2, int y2, int x3, int y3){
		int dxAB = x2 - x1, dyAB = y2 - y1;
		int dxAC = x3 - x1, dyAC = y3 - y1;
		int dir;
		if(dxAB * dyAC < dyAB * dxAC){dir = 1;}
		else if(dxAB * dyAC > dyAB * dxAC){dir = -1;}
		else{
			if(dxAB == 0 && dyAB == 0) dir = 0;
			else if((dxAB * dxAC < 0) || (dyAB * dyAC < 0)){dir = -1;}
			else if((dxAB + dyAB) >= (dxAC + dyAC)){dir = 0;}
			else{dir = 1;}
		}
		return dir;
	}
	
}
