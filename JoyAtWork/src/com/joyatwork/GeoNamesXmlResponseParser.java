package com.joyatwork;

import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class GeoNamesXmlResponseParser {

	/*
	 * <geonames> <streetSegment><wayId>17090924</wayId> <line>17.1069283
	 * 48.146448,17.1062347 48.1463814,17.1061468 48.1463894,17.1060806
	 * 48.1464153,17.1059886 48.1464922,17.1058627 48.1465974,17.1056916
	 * 48.1467404,17.1056446 48.1467755</line>
	 * <distance>0.03</distance><name>Veterná
	 * </name><highway>residential</highway> </streetSegment>
	 * <streetSegment><wayId>34325472</wayId><line>17.104631
	 * 48.1462329,17.1051678 48.1464972,17.1056446 48.1467755,17.1058828
	 * 48.1469129,17.106447 48.1472083,17.1067777 48.1473883,17.1068265
	 * 48.1474248,17.1069546 48.1475374,17.1071191 48.1477336,17.1071767
	 * 48.1479637</line><distance>0.05</distance>
	 * <name>Staromestská</name><highway
	 * >secondary</highway><oneway>true</oneway></streetSegment> </geonames>
	 */
	// metoda
	// http://api.geonames.org/findNearbyStreetsOSM?lat=48.14660&lng=17.10635&username=demo
	// Vracia nazov ulice, ale nie cislo, ani mesto, ani psc.
	// Taktiez vrati asi celu ulicu, tj suradnice od do, + zlomy, ?
	public static String ParseFindNearbyStreetsOSMResponse(String xml) {
		// Parsovanie xml cez DOM

		String retVal = "";
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			// Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// char[] data = xml.toCharArray();
			byte[] byteData = xml.getBytes();
			InputStream is = new ByteArrayInputStream(byteData);

			Document dom = db.parse(is);

			// get the root element
			Element docEle = dom.getDocumentElement();

			// get a nodelist of elements
			NodeList nl = docEle.getElementsByTagName("streetSegment"); // Street
																		// segments
			if (nl != null && nl.getLength() > 0) {
				for (int i = 0; i < nl.getLength(); i++) {

					// get the employee element
					Element el = (Element) nl.item(i);

					NodeList nlName = el.getElementsByTagName("name");
					if (nlName != null && nlName.getLength() > 0) {
						retVal = ((Element) nlName.item(0)).getTextContent();

						if (retVal != null && retVal != "") {
							return retVal;
						}
					}
				}
			}

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return retVal;
	}

	public static Element GetRootElement(String xml) {
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			// Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// char[] data = xml.toCharArray();
			byte[] byteData = xml.getBytes();
			InputStream is = new ByteArrayInputStream(byteData);

			Document dom = db.parse(is);

			// get the root element
			Element docEle = dom.getDocumentElement();
			return docEle;
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return null;
	}

	// http://api.geonames.org/findNearbyPlaceName?lat=48.14660&lng=17.10635&username=demo
	// Vrati mesto, stat
	public static String ParsefindNearbyPlaceNameResponse(String xml) {
		// Parsovanie xml cez DOM
		String retVal = "";

		// get the root element
		Element docEle = GetRootElement(xml);

		if (docEle == null)
			return "";

		// get a nodelist of elements
		NodeList nl = docEle.getElementsByTagName("geoname"); // Street
																	// segments
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {

				// get the employee element
				Element el = (Element) nl.item(i);

				NodeList nlName = el.getElementsByTagName("name");
				NodeList nlCountryName = el.getElementsByTagName("countryName");
				if (nlName != null && nlName.getLength() > 0 &&
						nlCountryName != null && nlCountryName.getLength() > 0) {
					retVal = ((Element) nlName.item(0)).getTextContent() + ", " + ((Element) nlCountryName.item(0)).getTextContent();

					if (retVal != null && retVal != "") {
						return retVal;
					}
				}
			}
		}

		return retVal;
	}

}
