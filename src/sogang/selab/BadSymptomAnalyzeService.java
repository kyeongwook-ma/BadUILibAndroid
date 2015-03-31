package sogang.selab;

public abstract class BadSymptomAnalyzeService {
	
	protected int timestamp;
	protected float targetX, targetY;
	protected String target, operation;
	
	public BadSymptomAnalyzeService(int timestamp, float x, float y, String target, String operation) {
		this.timestamp = timestamp;
		this.targetX = x; this.targetY = y;
		this.target = target; this.operation = operation;
	}
	
	public abstract BadSymptom analyze();
}
