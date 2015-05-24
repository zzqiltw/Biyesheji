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
	
	public static void main(String[] args) {
		String[] src1 = {"E", "E", "B", "A"};
		String[] src2 = {"A", "B", "C", "A"};
		List<String> s1 = Arrays.asList(src1);
		List<String> s2 = Arrays.asList(src2);
		new LDPath(s1, s2).ldCalcPath();
	}
	
	public LDPath() {
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
	    
	    System.out.println(r1);
	    System.out.println(r2);
	    
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

