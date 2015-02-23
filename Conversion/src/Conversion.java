
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Conversion {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse("table_a1.xml");
		
		//document.getDocumentElement().normalize();
		
		System.out.println("Root Element: " + document.getDocumentElement().getNodeName());
		
		List<Country> countries = new ArrayList<Country>();
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		
		for (int i=0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) node;
				
				String countryName = elem.getElementsByTagName("CtryNm")
						.item(0).getChildNodes().item(0).getNodeValue();
				
				String currencyName = elem.getElementsByTagName("CcyNm")
						.item(0).getChildNodes().item(0).getNodeValue();
				
				String currency = elem.getElementsByTagName("Ccy")
							.item(0).getChildNodes().item(0).getNodeValue();
				

				countries.add(new Country(countryName, currencyName, currency));
			}
		}
		
		for (Country cntry : countries)
			System.out.println(cntry.toString());
	}
}
