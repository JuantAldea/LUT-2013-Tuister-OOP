package clientStates;

import client.ClientController;

public class State {
	protected ClientController controller = null;
	
	public State(){}
	
	public State(ClientController controller){
		this.controller = controller;
	}
	
	public State ack(String type) {
		return null;
	}
	
	public State register(String username, String password) {
		return null;
	}
	
	public State login(String username, String password) {
		return null;
	}
	
	public State logout() {
		return null;
	}
	
	public State update() {
		return null;
	}
	
	public State publish(String text) {
		return null;
	}
	
	public State follow(String username) {
		return null;
	}

	public State unfollow(String username) {
		return null;
	}

	public State like(String string) {
		return null;
	}

	public State unlike(String string) {
		return null;
	}

	public State following() {
		return null;
	}

	public State userContent(String username) {
		return null;
	}
	
	public State listUsers() {
		return null;
	}

	public State disconnectedFromServer() {
		return null;
	}
}
