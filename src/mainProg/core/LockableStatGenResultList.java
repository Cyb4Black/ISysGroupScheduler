package mainProg.core;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.*;

import simuClasses.StudCollection;
import simuClasses.TimeTable;

public class LockableStatGenResultList {
	private Lock l;
	private LinkedList<StatGenResult> resultList = new LinkedList<StatGenResult>();
	private int size;
	
	public LockableStatGenResultList(ReentrantLock lock){
		this.l = lock;
		size = 0;
	}
	
	public StatGenResult getResult(int i){
		l.lock();
		try{
			return this.resultList.get(i);
		}finally{
			l.unlock();
		}
	}
	
	public LinkedList<StatGenResult> getList(){
		return this.resultList;
	}
	
	public void add(StatGenResult SGR){
		l.lock();
		try{
			resultList.add(SGR);
			size++;
		}finally{
			l.unlock();
		}
	}
	
	
}
