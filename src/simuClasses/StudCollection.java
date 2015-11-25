package simuClasses;

import java.util.LinkedList;
import java.util.List;
/**
 * Eine Klasse, welche eine Menge von Studenten simuliert. Sie enthält listen mit allen Studenten,
 * Studenten mit drei Fächern, Studenten mit zwei Fächern und Studenten mit einem Fach.
 * 
 * @author Willnow & Selle
 *
 */

public class StudCollection {
	private List<Student> allStuds = new LinkedList<Student>();
	private List<Student> threeCourseStuds = new LinkedList<Student>();
	private List<Student> twoCourseStuds = new LinkedList<Student>();
	private List<Student> lazyStuds = new LinkedList<Student>();

	public void addStudent(Student student) {
		allStuds.add(student);
		switch (student.getCourses().size()) {
		case 1:
			lazyStuds.add(student);
			break;
		case 2:
			twoCourseStuds.add(student);
			break;
		case 3:
			threeCourseStuds.add(student);
			break;
		default:
			System.out.println("Error: CourseCount doesn't match any case!");
		}
	}
	
	public List<Student> getAllStuds(){
		return allStuds;
	}
	
	public void setAllLists(List<Student> all, List<Student> three, List<Student> two, List<Student> one){
		this.allStuds = all;
		this.threeCourseStuds = three;
		this.twoCourseStuds = two;
		this.lazyStuds = one;
	}
	
	public List<Student> getThreeCourseStuds(){
		return threeCourseStuds;
	}
	
	public List<Student> getTwoCourseStuds(){
		return twoCourseStuds;
	}
	
	public List<Student> getLazyStuds(){
		return lazyStuds;
	}
	
	public StudCollection clone(){
		StudCollection ret = new StudCollection();
		for(Student s : this.getAllStuds()){
			Student dummy = new Student(s.getID());
			for(Course c : s.getCourses()){
				dummy.addCourse(c);
			}
			ret.addStudent(dummy);
		}
		
		return ret;
	}
}
