package objectlayer;

import java.util.Date;

import persistlayer.BookstorePersistImpl;

/**
 * Customer class implementation
 * @author Ryan
 *
 */
public class Customer extends User {
	
	
	private String address;
	private String number;
	private String cardType;
	private String cardNumber;
	private Date cardExpDate;
	
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

	public Date getCardExpDate() {
		return cardExpDate;
	}

	public void setCardExpDate(Date cardExpDate) {
		this.cardExpDate = cardExpDate;
	}

	public Customer(String fname, String lname, String email, String pwd, Status status) {
		super(fname, lname, email, pwd, status, UserType.CUSTOMER);
		
		
		// TODO Auto-generated constructor stub
	}

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

	
	public int addCode(String code) {
		return getPersist().addCode(this,code);
	}
	
	public Customer getUser(String id) {
		return getPersist().getCustomer(id);
	}

	
}
