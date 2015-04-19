package Model;

public class MappingModel implements Comparable<MappingModel> {
	private TrainSentenceModel model;
	private Double score;
	
	@Override
	public int compareTo(MappingModel o) {
		return o.getScore().compareTo(this.score);
	}



	public MappingModel() {
		super();
		// TODO Auto-generated constructor stub
	}



	public MappingModel(TrainSentenceModel model, Double score) {
		super();
		this.model = model;
		this.score = score;
	}



	public TrainSentenceModel getModel() {
		return model;
	}



	public void setModel(TrainSentenceModel model) {
		this.model = model;
	}



	public Double getScore() {
		return score;
	}



	public void setScore(Double score) {
		this.score = score;
	}



	@Override
	public String toString() {
		return "[" + model + ", score=" + score + "]";
	}

//	@Override
//	public String toString() {
//		return "[Id=" + model.getId() + ",s=" + score + "]";
//	}
//	
//	@Override
//	public String toString() {
//		return String.valueOf(score);
//	}
//	
}
