package net.ahyane.renderbase;

public abstract class LayerAnimation {
	public Layer mLayer = null;
	private boolean isStarted = false;
		
	public void setLayer(Layer layer){
		mLayer = layer;
	}

	public boolean isStarted(){
		return isStarted;
	}
	
	abstract public void onStart();
	abstract public void onPlay(RenderView view, Layer layer);
	abstract public void onEnd();
	
	public void startAnimation(){
		isStarted = true;
	}
	
	public void endAnimation(){
		isStarted = false;
	}
}
