package Model;

public class MachineTra4Result {
	private String src;
	private String baiduTra;
	private String youdaoTra;
	private String bingTra;
	private String googleTra;
	
	private Integer ID;
	
	private Boolean baiduGet;
	private Boolean googleGet;
	private Boolean bingGet;
	private Boolean youdaoGet;
	
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getBaiduTra() {
		return baiduTra;
	}
	public void setBaiduTra(String baiduTra) {
		this.baiduTra = baiduTra;
	}
	public String getYoudaoTra() {
		return youdaoTra;
	}
	public void setYoudaoTra(String youdaoTra) {
		this.youdaoTra = youdaoTra;
	}
	public String getBingTra() {
		return bingTra;
	}
	public void setBingTra(String bingTra) {
		this.bingTra = bingTra;
	}
	public String getGoogleTra() {
		return googleTra;
	}
	public void setGoogleTra(String googleTra) {
		this.googleTra = googleTra;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public boolean isBaiduGet() {
		return baiduGet;
	}
	public void setBaiduGet(boolean baiduGet) {
		this.baiduGet = baiduGet;
	}
	public boolean isGoogleGet() {
		return googleGet;
	}
	public void setGoogleGet(boolean googleGet) {
		this.googleGet = googleGet;
	}
	public boolean isBingGet() {
		return bingGet;
	}
	public void setBingGet(boolean bingGet) {
		this.bingGet = bingGet;
	}
	public boolean isYoudaoGet() {
		return youdaoGet;
	}
	public void setYoudaoGet(boolean youdaoGet) {
		this.youdaoGet = youdaoGet;
	}
	@Override
	public String toString() {
		return "MachineTra4Result [src=" + src + ", baiduTra=" + baiduTra
				+ ", youdaoTra=" + youdaoTra + ", bingTra=" + bingTra
				+ ", googleTra=" + googleTra + ", ID=" + ID + "]";
	}

	
}
