package Tools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
public class FileTools {
	public static List<String> getFileContent(String filename) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
		String aLine = null;
		List<String> fileContent = new ArrayList<String>();
		while ((aLine = br.readLine()) != null) {
			aLine = aLine.replaceAll("[，。！？、]", "");//去除中文标点以计算相似度
			aLine = aLine.trim();
			fileContent.add(aLine);
		}
		br.close();
		return fileContent;
	}
	
	private static String addSpaceToCNLine(String cn) {
		int length = cn.length();
		StringBuffer sb = new StringBuffer(cn);
		for (int i = 1; i < length * 2 - 1; i += 2) {
			sb.insert(i, " ");
		}
		return new String(sb);
	}
	
	public static List<String> getFileContentAddSpaceEachWordWithOutAnySymbol(String filename) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
		String aLine = null;
		List<String> fileContent = new ArrayList<String>();
		while ((aLine = br.readLine()) != null) {
			aLine = aLine.replaceAll("[，。！？、,\\.\\!\\?\\- ]", "");//去除中文标点以计算相似度
			aLine = aLine.trim();
			aLine = addSpaceToCNLine(aLine);
			fileContent.add(aLine);
		}
		br.close();
		return fileContent;
	}
	
	public static List<String> getFileContentWithOutAnySymbol(String filename) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
		String aLine = null;
		List<String> fileContent = new ArrayList<String>();
		while ((aLine = br.readLine()) != null) {
			aLine = aLine.replaceAll("[，。！？、,\\.\\!\\?\\-]", "");//去除中文标点以计算相似度
			aLine = aLine.replaceAll("\\s{2,}", " ");
			aLine = aLine.trim();
			fileContent.add(aLine);
		}
		br.close();
		return fileContent;
	}
	
	
	
	public static String getFileString(String filename) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
		String aLine = null;
		StringBuffer result = new StringBuffer();
		while ((aLine = br.readLine()) != null) {
			aLine = aLine.replaceAll("[，。！？、]", "");
			aLine = aLine.trim();
			result.append(aLine);
		}
		br.close();
		return new String(result);
	}
	
	public static List<String> getFileContentWithoutSpace(String filename) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
		String aLine = null;
		List<String> fileContent = new ArrayList<String>();
		while ((aLine = br.readLine()) != null) {
			aLine = aLine.replaceAll("\\s", "");
			aLine = aLine.trim();
			fileContent.add(aLine);
		}
		br.close();
		return fileContent;
	}
	
	public static void write2File(List<String> content , String filename) throws Exception {
		PrintWriter pw = new PrintWriter(new FileOutputStream(new File(filename), true));
		for (String string : content) {
			pw.println(string);
		}
		pw.close();
	}
	
	public static void write2FileForString(String content , String filename) throws Exception {
		PrintWriter pw = new PrintWriter(new FileOutputStream(new File(filename), true));
		pw.println(content);
		pw.close();
	}
	
	public static void main(String[] args) {
		List<String> content;
		try {
			content = FileTools.getFileContent("Data/test.lowcased.zh");
			FileTools.write2File(content, "Output/testtool.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
