package mainProg.gui;

import mainProg.gui.timeTableParts.TimeTableBlock;
import mainProg.gui.timeTableParts.TimeTableCol;
import mainProg.gui.timeTableParts.TimeTableSlot;
import mainProg.gui.timeTableParts.TimeTableTemplate;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import simuClasses.CourseSlot;
import simuClasses.TimeTable;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;

public class DebugView {

	protected Object result;
	protected Shell shell;
	private TimeTableTemplate table;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DebugView() {
		shell = new Shell();
		shell.setText("Simple Debug View");
		createContents();
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		shell.open();
		shell.layout();
		Display display = Display.getDefault();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		//shell = new Shell(getParent(), getStyle());
		shell.setSize(1170, 597);
		//shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		table = new TimeTableTemplate(shell, SWT.NONE);

	}
	
	public void setResultTable(TimeTable tT){
		for(int i = 0; i < 30; i++){
			if(i < 6){
				for(CourseSlot CS : tT.getAllCourses()){
					if(CS.getTimeSlot() == i){
						table.colMon.blocks.get(i).slots.add(new TimeTableSlot(table.colMon.blocks.get(i), SWT.NONE, CS));
					}
				}
			}else if(i < 12){
				for(CourseSlot CS : tT.getAllCourses()){
					if(CS.getTimeSlot() == i){
						table.colTue.blocks.get(i - 6).slots.add(new TimeTableSlot(table.colTue.blocks.get(i - 6), SWT.NONE, CS));
					}
				}
			}else if(i < 18){
				for(CourseSlot CS : tT.getAllCourses()){
					if(CS.getTimeSlot() == i){
						table.colWed.blocks.get(i - 12).slots.add(new TimeTableSlot(table.colWed.blocks.get(i - 12), SWT.NONE, CS));
					}
				}
			}else if(i < 24){
				for(CourseSlot CS : tT.getAllCourses()){
					if(CS.getTimeSlot() == i){
						table.colThu.blocks.get(i - 18).slots.add(new TimeTableSlot(table.colThu.blocks.get(i - 18), SWT.NONE, CS));
					}
				}
			}else{
				for(CourseSlot CS : tT.getAllCourses()){
					if(CS.getTimeSlot() == i){
						table.colFri.blocks.get(i - 24).slots.add(new TimeTableSlot(table.colFri.blocks.get(i - 24), SWT.NONE, CS));
					}
				}
			}
		}
		
		addEventHandlers();
	}
	
	private void addEventHandlers(){
		for(TimeTableBlock block : table.colMon.blocks){
			for(TimeTableSlot slot : block.slots){
				slot.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseUp(MouseEvent arg0) {
						// Do nothing
						
					}
					
					@Override
					public void mouseDown(MouseEvent arg0) {
						// Do nothing
						
					}
					
					@Override
					public void mouseDoubleClick(MouseEvent arg0) {
						showCourseInfo(slot);						
					}
				});
			}
		}
	}
	
	private void showCourseInfo(TimeTableSlot slot){
		
	}
}
