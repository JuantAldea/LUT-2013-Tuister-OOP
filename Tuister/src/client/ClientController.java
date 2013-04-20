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

import javax.xml.bind.JAXBException;

import common.FollowPDU;
import common.FollowingUsersRequestPDU;
import common.LikePDU;
import common.LoginPDU;
import common.PublishPDU;
import common.RegisterPDU;
import common.UnfollowPDU;
import common.UserContentRequestPDU;
import common.UserListRequestPDU;

public class ClientController implements Runnable{
	private String host = "127.0.0.1";
	private int port = 1234;
	
	private SocketChannel socket = null;
	private Selector selector = null;
	
	private GUI gui = null;
	private ClientModel model = null;
	private boolean active = true;
	
	public ClientController(){
		this.gui = new GUI(this);
		
		this.model = new ClientModel();
		
		try {
			this.socket = SocketChannel.open(new InetSocketAddress(host, port));
			this.socket.configureBlocking(false);
			this.selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
			this.exit();
		}
	}

	public void register(String user, String password) {
		try {
			this.sendToServer(new RegisterPDU(user, password).toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void login(String username, String password) {
		try {
			this.sendToServer(new LoginPDU(username, password).toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void publish(String text) {
		try {
			this.sendToServer(new PublishPDU(text).toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void follow(String username) {
		try {
			this.sendToServer(new FollowPDU(username).toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void unfollow(String username) {
		try {
			this.sendToServer(new UnfollowPDU(username).toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void like(String string) {
		try {
			this.sendToServer(new LikePDU(this.model.postID(string)).toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void unlike(String string) {
		try {
			this.sendToServer(new LikePDU(this.model.postID(string)).toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void following() {
		try {
			this.sendToServer(new FollowingUsersRequestPDU().toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void userContent(String username) {
		try {
			this.sendToServer(new UserContentRequestPDU(username).toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void listUsers() {
		try {
			this.sendToServer(new UserListRequestPDU().toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exit() {
		// TODO Exit
		this.gui.deactivate();
		this.active = false;
		this.selector.wakeup();
	}
	
	private void sendToServer(String string) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(string.length()).order(ByteOrder.BIG_ENDIAN);
		buffer.clear();
		buffer.put(string.getBytes());
		buffer.flip();
		while(buffer.hasRemaining()){
			this.socket.write(buffer);
		}
	}
	
	public void wakeUp() { // TODO useless
		this.selector.wakeup();
	}

	@Override
	public void run() {
		ByteBuffer buffer = null;
		
		while (this.active){
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
