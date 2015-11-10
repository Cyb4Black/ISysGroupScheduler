package simuClasses;

import java.util.LinkedList;
import java.util.List;

public class CourseSlot {
	private int TimeSlot;
	private Course myCourse;
	private List<Student> myStuds = new LinkedList<Student>();
	
	public CourseSlot(Course c){
		this.myCourse = c;
	}
	
	public List<Student> getStudents(){
		return myStuds;
	}
	
	public void addStudent(Student s){
		myStuds.add(s);
	}

	public int getTimeSlot() {
		return TimeSlot;
	}

	public void setTimeSlot(int timeSlot) {
		TimeSlot = timeSlot;
	}
	
	public Course getCourse(){
		return myCourse;
	}
}
