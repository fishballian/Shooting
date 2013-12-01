package com.android13.shooting.screenItems;

import com.android13.shooting.Game;
import com.android13.shooting.R;
import com.android13.shooting.res.BitmapPool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * ÀºÈ¦£¬Singleton
 * @author Tiga <liangkangabc@gmail.com>
 *
 */
public class Hoop extends ScreenItem {
	
	private int currentFrame;
	
	private static Hoop instance;
	public static Hoop getInstance() {
		if(instance == null) {
			synchronized (Hoop.class) {
				if(instance == null)
					instance = new Hoop();
			}
		}
		return instance;
	}
	
	private Hoop() {
		this.x = Game.Constant.HOOP_X;
		this.y = Game.Constant.HOOP_Y;
		this.z = Game.Constant.FARTHEST - Game.Constant.HOOP_RADIUS;
		
		bmps = new Bitmap[5];
		for(int i = 0; i < 5; ++i)
			bmps[i] = BitmapPool.getBitmap(R.drawable.hoop00 + i);
		
		bmpWidth = bmps[0].getWidth();
		bmpHeight = bmps[0].getHeight();
		
		currentFrame = 0;
	}
	
	public void setFrame(int frame){
		currentFrame = frame;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bmps[currentFrame], x - bmpWidth / 2, y - bmpHeight / 2, paint);
	}
	
}
