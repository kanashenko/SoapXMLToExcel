package main;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CountryListParser extends DefaultHandler {

	String nodeName = "";
	List<Country> countries = new ArrayList<>();
	Country country;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		nodeName = qName;
		if (qName.equals("m:tCountryCodeAndName")) {
			country = new Country();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("m:tCountryCodeAndName")) {
			countries.add(country);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (nodeName.equals("m:sISOCode")) {
			String s = getElementName(ch, start, length);
			country.setCode(s);
		} else if (nodeName.equals("m:sName")) {
			String s = getElementName(ch, start, length);
			country.setName(s);
		}
		nodeName = "";
	}

	public List<Country> getCountryList() {
		return countries;
	}

	private String getElementName(char[] ch, int start, int length) {
		String s = "";
		for (int i = start; i < start + length; i++) {
			s += ch[i];
		}
		return s;
	}
}
