package mainProg.gui.studViewParts;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;

public class StudentTableHead extends Composite {
	public Label headLabel;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StudentTableHead(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
		
		headLabel = new Label(this, SWT.NONE);
		headLabel.setAlignment(SWT.CENTER);
		
		Composite labs = new Composite(this, SWT.NONE);
		labs.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblCourse = new Label(labs, SWT.NONE);
		//lblCourse.setBounds(10, 10, 55, 15);
		lblCourse.setText("Course");
		lblCourse.setAlignment(SWT.CENTER);
		
		Label lblStudents = new Label(labs, SWT.NONE);
		//lblStudents.setBounds(10, 31, 55, 15);
		lblStudents.setText("Students");
		lblStudents.setAlignment(SWT.CENTER);
		
		Label lblTotHap = new Label(labs, SWT.NONE);
		lblTotHap.setToolTipText("Amount of total happiness.");
		//lblTotHap.setBounds(10, 52, 55, 15);
		lblTotHap.setText("Val. Hap.");
		lblTotHap.setAlignment(SWT.CENTER);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
