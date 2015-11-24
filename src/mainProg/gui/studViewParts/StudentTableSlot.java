package mainProg.gui.studViewParts;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;

import simuClasses.CourseSlot;

public class StudentTableSlot extends Composite {
	Label lblSlotVal;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StudentTableSlot(Composite parent, int style) {
		super(parent, style);
		setSize(parent.getSize().x, 20);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		lblSlotVal = new Label(this, SWT.NONE);
		lblSlotVal.setAlignment(SWT.CENTER);
		//courseName.setBounds(64, 10, 55, 15);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
