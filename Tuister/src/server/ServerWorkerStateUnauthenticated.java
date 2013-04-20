package server;

public class ServerWorkerStateUnauthenticated extends ServerWorkerState {

    public ServerWorkerStateUnauthenticated(ServerWorkerThread context) {
        super(context);
        this.handler = new PDUUnauthHandler();
    }

}
