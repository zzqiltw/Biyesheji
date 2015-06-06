package SystemCombine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import BLEUMainWork.BLEUMain;
import Main.MainWork;
import Model.MachineTra4Result;
import Model.StandardTraAnd4MTResult;
import Model.TrainSentenceModel;

public class FinalEXModel {
	private String origonSrcString;
	private String origonTraString;
	private String simTraString;
	private String bestOfFourString;
	private String systemCombineString;
	
	
	public String getOrigonSrcString() {
		return origonSrcString;
	}

	public String getOrigonTraString() {
		return origonTraString;
	}
	public String getBestOfFourString() {
		return bestOfFourString;
	}
	public String getSystemCombineString() {
		return systemCombineString;
	}
	public String getSimTraString() {
		return simTraString;
	}
	
	public FinalEXModel(SCModel scModel, MachineTra4Result tra4Result) {
		this.origonSrcString = scModel.getS();
		this.origonTraString = scModel.getT();
		this.simTraString = scModel.getSimT();
		try {
			this.getBestOf4(tra4Result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.systemCombineString = this.getSystemCombine(tra4Result);
	}
	
	private String getSystemCombine(MachineTra4Result tra4Result) {
		String backbone = tra4Result.getGoogleTra();
		if (backbone == null || backbone.length() == 0) {
			backbone = tra4Result.getBaiduTra();
		}
		LDPath path = new LDPath();
		path.setS1(Arrays.asList(backbone.split(" ")));
		path.setS2(Arrays.asList(this.bestOfFourString.split(" ")));
		path.ldCalcPathOf12();
		List<String> r1 = path.getR1();
		List<String> r2 = path.getR2();
		for (int i = r1.size() - 1; i >= 0; --i) {
			if (r1.get(i).equalsIgnoreCase("_")) {
				r1.remove(i);
			} else {
				break;
			}
		}
		for (int i = 0; i < r1.size(); ++i) {
			String subString = r1.get(i);
			if (subString.equalsIgnoreCase("_")) {
				String replaceString = r2.get(i);
				r1.remove(i);
				r1.add(i, replaceString);
			}
		}
		
		StringBuffer result = new StringBuffer();
		for (String subString : r1) {
			if (!subString.equalsIgnoreCase("_")) {
				result.append(subString + " ");
			}
		}
		return new String(result).trim();
	}
	
	private void getBestOf4(MachineTra4Result tra4Result) throws Exception {
		BLEUMain bleuMain = new BLEUMain();
		TrainSentenceModel topSim = new MainWork().findTopSimSentence(tra4Result.getID());
		StandardTraAnd4MTResult stamtr = new StandardTraAnd4MTResult();
		stamtr.setTra4Result(tra4Result);
		stamtr.setTrainSentenceModel(topSim);
		stamtr.setStandTra(this.origonTraString);
		stamtr.countAllBLEUScores(bleuMain, 1);
		
		double baiduScore = stamtr.getBaiduEachOtherBLEUScore();
		double googleScore = stamtr.getGoogleEachOtherBLEUScore();
		double bingScore = stamtr.getBingEachOtherBLEUScore();
		double youdaoScore = stamtr.getYoudaoEachOtherBLEUScore();
		
		double maxScore = baiduScore;
		this.bestOfFourString = tra4Result.getBaiduTra();
		if (googleScore > maxScore) {
			maxScore = googleScore;
			this.bestOfFourString = tra4Result.getGoogleTra();
		}
		if (bingScore > maxScore) {
			maxScore = bingScore;
			this.bestOfFourString = tra4Result.getBingTra();
		}
		if (youdaoScore > maxScore) {
			maxScore = youdaoScore;
			this.bestOfFourString = tra4Result.getYoudaoTra();
		}
	}
	@Override
	public String toString() {
		return "FinalEXModel [origonSrcString=" + origonSrcString
				+ ", origonTraString=" + origonTraString + ", simTraString="
				+ simTraString + ", bestOfFourString=" + bestOfFourString
				+ ", systemCombineString=" + systemCombineString + "]";
	}

}
