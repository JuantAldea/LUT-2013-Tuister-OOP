package client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import clientXMLParsers.ClientHandler;

import common.Post;
import common.User;

public class ClientModel {
	private ClientController controller;
	
	private SAXParser saxParser = null;
	protected ClientHandler handler = null;
	
	/* Status:
	 * 0: Not waiting for anything.
	 * 1: Waiting for ACK.
	 * 2: Waiting for a list of posts.
	 * 3: Waiting for a list of users
	 */
	private int status = 0;
	
	/*
	 * 0: Not waiting for a list.
	 * 1: List started, waiting for an end.
	 */
	private int listStatus = 0;
	private LinkedList<Post> postList = null;
	
	public ClientModel(ClientController controller) {
        this.controller = controller;
        
        try {
            this.saxParser = SAXParserFactory.newInstance().newSAXParser();
            this.handler = new ClientHandler();
        } catch (ParserConfigurationException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        } catch (SAXException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
    }

	public Integer postID(String string) {
		if (postList == null){
			return -1;
		} else {
			Integer localId = Integer.parseInt(string);
			if (localId < 1 || localId > this.postList.size()){
				return -1;
			} else {
				Integer id = this.postList.get(this.postList.size() - localId).getId();
				return id;
			}
		}
	}

	public void processData(int received_bytes, ByteBuffer buffer) throws SAXException, IOException {
		if (received_bytes <= 0){
			this.controller.disconnectFromServer();
			this.controller.gui.disconneceted();
		} else {
			buffer.flip();
			byte[] byteArray = new byte[received_bytes];
			buffer.get(byteArray, 0, received_bytes);
			buffer.clear();
			
			String inputString = new String(byteArray);
			String[] splitInput = inputString.split(">");
			
			for (int i = 0; i < splitInput.length; i++){
				if (splitInput[i].equals("\n")) continue; // Debugging
				String part = splitInput[i] + ">";
				byte[] bytePart = part.getBytes();
				
				if (this.handler != null) {
		            this.saxParser.parse(new ByteArrayInputStream(bytePart), this.handler);
		            
		            String name = this.handler.getName();
		            
		            if (name.equalsIgnoreCase("error")){
		            	this.controller.gui.errorReceived(this.handler.getAttributes().getValue("reason"));
		            }
		            
		            else if (this.status == 1 && name.equalsIgnoreCase("ack")){
		            	this.controller.ack(this.handler.getAttributes().getValue("type"));
		            	this.status = 0;
		            }
		            
		            else if (this.status == 2){
		            	if (this.listStatus == 0
		            			&& name.equalsIgnoreCase("list_begin")
		            			&& this.handler.getAttributes().getValue("type").equalsIgnoreCase("posts")){

		            		// Starts the reception of posts
		            		this.postList = new LinkedList<Post>();
		            		this.listStatus = 1;
		            	}
		            	
		            	else if (this.listStatus == 1
		            			&& name.equalsIgnoreCase("post")){
		            		
		            		System.out.println(this.handler.getAttributes().getValue("text"));
		            		
		            		Post p = new Post(this.handler.getAttributes().getValue("text"),
		            				this.handler.getAttributes().getValue("author"),
		            				Integer.parseInt(this.handler.getAttributes().getValue("likes")),
		            				this.handler.getAttributes().getValue("date"),
		            				Integer.parseInt(this.handler.getAttributes().getValue("id")));
		            		
		            		this.postList.add(p);

		            	}
		            	
		            	else if (this.listStatus == 1
		            			&& name.equalsIgnoreCase("list_end")){
		            		
		            		Iterator<Post> it = this.postList.iterator();
		            		int localId = postList.size() + 1;
		            		
		            		while (it.hasNext()){
		            			localId -= 1;
		            			Post p = it.next();
		            			this.controller.gui.printPost(localId, p);
		            		}
		            		
		            		this.listStatus = 0;
		            		this.status = 0;
		            	}
		            	
		            	else {
		            		// Unexpected
		            	}
		            }
		            
		            else if (this.status == 3){
		            	if (this.listStatus == 0
		            			&& name.equalsIgnoreCase("list_begin")
		            			&& this.handler.getAttributes().getValue("type").equalsIgnoreCase("users")){
		            		
		            		// Starts the reception of posts
		            		this.listStatus = 1;
		            	}
		            	
		            	else if (this.listStatus == 1
		            			&& name.equalsIgnoreCase("user")){
		            		
		            		// TODO acumula usuarios
		            		User u = new User(this.handler.getAttributes().getValue("username"));
		            		this.controller.gui.printUser(u);
		            		
		            	}
		            	
		            	else if (this.listStatus == 1
		            			&& name.equalsIgnoreCase("list_end")){
		            		
		            		this.listStatus = 0;
		            		this.status = 0;
		            		// TODO saca los usuarios
		            	}
		            	
		            	else {
		            		// Unexpected
		            		// TODO
		            		
		            	}
		            }
		            
		            else {
		            	// Unexpected
		            }
		        }
			}
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
