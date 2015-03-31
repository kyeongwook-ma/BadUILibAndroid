package sogang.selab;
import sogang.dev.baduilib.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;


public class WrongPosEffector {

	public static void relocateGUI(Activity baseActivity, Object GUIObj, Class clazz) {		
		View v = (View) clazz.cast(GUIObj);
		ViewGroup parent = (ViewGroup) v.getParent();
		parent.removeView(v);
		
		LayoutInflater li = (LayoutInflater) BadUI.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout baseView  = (LinearLayout) li.inflate(R.layout.base, null);
		baseView.addView(v);
	
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);		
		baseActivity.addContentView(baseView, lp);
	}

}
