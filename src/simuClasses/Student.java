package simuClasses;

import java.util.LinkedList;
import java.util.List;

/**
 * Eine Klasse, welche einen Studenten simuliert.
 * Der Student enthält seine ID, seine Fächer und seine Praktikumstermine
 *
 * @author Willnow & Selle
 */

public class Student {
	private int ID;
	private List<Course> Courses = new LinkedList<Course>();
	private List<CourseSlot> MySlots = new LinkedList<CourseSlot>();
	
	public Student(int id){
		this.ID = id;
	}

	public List<Course> getCourses() {
		return Courses;
	}

	public void addCourse(Course c) {
		Courses.add(c);
	}

	public List<CourseSlot> getMySlots() {
		return MySlots;
	}

	public void setMySlots(List<CourseSlot> mySlots) {
		MySlots = mySlots;
	}
	
	public int getID(){
		return ID;
	}

	public void addSlot(CourseSlot courseSlot) {
		MySlots.add(courseSlot);
		
	}
	
	public void removeSlot(CourseSlot courseSlot){
		MySlots.remove(courseSlot);
	}
	
	public void removeAllSlots(){
		MySlots = new LinkedList<CourseSlot>();
	}
	
	/**
	 * Eine Hilfsmethode die bestimmt ob ein Student an einem TimeSlot frei hat.
	 * @param i der zu überprüfenden TimeSlot
	 * @return true wenn der Student frei hat, sonst false
	 */
	public boolean gotTime(int i){
		boolean Time=true;
		for (CourseSlot cs : MySlots) {
			if(cs.getTimeSlot()==i){
				Time=false;
			}
		}
		return Time;
		
	}
}
