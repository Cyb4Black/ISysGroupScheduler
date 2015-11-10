package simuClasses;

import java.util.LinkedList;
import java.util.List;

public class Course {
	private String Name;
	private List<CourseSlot> MySlots = new LinkedList<CourseSlot>();
	private List<Student> MyStudents = new LinkedList<Student>();
	
	public Course(String name){
		this.Name = name;
	}
	
	public String getName(){
		return Name;
	}

	public List<Student> getMyStudents() {
		return MyStudents;
	}

	public void setMyStudents(List<Student> myStudents) {
		MyStudents = myStudents;
	}

	public List<CourseSlot> getMySlots() {
		return MySlots;
	}

	public void generateMySlots() {
		
	}
}
