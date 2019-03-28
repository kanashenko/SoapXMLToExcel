package main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class Main {
	
	public static void main(String[] args) {
		List<Country> countries;
		SoapClient soapClient = new SoapClient();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();

			CountryListParser countryHandler = new CountryListParser();
			ByteArrayOutputStream baos = soapClient.getCountries();
			parser.parse(new ByteArrayInputStream(baos.toByteArray()), countryHandler);
			countries = countryHandler.getCountryList();

			CountryInfoParser infoHandler = new CountryInfoParser(countries);
			for (int i = 0; i < countries.size(); i++) {
				baos = soapClient.getCountryInfo(countries.get(i).getCode());
				parser.parse(new ByteArrayInputStream(baos.toByteArray()), infoHandler);
			}

			ExcelWriter.writeXml(countries);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
}
