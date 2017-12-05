package persistlayer;

import objectlayer.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleSequence;

import persistlayer.DbAccessConfiguration;

public class DbAccessImpl {
	
	
	/**
	 * connection 
	 * @return
	 */
	protected static Connection connect() {
		Connection con = null;
		try {
			Class.forName(DbAccessConfiguration.DRIVE_NAME);
			con = DriverManager.getConnection(DbAccessConfiguration.CONNECTION_URL, DbAccessConfiguration.DB_CONNECTION_USERNAME, DbAccessConfiguration.DB_CONNECTION_PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	} // end of connect
	/**
	 * retrieves a resultset and returns it
	 * @param query
	 * @return
	 */
	protected static ResultSet retrieve (String query, Connection con) {
		ResultSet rset = null;
		try {
			Statement stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			return rset;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rset;
	}// end of retrieve
	
	protected static ResultSetMetaData retrieveMD(ResultSet rs) {
		ResultSetMetaData rsmd=null;
		try {
			rsmd = rs.getMetaData();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return rsmd;
	}
	/**
	 * creates a new value in a table
	 * @param sql
	 * @return
	 */
	protected static int create(String sql) {
		Connection c = connect();
		int r = 0;
		try {
			Statement s = c.createStatement();
			r = s.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect(c);
		}
		return r;
	}
	/**
	 * updates values in the table
	 * @param sql
	 * @return
	 */
	protected static int update(String sql){
		Connection c = connect();
		int r = 0;
		try {
			Statement s = c.createStatement();
			r = s.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect(c);
		}
		return r;
	}
	/**
	 * deletes from the table based on the query
	 * @param sql
	 * @return
	 */
	protected static int delete(String sql){
		Connection c = connect();
		int r = 0;
		try {
			Statement s = c.createStatement();
			r = s.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect(c);
		}
		return r;
	}
	/**
	 * disconnects from a connection
	 * @param con
	 */
	protected static void disconnect(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // end of closeConnection
	
	protected static String getString(String sql, String toget) {
		Connection con = connect();
		ResultSet rs = retrieve(sql,con);
		String r = null;
		try {
			if (rs.next())
				r = rs.getString(toget);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		disconnect(con);
		return r;	
	}
	
	protected static int getInt(String sql, String toget){
		Connection c = connect();
		ResultSet rs = retrieve(sql, c);
		int r = 0;
		try {
			if (rs.next())
				r = rs.getInt(toget);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		disconnect(c);
		return r;	
	}
	
	protected static SimpleSequence getSequence(String sql, DefaultObjectWrapperBuilder db){
		Connection c = connect();
		ResultSet rs = retrieve(sql, c);
		ResultSetMetaData rsmd = retrieveMD(rs);
		SimpleSequence sq = new SimpleSequence(db.build());
		try {
			int columnCount = rsmd.getColumnCount();
				while(rs.next()){
					for(int i = 1;i<=columnCount;++i) {
						sq.add(rs.getObject(i));
					}
				}
			
			}catch (SQLException e){
				e.printStackTrace();
			}
		
		disconnect(c);
		return sq;
		
	}
	
	@SuppressWarnings("unchecked")
	protected static <T> List<T> getList(String sql, ObjectType o, Boolean cartFlag){
		Connection c = connect();
		ResultSet rs = retrieve(sql, c);
		
		List<T> ls = new ArrayList<T>();
		try {
		switch(o) {
			case book:
				Book b;
				
				while(rs.next()){
					
					b = new Book(rs.getString("isbn"),rs.getString("title"),rs.getString("category"),rs.getString("author"),rs.getInt("edition"),rs.getString("publisher"), rs.getInt("pub_year"),
							rs.getInt("quantity"), rs.getString("cover"), rs.getDouble("selling_price"), rs.getDouble("buying_price"),rs.getInt("min_thresh"));
					b.setId(rs.getInt("book_id"));
					b.setRating(rs.getInt("rating"));
					if(cartFlag) {
					b.setCartQuantity(rs.getInt("cart_quantity"));
					}
					ls.add((T) b);
				}
				break;
			case users:
				User u;
				while(rs.next()) {
					u = new User(rs.getString("fname"),rs.getString("lname"),rs.getString("email"),rs.getString("password"),Status.valueOf(rs.getString("status")),UserType.valueOf(rs.getString("type")));
					u.setId(rs.getInt("id"));
					ls.add((T) u);
				}
				break;
			case cart:
				ShoppingCart sc;
				while(rs.next()) {
					sc = new ShoppingCart(rs.getInt("cart_id"),rs.getDouble("totalPrice"),rs.getInt("number"), null);
					ls.add((T) sc);
				}
				break;
			case promotion:
				Promotion p;
				while(rs.next()) {
					p = new Promotion(rs.getString("code"),rs.getDouble("percentage"),rs.getString("expiration"));
					p.setId(rs.getInt("id"));
					ls.add((T) p);
				}
				break;
			case bookId:
				while(rs.next()) {
					ls.add((T) (Integer) rs.getInt("book_id"));
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return ls;
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getObject(String sql,  ObjectType o, Boolean CustomerFlag) {
		Connection c = connect();
		ResultSet rs = retrieve(sql, c);
		
		
		try {
			
			
			switch(o) {
			case book:
				Book b = null;
				while(rs.next()) {
					b = new Book(rs.getString("isbn"),rs.getString("title"),rs.getString("category"),rs.getString("author"),rs.getInt("edition"),rs.getString("publisher"), rs.getInt("pub_year"),
							rs.getInt("quantity"), rs.getString("cover"), rs.getDouble("selling_price"), rs.getDouble("buying_price"),rs.getInt("min_thresh"));
					b.setId(rs.getInt("book_id"));
					b.setRating(rs.getInt("rating"));
				}
				return (T) b;
			case users:
				User u = null;
				while(rs.next()) {
					u = new User(rs.getString("fname"),rs.getString("lname"),rs.getString("email"),rs.getString("password"),Status.valueOf(rs.getString("status")),UserType.valueOf(rs.getString("type")));
					u.setId(rs.getInt("id"));
				}
				return (T) u;
			case cart:
				ShoppingCart sc = null;
				while(rs.next()) {
					sc = new ShoppingCart(rs.getInt("cart_id"),rs.getDouble("totalPrice"), rs.getInt("number"), null);
				}
				return (T) sc;
			case promotion:
				Promotion p = null;
				while(rs.next()) {
					p = new Promotion(rs.getString("code"),rs.getDouble("percentage"),rs.getString("expiration"));
					p.setId(rs.getInt("id"));
				}
				return (T) p;
			case customer:
				Customer cu = null;
				while(rs.next()) {
					cu = new Customer(rs.getString("fname"),rs.getString("lname"),rs.getString("email"),rs.getString("password"),Status.valueOf(rs.getString("status")));
					if(CustomerFlag) {
					cu.setNumber(rs.getString("phone_number"));
					cu.setAddress(rs.getString("address"));
					cu.setCardExpDate(rs.getDate("card_exp_date"));
					cu.setCardType(rs.getString("card_type"));
					cu.setCardNumber(rs.getString("card_number"));
					}
					cu.setId(rs.getInt("id"));
				}
				return (T) cu;
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

}

