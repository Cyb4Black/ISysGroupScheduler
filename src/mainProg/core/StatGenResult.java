package mainProg.core;

public class StatGenResult {
	private int id;
	private double bestRandomHappiness = 0, allRandomHappiness = 0, averageRandHappiness = 0;
	private double bestOptHappiness = 0, allOptHappiness = 0, averageOptHappiness = 0;
	private double worstRandomHappiness = 99999, worstOptHappiness = 99999;
	public StatGenResult(int ID) {
		this.id = ID;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void addAverageRand(double in){
		averageRandHappiness += in;
	}
	
	public void addAverageOpt(double in){
		averageOptHappiness += in;
	}
	
	public double getAverageRand(){
		return averageRandHappiness;
	}
	
	public double getAverageOpt(){
		return averageOptHappiness;
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
