package mainProg.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import searchClasses.DeepSearchCore;
import simuClasses.*;

public abstract class AbstractMain {
	
	private String[] courseNames = {"A","B","C"};
	private int[] courseChances = {80,90,85};
	private int OVERLAP;
	private int STUDENTS = 80;
	private int MAXSTUDSPERCOURSESLOT = 12;
	private int BLOCKCOUNT = 30;
	private Random myRandom = new Random();
//--------------------------------------------------------
	
	private List<Course> COURSES;
	private StudCollection studsToManage;
	private HappinessList happyMatrix;
	//private TimeTable resultTable;
	private TimeTable initialTable;
	TimeTable finalTable;
	private List<Integer> usedSlots;
	
//--------------------------------------------------------
	
	public void init(int ol){
		OVERLAP = ol;
		COURSES = new LinkedList<Course>();
		studsToManage = new StudCollection();
		happyMatrix = new HappinessList();
		initialTable = new TimeTable();
		finalTable = new TimeTable();
		usedSlots = new LinkedList<Integer>();
		//---------------------------------------------------
		generateCourses();
		generateStudents();
		createInitialTable();
		for(CourseSlot cs : initialTable.getAllCourses()){
			cs.initializePairings(happyMatrix);
		}
	}
	
	public void startSearch(boolean ignoreHappiness){
		DeepSearchCore DSC = new DeepSearchCore();
		DSC.generateDeepSearch(studsToManage, ignoreHappiness, initialTable, finalTable);
		for(CourseSlot cs : finalTable.getAllCourses()){
			cs.initializePairings(happyMatrix);
		}
	}
	
	private void createInitialTable(){
		for(Course c : COURSES){
			if(initialTable.getAllCourses().isEmpty()){
				initializeInitialTable(c);
			}else{
				createInitialTable(c);
			}
		}
	}
	
	public TimeTable getEmptyTable(){
		return initialTable;
	}
	
	public TimeTable getFinalTable(){
		return finalTable;
	}
	
	private void createInitialTable(Course c){
		int slotCount = (c.getStudents().size() / MAXSTUDSPERCOURSESLOT) + ((c.getStudents().size() % MAXSTUDSPERCOURSESLOT == 0) ? 0 : 1);
		
		int newSlotNo;
		
		for(int i = 0; i < slotCount; i++){
			newSlotNo = myRandom.nextInt(BLOCKCOUNT);
			boolean self = false;
			if(i < OVERLAP){
				for(CourseSlot ts : c.getMySlots()){
					if(ts.getTimeSlot() == newSlotNo){
						self = true;
						break;
					}
				}
				while(!usedSlots.contains(newSlotNo) ||	self){
					newSlotNo = myRandom.nextInt(BLOCKCOUNT);
					self = false;
					for(CourseSlot ts : c.getMySlots()){
						if(ts.getTimeSlot() == newSlotNo){
							self = true;
							break;
						}
					}
				}
				c.addSlot(new CourseSlot(c, newSlotNo, MAXSTUDSPERCOURSESLOT));
			}else{
				while(usedSlots.contains(newSlotNo)){
					newSlotNo = myRandom.nextInt(BLOCKCOUNT);
				}
				
				c.addSlot(new CourseSlot(c, newSlotNo, MAXSTUDSPERCOURSESLOT));
				usedSlots.add(newSlotNo);
			}
		}
		initialTable.addAllCourseSlots(c.getMySlots());
	}
	
	private void initializeInitialTable(Course c){
		initialTable = new TimeTable();
		int slotCount = (c.getStudents().size() / MAXSTUDSPERCOURSESLOT) + ((c.getStudents().size() % MAXSTUDSPERCOURSESLOT == 0) ? 0 : 1);
		int newSlotNo = myRandom.nextInt(BLOCKCOUNT);
		c.addSlot(new CourseSlot(c, newSlotNo, MAXSTUDSPERCOURSESLOT));
		usedSlots.add(newSlotNo);
		for(int i = 1; i < slotCount; i++){			
			while(usedSlots.contains(newSlotNo)){
				newSlotNo = myRandom.nextInt(BLOCKCOUNT);
			}
			
			c.addSlot(new CourseSlot(c, newSlotNo, MAXSTUDSPERCOURSESLOT));
			usedSlots.add(newSlotNo);
		}
		initialTable.addAllCourseSlots(c.getMySlots());
	}
	
	private void generateCourses(){
		for(int i = 0; i < courseNames.length; i++){
			COURSES.add(new Course(courseNames[i]));
			COURSES.get(i).setChance(courseChances[i]);
		}
	}
	
	private void generateStudents(){
				
		for(int i = 0; i < STUDENTS; i++){
			Student newStudent = new Student(i);			
			for(Course c : COURSES){
				if(myRandom.nextInt(100) <= c.getChance()){
					newStudent.addCourse(c);
					c.addStudent(newStudent);
				}
			}
			studsToManage.addStudent(newStudent);
			happyMatrix.generatePairing(newStudent.getID(), studsToManage.getAllStuds());
		}
	}
	
	@SuppressWarnings("unused")
	private void putStudents(){
		List<Student> tempStuds =studsToManage.getThreeCourseStuds();
		Stack<Student> putStuds = new Stack<Student>();
		List<CourseSlot> AllCourses =initialTable.getAllCourses();
		
		for (Student student : tempStuds) {
			for (CourseSlot courseSlot : AllCourses) {
				if(student.getMySlots().isEmpty() && !(courseSlot.isFilled())){
					courseSlot.addStudent(student);
					student.addSlot(courseSlot);
					putStuds.push(student);
					tempStuds.remove(student);
				}
				for (CourseSlot StudentSlots : student.getMySlots()){
					if(StudentSlots.getCourse()!=courseSlot.getCourse() && StudentSlots.getTimeSlot() != courseSlot.getTimeSlot() && !(courseSlot.isFilled())){
						courseSlot.addStudent(student);
						student.addSlot(courseSlot);
						putStuds.push(student);
						tempStuds.remove(student);
					}
				}
			}
			if(student.getMySlots().size()!=student.getCourses().size()){
				
			}
		}
		
	}

}
