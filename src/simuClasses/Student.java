package simuClasses;

import java.util.LinkedList;
import java.util.List;

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
}
