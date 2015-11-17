package mainProg.gui.timeTableParts;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;

public class TimeTableHead extends Composite {
	public Label headLabel;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TimeTableHead(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		headLabel = new Label(this, SWT.NONE);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
