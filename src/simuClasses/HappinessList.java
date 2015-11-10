package simuClasses;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class HappinessList {
	private List<HappinessPairing> allPairings = new LinkedList<HappinessPairing>();
	private Random myRandom = new Random();

	public void generatePairing(int id, List<Student> allStuds) {
		if (allStuds.isEmpty())
			return;

		for (Student s : allStuds) {
			if (s.getID() != id) {
				allPairings.add(new HappinessPairing(id, s.getID(), myRandom
						.nextDouble()));
			}
		}
	}
	
	public HappinessPairing getHpById(int x, int y){
		HappinessPairing retPairing = null;
		
		for(HappinessPairing hp : allPairings){
			if(hp.contains(x, y)){
				return hp;//more then one return doesn't meet conventions, but will shorten runtime at this point
			}
		}
		
		return retPairing;
	}
	
	public List<HappinessPairing> getHpById(int x){
		List<HappinessPairing> retPairings = new LinkedList<HappinessPairing>();
		for(HappinessPairing hp : allPairings){
			if(hp.contains(x)){
				retPairings.add(hp);
			}
		}
		
		return retPairings;
	}
}
