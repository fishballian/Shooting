package com.android13.shooting.screenItems;

import com.android13.shooting.Game;
import com.android13.shooting.R;
import com.android13.shooting.res.BitmapPool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 篮球
 * @author Tiga <liangkangabc@gmail.com>
 *
 */
public class Ball extends ScreenItem {
	
	private int currentFrame;
	private float speedX, speedY;
	
	public Ball() {
		this.x = Game.Constant.SCREEN_WIDTH / 2;
		this.y = Game.Constant.SCREEN_HEIGHT - Game.Constant.BALL_RADIUS;
		this.z = Game.Constant.NEAREST;
		
		bmps = new Bitmap[5];
		for(int i = 0; i < 5; ++i)
			bmps[i] = BitmapPool.getBitmap(R.drawable.ball000 + i);
		
		bmpWidth = bmps[0].getWidth();
		bmpHeight = bmps[0].getHeight();
		
		currentFrame = 0;
		
		speedX = speedY = 30;
	}
	
	@Override
	protected void logic() {
		/**
		 * 动画效果的一种实现方法(参考)
		 */
		if(x - Game.Constant.BALL_RADIUS <= 0 ||
				x + Game.Constant.BALL_RADIUS >= Game.Constant.SCREEN_WIDTH)
			speedX *= -1;
		if(y - Game.Constant.BALL_RADIUS <= 0 ||
				y + Game.Constant.BALL_RADIUS >= Game.Constant.SCREEN_HEIGHT)
			speedY *= -1;
		
		x += speedX;
		y += speedY;
		currentFrame = (currentFrame + 1) % 5;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		logic();
		canvas.drawBitmap(bmps[currentFrame], x - bmpWidth / 2, y - bmpHeight / 2, paint);
	}

}
