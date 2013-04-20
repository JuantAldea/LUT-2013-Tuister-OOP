package server;

public class ServerWorkerStateAuthenticated extends ServerWorkerState {

    public ServerWorkerStateAuthenticated(ServerWorker context) {
        super(context);
        this.handler = new PDUAuthHandler(context);
    }
}
