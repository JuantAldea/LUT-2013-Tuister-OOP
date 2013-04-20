package server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import org.xml.sax.SAXException;

public class ServerWorker implements Runnable {
    protected Integer userID;
    protected SocketChannel socket;
    protected Selector selector = null;
    protected final ServerWorkerStateUnauthenticated STATE_UNAUTH;
    protected final ServerWorkerStateAuthenticated STATE_AUTH;
    protected ServerWorkerState state = null;
    protected boolean running = true;

    @SuppressWarnings("unused")
    private ServerWorker() {
        this.STATE_AUTH = null;
        this.STATE_UNAUTH = null;
    }

    public ServerWorker(SocketChannel socket) {
        this.socket = socket;
        this.STATE_UNAUTH = new ServerWorkerStateUnauthenticated(this);
        this.STATE_AUTH = new ServerWorkerStateAuthenticated(this);
        this.state = this.STATE_UNAUTH;
    }

    public void changeStateToAuthenticated() {
        this.state = this.STATE_AUTH;
    }

    public void changeStateToUnauthenticated() {
        this.state = this.STATE_UNAUTH;
    }

    @Override
    public void run() {
        try {
            selector = Selector.open();
            ByteBuffer buf = ByteBuffer.allocate(socket.socket().getReceiveBufferSize()).order(ByteOrder.BIG_ENDIAN);
            this.socket.configureBlocking(false);
            while (running) {
                socket.register(selector, SelectionKey.OP_READ);
                // wait for activity
                System.out.println(this.state.toString());
                selector.select();
                if (socket.isConnected()) {
                    int received_bytes = socket.read(buf);
                    if (received_bytes < 0) {
                        // client disconnected, finish everything
                        running = false;
                        break;
                    }
                    buf.flip();
                    byte[] byteArray = new byte[received_bytes];
                    buf.get(byteArray, 0, received_bytes);
                    buf.clear();
                    String pdu = new String(byteArray);
                    System.out.println(pdu);
                    try {
                        this.state.process(new ByteArrayInputStream(byteArray));
                    } catch (SAXException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    // socket not connected, finish everything
                    running = false;
                    break;
                }
            }
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        try {
            socket.close();
            selector.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Thread muere");
    }

}