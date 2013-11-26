package com.android13.shooting.res;

import com.android13.shooting.Game;
import com.android13.shooting.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;

/**
 * ������Ϸ��λͼ��Դ
 * @author Tiga <liangkangabc@gmail.com>
 *
 */
public class BitmapPool {
	
	private static SparseArray<Bitmap> pool = null;
	
	public static void loadAll(Context context) {
		pool = new SparseArray<Bitmap>();
		
		Resources res = context.getResources();
		/**������ԭͼ���ٸ�����Ļ���������ţ�������pool�� */
		///����
		Bitmap bb = BitmapFactory.decodeResource(res, R.drawable.backboard);
		pool.put(R.drawable.backboard, Bitmap.createScaledBitmap(bb,
				(int)Game.Constant.BACKBOARD_WIDHT, (int)Game.Constant.BACKBOARD_HEIGHT, true));
		bb.recycle();
		bb = null;
		///����
		Bitmap bg = BitmapFactory.decodeResource(res, R.drawable.background);
		pool.put(R.drawable.background, Bitmap.createScaledBitmap(bg,
				(int)Game.Constant.SCREEN_WIDTH, (int)Game.Constant.SCREEN_HEIGHT, true));
		bg.recycle();
		bg = null;
		///����
		for(int i = 0; i < 5; ++i) {
			Bitmap hoop = BitmapFactory.decodeResource(res, R.drawable.hoop00 + i);
			pool.put(R.drawable.hoop00 + i, Bitmap.createScaledBitmap(hoop,
					(int)Game.Constant.HOOP_WIDTH, (int)Game.Constant.HOOP_HEIGHT, true));
			hoop.recycle();
			hoop = null;
		}
		for(int i = 0; i < 5; ++i) {
			Bitmap ball = BitmapFactory.decodeResource(res, R.drawable.ball000 + i);
			pool.put(R.drawable.ball000 + i, Bitmap.createScaledBitmap(ball,
					(int)Game.Constant.BALL_RADIUS * 2, (int)Game.Constant.BALL_RADIUS * 2, true));
			ball.recycle();
			ball = null;
		}
	}
	
	public static Bitmap getBitmap(int what) {
		if(null == pool) return null;
		return pool.get(what);
	}
}
