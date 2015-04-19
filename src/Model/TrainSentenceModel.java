package Model;

public class TrainSentenceModel {
	private int id;
	private String srcText;
	private String traText;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSrcText() {
		return srcText;
	}
	public void setSrcText(String srcText) {
		this.srcText = srcText;
	}
	public String getTraText() {
		return traText;
	}
	public void setTraText(String traText) {
		this.traText = traText;
	}

	
	@Override
	public String toString() {
		return "[id=" + id + ", srcText=" + srcText
				+ ", traText=" + traText + "]";
	}
	
//	@Override
//	public String toString() {
//		return "[id=" + id + "]";
//	}
//	
}
