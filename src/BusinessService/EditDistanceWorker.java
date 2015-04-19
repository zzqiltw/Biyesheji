package BusinessService;

import java.text.DecimalFormat;

public class EditDistanceWorker implements ScoreWorker {
	public static void main(String[] args) {
		double score = new EditDistanceWorker().getScore("你 的 鞋 带 松 了", "的 鞋 带 你 了 松");
		System.out.println("edit_distance_sim: " + score);
	}
	
	@Override
	public double getScore(String firstString, String secondString) {
		DecimalFormat df = new DecimalFormat("#.000");
		return Double.valueOf(df.format(editDistance(firstString.split("\\s+"), secondString.split("\\s+"))));
	}

	 public double editDistance(String[] str1,String[] str2) {  
	        //计算两个字符串的长度。  
	        int len1 = str1.length;  
	        int len2 = str2.length;  
	        //建立上面说的数组，比字符长度大一个空间  
	        int[][] dif = new int[len1 + 1][len2 + 1];  
	        //赋初值，步骤B。  
	        for (int a = 0; a <= len1; a++) {  
	            dif[a][0] = a;  
	        }  
	        for (int a = 0; a <= len2; a++) {  
	            dif[0][a] = a;  
	        }  
	        //计算两个字符是否一样，计算左上的值  
	        int temp;  
	        for (int i = 1; i <= len1; i++) {  
	            for (int j = 1; j <= len2; j++) {  
	                if (str1[i - 1].equalsIgnoreCase(str2[j - 1])) {  
	                    temp = 0;  
	                } else {  
	                    temp = 1;  
	                }  
	                //取三个值中最小的  
	                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,  
	                        dif[i - 1][j] + 1);  
	            }  
	        }  
	        //计算相似度  
	        double similarity =1 - (double) dif[len1][len2] / Math.max(str1.length, str2.length);  
	        return similarity;
	    }  
	  
	    //得到最小值  
	    private int min(int... is) {  
	        int min = Integer.MAX_VALUE;  
	        for (int i : is) {  
	            if (min > i) {  
	                min = i;  
	            }  
	        }  
	        return min;  
	    }  
}
