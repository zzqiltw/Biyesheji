package Model;

import java.util.ArrayList;
import java.util.List;

import BLEUMainWork.BLEUMain;

public class StandardTraAnd4MTResult {
	private String standTra; 
	
	private MachineTra4Result tra4Result;
	
	private List<TrainSentenceModel> topNSim;
	private TrainSentenceModel topSimTrainSentenceModel;
	
	private double baiduBLEUScore;
	private double googleBLEUScore;
	private double youdaoBLEUScore;
	private double bingBLEUScore;
	
	private double baiduRefBLEUScore;
	private double googleRefBLEUScore;
	private double youdaoRefBLEUScore;
	private double bingRefBLEUScore;


	public void countAllBLEUScores(BLEUMain bleuMain, int n) {
		// fake ref
		List<String> fakeRefs = new ArrayList<>();
		if (this.topNSim == null) {
			fakeRefs.add(topSimTrainSentenceModel.getTraText());
		} else {
			for (int i = 0; i < this.topNSim.size(); ++i) {
				fakeRefs.add(this.topNSim.get(i).getTraText());
			}
		}
		bleuMain.setStrs(fakeRefs);
		
		bleuMain.setS(tra4Result.getBaiduTra());
		this.setBaiduBLEUScore(bleuMain.getScore(n));
		
		bleuMain.setS(tra4Result.getYoudaoTra());
		this.setYoudaoBLEUScore(bleuMain.getScore(n));
		
		bleuMain.setS(tra4Result.getGoogleTra());
		this.setGoogleBLEUScore(bleuMain.getScore(n));
		
		bleuMain.setS(tra4Result.getBingTra());
		this.setBingBLEUScore(bleuMain.getScore(n));
		
		// true ref
		List<String> standardRefs = new ArrayList<>();
		standardRefs.add(standTra);
		bleuMain.setStrs(standardRefs);
		
		bleuMain.setS(tra4Result.getBaiduTra());
		this.setBaiduRefBLEUScore(bleuMain.getScore(n));
		
		bleuMain.setS(tra4Result.getGoogleTra());
		this.setGoogleRefBLEUScore(bleuMain.getScore(n));
		
		bleuMain.setS(tra4Result.getBingTra());
		this.setBingRefBLEUScore(bleuMain.getScore(n));
		
		bleuMain.setS(tra4Result.getYoudaoTra());
		this.setYoudaoRefBLEUScore(bleuMain.getScore(n));
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
	
	public String getStandTra() {
		return standTra;
	}

	public void setStandTra(String standTra) {
		this.standTra = standTra;
	}

	
	public TrainSentenceModel getTopSimTrainSentenceModel() {
		return topSimTrainSentenceModel;
	}

	public void setTopSimTrainSentenceModel(
			TrainSentenceModel topSimTrainSentenceModel) {
		this.topSimTrainSentenceModel = topSimTrainSentenceModel;
	}

	public double getBaiduRefBLEUScore() {
		return baiduRefBLEUScore;
	}

	public void setBaiduRefBLEUScore(double baiduRefBLEUScore) {
		this.baiduRefBLEUScore = baiduRefBLEUScore;
	}

	public double getGoogleRefBLEUScore() {
		return googleRefBLEUScore;
	}

	public void setGoogleRefBLEUScore(double googleRefBLEUScore) {
		this.googleRefBLEUScore = googleRefBLEUScore;
	}

	public double getYoudaoRefBLEUScore() {
		return youdaoRefBLEUScore;
	}

	public void setYoudaoRefBLEUScore(double youdaoRefBLEUScore) {
		this.youdaoRefBLEUScore = youdaoRefBLEUScore;
	}

	public double getBingRefBLEUScore() {
		return bingRefBLEUScore;
	}

	public void setBingRefBLEUScore(double bingRefBLEUScore) {
		this.bingRefBLEUScore = bingRefBLEUScore;
	}

	public List<TrainSentenceModel> getTopNSim() {
		return topNSim;
	}

	public void setTopNSim(List<TrainSentenceModel> topNSim) {
		this.topNSim = topNSim;
	}

	@Override
	public String toString() {
		return "[ bdf=" + baiduBLEUScore
				+ ", ggf=" + googleBLEUScore + ", ydf="
				+ youdaoBLEUScore + ", byf=" + bingBLEUScore
				+ ", bdr=" + baiduRefBLEUScore
				+ ", ggr=" + googleRefBLEUScore
				+ ", ydr=" + youdaoRefBLEUScore
				+ ", byr=" + bingRefBLEUScore + "]";
	}

	
	
	
	
}
