package mainProg.gui;

import mainProg.core.AbstractMain;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class StaticMainGui extends AbstractMain{

	protected Shell shell;
	private static Label statusShow;
	Combo chooseOverlap;
	Button btnIgnoreHappiness;
	Button btnGenerateGroups;
	Button btnShowFinalTable;
	Button btnShowInitialTable;
	Button btnInitialize;
	Button btnShowStudents;
	Button btnShowStats;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			StaticMainGui window = new StaticMainGui();
			window.open();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();		
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(246, 356);
		shell.setText("Fcking Amazing GroupSchedule-GUI");
		shell.setLayout(null);
		
		Label lblStatus = new Label(shell, SWT.NONE);
		btnShowInitialTable = new Button(shell, SWT.NONE);
		btnIgnoreHappiness = new Button(shell, SWT.CHECK);
		chooseOverlap = new Combo(shell, SWT.NONE);
		btnInitialize = new Button(shell, SWT.NONE);
		btnShowFinalTable = new Button(shell, SWT.NONE);
		btnGenerateGroups = new Button(shell, SWT.NONE);
		btnShowStudents = new Button(shell, SWT.NONE);
		btnShowStats = new Button(shell, SWT.NONE);
		
		
		lblStatus.setBounds(10, 10, 55, 15);
		lblStatus.setText("Status:");
		
		statusShow = new Label(shell, SWT.NONE);
		statusShow.setBounds(71, 10, 157, 15);
		statusShow.setText("Waiting for Input to initialize");
		
		
		btnShowInitialTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showInitialTable();
			}
		});
		btnShowInitialTable.setToolTipText("Shows debug data in external Window");
		btnShowInitialTable.setBounds(10, 157, 112, 25);
		btnShowInitialTable.setText("Show Initial Table");
		btnShowInitialTable.setEnabled(false);
		
		
		btnIgnoreHappiness.setEnabled(false);
		btnIgnoreHappiness.setSelection(true);
		btnIgnoreHappiness.setBounds(10, 280, 112, 16);
		btnIgnoreHappiness.setText("Ignore Happiness");
		
		
		chooseOverlap.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				btnShowInitialTable.setEnabled(false);
				btnShowFinalTable.setEnabled(false);
				btnGenerateGroups.setEnabled(false);
				btnShowStudents.setEnabled(false);
			}
		});
		chooseOverlap.setItems(new String[] {"0", "1", "2", "3", "4", "5"});
		chooseOverlap.setBounds(71, 42, 55, 23);
		chooseOverlap.select(0);
		
		Label lblOverlap = new Label(shell, SWT.NONE);
		lblOverlap.setBounds(14, 50, 55, 15);
		lblOverlap.setText("Overlap:");
		
		
		btnInitialize.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				initialize();
				statusShow.setText("Initialized");
				btnShowInitialTable.setEnabled(true);
				btnGenerateGroups.setEnabled(true);
				btnShowFinalTable.setEnabled(false);
			}
		});
		btnInitialize.setBounds(10, 126, 75, 25);
		btnInitialize.setText("Initialize");
		
		
		btnGenerateGroups.setEnabled(false);
		btnGenerateGroups.setToolTipText("Generates filled TimeTable.\r\nIf \"Ignore Happiness\" checked, generates any TimeTable free of Conflicts,\r\nelse a TimeTable with optimized Happiness of Students.");
		btnGenerateGroups.setBounds(10, 249, 92, 25);
		btnGenerateGroups.setText("Generate Groups");
		btnGenerateGroups.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				searchStart();
				statusShow.setText("Groups generated");
				btnShowFinalTable.setEnabled(true);
				btnGenerateGroups.setEnabled(false);
				btnShowStudents.setEnabled(true);
			}
		});
		
		
		btnShowFinalTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showFinalTable();
			}
		});
		btnShowFinalTable.setToolTipText("Shows debug data in external Window");
		btnShowFinalTable.setText("Show Final Table");
		btnShowFinalTable.setEnabled(false);
		btnShowFinalTable.setBounds(10, 218, 112, 25);
		
		
		btnShowStudents.setBounds(10, 188, 92, 25);
		btnShowStudents.setText("Show Students");
		btnShowStudents.setEnabled(false);
		btnShowStudents.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){
				showStudTable();
			}
		});
		
		btnShowStats.setBounds(108, 187, 75, 25);
		btnShowStats.setText("Show Stats");
	}
	
	private void showInitialTable(){
		TimeTableView dV = new TimeTableView();
		dV.setResultTable(this.getEmptyTable());
		dV.open();
	}
	
	private void showFinalTable(){
		TimeTableView dV = new TimeTableView();
		dV.setResultTable(this.getFinalTable());
		dV.open();
		return;//just for debugging TODO remove this at some time
	}
	
	private void showStudTable(){
		StudentView STV = new StudentView(this.getStudCol());
		STV.open();
	}
	
	private void initialize(){
		this.init(Integer.parseInt(chooseOverlap.getText()));
	}
	
	private void searchStart(){
		this.startSearch(btnIgnoreHappiness.getSelection());
	}
}
