package mainProg.gui.studViewParts;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;

public class StudentTableHead extends Composite {
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StudentTableHead(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite labs = new Composite(this, SWT.NONE);
		labs.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblStudID = new Label(labs, SWT.NONE);
		//lblCourse.setBounds(10, 10, 55, 15);
		lblStudID.setText("Student");
		lblStudID.setAlignment(SWT.CENTER);
		
		Label lblCourseA = new Label(labs, SWT.NONE);
		//lblStudents.setBounds(10, 31, 55, 15);
		lblCourseA.setText("Course A");
		lblCourseA.setAlignment(SWT.CENTER);
		
		Label lblCourseB = new Label(labs, SWT.NONE);
		lblCourseB.setToolTipText("Amount of total happiness.");
		//lblTotHap.setBounds(10, 52, 55, 15);
		lblCourseB.setText("Course B");
		lblCourseB.setAlignment(SWT.CENTER);
		
		Label lblCourseC = new Label(labs, SWT.NONE);
		lblCourseC.setToolTipText("Amount of total happiness.");
		//lblTotHap.setBounds(10, 52, 55, 15);
		lblCourseC.setText("Course C");
		lblCourseC.setAlignment(SWT.CENTER);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
