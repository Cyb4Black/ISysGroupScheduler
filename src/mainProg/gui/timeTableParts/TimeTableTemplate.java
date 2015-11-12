package mainProg.gui.timeTableParts;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;

public class TimeTableTemplate extends Composite {
	public Composite colMon;
	public Composite colTue;
	public Composite colWed;
	public Composite colThu;
	public Composite colFri;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TimeTableTemplate(Composite parent, int style) {
		super(parent, style);
		setSize(400,240);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		colMon = new TimeTableCol(this, SWT.NONE);
		colTue = new TimeTableCol(this, SWT.NONE);
		colWed = new TimeTableCol(this, SWT.NONE);
		colThu = new TimeTableCol(this, SWT.NONE);
		colFri = new TimeTableCol(this, SWT.NONE);
		

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
