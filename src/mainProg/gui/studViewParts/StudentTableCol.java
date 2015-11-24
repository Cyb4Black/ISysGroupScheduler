package mainProg.gui.studViewParts;

import java.util.LinkedList;
import java.util.List;

import javax.swing.text.AbstractDocument.Content;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class StudentTableCol extends ScrolledComposite {
	public List<StudentTableBlock> blocks;
	

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public StudentTableCol(Composite parent, int style) {
		super(parent, SWT.BORDER);
		setSize(parent.getSize());
		setLayout(new FillLayout(SWT.VERTICAL));

		blocks = new LinkedList<StudentTableBlock>();

		

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}