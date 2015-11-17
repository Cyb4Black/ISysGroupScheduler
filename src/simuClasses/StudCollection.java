package simuClasses;

import java.util.LinkedList;
import java.util.List;

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
		ret.allStuds.addAll(this.allStuds);
		ret.threeCourseStuds.addAll(this.threeCourseStuds);
		ret.twoCourseStuds.addAll(this.twoCourseStuds);
		ret.lazyStuds.addAll(this.lazyStuds);
		return ret;
	}
}
