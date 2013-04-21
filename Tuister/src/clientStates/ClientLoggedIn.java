package clientStates;

import javax.xml.bind.JAXBException;

import pdus.FollowPDU;
import pdus.FollowingUsersRequestPDU;
import pdus.LikePDU;
import pdus.LogoutPDU;
import pdus.PublishPDU;
import pdus.UnfollowPDU;
import pdus.UnlikePDU;
import pdus.UpdatePDU;
import pdus.UserContentRequestPDU;
import pdus.UserListRequestPDU;
import client.ClientController;

public class ClientLoggedIn extends State {

	public ClientLoggedIn() {
		super();
	}
	
	public ClientLoggedIn(ClientController controller){
		this.controller = controller;
	}
	
	public State ack(String type) {
		if (type.equalsIgnoreCase("logout")){
			this.controller.gui.logoutSuccessful();
			this.controller.disconnectFromServer();
			return new ClientNotLoggedIn(this.controller);
		} else {
			return this;
		}
	}
	
	public State register(String username, String password) {
		this.controller.gui.errorAlreadyLoggedIn();
		return this;
	}
	
	public State login(String username, String password) {
		this.controller.gui.errorAlreadyLoggedIn();
		return this;
	}
	
	public State logout() {
		try {
			this.controller.sendToServer(new LogoutPDU().toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public State update() {
		try {
			this.controller.sendToServer(new UpdatePDU().toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		this.controller.model.waitForListOfPosts();
		return this;
	}
	
	public State publish(String text) {
		try {
			this.controller.sendToServer(new PublishPDU(text).toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		this.controller.model.waitForACK();
		return this;
	}
	
	public State follow(String username) {
		try {
			this.controller.sendToServer(new FollowPDU(username).toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		this.controller.model.waitForACK();
		return this;
	}

	public State unfollow(String username) {
		try {
			this.controller.sendToServer(new UnfollowPDU(username).toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		this.controller.model.waitForACK();
		return this;
	}

	public State like(String string) {
		try {
			Integer id = this.controller.model.postID(string);
			if (id < 0){
				this.controller.gui.errorNoPostList();
			} else {
				this.controller.sendToServer(new LikePDU(id).toXML());
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		this.controller.model.waitForACK();
		return this;
	}

	public State unlike(String string) {
		try {
			Integer id = this.controller.model.postID(string);
			if (id < 0){
				this.controller.gui.errorNoPostList();
			} else {
				this.controller.sendToServer(new UnlikePDU(id).toXML());
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		this.controller.model.waitForACK();
		return this;
	}

	public State following() {
		try {
			this.controller.sendToServer(new FollowingUsersRequestPDU().toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		this.controller.model.waitForListOfUsers();
		return this;
	}

	public State userContent(String username) {
		try {
			this.controller.sendToServer(new UserContentRequestPDU(username).toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		this.controller.model.waitForListOfPosts();
		return this;
	}
	
	public State listUsers() {
		try {
			this.controller.sendToServer(new UserListRequestPDU().toXML());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		this.controller.model.waitForListOfUsers();
		return this;
	}
}
