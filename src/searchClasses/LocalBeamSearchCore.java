package searchClasses;

import java.util.ArrayList;
import java.util.List;
import simuClasses.TimeTable;

public class LocalBeamSearchCore extends Thread {
	private TimeTable MyTimeTable;
	private LockableResultSet results;
	private boolean overPower;
	
	public LocalBeamSearchCore( TimeTable TT, LockableResultSet LRS, boolean op){
		this.MyTimeTable=TT;
		this.results = LRS;
		this.overPower = op;
	}
	
	public void run(){
		List<LocalBeamSearchThread> threads = new ArrayList<LocalBeamSearchThread>();
		
		for (int i = 0; i < 3; i++) {
			threads.add(new LocalBeamSearchThread(MyTimeTable, results, overPower));
		}

		for (LocalBeamSearchThread LBST : threads) {
			LBST.start();
		}

		for (LocalBeamSearchThread LBST : threads) {
			try {
				LBST.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return;
	}
}
