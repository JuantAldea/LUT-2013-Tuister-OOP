package server;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLParser {
    public static void main(String argv[]) {
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            saxParser.parse("ejemplo.xml", new XMLRootHandler());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
