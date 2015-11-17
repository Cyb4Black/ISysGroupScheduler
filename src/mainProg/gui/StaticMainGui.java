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
		shell.setSize(435, 293);
		shell.setText("Fcking Amazing GroupSchedule-GUI");
		shell.setLayout(null);
		
		Label lblStatus = new Label(shell, SWT.NONE);
		lblStatus.setBounds(10, 10, 55, 15);
		lblStatus.setText("Status:");
		
		statusShow = new Label(shell, SWT.NONE);
		statusShow.setBounds(71, 10, 157, 15);
		statusShow.setText("Waiting for Input to initialize");
		
		Button btnShowTable = new Button(shell, SWT.NONE);
		btnShowTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showDebug();
			}
		});
		btnShowTable.setToolTipText("Shows debug data in external Window");
		btnShowTable.setBounds(10, 157, 75, 25);
		btnShowTable.setText("Show Table");
		btnShowTable.setEnabled(false);
		
		Button btnGenerateGroups = new Button(shell, SWT.NONE);
		btnGenerateGroups.setToolTipText("Generates filled TimeTable.\r\nIf \"Ignore Happiness\" checked, generates any TimeTable free of Conflicts,\r\nelse a TimeTable with optimized Happiness of Students.");
		btnGenerateGroups.setBounds(10, 198, 92, 25);
		btnGenerateGroups.setText("Generate Groups");
		
		Button btnIgnoreHappiness = new Button(shell, SWT.CHECK);
		btnIgnoreHappiness.setBounds(10, 229, 112, 16);
		btnIgnoreHappiness.setText("Ignore Happiness");
		
		chooseOverlap = new Combo(shell, SWT.NONE);
		chooseOverlap.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				btnShowTable.setEnabled(false);
			}
		});
		chooseOverlap.setItems(new String[] {"0", "1", "2", "3", "4"});
		chooseOverlap.setBounds(71, 42, 55, 23);
		chooseOverlap.select(0);
		
		Label lblOverlap = new Label(shell, SWT.NONE);
		lblOverlap.setBounds(14, 50, 55, 15);
		lblOverlap.setText("Overlap:");
		
		Button btnInitialize = new Button(shell, SWT.NONE);
		btnInitialize.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				initialize();
				statusShow.setText("Initialized");
				btnShowTable.setEnabled(true);
			}
		});
		btnInitialize.setBounds(10, 126, 75, 25);
		btnInitialize.setText("Initialize");
	}
	
	private void showDebug(){
		DebugView dV = new DebugView();
		dV.setResultTable(this.getEmptyTable());
		dV.open();
	}
	
	private void initialize(){
		this.init(Integer.parseInt(chooseOverlap.getText()));
	}
}
