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

import model.Cast;

public class CastManager {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	DataSource ds;
	public CastManager() {
		try {
			Context jndi = new InitialContext();
			ds = (DataSource) jndi.lookup("java:comp/env/jdbc/movies");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	String createCast = "INSERT INTO CAST (CASTID, CHARACTERNAME, MOVIEID, ACTORID) VALUES (?, ?, ?, ?);";
	String readAllCast = "SELECT * FROM CAST;";
	String readCastForActor = "SELECT * FROM CAST WHERE ACTORID=?;";
	String readCastForMovie = "SELECT * FROM CAST WHERE MOVIEID=?;";
	String readCastForId = "SELECT * FROM CAST WHERE CASTID=?;";
	String updateCast = "UPDATE CAST SET CHARACTERNAME=?, MOVIEID=?, ACTORID=? WHERE CASTID=?;";
	String deleteCast = "DELETE FROM CAST WHERE CASTID=?;";
	
	public void createCast(Cast newCast){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(createCast);
			pstmt.setInt(1, newCast.getCastId());
			pstmt.setString(2, newCast.getCharacterName());
			pstmt.setInt(3, newCast.getMovieId());
			pstmt.setInt(4, newCast.getActorId());
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
	
	public List<Cast> readAllCast(){
		List<Cast> casts = new ArrayList<Cast>();
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs= stmt.executeQuery(readAllCast);
			casts = readResultSet(rs);
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
		return casts;	
	}
	
	public List<Cast> readAllCastForActor(int actorId){
		List<Cast> casts = new ArrayList<Cast>();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(readCastForActor);
			pstmt.setInt(1, actorId);
			rs= pstmt.executeQuery();
			casts = readResultSet(rs);
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
		return casts;
	}
	
	public List<Cast> readAllCastForMovie(int movieId){
		List<Cast> casts = new ArrayList<Cast>();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(readCastForMovie);
			pstmt.setInt(1, movieId);
			rs= pstmt.executeQuery();
			casts = readResultSet(rs);
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
		return casts;
	}
	
	public Cast readCastForId(int castId){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(readCastForId);
			pstmt.setInt(1, castId);
			rs= pstmt.executeQuery();
			while(rs.next()){
				Cast cast = new Cast(rs.getInt("castId"),
									rs.getString("characterName"),
									rs.getInt("movieId"),
									rs.getInt("actorId"));
				return cast;
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
	
	public void updateCast(int castId, Cast newCast){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(updateCast);
			pstmt.setString(1, newCast.getCharacterName());
			pstmt.setInt(2, newCast.getMovieId());
			pstmt.setInt(3, newCast.getActorId());
			pstmt.setInt(4, castId);
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
	
	public void deleteCast(int castId){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(deleteCast);
			pstmt.setInt(1, castId);
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
	
	public List<Cast> readResultSet(ResultSet rs){
		List<Cast> casts = new ArrayList<Cast>();
		try {
			while(rs.next()){
				Cast cast = new Cast(rs.getInt("castId"),
									rs.getString("characterName"),
									rs.getInt("movieId"),
									rs.getInt("actorId"));
				casts.add(cast);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return casts;	
	}

}
