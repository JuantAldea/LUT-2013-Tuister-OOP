package serverXMLHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.xml.bind.JAXBException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import pdus.AckPDU;
import pdus.ErrorPDU;
import pdus.PostPDU;
import pdus.UserPDU;
import server.ServerWorker;

/*
 * SAX handler for the authenticated state
 */
public class PDUAuthHandler extends PDUHandler {

    public PDUAuthHandler(ServerWorker context) {
        super(context);
    }

    /*
     * This function is called by the SAX parser every time it finds a start tag <tag ..> this is the heart the server
     * since server's behavior is driven by user's input
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("publish")) {
            this.onPublish(attributes);
        } else if (qName.equalsIgnoreCase("logout")) {
            this.onLogout(attributes);
        } else if (qName.equalsIgnoreCase("like")) {
            this.onLike(attributes);
        } else if (qName.equalsIgnoreCase("unlike")) {
            this.onUnlike(attributes);
        } else if (qName.equalsIgnoreCase("follow")) {
            this.onFollow(attributes);
        } else if (qName.equalsIgnoreCase("unfollow")) {
            this.onUnFollow(attributes);
        } else if (qName.equalsIgnoreCase("update")) {
            this.onUpdate();
        } else if (qName.equalsIgnoreCase("following_users_request")) {
            this.onFollowingUsersRequest(attributes);
        } else if (qName.equalsIgnoreCase("user_list_request")) {
            this.onUserListRequest(attributes);
        } else if (qName.equalsIgnoreCase("user_content_request")) {
            this.onUserContentRequest(attributes);
        } else {
            System.out.println("Tag not valid in currrent state: " + qName);
        }
    }

    protected void onPublish(Attributes attributes) {
        try {
            if (attributes.getValue("text").length() == 0) {
                this.context.send(new ErrorPDU("publish").toXML());
            } else {
                boolean result = this.context.getDatabase().publish(this.context.getUserID(), attributes.getValue("text"));
                if (result) {
                    this.context.send(new AckPDU("publish").toXML());
                } else {
                    this.context.send(new ErrorPDU("publish").toXML());
                }
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    protected void onLogout(Attributes attributes) {
        this.context.changeStateToUnauthenticated();
        try {
            this.context.send(new AckPDU("logout").toXML());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        this.context.stop();
    }

    protected void onLike(Attributes attributes) {
        boolean result = this.context.getDatabase().like(this.context.getUserID(), Integer.parseInt(attributes.getValue("postid")));
        try {
            if (result) {
                this.context.send(new AckPDU("like").toXML());
            } else {
                this.context.send(new ErrorPDU("like").toXML());
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    protected void onUnlike(Attributes attributes) {
        boolean result = this.context.getDatabase().unLike(this.context.getUserID(), Integer.parseInt(attributes.getValue("postid")));
        try {
            if (result) {
                this.context.send(new AckPDU("unlike").toXML());
            } else {
                this.context.send(new ErrorPDU("unlike").toXML());
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    protected void onFollow(Attributes attributes) {
        boolean result = this.context.getDatabase().follow(this.context.getUserID(), attributes.getValue("username"));
        try {
            if (result) {
                this.context.send(new AckPDU("follow").toXML());
            } else {
                this.context.send(new ErrorPDU("follow").toXML());
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    protected void onUnFollow(Attributes attributes) {
        boolean result = this.context.getDatabase().unFollow(this.context.getUserID(), attributes.getValue("username"));
        try {
            if (result) {
                this.context.send(new AckPDU("unfollow").toXML());
            } else {
                this.context.send(new ErrorPDU("unfollow").toXML());
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    protected void onFollowingUsersRequest(Attributes attributes) {
        ResultSet rs = this.context.getDatabase().followingUsersRequest(this.context.getUserID());
        LinkedList<String> messages = new LinkedList<String>();
        try {
            while (rs != null && rs.next()) {
                messages.add(new UserPDU(rs.getString("username")).toXML());
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.sendList(messages, "users");
    }

    protected void onUserListRequest(Attributes attributes) {
        ResultSet rs = this.context.getDatabase().userListRequest();
        LinkedList<String> messages = new LinkedList<String>();
        try {
            while (rs != null && rs.next()) {
                messages.add(new UserPDU(rs.getString("username")).toXML());
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.sendList(messages, "users");
    }

    protected void onUpdate() {
        ResultSet queryResults = this.context.getDatabase().update(this.context.getUserID());
        LinkedList<String> messages = new LinkedList<String>();
        try {
            while (queryResults != null && queryResults.next()) {
                PostPDU post = new PostPDU(queryResults.getString("body"), queryResults.getString("username"), queryResults.getInt("likes"),
                        queryResults.getString("post_date"), queryResults.getInt("id"));
                messages.add(post.toXML());
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.sendList(messages, "posts");
    }
}
