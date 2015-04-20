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
			aLine = aLine.replaceAll("[，。！？、]", "");
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
