package clientXMLParsers;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class ClientParser {
    protected SAXParser saxParser = null;

    public ClientParser() {
        try {
            saxParser = SAXParserFactory.newInstance().newSAXParser();
        } catch (ParserConfigurationException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        } catch (SAXException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
    }
}
