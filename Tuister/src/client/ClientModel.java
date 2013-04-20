package client;

import java.nio.ByteBuffer;

public class ClientModel {
	
	public ClientModel(ClientController clientController) {
        
    }

	public Integer postID(String string) {
		return 1;
	}

	public void processData(ByteBuffer buffer) {
		buffer.position(0);
		byte[] byteArray = new byte[buffer.remaining()];
		buffer.get(byteArray);
		String s = new String(byteArray);
		
		System.out.println(" + " + s);
	}
}
