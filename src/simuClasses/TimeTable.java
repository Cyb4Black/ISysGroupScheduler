package simuClasses;

import java.util.LinkedList;
import java.util.List;

/**
 * Eine Klasse, welche einen Stundenplan simuliert.
 * Ein Stundenplan enthält seine Praktikumstermine, wie oft auf ihm getauscht wurde in wievielen Durchläufen.
 * 
 * @author Willnow & Selle
 *
 */
public class TimeTable {
	/**
	 * Alle Praktikumstermine die der Stundenplan enthält.
	 */
	private List<CourseSlot> allCourses = new LinkedList<CourseSlot>();

	/**
	 * wie oft wurde auf diesem Stundenplan getauscht in wievielen Durchläufen.
	 */
	private int swaps, cycles;
	
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
		ret.setSwaps(swaps);
		ret.setCycles(cycles);
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

	/**
	 * Eine Hilfsmethode, welche den Glückswert für den Stundenplan berechnet.
	 * @return der Glückswert als double
	 */
	public double getHappiness(){
		double ret = 0;
		for(CourseSlot cs : allCourses){
			ret += cs.getHappiness();
		}
		return ret;
	}

	public int getSwaps() {
		return swaps;
	}

	public void setSwaps(int swaps) {
		this.swaps = swaps;
	}

	public int getCycles() {
		return cycles;
	}

	public void setCycles(int cycles) {
		this.cycles = cycles;
	}
}
