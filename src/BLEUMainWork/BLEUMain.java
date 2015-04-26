package BLEUMainWork;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BLEUMain {
	private List<Double> pValues;
	
	private String s;
	private List<String> strs;
	
	public String getS() {
		return s;
	}

	public void setS(String s) {
//		this.s = filt(s);
		if (s == null) {
			this.s = "";
		} else {
			this.s = s.toLowerCase();
		}
		
		this.pValues = null;
	}

	public List<String> getStrs() {
		return strs;
	}

	public void setStrs(List<String> strs) {
		this.strs = new ArrayList<>();
		for (String str : strs) {
			if (str == null) {
				this.strs.add("");
			}
			this.strs.add(str.toLowerCase());
		}		
		this.pValues = null;
	}

	private int getCountInMap(String part, Map<String, Integer> map) {
		if (map.containsKey(part)) {
			return map.get(part);
		}
		return 0;
	}
	
	private Map<String, Integer> countNgramTimes(List<String> strs) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		for (String string : strs) {
			if (result.containsKey(string)) {
				int count = result.get(string);
				count++;
				result.put(string, count);
			} else {
				result.put(string, 1);
			}
		}
		return result;
	}
	
	private int getCountOfNgram(String s, int n) {
		String[] strs = s.split("\\s+");
		int count = strs.length;
		return count - n + 1;
	}
	
	private int getLengthOfSentence(String s) {
		String[] strs = s.split("\\s+");
		int count = strs.length;
		return count;
	}
	
	private Map<String, Integer> clipStringNgram(String s, int n) {
		String[] strs = s.split("\\s+");
		int count = strs.length;
		List<String> strings = new ArrayList<>();
		for (int i = 0; i < count - n + 1; ++i) {
			StringBuffer sb = new StringBuffer(strs[i]);
			for (int j = 1; j < n; ++j) {
				sb.append(strs[i + j]);
			}
			strings.add(new String(sb));
		}
		return countNgramTimes(strings);
	}
	
	private double format(double x) {
		DecimalFormat df = new DecimalFormat("#.0000");
		return Double.valueOf(df.format(x));
	}
	
	public String filt(String s) {
		return s.toLowerCase().replaceAll("[，。！？、]", "").trim();
	}
	
	private double getPValue(String s, List<String> strs, int n) {
		Map<String, Integer> map = clipStringNgram(s, n);
		double result = 0.0;
		List<Map<String, Integer>> strMaps = new ArrayList<>();
		for (String str : strs) {
			strMaps.add(clipStringNgram(str, n));
		}
		for (String key : map.keySet()) {
			int count = map.get(key);
			int tmpResult = 0;
			int max = -1;
			for (Map<String, Integer> strMap : strMaps) {
				max = Math.max(getCountInMap(key, strMap), max);
			}	
			tmpResult = Math.min(max, count);
			result += tmpResult;
		}
		result = result / (double)getCountOfNgram(s, n);
		return result;
	}
	
	private double getBP(String s, List<String> strs) {
		int count = getLengthOfSentence(s);
		int min = getLengthOfSentence(strs.get(0));
		for (String str : strs) {
			min = Math.min(min, getLengthOfSentence(str));
		}
		if (count >= min) {
			return 1;
		}
		double result = Math.exp(1 - min /(double)count);
		return result;
	}
	
	public double getScore(int n) {
		if (n > 4) {
			return -1;
		}
		double score = 0.0;
		double bp = getBP(s, strs);
		double weight = 1.0 / (double)n;
		
		if (pValues == null || pValues.size() == 0) {
			pValues = new ArrayList<>();
			double p1 = getPValue(s, strs, 1);
			double p2 = getPValue(s, strs, 2);
			double p3 = getPValue(s, strs, 3);
			double p4 = getPValue(s, strs, 4);
			pValues.add(p1);
			pValues.add(p2);
			pValues.add(p3);
			pValues.add(p4);
		}
		
		for (int i = 0; i < n; ++i) {
			score += Math.log(pValues.get(i));
		}
		score = bp * Math.exp(weight * score);
//		return format(score);
		return score;
	}
	
	public static void main(String[] args) {
//		BLEUMain main = new BLEUMain();
//		String s1 = main.filt("It is a guide to action which ensures that the military always obeys the commands of the party");
//		String s2 = main.filt("It is to insure the troops forever hearing the activity guidebook that party direct");
//		List<String> strs = new ArrayList<>();
//		strs.add(main.filt("It is a guide to action that ensures that the military will forever heed party commands"));
//		strs.add(main.filt("It is the guiding principle which guarantees the military forces always being under the command of the party"));
//		strs.add(main.filt("It is the practical guide for the army always to heed the directions of the party"));
//		
//		main.setS(s1);
//		main.setStrs(strs);
//		for (int i = 1; i <= 4; ++i) {
//			System.out.println(main.getScore(i));
//		}
		
		BLEUMain main = new BLEUMain();
		String s1 = "a former United Nations envoy to the Middle East , the situation in this region not days so dangerous as it is today , comparing the region called  a powder   music . ";
		List<String> strs = new ArrayList<>();
		strs.add("the former United Nations Middle East envoy , considered the situation in the region as having never been as dangerous as it is today and compared the region to a powder keg with a lit fuse . ");
		main.setS(s1);
		main.setStrs(strs);
		for (int i = 1; i <= 4; ++i) {
			System.out.println(main.getScore(i));
		}
	}
}
