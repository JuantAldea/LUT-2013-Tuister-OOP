package clientStates;

import javax.xml.bind.JAXBException;

import pdus.LoginPDU;
import pdus.RegisterPDU;
import pdus.UserContentRequestPDU;
import client.ClientController;

public class ClientNotLoggedIn extends State {
	
	public ClientNotLoggedIn() {
		super();
	}
	
	public ClientNotLoggedIn(ClientController controller){
		this.controller = controller;
	}
	
	public State ack(String type) {
		if (type.equalsIgnoreCase("register")){
			this.controller.gui.registrationSuccessful();
			return this;
		} else if (type.equalsIgnoreCase("login")){
			this.controller.gui.loginSuccessful();
			return new ClientLoggedIn(this.controller);
		} else {
			return this;
		}
	}
	
	public State register(String username, String password) {
		System.out.println("Sending registration request...");
		
		this.controller.connectToServer();
		
		try {
			this.controller.sendToServer(new RegisterPDU(username, password).toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return this;
	}
	
	public State login(String username, String password) {
		System.out.println("Sending login request...");
		
		this.controller.connectToServer();
		
		try {
			this.controller.sendToServer(new LoginPDU(username, password).toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public State logout() {
		this.controller.gui.errorNotLoggedIn();
		return this;
	}
	
	public State publish(String text) {
		this.controller.gui.errorNotLoggedIn();
		return this;
	}
	
	public State follow(String username) {
		this.controller.gui.errorNotLoggedIn();
		return this;
	}

	public State unfollow(String username) {
		this.controller.gui.errorNotLoggedIn();
		return this;
	}

	public State like(String string) {
		this.controller.gui.errorNotLoggedIn();
		return this;
	}

	public State unlike(String string) {
		this.controller.gui.errorNotLoggedIn();
		return this;
	}

	public State following() {
		this.controller.gui.errorNotLoggedIn();
		return this;
	}

	public State userContent(String username) {
		try {
			this.controller.sendToServer(new UserContentRequestPDU(username).toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public State listUsers() {
		this.controller.gui.errorNotLoggedIn();
		return this;
	}
}
