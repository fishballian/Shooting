package com.android13.shooting.screenItems;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 所有在画面上展示的Item都需要继承该抽象类，
 * 画面上的Item按照 z 坐标有序存储在Game.sortedItems中
 * @author Tiga <liangkangabc@gmail.com>
 *
 */
public abstract class ScreenItem implements Comparable<ScreenItem> {
	
	/** Item 的 Bitmap 的中心在 3 维坐标系中的坐标 */
	protected float x, y, z;
	
	/** Item 的 Bitmap 的尺寸 */
	protected int bmpWidth, bmpHeight;
	
	/** Item 对应的 Bitmap，可能有多帧 */
	protected Bitmap[] bmps;
	
	/** 每个 Item 都有自己的逻辑(改变位置，改变缩放尺度等等)
	  * 和画图(画本身)函数 */
	protected void logic() {}
	
	public abstract void draw(Canvas canvas, Paint paint);
	
	/** 在屏幕上画图，应先画 z 坐标较大的 */
	@Override
	public int compareTo(ScreenItem another) {
		if(z > another.z) return -1;
		else if(z < another.z) return 1;
		return 0;
	}
}
