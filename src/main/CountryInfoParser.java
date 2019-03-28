package main;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CountryInfoParser extends DefaultHandler {
	static int counter;
	String nodeName = "";
	List<Country> countries = new ArrayList<>();

	public CountryInfoParser(List<Country> countries) {
		this.countries = countries;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		nodeName = qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("m:FullCountryInfoResult")) {
			counter++;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (nodeName.equals("m:sCapitalCity")) {
			String s = getElementName(ch, start, length);
			countries.get(counter).setCapital(s);
		} else if (nodeName.equals("m:sCurrencyISOCode")) {
			String s = getElementName(ch, start, length);
			countries.get(counter).setCurrencyCode(s);
		}
		nodeName = "";
	}

	private String getElementName(char[] ch, int start, int length) {
		String s = "";
		for (int i = start; i < start + length; i++) {
			s += ch[i];
		}
		return s;
	}
}
