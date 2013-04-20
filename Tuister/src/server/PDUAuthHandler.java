package server;

import javax.xml.bind.JAXBException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import pdus.AckPDU;

public class PDUAuthHandler extends StateHandler {

    public PDUAuthHandler(ServerWorker context) {
        super(context);
    }

    protected void onPublish(Attributes attributes) {
        this.printAttributes(attributes);
        this.context.getDatabase().publish(this.context.userID, attributes.getValue("text"));
    }

    protected void onLogout(Attributes attributes) {
        this.printAttributes(attributes);
        this.context.changeStateToUnauthenticated();
        try {
            this.context.send(new AckPDU("logout").toXML());
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.context.running = false;
        this.context.selector.wakeup();
    }

    protected void onLike(Attributes attributes) {
        this.printAttributes(attributes);
    }

    protected void onUnlike(Attributes attributes) {
        this.printAttributes(attributes);
    }

    protected void onFollow(Attributes attributes) {
        this.printAttributes(attributes);
    }

    protected void onUnFollow(Attributes attributes) {
        this.printAttributes(attributes);
    }

    protected void onFollowingUsersRequest(Attributes attributes) {
        this.printAttributes(attributes);
    }

    protected void onUserListRequest(Attributes attributes) {
        this.printAttributes(attributes);
    }

    protected void onUserContentRequest(Attributes attributes) {
        this.printAttributes(attributes);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("publish")) {
            this.onPublish(attributes);
        } else if (qName.equalsIgnoreCase("logout")) {
            System.out.println("Tag: " + qName);
            this.onLogout(attributes);
        } else if (qName.equalsIgnoreCase("like")) {
            System.out.println("Tag: " + qName);
            this.onLike(attributes);
        } else if (qName.equalsIgnoreCase("unlike")) {
            System.out.println("Tag: " + qName);
            this.onUnlike(attributes);
        } else if (qName.equalsIgnoreCase("follow")) {
            System.out.println("Tag: " + qName);
            this.onFollow(attributes);
        } else if (qName.equalsIgnoreCase("unfollow")) {
            System.out.println("Tag: " + qName);
            this.onUnFollow(attributes);
        } else if (qName.equalsIgnoreCase("followingusersrequest")) {
            System.out.println("Tag: " + qName);
            this.onFollowingUsersRequest(attributes);
        } else if (qName.equalsIgnoreCase("userlistrequest")) {
            System.out.println("Tag: " + qName);
            this.onUserListRequest(attributes);
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
