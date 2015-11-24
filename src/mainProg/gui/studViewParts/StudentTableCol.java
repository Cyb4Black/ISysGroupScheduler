package mainProg.gui.studViewParts;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class StudentTableCol extends Composite {
	public StudentTableHead head;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public StudentTableCol(Composite parent, int style) {
		super(parent, SWT.BORDER);
		setLayout(new FillLayout(SWT.VERTICAL));

		head = new StudentTableHead(this, SWT.NONE);

		

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
