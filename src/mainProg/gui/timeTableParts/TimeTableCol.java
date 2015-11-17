package mainProg.gui.timeTableParts;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class TimeTableCol extends Composite {
	public TimeTableHead head;
	public TimeTableBlock block1;
	public TimeTableBlock block2;
	public TimeTableBlock block3;
	public TimeTableBlock block4;
	public TimeTableBlock block5;
	public TimeTableBlock block6;
	public List<TimeTableBlock> blocks;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public TimeTableCol(Composite parent, int style) {
		super(parent, SWT.BORDER);
		setLayout(new FillLayout(SWT.VERTICAL));

		head = new TimeTableHead(this, SWT.NONE);

		blocks = new LinkedList<TimeTableBlock>();
		for(int i = 0; i < 6; i++){
			blocks.add(new TimeTableBlock(this, SWT.NONE));
		}

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
