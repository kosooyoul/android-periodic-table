package net.ahyane.education.periodictable;

import java.util.HashMap;

import javax.microedition.khronos.opengles.GL11;

import android.opengl.GLES11;
import android.opengl.GLES11Ext;

import net.ahyane.renderbase.Layer;
import net.ahyane.renderbase.RenderView;
import net.ahyane.renderbase.StringTexture;
import net.ahyane.renderbase.RenderView.Lists;

public class FunctionLayer extends Layer{
	static final float FONT_RESOLUTION_RATIO = 2.0f;

	static final StringTexture.Config sConfigFunctionName = new StringTexture.Config();
	
	static{
		sConfigFunctionName.r = 0;
		sConfigFunctionName.g = 0;
		sConfigFunctionName.b = 0;
		sConfigFunctionName.a = 1.0f;
		sConfigFunctionName.fontSize = 28 * FONT_RESOLUTION_RATIO;
		sConfigFunctionName.bold = false;
		sConfigFunctionName.shadowRadius = 1;
		sConfigFunctionName.xalignment = StringTexture.Config.ALIGN_HCENTER;
	}
	
	static final String sFunctionNames[] = {
		"Details",
		"Favorit",
		"Search", //Search by WIKI, etc.
	};
	
	private HashMap<String, StringTexture> mFunctionNameTextureBox = new HashMap<String, StringTexture>();
	
	public FunctionLayer() {
		super();
	}

	@Override
	public void generate(RenderView view, Lists lists) {
		lists.blendedList.add(this);
		lists.hitTestList.add(this);
	}

	@Override
	public boolean containsPoint(float x, float y) {

		return false;
	}

	@Override
	public void renderBlended(RenderView view, GL11 gl) {
		GLES11.glDisable(GLES11.GL_TEXTURE_2D);
		GLES11.glColor4f(0.2f, 0.3f, 0.3f, 0.5f);
		GLES11Ext.glDrawTexfOES(0.0f, 0.0f, 0.0f, view.getWidth(), view.getHeight() / 6);

	}
	
	public void startShowAnimation(){
		
	}

	public void startHideAnimation(){
		
	}

	
}
