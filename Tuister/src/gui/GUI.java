package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import client.ClientController;

public class GUI implements Runnable {
	private ClientController controller = null;
	private BufferedReader stdIn = null;
	private boolean active = true;
	
	public GUI(ClientController controller) {
		this.controller = controller;
		this.stdIn = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Welcome blablabla");
	}


	@Override
	public void run() {
		String input = "";
		
		while(this.active){
			try {
				//if (this.stdIn.ready()){
					input = this.stdIn.readLine();
					this.controller.processInput(input);
//				} else {
//					Thread.sleep(100);
//				}
			} catch (IOException e) {
				e.printStackTrace();
			} /*catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}
	}
	
	public void printHelp() {
		System.out.println("lolz immahelp"); // TODO
		
		String help = "register <username> <password> -> Adds a new user in the system." +
					  "login <username> <password> -> Access with an existing username and password.";
		
		System.out.println(help);
	}
	
	public void deactivate() {
		this.active = false;
	}
}
