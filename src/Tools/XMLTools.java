package Tools;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.json.JSONArray;

import BLEUModel.BLEUModel;
import Main.MainWork;
import Model.TrainSentenceModel;

public class XMLTools {
	public static final String trainSentenceSrcFileName = "Data/train.HIT.lowcased.zh";
	public static final String trainSentenceTraFileName = "Data/train.HIT.lowcased.en";
	public static final String devSentenceSrcFileName = "Data/dev.HIT.lowcased.zh";
	public static final String devSentenceTraFileName = "Data/dev.HIT.lowcased.en";
	
	public static List<String> readBLEUXml(String filename) throws Exception {
		SAXReader sax = new SAXReader();
		Document xmlDoc = sax.read(new File(filename));
		Element root = xmlDoc.getRootElement();
		List<Node> segNodes = root.selectNodes("//seg");
		List<String> results = new ArrayList<>();
		for (int i = 0; i < segNodes.size(); i++) {
			Node seg = (Node) segNodes.get(i);
			results.add(seg.getText());
		}
		return results;
	}

	public static void write2XML(List<String> strs, String fileName) {
		Document document = DocumentHelper.createDocument();
		Element mteval = document.addElement("mteval");
		Element refset = mteval.addElement("refset");
		Element doc = refset.addElement("doc");
		int count = strs.size();
		for (int i = 0; i < count; ++i) {
			Element p = doc.addElement("p");
			Element seg = p.addElement("seg");
			seg.addAttribute("id", i + "");
			seg.addText(strs.get(i));
		}

		try {
			XMLWriter writer = new XMLWriter(new FileWriter(new File(fileName)));
			writer.write(document);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void write2plist(List<String> strs, String fileName) {
		Document document = DocumentHelper.createDocument();
		Element plist = document.addElement("plist");
		Element array = plist.addElement("array");
		int count = strs.size();
		for (int i = 0; i < count; ++i) {
			Element string = array.addElement("string");
			string.addText(strs.get(i));
		}

		try {
			XMLWriter writer = new XMLWriter(new FileWriter(new File(fileName)));
			writer.write(document);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void write2plistForTrainSet() throws Exception {
		List<String> srcStrings = FileTools
				.getFileContentAddSpaceEachWordWithOutAnySymbol(trainSentenceSrcFileName);

		List<String> traStrings = FileTools
				.getFileContentWithOutAnySymbol(trainSentenceTraFileName);

		int count = srcStrings.size();
		// int count = 3;
		List<Map<String, String>> list = new ArrayList<>();
		for (int i = 0; i < count; ++i) {
			Map<String, String> map = new HashMap<String, String>();

			String srcString = srcStrings.get(i);
			String traString = traStrings.get(i);

			map.put("s", srcString);
			map.put("t", traString);

			list.add(map);
		}

		int jMax = 49;
		int perCount = count / jMax;
		for (int j = 0; j < jMax + 1; ++j) {
			Document document = DocumentHelper.createDocument();
			Element plist = document.addElement("plist");
			Element array = plist.addElement("array");
			int perMax = (j + 1) * perCount > count ?  count : (j + 1) * perCount;
			for (int i = j * perCount; i < perMax; ++i) {
				Map<String, String> map = list.get(i);
				Element dict = array.addElement("dict");

				Element keyString = dict.addElement("key");
				keyString.addText("s");
				Element key = dict.addElement("string");
				key.addText(map.get("s"));

				Element valueString = dict.addElement("key");
				valueString.addText("t");
				Element value = dict.addElement("string");
				value.addText(map.get("t"));
			}

			try {
				XMLWriter writer = new XMLWriter(new FileWriter(new File(
						"/Users/zzqiltw/Desktop/bleuplists/s_t"+j+".plist")));
				writer.write(document);
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void write2plistForDevTop400Set(List<String> srcStrings, List<String> traStrings, int offset, int step) throws Exception {
		int count = step;
		List<Map<String, String>> list = new ArrayList<>();
		MainWork mainWork = new MainWork();
		for (int i = offset; i < offset + step; ++i) {
			System.out.println(i);
			TrainSentenceModel top1 = mainWork.findTopSimSentence(i);
			
			Map<String, String> map = new HashMap<String, String>();

			String srcString = srcStrings.get(i);
			String traString = traStrings.get(i);
			String simTraString = top1.getTraText();

			map.put("s", srcString);
			map.put("t", traString);
			map.put("simT", simTraString);

			list.add(map);
		}
		
		Document document = DocumentHelper.createDocument();
		Element plist = document.addElement("plist");
		Element array = plist.addElement("array");
		for (int i = 0; i < count; ++i) {
			Map<String, String> map = list.get(i);
			Element dict = array.addElement("dict");

			Element keyString = dict.addElement("key");
			keyString.addText("s");
			Element key = dict.addElement("string");
			key.addText(map.get("s"));

			Element valueString = dict.addElement("key");
			valueString.addText("t");
			Element value = dict.addElement("string");
			value.addText(map.get("t"));
			
			Element simTraString = dict.addElement("key");
			simTraString.addText("simT");
			Element simTra = dict.addElement("string");
			simTra.addText(map.get("simT"));
		}

		try {
			XMLWriter writer = new XMLWriter(new FileWriter(new File(
					"/Users/zzqiltw/Desktop/SystemCombinePlist/devSrcTraSimTop400OfPage"+(offset/step)+".plist")));
			writer.write(document);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	public static void main(String[] args) throws Exception {
		List<String> srcStrings = FileTools
				.getFileContentWithOutAnySymbol(devSentenceSrcFileName);
		List<String> traStrings = FileTools
				.getFileContentWithOutAnySymbol(devSentenceTraFileName);
		
		int page = 7;
		int step = 50;
		int offset = page * step;
		write2plistForDevTop400Set(srcStrings, traStrings, offset, step);
		
	}
}
