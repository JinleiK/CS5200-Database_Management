package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Actor;

public class ActorManager {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	DataSource ds;
	public ActorManager() {
		try {
			Context jndi = new InitialContext();
			ds = (DataSource) jndi.lookup("java:comp/env/jdbc/movies");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	String createActor = "INSERT INTO ACTOR (ID, FIRSTNAME, LASTNAME, DATEOFBIRTH) VALUES (?, ?, ?, ?);";
	String readAllActor = "SELECT * FROM ACTOR;";
	String readActor = "SELECT * FROM ACTOR WHERE ID=?;";
	String updateActor = "UPDATE ACTOR SET FIRSTNAME=?, LASTNAME=?, DATEOFBIRTH=? WHERE ID=?;";
	String deleteActor = "DELETE FROM ACTOR WHERE ID=?;";
	
	public void createActor(Actor newActor){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(createActor);
			pstmt.setInt(1, newActor.getId());
			pstmt.setString(2, newActor.getFirstName());
			pstmt.setString(3, newActor.getLastName());
			pstmt.setDate(4, new java.sql.Date(newActor.getDateOfBirth().getTime()));
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
	
	public List<Actor> readAllActors(){
		List<Actor> actors = new ArrayList<Actor>();
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs= stmt.executeQuery(readAllActor);
			while(rs.next()){
				Actor actor = new Actor(rs.getInt("id"),
									rs.getString("firstName"),
									rs.getString("lastName"),
									rs.getDate("dateOfBirth"));
				actors.add(actor);
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
		return actors;	
	}
	
	public Actor readActor(int actorId){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(readActor);
			pstmt.setInt(1, actorId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Actor actor = new Actor(rs.getInt("id"),
						rs.getString("firstName"),
						rs.getString("lastName"),
						rs.getDate("dateOfBirth"));
				return actor;
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
	
	public void updateActor(int actorId, Actor actor){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(updateActor);
			pstmt.setString(1, actor.getFirstName());
			pstmt.setString(2, actor.getLastName());
			pstmt.setDate(3, new java.sql.Date(actor.getDateOfBirth().getTime()));
			pstmt.setInt(4, actorId);
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
	
	public void deleteActor(int actorId){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(deleteActor);
			pstmt.setInt(1, actorId);
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
