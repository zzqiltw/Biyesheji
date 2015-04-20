package Model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import BusinessService.EditDistanceWorker;
import BusinessService.LcsWorker;
import BusinessService.ScoreWorker;
import BusinessService.SetWorker;


public class TestModel {
	public static final double setWeight = 0.9;
	public static final double editWeight = 0.05;
	public static final double lcsWeight = 0.05;
	public static final String OutputFinalFileNamePre = "FinalOutput/";
	
	private String testedSentence;
	private String traSentence;
	private String filenamePre;
	
	private List<MappingModel> finalScores;
	private List<String> finalScore2FileStringList;
	
	private List<MappingModel> setScores;
	private List<String> setScore2FileStringList;
	
	private List<MappingModel> editDistanceScores;
	private List<String> editDistanceScore2FileStringList;
	
	private List<MappingModel> lcsScores;
	private List<String> lcsScore2FileStringList;
	
	public void finalWork(double x) {
		this.addFinalWeightToScore();
		this.filtFinal(x);
		this.finalString2file();
	}
	
	public TrainSentenceModel finalWorkChoseOne() {
		this.addFinalWeightToScore();
		return this.choseFinalTopOne();
	}
	
	private void addFinalWeightToScore() {
		int count = this.editDistanceScores.size();
		
		if (finalScores == null) {
			finalScores = new ArrayList<MappingModel>();
		}
		
		for (int i = 0; i < count; ++i) {
			MappingModel model1 = this.editDistanceScores.get(i);
			MappingModel model2 = this.lcsScores.get(i);
			MappingModel model3 = this.setScores.get(i);
			
			double finalScore = model1.getScore() * editWeight + model2.getScore() * lcsWeight + model3.getScore() * setWeight;
			
			MappingModel finalMappingModel = new MappingModel();
			finalMappingModel.setModel(model1.getModel());
			DecimalFormat df = new DecimalFormat("#.000");
			finalMappingModel.setScore(Double.valueOf(df.format(finalScore)));
			finalScores.add(finalMappingModel);
		}
	}
	
	private void filtFinal(double x) {
		Collections.sort(finalScores);
		List<MappingModel> tmpList1 = new ArrayList<>();
		for (MappingModel model : this.finalScores) {
			if (model.getScore() >= x) {
				tmpList1.add(model);
			}
		}
		this.finalScores = tmpList1;
	}
	
	private TrainSentenceModel choseFinalTopOne() {
		Collections.sort(finalScores);
		return finalScores.get(0).getModel();
	}
	
	private void finalString2file() {
		if (this.finalScore2FileStringList == null) {
			this.finalScore2FileStringList = new ArrayList<>();
		}
		for (MappingModel model : this.finalScores) {
			this.finalScore2FileStringList.add(this.model2StringWithTra(model));
		}
	}
	
	public void filt(double x) {
		Collections.sort(this.editDistanceScores);
		Collections.sort(this.setScores);
		Collections.sort(this.lcsScores);
		
		List<MappingModel> tmpList1 = new ArrayList<>();
		for (MappingModel model : this.editDistanceScores) {
			if (model.getScore() >= x) {
				tmpList1.add(model);
			}
		}
		this.editDistanceScores = tmpList1;
		
		List<MappingModel> tmpList2 = new ArrayList<>();
		for (MappingModel model : this.setScores) {
			if (model.getScore() >= x) {
				tmpList2.add(model);
			}
		}
		this.setScores = tmpList2;

		List<MappingModel> tmpList3 = new ArrayList<>();
		for (MappingModel model : this.lcsScores) {
			if (model.getScore() >= x) {
				tmpList3.add(model);
			}
		}
		this.lcsScores = tmpList3;

	}
	
	private String model2String(MappingModel model) {
		return model.getModel().getId() + "-" + model.getModel().getSrcText() +"-" + model.getScore();
	}
	
	private String model2StringWithTra(MappingModel model) {
		return model.getModel().getId() + "-" + model.getModel().getSrcText() +"-" + model.getModel().getTraText() +"-" + model.getScore();
	}
	
	public void generateString2File() {
		if (this.editDistanceScores == null || this.setScores == null || this.lcsScores == null) {
			return;
		}
		
		if (editDistanceScore2FileStringList == null) {
			this.editDistanceScore2FileStringList = new ArrayList<>();
		}
		
		if (setScore2FileStringList == null) {
			this.setScore2FileStringList = new ArrayList<>();
		}
		
		if (lcsScore2FileStringList == null) {
			this.lcsScore2FileStringList = new ArrayList<>();
		}
		
		for (MappingModel model : this.editDistanceScores) {
			this.editDistanceScore2FileStringList.add(this.model2String(model));
		}
		for (MappingModel model : this.lcsScores) {
			this.lcsScore2FileStringList.add(this.model2String(model));
		}
		for (MappingModel model : this.setScores) {
			this.setScore2FileStringList.add(this.model2String(model));
		}
	}
	
	public void countScores(ScoreWorker worker, TrainSentenceModel traModel) {
		if (editDistanceScores == null) {
			this.editDistanceScores = new ArrayList<>();
		}
		
		if (setScores == null) {
			this.setScores = new ArrayList<>();
		}
		
		if (lcsScores == null) {
			this.lcsScores = new ArrayList<>();
		}
		
		MappingModel model = new MappingModel();
		model.setModel(traModel);
		double score = worker.getScore(testedSentence, traModel.getSrcText());
		model.setScore(score);
		
		if (worker instanceof EditDistanceWorker) {
			this.editDistanceScores.add(model);
		} else if (worker instanceof SetWorker) {
			this.setScores.add(model);
		} else if (worker instanceof LcsWorker) {
			this.lcsScores.add(model);
		}
	}
	
	public String getTestedSentence() {
		return testedSentence;
	}
	public void setTestedSentence(String testedSentence) {
		this.testedSentence = testedSentence;
		this.filenamePre = new String("Output/");
	}
	public List<MappingModel> getSetScores() {
		return setScores;
	}
	public void setSetScores(List<MappingModel> setScores) {
		this.setScores = setScores;
	}
	public List<MappingModel> getEditDistanceScores() {
		return editDistanceScores;
	}
	public void setEditDistanceScores(List<MappingModel> editDistanceScores) {
		this.editDistanceScores = editDistanceScores;
	}
	public List<MappingModel> getLcsScores() {
		return lcsScores;
	}
	public void setLcsScores(List<MappingModel> lcsScores) {
		this.lcsScores = lcsScores;
	}
	
	public String getFilenamePre() {
		return filenamePre;
	}

	public void setFilenamePre(String filenamePre) {
		this.filenamePre = filenamePre;
	}

	public List<String> getSetScore2FileStringList() {
		return setScore2FileStringList;
	}

	public void setSetScore2FileStringList(List<String> setScore2FileStringList) {
		this.setScore2FileStringList = setScore2FileStringList;
	}

	public List<String> getEditDistanceScore2FileStringList() {
		return editDistanceScore2FileStringList;
	}

	public void setEditDistanceScore2FileStringList(
			List<String> editDistanceScore2FileStringList) {
		this.editDistanceScore2FileStringList = editDistanceScore2FileStringList;
	}

	public List<String> getLcsScore2FileStringList() {
		return lcsScore2FileStringList;
	}

	public void setLcsScore2FileStringList(List<String> lcsScore2FileStringList) {
		this.lcsScore2FileStringList = lcsScore2FileStringList;
	}

	public List<MappingModel> getFinalScores() {
		return finalScores;
	}

	public void setFinalScores(List<MappingModel> finalScores) {
		this.finalScores = finalScores;
	}

	public List<String> getFinalScore2FileStringList() {
		return finalScore2FileStringList;
	}

	public void setFinalScore2FileStringList(List<String> finalScore2FileStringList) {
		this.finalScore2FileStringList = finalScore2FileStringList;
	}

	public static String getOutputfinalfilenamepre() {
		return OutputFinalFileNamePre;
	}
	
	
	public String getTraSentence() {
		return traSentence;
	}

	public void setTraSentence(String traSentence) {
		this.traSentence = traSentence;
	}

	@Override
	public String toString() {
		return "TestModel [testedSentence=" + testedSentence + "\n  setScores="
				+ setScores + "\n  editScores=" + editDistanceScores
				+ "\n  lcsScores=" + lcsScores + "]";
	}
}
