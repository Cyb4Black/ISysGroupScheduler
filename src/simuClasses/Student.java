package simuClasses;

import java.util.LinkedList;
import java.util.List;

public class Student {
	private int ID;
	private List<Course> Courses;
	private List<CourseSlot> MySlots = new LinkedList<CourseSlot>();
	
	public Student(int id, List<Course> courses){
		this.ID = id;
		this.setCourses(courses);
	}

	public List<Course> getCourses() {
		return Courses;
	}

	public void setCourses(List<Course> courses) {
		Courses = courses;
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
}
