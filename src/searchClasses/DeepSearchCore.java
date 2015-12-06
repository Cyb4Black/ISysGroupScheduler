package searchClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import simuClasses.StudCollection;
import simuClasses.TimeTable;

/**
 * Eine Klasse, welche entweder 4 DeepSearchThreads erzeugt oder 8 DeepSearchThreads und dann die LocalBeamSearch anstößt
 * @author Willnow & Selle
 */
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
	
	/**
	 * Eine Methode, welche entweder 4 DeepSearchThreads erzeugt oder 8 DeepSearchThreads und dann die LocalBeamSearch anstößt
	 * @param studCol die StudCollection mit der gearbeitet wird.
	 * @param poolSize Die Anzahl der Threads
	 * @param initTable der unbefüllte Stundenplan
	 * @param outputTable der befüllte Stundenplan mit dem weiter gearbeitet wird
	 * @param ignoreHappiness Flag ob die Glückswerte ignoriert werden
	 * @param LBSCP LocalBeamSearchCorePool Objekt welches die LocalBeamSearch startet
	 * @param op Flag nach welcher Art gesucht wird.
	 */
	private void generateDeepSearch(StudCollection studCol, int poolSize, TimeTable initTable, TimeTable outputTable, boolean ignoreHappiness, LocalBeamSearchCorePool LBSCP, boolean op){
		List<DeepSearchThread> threads = new ArrayList<DeepSearchThread>();
		LockableResultSet LRS = new LockableResultSet(new ReentrantLock());
		LockableCounter lockC = new LockableCounter(new ReentrantLock());
		
		//Erzeugen der Threads
		for(int i = 0; i < poolSize; i++){
			threads.add(new DeepSearchThread(studCol, initTable, lockC, ignoreHappiness, poolSize, LRS));
		}
		
		//Starten der Threads
		for(DeepSearchThread dst : threads){
			dst.start();
		}
		
		//Joinen der Threads
		for(DeepSearchThread dst : threads){
			try {
				dst.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(ignoreHappiness){
			outputTable.setAllCourses(LRS.getResultTable(0).getAllCourses());
			studCol.setAllLists(LRS.getResultStudCol(0).getAllStuds(), LRS.getResultStudCol(0).getThreeCourseStuds(), LRS.getResultStudCol(0).getTwoCourseStuds(), LRS.getResultStudCol(0).getLazyStuds());
		}else{
			LBSCP.generateBeamSearchCores(outputTable, studCol, LRS, op);
		}
		
	}
}
