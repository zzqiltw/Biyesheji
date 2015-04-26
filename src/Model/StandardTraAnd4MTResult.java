package Model;

import java.util.ArrayList;
import java.util.List;

import BLEUMainWork.BLEUMain;

public class StandardTraAnd4MTResult {
	private String standTra; 
	
	private MachineTra4Result tra4Result;
	private TrainSentenceModel topSimTrainSentenceModel;
	
	private double baiduBLEUScore;
	private double googleBLEUScore;
	private double youdaoBLEUScore;
	private double bingBLEUScore;
	
	private double refBLEUScore;

	public void countAllBLEUScores(BLEUMain bleuMain, int n) {
//		List<String> fakeRefs = new ArrayList<>();
//		fakeRefs.add(topSimTrainSentenceModel.getTraText());
//		bleuMain.setStrs(fakeRefs);
		
		List<String> standardRefs = new ArrayList<>();
		standardRefs.add(standTra);
		bleuMain.setStrs(standardRefs);
		
		bleuMain.setS(tra4Result.getBaiduTra());
		this.setBaiduBLEUScore(bleuMain.getScore(n));
		
		bleuMain.setS(tra4Result.getYoudaoTra());
		this.setYoudaoBLEUScore(bleuMain.getScore(n));
		
		bleuMain.setS(tra4Result.getGoogleTra());
		this.setGoogleBLEUScore(bleuMain.getScore(n));
		
		bleuMain.setS(tra4Result.getBingTra());
		this.setBingBLEUScore(bleuMain.getScore(n));
		
	}
	
	public MachineTra4Result getTra4Result() {
		return tra4Result;
	}

	public void setTra4Result(MachineTra4Result tra4Result) {
		this.tra4Result = tra4Result;
	}

	public TrainSentenceModel getTrainSentenceModel() {
		return topSimTrainSentenceModel;
	}

	public void setTrainSentenceModel(TrainSentenceModel trainSentenceModel) {
		this.topSimTrainSentenceModel = trainSentenceModel;
	}

	public double getBaiduBLEUScore() {
		return baiduBLEUScore;
	}

	public void setBaiduBLEUScore(double baiduBLEUScore) {
		this.baiduBLEUScore = baiduBLEUScore;
	}

	public double getGoogleBLEUScore() {
		return googleBLEUScore;
	}

	public void setGoogleBLEUScore(double googleBLEUScore) {
		this.googleBLEUScore = googleBLEUScore;
	}

	public double getYoudaoBLEUScore() {
		return youdaoBLEUScore;
	}

	public void setYoudaoBLEUScore(double youdaoBLEUScore) {
		this.youdaoBLEUScore = youdaoBLEUScore;
	}

	public double getBingBLEUScore() {
		return bingBLEUScore;
	}

	public void setBingBLEUScore(double bingBLEUScore) {
		this.bingBLEUScore = bingBLEUScore;
	}

	public double getRefBLEUScore() {
		return refBLEUScore;
	}

	public void setRefBLEUScore(double refBLEUScore) {
		this.refBLEUScore = refBLEUScore;
	}

	public String getStandTra() {
		return standTra;
	}

	public void setStandTra(String standTra) {
		this.standTra = standTra;
	}

	@Override
	public String toString() {
		return "StandardTraAnd4MTResult [standTra=" + standTra
				+ ", tra4Result=" + tra4Result + ", topSimTrainSentenceModel="
				+ topSimTrainSentenceModel + ", baiduBLEUScore="
				+ baiduBLEUScore + ", googleBLEUScore=" + googleBLEUScore
				+ ", youdaoBLEUScore=" + youdaoBLEUScore + ", bingBLEUScore="
				+ bingBLEUScore + "]\n";
		
	}
}
