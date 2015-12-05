package searchClasses;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.*;

import simuClasses.StudCollection;
import simuClasses.TimeTable;

public class LockableResultSet {
	private Lock l;
	private List<TimeTable> resultTableSet = new LinkedList<TimeTable>();
	private int size;
	
	public LockableResultSet(ReentrantLock lock){
		this.l = lock;
		setSize(0);
	}
	
	public TimeTable getResultTable(int i){
		l.lock();
		try{
			return this.resultTableSet.get(i);
		}finally{
			l.unlock();
		}
	}
	
	public StudCollection getResultStudCol(int i){
		l.lock();
		try{
			return this.resultTableSet.get(i).getMyStuds();
		}finally{
			l.unlock();
		}
	}
	
	public void addResult(TimeTable inTable){
		l.lock();
		try{
			resultTableSet.add(inTable);
			setSize(getSize() + 1);
		}finally{
			l.unlock();
		}
	}
	
	public void sortTablesByHappiness() {
		for (int i = 0; i < resultTableSet.size(); i++) {
			for (int j = i + 1; j < resultTableSet.size(); j++) {
				if (resultTableSet.get(i).getHappiness() < resultTableSet.get(j).getHappiness()) {
					TimeTable dummy1 = resultTableSet.get(i);
					resultTableSet.set(i, resultTableSet.get(j));
					resultTableSet.set(j, dummy1);
				}
			}
		}
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
