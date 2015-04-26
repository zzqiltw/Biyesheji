package Tools;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import BLEUModel.BLEUModel;

public class XMLTools {
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
			seg.addAttribute("id", i+"");
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
	

	public static void main(String[] args) throws Exception {
//		String filename = "/Users/zzqiltw/Desktop/mteval-v13a-20091001/mteval-v13a-20091001/example/ref.xml";
//		List<String> results = readBLEUXml(filename);
//		results.add(0, "hahah");
//		
//		String outputFilename = "/Users/zzqiltw/Desktop/outputXml.xml";
//		write2XML(results, outputFilename);
		
		List<String> testSrcContent = FileTools.getFileContentWithoutSpace("Data/dev.HIT.lowcased.zh");
		write2plist(testSrcContent, "/Users/zzqiltw/Desktop/devSrc.plist");
	}
}
