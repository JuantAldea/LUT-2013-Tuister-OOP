package serverXMLHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.xml.bind.JAXBException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import pdus.PDU;
import pdus.AckPDU;
import pdus.ListBeginPDU;
import pdus.ListEndPDU;
import pdus.PostPDU;
import pdus.UserPDU;
import server.ServerWorker;

public class PDUAuthHandler extends StateHandler {

    public PDUAuthHandler(ServerWorker context) {
        super(context);
    }

    protected void onPublish(Attributes attributes) {
        if (attributes.getValue("text").length() > 0) {
            this.context.getDatabase().publish(this.context.getUserID(), attributes.getValue("text"));
        } else {
            try {
                this.context.send(new AckPDU("publish").toXML());
            } catch (JAXBException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
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
        this.context.stop();
    }

    protected void onLike(Attributes attributes) {
        this.context.getDatabase().like(this.context.getUserID(), Integer.parseInt(attributes.getValue("postid")));
    }

    protected void onUnlike(Attributes attributes) {
        this.context.getDatabase().unLike(this.context.getUserID(), Integer.parseInt(attributes.getValue("postid")));
    }

    protected void onFollow(Attributes attributes) {
        this.context.getDatabase().follow(this.context.getUserID(), attributes.getValue("username"));
    }

    protected void onUnFollow(Attributes attributes) {
        this.context.getDatabase().unFollow(this.context.getUserID(), attributes.getValue("username"));
    }

    protected void onFollowingUsersRequest(Attributes attributes) {
        ResultSet rs = this.context.getDatabase().followingUsersRequest(this.context.getUserID());
        LinkedList<String> messages = new LinkedList<String>();
        try {
            while (rs != null && rs.next()) {
                messages.add(new UserPDU(rs.getString("username")).toXML());
            }
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.sendList(messages);
    }

    protected void onUserListRequest(Attributes attributes) {
        ResultSet rs = this.context.getDatabase().userListRequest();
        LinkedList<String> messages = new LinkedList<String>();
        try {
            while (rs != null && rs.next()) {
                messages.add(new UserPDU(rs.getString("username")).toXML());
            }
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.sendList(messages);

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
        } else if (qName.equalsIgnoreCase("following_users_request")) {
            System.out.println("Tag: " + qName);
            this.onFollowingUsersRequest(attributes);
        } else if (qName.equalsIgnoreCase("user_list_request")) {
            System.out.println("Tag: " + qName);
            this.onUserListRequest(attributes);
        } else if (qName.equalsIgnoreCase("user_content_request")) {
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
