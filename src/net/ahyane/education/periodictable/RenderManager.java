package net.ahyane.education.periodictable;

import java.util.HashMap;

import net.ahyane.education.periodictable.PeriodicTable.Atom;
import net.ahyane.renderbase.RenderView;
import net.ahyane.renderbase.StringTexture;
import net.ahyane.renderbase.object.GLCube;
import net.ahyane.renderbase.object.GLObject;
import android.opengl.GLES11;

public class RenderManager {
	static final float FONT_RESOLUTION_RATIO = 2.0f;
	
	static final StringTexture.Config sConfigSign = new StringTexture.Config();
	static final StringTexture.Config sConfigNumber = new StringTexture.Config();
	static final StringTexture.Config sConfigWeight = new StringTexture.Config();
	static final StringTexture.Config sConfigName = new StringTexture.Config();
	
	static{
		sConfigSign.r = 0;
		sConfigSign.g = 0;
		sConfigSign.b = 0;
		sConfigSign.a = 1.0f;
		sConfigSign.fontSize = 28 * FONT_RESOLUTION_RATIO;
		sConfigSign.bold = false;
		sConfigSign.shadowRadius = 1;
		sConfigSign.xalignment = StringTexture.Config.ALIGN_HCENTER;

		sConfigNumber.r = 0;
		sConfigNumber.g = 0;
		sConfigNumber.b = 0;
		sConfigNumber.a = 1.0f;
		sConfigNumber.fontSize = 14 * FONT_RESOLUTION_RATIO;
		sConfigNumber.bold = false;
		sConfigNumber.shadowRadius = 1;
		sConfigNumber.xalignment = StringTexture.Config.ALIGN_HCENTER;
		
		sConfigWeight.r = 0;
		sConfigWeight.g = 0;
		sConfigWeight.b = 0;
		sConfigWeight.a = 1.0f;
		sConfigWeight.fontSize = 14 * FONT_RESOLUTION_RATIO;
		sConfigWeight.bold = false;
		sConfigWeight.shadowRadius = 1;
		sConfigWeight.xalignment = StringTexture.Config.ALIGN_HCENTER;

		sConfigName.r = 0;
		sConfigName.g = 0;
		sConfigName.b = 0;
		sConfigName.a = 1.0f;
		sConfigName.fontSize = 20 * FONT_RESOLUTION_RATIO;
		sConfigName.bold = false;
		sConfigName.shadowRadius = 1;
		sConfigName.xalignment = StringTexture.Config.ALIGN_HCENTER;
	}
	
	private HashMap<String, StringTexture> mSignTextureBox = new HashMap<String, StringTexture>();
	private HashMap<Integer, StringTexture> mNumberTextureBox = new HashMap<Integer, StringTexture>();
	private HashMap<String, StringTexture> mWeightTextureBox = new HashMap<String, StringTexture>();
	private HashMap<String, StringTexture> mNameTextureBox = new HashMap<String, StringTexture>();
	
	RenderView mRenderView;
	private GLCube mGLCube;
	private GLObject mGLObject;
	private float mRatio = 1.0f;

	public RenderManager(RenderView renderView) {
		super();
		mRenderView = renderView;
		mGLCube = new GLCube(renderView);
		mGLObject = new GLObject(renderView);
	}
	
	public void setRatio(float ratio) {
		mRatio = ratio;
	}
	
	public void drawAtom(Atom atom){
		if(atom != null){
			String sign = atom.sign;
			StringTexture signTexture;
			signTexture = mSignTextureBox.get(sign);
			if(signTexture == null){
				signTexture = new StringTexture(sign, sConfigSign);
				mRenderView.loadTexture(signTexture);
				mSignTextureBox.put(sign, signTexture);
			}

			int no = atom.no;
			StringTexture numberTexture;
			numberTexture = mNumberTextureBox.get(no);
			if(numberTexture == null){
				numberTexture = new StringTexture(String.valueOf(no), sConfigNumber);
				mRenderView.loadTexture(numberTexture);
				mNumberTextureBox.put(no, numberTexture);
			}

			String weight = atom.weight;
			StringTexture weightTexture;
			weightTexture = mWeightTextureBox.get(weight);
			if(weightTexture == null){
				weightTexture = new StringTexture(String.valueOf(weight), sConfigWeight);
				mRenderView.loadTexture(weightTexture);
				mWeightTextureBox.put(weight, weightTexture);
			}

			String name = atom.name;
			StringTexture nameTexture;
			nameTexture = mNameTextureBox.get(name);
			if(nameTexture == null){
				nameTexture = new StringTexture(String.valueOf(name), sConfigName);
				mRenderView.loadTexture(nameTexture);
				mNameTextureBox.put(name, nameTexture);
			}
			
			GLES11.glPushMatrix();
			{
				GLES11.glDisable(GLES11.GL_BLEND);
				atom.animation3d.translate();
				atom.animation3d.play();
				GLES11.glPushMatrix();
					GLES11.glScalef(0.95f, 0.95f, 0.95f);
					mGLCube.render();
				GLES11.glPopMatrix();
				GLES11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				GLES11.glEnable(GLES11.GL_BLEND);

				GLES11.glEnable(GLES11.GL_TEXTURE_2D);
				GLES11.glEnable(GLES11.GL_CULL_FACE);
				GLES11.glPushMatrix();
				{
					GLES11.glTranslatef(0.0f, 0.0f, 0.5f);
				
					if(mRenderView.bind(signTexture)){
						GLES11.glPushMatrix();
						{
							GLES11.glScalef((float)signTexture.getWidth() * mRatio / FONT_RESOLUTION_RATIO, (float)signTexture.getHeight() * mRatio / FONT_RESOLUTION_RATIO, 1.0f);
							mGLObject.render();
						}
						GLES11.glPopMatrix();
					}
					
					if(mRenderView.bind(numberTexture)){
						GLES11.glPushMatrix();
						{
							GLES11.glTranslatef((0.5f - 0.05f) - (float)numberTexture.getWidth() * mRatio * 0.5f / FONT_RESOLUTION_RATIO, 0.35f, 0.0f);
							GLES11.glScalef((float)numberTexture.getWidth() * mRatio / FONT_RESOLUTION_RATIO, (float)numberTexture.getHeight() * mRatio / FONT_RESOLUTION_RATIO, 1.0f);
							mGLObject.render();
						}
						GLES11.glPopMatrix();
					}
					
					if(mRenderView.bind(weightTexture)){
						GLES11.glPushMatrix();
						{
							GLES11.glTranslatef(0.0f, -0.35f, 0.0f);
							GLES11.glScalef((float)weightTexture.getWidth() * mRatio / FONT_RESOLUTION_RATIO, (float)weightTexture.getHeight() * mRatio / FONT_RESOLUTION_RATIO, 1.0f);
							mGLObject.render();
						}
						GLES11.glPopMatrix();
					}
				}
				GLES11.glPopMatrix();
				GLES11.glDisable(GLES11.GL_CULL_FACE);
				GLES11.glDisable(GLES11.GL_TEXTURE_2D);
			}
			GLES11.glPopMatrix();
		}
		
	}

	
	
}
