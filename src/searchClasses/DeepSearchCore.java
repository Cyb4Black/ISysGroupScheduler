package searchClasses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import simuClasses.StudCollection;
import simuClasses.TimeTable;
public class DeepSearchCore {
	private int WITH = 8;
	private int WITHOUT = 4;

	public void generateDeepSearch(StudCollection studCol, boolean ignoreHappiness, TimeTable initTable, TimeTable outputTable, LocalBeamSearchCorePool LBSCP) {
		if(ignoreHappiness){
			generateDeepSearch(studCol, WITHOUT, initTable, outputTable, ignoreHappiness, LBSCP);
		}else{
			generateDeepSearch(studCol, WITH, initTable, outputTable, ignoreHappiness, LBSCP);
		}
	}
	
	private void generateDeepSearch(StudCollection studCol, int poolSize, TimeTable initTable, TimeTable outputTable, boolean ignoreHappiness, LocalBeamSearchCorePool LBSCP){
		List<DeepSearchThread> threads = new ArrayList<DeepSearchThread>();
		List<TimeTable> resultTableSet = new LinkedList<TimeTable>();
		List<StudCollection> resultStudSet = new LinkedList<StudCollection>();
		LockableCounter lockC = new LockableCounter(new ReentrantLock());
		
		for(int i = 0; i < poolSize; i++){
			threads.add(new DeepSearchThread(studCol, initTable, lockC, ignoreHappiness, poolSize, resultTableSet, resultStudSet));
		}
		
		for(DeepSearchThread dst : threads){
			dst.start();
		}
		
		for(DeepSearchThread dst : threads){
			try {
				dst.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(ignoreHappiness){
			outputTable.setAllCourses(resultTableSet.get(0).getAllCourses());
			studCol.setAllLists(resultStudSet.get(0).getAllStuds(), resultStudSet.get(0).getThreeCourseStuds(), resultStudSet.get(0).getTwoCourseStuds(), resultStudSet.get(0).getLazyStuds());
		}else{
			LBSCP.generateBeamSearchCores(outputTable, studCol, resultTableSet, resultStudSet);
		}
		
	}
}
