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
	
	public void addAllCourseSlots(List<CourseSlot> cs){
		allCourses.addAll(cs);
	}
	
	public TimeTable clone(){
		TimeTable ret = new TimeTable();
		List<CourseSlot> retList = new LinkedList<CourseSlot>();
		for(CourseSlot cs : this.allCourses){
			retList.add(new CourseSlot(cs.getCourse(), cs.getTimeSlot(),cs.getMax(), cs.getHappyMatrix()));
		}
		ret.allCourses = retList;
		return ret;
	}
}
