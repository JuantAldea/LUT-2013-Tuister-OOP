package serverXMLHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.xml.bind.JAXBException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import pdus.ListBeginPDU;
import pdus.ListEndPDU;
import pdus.PostPDU;
import server.ServerWorker;

abstract public class StateHandler extends DefaultHandler {
    protected ServerWorker context = null;

    @SuppressWarnings("unused")
    private StateHandler() {

    }

    protected void printAttributes(Attributes attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            System.out.println("\t" + attributes.getQName(i) + ": " + attributes.getValue(i));
        }
    }

    protected void sendList(LinkedList<String> list, String type) {
        try {
            list.addFirst(new ListBeginPDU(type).toXML());
            list.addLast(new ListEndPDU().toXML());
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (String msg : list) {
            this.context.send(msg);
        }

    }

    public StateHandler(ServerWorker context) {
        this.context = context;
        this.context.getDatabase();
    }

    protected void onUserContentRequest(Attributes attributes) {
        ResultSet rs = this.context.getDatabase().userContentRequest(attributes.getValue("username"));
        LinkedList<String> messages = new LinkedList<String>();
        try {
            while (rs != null && rs.next()) {
                PostPDU post = new PostPDU(rs.getString("body"), attributes.getValue("username"), rs.getInt("likes"),
                        rs.getDate("post_date"), rs.getInt("id"));
                messages.add(post.toXML());
            }
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.sendList(messages, "posts");
    }

    abstract public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException;
}
