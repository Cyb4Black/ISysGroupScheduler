package mainProg.gui.timeTableParts;

import org.eclipse.swt.widgets.Composite;

public class TimeTableSlot extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TimeTableSlot(Composite parent, int style) {
		super(parent, style);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
