package searchClasses;

import java.util.ArrayList;
import java.util.List;

import simuClasses.StudCollection;
import simuClasses.TimeTable;

/**
 * Eine Klasse, welche drei LocalBeamSearchThreads für einen übergebenen Stundenplan durchführt
 * @author Willnow & Selle
 *
 */
public class LocalBeamSearchCore extends Thread {
	private StudCollection MyStuds;
	private TimeTable MyTimeTable;
	private LockableResultSet results;
	private boolean overPower;
	
	/**
	 * Konstruktor für den LocalBeamSearchCore
	 * @param SC Die StudCollection mit der alle Threads arbeiten
	 * @param TT Der Stundenplan mit dem alle Threads arbeiten.
	 * @param LRS Das LockableResultSet in das alle Threads schreiben.
	 * @param op die Flag nach welcher Art gesucht wird.
	 */
	public LocalBeamSearchCore(StudCollection SC, TimeTable TT, LockableResultSet LRS, boolean op){
		this.MyStuds=SC;
		this.MyTimeTable=TT;
		this.results = LRS;
		this.overPower = op;
	}
	
	public void run(){
		//Liste über die LocalBeamSearchThreads
		List<LocalBeamSearchThread> threads = new ArrayList<LocalBeamSearchThread>();
		
		//Erzeugung der Threads
		for (int i = 0; i < 3; i++) {
			threads.add(new LocalBeamSearchThread(MyStuds,
					MyTimeTable, results, overPower));
		}
		
		//Starten der Threads
		for (LocalBeamSearchThread LBST : threads) {
			LBST.start();
		}
		
		//Joinen des Threads
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
