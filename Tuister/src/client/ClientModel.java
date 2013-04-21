package client;

import java.nio.ByteBuffer;

public class ClientModel {
	private ClientController controller;
	
	/* Status:
	 * 0: Not waiting for anything.
	 * 1: Waiting for ack.
	 * 2: Waiting for a list of posts.
	 * 3: Waiting for a list of users
	 */
	private int status = 0;
	
	public ClientModel(ClientController controller) {
        this.controller = controller;
    }

	public Integer postID(String string) {
		return 1;
	}

	public void processData(int r, ByteBuffer buffer) {
		if (r <= 0){
			this.controller.disconnectFromServer();
		} else {
			buffer.position(0);
			byte[] byteArray = new byte[buffer.remaining()];
			buffer.get(byteArray);
			String s = new String(byteArray);
			
			System.out.println(" + " + s);
		}
	}
	
	public void waitForACK() {
		this.status = 1;
	}
	
	public void waitForListOfPosts() {
		this.status = 2;
	}
	
	public void waitForListOfUsers() {
		this.status = 3;
	}
}
