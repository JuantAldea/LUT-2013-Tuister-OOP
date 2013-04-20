package server;

public class ServerWorkerStateUnauthenticated extends ServerWorkerState {

    public ServerWorkerStateUnauthenticated(ServerWorker context) {
        super(context);
        this.handler = new PDUUnauthHandler(context);
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
