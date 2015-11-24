package mainProg.gui.studViewParts;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class StudentTableBlock extends Composite {
	public List<StudentTableSlot> slots;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StudentTableBlock(Composite parent, int style) {
		super(parent, SWT.BORDER);
		setSize(parent.getSize().x, 21);
		setLayout(new FillLayout(SWT.VERTICAL));
		
		slots = new LinkedList<StudentTableSlot>();
		for(int i = 0; i < 4; i++){
			slots.add(new StudentTableSlot(this, SWT.BORDER));
		}
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	

}
