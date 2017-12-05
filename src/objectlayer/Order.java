package objectlayer;

public class Order {
	
	private int orderId;
	private int customerId;
	private OrderStatus status;
	private int price;
	private int lastBookId;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getLastBookId() {
		return lastBookId;
	}
	public void setLastBookId(int lastBookId) {
		this.lastBookId = lastBookId;
	}
	public Order(int orderId, int customerId, OrderStatus status, int price, int lastBookId) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.status = status;
		this.price = price;
		this.lastBookId = lastBookId;
	}
	

}
