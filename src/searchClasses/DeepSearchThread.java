package searchClasses;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;



import simuClasses.*;

public class DeepSearchThread extends Thread {
	//private Stack<Student> backtrace;
	private StudCollection tempCollection;
	private TimeTable initialTable;
	private int stopCount;
	LockableCounter finishCount;
	boolean ignoreHappiness;
	List<TimeTable> resultTableSet;
	List<StudCollection> resultStudSet;

	public DeepSearchThread(StudCollection SC, TimeTable rT, LockableCounter lockC,
			boolean iH, int poolSize, List<TimeTable> rTS, List<StudCollection> rSS) {
		initialTable = rT;
		
		tempCollection = SC;
		finishCount = lockC;
		ignoreHappiness = iH;
		resultTableSet = rTS;
		resultStudSet = rSS;

		if (iH) {
			stopCount = 1;
		} else {
			stopCount = poolSize;
		}
	}

	public void run() {
		TimeTable myResultTable = initialTable.clone();
		List<CourseSlot> allCourseSlots = myResultTable.getAllCourses();
		Stack<Student>backtrace = new Stack<Student>();
		StudCollection myTempCollection = tempCollection.clone();
		
		List<Student> tempStuds = myTempCollection.getThreeCourseStuds();
		if(searchStep(tempStuds, allCourseSlots, backtrace, myTempCollection, myResultTable) == 1)return;
		tempStuds = myTempCollection.getTwoCourseStuds();
		if(searchStep(tempStuds, allCourseSlots, backtrace, myTempCollection, myResultTable) == 1)return;
		tempStuds = myTempCollection.getLazyStuds();
		if(searchStep(tempStuds, allCourseSlots, backtrace, myTempCollection, myResultTable) == 1)return;
		
		if (finishCount.pp() >= stopCount)return;
		resultTableSet.add(myResultTable);
		resultStudSet.add(myTempCollection);

	}

	private int searchStep(List<Student> tempStuds, List<CourseSlot> AllCourses, Stack<Student> backtrace, StudCollection myTempCollection, TimeTable myResultTable) {
		List<Student> backStuds = new LinkedList<Student>();
		Random myRand = new Random();
		while (!(tempStuds.isEmpty())) {
			//for (Student student : tempStuds) {
			while (!(tempStuds.isEmpty())) {
				Student student = tempStuds.get(myRand.nextInt(tempStuds.size()));
				List<CourseSlot> tempCourses = new LinkedList<CourseSlot>();
				tempCourses.addAll(AllCourses);
				List<String> found = new LinkedList<String>();
//				for (CourseSlot courseSlot : AllCourses) {
				while(!(tempCourses.isEmpty())){
					CourseSlot courseSlot = tempCourses.get(myRand.nextInt(tempCourses.size()));
					// Fall Student hat noch keinen Termin von diesem Fach
					// UND belegt noch keinen Termin zur gleichen Uhrzeit
					// UND Termin ist noch nicht voll
					if(  student.gotTime(courseSlot.getTimeSlot())
							&& !(courseSlot.isFilled())
							&& student.getCourses().contains(courseSlot.getCourse())
							&& !(found.contains(courseSlot.getCourse().getName()))){
						courseSlot.addStudent(student);
						student.addSlot(courseSlot);
						found.add(courseSlot.getCourse().getName());
						backtrace.push(student);
					}
					tempCourses.remove(courseSlot);
//					boolean b1 = student.gotTime(courseSlot.getTimeSlot());
//					boolean b2 = !(courseSlot.isFilled());
//					boolean b3 = !(student.getCourses().contains(courseSlot.getCourse()));
//					boolean b4 = found.contains(courseSlot.getCourse().getName());
//					if(  b1	&& b2 && (b3 || b4)){
//						courseSlot.addStudent(student);
//						student.addSlot(courseSlot);
//						found.add(courseSlot.getCourse().getName());
//						backtrace.push(student);
//					}
				}
				
				// Fall Student konnte nicht gesetzt werden
				if (student.getMySlots().size() != student.getCourses().size()) {
					//remove actual Stud from backtrace and save for new try
					for (CourseSlot StudentSlot : student.getMySlots()) {
						StudentSlot.removeStudent(student);
					}
					student.removeAllSlots();
					while(backtrace.remove(student));
					backStuds.add(student);
					
					//undo previous step
					Student prevStud = backtrace.pop();
					
					for (CourseSlot StudentSlot : prevStud.getMySlots()) {
						StudentSlot.removeStudent(prevStud);
					}
					student.removeAllSlots();
					while(backtrace.remove(prevStud));
					backStuds.add(prevStud);
				}else{
					tempStuds.remove(student);
				}
				if (finishCount.getCount() >= stopCount)
					return 1;
			}
			
			tempStuds = new LinkedList<Student>();
			tempStuds.addAll(backStuds);
			backStuds = new LinkedList<Student>();
		}
		
		return 0;
	}
}
