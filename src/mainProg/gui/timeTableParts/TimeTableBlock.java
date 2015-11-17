package mainProg.gui.timeTableParts;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class TimeTableBlock extends Composite {
	public List<TimeTableSlot> slots;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TimeTableBlock(Composite parent, int style) {
		super(parent, SWT.BORDER);
		setLayout(new FillLayout(SWT.VERTICAL));
		
		slots = new LinkedList<TimeTableSlot>();
		
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	

}
