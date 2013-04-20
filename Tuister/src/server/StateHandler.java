package server;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

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
    }
}
