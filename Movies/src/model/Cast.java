package model;

public class Cast {
	private int castId;
	private String characterName;
	private int movieId;
	private int actorId;

	public Cast() {
		this.castId = 0;
		this.characterName = null;
		this.movieId = 0;
		this.actorId = 0;
	}

	public Cast(int castId, String characterName, int movieId,
			int actorId) {
		this.castId = castId;
		this.characterName = characterName;
		this.movieId = movieId;
		this.actorId = actorId;
	}

	public int getCastId() {
		return castId;
	}

	public void setCastId(int castId) {
		this.castId = castId;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public int getActorId() {
		return actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}

}
