package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.User;

public class UserManager {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	DataSource ds;
	public UserManager() {
		try {
			Context jndi = new InitialContext();
			ds = (DataSource) jndi.lookup("java:comp/env/jdbc/movies");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	String createUser = "INSERT INTO USER (USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, DATEOFBIRTH) " +
			"VALUES (?, ?, ?, ?, ?, ?);";
	String readAllUser = "SELECT * FROM USER;";
	String readUser = "SELECT * FROM USER WHERE USERNAME=?;";
	String updateUser = "UPDATE USER SET PASSWORD=? FIRSTNAME=?, LASTNAME=?, EMAIL=?, DATEOFBIRTH=? WHERE USERNAME = ?;";
	String deleteUser = "DELETE FROM USER WHERE USERNAME=?;";

	public void createUser(User newUser) {
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(createUser);
			pstmt.setString(1, newUser.getUsername());
			pstmt.setString(2, newUser.getPassword());
			pstmt.setString(3, newUser.getFirstName());
			pstmt.setString(4, newUser.getLastName());
			pstmt.setString(5, newUser.getEmail());
			pstmt.setDate(6, new java.sql.Date(newUser.getDateOfBirth().getTime()));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public List<User> readAllUsers(){
		List<User> users = new ArrayList<User>();
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs= stmt.executeQuery(readAllUser);
			while(rs.next()){
				User user = new User(rs.getString("username"),
									rs.getString("password"),
									rs.getString("firstName"),
									rs.getString("lastName"),
									rs.getString("email"),
									rs.getDate("dateOfBirth"));
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return users;	
	}
	
	public User readUser(String username){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(readUser);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			while(rs.next()){
				User user = new User(rs.getString("username"),
						rs.getString("password"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getString("email"),
						rs.getDate("dateOfBirth"));
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public void updateUser(String username, User user){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(updateUser);
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getFirstName());
			pstmt.setString(3, user.getLastName());
			pstmt.setString(4, user.getEmail());
			pstmt.setDate(5, new java.sql.Date(user.getDateOfBirth().getTime()));
			pstmt.setString(6, username);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void deleteUser(String username){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(deleteUser);
			pstmt.setString(1, username);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
