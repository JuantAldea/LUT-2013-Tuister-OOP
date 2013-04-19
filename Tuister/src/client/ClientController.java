package client;

import gui.GUI;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import javax.xml.bind.JAXBException;

import common.Message;
import common.User;

public class ClientController {
	private String host = "127.0.0.1";
	private int port = 1234;
	
	private SocketChannel socket = null;
	private Selector selector = null;
	
	private GUI gui = null;
	private ClientModel client = null;
	
	public ClientController(){
		this.gui = new GUI(this);
		this.gui.start();
		
		this.client = new ClientModel();
	}

	private void connect() {
		String user = this.gui.askUser();
		String password = this.gui.askPassword();
		
		try {
			this.socket = SocketChannel.open(new InetSocketAddress(host, port));
			this.socket.configureBlocking(false);
			
			this.sendToServer(Message.generateMessage("jelouz", new User(user, password).toXML()));

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}
	
	private void sendToServer(String string) throws IOException {
		System.out.println("enviando " + string);
		ByteBuffer buffer = ByteBuffer.allocate(string.length()).order(ByteOrder.BIG_ENDIAN);
		buffer.clear();
		buffer.put(string.getBytes());
		buffer.flip();
		while(buffer.hasRemaining()){
			this.socket.write(buffer);
		}
		System.out.println("enviado");
	}
	
	public static void main(String[] args) {
		ClientController controller = new ClientController();
		controller.connect();
	}
}
