package searchClasses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import simuClasses.StudCollection;
import simuClasses.TimeTable;
public class DeepSearchCore {
	private int WITH = 8;
	private int WITHOUT = 4;

	public void generateDeepSearch(StudCollection studCol, boolean ignoreHappiness, TimeTable initTable) {
		if(ignoreHappiness){
			generateDeepSearch(studCol, WITHOUT, initTable, ignoreHappiness);
		}else{
			generateDeepSearch(studCol, WITH, initTable, ignoreHappiness);
		}
	}
	
	private void generateDeepSearch(StudCollection studCol, int poolSize, TimeTable initTable, boolean ignoreHappiness){
		List<DeepSearchThread> threads = new ArrayList<DeepSearchThread>();
		int finishCount = 0;
		List<TimeTable> resultSet = new LinkedList<TimeTable>();
		
		for(int i = 0; i < poolSize; i++){
			threads.add(new DeepSearchThread(studCol, initTable, finishCount, ignoreHappiness, poolSize, resultSet));
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
		
	}
}
