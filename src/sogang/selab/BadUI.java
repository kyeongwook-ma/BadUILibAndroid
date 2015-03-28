package sogang.selab;
import android.app.Application;
import android.content.Context;


public class BadUI extends Application{
	private static Context context;

	@Override
	public void onCreate() {
		context = this;
	}

	public static Context getContext() {
		return context;
	}
}
