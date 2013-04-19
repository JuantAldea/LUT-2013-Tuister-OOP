package client;

import gui.GUI;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import javax.xml.bind.JAXBException;

import common.LoginPDU;
import common.RegisterPDU;

public class ClientController implements Runnable {
	private String host = "127.0.0.1";
	private int port = 1234;
	
	private SocketChannel socket = null;
	private Selector selector = null;
	
	private GUI gui = null;
	private ClientModel client = null;
	
	public ClientController(){
		this.gui = new GUI(this);
		this.gui.run();
		
		this.client = new ClientModel();
		
		try {
			this.socket = SocketChannel.open(new InetSocketAddress(host, port));
			this.socket.configureBlocking(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void processInput(String input) {
		String[] inputSplit = input.split(" ");
		
		if (inputSplit[0] == "register"){
			if (inputSplit.length == 3){
				this.register(inputSplit[1], inputSplit[2]);
			} else {
				this.gui.printHelp();
			}
		} else if (inputSplit[0] == "login"){
			if (inputSplit.length == 3){
				this.login(inputSplit[1], inputSplit[2]);
			} else {
				this.gui.printHelp();
			}
		} else if (inputSplit[0] == "exit"){
			this.gui.deactivate();
			// TODO Exit
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
	
	public void login(String user, String password) {
		try {
			this.sendToServer(new LoginPDU(user, password).toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

	public void exit() {
		this.gui.deactivate();
	}
	
	public void wakeUp() {
		
	}
	
	@Override
	public void run() {
		
		
	}
	
	public static void main(String[] args) {
		ClientController controller = new ClientController();
		controller.run();
	}
}
