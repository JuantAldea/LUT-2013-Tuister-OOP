package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

public class Server implements Runnable {
    protected Selector selector = null;
    protected ServerSocketChannel server = null;
    protected ServerState serverState = ServerState.getInstance();
    protected HashMap<SocketChannel, Integer> clientList = new HashMap<SocketChannel, Integer>();

    public Server() {

    }

    public void wakeUp() {
        if (selector != null) {
            // unlocks threads that are waiting on the selector
            selector.wakeup();
        }
    }

    @Override
    public void run() {
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
                new Thread(new ServerWorkerThread(server.accept())).start();
            }
            clientList.clear();
            server.close();
            selector.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    protected void sendJoke(SocketChannel client, String joke) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(joke.length() + (Integer.SIZE / 8)).order(ByteOrder.BIG_ENDIAN);
        buf.clear();
        buf.putInt(joke.length() + (Integer.SIZE / 8));
        buf.put(joke.getBytes());
        buf.flip();
        client.write(buf);
    }
}
