package searchClasses;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import simuClasses.*;

/**
 * Eine Klasse für Threads, welche eine Tiefensuche durchführen.
 * @author Willnow & Selle
 *
 */
public class DeepSearchThread extends Thread {
	// private Stack<Student> backtrace;
	private StudCollection tempCollection;
	private TimeTable initialTable;
	private int stopCount;
	LockableCounter finishCount;
	boolean ignoreHappiness;
	LockableResultSet results;
	
	/**
	 * Konstruktor für DeepSearchThreads
	  * @param SC die Studcollection mit der gearbeitet wird.
	 * @param TT der Stundenplan auf dem gearbeitet wird.
	 * @param lockC ein lockable Counter
	 * @param iH Flag ob die Glückswerte ignoriert werden
	 * @param poolSize wieviele unterschiedlich befüllte Stundenpläne sollen erzeugt werden
	 * @param LRS Das LockableResultSet in das alle Threads schreiben.
	 */
	public DeepSearchThread(StudCollection SC, TimeTable rT,LockableCounter lockC, boolean iH, int poolSize,LockableResultSet LRS) {
		initialTable = rT;

		tempCollection = SC;
		finishCount = lockC;
		ignoreHappiness = iH;
		results = LRS;

		if (iH) {
			stopCount = 1;
		} else {
			stopCount = poolSize;
		}
	}

	public void run() {
		TimeTable myResultTable = initialTable.clone();
		List<CourseSlot> allCourseSlots = myResultTable.getAllCourses();
		Stack<Student> backtrace = new Stack<Student>();
		StudCollection myTempCollection = tempCollection.clone();

		//Zuerst werden die 3Course Studenten gesetzt
		List<Student> tempStuds = myTempCollection.getThreeCourseStuds();
		if (searchStep(tempStuds, allCourseSlots, backtrace, myTempCollection,
				myResultTable) == 1)
			return;
		
		//Dann werden die 2Course Studenten gesetzt
		tempStuds = myTempCollection.getTwoCourseStuds();
		if (searchStep(tempStuds, allCourseSlots, backtrace, myTempCollection,
				myResultTable) == 1)
			return;
		//Zuletzt werden die 1Course Studenten gesetzt
		tempStuds = myTempCollection.getLazyStuds();
		if (searchStep(tempStuds, allCourseSlots, backtrace, myTempCollection,
				myResultTable) == 1)
			return;
		//Wenn es schon genug Ergebnisse gibt --> Abbruch
		if (finishCount.pp() >= stopCount)
			return;
		//befüllter Stundenplan wird ins LockableResultSet eingetragen
		results.addResults(myResultTable, myTempCollection);

	}

	/**
	 * Eine Hilfsmethode, welche eine Liste von Studenten konfliktfrei in einem Stundenplan unterbringt.
	 * @param tempStuds Die Liste von Studenten, welche abgelegt werden.
	 * @param AllCourses Liste mit allen Praktikumsterminen
	 * @param backtrace Stack von schon gesetzten Studenten
	 * @param myTempCollection
	 * @param myResultTable
	 * @return
	 */
	private int searchStep(List<Student> tempStuds,List<CourseSlot> AllCourses, Stack<Student> backtrace,StudCollection myTempCollection, TimeTable myResultTable) {
		List<Student> backStuds = new LinkedList<Student>();
		Random myRand = new Random();
		while (!(tempStuds.isEmpty())) {
			// for (Student student : tempStuds) {
			while (!(tempStuds.isEmpty())) {
				Student student = tempStuds.get(myRand.nextInt(tempStuds.size()));
				List<CourseSlot> tempCourses = new LinkedList<CourseSlot>();
				tempCourses.addAll(AllCourses);
				List<String> found = new LinkedList<String>();
				// for (CourseSlot courseSlot : AllCourses) {
				while (!(tempCourses.isEmpty())) {
					CourseSlot courseSlot = tempCourses.get(myRand.nextInt(tempCourses.size()));
					// Fall Student hat noch keinen Termin von diesem Fach
					// UND belegt noch keinen Termin zur gleichen Uhrzeit
					// UND Termin ist noch nicht voll
					if (student.gotTime(courseSlot.getTimeSlot())&& !(courseSlot.isFilled())&& student.getCourses().contains(courseSlot.getCourse())&& !(found.contains(courseSlot.getCourse().getName()))) {
						courseSlot.addStudent(student);
						student.addSlot(courseSlot);
						found.add(courseSlot.getCourse().getName());
						backtrace.push(student);
					}
					tempCourses.remove(courseSlot);
				}

				// Fall Student konnte nicht gesetzt werden
				if (student.getMySlots().size() != student.getCourses().size()) {
					// remove actual Stud from backtrace and save for new try
					for (CourseSlot StudentSlot : student.getMySlots()) {
						StudentSlot.removeStudent(student);
					}
					student.removeAllSlots();
					while (backtrace.remove(student))
						;
					backStuds.add(student);
					if (!(backtrace.isEmpty())) {
						// undo previous step
						Student prevStud = backtrace.pop();

						for (CourseSlot StudentSlot : prevStud.getMySlots()) {
							StudentSlot.removeStudent(prevStud);
						}
						prevStud.removeAllSlots();
						while (backtrace.remove(prevStud))
							;
						backStuds.add(prevStud);
						tempStuds.remove(prevStud);
					}
					tempStuds.remove(student);
					
				} 
				// Fall Student wurde gesetzt
				else {
					tempStuds.remove(student);
				}
				//Wenn es schon genug Ergebnisse gibt --> Abbruch
				if (finishCount.getCount() >= stopCount)
					return 1;
			}
			
			//Falls vom Stack gepoppt wurde müssen diese Studenten neu gesetzt werden
			tempStuds = new LinkedList<Student>();
			tempStuds.addAll(backStuds);
			backStuds = new LinkedList<Student>();
		}

		return 0;
	}
}
