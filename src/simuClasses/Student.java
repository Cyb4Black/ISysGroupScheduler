package simuClasses;

import java.util.LinkedList;
import java.util.List;

public class Student {
	private int ID;
	private List<Course> Courses = new LinkedList<Course>();
	private List<CourseSlot> MySlots = new LinkedList<CourseSlot>();
	private List<HappinessPairing> myPairings = new LinkedList<HappinessPairing>();
	
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
}
