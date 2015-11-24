package mainProg.gui.studViewParts;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;

public class StudentTableTemplate extends Composite {
	public StudentTableCol col;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StudentTableTemplate(Composite parent, int style) {
		super(parent, style);
		setSize(400,240);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		col = new StudentTableCol(this, SWT.NONE);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
