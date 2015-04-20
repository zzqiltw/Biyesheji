package BusinessService;

import java.text.DecimalFormat;

public class EditDistanceWorker implements ScoreWorker {
	
	@Override
	public double getScore(String firstString, String secondString) {
		DecimalFormat df = new DecimalFormat("#.000");
		return Double.valueOf(df.format(editDistance(firstString.split("\\s+"), secondString.split("\\s+"))));
	}

	 public double editDistance(String[] str1,String[] str2) {  
	        //���������ַ�ĳ��ȡ�  
	        int len1 = str1.length;  
	        int len2 = str2.length;  
	        //��������˵�����飬���ַ�ȴ�һ���ռ�  
	        int[][] dif = new int[len1 + 1][len2 + 1];  
	        //����ֵ������B��  
	        for (int a = 0; a <= len1; a++) {  
	            dif[a][0] = a;  
	        }  
	        for (int a = 0; a <= len2; a++) {  
	            dif[0][a] = a;  
	        }  
	        //���������ַ��Ƿ�һ��������ϵ�ֵ  
	        int temp;  
	        for (int i = 1; i <= len1; i++) {  
	            for (int j = 1; j <= len2; j++) {  
	                if (str1[i - 1].equalsIgnoreCase(str2[j - 1])) {  
	                    temp = 0;  
	                } else {  
	                    temp = 1;  
	                }  
	                //ȡ���ֵ����С��  
	                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,  
	                        dif[i - 1][j] + 1);  
	            }  
	        }  
	        //�������ƶ�  
	        double similarity =1 - (double) dif[len1][len2] / Math.max(str1.length, str2.length);  
	        return similarity;
	    }  
	  
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
