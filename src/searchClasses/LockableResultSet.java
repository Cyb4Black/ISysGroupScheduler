package searchClasses;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.*;
import simuClasses.StudCollection;
import simuClasses.TimeTable;

/**
 * Eine Klasse in der die Ergebnisse der Threads gespeichert werden. Eingef�hrt f�r ThreadSafety
 * 
 * @author Willnow & Selle
 */
public class LockableResultSet {
	private Lock l;
	private List<TimeTable> resultTableSet = new LinkedList<TimeTable>();
	private List<StudCollection> resultStudSet = new LinkedList<StudCollection>();
	private int size;
	
	public LockableResultSet(ReentrantLock lock){
		this.l = lock;
		size = 0;
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
			return this.resultStudSet.get(i);
		}finally{
			l.unlock();
		}
	}
	/**
	 * Eine Methode, welche einen Stundenplan und eine Menge von Studenten den Ergebnissen hinzuf�gt.
	 * @param inTable der Stundenplan
	 * @param inCol die Menge von Studenten
	 */
	public void addResults(TimeTable inTable, StudCollection inCol){
		l.lock();
		try{
			resultTableSet.add(inTable);
			resultStudSet.add(inCol);
			size++;
		}finally{
			l.unlock();
		}
	}
	
	/**
	 * Eine Methode, welche die Ergebnisse nach Gl�ckswerten sortiert.
	 */
	public void sortTablesByHappiness() {
		for (int i = 0; i < resultTableSet.size(); i++) {
			for (int j = i + 1; j < resultTableSet.size(); j++) {
				if (resultTableSet.get(i).getHappiness() < resultTableSet
						.get(j).getHappiness()) {
					TimeTable dummy1 = resultTableSet.get(i);
					resultTableSet.set(i, resultTableSet.get(j));
					resultTableSet.set(j, dummy1);

					StudCollection dummy2 = resultStudSet.get(i);
					resultStudSet.set(i, resultStudSet.get(j));
					resultStudSet.set(j, dummy2);
				}
			}
		}
	}
}
