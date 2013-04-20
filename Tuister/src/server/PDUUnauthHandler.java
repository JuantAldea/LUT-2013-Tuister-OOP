package server;

import java.sql.ResultSet;

import javax.xml.bind.JAXBException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import pdus.AckPDU;
import pdus.ErrorPDU;

public class PDUUnauthHandler extends StateHandler {

    public PDUUnauthHandler(ServerWorker context) {
        super(context);
    }

    protected Integer onRegister(Attributes attributes) {
        Integer userID = -1;

        if (attributes.getValue("password").length() <= 0) {
            try {
                this.context.send(new ErrorPDU("Invalid password").toXML());
                this.context.running = false;
                this.context.selector.wakeup();
            } catch (JAXBException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return userID;
        }

        userID = this.context.getDatabase().registerUser(attributes.getValue("username"), attributes.getValue("password"));
        if (userID != -1) {
            try {
                this.context.send(new AckPDU("register").toXML());
            } catch (JAXBException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                this.context.send(new ErrorPDU("Username already in use").toXML());
                this.context.running = false;
                this.context.selector.wakeup();
            } catch (JAXBException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return userID;
    }

    protected void onLogin(Attributes attributes) {
        Integer id = this.context.getDatabase().login(attributes.getValue("username"), attributes.getValue("password"));
        if (id != -1) {
            this.context.userID = id;
            this.context.changeStateToAuthenticated();
            try {
                this.context.send(new AckPDU("login").toXML());
            } catch (JAXBException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            this.context.running = false;
            try {
                this.context.send(new ErrorPDU("Username or password invalid").toXML());
            } catch (JAXBException e) {
                e.printStackTrace();
            }
            this.context.selector.wakeup();
        }
    }

    protected void onUserContentRequest(Attributes attributes) {
        ResultSet rs = this.context.getDatabase().userContentRequest(attributes.getValue("username"));
        try {
            this.context.send(new AckPDU("QUE NO TE FALTE DE NADA NIÑO").toXML());
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.printAttributes(attributes);
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
