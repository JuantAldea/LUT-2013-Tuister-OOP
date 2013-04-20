package clientStates;

import client.ClientController;

public class ClientLoggedIn extends State {

	public ClientLoggedIn() {
		super();
	}
	
	public ClientLoggedIn(ClientController controller){
		this.controller = controller;
	}
}
