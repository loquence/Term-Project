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
	
	
	private UserInfo userInfo;
	private List<Order> orderInfo;
	private int orderNumber;
	
	
	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<Order> getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(List<Order> orderInfo) {
		int num = 0;
		for(Order o: orderInfo) {
			++num;
		}
		this.orderNumber = num;
		this.orderInfo = orderInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Customer(User u) {
		super(u.getFname(),u.getLname(),u.getEmail(),u.getPwd(),u.getStatus(),UserType.CUSTOMER);
		setId(u.getId());
	}
	
	public Customer(String fname, String lname, String email, String pwd, Status status) {
		super(fname, lname, email, pwd, status, UserType.CUSTOMER);
		
		
		// TODO Auto-generated constructor stub
	}

	

	
	public int addCode(String code) {
		return getPersist().addCode(this,code);
	}
	
	public Customer getUser(String id) {
		return getPersist().getCustomer(id);
	}
	
	public ShoppingCart addToCart(String id) {
		return getPersist().addToCart(id, this);
	}
	
	public ShoppingCart getCart() {
		
		
		ShoppingCart c = getPersist().updateCart(this);
		return c;
	}
	
	public List<Book> getBooksInCart(){
		return getPersist().getBooksInCart(this);
	}
	
	public ShoppingCart deleteFromCart(String id) {
		return getPersist().deleteFromCart(id, this);
	}
	
	public UserInfo pullUserInfo() {
		String test = "" + this.getId();
		return getPersist().getObject("user_id", ObjectType.user_info, test);
	}
	
	public int updateUserInfo() {
		return getPersist().updateUserInfo(this.userInfo,this);
	}
	
	public List<Order> placeOrder() {
		return getPersist().placeOrder(this);
	}
	
	public List<Order> getOrders(){
		return getPersist().getOrders(this);
	}
	
	public int updateCustomer() {
		return getPersist().updateCustomer(this);
	}

	
}
