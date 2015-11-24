package mainProg.gui;

import mainProg.gui.studViewParts.StudentTableTemplate;
import mainProg.gui.timeTableParts.TimeTableSlot;
import mainProg.gui.timeTableParts.TimeTableTemplate;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;

import simuClasses.CourseSlot;
import simuClasses.StudCollection;
import simuClasses.TimeTable;

import org.eclipse.swt.layout.FillLayout;

public class StudentView {

	protected Object result;
	protected Shell shell;
	private StudentTableTemplate table;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public StudentView(StudCollection SC) {
		shell = new Shell();
		shell.setText("Student View");
		createContents(SC);
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
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

	/**
	 * Create contents of the dialog.
	 */
	private void createContents(StudCollection SC) {
		//shell = new Shell(getParent(), getStyle());
		shell.setSize(400, 240);
		//shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		table = new StudentTableTemplate(shell, SWT.NONE, SC);

	}
	
	
}
