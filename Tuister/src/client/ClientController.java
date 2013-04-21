package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.Semaphore;

import org.xml.sax.SAXException;

import clientStates.ClientStateHandler;

public class ClientController implements Runnable {
	public Semaphore semaphore = new Semaphore(0);

	private String host = "192.168.1.101";
	private int port = 27015;

	private SocketChannel socket = null;
	private Selector selector = null;

	public GUI gui = null;
	public ClientModel model = null;
	private ClientStateHandler state = null;

	private boolean active = true;

	public ClientController() {
		this.gui = new GUI(this);
		this.model = new ClientModel(this);
		this.state = new ClientStateHandler(this);

		try {
			this.selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
			this.exit();
		}
	}

	public void register(String username, String password) {
		this.state.register(username, password);
	}

	public void login(String username, String password) {
		this.state.login(username, password);
	}
	
	public void ack(String type) {
		this.state.ack(type);
	}

	public void logout() {
		this.state.logout();
	}
	
	public void update() {
		this.state.update();
	}

	public void publish(String text) {
		this.state.publish(text);
	}

	public void follow(String username) {
		this.state.follow(username);
	}

	public void unfollow(String username) {
		this.state.unfollow(username);
	}

	public void like(String string) {
		this.state.like(string);
	}

	public void unlike(String string) {
		this.state.unlike(string);
	}

	public void following() {
		this.state.following();
	}

	public void userContent(String username) {
		this.state.userContent(username);
	}

	public void listUsers() {
		this.state.listUsers();
	}

	public void exit() {
		// TODO Exit
		System.out.println("Exitting.");
		this.gui.deactivate();
		this.active = false;
		this.selector.wakeup();
	}

	public synchronized void connectToServer() {
		if (this.socket == null) {
			try {
				this.socket = SocketChannel.open(new InetSocketAddress(host,
						port));
				this.socket.configureBlocking(false);
				this.semaphore.release();
			} catch (IOException e) {
				this.gui.errorOpeningSocket();
				this.exit();
			}
		}
	}
	
	public synchronized void disconnectFromServer() {
		if (this.socket != null) {
			try {
				this.socket.close();
				this.state.disconnectedFromServer();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.socket = null;
		}
	}

	public void sendToServer(String string) {
		ByteBuffer buffer = ByteBuffer.allocate(string.length()).order(
				ByteOrder.BIG_ENDIAN);
		buffer.clear();
		buffer.put(string.getBytes());
		buffer.flip();

		try {
			while (this.socket != null && buffer.hasRemaining()) {
				this.socket.write(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void wakeUp() { // TODO useless
		this.selector.wakeup();
	}

	@Override
	public void run() {
		// this.state.login("asdf", "fdsa");
		ByteBuffer buffer = null;

		while (this.active) {
			if (this.socket == null){
				try {
					this.semaphore.acquire();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			
			/*synchronized (this.model){
				//if (this.socket == null) continue;
			}*/
			while (this.socket != null) {
				try {
					buffer = ByteBuffer.allocate(
							this.socket.socket().getReceiveBufferSize()).order(
							ByteOrder.BIG_ENDIAN);
					this.socket.register(selector, SelectionKey.OP_READ);
					selector.select();
				} catch (ClosedChannelException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();

				while (it.hasNext()) {
					SelectionKey selKey = it.next();
					it.remove();
					if (selKey.isReadable()) {
						SocketChannel s = (SocketChannel) selKey.channel();
						int r = 0;
						try {
							r = s.read(buffer);
						} catch (IOException e) {
							e.printStackTrace();
						}
							
						try {
							this.model.processData(r, buffer);
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		ClientController controller = new ClientController();
		Thread controllerThread = new Thread(controller);
		Thread guiThread = new Thread(controller.gui);

		controllerThread.start();
		guiThread.start();

		try {
			guiThread.join();
			controllerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("End of potato");
	}

}
