package sogang.selab;


import android.app.Activity;
import android.util.DisplayMetrics;

public class DisplayMeasure {
	
	public static int getDispWidth(Activity activity) {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		return displaymetrics.widthPixels;
	}
	
	public static int getDispHeight(Activity activity) {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		return displaymetrics.heightPixels;
	}
	
	
}
