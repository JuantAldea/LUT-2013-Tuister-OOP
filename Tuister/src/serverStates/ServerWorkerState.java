package serverStates;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

import server.ServerWorker;
import serverXMLHandlers.PDUHandler;

abstract public class ServerWorkerState {
    protected ServerWorker context;
    protected SAXParser saxParser = null;
    protected PDUHandler handler = null;

    @SuppressWarnings("unused")
    private ServerWorkerState() {
    }

    public ServerWorkerState(ServerWorker context) {
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

    public void process(InputStream input) {
        if (this.handler != null) {
            try {
                saxParser.parse(input, this.handler);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    abstract public String toString();
}
