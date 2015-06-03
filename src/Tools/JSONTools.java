package Tools;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.stream.events.StartDocument;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import BLEUMainWork.BLEUMain;
import Main.MainWork;
import Model.MachineTra4Result;
import Model.StandardTraAnd4MTResult;
import Model.TrainSentenceModel;

public class JSONTools {
	private static List<String> generateJson(
			List<StandardTraAnd4MTResult> models) throws Exception {
		List<String> results = new ArrayList<>();
		for (StandardTraAnd4MTResult model : models) {
			Map map = convertBean(model);

			map.remove("standTra");
			map.remove("tra4Result");
			map.remove("topNSim");
			map.remove("topSimTrainSentenceModel");
			map.remove("trainSentenceModel");

			JSONObject jsonObject = new JSONObject(map);
			jsonObject.put("Id", model.getTra4Result().getID());
			results.add(jsonObject.toString());
		}
		return results;
	}

	public static List<MachineTra4Result> readJsonFile(String filename)
			throws Exception {
		String jsonString = FileTools.getFileString(filename);
		JSONArray jsonArray = new JSONArray(jsonString);

		int count = jsonArray.length();
		List<MachineTra4Result> resultArrayList = new ArrayList<MachineTra4Result>();

		for (int i = 0; i < count; ++i) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Iterator<String> iterator = jsonObject.keys();
			Map<String, Object> map = new HashMap<String, Object>();
			String key = null;
			Object value = null;
			while (iterator.hasNext()) {
				key = iterator.next();
				value = jsonObject.get(key);
				if (value == null || value.equals("")) {
					System.out.println("==============find a null value");
				}
				map.put(key, value);
			}
			MachineTra4Result result = (MachineTra4Result) convertMap(
					MachineTra4Result.class, map);
			resultArrayList.add(result);
		}

		return resultArrayList;
	}

	public static List<StandardTraAnd4MTResult> readJsonFile2STAMTR(
			String filenamePre) throws Exception {
		List<StandardTraAnd4MTResult> resultArrayList = new ArrayList<StandardTraAnd4MTResult>();

		for (int j = 0; j < 10; ++j) {
			String filename = filenamePre + j;
//			String jsonString = FileTools.getFileString(filename);
			List<String> jsonStrings = FileTools.getFileContent(filename);

			int count = jsonStrings.size();

			for (int i = 0; i < count; ++i) {
				JSONObject jsonObject = new JSONObject(jsonStrings.get(i));
				Iterator<String> iterator = jsonObject.keys();
				Map<String, Object> map = new HashMap<String, Object>();
				String key = null;
				Object value = null;
				while (iterator.hasNext()) {
					key = iterator.next();
					value = jsonObject.get(key);
					map.put(key, value);
				}

				StandardTraAnd4MTResult result = (StandardTraAnd4MTResult) convertMap(
						StandardTraAnd4MTResult.class, map);
				resultArrayList.add(result);
			}
		}
		return resultArrayList;
	}

	public static Map convertBean(Object bean) throws IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	public static Object convertMap(Class type, Map map)
			throws IntrospectionException, IllegalAccessException,
			InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		Object obj = type.newInstance();
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
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

	public static void premethod() throws Exception {
		List<MachineTra4Result> data = new ArrayList<>();
		for (int i = 0; i < 5; ++i) {
			List<MachineTra4Result> per100 = readJsonFile("Data/testSrcAnd4TraOutputFile"
					+ i);
			data.addAll(per100);
		}
		int count = data.size();
		MachineTra4Result one = null;
		TrainSentenceModel topSim = null;
		String standardTra = null;
		MainWork mainWork = new MainWork();
		BLEUMain bleuMain = new BLEUMain();
		List<StandardTraAnd4MTResult> results = new ArrayList<>();
		List<Double> fakeRefScores = new ArrayList<>();
		List<Double> realRefScores = new ArrayList<>();
		int j = 9;
		for (int i = j * 50; i < (j + 1) * 50; ++i) {
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

			fakeRefScores.add(stamtr.getBaiduBLEUScore());
			// fakeRefScores.add(stamtr.getBingBLEUScore());
			// fakeRefScores.add(stamtr.getGoogleBLEUScore());
			// fakeRefScores.add(stamtr.getYoudaoBLEUScore());

			realRefScores.add(stamtr.getBaiduRefBLEUScore());
			// realRefScores.add(stamtr.getBingRefBLEUScore());
			// realRefScores.add(stamtr.getGoogleRefBLEUScore());
			// realRefScores.add(stamtr.getYoudaoRefBLEUScore());
		}

		double similarity = Similarity.getSim(realRefScores, fakeRefScores);
		;
		System.out.println(similarity);

		FileTools.write2File(generateJson(results),
				"FinalOutput/DevBleuScoresSimTopOneJsonFileOtherMethod" + j);
	}

	public static void aftermethod() throws Exception {

		List<MachineTra4Result> data = new ArrayList<>();
		for (int i = 0; i < 5; ++i) {
			List<MachineTra4Result> per100 = readJsonFile("Data/testSrcAnd4TraOutputFile"
					+ i);
			data.addAll(per100);
		}
		int count = data.size();
		MachineTra4Result one = null;
		List<TrainSentenceModel> topSimN = null;
		String standardTra = null;
		MainWork mainWork = new MainWork();
		BLEUMain bleuMain = new BLEUMain();
		List<StandardTraAnd4MTResult> results = new ArrayList<>();
		List<String> resultString2File = new ArrayList<>();
		List<Double> fakeRefScores = new ArrayList<>();
		List<Double> realRefScores = new ArrayList<>();

		for (int i = 0; i < count; ++i) {
			System.out.println(i);
			one = data.get(i);
			topSimN = mainWork.findTopNSimSentence(one.getID(), 5);
			standardTra = mainWork.getStandardTra(i);
			StandardTraAnd4MTResult stamtr = new StandardTraAnd4MTResult();
			stamtr.setTra4Result(one);
			stamtr.setTopNSim(topSimN);
			stamtr.setStandTra(standardTra);
			stamtr.countAllBLEUScores(bleuMain, 1);
			results.add(stamtr);
			resultString2File.add(stamtr.toString());

			fakeRefScores.add(stamtr.getBaiduBLEUScore());
			// fakeRefScores.add(stamtr.getBingBLEUScore());
			// fakeRefScores.add(stamtr.getGoogleBLEUScore());
			// fakeRefScores.add(stamtr.getYoudaoBLEUScore());

			realRefScores.add(stamtr.getBaiduRefBLEUScore());
			// realRefScores.add(stamtr.getBingRefBLEUScore());
			// realRefScores.add(stamtr.getGoogleRefBLEUScore());
			// realRefScores.add(stamtr.getYoudaoRefBLEUScore());
		}

		double similarity = Similarity.getSim(realRefScores, fakeRefScores);
		;
		System.out.println(similarity);
		FileTools.write2File(resultString2File,
				"FinalOutput/DevBleuScoresSimTop5");
	}


	public static void main(String[] args) throws Exception {
//		new JSONTools().premethod();
//		 new JSONTools().aftermethod();
//		new JSONTools().test();
		List<MachineTra4Result> data = new ArrayList<>();
		for (int i = 0; i < 5; ++i) {
			List<MachineTra4Result> per100 = readJsonFile("Data/testSrcAnd4TraOutputFile"
					+ i);
			data.addAll(per100);
		}
		
	}
	
	private void test() throws Exception {
		List<StandardTraAnd4MTResult> results = readJsonFile2STAMTR("FinalOutput/DevBleuScoresSimTopOneJsonFileOtherMethod");
		
		List<Double> baiduXList = new ArrayList<>();
		List<Double> baiduYList = new ArrayList<>();
		List<Double> baiduZList = new ArrayList<>();
		
		List<Double> googleXList = new ArrayList<>();
		List<Double> googleYList = new ArrayList<>();
		List<Double> googleZList = new ArrayList<>();
		
		List<Double> bingXList = new ArrayList<>();
		List<Double> bingYList = new ArrayList<>();
		List<Double> bingZList = new ArrayList<>();
		
		List<Double> youdaoXList = new ArrayList<>();
		List<Double> youdaoYList = new ArrayList<>();
		List<Double> youdaoZList = new ArrayList<>();
		
		List<Double> totalXList = new ArrayList<>();
		List<Double> totalYList = new ArrayList<>();
		List<Double> totalZList = new ArrayList<>();
		
		for (StandardTraAnd4MTResult result : results) {
			baiduXList.add(result.getBaiduBLEUScore());
			baiduYList.add(result.getBaiduRefBLEUScore());
			baiduZList.add(result.getBaiduEachOtherBLEUScore());
			
			googleXList.add(result.getGoogleBLEUScore());
			googleYList.add(result.getGoogleRefBLEUScore());
			googleZList.add(result.getGoogleEachOtherBLEUScore());
			
			youdaoXList.add(result.getYoudaoBLEUScore());
			youdaoYList.add(result.getYoudaoRefBLEUScore());
			youdaoZList.add(result.getYoudaoEachOtherBLEUScore());
			
			bingXList.add(result.getBingBLEUScore());
			bingYList.add(result.getBingRefBLEUScore());
			bingZList.add(result.getBingEachOtherBLEUScore());
		}
		totalXList.addAll(baiduXList);
		totalXList.addAll(googleXList);
		totalXList.addAll(youdaoXList);
		totalXList.addAll(bingXList);

		totalYList.addAll(baiduYList);
		totalYList.addAll(googleYList);
		totalYList.addAll(youdaoYList);
		totalYList.addAll(bingYList);
		
		totalZList.addAll(baiduZList);
		totalZList.addAll(googleZList);
		totalZList.addAll(youdaoZList);
		totalZList.addAll(bingZList);
		
		double sim = 0;
		sim = Similarity.getSim(baiduXList, baiduYList);
		System.out.println("baidu:X-Y=" + sim);
		sim = Similarity.getSim(youdaoXList, youdaoYList);
		System.out.println("youdao:X-Y=" + sim);
		sim = Similarity.getSim(googleXList, googleYList);
		System.out.println("google:X-Y=" + sim);
		sim = Similarity.getSim(bingXList, bingYList);
		System.out.println("bing:X-Y=" + sim);
		sim = Similarity.getSim(totalXList, totalYList);
		System.out.println("total:X-Y=" + sim + "\n");
		
		sim = Similarity.getSim(baiduZList, baiduYList);
		System.out.println("baidu:Z-Y=" + sim);
		sim = Similarity.getSim(youdaoZList, youdaoYList);
		System.out.println("youdao:Z-Y=" + sim);
		sim = Similarity.getSim(googleZList, googleYList);
		System.out.println("google:Z-Y=" + sim);
		sim = Similarity.getSim(bingZList, bingYList);
		System.out.println("bing:Z-Y=" + sim);
		sim = Similarity.getSim(totalZList, totalYList);
		System.out.println("total:Z-Y=" + sim + "\n");
	}

}
