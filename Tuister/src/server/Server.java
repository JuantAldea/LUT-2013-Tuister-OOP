package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server implements Runnable {
    protected Selector selector = null;
    protected ServerSocketChannel server = null;
    protected ServerState serverState = ServerState.getInstance();

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

    protected void sendJoke(SocketChannel client, String joke) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(joke.length() + (Integer.SIZE / 8)).order(ByteOrder.BIG_ENDIAN);
        buf.clear();
        buf.putInt(joke.length() + (Integer.SIZE / 8));
        buf.put(joke.getBytes());
        buf.flip();
        client.write(buf);
    }
}
