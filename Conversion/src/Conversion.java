
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

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
		
		HashMap<String, Country> mapper = new HashMap<String, Country>();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse("table_a1.xml");
		
		document.getDocumentElement().normalize();
		
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
				
				addEntry(mapper, countryName, currencyName, currency);
				
			}
		}
		
		for (int i=0; i<countries.size(); i++) {
			System.out.println(countries.get(i).getCountry());
		}

		Country from = mapper.get("MEXICO");
		Country to = mapper.get("UNITED STATES");		
		
		//Test convert peso to dollar
		System.out.println("Peso to Dollar: " + convertCurrency(from.getCurrency(), to.getCurrency(), 1200));
		
		//Test convert dollar to peso
		System.out.println("Dollar to Peso: " + convertCurrency(to.getCurrency(), from.getCurrency(), 1200));
	}

	public static Double convertCurrency(String from, String to, int amount) {
		try {
			URL url = new URL("http://finance.yahoo.com/d/quotes.csv?f=l1&s="+ from + to + "=X");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = reader.readLine();
			if (line.length()>0) {
				return Double.parseDouble(line)*amount;
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	static void addEntry(HashMap<String, Country> map, String countryName, String currencyName, String currency) {
		map.put(countryName, new Country(countryName, currencyName, currency));
	}
}
