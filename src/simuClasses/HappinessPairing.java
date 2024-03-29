package simuClasses;

/**
 * Eine Klasse, welche die Beziehung zwischen zwei Studenten simuliert. Sie enth�lt die IDs der
 * Studenten und den Gl�ckswert als double.
 * 
 * @author Willnow & Selle
 *
 */

public class HappinessPairing {
	private int IDx;
	private int IDy;
	private double Happiness;
	
	public HappinessPairing(int x, int y, double hp){
		this.IDx = x;
		this.IDy = y;
		this.Happiness = hp;
	}
	
	public int getIDx(){
		return IDx;
	}
	
	public int getIDy(){
		return IDy;
	}
	
	public double getHappiness(){
		return Happiness;
	}
	
	public boolean contains(int x){
		return (IDx == x || IDy == x);
	}
	
	public boolean contains(int x, int y){
		return (this.contains(x) && this.contains(y));
	}
}
