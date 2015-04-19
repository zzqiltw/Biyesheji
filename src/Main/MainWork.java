package Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import BusinessService.EditDistanceWorker;
import BusinessService.LcsWorker;
import BusinessService.SetWorker;
import Model.TestModel;
import Model.TrainSentenceModel;
import Tools.FileTools;

public class MainWork {
	private static final String trainSentenceSrcFileName = "Data/train.HIT.lowcased.zh";
	private static final String trainSentenceTraFileName = "Data/train.HIT.lowcased.en";
	private static final String testedFileName = "Data/test.lowcased.zh";

	private List<TrainSentenceModel> trainSentenceModels;
	private List<TestModel> testModels;

	public void generateSentenceModels() throws Exception {
		List<String> srcContent = FileTools
				.getFileContent(trainSentenceSrcFileName);
		List<String> traContent = FileTools
				.getFileContent(trainSentenceTraFileName);

		if (srcContent.size() != traContent.size())
			return;

		if (this.trainSentenceModels == null) {
			this.trainSentenceModels = new ArrayList<>();
		}
		int count = srcContent.size();

		for (int i = 0; i < count; ++i) {
			TrainSentenceModel model = new TrainSentenceModel();
			model.setId(i);
			model.setSrcText(srcContent.get(i));
			model.setTraText(traContent.get(i));
			this.trainSentenceModels.add(model);
		}
	}

	// public void generateTestModel() throws Exception {
	// long startTime = System.currentTimeMillis();
	// if (this.testModels == null) {
	// this.testModels = new ArrayList<>();
	// }
	//
	// if (this.trainSentenceModels == null) {
	// this.generateSentenceModels();
	// }
	//
	// List<String> testSrcContent = FileTools.getFileContent(testedFileName);
	// int topCount = testSrcContent.size();
	// // for (int topIndex = 0; topIndex < topCuont; ++topIndex) {
	// int topIndex = 0;
	// String aLine = testSrcContent.get(topIndex);
	//
	// TestModel testModel = new TestModel();
	// testModel.setTestedSentence(aLine);
	//
	// int count = this.trainSentenceModels.size();
	// // int count = 500;
	// for (int i = 0; i < count; ++i) {
	// TrainSentenceModel traModel = this.trainSentenceModels.get(i);
	//
	// testModel.countScores(new LcsWorker(), traModel);
	// testModel.countScores(new SetWorker(), traModel);
	// testModel.countScores(new EditDistanceWorker(), traModel);
	// }
	//
	// Collections.sort(testModel.getEditDistanceScores());
	// Collections.sort(testModel.getSetScores());
	// Collections.sort(testModel.getLcsScores());
	//
	// this.testModels.add(testModel);
	// testModel.generateString2File();
	//
	// FileTools.write2File(testModel.getEditDistanceScore2FileStringList(),
	// testModel.getFilenamePre() + topIndex + "_edit.txt");
	// FileTools.write2File(testModel.getLcsScore2FileStringList(),
	// testModel.getFilenamePre() + topIndex + "_lcs.txt");
	// FileTools.write2File(testModel.getSetScore2FileStringList(),
	// testModel.getFilenamePre() + topIndex + "_set.txt");
	// }

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
	
//	public void generateTestModel() throws Exception {
//		long startTime = System.currentTimeMillis();
//		if (this.testModels == null) {
//			this.testModels = new ArrayList<>();
//		}
//
//		if (this.trainSentenceModels == null) {
//			this.generateSentenceModels();
//		}
//
//		List<String> testSrcContent = FileTools.getFileContent(testedFileName);
//		int topCount = testSrcContent.size();
//		int count = this.trainSentenceModels.size();
//		System.out.println(topCount);
//		for (int topIndex = 0; topIndex < topCount; ++topIndex) {
//			System.out.println(topIndex);
//			String aLine = testSrcContent.get(topIndex);
//
//			TestModel testModel = new TestModel();
//			testModel.setTestedSentence(aLine);
//
//			for (int i = 0; i < count; ++i) {
//				TrainSentenceModel traModel = this.trainSentenceModels.get(i);
//
//				testModel.countScores(new LcsWorker(), traModel);
//				testModel.countScores(new SetWorker(), traModel);
//				testModel.countScores(new EditDistanceWorker(), traModel);
//			}
//
//			testModel.filt(0.1);
//			
//			this.testModels.add(testModel);
//
//			testModel.generateString2File();
//
//			FileTools.write2File(
//					testModel.getEditDistanceScore2FileStringList(),
//					testModel.getFilenamePre() + topIndex + "_edit.txt");
//			FileTools.write2File(testModel.getLcsScore2FileStringList(),
//					testModel.getFilenamePre() + topIndex + "_lcs.txt");
//			FileTools.write2File(testModel.getSetScore2FileStringList(),
//					testModel.getFilenamePre() + topIndex + "_set.txt");
//		}
//		System.out.println(System.currentTimeMillis() - startTime);
//	}

	public static void main(String[] args) {
		try {
			new MainWork().generateTestModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
