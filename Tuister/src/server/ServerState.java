package server;

import server.State;

public class ServerState extends State {
    private static final ServerState instance = new ServerState();
    protected boolean acceptingNewConnections = true;
    protected boolean running = false;
    protected int listeningPort = 27015;

    private ServerState() {
        super();
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
