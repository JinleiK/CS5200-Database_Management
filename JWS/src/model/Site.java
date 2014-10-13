package model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

import javax.xml.bind.annotation.*;


/**
 * The persistent class for the site database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Site.findAll", query="SELECT s FROM Site s")
})
public class Site implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private double latitude;
	private double longitude;
	private String name;

	//bi-directional many-to-one association to Tower
	@OneToMany(mappedBy="site")
	private List<Tower> towers;

	public Site() {
	}

	public Site(int id, double latitude, double longitude, String name,
			List<Tower> towers) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
		this.towers = towers;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Tower> getTowers() {
		return this.towers;
	}

	public void setTowers(List<Tower> towers) {
		this.towers = towers;
	}

	public Tower addTower(Tower tower) {
		getTowers().add(tower);
		tower.setSite(this);

		return tower;
	}

	public Tower removeTower(Tower tower) {
		getTowers().remove(tower);
		tower.setSite(null);

		return tower;
	}

}