package clientXMLParsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ClientHandler extends DefaultHandler {
	private String lastName;
	private Attributes lastAttributes;
	
	public ClientHandler() {
		super();
	}
	
	public String getName() {
		return this.lastName;
	}
	
	public Attributes getAttributes() {
		return this.lastAttributes;
	}

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    	this.lastName = qName;
    	this.lastAttributes = attributes;
    }

}
