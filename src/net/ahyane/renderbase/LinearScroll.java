package net.ahyane.renderbase;

public class LinearScroll {
	private static final float FACTOR_MOVE_TO_TARGET = 0.2f;
    private static final float FACTOR_DRAGING = 0.9f;
    private static final float DECREMENT_DRAGING_OUT_OF_RANGE = 0.5f;
    private static final float DECREMENT_FLING_SIZE = 0.5f;
    private static final float DECREMENT_FLING_IN_OF_RANGE = 0.95f;
    private static final float DECREMENT_FLING_OUT_OF_RANGE = 0.5f;
    private static final float FACTOR_BOUNCED = 0.2f;
    private static final float STOP_RANGE_SCROLLED_SIZE = 1.0f;
    
    protected boolean isBounced = false;
	private boolean isTouchDowned  = false;
	private boolean isArrived = true;

	private float mTouchedPosition;
	private float mOldTouchedPosition;
	private float mScrolledSize;
	private float mOldScrolledSize;
	private float mScrolledPosition;
	private float mTargetPosition;
	
	private Layer mLayer = null;
	private float mLayerSize = 1;
	private float mScrollerSize = 1;
	private OnScrollingListener mOnScrollingListener = null;
	
    private float mStartMargin = 0;
    private float mEndMargin = 0;
	
    private float mStep = 0;
    private boolean isFactorMove = false;
    
    private boolean isLoop = false;
    
	public LinearScroll() {
		super();
	}
	
	public LinearScroll(Layer layer) {
		mLayer = layer;
	}
	
	public LinearScroll(Layer layer, int scrolledPosition) {
		mLayer = layer;
		mScrolledPosition = -scrolledPosition;
	}

	public interface OnScrollingListener{
		public void onScroll(Layer layer, float scrolledPosition);
		public void onEndScroll(Layer layer, float scrolledPosition);
	}	
	
	public void setOnScrollingListener(OnScrollingListener listener){
		mOnScrollingListener = listener;
	}
	
    public float getScrolledPosition(){
    	return mScrolledPosition;
    }
    
    public void setSize(float scrollerSize){
    	mScrollerSize = scrollerSize;
    }
    
    public void setLayer(Layer layer){
    	mLayer = layer;
    }
    
    public void setLayerSize(float layerSize){
    	mLayerSize = layerSize;
    }
    
    public void setMargin(float startMargin, float endMargin){
    	mStartMargin = startMargin;
    	mEndMargin = endMargin;
    }

    public void setStep(float step){
    	mStep = step;
    }
    
    public boolean isStep(){
    	return mStep > 0;
    }
    
	public void touchDown(int touchedPosition){
		isTouchDowned = true;
		isFactorMove = false;
		mTouchedPosition = mOldTouchedPosition = touchedPosition;
		
        // Check Bounce
        if (isOutOfStartRange()) {
        	mScrolledPosition = -getStartPosition();
        	mOldScrolledSize = 0;
        }else if (isOutOfEndRange()) {
        	mScrolledPosition = -getEndPosition();
        	mOldScrolledSize = 0;
        }
	}
	
	public void touchMove(int touchedPosition){
		mTouchedPosition = touchedPosition;
	}
	
	public void touchUp(int touchedPosition){
		isTouchDowned = false;
		isArrived = false;
		mTouchedPosition = mOldTouchedPosition = touchedPosition;
	}
	
	public void play() {
        if(isTouchDowned){
        	drag();
        	isArrived = true;
        }else if(isFactorMove){
        	isArrived = false;
        	mScrolledPosition += (mTargetPosition - mScrolledPosition) * FACTOR_MOVE_TO_TARGET;
        	if(Math.abs(mTargetPosition - mScrolledPosition) < STOP_RANGE_SCROLLED_SIZE){
        		mScrolledPosition = mTargetPosition;
        		isFactorMove = false;
        		isArrived = true;
        	}
        }else{
        	fling();
            if(!isOutOfStartRange() && !isOutOfEndRange()){
    	    	if(Math.abs(mOldScrolledSize) < STOP_RANGE_SCROLLED_SIZE){
    	    		mOldScrolledSize = 0;
    	    		isArrived = true;
    	            if(mOnScrollingListener != null){
   	            		mOnScrollingListener.onEndScroll(mLayer, mScrolledPosition);
    	            }
    	    	}
        	}else{
            	isArrived = false;
        	}
        }
        if(mOnScrollingListener != null){
       		mOnScrollingListener.onScroll(mLayer, mScrolledPosition);
        }
    }
    
    private void drag(){
    	float scrolledSize = mTouchedPosition - mOldTouchedPosition;
        mScrolledSize += (scrolledSize - mScrolledSize) * FACTOR_DRAGING;

    	mOldScrolledSize = mScrolledSize;
        
        mOldTouchedPosition = mTouchedPosition;
        mScrolledPosition += mScrolledSize; 

        // Out of range
        if (isOutOfStartRange() || isOutOfEndRange()) {
        	mScrolledPosition -= mScrolledSize * (1.0f - DECREMENT_DRAGING_OUT_OF_RANGE);
        	bounced();
//       	// Out of limited range
//        }else if (isOutOfLimitedStartRange() || isOutOfLimitedEndRange()){
        // In of range
        }else{
        	resetBounced();
        }
    }
    
    private void fling(){
        mScrolledPosition += mOldScrolledSize * DECREMENT_FLING_SIZE;
        mOldScrolledSize *= DECREMENT_FLING_IN_OF_RANGE;
        
        if(isStep() && !isArrived){
        	if(Math.abs(mOldScrolledSize) < mStep * 0.1f){
        		setTarget(-Math.round(mScrolledPosition / mStep) * mStep);
        	}
        }
        
        // Bounced
        if (isBounced){
            if (isOutOfStartRange()) {
                mScrolledPosition += (-getStartPosition() - mScrolledPosition) * FACTOR_BOUNCED;
            } else if (isOutOfEndRange()) {
                mScrolledPosition += (-getEndPosition() - mScrolledPosition) * FACTOR_BOUNCED;
            }
        // Not bounced
        } else {
            if (isOutOfStartRange()) {
                mOldScrolledSize *= DECREMENT_FLING_OUT_OF_RANGE;
                
                // Check Bounce
				if(Math.abs(mOldScrolledSize) < STOP_RANGE_SCROLLED_SIZE){
					bounced();
				}
            } else if (isOutOfEndRange()) {
                mOldScrolledSize *= DECREMENT_FLING_OUT_OF_RANGE;
                // Check Bounce
                if(Math.abs(mOldScrolledSize) < STOP_RANGE_SCROLLED_SIZE){
                	bounced();
				}
            }
        }
        
        // Check Bounce
        if (isOutOfLimitedStartRange()) {
        	//mScrolledPosition = -getLimitedStartPosition();
        	bounced();
        }else if (isOutOfLimitedEndRange()) {
        	//mScrolledPosition = -getLimitedEndPosition();
        	bounced();
        }
        
        if(isLoop){
        	if(mScrolledPosition < -mLayerSize){
        		mScrolledPosition += mLayerSize;
        	}else if(mScrolledPosition > mLayerSize){
        		mScrolledPosition -= mLayerSize;
        	}
        }
    }

    private void resetBounced(){
    	isBounced = false;
    }
    
    private void bounced(){
    	mOldScrolledSize = 0;
		isBounced = true;
    }

    private float getStartPosition(){
    	return (0) - mStartMargin;
    }
    
    private float getEndPosition(){
    	return (mLayerSize - mScrollerSize) + mEndMargin;
    }
    
    private float getLimitedStartPosition(){
    	return getStartPosition() - mScrollerSize / 2;
    }
    
    private float getLimitedEndPosition(){
    	return getEndPosition() + mScrollerSize / 2;
    }
    
    private boolean isOutOfStartRange(){
    	return (mScrolledPosition > -getStartPosition()) && !isLoop;
    }
    
    private boolean isOutOfEndRange(){
    	return (mScrolledPosition < -getEndPosition()) && !isLoop;
    }
    
    private boolean isOutOfStartRange(float position){
    	return (position > -getStartPosition()) && !isLoop;
    }
    
    private boolean isOutOfEndRange(float position){
    	return (position < -getEndPosition()) && !isLoop;
    }
    
    private boolean isOutOfLimitedStartRange(){
    	return (mScrolledPosition > -getLimitedStartPosition()) && !isLoop;
    }
    
    private boolean isOutOfLimitedEndRange(){
    	return (mScrolledPosition < -getLimitedEndPosition()) && !isLoop;
    }
    
    public boolean isArrived(){
    	return isArrived;
    }
    
    public float getScrolledRatio(){
    	return -((float)(mScrolledPosition - mStartMargin) / (getEndPosition() - getStartPosition())); 
    }
    
    public void setLoop(boolean flag){
    	isLoop = flag;
    }
    
    public boolean isLoop(){
    	return isLoop;
    }
    
    
    
    public void setTarget(float targetPosition){
    	if(isOutOfStartRange()){
    		//mScrolledPosition = -getStartPosition();
    		//return;
    	}else if(isOutOfEndRange()){
    		//mScrolledPosition = -getEndPosition();
    		//return;
    	}
    	mOldScrolledSize = 0;//(int)(-targetPosition - mScrolledPosition);
    	//mScrolledPosition = 0;
    	
    	
    	isFactorMove = true;
    	mTargetPosition = -targetPosition;
    	
//    	if(isLoop){
//			if((mTargetPosition - mScrolledPosition) > mLayerSize * 0.5){
//				mScrolledPosition -= mLayerSize;
//			}else if((mTargetPosition - mScrolledPosition) < -mLayerSize * 0.5){
//				mScrolledPosition += mLayerSize;
//			}
//    	}else
    	{
	    	if(isOutOfStartRange(mTargetPosition)){
	    		mTargetPosition = -getStartPosition();
	    	}else if(isOutOfEndRange(mTargetPosition)){
	    		mTargetPosition = -getEndPosition();
	    	}
    	}
    }
    
    public void setPosition(float position){
    	if(isOutOfStartRange()){
    		//mScrolledPosition = -getStartPosition();
    		//return;
    	}else if(isOutOfEndRange()){
    		//mScrolledPosition = -getEndPosition();
    		//return;
    	}
    	mScrolledSize = 0;
    	mOldScrolledSize = 0;//(int)(-targetPosition - mScrolledPosition);
    	mScrolledPosition = -position;
    	mTargetPosition = 0;
    }

    public boolean isFirstStep(){
    	return (mScrolledPosition + mStep * 0.5f > -getStartPosition()) && !isLoop;
    }

    public boolean isLastStep(){
    	return (mScrolledPosition - mStep * 0.5f < -getEndPosition()) && !isLoop;
    }
    
}
