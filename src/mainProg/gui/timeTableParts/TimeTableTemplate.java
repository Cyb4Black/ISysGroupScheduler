package mainProg.gui.timeTableParts;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;

public class TimeTableTemplate extends Composite {
	public TimeTableCol colMon;
	public TimeTableCol colTue;
	public TimeTableCol colWed;
	public TimeTableCol colThu;
	public TimeTableCol colFri;
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
		colMon.head.headLabel.setText("Monday");
		colTue = new TimeTableCol(this, SWT.NONE);
		colTue.head.headLabel.setText("Tuesday");
		colWed = new TimeTableCol(this, SWT.NONE);
		colWed.head.headLabel.setText("Wednesday");
		colThu = new TimeTableCol(this, SWT.NONE);
		colThu.head.headLabel.setText("Thursday");
		colFri = new TimeTableCol(this, SWT.NONE);
		colFri.head.headLabel.setText("Friday");
		

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
