package com.android13.shooting.screenItems;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * �����ڻ�����չʾ��Item����Ҫ�̳иó����࣬
 * �����ϵ�Item���� z ��������洢��Game.sortedItems��
 * @author Tiga <liangkangabc@gmail.com>
 *
 */
public abstract class ScreenItem implements Comparable<ScreenItem> {
	
	/** Item �� Bitmap �������� 3 ά����ϵ�е����� */
	protected float x, y, z;
	
	/** Item �� Bitmap �ĳߴ� */
	protected int bmpWidth, bmpHeight;
	
	/** Item ��Ӧ�� Bitmap�������ж�֡ */
	protected Bitmap[] bmps;
	
	/** ÿ�� Item �����Լ����߼�(�ı�λ�ã��ı����ų߶ȵȵ�)
	  * �ͻ�ͼ(������)���� */
	protected void logic() {}
	
	public abstract void draw(Canvas canvas, Paint paint);
	
	/** ����Ļ�ϻ�ͼ��Ӧ�Ȼ� z ����ϴ�� */
	@Override
	public int compareTo(ScreenItem another) {
		if(z > another.z) return -1;
		else if(z < another.z) return 1;
		return 0;
	}
}
