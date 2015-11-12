package mainProg.gui.timeTableParts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class TimeTableCol extends Composite {
	public Composite head;
	public Composite block1;
	public Composite block2;
	public Composite block3;
	public Composite block4;
	public Composite block5;
	public Composite block6;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TimeTableCol(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.VERTICAL));
		
		head = new Composite(this, SWT.NONE);
		
		block1 = new Composite(this, SWT.NONE);		
		block2 = new Composite(this, SWT.NONE);		
		block3 = new Composite(this, SWT.NONE);		
		block4 = new Composite(this, SWT.NONE);		
		block5 = new Composite(this, SWT.NONE);		
		block6 = new Composite(this, SWT.NONE);
		

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
