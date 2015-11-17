package mainProg.gui;

import mainProg.core.AbstractMain;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class StaticMainGui extends AbstractMain{

	protected Shell shell;
	private static Label statusShow;

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
		this.init();
		statusShow.setText("Initialized");
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
		shell.setSize(241, 208);
		shell.setText("SWT Application");
		shell.setLayout(null);
		
		Label lblStatus = new Label(shell, SWT.NONE);
		lblStatus.setBounds(10, 10, 55, 15);
		lblStatus.setText("Status:");
		
		statusShow = new Label(shell, SWT.NONE);
		statusShow.setBounds(71, 10, 55, 15);
		statusShow.setText("Idle");
		
		Button btnShowdebug = new Button(shell, SWT.NONE);
		btnShowdebug.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showDebug();
			}
		});
		btnShowdebug.setToolTipText("Shows debug data in external Window");
		btnShowdebug.setBounds(10, 46, 75, 25);
		btnShowdebug.setText("ShowDebug");
		
		Button btnGenerateGroups = new Button(shell, SWT.NONE);
		btnGenerateGroups.setToolTipText("Generates filled TimeTable.\r\nIf \"Ignore Happiness\" checked, generates any TimeTable free of Conflicts,\r\nelse a TimeTable with optimized Happiness of Students.");
		btnGenerateGroups.setBounds(10, 87, 92, 25);
		btnGenerateGroups.setText("Generate Groups");
		
		Button btnIgnoreHappiness = new Button(shell, SWT.CHECK);
		btnIgnoreHappiness.setBounds(10, 118, 112, 16);
		btnIgnoreHappiness.setText("Ignore Happiness");
	}
	
	private void showDebug(){
		DebugView dV = new DebugView();
		dV.setResultTable(this.getEmptyTable());
		dV.open();
	}
}
