package objectlayer;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

	private int customerID;
	private double totalPrice;
	private int number;
	private List<Integer> bookIdList;
	
	public ShoppingCart(int customerId, double totalPrice, int number, List<Integer> list) {
		this.customerID = customerId;
		this.totalPrice = totalPrice;
		this.number=number;
		bookIdList = list;
	}
	
	public List<Integer> getBookIdList() {
		return bookIdList;
	}

	public void setBookIdList(List<Integer> bookIdList) {
		this.bookIdList = bookIdList;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getCustomerID() {
		return customerID;
	}
	
	public double getTotal() {
		return totalPrice;
	}
	
	public void setTotal(double price) {
		this.totalPrice = price;
	}
	
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	
	public void calculatePriceAndNumber(List<Book> lb) {
		double price = 0;
		int num = 0;
		
		for (Book b : lb) {
			for (int i = 0; i <b.getCartQuantity();++i) {
				price = price + b.getSellingPrice();
				++num;
			}
			
		}
		this.number = num;
		this.totalPrice = price;
		
	}
}
