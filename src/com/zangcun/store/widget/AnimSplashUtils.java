package com.zangcun.store.widget;


import android.app.Activity;
import android.content.Context;
import android.view.animation.*;
import com.zangcun.store.R;

/**
 * 自定义控件的动画效果
 *
 */
public class AnimSplashUtils {

	public static LayoutAnimationController getListAnimTranslate() {
		AnimationSet set = new AnimationSet(true);
		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(500);
		set.addAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(800);
		set.addAnimation(animation);
		LayoutAnimationController controller = new LayoutAnimationController(
				set, 0.5f);

		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		return controller;
	}

	 //退出Activity的动画 : zoom 动画
	public static void finishActivityAnimation(Context context) {
		((Activity) context).finish();
		((Activity) context).overridePendingTransition(R.anim.zoom_enter,
				R.anim.zoom_exit);
	}

	public static void activityZoomAnimation(Context context) {
		((Activity) context).overridePendingTransition(R.anim.zoom_enter,
				R.anim.zoom_exit);
	}

}
