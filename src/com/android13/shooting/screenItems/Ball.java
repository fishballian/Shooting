package com.android13.shooting.screenItems;

import com.android13.shooting.Game;
import com.android13.shooting.R;
import com.android13.shooting.res.BitmapPool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * 篮球
 * @author Tiga <liangkangabc@gmail.com>
 *
 */
public class Ball extends ScreenItem {
	
	private int currentFrame,hoodFrame;
	private float speedX, speedY, speedZ;
	private boolean start,isWin;
	private int time;
	private float newscale;
	
	public Ball() {
		reset();
		
		bmps = new Bitmap[5];
		for(int i = 0; i < 5; ++i)
			bmps[i] = BitmapPool.getBitmap(R.drawable.ball000 + i);
		
		bmpWidth = bmps[0].getWidth();
		bmpHeight = bmps[0].getHeight();
		
		
	}
	public void reset(){
		speedX = speedY = 0;
		this.x = Game.Constant.SCREEN_WIDTH / 2;
		this.y = Game.Constant.SCREEN_HEIGHT - Game.Constant.BALL_RADIUS;
		this.z = Game.Constant.NEAREST;
		start = false;
		isWin = false;
		time = 0;
		currentFrame = 0;
		hoodFrame = 0;
		speedZ=(Game.Constant.FARTHEST-Game.Constant.NEAREST)/55f;
		newscale = 1;
	}
	@Override
	protected void logic() {
		/**
		 * 动画效果的一种实现方法(参考)
		 */
		if(start)
			{
			
			// 计算缩放倍数
			float baze_z = 2f*(0.5f*Game.Constant.FARTHEST-Game.Constant.NEAREST);
			newscale = (baze_z+Game.Constant.NEAREST)/(baze_z+z);
			newscale = 0.8f*newscale*newscale-0.2f*newscale+0.4f;
			
				
			
			
			x += speedX;
			y += speedY;
			if(z>-200)
				z += speedZ;
			
			currentFrame = (currentFrame + 1) % 5;
			time++;
			if (time>150)
				reset();
			if(x - Game.Constant.BALL_RADIUS <= -4*Game.Constant.BALL_RADIUS ||
					x - 3*Game.Constant.BALL_RADIUS >= Game.Constant.SCREEN_WIDTH)
				reset();
			
			
			// 重力逻辑、地面碰撞
			if(y + Game.Constant.BALL_RADIUS*newscale >= Game.Constant.SCREEN_HEIGHT*(1-0.32f*(1-newscale)))
				if(Math.abs(speedY)<0.5f)
					speedY=0f;
				else
					speedY = -0.8f*Math.abs(speedY);
			else
				speedY+=5f;//重力
			
			//篮筐碰撞
			float x1 = Game.Constant.HOOP_X-Game.Constant.HOOP_RADIUS;
			float z1 = Game.Constant.FARTHEST-Game.Constant.HOOP_RADIUS;
			ballHitHoop(x1,z1);
			
			float x2 = Game.Constant.HOOP_X;
			float z2 = Game.Constant.FARTHEST-2f*Game.Constant.HOOP_RADIUS;
			ballHitHoop(x2,z2);
			
			float x3 = Game.Constant.HOOP_X+Game.Constant.HOOP_RADIUS;
			float z3 = Game.Constant.FARTHEST-Game.Constant.HOOP_RADIUS;
			ballHitHoop(x3,z3);
			
			//篮板碰撞
			if((Game.Constant.FARTHEST-z-Game.Constant.BALL_RADIUS*newscale)<=0f
					&&(Game.Constant.FARTHEST-z-Game.Constant.BALL_RADIUS*newscale)>=-(Game.Constant.FARTHEST-Game.Constant.NEAREST)/30f
					&&Game.isBallHitBlackboard(x, y, Game.Constant.BALL_RADIUS*newscale))
			{
				speedZ *= -0.8f;
				speedX *= 0.8f;
				speedY *= 0.8f;
			}
			
			
			float difx = x-Game.Constant.HOOP_X;
			float dify = y-Game.Constant.HOOP_Y;
			float difz = z-(Game.Constant.FARTHEST-Game.Constant.HOOP_RADIUS);
			float new_ball_radius = Game.Constant.BALL_RADIUS*newscale;
			if(difx*difx+dify*dify+difz*difz<new_ball_radius*new_ball_radius*new_ball_radius*0.8f)
				isWin = true;
			if (isWin&&hoodFrame<5)
				Game.setHoodFrame(++hoodFrame%5);
		}
	}
	
	public void ballHitHoop(float hoop_point_x, float hoop_point_z){
		float difx = x-hoop_point_x;
		float dify = y-Game.Constant.HOOP_Y;
		float difz = z-hoop_point_z;
		float new_ball_radius = Game.Constant.BALL_RADIUS*newscale;
		if((difx*difx+dify*dify+difz*difz<new_ball_radius*new_ball_radius*new_ball_radius)
				&&(Math.abs(speedY+y-Game.Constant.HOOP_Y)<Math.abs(y-Game.Constant.HOOP_Y)
				  ||Math.abs(speedX+x-hoop_point_x)<Math.abs(x-hoop_point_x)
				  ||Math.abs(speedZ+z-hoop_point_z)<Math.abs(z-hoop_point_z)))
		{
			double vpow2 = (speedX*speedX + speedY*speedY + speedZ*speedZ)*0.8f;
			float y_byk = (y - Game.Constant.HOOP_Y)/(z-hoop_point_z);
			float x_byk = (x - hoop_point_x)/(z-hoop_point_z);
			double new_vpow2_byk = 1 + y_byk*y_byk + x_byk*x_byk;
			speedZ = (float) ((-speedZ/Math.abs(speedZ))*Math.sqrt(vpow2/new_vpow2_byk));
			speedY = (y_byk*speedZ);
			speedX = (x_byk*speedZ);
			Log.d("call",String.valueOf(hoop_point_x)+" "+String.valueOf(hoop_point_z));
		}
		   
	}

	
	@Override
	public void draw(Canvas canvas, Paint paint) {
		
		logic();
		canvas.save();
	   
	    canvas.scale(newscale, newscale,x,y);
	    
		canvas.drawBitmap(bmps[currentFrame], x - bmpWidth / 2, y - bmpHeight / 2, paint);
		canvas.restore();
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	public void ballStart(float x, float y){
		speedX = x;
		speedY = y;
		start = true;
	}
	public boolean isStart() {
		// TODO Auto-generated method stub
		return start;
	}
	
}
