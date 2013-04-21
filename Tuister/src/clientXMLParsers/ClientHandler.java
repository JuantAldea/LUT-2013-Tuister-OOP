package clientXMLParsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import common.Post;
import common.User;

public class ClientHandler extends DefaultHandler {

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("ack")) {
            System.out.println("Tag: " + qName);
        } else if (qName.equalsIgnoreCase("error")) {
            System.out.println("Tag: " + qName);
        } else if (qName.equalsIgnoreCase("list")) {
            System.out.println("Tag: " + qName);
            if (attributes.getValue("type").equalsIgnoreCase("users")) {
                System.out.println("HOYGA BIENEN HUSUARIOS");
            } else if (attributes.getValue("type").equalsIgnoreCase("posts")) {
                System.out.println("HOYGA HAMIJO NESECITO SU HALLUDA");
            }
        } else if (qName.equalsIgnoreCase("user")) {
            User user = new User(attributes.getValue("username"));
            System.out.println(user);
        } else if (attributes.getValue("type").equalsIgnoreCase("posts")) {
            SimpleDateFormat parser = new SimpleDateFormat();
            Date date = new Date();
            try {
                date = parser.parse(attributes.getValue("date"));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Post post = new Post(attributes.getValue("text"), attributes.getValue("author"), Integer.parseInt(attributes.getValue("likes")), date,
                    Integer.parseInt((attributes.getValue("id"))));
            System.out.println(post);
        }
    }
}
