package com.android13.shooting;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;

/**
 * 游戏的入口Activity
 * @author Tiga <liangkangabc@gmail.com>
 *
 */
public class ShootingActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 1000; // 延迟六秒

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent mainIntent = new Intent(ShootingActivity.this,
						MainActivity.class);
				ShootingActivity.this.startActivity(mainIntent);
				ShootingActivity.this.finish();
			}

		}, SPLASH_DISPLAY_LENGHT);

	}
}
