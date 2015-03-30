package sogang.selab;

import java.util.ArrayList;
import java.util.List;

import model.Point;
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

		Point points[] = getAllPoint(bms);
		double[] actualX = extractXPoint(points);
		double[] actualY = extractYPoint(points);
		
		for(int i = 1; i < bms.size(); i += 3) {
			Transition t = bms.get(i);
			Transition prevTransition = bms.get(i - 1);
			Transition nextTransition = bms.get(i + 1);

			if(t.equals(nextTransition) || t.equals(prevTransition)) {
				if(isDistracted(targetX, targetY, actualX, actualY))
					return true;
			}
		}

		return false;
	}
	
	private double[] extractXPoint(Point points[]) {
		
		double x[] = new double[points.length];
		
		for(int i = 0; i < points.length; ++i) {
			x[i] = points[i].getX();
		}
		
		return x;
	}
	
	private double[] extractYPoint(Point points[]) {
		
		double y[] = new double[points.length];
		
		for(int i = 0; i < points.length; ++i) {
			y[i] = points[i].getY();
		}
		
		return y;
	}
	
	private Point[] getAllPoint(List<Transition> bms) {
		
		int bmsSize = bms.size();
		
		Point points[] = new Point[bms.size()];
		
		for(int i = 0; i < bmsSize; ++i) {
			points[i] = bms.get(i).getTouchPoint();
		}
		
		return points;
	}

	private List<Transition> getAllBM(Cursor c) {

		ArrayList<Transition> bm = new ArrayList<Transition>();

		while(c.moveToNext()) {
			int currState = c.getInt(c.getColumnIndex(BMDBscheme.COLUMN_ID));
			
			double x = c.getDouble(c.getColumnIndex(BMDBscheme.COLUMN_X));
			double y = c.getDouble(c.getColumnIndex(BMDBscheme.COLUMN_Y));
			
			Point p = new Point(x, y);
			
			Transition transition = 
					new Transition.TransitionBuilder(
							State.newInstance(currState), State.newInstance(currState+1))
			.point(p)
			.target(c.getString(c.getColumnIndex(BMDBscheme.COLUMN_CLASS)))
			.timestamp(c.getDouble(c.getColumnIndex(BMDBscheme.COLUMN_TIMESTAMP)))
			.event(c.getString(c.getColumnIndex(BMDBscheme.COLUMN_MODE)))
			.createTransition();

			bm.add(transition);
		}

		return bm;
	}

	private boolean isDistracted(double targetX, double targetY, double actualX[], double actualY[]) {

		boolean isXTargeted = false, isYTargetd = false;

		isXTargeted = checkTargeted(targetX, actualX);
		isYTargetd = checkTargeted(targetY, actualY);

		return true;
	}

	private boolean checkTargeted(double target, double range[]) {
		double firstVal = range[0];
		double lastVal = range[range.length-1];

		if(firstVal <= target && target <= lastVal) {
			return true;
		}

		return false;
	}

}
