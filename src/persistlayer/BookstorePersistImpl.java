package persistlayer;
import objectlayer.*;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleSequence;
import java.util.ArrayList;
import java.util.List;
/**
 * Class that calls the DbAccessImpl methods to access the database
 * Called by classes in the Object Layer
 * Currently can be called by BookstorelogicImpl but should be removed in
 * future versions
 * @author Ryan
 *
 */
public class BookstorePersistImpl {
	/**
	 * create customer object in database
	 * @param u
	 * @return
	 */
	public User addUser(User u) {
		String sql = "INSERT INTO users (fname,lname,email,password,type,status) VALUES" + "('"+u.getFname()+"','"+u.getLname()+"','"+ u.getEmail() + "','"+ u.getPwd() +"','"+u.getType() +"','"+u.getStatus()+"');" ;
		String sql2 = "SELECT * from users where email='" + u.getEmail() + "';";
		int check = DbAccessImpl.create(sql);
		if (check != 0) {
			return DbAccessImpl.getObject(sql2, ObjectType.users);
		}
		return null;
	}
	
	/**
	 * login a user 
	 * in the future, this should return the type to allow for 
	 * the server to differentiate between each type of user
	 * @param u
	 * @return
	 */
	public User login(User u) {
		String email = u.getEmail();
		String pwd = u.getPwd();
		String sql = "SELECT * FROM users WHERE email=\"" + email + "\";";
		String p = DbAccessImpl.getString(sql, "password");
		
		String f = DbAccessImpl.getString(sql,"fname");
		String l = DbAccessImpl.getString(sql, "lname");
		
		if(p != null) {
			if (pwd.equals(p)) {
				return DbAccessImpl.getObject(sql,ObjectType.users);
				/*Status s = Status.valueOf(DbAccessImpl.getString(sql, "status"));
				UserType t = UserType.valueOf(DbAccessImpl.getString(sql, "type"));
				u.setFname(f);
				u.setStatus(s);
				u.setType(t);
				u.setLname(l);
				*/
				
			}
		}
		return null;
	}
	
	/**
	 * SQL query called when checking if the email is available
	 * @param email
	 * @return
	 */
	public User checkEmail(String email){
		String sql ="SELECT * FROM users WHERE email=\'" + email + "\';";
		return new Customer("","", DbAccessImpl.getString(sql,"email"),"",Status.VERIFIED);
	}
	public int addCode(Customer u, String code) {
		String sql = "INSERT into user_verification_code (email,code) VALUES" +"('"+ u.getEmail() +"','"+ code+ "');" ;
		return DbAccessImpl.create(sql);
	
	}
	public int checkCode(String code) {
		String sql ="SELECT * from user_verification_code WHERE code=\'" + code +"\';";
		String cd = DbAccessImpl.getString(sql, "code");
		if (cd != null)
			return -1;
		return 1;
	}
	
	public User verifyCode(User u, String code) {
		String checkCode = "SELECT * FROM user_verification_code WHERE email=\'" + u.getEmail() + "\';";
		String update = "UPDATE users SET status='" + u.getStatus() + "' WHERE email='" + u.getEmail() +  "';";
		String delete = "DELETE from user_verification_code WHERE email='" + u.getEmail() + "';";
		String sql = "SELECT * FROM users WHERE email=\"" + u.getEmail() + "\";";
		String c = DbAccessImpl.getString(checkCode,"code");

		
		if(c != null) {
			if(c.equals(code)) {
				
				DbAccessImpl.update(update);
				DbAccessImpl.update(delete);
				User ret = DbAccessImpl.getObject(sql, ObjectType.users);
				String cart = "INSERT into cart (cart_id) VALUES" + "('" + ret.getId() + "');";
				DbAccessImpl.update(cart);
				return ret;
				
			}
		}
		
		return null;
	}
	
	public <T> List<T> getUsers() {
		String sql = "Select * FROM users where type!='" +UserType.ADMIN + "';";
		return DbAccessImpl.getList(sql, ObjectType.users,false,false);
	}
	
	public int addBook(Book b) {
		String sql = "INSERT INTO book (isbn, title, category, author, edition, publisher, pub_year, min_thresh, buying_price, selling_price, cover) VALUES"
				+ " ('"+b.getISBN()+"','"+b.getTitle()+"','"+b.getGenre()+"','"+b.getAuthor()+"',"+b.getEdition()+",'"+b.getPublisher()+"',"+b.getPublicationYear()+","+b.getMinThreshold()+","+b.getBuyingPrice()+","+b.getSellingPrice()+",'"+b.getCover()+"');" ;
		return DbAccessImpl.create(sql);
	}
	
	public <T> List<T> getObjectList(ObjectType o) {
		String sql = "Select * from " + o + ";";
		return DbAccessImpl.getList(sql,o,false,false);
	}
	
	public <T> T getObject(String column, ObjectType o, String value) {
		
			String sql = "Select * from " + o + " where " + column + "='" + value + "';";
		return DbAccessImpl.getObject(sql, o);
	}
	
	public Customer getCustomer(String id) {
		String sql = "Select * from user_info where user_id='" + id + "';";
		String sql2 = "SELECT * from users where id='" + id+ "';";
		sql2 = "SELECT * from users where id='" + id+ "';";
			
		Customer c = DbAccessImpl.getObject(sql2, ObjectType.customer);
		UserInfo ui = DbAccessImpl.getObject(sql,ObjectType.user_info);
		c.setUserInfo(ui);
		return c;
	}
	
	public int updateUser(String id, Status s) {
		String sql="UPDATE users SET status='" + s + "' WHERE id='" + id + "';";
		return DbAccessImpl.update(sql);
	}
	
	public int deleteObject(String id, ObjectType o) {
		String sql="DELETE from " + o + "where id='" + id + "';";
		return DbAccessImpl.update(sql);
	}
	
	public int addPromo(Promotion p) {
		String sql = "INSERT INTO promotion (code,percentage,expiration) VALUES " + "('"+p.getCode()+"',"+p.getPercentage()+",'"+p.getExpiration()+"');" ;
		return DbAccessImpl.create(sql);
	}
	
	public ShoppingCart addToCart(String id, Customer c) {
		String sql = "INSERT into books_in_cart (book_id,cart_id) VALUES ('" + id + "','" + c.getId() + "');";
		String sql2 = "SELECT * from books_in_cart where book_id='" + id + "' AND cart_id='" + c.getId() + "';";
		int duplicate = DbAccessImpl.getInt(sql2, "book_id");
		
		
		
		
		if (duplicate > 0) {
			int quantity = DbAccessImpl.getInt(sql2, "cart_quantity");
			quantity++;
			String update = "UPDATE books_in_cart SET cart_quantity='" + quantity + "' where book_id='" + id + "' AND cart_id='" + c.getId() + "';";
			DbAccessImpl.update(update);
			ShoppingCart cs = updateCart(c);
			return cs;
		}else {
			DbAccessImpl.update(sql);
			ShoppingCart cs = updateCart(c);
			return cs;
		}
		
	}
	
	public List<Book> getBooksInCart(Customer c){
		String sql = "SELECT * FROM book join books_in_cart on book.book_id = books_in_cart.book_id where cart_id='" + c.getId() + "';";
		return DbAccessImpl.getList(sql, ObjectType.book,true,true);
		
	}
	public ShoppingCart deleteFromCart(String id, Customer c) {
		String sql = "DELETE from books_in_cart where cart_id='" + c.getId() + "' AND book_id='" + id + "';";
		DbAccessImpl.update(sql);
		ShoppingCart cart = updateCart(c);
		return cart;
	}
	
	public ShoppingCart updateCart(Customer cu) {
		String sql2 = "SELECT * from cart where cart_id='" + cu.getId() + "';";
		String getBooksList = "SELECT * from books_in_cart where cart_id='" + cu.getId() + "';";
		
		ShoppingCart c = DbAccessImpl.getObject(sql2, ObjectType.cart);
		List<Book> booksIn = getBooksInCart(cu);
		List<Integer> ls = DbAccessImpl.getList(getBooksList, ObjectType.bookId,false,false);
		c.calculatePriceAndNumber(booksIn);
		String updateCart = "UPDATE cart SET totalPrice='" + c.getTotalPrice() + "', number='" + c.getNumber() + "' where cart_id='" + c.getCustomerID() + "';";
		DbAccessImpl.update(updateCart);
		c = DbAccessImpl.getObject(sql2, ObjectType.cart);
		c.setBookIdList(ls);
		return c;
	}
	
	public int editBook(Book b) {
		String sql = "UPDATE book SET isbn='" +b.getISBN() 
			+ "', title='" + b.getTitle() 
			+ "', category='" + b.getGenre() 
			+ "', author='" + b.getAuthor() 
			+ "', edition='" + b.getEdition() 
			+ "', publisher='" + b.getPublisher() 
			+ "', pub_year='" + b.getPublicationYear() 
			+ "', min_thresh='" + b.getMinThreshold() 
			+ "', buying_price='" + b.getBuyingPrice() 
			+ "', selling_price='" + b.getSellingPrice() 
			+ "', quantity='" + b.getQuantity() 
			+ "', rating='" + b.getRating() + "' where book_id='" + b.getId() + "';"; 
		return DbAccessImpl.update(sql);
	}
	
	public int updateUserInfo(UserInfo ui, Customer c) {
		String sql ="SELECT * from user_info where user_id='" + c.getId() + "';";
		UserInfo us = DbAccessImpl.getObject(sql, ObjectType.user_info);
		int promos = 0;
		if(ui.getPromos()) {
			promos = 1;
		}
		if (us != null) {
			String update = "UPDATE user_info SET phone_number='" + ui.getNumber() + "', card_type='" + ui.getCardType() + "',card_number='" + ui.getCardNumber() + "', address='" + ui.getAddress() + "', promos='" + promos + "' where user_id='" + c.getId() + "';";
			return DbAccessImpl.update(update);
		}else {
			String insert = "INSERT into user_info (phone_number,card_type,card_number,card_exp_date,user_id,address,promos) VALUES ('" + ui.getNumber() + "','" + ui.getCardType() + "','" + ui.getCardNumber() + "','" + ui.getCardExpDate() + "','" + c.getId() + "','" + ui.getAddress() + "','" + promos + "');"; 
			return DbAccessImpl.update(insert);
			
		}
		
	}
	
	public List<Order> placeOrder(Customer c) {
		ShoppingCart cart = updateCart(c);
		
		
		
		
		List<Book> bk = getBooksInCart(c);
		int lastB = 0;
		for (Book b : bk) {
			lastB=b.getDbId();
		}
		String updateOrder = "INSERT into bookstore.order (customer_id,order_status,order_price,lastBookId) VALUES ('" + c.getId() + "','" + OrderStatus.PROCESSING + "','" + cart.getTotalPrice() + "','" + lastB + "');";
		int orderId = DbAccessImpl.update(updateOrder);
		String sql2 = "Select * from bookstore.order where lastBookId='" + lastB + "';";
		Order or = DbAccessImpl.getObject(sql2, ObjectType.order);
		String updateCart = "UPDATE books_in_cart SET cart_id='" + 0 + "', order_id='"+ or.getOrderId() + "'where cart_id='" + c.getId() + "';";
		int check = DbAccessImpl.update(updateCart);
		String sql = "SELECT * from bookstore.order where lastBookId='" + lastB + "';";
		List<Order> o = DbAccessImpl.getList(sql, ObjectType.order, false,false);
		
		return o;
	}
	
	public List<Order> getOrders(Customer c){
		String sql="SELECT * from bookstore.order where customer_id='" + c.getId() + "';";
		return DbAccessImpl.getList(sql, ObjectType.order, false, false);
	}
	
	public List<Book> getBookListAdv(String type, String value){
	String sql = "SELECT * from book where " + type +" LIKE '%" + value + "%';";
	return DbAccessImpl.getList(sql, ObjectType.book, false, false);
	}
	
	
	
}