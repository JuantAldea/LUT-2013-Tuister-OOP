package server;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PDUUnauthHandler extends StateHandler {

    public PDUUnauthHandler(ServerWorker context) {
        super(context);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("register")) {
            System.out.println("Tag: " + qName);
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println("\t" + attributes.getQName(i) + ": " + attributes.getValue(i));
            }
        } else if (qName.equalsIgnoreCase("login")) {
            System.out.println("Tag: " + qName);
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println("\t" + attributes.getQName(i) + ": " + attributes.getValue(i));
            }
            this.context.changeStateToAuthenticated();
        } else if (qName.equalsIgnoreCase("usercontentrequest")) {
            System.out.println("Tag: " + qName);
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println("\t" + attributes.getQName(i) + ": " + attributes.getValue(i));
            }
        }else{
            System.out.println("Not valid in currrent state Tag: " + qName);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
    }

    public void characters(char ch[], int start, int length) throws SAXException {
    }
}
