package com.android13.shooting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * 显示游戏主体画面的SurfaceView
 * @author Tiga <liangkangabc@gmail.com>
 *
 */
public class MainSurfaceView extends SurfaceView implements Callback, Runnable {
	
	private boolean flag;
	
	private SurfaceHolder surfaceHolder;
	private Canvas canvas;
	private Paint paint;
	
	private Thread thread;

	public MainSurfaceView(Context context) {
		super(context);
		
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		
		setKeepScreenOn(true);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}
	
	@Override
	public void run() {
		while(flag) {
			long start = System.currentTimeMillis();

			Game.refreshScreen(surfaceHolder, canvas, paint);
			
			long end = System.currentTimeMillis();
			try {
				//控制FPS 为标准的 30
				if(end - start < 34)
					Thread.sleep(34 - (end - start));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
