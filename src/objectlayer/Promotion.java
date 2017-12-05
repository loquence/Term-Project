package objectlayer;
import java.time.LocalDateTime;	//should this be a Calendar though? Date is deprec.

public class Promotion {
	
	private  String code;
	private double percentage;
	private String expiration;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Promotion(String code, double percentage, String expiration) {
		this.code = code;
		this.percentage=percentage;
		this.expiration=expiration;
	}
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public double getPercentage() {
		return percentage;
	}
	
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	public String getExpiration() {
		return expiration;
	}
	
	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

}