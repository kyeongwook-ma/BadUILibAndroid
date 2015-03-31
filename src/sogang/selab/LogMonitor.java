package sogang.selab;

import java.util.ArrayList;
import java.util.List;

import sogang.selab.db.BMDBscheme;
import sogang.selab.db.DBHelper;
import sogang.selab.model.Point;
import sogang.selab.model.State;
import sogang.selab.model.Transition;
import android.database.Cursor;

public class LogMonitor {

	public static List<Transition> getAllBM() {
		Cursor c = DBHelper.getInstance().getAllColumns(BMDBscheme.TABLE_NAME);

		ArrayList<Transition> bm = new ArrayList<Transition>();

		while(c.moveToNext()) {
			Transition transition = assembleBM(c);
			bm.add(transition);
		}

		return bm;
	}

	private static Transition assembleBM(Cursor c) {

		String currState = c.getString(c.getColumnIndex(BMDBscheme.COLUMN_CLASS));

		if(c.moveToNext()) {
			String nextState = c.getString(c.getColumnIndex(BMDBscheme.COLUMN_CLASS));
			c.moveToPrevious();
			double x = c.getDouble(c.getColumnIndex(BMDBscheme.COLUMN_X));
			double y = c.getDouble(c.getColumnIndex(BMDBscheme.COLUMN_Y));

			Point p = new Point(x, y);
			Transition transition = 
					new Transition.TransitionBuilder(
							State.newInstance(currState), State.newInstance(nextState))
			.point(p)
			.target(c.getString(c.getColumnIndex(BMDBscheme.COLUMN_CLASS)))
			.timestamp(c.getDouble(c.getColumnIndex(BMDBscheme.COLUMN_TIMESTAMP)))
			.event(c.getString(c.getColumnIndex(BMDBscheme.COLUMN_MODE)))
			.createTransition();
			return transition;
		}

		else {
			c.moveToPrevious();
			double x = c.getDouble(c.getColumnIndex(BMDBscheme.COLUMN_X));
			double y = c.getDouble(c.getColumnIndex(BMDBscheme.COLUMN_Y));

			Point p = new Point(x, y);

			Transition transition = 
					new Transition.TransitionBuilder(
							State.newInstance(currState), State.newInstance(null))
			.point(p)
			.target(c.getString(c.getColumnIndex(BMDBscheme.COLUMN_CLASS)))
			.timestamp(c.getDouble(c.getColumnIndex(BMDBscheme.COLUMN_TIMESTAMP)))
			.event(c.getString(c.getColumnIndex(BMDBscheme.COLUMN_MODE)))
			.createTransition();
			return transition;
		}
	}

}
