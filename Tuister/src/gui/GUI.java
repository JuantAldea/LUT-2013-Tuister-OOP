package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import client.ClientController;

public class GUI extends Thread {
	private ClientController controller = null;
	private BufferedReader stdIn = null;
	
	public GUI(ClientController controller) {
		this.controller = controller;
		this.stdIn = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Welcome blablabla");
	}


	@Override
	public void run() {
		String answer = "";
		
		while(Thread.interrupted() != true){
			try {
				if (this.stdIn.ready()){
					answer = this.stdIn.readLine();
					System.out.println("DIDN'T READ LOL " + answer);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public String askUser() {
		String answer = "";
		System.out.print("Username: ");
		try {
			answer = this.stdIn.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return answer;
	}


	public String askPassword() {
		String answer = "";
		System.out.print("Password: ");
		try {
			answer = this.stdIn.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return answer;
	}
}
