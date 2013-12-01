package com.android13.shooting;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class MainActivity extends Activity {
	private GestureDetector gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Game.init(this);
		
		setContentView(new MainSurfaceView(this));
		
		gestureDetector = new GestureDetector(new DefaultGestureDetector());
		
		
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event){
        return gestureDetector.onTouchEvent(event);
    }
	class DefaultGestureDetector extends SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
            final int FLING_MIN_VELOCITY=100;//x或者y轴上的移动速度(像素/秒)
            if(!Game.isFlying()&&Game.isInBall(e1.getRawX(),e1.getRawY())&&Math.abs(velocityY)>FLING_MIN_VELOCITY)
            {
            	Game.startBall(velocityX/150f, velocityY/70f);
            }
            return false;
        }
    }
	
}
