package simuClasses;

import java.util.LinkedList;
import java.util.List;

public class TimeTable {
	private List<CourseSlot> allCourses = new LinkedList<CourseSlot>();
	
	public TimeTable(){
		//stays empty
	}

	public List<CourseSlot> getAllCourses() {
		return allCourses;
	}

	public void setAllCourses(List<CourseSlot> allCourses) {
		this.allCourses = allCourses;
	}
	
	public void addCourseSlot(CourseSlot cs){
		allCourses.add(cs);
	}
	
}
