package SystemCombine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class ArrayData {
    
	private int dist;       /**< The min edit distance until current pos*/ 
    private int pre_x;      /**< Store the previous postion, x part horizontal */
    private int pre_y;      /**< Store the previous postion, y part vertical*/
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
	private List<String> s1;
	private List<String> s2;
	
	private List<String> r1;
	private List<String> r2;
	
	public void setS1(List<String> s1) {
		this.s1 = s1;
	}

	public void setS2(List<String> s2) {
		this.s2 = s2;
	}

	public List<String> getS1() {
		return s1;
	}

	public List<String> getS2() {
		return s2;
	}

	public List<String> getR1() {
		return r1;
	}

	public List<String> getR2() {
		return r2;
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
		
		List<List<String>> result = new ArrayList<>();
		LDPath path = new LDPath(s2, s3);
		path.ldCalcPath();
		System.out.println(path.getR1());
		System.out.println(path.getR2());
		
		path.setS1(path.getR1());
		path.setS2(s3);
		path.ldCalcPath();
		System.out.println(path.getR1());
		System.out.println(path.getR2());
		
		path.setS1(path.getR1());
		path.setS2(s4);
		path.ldCalcPath();
		System.out.println(path.getR1());
		System.out.println(path.getR2());
	}
	
	public LDPath() {
		if (r1 == null) {
			r1 = new ArrayList<>();
		}
		
		if (r2 == null) {
			r2 = new ArrayList<>();
		}
	}
	
	public LDPath(List<String> s1, List<String> s2) {
		this.s1 = s1;
		this.s2 = s2;
		
		if (r1 == null) {
			r1 = new ArrayList<>();
		}
		
		if (r2 == null) {
			r2 = new ArrayList<>();
		}
	}
	
	
	public int ldCalcPath() {
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
	            if (array[i -1][j].getDist() < array[i][j - 1].getDist()) {  //can also be <=
	            	array[i][j] = new ArrayData(array[i - 1][j].getDist() + 1, i - 1, j);
	            } else {
	            	array[i][j] = new ArrayData(array[i][j - 1].getDist() + 1, i, j - 1);
	            }
	            min_dist = array[i - 1][j - 1].getDist() + (s1.get(i - 1).equalsIgnoreCase(s2.get(j - 1)) ? 0 : 1);
	            if (min_dist < array[i][j].getDist()) { // < is OK but <= make modify high priority
	            	array[i][j] = new ArrayData(min_dist, i - 1, j - 1);
	            }
	        }
	    }
	    
	    this.storeResult(array, len1, len2);
	    
	    min_dist = array[len1][len2].getDist();
	    System.out.println(min_dist);
	    return min_dist;
	   
	}
	
	public void storeResult(ArrayData[][] array, int index_x, int index_y) {
		if (index_x == 0 && index_y == 0) {
			return;
		}

		if ((array[index_x][index_y].getPre_x() < index_x) && (array[index_x][index_y].getPre_y() < index_y)) {
			storeResult(array, index_x - 1, index_y - 1);
			r1.add(s1.get(index_x - 1));
			r2.add(s2.get(index_y - 1));
		} else if (array[index_x][index_y].getPre_x()  < index_x) {
			storeResult(array, index_x - 1, index_y);
			r1.add(s1.get(index_x - 1));
			r2.add("_");
		} else {
			storeResult(array, index_x, index_y - 1);
			r1.add("_");
			r2.add(s2.get(index_y - 1));
		}
	}

	
}

