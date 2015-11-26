package mainProg.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ProgressBar;

public class ProgressView implements Runnable {
	
	protected Object result;
	private Shell shell;
	int cycles;
	ProgressBar progressBar;
	Display display;
	Color yellow;
	Color red;
	
	public ProgressView(int cyc){
		this.cycles = cyc;
		shell = new Shell();
		shell.setText("Student View");
		display = Display.getCurrent();
		red = display.getSystemColor(SWT.COLOR_RED);
		yellow = display.getSystemColor(SWT.COLOR_YELLOW);
		createContents();
	}
	
	private void open() {
		shell.open();
		shell.layout();
		Display display = Display.getDefault();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	private void createContents() {
	    shell.setSize(349, 137);
	    shell.setLayout(new FillLayout());
	    shell.setText("Students Slots View");
	    
	    progressBar = new ProgressBar(shell, SWT.NONE);
	    progressBar.setMaximum(cycles * 12);
	  }
	
	public Display getDisplay(){
		return this.display;
	}
	public ProgressBar getBar(){
		return this.progressBar;
	}

	@Override
	public void run() {
		this.open();
	}
}
