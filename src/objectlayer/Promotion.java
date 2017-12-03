package objectlayer;
import java.util.Date;	//should this be a Calendar though? Date is deprec.

public class Promotion {
	
	private  String code;
	private double percentage;
	private Date expiration;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Promotion(String code, double percentage, Date expiration) {
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
	
	public Date getExpiration() {
		return expiration;
	}
	
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

}
