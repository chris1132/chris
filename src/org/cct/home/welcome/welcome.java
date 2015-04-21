package org.cct.home.welcome;

import org.cct.home.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class welcome extends Activity implements AnimationListener {

	private ImageView imageView = null;
	private Animation alphaAnimation = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);

		imageView = (ImageView) findViewById(R.id.welcome_image_view);
		alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome);
		alphaAnimation.setFillEnabled(true); // 启动Fill保持
		alphaAnimation.setFillAfter(true); // 设置动画的最后一帧是保持在View上面
		imageView.setAnimation(alphaAnimation);
		alphaAnimation.setAnimationListener(this); // 为动画设置监听

		
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// 动画结束时结束欢迎界面并转到软件的主界面
		Intent intent = new Intent(this, home.class);
		startActivity(intent);
		this.finish();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

	
}
