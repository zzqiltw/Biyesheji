package BusinessService;

import java.text.DecimalFormat;

public class LcsWorker implements ScoreWorker {

	@Override
	public double getScore(String firstString, String secondString) {
		DecimalFormat df = new DecimalFormat("#.000");
		return Double.valueOf(df.format(compute(firstString.split("\\s+"), secondString.split("\\s+"))));
	}
	
	public static double compute(String[] str1, String[] str2)
    {
        int substringLength1 = str1.length;
        int substringLength2 = str2.length;
 
        // �����ά�����¼������A[i]��B[j]��LCS�ĳ���
        int[][] opt = new int[substringLength1 + 1][substringLength2 + 1];
 
        // �Ӻ���ǰ����̬�滮�������������⡣Ҳ�ɴ�ǰ����
        for (int i = substringLength1 - 1; i >= 0; i--)
        {
            for (int j = substringLength2 - 1; j >= 0; j--)
            {
                if (str1[i].equalsIgnoreCase(str2[j]))
                    opt[i][j] = opt[i + 1][j + 1] + 1;// ״̬ת�Ʒ���
                else
                    opt[i][j] = Math.max(opt[i + 1][j], opt[i][j + 1]);// ״̬ת�Ʒ���
            }
        }
 
//        int i = 0, j = 0;
//        while (i < substringLength1 && j < substringLength2)
//        {
//            if (str1[i].equalsIgnoreCase(str2[j]))
//            {
//                i++;
//                j++;
//            }
//            else if (opt[i + 1][j] >= opt[i][j + 1])
//                i++;
//            else
//                j++;
//        }
        return opt[0][0] / (double)substringLength1;
    }
}
