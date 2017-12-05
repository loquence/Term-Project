package objectlayer;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleSequence;
import java.util.List;
public class Admin extends User {
	
	public Admin(String fname, String lname, String email, String pwd, Status status) {
		super(fname, lname, email, pwd, status, UserType.ADMIN);
	}
	
	public <T> List<T> getUsers() {
		return getPersist().getUsers();
	}
	
	public int addBook(Book b) {
		return getPersist().addBook(b);
	}
	
	public int addPromo(Promotion p) {
		return getPersist().addPromo(p);
	}
	
	public int editPromo(Promotion p) {
		return getPersist().editPromo(p);
	}
	
	public int deletePromo(String id) {
		return getPersist().deleteObject(id, ObjectType.promotion);
	}
	
	public int updateUser(String id, Status s) {
		return getPersist().updateUser(id,s);
	}
	public int deleteUser(String id) {
		return getPersist().deleteObject(id, ObjectType.users);
	}
	
	public int editBook(Book b) {
		return getPersist().editBook(b);
	}
	
}