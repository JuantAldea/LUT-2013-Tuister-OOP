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
            e2.printStackTrace();
        } catch (SAXException e2) {
            e2.printStackTrace();
        }
    }

	/*
	 * Gets the global ID of a post specified by the local ID shown in the list
	 */
	public Integer postID(String string) {
		if (postList == null){
			return -1;
		} else {
			try {
				Integer localId = Integer.parseInt(string);
				if (localId < 1 || localId > this.postList.size()){
					return -1;
				} else {
					Integer id = this.postList.get(this.postList.size() - localId).getId();
					return id;
				}
			} catch (NumberFormatException e) {
				return -1;
			}
		}
	}

	/*
	 * The model receives the data from the controller, and process it as needed.
	 */
	public void processData(int received_bytes, ByteBuffer buffer) throws SAXException, IOException {
		if (received_bytes <= 0){
			this.controller.disconnectFromServer();
		} else {
			buffer.flip();
			byte[] byteArray = new byte[received_bytes];
			buffer.get(byteArray, 0, received_bytes);
			buffer.clear();
			
			/*
			 * More than one valid xml can be readed at once. They have to be split.
			 */
			String inputString = new String(byteArray);
			String[] splitInput = inputString.split(">");
			
			for (int i = 0; i < splitInput.length; i++){
				if (splitInput[i].equals("\n")) continue; // For debugging purposes
				String part = splitInput[i] + ">";
				byte[] bytePart = part.getBytes();
				
				if (this.handler != null) {
		            this.saxParser.parse(new ByteArrayInputStream(bytePart), this.handler);
		            
		            String name = this.handler.getName();
		            
		            // If an error is received
		            if (name.equalsIgnoreCase("error")){
		            	this.controller.gui.errorReceived(this.handler.getAttributes().getValue("reason"));
		            }
		            
		            // If the model is waiting for an ack, and it is received
		            else if (this.status == 1 && name.equalsIgnoreCase("ack")){
		            	this.controller.ack(this.handler.getAttributes().getValue("type"));
		            	this.status = 0;
		            }
		            
		         // If the model is waiting for a post list, and it is received
		            else if (this.status == 2){
		            	
		            	// Start of the list
		            	if (this.listStatus == 0
		            			&& name.equalsIgnoreCase("list_begin")
		            			&& this.handler.getAttributes().getValue("type").equalsIgnoreCase("posts")){

		            		// Starts the reception of posts
		            		this.postList = new LinkedList<Post>();
		            		this.listStatus = 1;
		            	}
		            	
		            	// For every element of the list...
		            	else if (this.listStatus == 1
		            			&& name.equalsIgnoreCase("post")){
		            		
		            		Post p = new Post(this.handler.getAttributes().getValue("text"),
		            				this.handler.getAttributes().getValue("author"),
		            				Integer.parseInt(this.handler.getAttributes().getValue("likes")),
		            				this.handler.getAttributes().getValue("date"),
		            				Integer.parseInt(this.handler.getAttributes().getValue("id")));
		            		
		            		this.postList.add(p);

		            	}
		            	
		            	// End of the list
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
		            		this.controller.gui.unexpectedContentError();
		            	}
		            }
		            
		         // If the model is waiting for an user list, and it is received
		            else if (this.status == 3){
		            	
		            	// Start of the list
		            	if (this.listStatus == 0
		            			&& name.equalsIgnoreCase("list_begin")
		            			&& this.handler.getAttributes().getValue("type").equalsIgnoreCase("users")){
		            		
		            		// Starts the reception of posts
		            		this.listStatus = 1;
		            	}
		            	
		            	// For every element of the list...
		            	else if (this.listStatus == 1
		            			&& name.equalsIgnoreCase("user")){

		            		User u = new User(this.handler.getAttributes().getValue("username"));
		            		this.controller.gui.printUser(u);
		            		
		            	}
		            	
		            	// End of the list
		            	else if (this.listStatus == 1
		            			&& name.equalsIgnoreCase("list_end")){
		            		
		            		this.listStatus = 0;
		            		this.status = 0;
		            	}
		            	
		            	else {
		            		this.controller.gui.unexpectedContentError();
		            	}
		            }
		            
		            else {
		            	this.controller.gui.unexpectedContentError();
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
