package searchClasses;

import java.util.List;
import java.util.Stack;

import simuClasses.*;

public class DeepSearchThread extends Thread{
	private Stack<Student> backtrace;
	private StudCollection tempCollection;
	private TimeTable resultTable;
	private int stopCount;
	int finishCount;
	boolean ignoreHappiness;
	List<TimeTable> resultSet;
	
	public DeepSearchThread(StudCollection SC, TimeTable rT, int fC, boolean iH, int poolSize, List<TimeTable> rS){
		resultTable = rT.clone();
		backtrace = new Stack<Student>();
		tempCollection = SC.clone();
		finishCount = fC;
		ignoreHappiness = iH;
		resultSet = rS;
		
		if(iH){
			stopCount = 1;
		}else{
			stopCount = poolSize;
		}
	}
	
	public void run(){
		while(finishCount < stopCount){
			// TODO Insert DeepSearch-Algorithm here ;)
		}
		finishCount++;
		resultSet.add(resultTable);
	}
}
