package mainProg.core;

import java.util.List;

import mainProg.gui.ProgressView;

public class StatGenThread extends AbstractMain implements Runnable {
	StatGenResult result;
	List<StatGenResult> resultSet;
	LockableProgressCounter progCount;
	ProgressView globPV;
	private int cycles, myOverlap;
	private boolean overPower;

	public StatGenThread(int o, int cyc, LockableProgressCounter finishCount, List<StatGenResult> results, boolean op) {
		this.progCount = finishCount;
		this.cycles = cyc;
		this.myOverlap = o;
		this.resultSet = results;
		this.overPower = op;
		result = new StatGenResult(o);
	}

	@Override
	public void run() {
		for(int i = 0; i < cycles; i++){
			this.init(myOverlap);
			this.startSearch(true, false);
			this.result.addRandomHappiness(this.finalTable.getHappiness());
			progCount.pp();
//			---------------------------------------------------------------
			this.startSearch(false, overPower);
			this.result.addOptHappiness(this.finalTable.getHappiness());
			progCount.pp();
//			---------------------------------------------------------------
		}
		resultSet.add(result);
	}

}
