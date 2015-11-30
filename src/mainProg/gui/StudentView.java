package mainProg.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import simuClasses.CourseSlot;
import simuClasses.StudCollection;
import simuClasses.Student;

public class StudentView {
	protected Object result;
	private Shell shell;
	private String[] COURSES = {"A", "B", "C"};
	StudCollection SC;
	Display display;
	Color yellow;
	Color red;
	
	@SuppressWarnings("static-access")
	public StudentView(StudCollection sc){
		this.SC = sc;
		shell = new Shell();
		shell.setText("Student View");
		display = display.getCurrent();
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
	    shell.setSize(300, 600);
	    shell.setLayout(new FillLayout());
	    shell.setText("Students Slots View");
	    

	    Table table = new Table(shell, SWT.BORDER | SWT.V_SCROLL);
	    table.setHeaderVisible(true);
	    String[] titles = { "StudID", "Course A", "Course B", "Course C" };

	    for (int i = 0; i < titles.length; i++) {
	      TableColumn column = new TableColumn(table, SWT.NULL);
	      column.setText(titles[i]);
	    }

	    for(Student s : SC.getAllStuds()){
	    	TableItem item = new TableItem(table, SWT.NULL);
	    	item.setText(0, s.getID() + "");
	    	
	    	for(int i = 1; i < 4; i++){
				item.setText(i, "NONE");
				for(CourseSlot cs : s.getMySlots()){
					if(cs.getCourse().getName() == COURSES[i-1]){
						item.setText(i,cs.getTimeSlot() + "");
					}
				}
			}
	    	if(item.getText(1).equals(item.getText(2)) || item.getText(1).equals(item.getText(3)) || item.getText(2).equals(item.getText(3))){
	    		if((item.getText(1) != "NONE" && item.getText(2) != "NONE") || (item.getText(1) != "NONE" && item.getText(3) != "NONE") || (item.getText(2) != "NONE" && item.getText(3) != "NONE")){
		    		item.setBackground(red);
		    	}
	    	}
	    	
	    }

	    for (int i = 0; i < titles.length; i++) {
	      table.getColumn(i).pack();
	    }


	  }
}
