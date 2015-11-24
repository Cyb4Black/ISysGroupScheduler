package mainProg.gui.studViewParts;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;

import simuClasses.*;

import org.eclipse.swt.custom.ScrolledComposite;

public class StudentTableTemplate extends Composite {
	private String[] COURSES = {"A", "B", "C"};
	public StudentTableCol col;
	private StudCollection StudCol;
	public StudentTableHead head;
	Composite content;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StudentTableTemplate(Composite parent, int style, StudCollection SC) {
		super(parent, style);
		this.StudCol = SC;
		setSize(400,240);
		setLayout(null);
		
		head = new StudentTableHead(this, SWT.NONE);
		head.setBounds(0, 0, 400, 20);
		head.setSize(this.getSize().x, 20);
		
		col = new StudentTableCol(this, SWT.NONE);
		col.setAlwaysShowScrollBars(true);
		col.setBounds(0, 20, 400, 220);
		
		content = new Composite(col, SWT.NONE);
		
		
		for(Student s : StudCol.getAllStuds()){
			StudentTableBlock dummy = new StudentTableBlock(content,SWT.BORDER);
			dummy.slots.get(0).lblSlotVal.setText(s.getID() + "");
			for(int i = 1; i < 4; i++){
				dummy.slots.get(i).lblSlotVal.setText("");
				for(CourseSlot cs : s.getMySlots()){
					if(cs.getCourse().getName() == COURSES[i-1]){
						dummy.slots.get(i).lblSlotVal.setText(cs.getTimeSlot() + "");
					}
				}
			}
			col.blocks.add(dummy);
		}
		col.setContent(content);
		col.setMinSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		content.setSize(content.computeSize(col.getSize().x, SWT.DEFAULT));
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
