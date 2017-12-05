package objectlayer;

import java.util.Date;

public class UserInfo {

	private String address;
	private String number;
	private String cardType;
	private String cardNumber;
	private String cardExpDate;
	private Boolean promos;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardExpDate() {
		return cardExpDate;
	}
	public void setCardExpDate(String cardExpDate) {
		this.cardExpDate = cardExpDate;
	}
	
	public UserInfo(String address, String number, String cardType, String cardNumber, String cardExpDate, Boolean promos) {
		super();
		this.address = address;
		this.number = number;
		this.cardType = cardType;
		this.cardNumber = cardNumber;
		this.cardExpDate = cardExpDate;
		this.promos = promos;
	}
	public Boolean getPromos() {
		return promos;
	}
	public void setPromos(Boolean promos) {
		this.promos = promos;
	}
	
	

}
