package BusinessService;

import java.text.DecimalFormat;

public class SetWorker implements ScoreWorker {
	
	@Override
	public double getScore(String firstString, String secondString) {
		DecimalFormat df = new DecimalFormat("#.000");
		return Double.valueOf(df.format(set(firstString.split("\\s+"), secondString.split("\\s+"))));
	}
	
	public double set(String[] str1, String[] str2) {
		int count = 0;
		int length = str1.length;
		for (String s1 : str1) {
			for (String s2 : str2) {
				if (s1.equalsIgnoreCase(s2)) {
					count++;
					break;
				}
			} 
		}
		return count / (double)length;
	}

}
