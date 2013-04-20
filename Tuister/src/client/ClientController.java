package client;

import gui.GUI;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import clientStates.ClientState;

public class ClientController implements Runnable{
	private String host = "127.0.0.1";
	private int port = 1234;
	
	private SocketChannel socket = null;
	private Selector selector = null;
	
	public GUI gui = null;
	private ClientModel model = null;
	private ClientState state = null;
	
	private boolean active = true;
	
	public ClientController(){
		this.gui = new GUI(this);
		this.model = new ClientModel(this);
		this.state = new ClientState(this);
		
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
	
	public void logout() {
		this.state.logout();
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
		this.gui.deactivate();
		this.active = false;
		this.selector.wakeup();
	}
	
	public void connectToServer() {
		try {
			this.socket = SocketChannel.open(new InetSocketAddress(host, port));
			this.socket.configureBlocking(false);
		} catch (IOException e) {
			this.gui.errorOpeningSocket();
			this.exit();
		}
	}
	
	public void sendToServer(String string) {
		ByteBuffer buffer = ByteBuffer.allocate(string.length()).order(ByteOrder.BIG_ENDIAN);
		buffer.clear();
		buffer.put(string.getBytes());
		buffer.flip();
		
		try {
			while(buffer.hasRemaining()){
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
		ByteBuffer buffer = null;
		
		while (this.active){
			if (this.socket != null){
				try {
					buffer = ByteBuffer.allocate(this.socket.socket().getReceiveBufferSize()).order(ByteOrder.BIG_ENDIAN);
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
		                
		                if (r <= 0){
		                	this.exit();
		                } else {
		                	this.model.processData(buffer);
		                }
		            }
		        }
			}
		}
	}
	
	public static void main(String[] args) {
		ClientController controller = new ClientController();
		Thread guiThread = new Thread(controller.gui);
		Thread controllerThread = new Thread(controller);
		
		guiThread.start();
		controllerThread.start();
		
		try {
			guiThread.join();
			controllerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("End of potato");
	}

}
