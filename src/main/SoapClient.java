package main;

import java.io.ByteArrayOutputStream;
import java.util.function.Consumer;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class SoapClient {
	static private final String myNamespace = "ns1";
	static private final String myNamespaceURI = "http://www.oorsprong.org/websamples.countryinfo";
	static private final String soapEndpointUrl = "http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL";

	public static void main(String args[]) {
		callSoapWebService((msg) -> {
			try {
				createCountryInfoEnvelope(msg, "FR");
			} catch (SOAPException e) {
				e.printStackTrace();
			}
		});
	}

	public ByteArrayOutputStream getCountryInfo(String code) {
		ByteArrayOutputStream baos = callSoapWebService((msg) -> {
			try {
				createCountryInfoEnvelope(msg, code);
			} catch (SOAPException e) {
				e.printStackTrace();
			}
		});
		return baos;
	}

	public ByteArrayOutputStream getCountries() {
		ByteArrayOutputStream baos = callSoapWebService((msg) -> {
			try {
				createListOfCountriesEnvelope(msg);
			} catch (SOAPException e) {
				e.printStackTrace();
			}
		});
		return baos;
	}

	private static void createListOfCountriesEnvelope(SOAPMessage soapMessage) throws SOAPException {
		SOAPBody soapBody = initEnvelope(soapMessage);
		SOAPElement soapBodyElem = soapBody.addChildElement("ListOfCountryNamesByCode", myNamespace);
	}

	private static void createCountryInfoEnvelope(SOAPMessage soapMessage, String countryCode) throws SOAPException {
		SOAPBody soapBody = initEnvelope(soapMessage);
		SOAPElement soapBodyElem = soapBody.addChildElement("FullCountryInfo", myNamespace);
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("sCountryISOCode", myNamespace);
		soapBodyElem1.addTextNode(countryCode);
	}

	private static SOAPBody initEnvelope(SOAPMessage soapMessage) {
		SOAPPart soapPart = soapMessage.getSOAPPart();
		SOAPBody soapBody = null;
		try {
			SOAPEnvelope envelope = soapPart.getEnvelope();
			envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
			soapBody = envelope.getBody();
		} catch (SOAPException e) {
			e.printStackTrace();
		}
		return soapBody;
	}

	private static ByteArrayOutputStream callSoapWebService(Consumer<SOAPMessage> envelopBuilder) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(envelopBuilder), soapEndpointUrl);
			soapResponse.writeTo(baos);
			soapConnection.close();
		} catch (Exception e) {
			System.err.println(
					"\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL!\n");
			e.printStackTrace();
		}
		return baos;
	}

	private static SOAPMessage createSOAPRequest(Consumer<SOAPMessage> envelopBuilder) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		envelopBuilder.accept(soapMessage);
		return soapMessage;
	}
}
