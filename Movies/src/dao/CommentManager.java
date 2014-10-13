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

import model.Comment;

public class CommentManager {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	DataSource ds;
	public CommentManager() {
		try {
			Context jndi = new InitialContext();
			ds = (DataSource) jndi.lookup("java:comp/env/jdbc/movies");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	String createComment = "INSERT INTO COMMENT (COMMENTID, USERNAME, MOVIEID, COMMENT, DATE) VALUES (?, ?, ?, ?, ?);";
	String readAllComments = "SELECT * FROM COMMENT;";
	String readCommentsForUsername = "SELECT * FROM COMMENT WHERE USERNAME=?;";
	String readCommentsForMovie = "SELECT * FROM COMMENT WHERE MOVIEID=?;";
	String readCommentsForId = "SELECT * FROM COMMENT WHERE COMMENTID=?;";
	String updateComment = "UPDATE COMMENT SET USERNAME=?, MOVIEID=?, COMMENT=?, DATE=? WHERE COMMENTID=?;";
	String deleteComment = "DELETE FROM COMMENT WHERE COMMENTID=?;";
	
	public void createComment(Comment newComment){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(createComment);
			pstmt.setInt(1, newComment.getCommentId());
			pstmt.setString(2, newComment.getUsername());
			pstmt.setInt(3, newComment.getMovieId());
			pstmt.setString(4, newComment.getComment());
			pstmt.setDate(5, new java.sql.Date(newComment.getDate().getTime()));
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
	
	public List<Comment> readAllComments(){
		List<Comment> comments = new ArrayList<Comment>();
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs= stmt.executeQuery(readAllComments);
			comments = readResultSet(rs);
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
		return comments;	
	}
	
	public List<Comment> readAllCommentsForUsername(String username){
		List<Comment> comments = new ArrayList<Comment>();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(readCommentsForUsername);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			comments = readResultSet(rs);
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
		return comments;	
	}
	
	public List<Comment> readAllCommentsForMovie(int movieId){
		List<Comment> comments = new ArrayList<Comment>();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(readCommentsForMovie);
			pstmt.setInt(1, movieId);
			rs = pstmt.executeQuery();
			comments = readResultSet(rs);
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
		return comments;
	}
	
	public Comment readCommentForId(int commentId){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(readCommentsForId);
			pstmt.setInt(1, commentId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Comment comment = new Comment(rs.getInt("commentId"),
									rs.getString("username"),
									rs.getInt("movieId"),
									rs.getString("comment"),
									rs.getDate("date"));
				return comment;
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
	
	public void updateComment(int commentId, Comment newComment){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(updateComment);
			pstmt.setString(1, newComment.getUsername());
			pstmt.setInt(2, newComment.getMovieId());
			pstmt.setString(3, newComment.getComment());
			pstmt.setDate(4, new java.sql.Date(newComment.getDate().getTime()));
			pstmt.setInt(5, commentId);
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
	
	public void deleteComment(int commentId){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(deleteComment);
			pstmt.setInt(1, commentId);
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
	
	public List<Comment> readResultSet(ResultSet rs){
		List<Comment> comments = new ArrayList<Comment>();
		try {
			while(rs.next()){
				Comment comment = new Comment(rs.getInt("commentId"),
									rs.getString("username"),
									rs.getInt("movieId"),
									rs.getString("comment"),
									rs.getDate("date"));
				comments.add(comment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;	
	}

}
