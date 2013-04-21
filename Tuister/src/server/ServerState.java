package server;

public class ServerState implements Cloneable {
    private static final ServerState instance = new ServerState();
    protected boolean acceptingNewConnections = true;
    protected boolean running = false;
    protected int listeningPort = 27015;

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    private ServerState() {

    }

    public static synchronized ServerState getInstance() {
        return instance;
    }

    public synchronized void startServer() {
        this.running = true;
    }

    public synchronized void stopServer() {
        this.running = false;
    }

    public synchronized boolean isRunning() {
        return this.running;
    }

    public synchronized void setAcceptingNewConnections(boolean accepting) {
        acceptingNewConnections = accepting;
    }

    public synchronized boolean getAcceptingNewConnections() {
        return acceptingNewConnections;
    }

    public synchronized int getListeningPort() {
        return listeningPort;
    }

    public synchronized void setListeningPort(int listeningPort) {
        this.listeningPort = listeningPort;
    }
}
