package serverStates;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

import server.ServerWorker;
import serverXMLHandlers.PDUHandler;

/*
 * Since the communication is encoded as XML, we are using
 * a SAX XML parser to react to client's requests, this
 * is the base class for the SAX handler used on each
 * server state
 */

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
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    // Parse the input with the handler
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
}
