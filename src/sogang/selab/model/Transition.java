package sogang.selab.model;

import java.util.HashSet;
import java.util.Iterator;

/**
 * @author se
 * State ���� transition�� ��Ÿ���� Ŭ����
 * �����(src)�� ������(dst)�� ������ �ִ�.
 */
public class Transition {

	private State src, dst;
	private double timestamp;
	private String event;
	private Point touchPoint;
	private HashSet<String> targets;

	public void setSrc(State src) {
		this.src = src;
	}

	public void setDst(State dst) {
		this.dst = dst;
	}

	public State getSrc() {
		return src;
	}

	public State getDst() {
		return dst;
	}

	public double getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}


	public Point getTouchPoint() {
		return touchPoint;
	}

	public void setTouchPoint(Point touchPoint) {
		this.touchPoint = touchPoint;
	}

	public void setTargets(HashSet<String> targets) {
		this.targets = targets;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public void addTarget(HashSet<String> targets) {
		this.targets.addAll(targets);
	}

	public Transition(State src, State dst) {
		this.src = src;
		this.dst = dst;
		this.targets = new HashSet<String>();
	}

	/**
	 * @author se
	 * Trnasition�� builder Ŭ����
	 * 
	 * ex)
	 * new Transition.TransBuilder(src, dst)
	 * .x(30).y(40)
	 * // ��Ÿ attribute �Ҵ�
	 * .createTransition();
	 * 
	 */
	public static class TransitionBuilder {

		private State src, dst;
		private double timestamp;
		private Point touchPoint;
		private String event;
		private HashSet<String> targets;

		public TransitionBuilder(State src, State dst) {
			this.src = src;
			this.dst = dst;
			targets = new HashSet<String>();
		}

		public TransitionBuilder point(Point p) {
			this.touchPoint = p;
			return this;
		}

		public TransitionBuilder event(String event) {
			this.event = event;
			return this;
		}

		public TransitionBuilder timestamp(double timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public TransitionBuilder target(String target) {
			this.targets.add(target);
			return this;
		}

		/**
		 * @return �� ��ü�� ����� ������ ��� transition instance
		 */
		public Transition createTransition() {
			Transition t = new Transition(this.src, this.dst);
			t.setEvent(this.event);
			t.setTouchPoint(this.touchPoint);
			t.addTarget(targets);
			t.setTimestamp(this.timestamp);
			return t;
		}
	}

	public Iterator<String> getTargets() {
		return targets.iterator();
	}

	public void expend(Transition transition) {

		double transitionX = transition.getTouchPoint().getX();
		double transitionY = transition.getTouchPoint().getY();

		/* �� ���� ������ ���� �Ҵ��� */
		this.touchPoint.setX(this.touchPoint.getX() - transitionX > 0 ? 
				transitionX : 
				this.getTouchPoint().getX()); 

		this.touchPoint.setY(this.touchPoint.getY() - transitionX > 0 ? 
				transitionY : 
				this.getTouchPoint().getY()); 

		/* target �߰� */
		Iterator<String> transitionTarget = transition.getTargets();
			while(transitionTarget.hasNext()) {
				String target = transitionTarget.next();
				this.targets.add(target);
		}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( "\n" + src.toString() + " -> " + dst.toString() + "\n");
		sb.append("x : " + this.getTouchPoint().getX() + " ,");
		sb.append("y : " + this.getTouchPoint().getY() + " ,");
		sb.append("event : " + event + " ,");

		Iterator<String> transitionTarget = getTargets();
		StringBuilder targetSB = new StringBuilder();
		while(transitionTarget.hasNext()) {
			String target = transitionTarget.next();
			targetSB.append(" " + target);
		}

		sb.append("target : " + targetSB.toString() + " ,");
		sb.append("timestamp : " + String.valueOf(timestamp));

		return sb.toString();
	}

	/* (non-Javadoc)
	 * Transition�� event�� ���� ��� ���� transition���� ��� 
	 */
	@Override
	public boolean equals(Object obj) {
		Transition t = (Transition) obj;
		return t.src.equals(src) && t.dst.equals(dst);
	}

	@Override
	public int hashCode() {
		return (String.valueOf(src.getStateId()) + 
				String.valueOf(dst.getStateId())).hashCode();
	}

	
}