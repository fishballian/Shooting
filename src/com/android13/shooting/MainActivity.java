package com.android13.shooting;

import android.os.Bundle;
import android.app.Activity;

/**
 * 游戏的入口Activity
 * @author Tiga <liangkangabc@gmail.com>
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Game.init(this);
		
		setContentView(new MainSurfaceView(this));
	}
}
