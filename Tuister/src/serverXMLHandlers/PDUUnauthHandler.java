package serverXMLHandlers;

import javax.xml.bind.JAXBException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import pdus.AckPDU;
import pdus.ErrorPDU;
import server.ServerWorker;

public class PDUUnauthHandler extends PDUHandler {

    public PDUUnauthHandler(ServerWorker context) {
        super(context);
    }

    protected boolean onRegister(Attributes attributes) {
        try {
            if (attributes.getValue("password").length() <= 0) {
                this.context.send(new ErrorPDU("Invalid password").toXML());
                this.context.stop();
                return false;
            }
            // try to register the user
            boolean result = this.context.getDatabase().registerUser(attributes.getValue("username"), attributes.getValue("password"));
            if (!result) {
                this.context.send(new AckPDU("register").toXML());
            } else {
                this.context.send(new ErrorPDU("Username already in use").toXML());
                this.context.stop();
                return false;
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        this.context.stop();
        return true;
    }

    protected void onLogin(Attributes attributes) {
        Integer userID = this.context.getDatabase().login(attributes.getValue("username"), attributes.getValue("password"));
        try {
            // invalid user id => wrong login data
            if (userID == -1) {
                this.context.send(new ErrorPDU("Username or password invalid").toXML());
                this.context.stop();
                return;
            }
            this.context.setUserID(userID);
            this.context.changeStateToAuthenticated();
            this.context.send(new AckPDU("login").toXML());
        } catch (JAXBException e) {
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
        } else if (qName.equalsIgnoreCase("user_content_request")) {
            System.out.println("Tag: " + qName);
            this.onUserContentRequest(attributes);
        } else {
            System.out.println("Not valid in currrent state Tag: " + qName);
        }
    }
}
