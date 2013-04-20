package server;

import org.xml.sax.helpers.DefaultHandler;

abstract public class StateHandler extends DefaultHandler {
    protected ServerWorker context = null;

    @SuppressWarnings("unused")
    private StateHandler() {

    }

    public StateHandler(ServerWorker context) {
        this.context = context;
    }
}
