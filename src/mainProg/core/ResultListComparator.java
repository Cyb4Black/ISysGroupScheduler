package mainProg.core;

import java.util.Comparator;

public class ResultListComparator implements Comparator<StatGenResult> {

	public ResultListComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(StatGenResult sgr1, StatGenResult sgr2) {
		if(sgr1.getId() < sgr2.getId()){
			return -1;
		}else if(sgr1.getId() > sgr2.getId()){
			return 1;
		}else{
			return 0;
		}
	}

}
