package util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.*;

import model.*;

public class AmazonService {
	private static final String AWS_ACCESS_KEY_ID = "AWS_ACCESS_KEY_ID";
	private static final String AWS_SECRET_KEY = "AWS_SECRET_KEY";
	private static final String ENDPOINT = "ecs.amazonaws.com";
	
	private SignedRequestsHelper helper;
	private String requestUrl;
	private String queryString = "Service=AWSECommerceService&Version=2009-03-31&ResponseGroup=Medium&AssociateTag=ID";
	
	public AmazonService(){
		try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
	}
	
	public List<Product> ItemSearch(String keywords) {
		queryString += "&Operation=ItemSearch&SearchIndex=OfficeProducts&Keywords=" + keywords;
		requestUrl = helper.sign(queryString);
		//System.out.println(requestUrl);
		return getProducts(requestUrl);
	}
	
	public List<Product> getProducts(String requestUrl){
		List<Product> products = new ArrayList<Product>();
		 try {
	            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            DocumentBuilder db = dbf.newDocumentBuilder();

	            Document doc = db.parse(requestUrl);
	            NodeList asins= doc.getElementsByTagName("ASIN");
	            NodeList titles = doc.getElementsByTagName("Title");
	            NodeList brands = doc.getElementsByTagName("Brand");
	            NodeList prices = doc.getElementsByTagName("FormattedPrice");
	            NodeList images = doc.getElementsByTagName("MediumImage");
	            NodeList ranks = doc.getElementsByTagName("SalesRank");
	            NodeList urls = doc.getElementsByTagName("DetailPageURL");
	            NodeList totalNews = doc.getElementsByTagName("TotalNew");
	            NodeList reviews = doc.getElementsByTagName("EditorialReviews");
	            for(int i = 0; i < asins.getLength(); i ++){
	            	Product p = new Product();
	            	p.setASIN(getContent(asins.item(i)));
	            	//System.out.println(getContent(asins.item(i)));
	            	p.setTitle(getContent(titles.item(i)));
	            	p.setBrand(getContent(brands.item(i)));
	            	p.setFormattedPrice(getContent(prices.item(i)));
	            	p.setSmallImage(getContent(images.item(i).getChildNodes().item(0)));
	            	p.setSalesRank(getContent(ranks.item(i)));
	            	p.setUrl(getContent(urls.item(i)));
	            	p.setTotalNew(getContent(totalNews.item(i)));
	            	p.setEditorialReview(getContent(reviews.item(i)));
	            	products.add(p);
	            }
	            
	            return products; 
	            
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        } 
		 
	}
	
	public String getContent(Node n) {
		return n != null? n.getTextContent() : "N/A";
	}
	
	
	public Product itemLookup(String asin){
		queryString += "&Operation=ItemLookup&IdType=ASIN&ItemId=" + asin;
		requestUrl = helper.sign(queryString);
		//System.out.println(requestUrl);
		return getProducts(requestUrl).get(0);
	}
	
	
	
//	public static void main(String[] args) {
//		AmazonService as = new AmazonService();
//		List<Product> ps = as.ItemSearch("pencil");
//		//System.out.println(ps.get(0).getASIN());
//	}
}
