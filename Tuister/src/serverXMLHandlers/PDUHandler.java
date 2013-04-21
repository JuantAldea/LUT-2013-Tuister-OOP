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

/*
 * Superclass of SAX parser handlers used in the application. It encapsulates the functionality that is common to both states
 */

abstract public class PDUHandler extends DefaultHandler {
    protected ServerWorker context = null;

    @SuppressWarnings("unused")
    private PDUHandler() {

    }

    abstract public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException;

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
            e.printStackTrace();
        }
        for (String msg : list) {
            this.context.send(msg);
        }
    }

    public PDUHandler(ServerWorker context) {
        this.context = context;
        this.context.getDatabase();
    }

    protected void onUserContentRequest(Attributes attributes) {
        ResultSet queryResults = this.context.getDatabase().userContentRequest(attributes.getValue("username"));
        LinkedList<String> messages = new LinkedList<String>();
        try {
            while (queryResults != null && queryResults.next()) {
                PostPDU post = new PostPDU(queryResults.getString("body"), attributes.getValue("username"), queryResults.getInt("likes"),
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
