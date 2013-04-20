package server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import org.xml.sax.SAXException;

public class ServerWorkerThread implements Runnable {
    protected Integer userID;
    protected SocketChannel socket;
    protected Selector selector = null;
    protected final ServerWorkerStateUnauthenticated STATE_UNAUTH;
    protected final ServerWorkerStateAuthenticated STATE_AUTH;
    protected ServerWorkerState state = null;

    @SuppressWarnings("unused")
    private ServerWorkerThread() {
        this.STATE_AUTH = null;
        this.STATE_UNAUTH = null;
    }

    public ServerWorkerThread(SocketChannel socket) {
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
            boolean running = true;
            while (running) {
                socket.register(selector, SelectionKey.OP_READ);
                // wait for activity
                selector.select();
                if (socket.isConnected()) {
                    // activity + socket connected => is talking
                    int received_bytes = socket.read(buf);
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
                    running = false;
                    break;

                }
            }
            socket.close();
            selector.close();
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
    }

}
