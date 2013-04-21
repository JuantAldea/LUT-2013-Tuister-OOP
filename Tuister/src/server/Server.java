package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

import database.DatabaseWrapper;

public class Server implements Runnable {
    protected Selector selector = null;
    protected ServerSocketChannel server = null;
    protected ServerState serverState = ServerState.getInstance();

    public void wakeUp() {
        if (selector != null) {
            // unlocks threads that are waiting on the selector
            selector.wakeup();
        }
    }

    @Override
    public void run() {
        DatabaseWrapper.getInstance();
        try {
            server = ServerSocketChannel.open();
            server.socket().bind(new InetSocketAddress(serverState.getListeningPort()));
            server.configureBlocking(false);
            server.socket().setReuseAddress(true);
            selector = Selector.open();
            while (serverState.isRunning()) {
                server.register(selector, SelectionKey.OP_ACCEPT);
                // wait for activity
                selector.select();
                new Thread(new ServerWorker(server.accept())).start();
            }
            server.close();
            selector.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
