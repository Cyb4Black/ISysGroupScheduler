package mainProg.gui.timeTableParts;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;

import simuClasses.CourseSlot;

public class TimeTableSlot extends Composite {
	public CourseSlot myCourse;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TimeTableSlot(Composite parent, int style, CourseSlot CS) {
		super(parent, style);
		myCourse = CS;
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label courseName = new Label(this, SWT.NONE);
		courseName.setAlignment(SWT.CENTER);
		//courseName.setBounds(64, 10, 55, 15);
		courseName.setText(myCourse.getCourse().getName());
		
		Label courseStuds = new Label(this, SWT.NONE);
		courseStuds.setAlignment(SWT.CENTER);
		//courseStuds.setBounds(64, 31, 55, 15);
		courseStuds.setText(myCourse.getStudents().size() + "");
		
		Label courseHappiness = new Label(this, SWT.NONE);
		courseHappiness.setAlignment(SWT.CENTER);
		//courseHappiness.setBounds(64, 52, 55, 15);
		
		//courseHappiness.setText(myCourse.getHappiness() + "");
		courseHappiness.setText(String.format("%.2g%n", myCourse.getHappiness()));

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
