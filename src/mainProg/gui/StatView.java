package mainProg.gui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import simuClasses.Course;
import simuClasses.CourseSlot;
import simuClasses.StudCollection;
import simuClasses.Student;
import simuClasses.TimeTable;


public class StatView {
	protected Object result;
	private Shell shell;
	private String[] COURSES = {"A", "B", "C"};
	TimeTable TT;
	StudCollection SC;
	List<Course> LC;
	Display display;
	Color yellow;
	Color red;
	
	public StatView(TimeTable tt, StudCollection sc, List<Course> lc){
		this.TT = tt;
		this.SC = sc;
		this.LC = lc;
		shell = new Shell();
		shell.setText("Student View");
		display = Display.getCurrent();
		red = display.getSystemColor(SWT.COLOR_RED);
		yellow = display.getSystemColor(SWT.COLOR_YELLOW);
		createContents();
	}
	
	public Object open() {
		shell.open();
		shell.layout();
		Display display = Display.getDefault();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}
	
	private void createContents() {
	    shell.setSize(370, 140);
	    shell.setLayout(new FillLayout());
	    shell.setText("Students Slots View");
	    

	    Table table = new Table(shell, SWT.BORDER | SWT.V_SCROLL);
	    table.setHeaderVisible(true);
	    String[] titles = { "Info Title", "Course A", "Course B", "Course C", "Overall" };

	    for (int i = 0; i < titles.length; i++) {
	      TableColumn column = new TableColumn(table, SWT.NULL);
	      column.setText(titles[i]);
	    }

	    TableItem itemStudCount = new TableItem(table, SWT.NULL);
	    itemStudCount.setText(0, "Course Studs");
	    TableItem itemCourseHappiness = new TableItem(table, SWT.NULL);
	    itemCourseHappiness.setText(0, "Course Happiness");
	    double sum = 0;
	    for(int i = 1; i < 4; i++){
	    	int count = 0;
	    	double hp = 0;
	    	for(CourseSlot cs : TT.getAllCourses()){
	    		if(cs.getCourse().getName().equals(COURSES[i-1])){
	    			count += cs.getStudents().size();
	    			hp += cs.getHappiness();
	    		}
	    	}
	    	itemStudCount.setText(i, count + "/" + LC.get(i-1).getStudents().size());
	    	itemCourseHappiness.setText(i, String.format("%.5g%n", hp));
	    	sum += hp;
	    }
	    itemStudCount.setText(4, "");
	    itemCourseHappiness.setText(4, String.format("%.6g%n", sum));

	    for (int i = 0; i < titles.length; i++) {
	      table.getColumn(i).pack();
	    }


	  }
}
