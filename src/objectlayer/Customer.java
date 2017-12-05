package objectlayer;

import java.util.Date;
import java.util.List;

import persistlayer.BookstorePersistImpl;
import persistlayer.DbAccessImpl;

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
	private ShoppingCart cart;
	
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
	
	public Customer(User u) {
		super(u.getFname(),u.getLname(),u.getEmail(),u.getPwd(),u.getStatus(),UserType.CUSTOMER);
		setId(u.getId());
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
	
	public ShoppingCart addToCart(String id) {
		return getPersist().addToCart(id, this.getId());
	}
	
	public ShoppingCart getCart() {
		String id = "" + this.getId();
		
		ShoppingCart c = getPersist().getObject("cart_id", ObjectType.cart, id);
		return c;
	}
	
	public List<Book> getBooksInCart(){
		return getPersist().getBooksInCart(this);
	}

	
}
