package sogang.selab.model;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class EFSM {

	private LinkedList<Transition> transitionSeqs;

	public EFSM() {
		transitionSeqs = new LinkedList<Transition>();
	}
	
	public List<Transition> getAllTransition() {
		return transitionSeqs;
	}

	public int size() {
		return transitionSeqs.size();
	}

	public Transition get(int idx) {
		return transitionSeqs.get(idx);
	}

	public boolean checkCompletness() {
		/* 0번째 dst State */
		State dstState = transitionSeqs.get(0).getDst();
		
		for(int i = 0; i < transitionSeqs.size(); ++i) {
			Transition t = transitionSeqs.get(i);
			/* dst State와 다음 src State 가 다른 경우 불안정 */
			if(!dstState.equals(t.getSrc())) {
				return false;
			}
			dstState = transitionSeqs.get(i).getDst();
		}

		return false;
	}

	public void addStateSeq(Transition... transitions) {
		
		if(transitionSeqs.size() == 0) {
			transitionSeqs.addAll(Arrays.asList(transitions));
			return;
		}
			
		for(Transition t : transitions) {
			/* ������ transition �� ���Ͽ� ������ Ȯ���Ŵ */
			Transition lastTransition = endTransition();
			
			if(lastTransition.equals(t)) {
				lastTransition.expend(t);			
			} else {
				transitionSeqs.add(t);
			}
		}
		
	}
	
	public Transition startTransition() {
		return transitionSeqs.getFirst();
	}

	public Transition endTransition() {
		return transitionSeqs.getLast();
	}
	
	@Override
	public boolean equals(Object obj) {
		EFSM efsm = (EFSM) obj;
		
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		ListIterator<Transition> listIter = transitionSeqs.listIterator();
		while(listIter.hasNext()) {
			Transition t = listIter.next();
			sb.append(t.toString());
		}

		return sb.toString();
	}

}
