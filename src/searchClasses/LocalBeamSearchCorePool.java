package searchClasses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import simuClasses.StudCollection;
import simuClasses.TimeTable;

/**
 * Eine Klasse, welche einen LocalBeamSearchCore für jedes DeepSearch ergebnis erzeugt
 * 
 * @author Willnow & Selle
 *
 */
public class LocalBeamSearchCorePool {

	public void generateBeamSearchCores(TimeTable outputTable,StudCollection studCol, LockableResultSet LRS, boolean op) {
		//Liste über die LocalbeamSearchCorethreads
		List<LocalBeamSearchCore> threads = new ArrayList<LocalBeamSearchCore>();
		
		//Erzeugen des Objekts in dem die LocalBeamSearch ergebnisse gespeichert werden
		LockableResultSet results = new LockableResultSet(new ReentrantLock());

		LRS.sortTablesByHappiness();
		
		//Erzeugung der LocalBeamSearchCores mit jeweils einem eigenen TimeTable und eigener StudCollection
		for (int i = 0; i < 4; i++) {
			threads.add(new LocalBeamSearchCore(LRS.getResultStudCol(i),LRS.getResultTable(i), results, op));
		}
		//Starten der Threads
		for (LocalBeamSearchCore LBSC : threads) {
			LBSC.start();
		}
		//Joinen der Threads
		for (LocalBeamSearchCore LBSC : threads) {
			try {
				LBSC.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//Sortierung der Ergebnisse und Ausgabe des besten Ergebnis
		results.sortTablesByHappiness();
		outputTable.setAllCourses(results.getResultTable(0).getAllCourses());
		outputTable.setCycles(results.getResultTable(0).getCycles());
		outputTable.setSwaps(results.getResultTable(0).getSwaps());
		studCol.setAllLists(results.getResultStudCol(0).getAllStuds(), results.getResultStudCol(0).getThreeCourseStuds(), results.getResultStudCol(0)
				.getTwoCourseStuds(), results.getResultStudCol(0).getLazyStuds());
	}

}
