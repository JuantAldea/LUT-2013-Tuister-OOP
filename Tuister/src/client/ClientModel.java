package client;

import java.nio.ByteBuffer;

public class ClientModel {
	
	public ClientModel() {
        
    }

	public Integer postID(String string) {
		return 1;
	}

	public void processData(ByteBuffer buffer) {
		byte[] byteArray = new byte[buffer.remaining()];
		buffer.get(byteArray);
		String s = new String(byteArray);
		
		System.out.println(s);
	}
}
