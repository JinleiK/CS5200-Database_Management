package model;

import java.util.Date;

public class Comment {
	private int commentId;
	private String username;
	private int movieId;
	private String comment;
	private Date date;
	
	public Comment() {
		this.commentId = 0;
		this.username = null;
		this.movieId = 0;
		this.comment = null;
		this.date = null;
	}

	public Comment(int commentId, String username, int movieId, String comment, Date date) {
		this.commentId = commentId;
		this.username = username;
		this.movieId = movieId;
		this.comment = comment;
		this.date = date;
	}
	
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
