package mainProg.gui.timeTableParts;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class TimeTableBlock extends Composite {
	List<Composite> slots;
	int blockNo;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TimeTableBlock(Composite parent, int style, int overlap, int no) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		slots = new LinkedList<Composite>();
		blockNo = no;
		for(int i = 0; i < overlap; i++){
			slots.add(new TimeTableSlot(this, SWT.NONE));
		}
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
