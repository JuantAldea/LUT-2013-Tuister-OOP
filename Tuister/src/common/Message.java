package common;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.NONE)
public class Message extends XMLSerializable {
    @XmlAttribute(name = "type")
    protected String type;

    @XmlAttribute(name = "content")
    protected String content;
	
	public static String generateMessage(String type, String content){
		try {
			return new Message(type, content).toXML();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Message(String type, String content){
		this.type = type;
		this.content = content;
	}
	
	public static Message XMLParseUser(String xml) throws JAXBException {
        return (Message) XMLSerializable.XMLParse(xml);
    }
}
