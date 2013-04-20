package server;

import java.nio.channels.SocketChannel;

public class ServerWorkerThread implements Runnable {
    protected Integer userID;
    protected SocketChannel socket;

    @SuppressWarnings("unused")
    private ServerWorkerThread() {

    }

    public ServerWorkerThread(Integer id, SocketChannel socket) {
        this.userID = id;
        this.socket = socket;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

}
