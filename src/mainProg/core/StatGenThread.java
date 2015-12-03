package mainProg.core;

import java.util.List;

import simuClasses.*;
import mainProg.gui.ProgressView;

public class StatGenThread extends AbstractMain implements Runnable {
	StatGenResult result;
	List<StatGenResult> resultSet;
	LockableProgressCounter progCount;
	ProgressView globPV;
	private int cycles, myOverlap;
	private boolean overPower;

	public StatGenThread(int o, int cyc, LockableProgressCounter finishCount,
			List<StatGenResult> results, boolean op) {
		this.progCount = finishCount;
		this.cycles = cyc;
		this.myOverlap = o;
		this.resultSet = results;
		this.overPower = op;
		result = new StatGenResult(o);
	}

	@Override
	public void run() {
		for (int i = 0; i < cycles; i++) {
			this.init(myOverlap);
			this.startSearch(true, false);
			this.result.addRandomHappiness(this.finalTable.getHappiness());
			this.result.addAverageRand(calcAverageStudHappiness());
			System.out.println("My OL:" + this.myOverlap + " My Happ: " + calcAverageStudHappiness());
			progCount.pp();
			// ---------------------------------------------------------------
			this.startSearch(false, overPower);
			this.result.addOptHappiness(this.finalTable.getHappiness());
			this.result.addAverageOpt(calcAverageStudHappiness());
			System.out.println("My OL:" + this.myOverlap + " My Happ: " + calcAverageStudHappiness());
			progCount.pp();
			// ---------------------------------------------------------------
		}
		resultSet.add(result);
	}

	private double calcAverageStudHappiness() {
		double ret = 0;
		int studsToCount = 0;
		for (Student s : this.getStudCol().getAllStuds()) {
			if (!(s.getMySlots().isEmpty())) {
				studsToCount++;

				double tempPerStud = 0;
				for (CourseSlot cs : s.getMySlots()) {
					double tempPerCS = 0;
					for (Student s2 : cs.getStudents()) {
						if (s != s2) {
							tempPerCS += cs.getHappyMatrix()
									.getHpById(s.getID(), s2.getID())
									.getHappiness();
						}
					}
					tempPerStud += (tempPerCS / (cs.getStudents().size() - 1));
				}
				ret += (tempPerStud / s.getMySlots().size());
			}
		}
		System.out.println("My OL:" + this.myOverlap + " STC: " + studsToCount);
		return (ret / studsToCount);
	}

}
