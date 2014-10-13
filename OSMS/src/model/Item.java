package model;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Item
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Item.getStock", query="select item from Item item where item.inStock >= 0"),
	@NamedQuery(name="Item.getItemByName", query="select item from Item item where item.itemName like :itemName")
})
public class Item implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int itemId;
	private String itemName;
	private int inStock;
	private String amazonId;
	@OneToMany(mappedBy="item")
	private List<Application> applications;
	private static final long serialVersionUID = 1L;

	public Item() {
		super();
	} 
	
	public Item(int itemId, String itemName, int inStock, String amazonId,
			List<Application> applications) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.inStock = inStock;
		this.amazonId = amazonId;
		this.applications = applications;
	}

	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}   
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}   
	public int getInStock() {
		return this.inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}   
	public String getAmazonId() {
		return this.amazonId;
	}

	public void setAmazonId(String amazonId) {
		this.amazonId = amazonId;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
   
}
