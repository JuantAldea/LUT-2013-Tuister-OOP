package server;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PDUUnauthHandler extends StateHandler {

    public PDUUnauthHandler(ServerWorker context) {
        super(context);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println("Tag: " + qName);
        if (qName.equalsIgnoreCase("register")) {
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println("\t" + attributes.getQName(i) + ": " + attributes.getValue(i));
            }
        } else if (qName.equalsIgnoreCase("login")) {
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println("\t" + attributes.getQName(i) + ": " + attributes.getValue(i));
            }
        } else if (qName.equalsIgnoreCase("usercontentrequest")) {
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
