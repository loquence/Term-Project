package logiclayer;

import persistlayer.BookstorePersistImpl;
import objectlayer.*;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleSequence;
import java.util.ArrayList;
import java.util.List;
public class BookstoreLogicImpl {
	private BookstorePersistImpl bookstorePersist;
	
	public BookstoreLogicImpl() {
		bookstorePersist = new BookstorePersistImpl();
	}
	/*
	public int createCustomer(Customer u) {		
		return bookstorePersist.addCustomer(u);
	}
	*/
	public User checkEmail(String email){
		return bookstorePersist.checkEmail(email);
	}
	
	public int checkCode(String code) {
		return bookstorePersist.checkCode(code);
	}
	
	public <T> List<T> getBook(ObjectType o) {
		return bookstorePersist.getBook(o);
	}
	
	
	
	/*public int updateVerification(Status status, String email){
		return bookstorePersist.updateVerification(status, email);
	}*/
	
	
	
}
