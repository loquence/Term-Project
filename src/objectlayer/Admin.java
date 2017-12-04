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
	
	
	
	
}
