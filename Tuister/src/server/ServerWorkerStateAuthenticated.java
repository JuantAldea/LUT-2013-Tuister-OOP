package server;

public class ServerWorkerStateAuthenticated extends ServerWorkerState {

    public ServerWorkerStateAuthenticated(ServerWorker context) {
        super(context);
        this.handler = new PDUAuthHandler(context);
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
