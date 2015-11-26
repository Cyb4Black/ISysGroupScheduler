package mainProg.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import mainProg.gui.ProgressView;
import mainProg.gui.StatData;

public class StatGenPool {
	
//	ProgressView globPV;
	private int MAXOVERLAP = 6;
	private int cycles;
	LockableProgressCounter progCount;
	LinkedList<StatGenResult> results;

	public StatGenPool(int cyc) {
		this.cycles = cyc;
//		 globPV = new ProgressView(cycles);
		this.progCount = new LockableProgressCounter(new ReentrantLock(), cycles);
		results = new LinkedList<StatGenResult>();
	}
	
	public void run(){
		List<Thread> threads = new ArrayList<Thread>();
		for(int i = 0; i < MAXOVERLAP; i++){
			threads.add(new Thread(new StatGenThread(i, cycles, progCount, results)));
		}
//		Thread progT = new Thread(globPV);
//		progT.start();
		for(Thread t : threads){
			t.start();
		}
//		threads.add(progT);
		for(Thread t : threads){
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		StatData sd = new StatData(results, cycles);
		sd.open();
	}

}
