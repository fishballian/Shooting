package com.android13.shooting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.android13.shooting.res.BitmapPool;
import com.android13.shooting.screenItems.Backboard;
import com.android13.shooting.screenItems.Background;
import com.android13.shooting.screenItems.Ball;
import com.android13.shooting.screenItems.Hoop;
import com.android13.shooting.screenItems.ScreenItem;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;


/**
 * 游戏控制类，负责游戏整体的逻辑调度
 * @author Tiga <liangkangabc@gmail.com>
 *
 */
public class Game {
	
	/** 根据 z 坐标排序 */
	private static List<ScreenItem> sortedItems;
	private static Ball ball;
	private static Hoop hoop;
	public static void init(Activity activity) {
		
		/** 根据屏幕实际参数，初始化所有距离相关的常量 */
		DisplayMetrics dm = new DisplayMetrics();
	    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
	    Constant.init(dm.widthPixels, dm.heightPixels);
		
		/** 一次加载所有图片资源 */
		BitmapPool.loadAll(activity);
		
		sortedItems = new ArrayList<ScreenItem>();
		sortedItems.add(Background.getInstance());
		sortedItems.add(Backboard.getInstance());
		hoop = Hoop.getInstance();
		sortedItems.add(hoop);
		ball = new Ball();
		sortedItems.add(ball);
	}
	
	public static void refreshScreen(SurfaceHolder surfaceHolder, Canvas canvas,
			Paint paint) {
		
		gameLogic();
		
		try {
			canvas = surfaceHolder.lockCanvas();
			canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG |
					Paint.FILTER_BITMAP_FLAG));
			
			if(canvas != null) {
				for(ScreenItem item : sortedItems)
					item.draw(canvas, paint);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(canvas != null)
				surfaceHolder.unlockCanvasAndPost(canvas);
		}
	}
	
	private static void gameLogic() {
		/** 根据 z 坐标对 ScreenItem 排序 */
		Collections.sort(sortedItems);
	}
	
	public static boolean isFlying(){
		/**
		 * 球在飞行时不容许拨动球
		 */
		return ball.isStart();
	}
	public static void startBall(float x, float y) {
		/**
		 * 设置球飞出的初始速度
		 */
		ball.ballStart(x, y);
	}
	/**
	 * 定义了游戏的很多全局常量，如视野内三围维空间的坐标范围
	 */
	public static class Constant {
		
		private static void init(int sw, int sh) {
			SCREEN_WIDTH = sw;
			SCREEN_HEIGHT = sh;
			BACKBOARD_WIDHT = SCREEN_WIDTH / 2;
			BACKBOARD_HEIGHT = SCREEN_WIDTH / 3;
			BACKBOARD_X = SCREEN_WIDTH / 2;
			BACKBOARD_Y = SCREEN_HEIGHT / 3.5f;
			HOOP_WIDTH = BACKBOARD_WIDHT / 2.5f;
			HOOP_HEIGHT = HOOP_WIDTH;
			HOOP_X = SCREEN_WIDTH / 2;
			HOOP_Y = BACKBOARD_Y + BACKBOARD_HEIGHT / 2;
			HOOP_RADIUS = HOOP_WIDTH / 2;
			BALL_RADIUS = HOOP_RADIUS * 1.2f;
			FARTHEST = SCREEN_HEIGHT;
			NEAREST = 0;
		}
		
		public static float SCREEN_WIDTH, SCREEN_HEIGHT;
		public static float BACKBOARD_WIDHT, BACKBOARD_HEIGHT, BACKBOARD_X, BACKBOARD_Y;
		public static float HOOP_WIDTH, HOOP_HEIGHT, HOOP_X, HOOP_Y, HOOP_RADIUS;
		/**
		 * #Tips#
		 * 篮球在投向篮筐过程中，由于透视，在屏幕上看起来越来越小，BALL_RADIUS 为篮球投出前
		 * 显示的半径，透视效果应使得Ball在篮筐位置附近时半径小于Hoop的半径
		 */
		public static float BALL_RADIUS;
		/** 可见3维区域的 z 坐标范围，最远即为篮板的 z 坐标，最近即为篮球初始位置 */
		public static float FARTHEST, NEAREST;
		
	}
	
	public static boolean isInBall(float x, float y) {
		/**
		 * 如果坐标点落在ball上，返回true
		 */
		return (x-ball.getX())*(x-ball.getX())+(y-ball.getY())*(y-ball.getY())<Constant.BALL_RADIUS*Constant.BALL_RADIUS;
	}
	
	public static boolean isInBlackboard(float x, float y){
		/**
		 * 如果坐标点落在blackboard上，返回true
		 */
		return (Math.abs(x-Game.Constant.BACKBOARD_X)<Game.Constant.BACKBOARD_WIDHT/2f)
				 &&(Math.abs(y-Game.Constant.BACKBOARD_Y)<Game.Constant.BACKBOARD_HEIGHT/2f);
	}
	public static boolean isBallHitBlackboard(float x, float y, float ball_radius){
		return (Math.abs(x-Game.Constant.BACKBOARD_X)<Game.Constant.BACKBOARD_WIDHT/2f+ball_radius)
				 &&(Math.abs(y-Game.Constant.BACKBOARD_Y)<Game.Constant.BACKBOARD_HEIGHT/2f+ball_radius);
	}

	public static void setHoodFrame(int frame) {
		// TODO Auto-generated method stub
		hoop.setFrame(frame);
	}
}