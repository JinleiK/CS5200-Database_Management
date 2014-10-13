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

import model.Movie;

public class MovieManager {
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	static DataSource ds;
	public MovieManager() {
		try {
			Context jndi = new InitialContext();
			ds = (DataSource) jndi.lookup("java:comp/env/jdbc/movies");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	String createMovie = "INSERT INTO MOVIE (ID, TITLE, POSTERIMAGE, RELEASEDATE) VALUES (?, ?, ?, ?);";
    String readAllMovies = "SELECT * FROM MOVIE;";
	String readMovie = "SELECT * FROM MOVIE WHERE ID=?;";
	String updateMovie = "UPDATE MOVIE SET TITLE=?, POSTERIMAGE=?, RELEASEDATE=? WHERE ID=?;";
	String deleteMovie = "DELETE FROM MOVIE WHERE ID=?;";
	
	public void createMovie(Movie newMovie){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(createMovie);
			pstmt.setInt(1, newMovie.getId());
			pstmt.setString(2, newMovie.getTitle());
			pstmt.setString(3, newMovie.getPosterImage());
			pstmt.setDate(4, new java.sql.Date(newMovie.getReleaseDate().getTime()));
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
	
	public List<Movie> readAllMovies(){
		List<Movie> movies = new ArrayList<Movie>();
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(readAllMovies);
			while(rs.next()){
				Movie movie = new Movie(rs.getInt("id"),
									rs.getString("title"),
									rs.getString("posterImage"),
									rs.getDate("releaseDate"));
				movies.add(movie);
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
		return movies;	
	}
	
	public Movie readMovie(int movieId){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(readMovie);
			pstmt.setInt(1, movieId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Movie movie = new Movie(rs.getInt("id"),
						rs.getString("title"), rs.getString("posterImage"),
						rs.getDate("releaseDate"));
				return movie;
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
	
	public void updateMovie(int movieId, Movie movie){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(updateMovie);
			pstmt.setString(1, movie.getTitle());
			pstmt.setString(2, movie.getPosterImage());
			pstmt.setDate(3, new java.sql.Date(movie.getReleaseDate().getTime()));
			pstmt.setInt(4, movieId);
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
	
	public void deleteMovie(int movieId){
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(deleteMovie);
			pstmt.setInt(1, movieId);
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
