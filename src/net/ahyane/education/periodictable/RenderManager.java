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
			//Get String Textures
			StringTexture signTexture = mSignTextureBox.get(atom.sign);
			StringTexture numberTexture = mNumberTextureBox.get(atom.no);
			StringTexture weightTexture = mWeightTextureBox.get(atom.weight);
			StringTexture nameTexture = mNameTextureBox.get(atom.name);
			
			//Make String Textures
			{
				if(signTexture == null){
					signTexture = new StringTexture(atom.sign, sConfigSign);
					mSignTextureBox.put(atom.sign, signTexture);
//					mRenderView.loadTexture(signTexture);
//					final StringTexture stringTexture = signTexture;
//					new Thread(new Runnable(){
//						@Override
//						public void run() {
//							mRenderView.loadTexture(stringTexture);
//						}
//					}).setPriority(Thread.MIN_PRIORITY);
				}
	
				if(numberTexture == null){
					numberTexture = new StringTexture(String.valueOf(atom.no), sConfigNumber);
					mNumberTextureBox.put(atom.no, numberTexture);
//					mRenderView.loadTexture(numberTexture);
//					final StringTexture stringTexture = numberTexture;
//					new Thread(new Runnable(){
//						@Override
//						public void run() {
//							mRenderView.loadTexture(stringTexture);
//						}
//					}).setPriority(Thread.MIN_PRIORITY);
				}
	
				if(weightTexture == null){
					weightTexture = new StringTexture(String.valueOf(atom.weight), sConfigWeight);
					mWeightTextureBox.put(atom.weight, weightTexture);
//					mRenderView.loadTexture(weightTexture);
//					final StringTexture stringTexture = weightTexture;
//					new Thread(new Runnable(){
//						@Override
//						public void run() {
//							mRenderView.loadTexture(stringTexture);
//						}
//					}).setPriority(Thread.MIN_PRIORITY);
				}
	
				if(nameTexture == null){
					nameTexture = new StringTexture(String.valueOf(atom.name), sConfigName);
					mNameTextureBox.put(atom.name, nameTexture);
//					mRenderView.loadTexture(nameTexture);
//					final StringTexture stringTexture = nameTexture;
//					new Thread(new Runnable(){
//						@Override
//						public void run() {
//							mRenderView.loadTexture(stringTexture);
//						}
//					}).setPriority(Thread.MIN_PRIORITY);
				}
			}
			
			//Rendering
			GLES11.glPushMatrix();
			{
				atom.animation3d.translate();
				GLES11.glPushMatrix();
					GLES11.glScalef(0.95f, 0.95f, 0.95f);
					mGLCube.renderWire();
					switch(atom.attribute){
						case PeriodicTable.G1:GLES11.glColor4f(1.0f, 0.5f, 0.0f, 0.75f);break;
						case PeriodicTable.G2:GLES11.glColor4f(1.0f, 0.75f, 0.0f, 0.75f);break;
						case PeriodicTable.GTM:GLES11.glColor4f(1.0f, 0.7f, 0.5f, 0.5f);break;
						case PeriodicTable.GM:GLES11.glColor4f(1.0f, 0.55f, 0.4f, 0.75f);break;
						case PeriodicTable.GML:GLES11.glColor4f(0.5f, 0.0f, 1.0f, 0.75f);break;
						case PeriodicTable.GNM:GLES11.glColor4f(0.8f, 0.25f, 0.75f, 0.75f);break;
						case PeriodicTable.GH:GLES11.glColor4f(0.0f, 0.75f, 0.0f, 0.75f);break;
						case PeriodicTable.G18:GLES11.glColor4f(0.0f, 0.75f, 1.0f, 0.75f);break;
						default:GLES11.glColor4f(0.87f, 0.93f, 0.95f, 0.75f);
					}
					mGLCube.renderFace();
				GLES11.glPopMatrix();
				GLES11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

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
