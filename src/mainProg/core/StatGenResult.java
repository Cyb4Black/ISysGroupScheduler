package mainProg.core;

public class StatGenResult {
	private int id;
	private double worstRandomHappiness, bestRandomHappiness,
			allRandomHappiness = 0;
	private double worstOptHappiness, bestOptHappiness, allOptHappiness = 0;

	public StatGenResult(int ID) {
		this.id = ID;
	}
	
	public int getId(){
		return this.id;
	}

	public void addRandomHappiness(double input) {
		allRandomHappiness += input;
		if (input < worstRandomHappiness) {
			worstRandomHappiness = input;
		} else if (input > bestRandomHappiness) {
			bestRandomHappiness = input;
		}
	}

	public void addOptHappiness(double input) {
		allOptHappiness += input;
		if (input < worstOptHappiness) {
			worstOptHappiness = input;
		} else if (input > bestOptHappiness) {
			bestOptHappiness = input;
		}
	}

	public double getWorstRandomHappiness() {
		return worstRandomHappiness;
	}

	public double getBestRandomHappiness() {
		return bestRandomHappiness;
	}

	public double getAllRandomHappiness() {
		return allRandomHappiness;
	}

	public double getWorstOptHappiness() {
		return worstOptHappiness;
	}

	public double getBestOptHappiness() {
		return bestOptHappiness;
	}

	public double getAllOptHappiness() {
		return allOptHappiness;
	}

}
