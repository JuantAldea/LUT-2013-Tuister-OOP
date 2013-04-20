package server;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

abstract public class ServerWorkerState {
    protected ServerWorkerThread context;
    protected SAXParser saxParser = null;
    protected DefaultHandler handler = null;

    @SuppressWarnings("unused")
    private ServerWorkerState() {
    }

    public ServerWorkerState(ServerWorkerThread context) {
        this.context = context;
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

    public void process(InputStream input) throws SAXException, IOException {
        if (this.handler != null) {
            saxParser.parse(input, this.handler);
        }
    }
}
