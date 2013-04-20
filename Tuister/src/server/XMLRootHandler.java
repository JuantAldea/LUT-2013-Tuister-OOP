package server;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLRootHandler extends DefaultHandler {

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("post")) {
            System.out.println("Tag: " + qName);
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println("\t" + attributes.getQName(i) + ": " + attributes.getValue(i));
            }
        } else if (qName.equalsIgnoreCase("login")) {
            System.out.println("Tag: " + qName);
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println("\t" + attributes.getQName(i) + ": " + attributes.getValue(i));
            }
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
    }

    public void characters(char ch[], int start, int length) throws SAXException {
    }
}