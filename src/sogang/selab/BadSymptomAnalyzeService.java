package sogang.selab;

public abstract class BadSymptomAnalyzeService {
	
	protected int timestamp;
	protected float targetX, targetY, screenX, screenY;
	protected String target, operation;
	
	/**
	 * Desc : Constructor of BadSymptomAnalyzeService.java class
	 * @param timestamp
	 * @param x
	 * @param y
	 * @param target
	 * @param operation
	 */
	public BadSymptomAnalyzeService(int timestamp, float x, float y, 
			float screenX, float screenY,
			String target, String operation) {
		this.timestamp = timestamp;
		this.targetX = x; this.targetY = y;
		this.screenX = screenX; this.screenY = screenY;
		this.target = target; this.operation = operation;
	}
	
	/**
	 * Desc : 각 전략별 분석을 통해 이상징후의 타입을 반환
	 * @Method Name : analyze
	 * @return 이상 징후
	 */
	public abstract BadSymptom analyze();
}
