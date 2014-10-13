package model;

public class Product {
	private String ASIN;
	private String title;
	private String brand;
	private String formattedPrice;
	private String smallImage;
	private String salesRank;
	private String url;
	private String totalNew;
	private String editorialReview;
	
	public Product() {
		super();
	}

	public Product(String aSIN, String title, String brand,
			String formattedPrice, String smallImage, String salesRank,
			String url, String totalNew, String editorialReview) {
		super();
		ASIN = aSIN;
		this.title = title;
		this.brand = brand;
		this.formattedPrice = formattedPrice;
		this.smallImage = smallImage;
		this.salesRank = salesRank;
		this.url = url;
		this.totalNew = totalNew;
		this.editorialReview = editorialReview;
	}

	public String getASIN() {
		return ASIN;
	}
	public void setASIN(String aSIN) {
		ASIN = aSIN;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getFormattedPrice() {
		return formattedPrice;
	}
	public void setFormattedPrice(String formattedPrice) {
		this.formattedPrice = formattedPrice;
	}
	public String getSmallImage() {
		return smallImage;
	}
	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}
	public String getSalesRank() {
		return salesRank;
	}
	public void setSalesRank(String salesRank) {
		this.salesRank = salesRank;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTotalNew() {
		return totalNew;
	}
	public void setTotalNew(String totalNew) {
		this.totalNew = totalNew;
	}
	public String getEditorialReview() {
		return editorialReview;
	}
	public void setEditorialReview(String editorialReview) {
		this.editorialReview = editorialReview;
	}
}
