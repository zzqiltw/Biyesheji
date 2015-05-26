package SystemCombine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class ArrayData {
	private int dist;      
    private int pre_x;   
    private int pre_y;      
	public int getDist() {
		return dist;
	}
	public void setDist(int dist) {
		this.dist = dist;
	}
	public int getPre_x() {
		return pre_x;
	}
	public void setPre_x(int pre_x) {
		this.pre_x = pre_x;
	}
	public int getPre_y() {
		return pre_y;
	}
	public void setPre_y(int pre_y) {
		this.pre_y = pre_y;
	}
	public ArrayData(int dist, int pre_x, int pre_y) {
		super();
		this.dist = dist;
		this.pre_x = pre_x;
		this.pre_y = pre_y;
	}
};


public class LDPath {
	/**
	 * 心力交瘁，懒得重构
	 */
	private List<String> s1;
	private List<String> s2;
	private List<String> s3;
	private List<String> s4;
	
	private List<String> r1;
	private List<String> r2;
	private List<String> r3;
	private List<String> r4;
	
	
	
	public List<String> getS1() {
		return s1;
	}

	public List<String> getS2() {
		return s2;
	}

	public List<String> getS3() {
		return s3;
	}

	public List<String> getS4() {
		return s4;
	}

	public List<String> getR1() {
		return r1;
	}

	public List<String> getR2() {
		return r2;
	}

	public List<String> getR3() {
		return r3;
	}

	public List<String> getR4() {
		return r4;
	}

	public void setS1(List<String> s1) {
		this.s1 = s1;
	}

	public void setS2(List<String> s2) {
		this.s2 = s2;
	}

	public void setS3(List<String> s3) {
		this.s3 = s3;
	}

	public void setS4(List<String> s4) {
		this.s4 = s4;
	}

	public void setR1(List<String> r1) {
		this.r1 = r1;
	}

	public void setR2(List<String> r2) {
		this.r2 = r2;
	}

	public void setR3(List<String> r3) {
		this.r3 = r3;
	}

	public void setR4(List<String> r4) {
		this.r4 = r4;
	}

	public static void main(String[] args) {
		
		String[] src1 = {"Please", "enter", "the", "content", "to", "translate"};
		String[] src2 = {"Please", "enter", "what", "you", "want","to"};
		String[] src3 = {"Please", "enter" ,"translated", "content"};
		String[] src4 = {"Please", "input", "to", "the", "content", "of", "the", "translation"};
		List<String> s1 = Arrays.asList(src1);
		List<String> s2 = Arrays.asList(src2);
		List<String> s3 = Arrays.asList(src3);
		List<String> s4 = Arrays.asList(src4);
		
		LDPath path = new LDPath();
		path.setS1(s1);
		path.setS2(s2);
		path.setS3(s3);
		path.setS4(s4);
		
		List<List<String>> result = path.alignmentList();
		for (List<String> list : result) {
			System.out.println(list);
		}
	}
	
	
	public List<List<String>> alignmentList() {
		List<List<String>> result = new ArrayList<>();
		this.ldCalcPathOf12();
		this.ldCalcPathOf13();
		this.ldCalcPathOf14();
		result.add(this.getR1());
		result.add(this.getR2());
		result.add(this.getR3());
		result.add(this.getR4());
		int min = result.get(0).size();
		for (List<String> list : result) {
			if (list.size() < min) {
				min = list.size();
			}
		}
		
		for (List<String> list : result) {
			for (int i = min + 1; i < list.size(); ++i) {
				list.remove(i);
			}
		}
		
		return result;
	}
	public LDPath() {
		if (r1 == null) {
			r1 = new ArrayList<>();
		}
		
		if (r2 == null) {
			r2 = new ArrayList<>();
		}
		
		if (r3 == null) {
			r3 = new ArrayList<>();
		}
		
		if (r4 == null) {
			r4 = new ArrayList<>();
		}
	}
	
	public int ldCalcPathOf12() {
		this.r1 = new ArrayList<>();
		this.r2 = new ArrayList<>();
		int len1 = s1.size();
	    int len2 = s2.size();
	    ArrayData[][] array;
	    array = new ArrayData[len1 + 1][];
	    for (int i = 0; i <= len1; ++i) {
	    	array[i] = new ArrayData[len2 + 1];
	    }
	    for (int i = 0; i <= len1; i++) {
	    	array[i][0] = new ArrayData(i, i-1, 0);
	    }
	    for (int j = 0; j <= len2; j++) {
	    	array[0][j] = new ArrayData(j, 0, j-1);
	    }
	    int min_dist;
	    for (int i = 1; i <= len1; i++) {
	        for (int j = 1; j <= len2; j++) {
	            if (array[i -1][j].getDist() < array[i][j - 1].getDist()) { 
	            	array[i][j] = new ArrayData(array[i - 1][j].getDist() + 1, i - 1, j);
	            } else {
	            	array[i][j] = new ArrayData(array[i][j - 1].getDist() + 1, i, j - 1);
	            }
	            min_dist = array[i - 1][j - 1].getDist() + (s1.get(i - 1).equalsIgnoreCase(s2.get(j - 1)) ? 0 : 1);
	            if (min_dist < array[i][j].getDist()) { 
	            	array[i][j] = new ArrayData(min_dist, i - 1, j - 1);
	            }
	        }
	    }
	    this.storeResult12(array, len1, len2);
	    min_dist = array[len1][len2].getDist();
	    System.out.println(min_dist);
	    return min_dist;
	}
	
	public void storeResult12(ArrayData[][] array, int index_x, int index_y) {
		if (index_x == 0 && index_y == 0) {
			return;
		}

		if ((array[index_x][index_y].getPre_x() < index_x) && (array[index_x][index_y].getPre_y() < index_y)) {
			storeResult12(array, index_x - 1, index_y - 1);
			r1.add(s1.get(index_x - 1));
			r2.add(s2.get(index_y - 1));
		} else if (array[index_x][index_y].getPre_x()  < index_x) {
			storeResult12(array, index_x - 1, index_y);
			r1.add(s1.get(index_x - 1));
			r2.add("_");
		} else {
			storeResult12(array, index_x, index_y - 1);
			r1.add("_");
			r2.add(s2.get(index_y - 1));
		}
	}
	
	public int ldCalcPathOf13() {
		this.r3 = new ArrayList<>();
		s1 = this.r1;
		this.r1 = new ArrayList<>();

		int len1 = s1.size();
	    int len2 = s3.size();
	    ArrayData[][] array;
	    array = new ArrayData[len1 + 1][];
	    for (int i = 0; i <= len1; ++i) {
	    	array[i] = new ArrayData[len2 + 1];
	    }
	    for (int i = 0; i <= len1; i++) {
	    	array[i][0] = new ArrayData(i, i-1, 0);
	    }
	    for (int j = 0; j <= len2; j++) {
	    	array[0][j] = new ArrayData(j, 0, j-1);
	    }
	    int min_dist;
	    for (int i = 1; i <= len1; i++) {
	        for (int j = 1; j <= len2; j++) {
	            if (array[i -1][j].getDist() < array[i][j - 1].getDist()) { 
	            	array[i][j] = new ArrayData(array[i - 1][j].getDist() + 1, i - 1, j);
	            } else {
	            	array[i][j] = new ArrayData(array[i][j - 1].getDist() + 1, i, j - 1);
	            }
	            min_dist = array[i - 1][j - 1].getDist() + (s1.get(i - 1).equalsIgnoreCase(s3.get(j - 1))  ? 0 : 1);
	            if (min_dist < array[i][j].getDist()) { 
	            	array[i][j] = new ArrayData(min_dist, i - 1, j - 1);
	            }
	        }
	    }
	    this.storeResult13(array, len1, len2);
	    min_dist = array[len1][len2].getDist();
	    System.out.println(min_dist);
	    return min_dist;
	}
	
	public void storeResult13(ArrayData[][] array, int index_x, int index_y) {
		if (index_x == 0 && index_y == 0) {
			return;
		}

		if ((array[index_x][index_y].getPre_x() < index_x) && (array[index_x][index_y].getPre_y() < index_y)) {
			storeResult13(array, index_x - 1, index_y - 1);
			r1.add(s1.get(index_x - 1));
			r3.add(s3.get(index_y - 1));
		} else if (array[index_x][index_y].getPre_x()  < index_x) {
			storeResult13(array, index_x - 1, index_y);
			r1.add(s1.get(index_x - 1));
			r3.add("_");
		} else {
			storeResult13(array, index_x, index_y - 1);
			r1.add("_");
			r2.add(index_x, "_");
			r3.add(s3.get(index_y - 1));
		}
	}
	
	public int ldCalcPathOf14() {
		this.r4 = new ArrayList<>();
		s1 = this.r1;
		this.r1 = new ArrayList<>();

		int len1 = s1.size();
	    int len2 = s4.size();
	    ArrayData[][] array;
	    array = new ArrayData[len1 + 1][];
	    for (int i = 0; i <= len1; ++i) {
	    	array[i] = new ArrayData[len2 + 1];
	    }
	    for (int i = 0; i <= len1; i++) {
	    	array[i][0] = new ArrayData(i, i-1, 0);
	    }
	    for (int j = 0; j <= len2; j++) {
	    	array[0][j] = new ArrayData(j, 0, j-1);
	    }
	    int min_dist;
	    for (int i = 1; i <= len1; i++) {
	        for (int j = 1; j <= len2; j++) {
	            if (array[i -1][j].getDist() < array[i][j - 1].getDist()) { 
	            	array[i][j] = new ArrayData(array[i - 1][j].getDist() + 1, i - 1, j);
	            } else {
	            	array[i][j] = new ArrayData(array[i][j - 1].getDist() + 1, i, j - 1);
	            }
	            min_dist = array[i - 1][j - 1].getDist() + (s1.get(i - 1).equalsIgnoreCase(s4.get(j - 1)) ? 0 : 1);
	            if (min_dist < array[i][j].getDist()) { 
	            	array[i][j] = new ArrayData(min_dist, i - 1, j - 1);
	            }
	        }
	    }
	    this.storeResult14(array, len1, len2);
	    min_dist = array[len1][len2].getDist();
	    System.out.println(min_dist);
	    return min_dist;
	}
	
	public void storeResult14(ArrayData[][] array, int index_x, int index_y) {
		if (index_x == 0 && index_y == 0) {
			return;
		}

		if ((array[index_x][index_y].getPre_x() < index_x) && (array[index_x][index_y].getPre_y() < index_y)) {
			storeResult14(array, index_x - 1, index_y - 1);
			r1.add(s1.get(index_x - 1));
			r4.add(s4.get(index_y - 1));
		} else if (array[index_x][index_y].getPre_x()  < index_x) {
			storeResult14(array, index_x - 1, index_y);
			r1.add(s1.get(index_x - 1));
			r4.add("_");
		} else {
			storeResult14(array, index_x, index_y - 1);
			r1.add("_");
			r2.add(index_x, "_");
			r3.add(index_x, "_");
			r3.add(s3.get(index_y - 1));
		}
	}


	
}

