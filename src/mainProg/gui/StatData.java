package mainProg.gui;

import java.util.LinkedList;
import java.util.List;

import mainProg.core.ResultListComparator;
import mainProg.core.StatGenResult;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class StatData {
	protected Object result;
	private Shell shell;
	private String[] OVERLAPS = { "O 0", "O 1", "O 2", "O 3", "O 4", "O 5" };
	private ResultListComparator c = new ResultListComparator();
	int cycles;
	LinkedList<StatGenResult> results;
	Display display;
	Color yellow;
	Color red;

	public StatData(LinkedList<StatGenResult> in, int cyc) {
		this.results = in;
		this.cycles = cyc;
		shell = new Shell();
		shell.setText("Statistical Data");
		display = Display.getCurrent();
		red = display.getSystemColor(SWT.COLOR_RED);
		yellow = display.getSystemColor(SWT.COLOR_YELLOW);
		createContents();
	}

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

	private void createContents() {
		shell.setSize(370, 140);
		shell.setLayout(new FillLayout());
		shell.setText("Satistical Data View");

		Table table = new Table(shell, SWT.BORDER | SWT.V_SCROLL);
		table.setHeaderVisible(true);
		String[] titles = { "Overlap", "worst Random", "best Random",
				"Random middle", "worst Optimum", "best Optimum", "Optimum middle"};

		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NULL);
			column.setText(titles[i]);
		}
		
		results.sort(c);
		for(StatGenResult result : results){
			TableItem item = new TableItem(table, SWT.NULL);
			item.setText(0, OVERLAPS[result.getId()] + " " + result.getId());
			item.setText(1, String.format("%.6g%n", result.getWorstRandomHappiness()));
			item.setText(2, String.format("%.6g%n", result.getBestRandomHappiness()));
			item.setText(3, String.format("%.6g%n", (result.getAllRandomHappiness() / cycles)));
			item.setText(4, String.format("%.6g%n", result.getWorstOptHappiness()));
			item.setText(5, String.format("%.6g%n", result.getBestOptHappiness()));
			item.setText(6, String.format("%.6g%n", (result.getAllOptHappiness() / cycles)));
		}

		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}

	}
}
