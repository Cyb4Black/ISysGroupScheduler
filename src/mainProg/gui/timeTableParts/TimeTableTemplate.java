package mainProg.gui.timeTableParts;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;

public class TimeTableTemplate extends Composite {
	private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	public List<TimeTableCol> cols;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TimeTableTemplate(Composite parent, int style) {
		super(parent, style);
		setSize(400,240);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		cols = new LinkedList<TimeTableCol>();
		for(int i = 0; i < 5; i++){
			cols.add(new TimeTableCol(this, SWT.NONE));
			cols.get(i).head.headLabel.setText(days[i]);
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
