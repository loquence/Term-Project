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
	public int addUser(User u) {
		String sql = "INSERT INTO users (fname,lname,email,password,type,status) VALUES" + "('"+u.getFname()+"','"+u.getLname()+"','"+ u.getEmail() + "','"+ u.getPwd() +"','"+u.getType() +"','"+u.getStatus()+"');" ;
		String sql2 = "SELECT * from users where email='" + u.getEmail() + "';";
		int check = DbAccessImpl.create(sql);
		if (check != 0) {
			u = DbAccessImpl.getObject(sql2, ObjectType.users,false);
		}
		return DbAccessImpl.create(sql);
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
				return DbAccessImpl.getObject(sql,ObjectType.users,false);
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
				return DbAccessImpl.getObject(sql, ObjectType.users,false);
				
			}
		}
		
		return null;
	}
	
	public <T> List<T> getUsers() {
		String sql = "Select * FROM users where type!='" +UserType.ADMIN + "';";
		return DbAccessImpl.getList(sql, ObjectType.users);
	}
	
	public int addBook(Book b) {
		String sql = "INSERT INTO book (isbn, title, category, author, edition, publisher, pub_year, min_thresh, buying_price, selling_price, cover) VALUES"
				+ " ('"+b.getISBN()+"','"+b.getTitle()+"','"+b.getGenre()+"','"+b.getAuthor()+"',"+b.getEdition()+",'"+b.getPublisher()+"',"+b.getPublicationYear()+","+b.getMinThreshold()+","+b.getBuyingPrice()+","+b.getSellingPrice()+",'"+b.getCover()+"');" ;
		return DbAccessImpl.create(sql);
	}
	
	public <T> List<T> getObjectList(ObjectType o) {
		String sql = "Select * from " + o + ";";
		return DbAccessImpl.getList(sql,o);
	}
	
	public <T> T getObject(String column, ObjectType o, String value) {
		
			String sql = "Select * from " + o + " where " + column + "='" + value + "';";
		return DbAccessImpl.getObject(sql, o,false);
	}
	
	public Customer getCustomer(String id) {
		String sql = "Select * from user_info where user_id='" + id + "';";
		String test = DbAccessImpl.getString(sql, "address");
		String sql2;
		Boolean flag = false;
		if (test == null) {
			sql2 = "SELECT * from users where id='" + id+ "';";
			
		}
		else {
			sql2 = "SELECT * from users join user_info on users.id=user_info.user_id where users.id='" + id + "';";
			flag = true;
		}
		return DbAccessImpl.getObject(sql2, ObjectType.customer, flag);
	}
	
	public int updateUser(String id, Status s) {
		String sql="UPDATE users SET status='" + s + "' WHERE id='" + id + "';";
		return DbAccessImpl.update(sql);
	}
	
	public int deleteUser(String id) {
		String sql="DELETE from users where id='" + id + "';";
		return DbAccessImpl.update(sql);
	}
	
	
	
}
