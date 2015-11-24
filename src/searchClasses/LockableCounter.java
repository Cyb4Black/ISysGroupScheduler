package searchClasses;

import java.util.concurrent.locks.*;

public class LockableCounter {
	private Lock l;
	private int count;
	
	public LockableCounter(ReentrantLock lock){
		this.l = lock;
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
	
	public int pp(){
		l.lock();
		try{
			count++;
			return (count - 1);
		}finally{
			l.unlock();
		}
	}
}
