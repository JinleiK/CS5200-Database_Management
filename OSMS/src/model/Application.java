package model;

import java.io.Serializable;
import java.lang.String;
import java.sql.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Application
 *
 */
@Entity

public class Application implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int appId;
	private Date proposeDate;
	private Date resultDate;
	//rejected -1; pending 0; approved :1;
	private int appStatus;
	@ManyToOne
	@JoinColumn(name="applicantId")
	private User applicant;
	@ManyToOne
	@JoinColumn(name="watcherId")
	private User watcher;
	@ManyToOne
	@JoinColumn(name="itemId")
	private Item item;
	private int quantity;
	private String description;
	//From stock:0 ; From Amazon: 1;
	private int itemSource;
	public int getItemSource() {
	    return itemSource;
	}

	public void setItemSource(int itemSource) {
	    this.itemSource = itemSource;
	}

	private static final long serialVersionUID = 1L;

	public Application() {
		super();
	} 
	
	public Application(int appId, Date proposeDate, Date resultDate,
			int appStatus, User applicant, User watcher, Item item,
			int quantity, String description) {
		super();
		this.appId = appId;
		this.proposeDate = proposeDate;
		this.resultDate = resultDate;
		this.appStatus = appStatus;
		this.applicant = applicant;
		this.watcher = watcher;
		this.item = item;
		this.quantity = quantity;
		this.description = description;
	}

	public int getAppId() {
		return this.appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}   
	public Date getProposeDate() {
		return this.proposeDate;
	}

	public void setProposeDate(Date proposeDate) {
		this.proposeDate = proposeDate;
	}   
	public Date getResultDate() {
		return this.resultDate;
	}

	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}   
	public int getAppStatus() {
		return this.appStatus;
	}

	public void setAppStatus(int appStatus) {
		this.appStatus = appStatus;
	}   
	public User getApplicant() {
		return applicant;
	}

	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}

	public User getWatcher() {
		return watcher;
	}

	public void setWatcher(User watcher) {
		this.watcher = watcher;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}   
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
   
}
