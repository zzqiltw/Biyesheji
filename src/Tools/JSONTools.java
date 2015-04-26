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

import org.json.JSONArray;
import org.json.JSONObject;

import Model.MachineTra4Result;

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
	        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性 
	        Object obj = type.newInstance(); // 创建 JavaBean 对象 

	        // 给 JavaBean 对象的属性赋值 
	        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
	        for (int i = 0; i< propertyDescriptors.length; i++) { 
	            PropertyDescriptor descriptor = propertyDescriptors[i]; 
	            String propertyName = descriptor.getName(); 

	            if (map.containsKey(propertyName)) { 
	                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。 
	                Object value = map.get(propertyName); 

	                Object[] args = new Object[1]; 
	                args[0] = value; 

	                descriptor.getWriteMethod().invoke(obj, args); 
	            } 
	        } 
	        return obj; 
	    } 
	
	public static void main(String[] args) throws Exception {
//		List<MachineTra4Result> results = readJsonFile("Data/test_set_first100_json");
		List<MachineTra4Result> results = readJsonFile("/Users/zzqiltw/Desktop/testSrcAnd4TraOutputFile");
		System.out.println(results);
	}
}
