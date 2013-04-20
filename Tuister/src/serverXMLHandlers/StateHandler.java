package serverXMLHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;

import org.xml.sax.Attributes;
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

    public StateHandler(ServerWorker context) {
        this.context = context;
        this.context.getDatabase();
    }

    protected void onUserContentRequest(Attributes attributes) {
        ResultSet rs = this.context.getDatabase().userContentRequest(attributes.getValue("username"));
        try {
            this.context.send(new ListBeginPDU().toXML());
            while (rs != null && rs.next()) {
                // public PostPDU(String text, String author, Integer likes, Date date, Integer id) {
                PostPDU post = new PostPDU(rs.getString("body"), attributes.getValue("username"), rs.getInt("likes"),
                        rs.getDate("post_date"), rs.getInt("id"));
                this.context.send(post.toXML());
            }
            this.context.send(new ListEndPDU().toXML());
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.printAttributes(attributes);
    }
}
