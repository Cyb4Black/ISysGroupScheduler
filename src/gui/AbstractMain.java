package gui;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import simuClasses.*;

public abstract class AbstractMain {
	
	String[] courseNames = {"A","B","C"};
	int[] courseChances = {80,90,85};
	int STUDENTS = 80;
//--------------------------------------------------------
	
	List<Course> COURSES = new LinkedList<Course>();
	StudCollection studsToManage = new StudCollection();
	TimeTable resultTable;
	TimeTable emptyTable;
	Random myRandom = new Random();
	
	public void createEmptyTable(){
		
	}
	
	public void generateCourses(){
		for(int i = 0; i < courseNames.length; i++){
			COURSES.add(new Course(courseNames[i]));
			COURSES.get(i).setChance(courseChances[i]);
		}
	}
	
	public void generateStudents(){
				
		for(int i = 0; i < STUDENTS; i++){
			Student newStudent = new Student(i);			
			for(Course c : COURSES){
				if(myRandom.nextInt(101) <= c.getChance()){
					newStudent.addCourse(c);
					c.addStudent(newStudent);
				}
			}
			studsToManage.addStudent(newStudent);
		}
	}
	
	

}
