package sogang.selab.model;

public class BehaviorModel {

	private float x,y;
	private String target, operation;
	private double timestamp;

	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}

	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public double getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}

}
