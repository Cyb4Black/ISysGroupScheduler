package searchClasses;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import simuClasses.*;

public class LocalBeamSearchThread extends Thread {
	private StudCollection MyStuds;
	private TimeTable MyTimeTable;
	private List<TimeTable> resultTableSet;
	private List<StudCollection> resultStudSet;

	public LocalBeamSearchThread(StudCollection SC, TimeTable TT,
			List<TimeTable> rTS, List<StudCollection> rSS) {
		this.MyStuds = SC;
		this.MyTimeTable = TT;
		this.resultTableSet = rTS;
		this.resultStudSet = rSS;

	}

	public void run() {
		double HappinessOld;
		double HappinessNew;
		TimeTable myResultTable = MyTimeTable.clone();
		StudCollection myTempCollection = MyStuds.clone();
		
		threadSafeClone(myResultTable, myTempCollection);
		
		List<CourseSlot> allCourseSlots = myResultTable.getAllCourses();
		Student tempStud1;
		Student tempStud2;
		List<CourseSlot> tempSlots1 = new LinkedList<CourseSlot>();
		List<CourseSlot> tempSlots2 = new LinkedList<CourseSlot>();
		Random myRand = new Random();

		// for (CourseSlot CS1 : allCourseSlots) {
		tempSlots1.addAll(allCourseSlots);
		while (!(tempSlots1.isEmpty())) {
			CourseSlot CS1 = tempSlots1.get(myRand.nextInt(tempSlots1.size()));

			// for (CourseSlot CS2 : allCourseSlots) {
			tempSlots2.addAll(allCourseSlots);
			while (!(tempSlots2.isEmpty())) {
				CourseSlot CS2 = tempSlots2.get(myRand.nextInt(tempSlots2
						.size()));

				// Fall CS1 und CS2 haben den selben Kurs sind aber nicht der
				// selbe Praktikumstermin
				if (CS1.getCourse() == CS2.getCourse() && CS1 != CS2) {
					do {
						HappinessOld = CS1.getHappiness() + CS2.getHappiness();

						tempStud1 = getLeastHappyFreeStud(CS1,
								CS2.getTimeSlot());
						tempStud2 = getLeastHappyFreeStud(CS2,
								CS1.getTimeSlot());

						swapStud(CS1, tempStud1, CS2, tempStud2);

						HappinessNew = CS1.getHappiness() + CS2.getHappiness();

					} while (HappinessNew > HappinessOld);

					swapStud(CS2, tempStud1, CS1, tempStud2);
				}
				tempSlots2.remove(CS2);
//				System.out.println(this.getId() + ": TS2 "
//						+ (allCourseSlots.size() - tempSlots2.size()) + " von "
//						+ allCourseSlots.size() + "fertig.");
			}
			tempSlots1.remove(CS1);
			System.out.println(this.getId() + ": TS1 "
					+ (allCourseSlots.size() - tempSlots1.size()) + " von "
					+ allCourseSlots.size() + "fertig.");
		}

		resultTableSet.add(myResultTable);
		resultStudSet.add(myTempCollection);

	}

	private void threadSafeClone(TimeTable myResultTable,
			StudCollection myTempCollection) {
		for (CourseSlot cs : MyTimeTable.getAllCourses()) {
			CourseSlot csCloned = myResultTable.getAllCourses().get(
					MyTimeTable.getAllCourses().indexOf(cs));
			for (Student s : cs.getStudents()) {
				for (Student sCloned : myTempCollection.getAllStuds()) {
					if (s.getID() == sCloned.getID()) {
						csCloned.addStudent(sCloned);
						sCloned.getMySlots().add(csCloned);
						break;
					}
				}
			}

		}
	}

	/**
	 * Eine Hilfsmethode welche zwei Studenten in zwei Praktikumsterminen
	 * tauscht
	 * 
	 * @param CS1
	 *            Der erste Praktikumstermin
	 * @param stud1
	 *            Der Student aus dem ersten Praktikumstermin
	 * @param CS2
	 *            Der zweite Praktikumstermin
	 * @param stud2
	 *            Der Student aus dem zweiten Praktikumstermin
	 * @return true wenn getauscht wurde, false wenn nicht
	 */
	private boolean swapStud(CourseSlot CS1, Student stud1, CourseSlot CS2,
			Student stud2) {
		if (stud1 == null || stud2 == null) {
			return false;
		}
		CS1.removeStudent(stud1);
		stud1.getMySlots().remove(CS1);
		
		CS1.addStudent(stud2);
		stud2.getMySlots().add(CS1);
		
		CS2.removeStudent(stud2);
		stud2.getMySlots().remove(CS2);
		
		CS2.addStudent(stud1);
		stud1.getMySlots().add(CS2);

		return true;

	}

	/**
	 * Eine Hilfsmethode, welche ausrechnet wie glücklich ein Student in einem
	 * Praktikumstermin ist
	 * 
	 * @param CS
	 *            der zu überprüfende Praktikumstermin
	 * @param stud1
	 *            der zu überprüfende Student
	 * @return den Glückswert als double
	 */
	private double getStudHappiness(CourseSlot CS, Student stud1) {
		HappinessList HL = CS.getHappyMatrix();
		double StudHappiness = 0;

		for (Student stud2 : CS.getStudents()) {
			if (stud1.getID() != stud2.getID()) {
				StudHappiness += HL.getHpById(stud1.getID(), stud2.getID())
						.getHappiness();
			}
		}

		return StudHappiness;
	}

	/**
	 * Eine Hilfsmethode welche den unglücklichsten Student aus einem
	 * Praktikumstermin zurück gibt welcher in einen bestimmten ZeitSlot
	 * wechseln kann
	 * 
	 * @param CS
	 *            der zu überprüfende Praktikumstermin
	 * @param i
	 *            der bestimmte ZeitSlot
	 * @return den unglücklichsten Student oder ein Null Objekt falls kein
	 *         Student in den bestimmten ZeitSlot tauschen kann
	 */
	private Student getLeastHappyFreeStud(CourseSlot CS, int i) {
		Student LeastHappyStud = null;

		for (Student stud : CS.getStudents()) {
			if (LeastHappyStud == null) {
				LeastHappyStud = stud;
			} else if (getStudHappiness(CS, LeastHappyStud) > getStudHappiness(
					CS, stud) && stud.gotTime(i)) {
				LeastHappyStud = stud;
			}
		}
		return LeastHappyStud;
	}

}
