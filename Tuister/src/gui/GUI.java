package gui;

import client.Client;

public class GUI {
	private Client client = null;
	
	public GUI(){
		this.client = new Client(this);
		
		System.out.println("Welcome blablabla");
	}
	
	public static void main(String[] args) {
		
    }
}
