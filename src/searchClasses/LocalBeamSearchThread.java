package searchClasses;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import simuClasses.*;

public class LocalBeamSearchThread extends Thread {
	private StudCollection MyStuds;
	private TimeTable MyTimeTable;
	private List<TimeTable> resultTableSet;
	private List<StudCollection> resultStudSet;
	private boolean overPower;

	public LocalBeamSearchThread(StudCollection SC, TimeTable TT,
			List<TimeTable> rTS, List<StudCollection> rSS, boolean op) {
		this.MyStuds = SC;
		this.MyTimeTable = TT;
		this.resultTableSet = rTS;
		this.resultStudSet = rSS;
		this.overPower = op;
	}

	public void run() {
		boolean swap;
		TimeTable myResultTable = MyTimeTable.clone();
		StudCollection myTempCollection = MyStuds.clone();

		threadSafeClone(myResultTable, myTempCollection);

		List<CourseSlot> allCourseSlots = myResultTable.getAllCourses();
		List<CourseSlot> tempSlots1 = new LinkedList<CourseSlot>();
		List<CourseSlot> tempSlots2 = new LinkedList<CourseSlot>();
		Random myRand = new Random();
		if (!overPower) {
			// for (CourseSlot CS1 : allCourseSlots) {
			tempSlots1.addAll(allCourseSlots);
			while (!(tempSlots1.isEmpty())) {
				CourseSlot CS1 = tempSlots1.get(myRand.nextInt(tempSlots1
						.size()));

				// for (CourseSlot CS2 : allCourseSlots) {
				tempSlots2.addAll(allCourseSlots);
				while (!(tempSlots2.isEmpty())) {
					CourseSlot CS2 = tempSlots2.get(myRand.nextInt(tempSlots2
							.size()));

					// Fall CS1 und CS2 haben den selben Kurs sind aber nicht
					// der
					// selbe Praktikumstermin
					if (CS1.getCourse() == CS2.getCourse() && CS1 != CS2) {
						do {

							swap = swapLeastHappyStud(CS1, CS2);

						} while (swap);

					}
					tempSlots2.remove(CS2);
					// System.out.println(this.getId() + ": TS2 "
					// + (allCourseSlots.size() - tempSlots2.size()) + " von "
					// + allCourseSlots.size() + "fertig.");
				}
				tempSlots1.remove(CS1);
				// System.out.println(this.getId() + ": TS1 "
				// + (allCourseSlots.size() - tempSlots1.size()) + " von "
				// + allCourseSlots.size() + "fertig.");
			}
		}

		// for (CourseSlot CS1 : allCourseSlots) {
		if (overPower) {// only use this part for REALLY optimized search!
			tempSlots1.addAll(allCourseSlots);
			while (!(tempSlots1.isEmpty())) {
				CourseSlot CS1 = tempSlots1.get(myRand.nextInt(tempSlots1
						.size()));

				// for (CourseSlot CS2 : allCourseSlots) {
				tempSlots2.addAll(allCourseSlots);
				while (!(tempSlots2.isEmpty())) {
					CourseSlot CS2 = tempSlots2.get(myRand.nextInt(tempSlots2
							.size()));

					// Fall CS1 und CS2 haben den selben Kurs sind aber nicht
					// der
					// selbe Praktikumstermin
					if (CS1.getCourse() == CS2.getCourse() && CS1 != CS2) {
						do {

							swap = swapEveryStud(CS1, CS2);

						} while (swap);

					}
					tempSlots2.remove(CS2);
					// System.out.println(this.getId() + ": TS2 "
					// + (allCourseSlots.size() - tempSlots2.size()) + " von "
					// + allCourseSlots.size() + "fertig.");
				}
				tempSlots1.remove(CS1);
				// System.out.println(this.getId() + ": TS1 "
				// + (allCourseSlots.size() - tempSlots1.size()) + " von "
				// + allCourseSlots.size() + "fertig.");
			}
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
	 * Eine Hilfsmethode welche zwei Studenten von zwei Praktikumsterminen
	 * tauscht. Falls dies zu einem h�heren Gl�ckswert f�hrt.
	 * 
	 * @param CS1
	 *            der erste Praktikumstermin
	 * @param CS2
	 *            der zweite Praktikumstermin
	 * @return true wenn getauscht wurde, false wenn nicht
	 */
	private boolean swapEveryStud(CourseSlot CS1, CourseSlot CS2) {

		for (Student stud1 : CS1.getStudents()) {

			for (Student stud2 : CS2.getStudents()) {
				if ((getTheoryHappiness(CS1, stud1, stud1)
						+ getTheoryHappiness(CS2, stud2, stud2) < getTheoryHappiness(
						CS1, stud2, stud1)
						+ getTheoryHappiness(CS2, stud1, stud2))
						&& stud1.gotTime(CS2.getTimeSlot())
						&& stud2.gotTime(CS1.getTimeSlot())) {

					return swapStud(CS1, stud1, CS2, stud2);
				}
			}

		}

		return false;
	}

	/**
	 * Eine Hilfsmethode welche die jeweiligen Ungl�cklichsten Studenten von
	 * zwei Praktikumsterminen tauscht. Falls dies zu einem h�heren Gl�ckswert
	 * f�hrt.
	 * 
	 * @param CS1
	 *            Der erste Praktikumstermin
	 * @param CS2
	 *            Der zweite Praktikumstermin
	 * @return true wenn getauscht wurde, false wenn nicht
	 */
	private boolean swapLeastHappyStud(CourseSlot CS1, CourseSlot CS2) {
		Student LeastHappyStud1 = getLeastHappyFreeStud(CS1, CS2.getTimeSlot());
		Student LeastHappyStud2 = getLeastHappyFreeStud(CS2, CS1.getTimeSlot());

		if (getTheoryHappiness(CS1, LeastHappyStud1, LeastHappyStud1)
				+ getTheoryHappiness(CS2, LeastHappyStud2, LeastHappyStud2) < getTheoryHappiness(
				CS1, LeastHappyStud2, LeastHappyStud1)
				+ getTheoryHappiness(CS2, LeastHappyStud1, LeastHappyStud2)) {

			return swapStud(CS1, LeastHappyStud1, CS2, LeastHappyStud2);
		}

		return false;
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
	 * Eine Hilfsmethode, welche ausrechnet wie gl�cklich ein Student in einem
	 * Praktikumstermin ist
	 * 
	 * @param CS
	 *            der zu �berpr�fende Praktikumstermin
	 * @param stud1
	 *            der zu �berpr�fende Student
	 * @return den Gl�ckswert als double
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
	 * Eine Hilfsmethode welche den ungl�cklichsten Student aus einem
	 * Praktikumstermin zur�ck gibt welcher in einen bestimmten ZeitSlot
	 * wechseln kann
	 * 
	 * @param CS
	 *            der zu �berpr�fende Praktikumstermin
	 * @param i
	 *            der bestimmte ZeitSlot
	 * @return den ungl�cklichsten Student oder ein Null Objekt falls kein
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

	/**
	 * Eine Hilfsmethode welche die theoretischen neuen Gl�ckswerte f�r eine
	 * Gruppe ausrechnet, falls der newStud mit dem oldStud tauscht.
	 * 
	 * @param CS
	 *            der zu �berpr�fende Praktikumstermin
	 * @param newStud
	 *            der Student f�r den die Gl�ckswerte ausgerechnet werden
	 * @param oldStud
	 *            der Student welcher in der Gruppe ignoriert wird
	 * @return die summe der Gl�ckswerte
	 */
	private double getTheoryHappiness(CourseSlot CS, Student newStud,
			Student oldStud) {
		HappinessList HL = CS.getHappyMatrix();
		double TheoryHappiness = 0;

		for (Student stud : CS.getStudents()) {
			if (stud.getID() != newStud.getID()
					&& stud.getID() != oldStud.getID()) {
				TheoryHappiness += HL.getHpById(stud.getID(), newStud.getID())
						.getHappiness();
			}
		}

		return TheoryHappiness;

	}
}