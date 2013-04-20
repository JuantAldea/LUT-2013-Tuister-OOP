package server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

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
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            while (serverState.isRunning()) {
                // register everyone in the selector
                server.register(selector, SelectionKey.OP_ACCEPT);
                for (Entry<SocketChannel, Integer> client : clientList.entrySet()) {
                    client.getKey().register(selector, SelectionKey.OP_READ);
                }

                // wait for activity
                selector.select();

                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey selKey = it.next();
                    it.remove();
                    if (selKey.isAcceptable()) {
                        // Activity on the server socket means new client arrived
                        SocketChannel newClientSocket = ((ServerSocketChannel) selKey.channel()).accept();
                        if (serverState.getAcceptingNewConnections()) {
                            newClientSocket.configureBlocking(false);
                            Integer clientID = 123;
                            clientList.put(newClientSocket, clientID);
                        } else {
                            newClientSocket.close();
                        }
                    } else if (selKey.isReadable()) {
                        // activity on some client socket means client left or client is talking
                        SocketChannel activeClient = (SocketChannel) selKey.channel();
                        ByteBuffer buf = ByteBuffer.allocate(activeClient.socket().getReceiveBufferSize()).order(ByteOrder.BIG_ENDIAN);
                        if (activeClient.isConnected()) {
                            // activity + socket connected => is talking
                            int received_bytes = activeClient.read(buf);
                            buf.flip();
                            byte[] byteArray = new byte[received_bytes];
                            buf.get(byteArray, 0, received_bytes);
                            buf.clear();
                            String pdu = new String(byteArray);
                            System.out.println(pdu);
                            InputStream is = new ByteArrayInputStream(byteArray);
                            try {
                                saxParser.parse(is, new XMLRootHandler());
                            } catch (SAXException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        } else {
                            // !isConnected => disconnected!!!1
                            clientList.remove(activeClient);
                        }
                    }
                }
            }
            // shutting down the server, so clean up everything
            for (Entry<SocketChannel, Integer> client : clientList.entrySet()) {
                client.getKey().close();
            }

            clientList.clear();
            server.close();
            selector.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ParserConfigurationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (SAXException e1) {
            // TODO Auto-generated catch block
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
