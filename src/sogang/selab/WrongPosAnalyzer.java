package sogang.selab;

import java.util.HashMap;
import java.util.List;

import android.util.Log;
import sogang.selab.model.AFSM;
import sogang.selab.model.EFSMUtil;
import sogang.selab.model.Point;
import sogang.selab.model.Transition;

public class WrongPosAnalyzer extends BadSymptomAnalyzeService {

	private final double THRESHOLD = 65.0;
	
	public WrongPosAnalyzer(int timestamp, 
			float x, float y, 
			float screenX, float screenY, 
			String target,
			String operation) {
		super(timestamp, x, y, screenX, screenY, target, operation);
	}

	private HashMap<Transition, Double> calculatedRatioMap() {
		
		List<Transition> allTransitions = LogMonitor.getAllTransition();

		HashMap<Transition, Double> transitionRatioMap
		= new HashMap<Transition, Double>();

		int transitionSize = allTransitions.size();

		for(Transition t : allTransitions) {

			if(transitionRatioMap.containsKey(t)) {	
				double preRatio = transitionRatioMap.get(t);
				double currRatio = getCurrRatio(preRatio, transitionSize);
				transitionRatioMap.put(t, currRatio);

			} else {
				transitionRatioMap.put(t, getRatio(transitionSize));	
			}
		}

		return transitionRatioMap;
	}

	private double getRatio(int transitionSize) {
		return ((double)1 / transitionSize  * 100);
	}

	private double getCurrRatio(double preRatio, int transitionSize) {
		int incrementedCount = (int) (preRatio * transitionSize / 100 ) + 1;
		return  (double)incrementedCount / (double)transitionSize * 100;
	}
	
	private AFSM generateWholeBM() throws Exception {
		AFSM wholeBM = null;

		List<AFSM> allBMs = LogMonitor.getAllBMs();
		int bmSize = allBMs.size();
		
		for(int i = 0; i < bmSize - 1; ++i) {
			wholeBM = EFSMUtil.gkTail(allBMs.get(i), allBMs.get(i+1), 3);
		}
	
		return EFSMUtil.gkTail(wholeBM, allBMs.get(bmSize), 3);
	}

	
	@Override
	public BadSymptom analyze() {

		/* 모든 상태 전이의 분포 계산 */
		HashMap<Transition, Double> transitionRatioMap = calculatedRatioMap();

		/* 사용자 전체의 AFSM */
		try {
			/* 병합된 형태의 BM */
			AFSM wholeBM = generateWholeBM();
			
			int userId = 3;
			AFSM userBM = LogMonitor.getUserBM(userId);
			
			/* 전체 사용자 BM - 현 사용자 BM */
			List<Transition> diffTransitions = wholeBM.getDiffTransition(userBM);
			
			for(Transition t : diffTransitions) {
				double ratio = transitionRatioMap.get(t);
				
				/* 다른 Transition 비율이 일정 THRESHOLD로 넘어가거나
				 * 일정 시간을 지나치면 
				 * WRONG POS 진단 */
				if(ratio > THRESHOLD || t.getTimestamp() > timestamp) {
					return BadSymptom.WRONG_POS;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	

}