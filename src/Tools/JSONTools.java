package Tools;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.stream.events.StartDocument;

import org.json.JSONArray;
import org.json.JSONObject;

import BLEUMainWork.BLEUMain;
import Main.MainWork;
import Model.MachineTra4Result;
import Model.StandardTraAnd4MTResult;
import Model.TrainSentenceModel;

public class JSONTools {
	public static List<MachineTra4Result> readJsonFile(String filename) throws Exception {
		String jsonString = FileTools.getFileString(filename);
		JSONArray jsonArray = new JSONArray(jsonString);
	
		int count = jsonArray.length();
		List<MachineTra4Result> resultArrayList = new ArrayList<MachineTra4Result>();
		
		for (int i = 0; i < count; ++i) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Iterator<String> iterator =	jsonObject.keys();
			Map<String, Object> map = new HashMap<String, Object>();
			String key = null;
			Object value = null;
			while (iterator.hasNext()) {
				key = iterator.next();
				value = jsonObject.get(key);
				map.put(key, value);
			}
			MachineTra4Result result = (MachineTra4Result) convertMap(MachineTra4Result.class, map);
			resultArrayList.add(result);
		}
		
		return resultArrayList;
	}
	
	 public static Object convertMap(Class type, Map map) 
	            throws IntrospectionException, IllegalAccessException, 
	            InstantiationException, InvocationTargetException { 
	        BeanInfo beanInfo = Introspector.getBeanInfo(type);  
	        Object obj = type.newInstance(); 
	        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
	        for (int i = 0; i< propertyDescriptors.length; i++) { 
	            PropertyDescriptor descriptor = propertyDescriptors[i]; 
	            String propertyName = descriptor.getName(); 

	            if (map.containsKey(propertyName)) { 
	                Object value = map.get(propertyName); 

	                Object[] args = new Object[1]; 
	                args[0] = value; 

	                descriptor.getWriteMethod().invoke(obj, args); 
	            } 
	        } 
	        return obj; 
	    } 
	
	public static void main(String[] args) throws Exception {
		List<MachineTra4Result> data = new ArrayList<>();
		for (int i = 0; i < 5; ++i) {
			List<MachineTra4Result> per100 = readJsonFile("Data/testSrcAnd4TraOutputFile" + i);
			data.addAll(per100);
		}
	
		int count = data.size();
		
		MachineTra4Result one = null;
		TrainSentenceModel topSim = null;
		String standardTra = null;
		
		MainWork mainWork = new MainWork();
		
		BLEUMain bleuMain = new BLEUMain();

		List<StandardTraAnd4MTResult> results = new ArrayList<>();
		List<String> resultString2File = new ArrayList<>();
		
		for (int i = 0; i < 50; ++i) {
			System.out.println(i);
			one = data.get(i);
			topSim = mainWork.findTopSimSentence(one.getID());

			standardTra = mainWork.getStandardTra(i);
			
			StandardTraAnd4MTResult stamtr = new StandardTraAnd4MTResult();
			stamtr.setTra4Result(one);
			stamtr.setTrainSentenceModel(topSim);
			stamtr.setStandTra(standardTra);
			
			stamtr.countAllBLEUScores(bleuMain, 1);
			
			results.add(stamtr);
			resultString2File.add(stamtr.toString());
		}
		FileTools.write2File(resultString2File, "FinalOutput/Top50DevResults");
	}
}
