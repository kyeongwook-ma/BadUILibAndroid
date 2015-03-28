package sogang.selab;

public interface WrongPosDetectionService {
	boolean isWrongPosition(int timestamp, float x, float y, String target, String operation);
}
