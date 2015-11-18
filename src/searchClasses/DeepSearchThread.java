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
				List<Student> tempStuds =tempCollection.getThreeCourseStuds();
				List<CourseSlot> AllCourses =resultTable.getAllCourses();
				
				for (Student student : tempStuds) {
					for (CourseSlot courseSlot : AllCourses) {
						//Fall Student hat noch keine Termine und Termin ist noch nicht voll
						if(student.getMySlots().isEmpty() && !(courseSlot.isFilled())){
							courseSlot.addStudent(student);
							student.addSlot(courseSlot);
							backtrace.push(student);
							tempStuds.remove(student);
						}
						for (CourseSlot StudentSlot : student.getMySlots()){
							//Fall Student hat noch keinen Termin von diesem Fach UND belegt noch keinen Termin zur gleichen Uhrzeit UND Termin ist noch nicht voll
							if(StudentSlot.getCourse()!=courseSlot.getCourse() && StudentSlot.getTimeSlot() != courseSlot.getTimeSlot() && !(courseSlot.isFilled())){
								courseSlot.addStudent(student);
								student.addSlot(courseSlot);
								backtrace.push(student);
								tempStuds.remove(student);
							}
						}
					}
					//Fall Student konnte nicht gesetzt werden
					if(student.getMySlots().size()!=student.getCourses().size()){
						for(CourseSlot StudentSlot :student.getMySlots()){
							StudentSlot.removeStudent(student);
							student.removeSlot(StudentSlot);
						}
						tempStuds.add(0, backtrace.pop());;
						
					}
				}
				
				finishCount++;
				resultSet.add(resultTable);
		}
		
	}
}
