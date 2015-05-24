package Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import BusinessService.EditDistanceWorker;
import BusinessService.LcsWorker;
import BusinessService.SetWorker;
import Model.TestModel;
import Model.TrainSentenceModel;
import Tools.FileTools;
import Tools.JSONTools;
import Tools.XMLTools;

public class MainWork {
	public static final String trainSentenceSrcFileName = "Data/train.HIT.lowcased.zh";
	public static final String trainSentenceTraFileName = "Data/train.HIT.lowcased.en";
	
//	private static final String testedFileName = "Data/test.lowcased.zh";
//	private static final String testedTraFileName = "Data/test.lowcased.en";
	
	private List<String> testSrcContent;
	private List<String> testTraContent;
	
	private List<String> trainSrcContent;
	private List<String> trainTraContent;
	
	private static final String testedFileName = "Data/dev.HIT.lowcased.zh";
	private static final String testedTraFileName = "Data/dev.HIT.lowcased.en";
	
	private static final String testSrcOutputFileName = "FinalOutput/FinalXmlOutput/src.xml";
	private static final String testTraOutputFileName = "FinalOutput/FinalXmlOutput/tra.xml";
	private static final String testFakeRefOutputFileName = "FinalOutput/FinalXmlOutput/fake_ref.xml";
	
	
	private List<TrainSentenceModel> trainSentenceModels;
	
	private List<TestModel> testModels;

	private List<TrainSentenceModel> topOneTrainSentenceModels;
	
	
	public void generateSentenceModels() throws Exception {
		if (this.trainSrcContent == null) { 
			this.trainSrcContent = FileTools.getFileContent(trainSentenceSrcFileName);
		} 
		
		if (this.trainTraContent == null) {
			this.trainTraContent = FileTools.getFileContent(trainSentenceTraFileName);
		}
		

		if (this.trainSrcContent.size() != this.trainTraContent.size())
			return;

		if (this.trainSentenceModels == null) {
			this.trainSentenceModels = new ArrayList<>();
		}
		int count = this.trainSrcContent.size();

		for (int i = 0; i < count; ++i) {
			TrainSentenceModel model = new TrainSentenceModel();
			model.setId(i);
			model.setSrcText(this.trainSrcContent.get(i));
			model.setTraText(this.trainTraContent.get(i));
			this.trainSentenceModels.add(model);
		}
	}

	public void generateTestModel() throws Exception {
		long startTime = System.currentTimeMillis();
		if (this.testModels == null) {
			this.testModels = new ArrayList<>();
		}

		if (this.trainSentenceModels == null) {
			this.generateSentenceModels();
		}

		List<String> testSrcContent = FileTools.getFileContent(testedFileName);
		int topCount = testSrcContent.size();
		int count = this.trainSentenceModels.size();
		System.out.println(topCount);
		for (int topIndex = 300; topIndex < 301; ++topIndex) {
			System.out.println(topIndex);
			String aLine = testSrcContent.get(topIndex);

			TestModel testModel = new TestModel();
			testModel.setTestedSentence(aLine);

			for (int i = 0; i < count; ++i) {
				TrainSentenceModel traModel = this.trainSentenceModels.get(i);

				testModel.countScores(new LcsWorker(), traModel);
				testModel.countScores(new SetWorker(), traModel);
				testModel.countScores(new EditDistanceWorker(), traModel);
			}

			testModel.finalWork(0.2);
			
			this.testModels.add(testModel);

			List<String> string2File = new ArrayList<>();
			string2File.add(testModel.getTestedSentence());
			string2File.addAll(testModel.getFinalScore2FileStringList());
			FileTools.write2File(
					string2File,
					TestModel.OutputFinalFileNamePre + topIndex + "_finalResult.txt");
		}
		System.out.println(System.currentTimeMillis() - startTime);
	}
	
	public void generateTestModel2XML() throws Exception {
		long startTime = System.currentTimeMillis();
		if (this.testModels == null) {
			this.testModels = new ArrayList<>();
		}
		
		if (this.topOneTrainSentenceModels == null) {
			this.topOneTrainSentenceModels = new ArrayList<>();
		}

		if (this.trainSentenceModels == null) {
			this.generateSentenceModels();
		}
		
		if (this.testSrcContent == null) { 
			testSrcContent = FileTools.getFileContent(testedFileName);
		} 
		
		if (this.testTraContent == null) {
			testTraContent = FileTools.getFileContent(testedTraFileName);
		}
		
		int topCount = testSrcContent.size();
		
		int count = this.trainSentenceModels.size();

		List<String> testTraXmlContent = new ArrayList<>();
		List<String> srcXmlContent = new ArrayList<>();
		
		
		for (int topIndex = 0; topIndex < 10; ++topIndex) {
			String aLine = testSrcContent.get(topIndex);
			String traLine = testTraContent.get(topIndex);
			
			TestModel testModel = new TestModel();
			testModel.setTestedSentence(aLine);
			testModel.setTraSentence(traLine);
			
			for (int i = 0; i < count; ++i) {
				TrainSentenceModel traModel = this.trainSentenceModels.get(i);

				testModel.countScores(new LcsWorker(), traModel);
				testModel.countScores(new SetWorker(), traModel);
				testModel.countScores(new EditDistanceWorker(), traModel);
			}

			TrainSentenceModel topOne = testModel.finalWorkChoseOne();
			
			this.testModels.add(testModel);
			this.topOneTrainSentenceModels.add(topOne);
			
			testTraXmlContent.add(testModel.getTraSentence());
			srcXmlContent.add(topOne.getTraText());
		}
		
		XMLTools.write2XML(testSrcContent, testSrcOutputFileName);
		XMLTools.write2XML(srcXmlContent, testFakeRefOutputFileName);
		XMLTools.write2XML(testTraXmlContent, testTraOutputFileName);
		
		System.out.println(System.currentTimeMillis() - startTime);
	}
	
	public TrainSentenceModel findTopSimSentence(int ID) throws Exception {
		if (this.testModels == null) {
			this.testModels = new ArrayList<>();
		}
		
		if (this.topOneTrainSentenceModels == null) {
			this.topOneTrainSentenceModels = new ArrayList<>();
		}

		if (this.trainSentenceModels == null) {
			this.generateSentenceModels();
		}
		
		if (this.testSrcContent == null) { 
			testSrcContent = FileTools.getFileContent(testedFileName);
		} 
		
		if (this.testTraContent == null) {
			testTraContent = FileTools.getFileContent(testedTraFileName);
		}
		
		
		int count = this.trainSentenceModels.size();
			String aLine = testSrcContent.get(ID);
			String traLine = testTraContent.get(ID);
			
			TestModel testModel = new TestModel();
			testModel.setTestedSentence(aLine);
			testModel.setTraSentence(traLine);
			
			for (int i = 0; i < count; ++i) {
				TrainSentenceModel traModel = this.trainSentenceModels.get(i);

				testModel.countScores(new LcsWorker(), traModel);
				testModel.countScores(new SetWorker(), traModel);
				testModel.countScores(new EditDistanceWorker(), traModel);
			}

			TrainSentenceModel topOne = testModel.finalWorkChoseOne();
			
			this.testModels.add(testModel);
		
			return topOne;
	}
	
	public List<TrainSentenceModel> findTopNSimSentence(int ID, int topN) throws Exception {
		if (this.testModels == null) {
			this.testModels = new ArrayList<>();
		}
		
		if (this.topOneTrainSentenceModels == null) {
			this.topOneTrainSentenceModels = new ArrayList<>();
		}

		if (this.trainSentenceModels == null) {
			this.generateSentenceModels();
		}
		
		if (this.testSrcContent == null) { 
			testSrcContent = FileTools.getFileContent(testedFileName);
		} 
		
		if (this.testTraContent == null) {
			testTraContent = FileTools.getFileContent(testedTraFileName);
		}
		
		int count = this.trainSentenceModels.size();
			String aLine = testSrcContent.get(ID);
			String traLine = testTraContent.get(ID);
			
			TestModel testModel = new TestModel();
			testModel.setTestedSentence(aLine);
			testModel.setTraSentence(traLine);
			
			for (int i = 0; i < count; ++i) {
				TrainSentenceModel traModel = this.trainSentenceModels.get(i);

				testModel.countScores(new LcsWorker(), traModel);
				testModel.countScores(new SetWorker(), traModel);
				testModel.countScores(new EditDistanceWorker(), traModel);
			}

		
			List<TrainSentenceModel> result = testModel.finalWorkChoseTopN(topN);
			this.testModels.add(testModel);
		
			return result;
	}
	
	
	public String getStandardTra(int ID) throws Exception {
		if (this.testTraContent == null) {
			testTraContent = FileTools.getFileContent(testedTraFileName);
		}
		return this.testTraContent.get(ID);
	}
	
	public static void main(String[] args) {
		try {
			new MainWork().generateTestModel2XML();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
