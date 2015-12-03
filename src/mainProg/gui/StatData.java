package mainProg.gui;

import java.util.LinkedList;
import mainProg.core.ResultListComparator;
import mainProg.core.StatGenResult;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class StatData{
	protected Object result;
	private Shell shell;
	private String[] OVERLAPS = { "O 0", "O 1", "O 2", "O 3", "O 4", "O 5" };
	private ResultListComparator c = new ResultListComparator();
	int cycles;
	LinkedList<StatGenResult> results;
	Display display;

	public StatData(LinkedList<StatGenResult> in, int cyc) {
		this.results = in;
		this.cycles = cyc;
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

	public void createContents() {
		shell = new Shell();
		shell.setSize(560, 240);
		shell.setLayout(new FillLayout());
		shell.setText("Satistical Data View");

		Table table = new Table(shell, SWT.BORDER | SWT.V_SCROLL);
		table.setHeaderVisible(true);
		String[] categories = {"worst Random", "best Random",
				"Random middle", "AverageRandom/Student", "worst Optimum", "best Optimum", "Optimum middle", "AverageOpt/Student"};
		String[] titles = {"Category", "Lvl. 0", "Lvl. 1", "Lvl. 2", "Lvl. 3", "Lvl. 4", "Lvl. 5"};

		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NULL);
			column.setText(titles[i]);
		}
		
		results.sort(c);
		
		for (int i = 0; i < categories.length; i++) {
			TableItem item = new TableItem(table, SWT.NULL);
			item.setText(0, categories[i]);
		}
		
		for(StatGenResult result : results){
			int col = result.getId() + 1;
			table.getItem(0).setText(col, String.format("%.6g%n", result.getWorstRandomHappiness()));
			table.getItem(1).setText(col, String.format("%.6g%n", result.getBestRandomHappiness()));
			table.getItem(2).setText(col, String.format("%.6g%n", (result.getAllRandomHappiness() / cycles)));
			table.getItem(3).setText(col, String.format("%.4g%n", (result.getAverageRand() / cycles)));
			table.getItem(4).setText(col, String.format("%.6g%n", result.getWorstOptHappiness()));
			table.getItem(5).setText(col, String.format("%.6g%n", result.getBestOptHappiness()));
			table.getItem(6).setText(col, String.format("%.6g%n", (result.getAllOptHappiness() / cycles)));
			table.getItem(7).setText(col, String.format("%.4g%n", (result.getAverageOpt() / cycles)));
		}
//		for(StatGenResult result : results){
//			TableItem item = new TableItem(table, SWT.NULL);
//			item.setText(0, OVERLAPS[result.getId()] + " " + result.getId());
//			item.setText(1, String.format("%.6g%n", result.getWorstRandomHappiness()));
//			item.setText(2, String.format("%.6g%n", result.getBestRandomHappiness()));
//			item.setText(3, String.format("%.6g%n", (result.getAllRandomHappiness() / cycles)));
//			item.setText(4, String.format("%.6g%n", result.getWorstOptHappiness()));
//			item.setText(5, String.format("%.6g%n", result.getBestOptHappiness()));
//			item.setText(6, String.format("%.6g%n", (result.getAllOptHappiness() / cycles)));
//		}

		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}

	}
	
	/**
	 * Debugging-Method for getting into Design-View, cause known default-methods have been modified for explicit usage.
	 * @wbp.parser.entryPoint
	 */
	public void showDesign(){
		createContents();
		open();
	}
}
