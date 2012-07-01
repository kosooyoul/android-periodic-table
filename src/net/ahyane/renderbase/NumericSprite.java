package net.ahyane.renderbase;

import java.util.HashMap;

import javax.microedition.khronos.opengles.GL11;

import net.ahyane.renderbase.object.GLObject;
import android.content.Context;

public class NumericSprite{
	private static final char[] sNumericSigns = "0123456789.#&-/\\!~@%".toCharArray();
    protected final HashMap<String, Texture> mTextureMap = new HashMap<String, Texture>();
    protected StringTexture.Config mConfig = null;
    private GLObject mGLObject;
    public void initialize(Context context, RenderView view, StringTexture.Config config){
    	mTextureMap.clear();
    	mConfig = config;

    	char[] numericSigns = sNumericSigns;
    	for(int i = 0; i < numericSigns.length; i++){
    		String c = String.valueOf(numericSigns[i]);
    		StringTexture texture = new StringTexture(
    			c,
    			config,
    			StringTexture.computeTextWidthForConfig(c, config),
    			config.height
    		);
    		texture.load(view);
    		mTextureMap.put(c, texture);
    	}
    	mGLObject = new GLObject(null);
    }
    
    public void release(){
    	mTextureMap.clear();
    }
    
    public void render(GL11 gl, RenderView view, int number){
    	render(gl, view, String.valueOf(number));
    }
    
    public void render(GL11 gl, RenderView view, float number){
    	render(gl, view, String.valueOf(number));
    }

	public void render(GL11 gl, RenderView view, String numericString){
    	float length = 0.0f;
    	gl.glDisable(GL11.GL_DEPTH_TEST);
    	for(int i = 0; i < numericString.length(); i++){
    		StringTexture texture = (StringTexture) mTextureMap.get(String.valueOf(numericString.charAt(i)));
    		if(texture != null){
    			gl.glPushMatrix();
	   				length += texture.mWidth + 1.0f;
	   				if(view.bind(texture)){
			    		gl.glTranslatef(length * 0.5f, -texture.mHeight * 0.5f, 0.0f);
		    			gl.glScalef(texture.mWidth, texture.mHeight, 1.0f);
		    			mGLObject.render(gl);
	   				}
		    		length += texture.mWidth * 0.5f;
		    	gl.glPopMatrix();
    		}
    	}
    	//gl.glEnable(GL11.GL_DEPTH_TEST);
	}
        
	public boolean isInitialized(){
		return !mTextureMap.isEmpty();
	}
    
	public float getTextureWidth(int number){
		if(mConfig != null){
			return StringTexture.computeTextWidthForConfig(String.valueOf(number), mConfig);
		}else{
			return 0.0f;
		}
		
	}

	public float getTextureWidth(String numericString){
		if(mConfig != null){
			return StringTexture.computeTextWidthForConfig(numericString, mConfig);
		}else{
			return 0.0f;
		}
	}
	
	public float getTextureHeight(){
		if(!mTextureMap.isEmpty()){
			StringTexture texture = (StringTexture)mTextureMap.get("0");
			return texture.mHeight;
		}else{
			return 0.0f;
		}
	}
}