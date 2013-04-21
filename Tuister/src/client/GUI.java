package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import common.Post;
import common.User;


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
			/*try {
				input = this.stdIn.readLine();
				this.processInput(input);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			
			
			try {
				if (this.stdIn.ready()){
					input = this.stdIn.readLine();
					this.processInput(input);
				} else {
					Thread.sleep(100);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

	public void processInput(String input) {
		String[] inputSplit = input.split(" ");
		
		if (inputSplit[0].equalsIgnoreCase("register")){
			if (inputSplit.length == 3){
				this.controller.register(inputSplit[1], inputSplit[2]);
			} else {
				this.printHelp();
			}
		} else if (inputSplit[0].equalsIgnoreCase("login")){
			if (inputSplit.length == 3){
				this.controller.login(inputSplit[1], inputSplit[2]);
			} else {
				this.printHelp();
			}
		} else if (inputSplit[0].equalsIgnoreCase("logout")){
			if (inputSplit.length == 1){
				this.controller.logout();
			} else {
				this.printHelp();
			}
		} else if (inputSplit[0].equalsIgnoreCase("update")){
			if (inputSplit.length == 1){
				this.controller.update();
			} else {
				this.printHelp();
			}
		} else if (inputSplit[0].equalsIgnoreCase("publish")){
			if (inputSplit.length >= 2){
				this.controller.publish(GUI.join(" ", inputSplit, 1));
			} else {
				this.printHelp();
			}
		} else if (inputSplit[0].equalsIgnoreCase("follow")){
			if (inputSplit.length == 2){
				this.controller.follow(inputSplit[1]);
			} else {
				this.printHelp();
			}
		} else if (inputSplit[0].equalsIgnoreCase("unfollow")){
			if (inputSplit.length == 2){
				this.controller.unfollow(inputSplit[1]);
			} else {
				this.printHelp();
			}
		} else if (inputSplit[0].equalsIgnoreCase("like")){
			if (inputSplit.length == 2){
				this.controller.like(inputSplit[1]);
			} else {
				this.printHelp();
			}
		} else if (inputSplit[0].equalsIgnoreCase("unlike")){
			if (inputSplit.length == 2){
				this.controller.unlike(inputSplit[1]);
			} else {
				this.printHelp();
			}
		} else if (inputSplit[0].equalsIgnoreCase("following")){
			if (inputSplit.length == 1){
				this.controller.following();
			} else {
				this.printHelp();
			}
		} else if (inputSplit[0].equalsIgnoreCase("usercontent")){
			if (inputSplit.length == 2){
				this.controller.userContent(inputSplit[1]);
			} else {
				this.printHelp();
			}
		} else if (inputSplit[0].equalsIgnoreCase("listusers")){
			if (inputSplit.length == 1){
				this.controller.listUsers();
			} else {
				this.printHelp();
			}
		} else if (inputSplit[0].equalsIgnoreCase("exit")){
			if (inputSplit.length == 1){
				this.controller.exit();
			} else {
				this.printHelp();
			}
		} else {
			this.printHelp();
		}
	}
	
	public void printHelp() {
		System.out.println("lolz immahelp"); // TODO
		
		String help = "register <username> <password> -> Adds a new user in the system.\n" +
					  "login <username> <password> -> Access with an existing username and password.";
		
		System.out.println(help);
	}
	
	// http://stackoverflow.com/questions/794248/a-method-to-reverse-effect-of-java-string-split
	public static String join(String delimitor, String[] subkeys, int start) {
	    String result = null;
	    if(null!=subkeys && subkeys.length>0) {
	        StringBuffer joinBuffer = new StringBuffer(subkeys[start]);
	        for(int idx=1;idx<subkeys.length;idx++) {
	            joinBuffer.append(delimitor).append(subkeys[idx]);
	        }
	        result = joinBuffer.toString();
	    }
	    return result;
	}
	
	public void deactivate() {
		this.active = false;
	}


	public void errorNotLoggedIn() {
		System.out.println("You are not logged in.");
	}


	public void errorOpeningSocket() {
		System.out.println("The connection could not be established.");
	}


	public void registrationSuccessful() {
		System.out.println("Registered. Please, login.");
	}


	public void loginSuccessful() {
		System.out.println("Logged in.");
	}


	public void logoutSuccessful() {
		System.out.println("Logged out.");
	}


	public void errorAlreadyLoggedIn() {
		System.out.println("You are already logged in.");
	}


	public void disconneceted() {
		System.out.println("You are disconnected.");
	}


	public void errorReceived(String value) {
		System.out.println("ERROR: " + value);
	}


	public void printPost(int localId, Post p) {
		System.out.println("[" + localId + "]" + p);
	}


	public void errorNoPostList() {
		System.out.println("There is no post list.");
	}


	public void printUser(User u) {
		System.out.println("- " + u);
	}
}
