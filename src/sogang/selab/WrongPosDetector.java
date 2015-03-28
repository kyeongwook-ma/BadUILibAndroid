package sogang.selab;

import java.util.ArrayList;
import java.util.List;

import model.State;
import model.Transition;
import sogang.selab.db.BMDBscheme;
import sogang.selab.db.DBHelper;
import android.database.Cursor;


public class WrongPosDetector implements WrongPosDetectionService {

	@Override
	public boolean isWrongPosition(int timeThreshold, float targetX, float targetY, String target,
			String operation) {

		Cursor c = DBHelper.getInstance().getAllColumns(BMDBscheme.TABLE_NAME);

		List<Transition> bms = getAllBM(c);

		for(Transition t : bms) {
			if(isTargeted(target, t)) {
				
			}	
 		}

		return true;
	}
	
	private boolean isTargeted(String target, Transition t) {
		return t.getSrc().equals(target) || t.getDst().equals(target);
	}

	private List<Transition> getAllBM(Cursor c) {

		ArrayList<Transition> bm = new ArrayList<Transition>();
		
		while(c.moveToNext()) {
			int currState = c.getInt(c.getColumnIndex(BMDBscheme.COLUMN_ID));
			Transition transition = 
					new Transition.TransitionBuilder(
							State.newInstance(currState), State.newInstance(currState+1))
			.x(c.getDouble(c.getColumnIndex(BMDBscheme.COLUMN_X)))
			.y(c.getDouble(c.getColumnIndex(BMDBscheme.COLUMN_Y)))
			.target(c.getString(c.getColumnIndex(BMDBscheme.COLUMN_CLASS)))
			.timestamp(c.getDouble(c.getColumnIndex(BMDBscheme.COLUMN_TIMESTAMP)))
			.event(c.getString(c.getColumnIndex(BMDBscheme.COLUMN_MODE)))
			.createTransition();
			
			bm.add(transition);
		}

		return bm;
	}

	

	private boolean isDistracted(float targetX, float targetY, float actualX[], float actualY[]) {

		boolean isXTargeted = false, isYTargetd = false;

		isXTargeted = checkTargeted(targetX, actualX);
		isYTargetd = checkTargeted(targetY, actualY);

		return true;
	}

	private boolean checkTargeted(float target, float range[]) {
		float firstVal = range[0];
		float lastVal = range[range.length-1];

		if(firstVal <= target && target <= lastVal) {
			return true;
		}

		return false;
	}

}
