package simuClasses;

import java.util.LinkedList;
import java.util.List;

/**
 * Eine Klasse, welche einen Praktikumstermin simuliert. Sie enthält einen ZeitSlot, ein Fach,
 * eine maximal Anzahl an Studenten, eine Liste mit seinen Studenten und die HappinessList
 * 
 * @author Willnow & Selle
 *
 */
public class CourseSlot {
	private int TimeSlot;
	private Course myCourse;
	private int maxStuds;
	private List<Student> myStuds = new LinkedList<Student>();
	private HappinessList GlobalHappinessList;
	//private List<StudentPair> myPairs;
	
	public CourseSlot(Course c, int slotNo, int max, HappinessList HL){
		this.myCourse = c;
		this.TimeSlot = slotNo;
		this.maxStuds = max;
		this.GlobalHappinessList= HL;
	}
	
	public List<Student> getStudents(){
		return myStuds;
	}
	
	public void setStudents(List<Student> MyStuds){
		this.myStuds=MyStuds;
	}
	
	public void addStudent(Student s){
		myStuds.add(s);
	}
	
	public void removeStudent(Student s){
		myStuds.remove(s);
	}

	public int getTimeSlot() {
		return TimeSlot;
	}
	
	public int getMax(){
		return maxStuds;
	}

	public void setTimeSlot(int timeSlot) {
		TimeSlot = timeSlot;
	}
	
	public Course getCourse(){
		return myCourse;
	}
	
	public boolean isFilled(){
		return myStuds.size() == maxStuds;
	}
	
	/*public void initializePairings(HappinessList HL){
		myPairs = new LinkedList<StudentPair>();
		List<Student> tempStuds = new LinkedList<Student>();
		tempStuds.addAll(myStuds);
		
		while(!tempStuds.isEmpty()){
			StudentPair tempPair = new StudentPair();
			tempPair.setStud1(tempStuds.remove(0));
			
			if(!tempStuds.isEmpty()){
				tempPair.setStud2(tempStuds.remove(tempStuds.size()-1));
				tempPair.setOurHappiness(HL.getHpById(tempPair.getStud1().getID(), tempPair.getStud2().getID()).getHappiness());
			}else{
				tempPair.setStud2(null);
				tempPair.setOurHappiness(0.5);
			}
			myPairs.add(tempPair);
		}
	}*/
	public HappinessList getHappyMatrix(){
		return this.GlobalHappinessList;
	}
	
	/**
	 * Eine Methode, welche den Glückswert eines Praktikumstermins errechnet
	 * @return den Glückswert als double
	 */
	public double getHappiness(){
		double Happiness = 0;
		for (int i = 0; i < myStuds.size(); i++) {
			for(int j=1; j < myStuds.size(); j++){
				Happiness += GlobalHappinessList.getHpById(myStuds.get(i).getID(), myStuds.get(j).getID()).getHappiness(); 
			}
		}
		
		return Happiness;
	}
	
}
