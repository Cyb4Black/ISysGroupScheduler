package searchClasses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import simuClasses.StudCollection;
import simuClasses.TimeTable;

public class LocalBeamSearchCorePool {

	public void generateBeamSearchCores(TimeTable outputTable,
			StudCollection studCol, List<TimeTable> initialTableSet,
			List<StudCollection> initialStudSet) {
		List<LocalBeamSearchCore> threads = new ArrayList<LocalBeamSearchCore>();
		List<TimeTable> resultTableSet = new LinkedList<TimeTable>();
		List<StudCollection> resultStudSet = new LinkedList<StudCollection>();

		sortTablesByHappiness(initialTableSet, initialStudSet);

		for (int i = 0; i < 4; i++) {
			threads.add(new LocalBeamSearchCore(initialStudSet.get(i),
					initialTableSet.get(i), resultTableSet, resultStudSet));
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

		sortTablesByHappiness(resultTableSet, resultStudSet);
		outputTable.setAllCourses(resultTableSet.get(0).getAllCourses());
		studCol.setAllLists(resultStudSet.get(0).getAllStuds(), resultStudSet
				.get(0).getThreeCourseStuds(), resultStudSet.get(0)
				.getTwoCourseStuds(), resultStudSet.get(0).getLazyStuds());
	}

	private void sortTablesByHappiness(List<TimeTable> initialTableSet,
			List<StudCollection> initialStudSet) {
		for (int i = 0; i < initialTableSet.size(); i++) {
			for (int j = i + 1; j < initialTableSet.size(); j++) {
				if (initialTableSet.get(i).getHappiness() < initialTableSet
						.get(j).getHappiness()) {
					TimeTable dummy1 = initialTableSet.get(i);
					initialTableSet.set(i, initialTableSet.get(j));
					initialTableSet.set(j, dummy1);

					StudCollection dummy2 = initialStudSet.get(i);
					initialStudSet.set(i, initialStudSet.get(j));
					initialStudSet.set(j, dummy2);
				}
			}
		}
	}

}
