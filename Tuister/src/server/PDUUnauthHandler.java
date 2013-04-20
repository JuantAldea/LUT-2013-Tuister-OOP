package server;

import javax.xml.bind.JAXBException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import pdus.AckPDU;

public class PDUUnauthHandler extends StateHandler {

    public PDUUnauthHandler(ServerWorker context) {
        super(context);
    }

    protected void onRegister(Attributes attributes) {
        try {
            this.context.send(new AckPDU().toXML());
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void onLogin(Attributes attributes) {

        try {
            this.context.send(new AckPDU().toXML());
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.context.changeStateToAuthenticated();
    }

    protected void onUserContentRequest(Attributes attributes) {
        try {
            this.context.send(new AckPDU().toXML());
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("register")) {
            System.out.println("Tag: " + qName);
            this.onRegister(attributes);
        } else if (qName.equalsIgnoreCase("login")) {
            System.out.println("Tag: " + qName);
            this.onLogin(attributes);
        } else if (qName.equalsIgnoreCase("usercontentrequest")) {
            System.out.println("Tag: " + qName);
            this.onUserContentRequest(attributes);
        } else {
            System.out.println("Not valid in currrent state Tag: " + qName);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
    }

    public void characters(char ch[], int start, int length) throws SAXException {
    }
}
