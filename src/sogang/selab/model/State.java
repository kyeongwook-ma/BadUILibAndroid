package sogang.selab.model;

public class State {

	private String stateId;
	
	public static State newInstance(String stateId) {
		return new State(stateId);
	}
	
	private State(String id) {
		this.stateId = id;
	}
		
	public String getStateId() {
		return stateId;
	}
	
	@Override
	public boolean equals(Object obj) {
		State s = (State) obj;
		return this.stateId.equals(s.getStateId()) ? true : false;
	}
	
	@Override
	public String toString() {
		return String.valueOf(stateId);
	}

}
