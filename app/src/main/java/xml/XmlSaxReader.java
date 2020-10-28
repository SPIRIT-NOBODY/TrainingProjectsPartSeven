package xml;

import java.io.File;
import java.io.IOException;
import java.net.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import xmltag.Forecast;

public class XmlSaxReader {
	private XmlWriter<Forecast> xmlWriter;
	private Forecast forecast;

	public XmlSaxReader(String url) throws IOException, ParserConfigurationException, SAXException {
		xmlWriter = new XmlWriter();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		XmlHandler xmlHandler = new XmlHandler();
		parser.parse(new File(url), xmlHandler);
	}

	private class XmlHandler extends DefaultHandler {
		private boolean forecastOpen = false;

		@Override
		public void startDocument() throws SAXException {
		}

		@Override
		public void endDocument() throws SAXException {
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			if (qName.equals("TOWN")) {
				xmlWriter.setCaptionName(attributes.getValue("sname"));
			}

			if (!forecastOpen) {
				forecastOpen = qName.equals("FORECAST");
				if (forecastOpen) {
					forecast = new Forecast();
				}
			}
			if (forecastOpen) {
				if (forecast != null) {
					switch (qName) {
					case "FORECAST":
						String info = "%s %s %s, %s, время(ч): %s ";
						forecast.setInfo(String.format(info, attributes.getValue("day"),
								forecast.getMonthValue(attributes.getValue("month")), attributes.getValue("year"),
								forecast.getDayValue(attributes.getValue("weekday")), attributes.getValue("hour")));
						break;
					case "TEMPERATURE":
						forecast.setTemperature(
								"от " + attributes.getValue("min") + " до " + attributes.getValue("max") + " °C");
						break;
					case "PRESSURE":
						forecast.setPressure(
								"от " + attributes.getValue("min") + " до " + attributes.getValue("max") + " мм.рт.ст");
						break;
					case "WIND":
						forecast.setWind(
								"от " + attributes.getValue("min") + " до " + attributes.getValue("max") + " м/с");
						break;
					case "PHENOMENA":
						forecast.setPrecipitation(forecast.getPrecipitationValue(attributes.getValue("precipitation")));
						forecast.setCloudiness(forecast.getCloudinessValue(attributes.getValue("cloudiness")));
						break;
					}
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (qName.equals("FORECAST")) {
				forecastOpen = false;
				xmlWriter.addForecast(forecast);
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
		}

		@Override
		public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

		}
	}

	public XmlWriter getXmlWriter() {
		return xmlWriter;
	}

}
