package mainProg.core;

import java.util.List;

import mainProg.gui.ProgressView;

public class StatGenThread extends AbstractMain implements Runnable {
	StatGenResult result;
	List<StatGenResult> resultSet;
	LockableProgressCounter progCount;
	ProgressView globPV;
	private int cycles, myOverlap;

	public StatGenThread(int o, int cyc, LockableProgressCounter finishCount, List<StatGenResult> results) {
		this.progCount = finishCount;
		this.cycles = cyc;
		this.myOverlap = o;
		this.resultSet = results;
		result = new StatGenResult(o);
	}

	@Override
	public void run() {
		for(int i = 0; i < cycles; i++){
			this.init(myOverlap);
			this.startSearch(true);
			this.result.addRandomHappiness(this.finalTable.getHappiness());
			progCount.pp();
//			---------------------------------------------------------------
			this.startSearch(false);
			this.result.addOptHappiness(this.finalTable.getHappiness());
			progCount.pp();
//			---------------------------------------------------------------
		}
		resultSet.add(result);
	}

}
