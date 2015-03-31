package sogang.selab;

import java.util.List;

import sogang.selab.model.Point;
import sogang.selab.model.Transition;


public class WrongPosAnalyzer extends BadSymptomAnalyzeService {

	public WrongPosAnalyzer(int timestamp, float x, float y, String target,
			String operation) {
		super(timestamp, x, y, target, operation);
	}

	@Override
	public BadSymptom analyze() {
		
		List<Transition> bms = LogMonitor.getAllBM();

		Point points[] = getAllPoint(bms);
		double[] actualX = extractXPoint(points);
		double[] actualY = extractYPoint(points);
		
		for(int i = 1; i < bms.size(); i += 3) {
			Transition t = bms.get(i);
			Transition prevTransition = bms.get(i - 1);
			Transition nextTransition = bms.get(i + 1);

			if(t.equals(nextTransition) || t.equals(prevTransition)) {
				if(isDistracted(targetX, targetY, actualX, actualY))
					return BadSymptom.WRONG_POS;
			}
		}
		
		return BadSymptom.WRONG_POS;
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
