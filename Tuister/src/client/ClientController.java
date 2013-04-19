package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;

import gui.GUI;
import client.ClientModel;

public class ClientController {
	private String host = "127.0.0.1";
	private int port = 1234;
	
	private Socket socket = null;
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
		
		//SocketChannel sc = null;
		
		try {
			//sc = SocketChannel.open(new InetSocketAddress(host, port));
            //sc.configureBlocking(false);

			this.socket = new Socket(host, port);
			PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updatePotato(){
		
	}
	
	public static void main(String[] args) {
		ClientController controller = new ClientController();
		controller.connect();
	}
}
