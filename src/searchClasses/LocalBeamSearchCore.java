package searchClasses;

import java.util.ArrayList;
import java.util.List;

import simuClasses.StudCollection;
import simuClasses.TimeTable;

public class LocalBeamSearchCore extends Thread {
	private StudCollection MyStuds;
	private TimeTable MyTimeTable;
	private List<TimeTable> resultTableSet;
	private List<StudCollection> resultStudSet;
	
	public LocalBeamSearchCore(StudCollection SC, TimeTable TT, List<TimeTable> rTS, List<StudCollection> rSS){
		this.MyStuds=SC;
		this.MyTimeTable=TT;
		this.resultTableSet=rTS;
		this.resultStudSet=rSS;
		
	}
	
	public void run(){
		List<LocalBeamSearchThread> threads = new ArrayList<LocalBeamSearchThread>();
		
		for (int i = 0; i < 3; i++) {
			threads.add(new LocalBeamSearchThread(MyStuds,
					MyTimeTable, resultTableSet, resultStudSet));
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
