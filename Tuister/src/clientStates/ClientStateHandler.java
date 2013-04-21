package clientStates;

import client.ClientController;

public class ClientStateHandler {
	private ClientController controller = null;
	private State currentState = null;
	
	public ClientStateHandler(){}
	
	public ClientStateHandler(ClientController controller){
		this.controller = controller;
		
		this.currentState = new ClientNotLoggedIn(this.controller);
	}
	
	public void ack(String type) {
		this.currentState = this.currentState.ack(type);
	}
	
	public void register(String username, String password) {
		this.currentState = this.currentState.register(username, password);
	}
	
	public void login(String username, String password) {
		this.currentState = this.currentState.login(username, password);
	}
	
	public void logout() {
		this.currentState = this.currentState.logout();
	}
	
	public void update() {
		this.currentState = this.currentState.update();
	}
	
	public void publish(String text) {
		this.currentState = this.currentState.publish(text);
	}
	
	public void follow(String username) {
		this.currentState = this.currentState.follow(username);
	}

	public void unfollow(String username) {
		this.currentState = this.currentState.unfollow(username);
	}

	public void like(String string) {
		this.currentState = this.currentState.like(string);
	}

	public void unlike(String string) {
		this.currentState = this.currentState.unlike(string);
	}

	public void following() {
		this.currentState = this.currentState.following();
	}

	public void userContent(String username) {
		this.currentState = this.currentState.userContent(username);
	}
	
	public void listUsers() {
		this.currentState = this.currentState.listUsers();
	}

	public void disconnectedFromServer() {
		this.currentState = this.currentState.disconnectedFromServer();
	}
}
