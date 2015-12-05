package mainProg.gui;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

import mainProg.core.AbstractMain;
import mainProg.core.LockableProgressCounter;
import mainProg.core.LockableStatGenResultList;
import mainProg.core.StatGenPool;
import mainProg.core.StatGenResult;

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
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.ProgressBar;

public class StaticMainGui extends AbstractMain {

	protected Shell shell;
	private static Label statusShow;
	Combo chooseOverlap;
	Button btnIgnoreHappiness;
	Button btnUseOverPoweredSearch;
	Button btnGenerateGroups;
	Button btnShowFinalTable;
	Button btnShowInitialTable;
	Button btnInitialize;
	Button btnShowStudents;
	Button btnShowStats;
	Spinner chooseStressLevel;
	private Label lblStressCycles;
	private Button btnStress;
	LockableProgressCounter lpc;
	private ProgressBar progressBar;
	private Button btnResults;
	LockableStatGenResultList results;

	/**
	 * Launch the application.
	 * 
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
		shell.setSize(550, 433);
		shell.setText("Fcking Amazing GroupSchedule-GUI");
		shell.setLayout(null);

		results = new LockableStatGenResultList(new ReentrantLock());
		Label lblStatus = new Label(shell, SWT.NONE);
		btnShowInitialTable = new Button(shell, SWT.NONE);
		btnIgnoreHappiness = new Button(shell, SWT.CHECK);
		chooseOverlap = new Combo(shell, SWT.NONE);
		btnInitialize = new Button(shell, SWT.NONE);
		btnShowFinalTable = new Button(shell, SWT.NONE);
		btnGenerateGroups = new Button(shell, SWT.NONE);
		btnShowStudents = new Button(shell, SWT.NONE);
		btnShowStats = new Button(shell, SWT.NONE);
		btnUseOverPoweredSearch = new Button(shell, SWT.CHECK);
		btnUseOverPoweredSearch
				.setToolTipText("Check this for getting an improved result.\r\nWARNING!\r\nTakes much more time for a pretty small improvement.");

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
		btnShowInitialTable
				.setToolTipText("Shows debug data in external Window");
		btnShowInitialTable.setBounds(10, 220, 112, 25);
		btnShowInitialTable.setText("Show Initial Table");
		btnShowInitialTable.setEnabled(false);

		btnIgnoreHappiness.setSelection(true);
		btnIgnoreHappiness.setBounds(10, 343, 112, 16);
		btnIgnoreHappiness.setText("Ignore Happiness");
		btnIgnoreHappiness.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				btnShowFinalTable.setEnabled(false);
				btnGenerateGroups.setEnabled(true);
				btnShowStudents.setEnabled(false);
				btnShowStats.setEnabled(false);
			}
		});

		chooseOverlap.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				btnShowInitialTable.setEnabled(false);
				btnShowFinalTable.setEnabled(false);
				btnGenerateGroups.setEnabled(false);
				btnShowStudents.setEnabled(false);
				btnShowStats.setEnabled(false);
			}
		});
		chooseOverlap.setItems(new String[] { "0", "1", "2", "3", "4", "5" });
		chooseOverlap.setBounds(67, 122, 55, 23);
		chooseOverlap.select(0);

		Label lblOverlap = new Label(shell, SWT.NONE);
		lblOverlap.setBounds(10, 130, 55, 15);
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
		btnInitialize.setBounds(10, 189, 75, 25);
		btnInitialize.setText("Initialize");

		btnGenerateGroups.setEnabled(false);
		btnGenerateGroups
				.setToolTipText("Generates filled TimeTable.\r\nIf \"Ignore Happiness\" checked, generates any TimeTable free of Conflicts,\r\nelse a TimeTable with optimized Happiness of Students.");
		btnGenerateGroups.setBounds(10, 312, 92, 25);
		btnGenerateGroups.setText("Generate Groups");
		btnGenerateGroups.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				searchStart();
				statusShow.setText("Groups generated");
				btnShowFinalTable.setEnabled(true);
				btnGenerateGroups.setEnabled(false);
				btnShowStats.setEnabled(true);
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
		btnShowFinalTable.setBounds(10, 281, 112, 25);

		btnShowStudents.setBounds(10, 251, 92, 25);
		btnShowStudents.setText("Show Students");
		btnShowStudents.setEnabled(false);
		btnShowStudents.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				showStudTable();
			}
		});

		btnShowStats.setBounds(108, 250, 75, 25);
		btnShowStats.setText("Show Stats");
		btnShowStats.setEnabled(false);
		btnShowStats.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				showStats();
			}
		});

		chooseStressLevel = new Spinner(shell, SWT.NONE);
		chooseStressLevel
				.setToolTipText("Every cycle runs the search with and without happiness vor all 6 overlaps.\r\nSo 10 cycles give 120 searches, where 60 use only deep-search and 60 the power of local search.");
		chooseStressLevel.setBounds(396, 189, 55, 23);
		chooseStressLevel.setMinimum(0);
		chooseStressLevel.setMaximum(1000);
		chooseStressLevel.setIncrement(5);

		lblStressCycles = new Label(shell, SWT.NONE);
		lblStressCycles.setText("Stress Cycles:");
		lblStressCycles.setBounds(311, 192, 81, 15);

		btnStress = new Button(shell, SWT.NONE);
		btnStress.setText("STRESS");
		btnStress.setBounds(348, 218, 75, 25);

		Label label = new Label(shell, SWT.SEPARATOR | SWT.VERTICAL);
		label.setBounds(226, 76, 34, 318);

		btnUseOverPoweredSearch.setText("Use Absolute Overpowered Search");
		btnUseOverPoweredSearch.setSelection(true);
		btnUseOverPoweredSearch.setBounds(285, 249, 202, 16);

		Label lblUseLeftSide = new Label(shell, SWT.NONE);
		lblUseLeftSide.setBounds(10, 81, 218, 15);
		lblUseLeftSide.setText("Use left side for single Run");

		Label lblUseRightSide = new Label(shell, SWT.NONE);
		lblUseRightSide.setBounds(266, 81, 258, 15);
		lblUseRightSide
				.setText("Use right side for mass destruction on CPU ;)");

		progressBar = new ProgressBar(shell, SWT.NONE);
		progressBar.setBounds(285, 292, 202, 17);

		btnResults = new Button(shell, SWT.NONE);
		btnResults.setText("results");
		btnResults.setEnabled(false);
		btnResults.setBounds(348, 334, 75, 25);
		btnResults.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				showResults();
			}
		});

		btnStress.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				statusShow.setText("Stessing now! Please wait!");
				startStress();
				statusShow.setText("TOTALLY STRESSED!!!");
			}
		});

	}

	private void showInitialTable() {
		TimeTableView dV = new TimeTableView();
		dV.setResultTable(this.getEmptyTable());
		dV.open();
	}

	private void showFinalTable() {
		TimeTableView dV = new TimeTableView();
		dV.setResultTable(this.getFinalTable());
		dV.open();
		return;// just for debugging TODO remove this at some time
	}

	private void showStudTable() {
		StudentView STV = new StudentView(this.getStudCol());
		STV.open();
	}

	private void showStats() {
		StatView STV = new StatView(this.getFinalTable(), this.getFinalTable().getMyStuds(),
				this.getCourses());
		STV.open();
	}

	private void initialize() {
		this.init(Integer.parseInt(chooseOverlap.getText()));
	}

	private void searchStart() {
		this.startSearch(btnIgnoreHappiness.getSelection(), true);
	}

	private void showResults() {
		StatData sd = new StatData(results.getList(), Integer.parseInt(chooseStressLevel
				.getText()));
		sd.createContents();
		sd.open();
	}

	private void startStress() {
		btnResults.setEnabled(false);
		progressBar.setSelection(0);
		int cycles = Integer.parseInt(chooseStressLevel.getText());
		boolean uops = btnUseOverPoweredSearch.getSelection();

		progressBar.setMaximum(cycles * 12);
		// StatGenPool SGP = new
		// StatGenPool(cycles,btnUseOverPoweredSearch.getSelection(), results);
		// Thread thread = new Thread(SGP);
		// thread.start();
		lpc = new LockableProgressCounter(new ReentrantLock(), cycles);

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!progressBar.isDisposed()) {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							int dummy = lpc.getCount();
							if (!progressBar.isDisposed()
									&& progressBar.getSelection() < progressBar
											.getMaximum())
								progressBar.setSelection(dummy);
						}
					});

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();

		new Thread(new Runnable() {
			public void run() {

				StatGenPool SGP = new StatGenPool(cycles, uops, results, lpc);
				Thread thread = new Thread(SGP);
				thread.start();
				try {
					thread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							btnResults.setEnabled(true);
						}
					});
				}
			}
		}).start();
	}
}
