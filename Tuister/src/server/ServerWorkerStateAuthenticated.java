package server;

public class ServerWorkerStateAuthenticated extends ServerWorkerState {

    public ServerWorkerStateAuthenticated(ServerWorkerThread context) {
        super(context);
        this.handler = new PDUAuthHandler();
    }
}
