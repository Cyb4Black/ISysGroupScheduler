package simuClasses;

import java.util.LinkedList;
import java.util.List;

/**
 * Eine Klasse, welche einen Stundenplan simuliert
 * 
 * @author Willnow & Selle
 *
 */
public class TimeTable {
	private List<CourseSlot> allCourses = new LinkedList<CourseSlot>();
	private StudCollection MyStuds = new StudCollection();
	private int Swaps,Moves;
	private double InitialHappiness, EndHappiness;
	
	
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
	
//	public TimeTable cloneDeep(){
//		TimeTable ret = new TimeTable();
//		List<CourseSlot> retList = new LinkedList<CourseSlot>();
//		for(CourseSlot cs : this.allCourses){
//			CourseSlot dummySlot = new CourseSlot(cs.getCourse(), cs.getTimeSlot(),cs.getMax(), cs.getHappyMatrix());
//			dummySlot.setStudents(cs.getStudents());
//			retList.add(dummySlot);
//		}
//		ret.allCourses = retList;
//		return ret;
//	}
	
	public double getHappiness(){
		double ret = 0;
		for(CourseSlot cs : allCourses){
			ret += cs.getHappiness();
		}
		return ret;
	}

	public StudCollection getMyStuds() {
		return MyStuds;
	}

	public void setMyStuds(StudCollection myStuds) {
		MyStuds = myStuds;
	}

	public int getSwaps() {
		return Swaps;
	}

	public void setSwaps(int swaps) {
		Swaps = swaps;
	}
	
	public void incSwaps(){
		Swaps ++;
	}

	public int getMoves() {
		return Moves;
	}

	public void setMoves(int moves) {
		Moves = moves;
	}

	public void incMoves(){
		Moves ++;
	}
	
	public double getInitialHappiness() {
		return InitialHappiness;
	}

	public void setInitialHappiness(double initialHappiness) {
		InitialHappiness = initialHappiness;
	}

	public double getEndHappiness() {
		return EndHappiness;
	}

	public void setEndHappiness(double endHappiness) {
		EndHappiness = endHappiness;
	}
}
