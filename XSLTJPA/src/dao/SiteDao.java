package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import model.*;

public class SiteDao {
	private EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("XSLTJPA");

	public Site findSite(int siteId) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		Query query = em.createNamedQuery("Site.findById");
		query.setParameter("siteId", siteId);
		Site site = (Site) query.getSingleResult();

		em.getTransaction().commit();
		em.close();
		return site;
	}

	@SuppressWarnings("unchecked")
	public List<Site> findAllSites() {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		List<Site> sites = new ArrayList<Site>();
		Query query = em.createNamedQuery("Site.findAll");
		sites = query.getResultList();

		em.getTransaction().commit();
		em.close();
		return sites;
	}

	public void exportSiteDatabaseToXmlFile(SiteList siteList,
			String xmlFileName) {
		File xmlFile = new File(xmlFileName);
		try {
			JAXBContext jaxb = JAXBContext.newInstance(SiteList.class);
			Marshaller marshaller = jaxb.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(siteList, xmlFile);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void convertXmlFileToOutputFile(String inputXmlFileName,
			String outputXmlFileName, String xsltFileName) {
		File inputXmlFile = new File(inputXmlFileName);
		File outputXmlFile = new File(outputXmlFileName);
		File xsltFile = new File(xsltFileName);

		StreamSource source = new StreamSource(inputXmlFile);
		StreamSource xslt    = new StreamSource(xsltFile);
		StreamResult target = new StreamResult(outputXmlFile);

		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer transformer = factory.newTransformer(xslt);
			transformer.transform(source, target);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		SiteDao siteDao = new SiteDao();
		List<Site> sites = siteDao.findAllSites();
		for(Site s : sites){
			System.out.println(s.getName());
		}
		
		SiteList siteDB = new SiteList(sites);
		siteDao.exportSiteDatabaseToXmlFile(siteDB, "xml/sites.xml");
		
		siteDao.convertXmlFileToOutputFile("xml/sites.xml", "xml/sites.html", "xml/sites2html.xslt");
		siteDao.convertXmlFileToOutputFile("xml/sites.xml", "xml/equipments.html", "xml/sites2equipment.xslt");
	}
}
