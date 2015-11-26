package mainProg.core;

import java.util.concurrent.locks.*;

import mainProg.gui.ProgressView;

public class LockableProgressCounter {
	private Lock l;
	private int count;
	int globPV;
	
	public LockableProgressCounter(ReentrantLock lock, int pv){
		this.l = lock;
		this.globPV = pv;
		count = 0;
	}
	
	public int getCount(){
		l.lock();
		try{
			return count;
		}finally{
			l.unlock();
		}
	}
	
	public void pp(){
		l.lock();
		try{
			count++;
			System.out.println(count + "/" + (globPV*12));
		}finally{
			l.unlock();
		}
	}
}
