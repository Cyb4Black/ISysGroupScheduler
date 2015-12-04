package searchClasses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import simuClasses.StudCollection;
import simuClasses.TimeTable;

public class LocalBeamSearchCorePool {

	public void generateBeamSearchCores(TimeTable outputTable,
			StudCollection studCol, LockableResultSet LRS, boolean op) {
		List<LocalBeamSearchCore> threads = new ArrayList<LocalBeamSearchCore>();
		LockableResultSet results = new LockableResultSet(new ReentrantLock());

		LRS.sortTablesByHappiness();

		for (int i = 0; i < 4; i++) {
			threads.add(new LocalBeamSearchCore(LRS.getResultStudCol(i),
					LRS.getResultTable(i), results, op));
		}

		for (LocalBeamSearchCore LBSC : threads) {
			LBSC.start();
		}

		for (LocalBeamSearchCore LBSC : threads) {
			try {
				LBSC.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		results.sortTablesByHappiness();
		outputTable.setAllCourses(LRS.getResultTable(0).getAllCourses());
		studCol.setAllLists(LRS.getResultStudCol(0).getAllStuds(), LRS.getResultStudCol(0).getThreeCourseStuds(), LRS.getResultStudCol(0)
				.getTwoCourseStuds(), LRS.getResultStudCol(0).getLazyStuds());
	}

}
