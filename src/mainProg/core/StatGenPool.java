package mainProg.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StatGenPool implements Runnable{
	
	private int MAXOVERLAP = 6;
	private int cycles;
	private boolean overPower;
	LockableProgressCounter progCount;
	LockableStatGenResultList results;

	public StatGenPool(int cyc, boolean op, LockableStatGenResultList r, LockableProgressCounter pc) {
		this.cycles = cyc;
		this.overPower = op;
		this.progCount = pc;
//		this.progCount = new LockableProgressCounter(new ReentrantLock(), cycles);
		this.results = r;
	}
	
	public void run(){
		long start = System.currentTimeMillis();
		List<Thread> threads = new ArrayList<Thread>();
		for(int i = 0; i < MAXOVERLAP; i++){
			threads.add(new Thread(new StatGenThread(i, cycles, progCount, results, overPower)));
		}
		for(Thread t : threads){
			t.start();
		}

		for(Thread t : threads){
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long stop = System.currentTimeMillis();
		System.out.println((stop - start)/1000);
	}

}
