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

	public void generateDeepSearch(StudCollection studCol, boolean ignoreHappiness, TimeTable initTable, TimeTable outputTable, LocalBeamSearchCorePool LBSCP, boolean op) {
		if(ignoreHappiness){
			generateDeepSearch(studCol, WITHOUT, initTable, outputTable, ignoreHappiness, LBSCP, op);
		}else{
			generateDeepSearch(studCol, WITH, initTable, outputTable, ignoreHappiness, LBSCP, op);
		}
	}
	
	private void generateDeepSearch(StudCollection studCol, int poolSize, TimeTable initTable, TimeTable outputTable, boolean ignoreHappiness, LocalBeamSearchCorePool LBSCP, boolean op){
		List<DeepSearchThread> threads = new ArrayList<DeepSearchThread>();
		LockableResultSet LRS = new LockableResultSet(new ReentrantLock());
		LockableCounter lockC = new LockableCounter(new ReentrantLock());
		
		for(int i = 0; i < poolSize; i++){
			threads.add(new DeepSearchThread(studCol, initTable, lockC, ignoreHappiness, poolSize, LRS));
		}
		
		for(DeepSearchThread dst : threads){
			dst.start();
		}
		
		for(DeepSearchThread dst : threads){
			try {
				dst.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		System.out.println("DEBUG");
		if(ignoreHappiness){
			outputTable.setAllCourses(LRS.getResultTable(0).getAllCourses());
			studCol.setAllLists(LRS.getResultStudCol(0).getAllStuds(), LRS.getResultStudCol(0).getThreeCourseStuds(), LRS.getResultStudCol(0).getTwoCourseStuds(), LRS.getResultStudCol(0).getLazyStuds());
		}else{
			LBSCP.generateBeamSearchCores(outputTable, studCol, LRS, op);
		}
		
	}
}
