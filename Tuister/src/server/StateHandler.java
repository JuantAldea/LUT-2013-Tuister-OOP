package server;

import org.xml.sax.helpers.DefaultHandler;

abstract public class StateHandler extends DefaultHandler {

    @SuppressWarnings("unused")
    private StateHandler() {

    }

    public StateHandler(ServerWorker context) {
        
    }
}
