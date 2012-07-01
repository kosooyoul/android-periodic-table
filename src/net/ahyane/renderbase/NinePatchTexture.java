package net.ahyane.renderbase;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

public class NinePatchTexture extends CanvasTexture {
	private static Paint SRC_PAINT = new Paint();
	
	static{
		SRC_PAINT.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
	}
	
	private NinePatch mNinePatch = null;
	private Rect mRect = new Rect();
	
	public NinePatchTexture(Context context, int ninePatchImage) {
        super(Bitmap.Config.ARGB_8888);
    	Resources resources = context.getResources();
        Bitmap background = BitmapFactory.decodeResource(resources, ninePatchImage);
        mNinePatch = new NinePatch(background, background.getNinePatchChunk(), null);
	}
	
	@Override
	protected void renderCanvas(Canvas canvas, Bitmap backing, int width, int height) {
        // Draw the background.
        if(backing != null){
            backing.eraseColor(Color.TRANSPARENT);
        }

        mNinePatch.draw(canvas, mRect, SRC_PAINT);
    }

	@Override
	protected void onSizeChanged() {
		mRect.set(0, 0, Shared.nextPowerOf2(getWidth()), Shared.nextPowerOf2(getHeight()));			
	}
}
