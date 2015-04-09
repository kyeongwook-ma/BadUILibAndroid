package sogang.selab;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import sogang.selab.db.BMDBscheme;
import sogang.selab.db.DBHelper;
import sogang.selab.model.AFSM;
import sogang.selab.model.Point;
import sogang.selab.model.State;
import sogang.selab.model.Transition;
import android.database.Cursor;

public class LogMonitor {

	private static ArrayList<Transition> transitions = new ArrayList<Transition>();
	private static ArrayList<AFSM> bms = new ArrayList<AFSM>();
	
	public static List<Transition> getAllTransition() {

		/* 비어있는 경우에 디비에서 추출하여 반환 */
		if(transitions.size() <= 0) {
			Cursor c = DBHelper.getInstance().getAllColumns(BMDBscheme.TABLE_NAME);

			while(c.moveToNext()) {
				Transition transition = assembleBM(c);
				transitions.add(transition);
			}
		}

		return transitions;
	}
	
	public static AFSM getUserBM(int userId) {
		List<AFSM> wholeBM = getAllBMs();
		
		for(AFSM afsm : wholeBM) {
			if(afsm.getUserId() == userId) {
				return afsm;
			}
		}
		
		return null;
	}
	
	public static List<AFSM> getAllBMs() {
		
		String sql = "SELECT * FROM " +
				BMDBscheme.TABLE_NAME + " GROUP BY " + BMDBscheme.COLUMN_ID + ";";
		
		/* 비어있는 경우에 디비에서 추출하여 반환 */
		if(bms.size() <= 0) {
			Cursor c = DBHelper.getInstance().get(sql);
		
			int currId = 1;
	
			while(c.moveToNext()) {
				int seqId = c.getInt(c.getColumnIndex(BMDBscheme.COLUMN_ID));

				AFSM bm = new AFSM();
				bm.setUserId(seqId);
				
				/* 현 seq와 일치하는 경우 Transition으로 추출 */
				if(currId == seqId) {
					currId = c.getInt(c.getColumnIndex(BMDBscheme.COLUMN_ID));
					Transition transition = assembleBM(c);
					bm.addStateSeq(transition);
				} 
				/* 다른 경우 Behavior model로 */
				else {
					bms.add(bm);
				}
			} 
		}
		
		return bms;	
	}

	private static Transition assembleBM(Cursor c) {

		String currState = c.getString(c.getColumnIndex(BMDBscheme.COLUMN_CLASS));

		if(c.moveToNext()) {
			String nextState = c.getString(c.getColumnIndex(BMDBscheme.COLUMN_CLASS));
			return assembleTransition(c, currState, nextState);
		}

		/* 마지막 Transition 의 경우 */
		else {
			c.moveToPrevious();
			return assembleTransition(c, currState, null);
		}
	}

	private static Transition assembleTransition(Cursor c, String currState,
			String nextState) {
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

	
}
