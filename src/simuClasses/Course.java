package simuClasses;

import java.util.LinkedList;
import java.util.List;

/**
 * Eine Klasse, welche ein Fach simuliert. Sie enthält einen Namen, die Wahrscheinlichkeit mit dem
 * das Fach belegt wird, eine Liste mit allen zugehörigen Praktikumsterminen und eine Liste mit den Studenten
 * die dieses Fach belegen.
 * 
 * @author Willnow & Selle
 *
 */
public class Course {
	private String Name;
	private double Chance;
	private List<CourseSlot> MySlots = new LinkedList<CourseSlot>();
	private List<Student> MyStudents = new LinkedList<Student>();
	
	public Course(String name){
		this.Name = name;
	}
	
	public String getName(){
		return Name;
	}

	public List<Student> getStudents() {
		return MyStudents;
	}

	public void setStudents(List<Student> myStudents) {
		MyStudents = myStudents;
	}
	
	public void addStudent(Student s){
		MyStudents.add(s);
	}

	public List<CourseSlot> getMySlots() {
		return MySlots;
	}

	public void addSlot(CourseSlot cs) {
		MySlots.add(cs);
	}

	public double getChance() {
		return Chance;
	}

	public void setChance(double chance) {
		Chance = chance;
	}
}
