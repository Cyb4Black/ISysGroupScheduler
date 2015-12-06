package searchClasses;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import simuClasses.*;

/**
 * Ein Klasse für Threads, die eine lokale BeamSearch durchführen.
 * @author Willnow & Selle
 *
 */
public class LocalBeamSearchThread extends Thread {
	private StudCollection MyStuds;
	private TimeTable MyTimeTable;
	private boolean overPower;
	private LockableResultSet results;
	
	/**
	 * Der Konstruktor für die LocalBeamSearchThreads.
	 * @param SC die Studcollection mit der gearbeitet wird.
	 * @param TT der Stundenplan auf dem gearbeitet wird.
	 * @param LRS die Ergebnisspeicherung.
	 * @param op die Flag nach welcher Art gesucht wird.
	 */
	public LocalBeamSearchThread(StudCollection SC, TimeTable TT, LockableResultSet LRS, boolean op) {
		this.MyStuds = SC;
		this.MyTimeTable = TT;
		this.results = LRS;
		this.overPower = op;
	}
	
	public void run() {
		boolean swap;
		
		//Jeder Thread klont sich seinen eigenen Stundenplan und Studcollection 
		TimeTable myResultTable = MyTimeTable.clone();
		StudCollection myTempCollection = MyStuds.clone();

		threadSafeClone(myResultTable, myTempCollection);
		
		//Erzeugung der Objekte für eine zufällige reihenfolge
		List<CourseSlot> allCourseSlots = myResultTable.getAllCourses();
		List<CourseSlot> tempSlots1 = new LinkedList<CourseSlot>();
		List<CourseSlot> tempSlots2 = new LinkedList<CourseSlot>();
		Random myRand = new Random();
		
		//Initialisierung für Zählervariablen
		int swaps = 0, cycles = 0;
		
		do{
			//Anzahl der Vertauschungen wird in den Stundenplan geschrieben
			myResultTable.setSwaps(swaps);
		
		//zufällige informierte LocalBeamSearch
		if (!overPower) {
			// for (CourseSlot CS1 : allCourseSlots) 
			tempSlots1.addAll(allCourseSlots);
			while (!(tempSlots1.isEmpty())) {
				
				CourseSlot CS1 = tempSlots1.get(myRand.nextInt(tempSlots1.size()));
				// for (CourseSlot CS2 : allCourseSlots) 
				tempSlots2.addAll(allCourseSlots);
				while (!(tempSlots2.isEmpty())) {
					CourseSlot CS2 = tempSlots2.get(myRand.nextInt(tempSlots2.size()));

					// Fall CS1 und CS2 haben den selben Kurs sind aber nicht
					// der
					// selbe Praktikumstermin
					if (CS1.getCourse() == CS2.getCourse() && CS1 != CS2) {
						do {
							swap = swapLeastHappyStud(CS1, CS2);
							if(swap)swaps++;
						} while (swap);
					}
					tempSlots2.remove(CS2);
				}
				tempSlots1.remove(CS1);

			}
		}

		// zufällige uninformierte LocalBeamSearch
		if (overPower) {// only use this part for REALLY optimized search!
			// for (CourseSlot CS1 : allCourseSlots) {
			tempSlots1.addAll(allCourseSlots);
			while (!(tempSlots1.isEmpty())) {
				CourseSlot CS1 = tempSlots1.get(myRand.nextInt(tempSlots1.size()));

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
							if(swap)swaps++;
						} while (swap);

					}
					tempSlots2.remove(CS2);
				}
				tempSlots1.remove(CS1);
			}
		}
		cycles++;
		myResultTable.setCycles(cycles);
		//Schleife wird verlassen, wenn es keine neuen Vertauschungen gab
		}while(swaps > myResultTable.getSwaps());
		//Ergebnis wird geschrieben
		results.addResults(myResultTable, myTempCollection);

	}

	
	private void threadSafeClone(TimeTable myResultTable,StudCollection myTempCollection) {
		for (CourseSlot cs : MyTimeTable.getAllCourses()) {
			CourseSlot csCloned = myResultTable.getAllCourses().get(MyTimeTable.getAllCourses().indexOf(cs));
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
	 * tauscht. Falls dies zu einem hï¿½heren Glï¿½ckswert fï¿½hrt.
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
					
				if ((getTheoryHappiness(CS1, stud1, stud1) + getTheoryHappiness(CS2, stud2, stud2) < getTheoryHappiness(CS1, stud2, stud1)+ getTheoryHappiness(CS2, stud1, stud2)) && stud1.gotTime(CS2.getTimeSlot()) && stud2.gotTime(CS1.getTimeSlot())) {

					return swapStud(CS1, stud1, CS2, stud2);
				}
			}

		}

		return false;
	}



	/**
	 * Eine Hilfsmethode welche die jeweiligen Unglï¿½cklichsten Studenten von
	 * zwei Praktikumsterminen tauscht. Falls dies zu einem hï¿½heren Glï¿½ckswert
	 * fï¿½hrt.
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
	 * Eine Hilfsmethode, welche ausrechnet wie glï¿½cklich ein Student in einem
	 * Praktikumstermin ist
	 * 
	 * @param CS
	 *            der zu ï¿½berprï¿½fende Praktikumstermin
	 * @param stud1
	 *            der zu ï¿½berprï¿½fende Student
	 * @return den Glï¿½ckswert als double
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
	 * Eine Hilfsmethode welche den unglï¿½cklichsten Student aus einem
	 * Praktikumstermin zurï¿½ck gibt welcher in einen bestimmten ZeitSlot
	 * wechseln kann
	 * 
	 * @param CS
	 *            der zu ï¿½berprï¿½fende Praktikumstermin
	 * @param i
	 *            der bestimmte ZeitSlot
	 * @return den unglï¿½cklichsten Student oder ein Null Objekt falls kein
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
	 * Eine Hilfsmethode welche die theoretischen neuen Glï¿½ckswerte fï¿½r eine
	 * Gruppe ausrechnet, falls der newStud mit dem oldStud tauscht.
	 * 
	 * @param CS
	 *            der zu ï¿½berprï¿½fende Praktikumstermin
	 * @param newStud
	 *            der Student fï¿½r den die Glï¿½ckswerte ausgerechnet werden
	 * @param oldStud
	 *            der Student welcher in der Gruppe ignoriert wird
	 * @return die summe der Glï¿½ckswerte
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
	
	/**
	 * Eine Hilfsmethode welche die theoretischen neuen Glueckswerte fuer eine
	 * Gruppe ausrechnet, falls der Student der Gruppe hinzugefï¿½gt wird oder aus ihr entfernt
	 * 
	 * @param CS der betreffende Praktikumstermin
	 * @param stud1 der betreffende Student
	 * 
	 * @return die summe der Glueckswerte
	 */
	private double getTheoryHappiness(CourseSlot CS, Student stud1) {
		HappinessList HL = CS.getHappyMatrix();
		double TheoryHappiness = 0;
		if(CS.getStudents().contains(stud1)){
			for (int i = 0; i < CS.getStudents().size(); i++) {
				for(int j=i+1; j < CS.getStudents().size(); j++){
					if(CS.getStudents().get(i).getID()!=stud1.getID() && CS.getStudents().get(j).getID()!=stud1.getID()){
						
						TheoryHappiness += HL.getHpById(CS.getStudents().get(i).getID(), CS.getStudents().get(j).getID()).getHappiness();
					
					} 
				}
			}
			return TheoryHappiness;
		}else{
			for (Student stud2 : CS.getStudents()) {
				TheoryHappiness += HL.getHpById(stud1.getID(), stud2.getID()).getHappiness();
			}

			return TheoryHappiness+CS.getHappiness();
		}
		

	}
	
	/**
	 * Eine Hilfsmethode welche einen Studenten von einen Praktikumstermin in einen anderen Praktikumstermin verschiebt
	 * 
	 * @param CS1
	 *            Der erste Praktikumstermin
	 * @param stud1
	 *            Der zu verschiebene Student
	 * @param CS2
	 *            Der zweite Praktikumstermin
	 * @return true wenn getauscht wurde, false wenn nicht
	 */
	private boolean moveStud(CourseSlot CS1, Student stud1, CourseSlot CS2) {
		if (stud1 == null) {
			return false;
		}
		CS1.removeStudent(stud1);
		stud1.getMySlots().remove(CS1);

		CS2.addStudent(stud1);
		stud1.getMySlots().add(CS2);

		return true;

	}
}